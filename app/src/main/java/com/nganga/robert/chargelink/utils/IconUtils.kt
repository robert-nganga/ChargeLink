package com.nganga.robert.chargelink.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.nganga.robert.chargelink.R
import com.nganga.robert.chargelink.models.Charger

object IconUtils {

    fun getChargerIcon(charger: String): Int{
        return when(charger){
            "CCS 1 DC" -> R.drawable.ic_ev_plug_ccs2
            "CCS 2 DC" -> R.drawable.ic_ev_plug_ccs2_combo
            "Mennekes (Type 2) AC" -> R.drawable.ic_ev_plug_iec_mennekes_t2
            "J1772 (Type 1) AC" -> R.drawable.ic_ev_plug_j1772_t1
            "Tesla NACS AC/DC" -> R.drawable.ic_ev_plug_tesla
            else -> R.drawable.ic_ev_plug_j1772_t1
        }
    }

    fun bitmapFromVector(
        context: Context,
        vectorResId: Int,
        color: Int,
        width: Int = 50,
        height: Int = 50
    ): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

        val tintedDrawable = DrawableCompat.wrap(vectorDrawable!!)
        DrawableCompat.setTint(tintedDrawable, color)

        vectorDrawable.setBounds(0, 0, width, height)
        val bitmap = Bitmap.createBitmap(
            width,
            height,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        tintedDrawable.setBounds(0, 0, canvas.width, canvas.height)
        tintedDrawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}