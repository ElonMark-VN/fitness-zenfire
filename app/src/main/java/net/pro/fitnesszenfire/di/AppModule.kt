package net.pro.fitnesszenfire.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.pro.fitnesszenfire.data.repository.HomeRepositoryImpl
import net.pro.fitnesszenfire.data.repository.LoginRepositoryImpl
import net.pro.fitnesszenfire.data.repository.OnboardingRepositoryImpl
import net.pro.fitnesszenfire.data.repository.ProfileRepositoryImpl
import net.pro.fitnesszenfire.data.repository.UserDataRepositoryImpl
import net.pro.fitnesszenfire.domain.repository.HomeRepository
import net.pro.fitnesszenfire.domain.repository.LoginRepository
import net.pro.fitnesszenfire.domain.repository.OnBoardingRepository
import net.pro.fitnesszenfire.domain.repository.ProfileRepository
import net.pro.fitnesszenfire.domain.repository.UserDataRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

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
    fun provideLoginRepository(
//        @ApplicationContext context: Context,
        auth: FirebaseAuth,
//        firestore: FirebaseFirestore
    ): LoginRepository {
        return LoginRepositoryImpl( auth)
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
    fun providesOnBoardingRepository( @ApplicationContext context: Context): OnBoardingRepository = OnboardingRepositoryImpl(context = context)
    // Add other dependencies like Room Database, SharedPreferences, etc.
}
