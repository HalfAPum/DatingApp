package com.narvatov.datingapp.ui.delegate.activity.admob

import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.narvatov.datingapp.model.local.admob.AdMobEvent
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.adMobEvents
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class AdMobDelegate : IAdMobDelegate, InterstitialAdLoadCallback {

    private var componentActivity: ComponentActivity by Delegates.notNull()

    private var loadedInterstitialAd: InterstitialAd? = null


    constructor() {
        //Stub
    }

    operator fun getValue(
        componentActivity: ComponentActivity,
        property: KProperty<*>,
    ) = AdMobDelegate(componentActivity)

    constructor(_componentActivity: ComponentActivity) {
        componentActivity = _componentActivity

        MobileAds.initialize(componentActivity) {
            Timber.tag(TAG).d("MobileAbs initialization. Status: ${it.adapterStatusMap}")
        }

        componentActivity.lifecycleScope.launch {
            adMobEvents.collectLatest { adMobEvent ->
                when(adMobEvent) {
                    AdMobEvent.LoadAdMob -> loadAdMob()
                    AdMobEvent.ShowAdMob -> showAdMob()
                }
            }
        }
    }

    private fun loadAdMob() {
        InterstitialAd.load(
            componentActivity,
            AD_UNIT_ID,
            AdRequest.Builder().build(),
            this,
        )
    }

    override fun onAdFailedToLoad(adError: LoadAdError) {
        Timber.tag(TAG).d("Add loading failed. Error: $adError")
        loadedInterstitialAd = null
    }

    override fun onAdLoaded(interstitialAd: InterstitialAd) {
        Timber.tag(TAG).d("Add successfully loaded")
        loadedInterstitialAd = interstitialAd
    }

    private fun showAdMob() {
        if (loadedInterstitialAd == null) {
            Timber.tag(TAG).d("Show ad but it isn't loaded yet")
            return
        }

        Timber.tag(TAG).d("Show ad")

        loadedInterstitialAd?.show(componentActivity)
    }

    companion object {
        private const val TAG = "ADMOB"
        private const val AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"
    }

}