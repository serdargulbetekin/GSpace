package com.example.gspace.di


import android.app.Application
import android.content.Context
import com.example.gspace.api.RestApi
import com.example.gspace.constant.URL_WEB
import com.example.gspace.modules.createspaceship.CreateSpaceShipRepo
import com.example.gspace.repo.GSpaceRepo
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Component(modules = [AppModule::class, NetModule::class])
@AppScope
interface AppComponent {
    fun getContext(): Context
    fun getApiInterface(): RestApi
    fun getRetrofit(): Retrofit
    fun getCreateShipRepo(): CreateSpaceShipRepo
    fun getGSpaceRepo(): GSpaceRepo

}

interface BaseAppComponentBuilder<out T, out K> {
    fun appComponent(appComponent: AppComponent): T
    fun build(): K
}

@Module
class AppModule(private val context: Context) {

    @Provides
    @AppScope
    fun provideContext(): Context {
        return context
    }

    @Provides
    @AppScope
    fun provideCreateShipRepo(application: Application) = CreateSpaceShipRepo(application)

    @Provides
    @AppScope
    fun provideGSpaceRepo(restApi: RestApi) = GSpaceRepo(restApi)
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