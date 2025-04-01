import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectopoli.mock.ProfileMockDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _profileState = MutableStateFlow<UserProfile?>(null)
    val profileState: StateFlow<UserProfile?> = _profileState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadMockProfile()
    }

    private fun loadMockProfile() {
        _isLoading.value = true
        _profileState.value = ProfileMockDataSource.getMockProfile()
        _isLoading.value = false
    }
}