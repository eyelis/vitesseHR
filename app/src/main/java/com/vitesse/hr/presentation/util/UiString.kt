package com.vitesse.hr.presentation.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource


sealed class UiString {

    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : UiString() {
        override fun toString(): String {
            return resId.toString()
        }
    }

    @Composable
    fun asString(): String {
        return when (this) {
            is StringResource -> stringResource(resId, *args)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is StringResource -> context.getString(resId, *args)
        }
    }

}