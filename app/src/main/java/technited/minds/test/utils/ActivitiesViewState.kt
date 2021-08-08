package technited.minds.test.utils

import technited.minds.test.models.Activities

sealed class ActivitiesViewState {
    object Empty : ActivitiesViewState()
    object Loading : ActivitiesViewState()
    data class Success(val notes: List<Activities>) : ActivitiesViewState()
    data class Error(val exception: Throwable) : ActivitiesViewState()
}