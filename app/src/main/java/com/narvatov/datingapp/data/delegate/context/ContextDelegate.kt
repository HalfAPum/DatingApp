package com.narvatov.datingapp.data.delegate.context

import android.content.Context
import com.narvatov.datingapp.utils.inject

object ContextDelegate : IContextDelegate {

    override val context: Context by inject()

}