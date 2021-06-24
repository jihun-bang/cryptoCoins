package jihun.bang.cryptocoins.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import jihun.bang.cryptocoins.BuildConfig
import jihun.bang.cryptocoins.api.ExchangeApi
import jihun.bang.cryptocoins.viewModels.ExchangeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://api.upbit.com")
            .client(OkHttpClient.Builder()
                .apply {
                    if (BuildConfig.DEBUG.not()) {
                        addNetworkInterceptor(
                            HttpLoggingInterceptor().setLevel(
                            HttpLoggingInterceptor.Level.BODY))
                        addNetworkInterceptor(StethoInterceptor())
                    }
                }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeApi::class.java)
    }
}

val viewModelModule = module {
    viewModel { ExchangeViewModel(get()) }
}