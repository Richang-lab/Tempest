package com.emix.tempest.ui.base

import com.emix.tempest.R

class ImageSelector {

    companion object{
        fun backgroundImage(code:Int) : Int{

            return when(code){
                1000 -> sunnyImageSelector()
                1009,1006,1003 -> cloudyImageSelector()
                1219,1279,1069,1216,1222,1210,1255,1213,1204,1066,1237,1249->lightSnowImageSelector()
                1225,1114,1282,1258,1264,1252,1207,1117-> heavySnowImageSelector()
                1030->mistImageSelector()
                1135,1147->fogImageSelector()
                1198,1240,1168,1150,1072,1183,1180,1261,1063,1153->R.drawable.lr_1198
                1189,1243,1201,1186-> R.drawable.mr_1189
                1171,1192,1246,1195-> R.drawable.hr_1192
                1276,1087,1273->R.drawable.hrt_1276
                else -> R.drawable.s_1000_5
            }
        }

        private fun fogImageSelector(): Int{
            return listOf(
                R.drawable.f_1135,
                R.drawable.ff_1147).random()
        }

        private fun mistImageSelector(): Int{
            return listOf(
                R.drawable.m_1030,
                R.drawable.m_1030_1,
                R.drawable.m_1030_2).random()
        }

        private fun heavySnowImageSelector(): Int{
            return listOf(
                R.drawable.mhs_1207,
                R.drawable.mhs_1282,
                R.drawable.mhs_1282_1).random()
        }

        private fun lightSnowImageSelector(): Int{
            return listOf(
                R.drawable.phs_1222,
                R.drawable.ps_1066,
                R.drawable.ls_1204,
                R.drawable.ls_1213,
                R.drawable.ip_1237,
                R.drawable.ms_1219).random()
        }

        private fun cloudyImageSelector(): Int{
            return listOf(
                R.drawable.c_1006,
                R.drawable.c_1006_1,
                R.drawable.c_1006_2,
                R.drawable.c_1006_3,
                R.drawable.o_1009,
                R.drawable.pc_1003).random()
        }

        private fun sunnyImageSelector() : Int{
            return listOf(
                R.drawable.s_1000_1,
                R.drawable.s_1000_2,
                R.drawable.s_1000_3,
                R.drawable.s_1000_4,
                R.drawable.s_1000_5,
                R.drawable.s_1000_6,
                R.drawable.s_1000_7).random()
        }
    }
}