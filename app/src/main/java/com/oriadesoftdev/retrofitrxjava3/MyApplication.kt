package com.oriadesoftdev.retrofitrxjava3

import android.app.Application
import com.oriadesoftdev.retrofitrxjava3.data.remote.NetworkService
import com.oriadesoftdev.retrofitrxjava3.data.remote.Networking

class MyApplication : Application() {
    lateinit var networkService: NetworkService
    override fun onCreate() {
        super.onCreate()
        networkService = Networking.create(BuildConfig.BASE_URL, this.cacheDir, 10 * 1024 * 1024)
    }
}