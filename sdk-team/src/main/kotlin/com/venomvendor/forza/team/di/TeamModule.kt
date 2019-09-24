/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 16-Sep-2019.
 */

package com.venomvendor.forza.team.di

import com.venomvendor.forza.core.network.NetworkManager
import com.venomvendor.forza.core.storage.SharedPreferencesManager
import com.venomvendor.forza.core.storage.factory.Storage
import com.venomvendor.forza.team.db.TeamDatabase
import com.venomvendor.forza.team.factory.TeamService
import com.venomvendor.forza.team.repository.TeamRepository
import com.venomvendor.forza.team.viewmodel.TeamViewModel
import org.koin.dsl.module

val teamModule = module {

    factory {
        TeamViewModel(get())
    }

    factory {
        TeamRepository(get(), get())
    }

    factory {
        SharedPreferencesManager() as Storage
    }

    factory {
        get<NetworkManager>().getWebService<TeamService>(get())
    }

    single {
        TeamDatabase.getInstance(get())
    }
}
