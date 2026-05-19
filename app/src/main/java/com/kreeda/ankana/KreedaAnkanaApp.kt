package com.kreeda.ankana

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * KreedaAnkanaApp — Application class for Hilt dependency injection.
 * Annotated with @HiltAndroidApp to trigger Hilt code generation.
 */
@HiltAndroidApp
class KreedaAnkanaApp : Application()
