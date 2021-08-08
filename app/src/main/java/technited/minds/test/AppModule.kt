package technited.minds.test

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import technited.minds.test.db.HealthDatabase
import javax.inject.Singleton

@InstallIn(ActivityComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(application: Application): HealthDatabase {
        return HealthDatabase.invoke(application.applicationContext)
    }
}