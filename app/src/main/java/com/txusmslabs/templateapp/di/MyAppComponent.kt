package com.txusmslabs.templateapp.di

import android.app.Application
import com.txusmslabs.templateapp.ui.detail.DetailFragmentComponent
import com.txusmslabs.templateapp.ui.detail.DetailFragmentModule
import com.txusmslabs.templateapp.ui.main.MainFragmentComponent
import com.txusmslabs.templateapp.ui.main.MainFragmentModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface MyAppComponent {

    fun plus(module: MainFragmentModule): MainFragmentComponent
    fun plus(module: DetailFragmentModule): DetailFragmentComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): MyAppComponent
    }
}