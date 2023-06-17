package com.narvatov.datingapp.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.narvatov.datingapp.ui.common.PhotoPickBottomSheet
import com.narvatov.datingapp.ui.navigation.BottomBar
import com.narvatov.datingapp.ui.navigation.NavHostContent
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.bottomSheetVisibilityEvents
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.hidePhotoBottomSheet
import com.narvatov.datingapp.ui.navigation.showBottomBar
import com.narvatov.datingapp.ui.theme.DatingAppTheme
import com.narvatov.datingapp.ui.theme.Shapes
import com.narvatov.datingapp.ui.viewmodel.PhotoViewModel
import com.narvatov.datingapp.ui.viewmodel.UserAvailabilityViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val photoViewModel by viewModel<PhotoViewModel>()
    private val userAvailabilityViewModel by viewModel<UserAvailabilityViewModel>()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

                ModalBottomSheetLayout(
                    sheetState = modalSheetState,
                    sheetShape = Shapes.large.copy(bottomStart = CornerSize(0.dp), bottomEnd = CornerSize(0.dp)),
                    sheetContent = { PhotoPickBottomSheet() }
                ) {
                    Scaffold(
                        navController = navController,
                        bottomBar = { BottomBar(navController, bottomBarState.value) },
                        content = { _, innerPadding ->
                            NavHostContent(navController, photoViewModel, innerPadding)
                        }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        userAvailabilityViewModel.isUserAvailable = true
    }

    override fun onPause() {
        super.onPause()
        userAvailabilityViewModel.isUserAvailable = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //TODO implement proper handling instead of this bullshit
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_CANCELED || data == null) return

        val bitmap = when(requestCode) {
            CAMERA_IMAGE_CODE -> {
                data.extras?.get("data") as? Bitmap ?: return
            }
            GALLERY_IMAGE_CODE -> {
                MediaStore.Images.Media.getBitmap(this.contentResolver, data.data)
            }
            else -> return
        }

        photoViewModel.onPhotoChosen(bitmap)

        hidePhotoBottomSheet()
    }

    companion object {
        const val CAMERA_IMAGE_CODE = 777
        const val GALLERY_IMAGE_CODE = 666
    }

}