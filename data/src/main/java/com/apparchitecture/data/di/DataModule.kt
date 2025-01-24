package com.apparchitecture.data.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.apparchitecture.data.database.AppDatabase
import com.apparchitecture.data.database.MyModelDao
import com.apparchitecture.data.network.MyApi
import com.apparchitecture.data.repository.DefaultMyModelRepository
import com.apparchitecture.data.repository.MyModelRepository
import com.apparchitecture.data.repository.MyRemoteRepository
import com.apparchitecture.data.repository.MyRemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.util.Arrays
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun bindsMyModelRepository(
        myModelRepository: DefaultMyModelRepository
    ): MyModelRepository = myModelRepository

    @Provides
    @Singleton
    fun provideMyModelDao(appDatabase: AppDatabase): MyModelDao {
        return appDatabase.myModelDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "MyModel"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMyApi(okHttpClient: OkHttpClient): MyApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(MyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMyRemoteRepository(impl: MyRemoteRepositoryImpl): MyRemoteRepository = impl

    @Provides
    @Singleton
    fun providesOkHttpClient(@ApplicationContext appContext: Context): OkHttpClient {

        val builder = OkHttpClient.Builder()
        builder.connectTimeout(
            NETWORK_TIMEOUT_SECONDS,
            TimeUnit.SECONDS
        )
        builder.writeTimeout(
            NETWORK_TIMEOUT_SECONDS,
            TimeUnit.SECONDS
        )
        builder.readTimeout(
            NETWORK_TIMEOUT_SECONDS,
            TimeUnit.SECONDS
        )
        builder.addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        )

        //Certificates
        try {
            //Create new instance of "Certificate" for every certificate we have.
            val certificates: MutableList<Certificate> = ArrayList()

            appContext.assets.list("")?.forEach { asset ->
                if (asset.endsWith(".cer") || asset.endsWith(".crt")) {
                    val isCertificate: InputStream = BufferedInputStream(
                        appContext.assets.open(asset)
                    )
                    val cf = CertificateFactory.getInstance("X.509")
                    certificates.add(cf.generateCertificate(isCertificate))
                }
            }

            if (certificates.size > 0) {

                // Create a KeyStore containing our trusted CAs
                val keyStoreType = KeyStore.getDefaultType()
                val keyStore = KeyStore.getInstance(keyStoreType)
                keyStore.load(null, null)
                for (i in certificates.indices) {
                    keyStore.setCertificateEntry(
                        "ca$i",
                        certificates[i]
                    )
                }

                // Create a TrustManager that trusts the CAs in our KeyStore
                val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
                val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
                tmf.init(keyStore)

                // Create an SSLContext that uses our TrustManager
                val context = SSLContext.getInstance("TLSv1.2")
                context.init(null, tmf.trustManagers, null)
                val trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                trustManagerFactory.init(null as KeyStore?)
                val trustManagers = trustManagerFactory.trustManagers
                check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
                    ("Unexpected default trust managers:"
                            + Arrays.toString(trustManagers))
                }
                val trustManager = trustManagers[0] as X509TrustManager
                builder.sslSocketFactory(context.socketFactory, trustManager)
                builder.hostnameVerifier { hostname: String?, session: SSLSession? -> true }

                //For HttpUrlConnection
                HttpsURLConnection.setDefaultHostnameVerifier { hostname, session -> true }
                HttpsURLConnection.setDefaultSSLSocketFactory(context.socketFactory)
            }
        } catch (ex: Exception) {
            Log.d(TAG, "provideOkHttpClient: ", ex)
        }
        return builder.build()
    }

    companion object {

        const val TAG = "DataModule"
        const val NETWORK_TIMEOUT_SECONDS = 120L

    }

}
