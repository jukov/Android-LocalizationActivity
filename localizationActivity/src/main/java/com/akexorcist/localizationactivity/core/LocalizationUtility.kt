package com.akexorcist.localizationactivity.core

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import java.util.*

/**
 * Created by Akexorcist on 10/19/2017 AD.
 */

object LocalizationUtility {
    fun applyLocalizationContext(baseContext: Context): Context {
        val baseLocale = getLocaleFromConfiguration(baseContext.resources.configuration)
        val currentLocale = LanguageSetting.getLanguage(baseContext)
        if (!baseLocale.toString().equals(currentLocale.toString(), ignoreCase = true)) {
            val context = LocalizationContext(baseContext)
            val config = context.resources.configuration
            return when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                    config.setLocale(currentLocale)
                    val localeList = LocaleList(currentLocale)
                    LocaleList.setDefault(localeList)
                    config.setLocales(localeList)
                    context.createConfigurationContext(config)
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 -> {
                    config.setLocale(currentLocale)
                    context.createConfigurationContext(config)
                }
                else -> context
            }
        } else {
            return baseContext
        }
    }

    @Suppress("DEPRECATION")
    private fun getLocaleFromConfiguration(configuration: Configuration): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.locales.get(0)
        } else {
            configuration.locale
        }
    }
}
