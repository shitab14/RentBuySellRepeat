package app.smir.rentbuysellrepeat.di

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/

import org.koin.dsl.module

object AppModule {
    private val networkModule = module {
    }

    private val databaseModule = module {
    }

    private val dataSourceModule = module {
    }

    private val repositoryModule = module {
    }

    private val useCaseModule = module {
    }

    private val utilityModule = module {
    }

    val all = listOf(
        networkModule,
        databaseModule,
        dataSourceModule,
        repositoryModule,
        useCaseModule,
        utilityModule
    )
}