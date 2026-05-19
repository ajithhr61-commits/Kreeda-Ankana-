package com.kreeda.ankana.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kreeda.ankana.data.model.*
import com.kreeda.ankana.data.repository.KreedaRepository
import com.kreeda.ankana.utils.SampleData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * DashboardViewModel — Manages dashboard state including upcoming matches,
 * today's bookings, challenge previews, and quick stats.
 */
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: KreedaRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val currentUser: StateFlow<User?> = repository.getCurrentUser()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val upcomingBookings: StateFlow<List<Booking>> = repository.getAllBookings()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val openChallenges: StateFlow<List<Challenge>> = repository.getOpenChallenges()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val latestResults: StateFlow<List<MatchResult>> = repository.getAllResults()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val unreadNotifications: StateFlow<Int> = repository.getUnreadCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    init {
        loadSampleData()
    }

    private fun loadSampleData() {
        viewModelScope.launch {
            try {
                // Seed data if DB is empty
                repository.saveUser(SampleData.currentUser)
                repository.saveGrounds(SampleData.grounds)
                repository.saveTeams(SampleData.teams)
                repository.saveBookings(SampleData.bookings)
                repository.saveChallenges(SampleData.challenges)
                repository.saveResults(SampleData.matchResults)
                repository.saveNotifications(SampleData.notifications)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
