package technited.minds.test.ui.activities

import android.app.Application
import kotlinx.coroutines.flow.MutableStateFlow
import technited.minds.test.models.Activities
import technited.minds.test.repository.HealthRepo
import javax.inject.Inject

class ActivitiesViewModel @Inject internal constructor(
    application: Application,
    private val healthRepo: HealthRepo,
) : ActivitiesViewModel(application) {

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow<ActivitiesViewState>(ActivitiesViewState.Loading)

    // The UI collects from this StateFlow to get its state updates
    val uiState = _uiState.asStateFlow()

    // save notes
    fun insertActivity(taskName: String, taskDesc: String) = viewModelScope.launch {
        val activity = Activities(
            title = taskName,
            description = taskDesc
        )
        healthRepo.insert(activity)
    }

    // update notes
    fun updateActivity(id: Int, taskName: String, taskDesc: String) = viewModelScope.launch {
        val activity = Activities(
            id = id,
            title = taskName,
            description = taskDesc
        )
        healthRepo.update(activity)
    }

    // get all saved notes by default
    init {
        viewModelScope.launch {
            healthRepo.getSavedActivities().distinctUntilChanged().collect { result ->
                if (result.isNullOrEmpty()) {
                    _uiState.value = ActivitiesViewState.Empty
                } else {
                    _uiState.value = ActivitiesViewState.Success(result)
                }
            }
        }
    }

    // delete note by ID
    fun deleteActivityByID(id: Int) = viewModelScope.launch {
        healthRepo.deleteActivity(id)
    }
}