package com.simplesoftware.pokedexdabia.domain

import com.simplesoftware.pokedexdabia.network.RemoteImpl
import com.simplesoftware.pokedexdabia.network.networkConfig.RetrofitInstance
import com.simplesoftware.pokedexdabia.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModule {

    val modules: List<Module>
        get() = listOf(
            homeViewModelModule,
            remoteModule
        )

    private val homeViewModelModule = module {
        viewModel { HomeViewModel(remote = get())}
    }

    private val remoteModule = module {
        single<Remote> { RemoteImpl(RetrofitInstance.apiService)}
    }


}