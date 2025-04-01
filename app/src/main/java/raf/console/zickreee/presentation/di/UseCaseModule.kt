package raf.console.zickreee.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import raf.console.zickreee.domain.repository.CounterRepository
import raf.console.zickreee.domain.use_cases.AddCounter
import raf.console.zickreee.domain.use_cases.DeleteCounter
import raf.console.zickreee.domain.use_cases.GetCounterById
import raf.console.zickreee.domain.use_cases.GetCounters
import raf.console.zickreee.domain.use_cases.UpdateCounter

// di/UseCaseModule.kt
@Module
@InstallIn(ViewModelComponent::class) // Важно для ViewModel
object UseCaseModule {

    @Provides
    fun provideGetCounters(repository: CounterRepository): GetCounters {
        return GetCounters(repository)
    }

    @Provides
    fun provideAddCounter(repository: CounterRepository): AddCounter {
        return AddCounter(repository)
    }

    @Provides
    fun provideUpdateCounter(repository: CounterRepository): UpdateCounter {
        return UpdateCounter(repository)
    }

    @Provides
    fun provideDeleteCounter(repository: CounterRepository): DeleteCounter {
        return DeleteCounter(repository)
    }

    @Provides
    fun provideGetCounterById(repository: CounterRepository): GetCounterById {
        return GetCounterById(repository)
    }
}