package technited.minds.test.repository

import technited.minds.test.db.HealthDatabase
import technited.minds.test.models.Activities
import javax.inject.Inject

class HealthRepo @Inject constructor(private val db: HealthDatabase) {

    // insert notes
    suspend fun insert(activities: Activities) = db.getActivitiesDao().insertActivity(activities)

    // update notes
    suspend fun update(activities: Activities) = db.getActivitiesDao().updateActivity(activities)

    // get saved notes
    fun getSavedActivities() = db.getActivitiesDao().getActivities()

    // delete note by ID
    suspend fun deleteActivity(id: Int) = db.getActivitiesDao().deleteActivity(id)
}