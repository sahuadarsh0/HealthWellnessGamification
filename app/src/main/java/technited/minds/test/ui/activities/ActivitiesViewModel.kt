package technited.minds.test.ui.activities

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import technited.minds.test.models.Activities
import technited.minds.test.repository.HealthRepo
import technited.minds.test.utils.ActivitiesViewState
import javax.inject.Inject

class ActivitiesViewModel @Inject internal constructor(
    application: Application,
    private val healthRepo: HealthRepo,
) : AndroidViewModel(application) {

    // Backing property to avoid state updates from other classes
    private val _uiState = MutableStateFlow<ActivitiesViewState>(ActivitiesViewState.Loading)

    // The UI collects from this StateFlow to get its state updates
//    val uiState = _uiState.collect()

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


    init {
        viewModelScope.launch {
            healthRepo.getSavedActivities().distinctUntilChanged().collect { result ->
                if (result.isNullOrEmpty()){
                    _uiState.value = ActivitiesViewState.Empty
                } else {
                    _uiState.value = ActivitiesViewState.Success(result)
                }
            }
        }
    }
//    init {
//        viewModelScope.launch {
//            healthRepo.getSavedActivities().distinctUntilChanged().collect { result ->
//                if (result.isNullOrEmpty()) {
//                    _uiState.value = ActivitiesViewState.Empty
//                } else {
//                    _uiState.value = ActivitiesViewState.Success(result)
//                }
//            }
//        }
//    }

    // delete note by ID
    fun deleteActivityByID(id: Int) = viewModelScope.launch {
        healthRepo.deleteActivity(id)
    }
}