package com.emix.tempest.internal

import java.io.IOException

// Custom Exception
class DateNotFoundException: Exception()
class NoConnectivityException: IOException()
class LocationPermissionNotGrantedException: Exception()