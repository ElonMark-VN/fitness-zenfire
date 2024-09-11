package net.pro.fitnesszenfire.di

import android.content.Context
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.pro.fitnesszenfire.R
import net.pro.fitnesszenfire.data.data_source.DataStoreManager
import net.pro.fitnesszenfire.data.repository.FeedRepositoryImpl
import net.pro.fitnesszenfire.data.repository.HomeRepositoryImpl
import net.pro.fitnesszenfire.data.repository.LoginRepositoryImpl
import net.pro.fitnesszenfire.data.repository.OnboardingRepositoryImpl
import net.pro.fitnesszenfire.data.repository.ProfileRepositoryImpl
import net.pro.fitnesszenfire.data.repository.UserDataRepositoryImpl
import net.pro.fitnesszenfire.domain.repository.FeedRepository
import net.pro.fitnesszenfire.domain.repository.HomeRepository
import net.pro.fitnesszenfire.domain.repository.LoginRepository
import net.pro.fitnesszenfire.domain.repository.OnBoardingRepository
import net.pro.fitnesszenfire.domain.repository.ProfileRepository
import net.pro.fitnesszenfire.domain.repository.UserDataRepository
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOneTapClient(
        @ApplicationContext context: Context
    ): SignInClient = Identity.getSignInClient(context)

    @Provides
    @Named("signInRequest")
    fun provideSignInRequest(
        @ApplicationContext context: Context
    ): BeginSignInRequest = BeginSignInRequest.Builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(context.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(true)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    @Provides
    @Named("signUpRequest")
    fun provideSignUpRequest(
        @ApplicationContext context: Context
    ): BeginSignInRequest = BeginSignInRequest.Builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(context.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build()
        )
        .build()

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideLoginRepository(
        oneTapClient: SignInClient,
        @Named("signInRequest") signInRequest: BeginSignInRequest,
        @Named("signUpRequest") signUpRequest: BeginSignInRequest,
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): LoginRepository =
        LoginRepositoryImpl(oneTapClient, signInRequest, signUpRequest, auth, firestore)


    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }


    @Provides
    @Singleton
    fun provideProfileRepository(
        @ApplicationContext context: Context,
        firestore: FirebaseFirestore,
    ): ProfileRepository = ProfileRepositoryImpl(context, firestore)

    @Provides
    @Singleton
    fun providesHomeRepository(): HomeRepository = HomeRepositoryImpl()

    @Provides
    @Singleton
    fun providesUserDataRepository(): UserDataRepository = UserDataRepositoryImpl()


    @Provides
    @Singleton
    fun providesOnBoardingRepository(@ApplicationContext context: Context): OnBoardingRepository =
        OnboardingRepositoryImpl(context = context)
    // Add other dependencies like Room Database, SharedPreferences, etc.

    @Provides
    @Singleton
    fun provideDataStoreManager(
        @ApplicationContext context: Context
    ): DataStoreManager = DataStoreManager(context)

    //


    @Provides
    fun providePostRepository(
            firestore: FirebaseFirestore,
            storage: FirebaseStorage
    ): FeedRepository{
        return FeedRepositoryImpl(firestore, storage)
    }
}
