package technited.minds.test.db

import android.app.Activity
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import technited.minds.test.models.Activities

@Dao
interface HealthDao {

    // insert notes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: Activities)

    // update notes
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateActivity(activity: Activities)

    // get all notes from db
    @Query("SELECT * FROM Activities")
    fun getActivities(): Flow<List<Activities>>

    // delete notes by id
    @Query("DELETE FROM Activities where id=:id")
    suspend fun deleteActivity(id: Int)
}