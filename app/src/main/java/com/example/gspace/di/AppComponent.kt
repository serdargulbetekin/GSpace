package com.example.gspace.di


import android.content.Context
import com.example.gspace.api.RestApi
import com.example.gspace.constant.URL_WEB
import com.example.gspace.modules.createspaceship.CreateSpaceShipRepo
import com.example.gspace.modules.createspaceship.room.SpaceShipDao
import com.example.gspace.modules.createspaceship.room.SpaceShipDatabase
import com.example.gspace.modules.station.StationRepo
import com.example.gspace.modules.station.room.StationDao
import com.example.gspace.modules.station.room.StationDatabase
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Component(modules = [AppModule::class, NetModule::class, StationRepoModule::class, CreateShipRepoModule::class])
@AppScope
interface AppComponent {
    fun getContext(): Context
    fun getApiInterface(): RestApi
    fun getRetrofit(): Retrofit
    fun getCreateShipRepo(): CreateSpaceShipRepo
    fun getStationRepo(): StationRepo
    fun getSpaceShipDao(): SpaceShipDao
    fun getStationDao(): StationDao

}

@Module
class AppModule(private val context: Context) {

    @Provides
    @AppScope
    fun provideContext(): Context {
        return context
    }

}

@Module
class StationRepoModule {
    @Provides
    fun provideStationRepo(restApi: RestApi) =
        StationRepo(restApi)

    @Provides
    fun provideStationDB(context: Context) = StationDatabase.invoke(context)

    @Provides
    fun provideStationDao(stationDatabase: StationDatabase) = stationDatabase.stationDao()

}

@Module
class CreateShipRepoModule {
    @Provides
    fun provideCreateShipRepo() = CreateSpaceShipRepo()

    @Provides
    fun provideSpaceShipDB(context: Context) = SpaceShipDatabase.invoke(context)

    @Provides
    fun provideStationDao(spaceShipDatabase: SpaceShipDatabase) = spaceShipDatabase.spaceShipDao()

}

@Module
class NetModule {
    @Provides
    @AppScope
    internal fun provideNetworkService(retrofit: Retrofit): RestApi {
        return retrofit.create(RestApi::class.java)
    }

    @Provides
    @AppScope
    internal fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL_WEB)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }
}