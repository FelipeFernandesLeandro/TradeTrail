package com.wolfgang.tradetrail.core.data.session

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val session: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { session.tokenFlow.first() }
        val request = if (token != null)
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        else chain.request()
        return chain.proceed(request)
    }
}