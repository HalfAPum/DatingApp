package com.narvatov.datingapp.delegate.common.context

import android.content.Context
import com.narvatov.datingapp.utils.inject

object ContextDelegate : IContextDelegate {

    override val context: Context by inject()

}