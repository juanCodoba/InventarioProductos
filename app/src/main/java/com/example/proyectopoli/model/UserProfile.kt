// domain/model/UserProfile.kt
data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val avatarUrl: String,
    val preferences: UserPreferences,
    val purchaseHistory: List<Purchase>
)

data class UserPreferences(
    val watchType: String,
    val brand: String
)

data class Purchase(
    val id: String,
    val detProductUrl :String,
    val productName: String,
    val description: String,
    val date: String
)