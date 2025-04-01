package raf.console.zickreee.presentation.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import raf.console.zickreee.data.local.dao.CounterDao
import raf.console.zickreee.data.repository.CounterRepositoryImpl
import raf.console.zickreee.data.local.database.CounterDatabase
import raf.console.zickreee.domain.repository.CounterRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CounterDatabase {
        return Room.databaseBuilder(
            context,
            CounterDatabase::class.java,
            "counter_db"
        )
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideDao(database: CounterDatabase): CounterDao = database.counterDao()


    @Provides
    @Singleton
    fun provideRepository(dao: CounterDao): CounterRepository {
        return CounterRepositoryImpl(dao)
    }
}