package com.mvnh.superfinancer.di

import android.app.Application
import androidx.compose.runtime.Composable
import com.mvnh.common.di.commonNetworkModule
import com.mvnh.feature_feed.di.feedDatabaseModule
import com.mvnh.feature_feed.di.feedRepositoryModule
import com.mvnh.feature_feed.di.feedUseCaseModule
import com.mvnh.feature_feed.di.feedViewModelModule
import com.mvnh.feature_finances.di.financesDatabaseModule
import com.mvnh.feature_finances.di.financesRepositoryModule
import com.mvnh.feature_finances.di.financesUseCaseModule
import com.mvnh.feature_finances.di.financesViewModelModule
import com.mvnh.feature_home.di.homeViewModelModule
import com.mvnh.feature_home.di.homeDatabaseModule
import com.mvnh.feature_home.di.homeNetworkModule
import com.mvnh.feature_home.di.homeRepositoryModule
import com.mvnh.feature_home.di.homeUseCaseModule
import com.mvnh.feature_search.di.searchNetworkModule
import com.mvnh.feature_search.di.searchRepositoryModule
import com.mvnh.feature_search.di.searchUseCaseModule
import com.mvnh.feature_search.di.searchViewModelModule
import com.mvnh.superfinancer.ui.screen.RootScreen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.KoinContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

internal val commonModules = listOf(
    commonNetworkModule
)

internal val feedModules = listOf(
    feedDatabaseModule,
    feedRepositoryModule,
    feedUseCaseModule,
    feedViewModelModule
)

internal val financesModules = listOf(
    financesDatabaseModule,
    financesRepositoryModule,
    financesUseCaseModule,
    financesViewModelModule
)

internal val mainModules = listOf(
    homeDatabaseModule,
    homeNetworkModule,
    homeRepositoryModule,
    homeUseCaseModule,
    homeViewModelModule
)

internal val searchModules = listOf(
    searchNetworkModule,
    searchRepositoryModule,
    searchUseCaseModule,
    searchViewModelModule
)

internal val appModules = commonModules + feedModules + financesModules + mainModules + searchModules

internal class SuperFinancerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@SuperFinancerApp)
            modules(appModules)
        }
    }
}

@Composable
internal fun SuperFinancer() {
    KoinContext {
        RootScreen()
    }
}
