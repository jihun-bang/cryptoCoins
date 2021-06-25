package jihun.bang.cryptocoins

import android.app.Application
import com.facebook.stetho.Stetho
import jihun.bang.cryptocoins.data.DB
import jihun.bang.cryptocoins.data.BookMarkDB
import jihun.bang.cryptocoins.di.networkModule
import jihun.bang.cryptocoins.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        DB.db = BookMarkDB.getInstance(this)
        startKoin {
            androidContext(applicationContext)
            modules(networkModule, viewModelModule)
        }
        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
    }
}