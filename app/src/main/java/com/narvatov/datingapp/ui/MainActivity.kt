package com.narvatov.datingapp.ui

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat.Type.ime
import androidx.core.view.WindowInsetsCompat.toWindowInsetsCompat
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.narvatov.datingapp.data.preference.NotificationPreferencesDataStore
import com.narvatov.datingapp.delegate.activity.admob.AdMobDelegate
import com.narvatov.datingapp.delegate.activity.availability.UserAvailabilityDelegate
import com.narvatov.datingapp.model.local.notification.NotificationPreference.*
import com.narvatov.datingapp.ui.common.PhotoPickBottomSheet
import com.narvatov.datingapp.ui.navigation.BottomBar
import com.narvatov.datingapp.ui.navigation.NavHostContent
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.bottomSheetVisibilityEvents
import com.narvatov.datingapp.ui.navigation.showBottomBar
import com.narvatov.datingapp.ui.theme.DatingAppTheme
import com.narvatov.datingapp.ui.theme.Shapes
import com.narvatov.datingapp.utils.inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel
import timber.log.Timber

class MainActivity : ComponentActivity() {

    private val userAvailabilityDelegate by UserAvailabilityDelegate()
    private val adMobDelegate by AdMobDelegate()

    private val notificationPreferenceDataStore: NotificationPreferencesDataStore by inject()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userAvailabilityDelegate.initialize()
        adMobDelegate.initialize()

        setContent {
            DatingAppTheme {
                val notificationTag = "NOTIFICATIONS_PERMISSION"

                val coroutineScope = rememberCoroutineScope()

                val launcher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { isGranted ->
                    coroutineScope.launch {
                        if (isGranted) {
                            Timber.tag(notificationTag).d("Permission granted")
                            notificationPreferenceDataStore.save(GRANTED)
                        } else {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                                Timber.tag(notificationTag).d("Permission denied show rationale")
                                notificationPreferenceDataStore.save(SHOW_RATIONALE)
                            } else {
                                Timber.tag(notificationTag).d("Permission denied")
                                notificationPreferenceDataStore.save(DENIED)
                            }
                        }
                    }
                }

                val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                val shouldBottomBarBeVisible = navBackStackEntry?.destination?.route?.showBottomBar ?: false

                bottomBarState.value = shouldBottomBarBeVisible

                window.decorView.setOnApplyWindowInsetsListener { view, insets ->
                    val insetsCompat = toWindowInsetsCompat(insets, view)
                    bottomBarState.value = shouldBottomBarBeVisible && insetsCompat.isVisible(ime()).not()
                    view.onApplyWindowInsets(insets)
                }

                val modalSheetState = rememberModalBottomSheetState(
                    initialValue = ModalBottomSheetValue.Hidden,
                    confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
                    skipHalfExpanded = true
                )

                BackHandler(modalSheetState.isVisible) {
                    coroutineScope.launch { modalSheetState.hide() }
                }

                LaunchedEffect("bottomSheetVisibilityEvents") {
                    bottomSheetVisibilityEvents.collectLatest { isBottomSheetVisible ->
                        if (isBottomSheetVisible) {
                            modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                        } else {
                            modalSheetState.hide()
                        }
                    }
                }

                val activityViewModelStoreOwner: ViewModelStoreOwner = this

                ModalBottomSheetLayout(
                    sheetState = modalSheetState,
                    sheetShape = Shapes.large.copy(bottomStart = CornerSize(0.dp), bottomEnd = CornerSize(0.dp)),
                    sheetContent = { PhotoPickBottomSheet(getViewModel(owner = activityViewModelStoreOwner), modalSheetState) }
                ) {
                    Scaffold(
                        navController = navController,
                        bottomBar = { BottomBar(navController, bottomBarState.value) },
                        content = { _, innerPadding ->
                            NavHostContent(navController, activityViewModelStoreOwner, innerPadding)
                        }
                    )
                    val context = LocalContext.current

//                    Button(
//                        {
//                            if (ContextCompat.checkSelfPermission(
//                                    context,
//                                    Manifest.permission.POST_NOTIFICATIONS
//                                ) == PackageManager.PERMISSION_GRANTED
//                            ) {
//                                Timber.tag(notificationTag).d("Permission has already been granted")
//                            } else {
//                                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
//                                    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
//                                }
//                            }
//                        }
//                    ) {
//                        Text(
//                            text = "permission"
//                        )
//                    }
                }
            }
        }
    }

}