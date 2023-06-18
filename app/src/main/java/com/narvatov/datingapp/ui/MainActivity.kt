package com.narvatov.datingapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
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
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat.Type.ime
import androidx.core.view.WindowInsetsCompat.toWindowInsetsCompat
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.narvatov.datingapp.ui.common.PhotoPickBottomSheet
import com.narvatov.datingapp.ui.delegate.activity.admob.AdMobDelegate
import com.narvatov.datingapp.ui.delegate.activity.availability.UserAvailabilityDelegate
import com.narvatov.datingapp.ui.navigation.BottomBar
import com.narvatov.datingapp.ui.navigation.NavHostContent
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.bottomSheetVisibilityEvents
import com.narvatov.datingapp.ui.navigation.showBottomBar
import com.narvatov.datingapp.ui.theme.DatingAppTheme
import com.narvatov.datingapp.ui.theme.Shapes
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    private val userAvailabilityDelegate by UserAvailabilityDelegate()
    private val adMobDelegate by AdMobDelegate()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userAvailabilityDelegate.initialize()
        adMobDelegate.initialize()

        setContent {
            DatingAppTheme {
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

                val coroutineScope = rememberCoroutineScope()
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
                    sheetContent = { PhotoPickBottomSheet(getViewModel(owner = activityViewModelStoreOwner)) }
                ) {
                    Scaffold(
                        navController = navController,
                        bottomBar = { BottomBar(navController, bottomBarState.value) },
                        content = { _, innerPadding ->
                            NavHostContent(navController, activityViewModelStoreOwner, innerPadding)
                        }
                    )
                }
            }
        }
    }

}