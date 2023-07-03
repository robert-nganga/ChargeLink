package com.nganga.robert.chargelink.utils

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
}