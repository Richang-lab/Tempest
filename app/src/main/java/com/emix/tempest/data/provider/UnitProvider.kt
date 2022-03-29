package com.emix.tempest.data.provider

import com.emix.tempest.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem() : UnitSystem
}