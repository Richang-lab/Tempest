package com.emix.tempest.data.network

import okhttp3.Interceptor

// we are using this interface because of dependency injection
interface ConnectivityInterceptor : Interceptor