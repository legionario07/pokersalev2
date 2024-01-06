package br.com.khodahafez.pokersale.ui.utils

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.widget.TextView
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector


fun Context.showToast(message: String?) {
    Toast.makeText(this, message.orEmpty(), Toast.LENGTH_LONG).show()
}
