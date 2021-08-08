package technited.minds.test.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import technited.minds.test.models.Activities


@Database(
    entities = [Activities::class],
    version = 1,
    exportSchema = false
)

abstract class HealthDatabase : RoomDatabase() {

    abstract fun getActivitiesDao(): HealthDao

    companion object {
        @Volatile
        private var instance: HealthDatabase? = null
        private val LOCK = Any()

        // Check for DB instance if not null then get or insert or else create new DB Instance
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {

            instance ?: createDatabase(context).also { instance = it }
        }

        // create db instance
        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            HealthDatabase::class.java,
            "HealthWellness_db.db"
        ).build()
    }
}