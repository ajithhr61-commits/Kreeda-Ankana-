# Kreeda-Ankana - README

## 🏏 Digital Sports Ground & Match Organizer for Villages

Kreeda-Ankana is a comprehensive Android application built with modern Android development best practices.

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose + Material 3
- **Architecture**: MVVM with Repository Pattern
- **DI**: Hilt (Dagger)
- **Database**: Room (offline) + Firebase Firestore/Realtime DB (cloud)
- **Auth**: Firebase Authentication
- **Messaging**: Firebase Cloud Messaging (FCM)
- **AI**: Google Gemini API
- **Navigation**: Navigation Compose
- **Image Loading**: Coil

## 📁 Project Structure

```
app/src/main/java/com/kreeda/ankana/
├── KreedaAnkanaApp.kt          # Application class
├── MainActivity.kt              # Main entry point
├── ai/                          # Gemini AI integration
│   └── GeminiService.kt
├── data/
│   ├── model/                   # Data models
│   │   ├── User.kt, Ground.kt, Booking.kt
│   │   ├── Challenge.kt, Team.kt
│   │   ├── MatchResult.kt, Notification.kt
│   └── repository/              # Repository pattern
│       ├── AuthRepository.kt
│       ├── BookingRepository.kt
│       ├── ChallengeRepository.kt
│       ├── GroundRepository.kt
│       ├── TeamRepository.kt
│       └── MatchRepository.kt
├── di/                          # Hilt DI modules
│   ├── AppModule.kt
│   ├── DatabaseModule.kt
│   └── FirebaseModule.kt
├── firebase/                    # Firebase services
│   ├── FirebaseAuthService.kt
│   ├── FirestoreService.kt
│   ├── RealtimeDbService.kt
│   └── KreedaMessagingService.kt
├── navigation/                  # Navigation
│   ├── Screen.kt
│   ├── NavGraph.kt
│   └── BottomNavBar.kt
├── roomdb/                      # Room Database
│   ├── KreedaDatabase.kt
│   ├── dao/
│   │   ├── TeamDao.kt, BookingDao.kt, MatchDao.kt
│   └── entity/
│       ├── TeamEntity.kt, BookingEntity.kt, MatchEntity.kt
├── ui/
│   ├── theme/                   # Material 3 Theme
│   ├── components/              # Reusable UI components
│   ├── splash/                  # Splash Screen
│   ├── auth/                    # Login & Register
│   ├── dashboard/               # Home Dashboard
│   ├── booking/                 # Ground Booking
│   ├── calendar/                # Calendar View
│   ├── challenge/               # Challenge Board
│   ├── team/                    # Team Management
│   ├── score/                   # Score Wall
│   ├── notifications/           # Notifications
│   ├── settings/                # Settings
│   ├── admin/                   # Admin Panel
│   └── ai/                      # AI Features UI
└── utils/                       # Utilities
    ├── Constants.kt
    ├── DateUtils.kt
    └── Extensions.kt
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug or later
- JDK 17
- Firebase project

### Setup
1. Open this project in Android Studio
2. Create a Firebase project at https://console.firebase.google.com
3. Add your Android app to Firebase (package: `com.kreeda.ankana`)
4. Download `google-services.json` and place it in `app/`
5. Enable Authentication (Email/Password, Google)
6. Create Firestore Database
7. Enable Realtime Database
8. Set up Cloud Messaging
9. (Optional) Get a Gemini API key and update `GeminiService.kt`
10. Build and run!

### Firebase Configuration Required
⚠️ You MUST add your `google-services.json` file to the `app/` directory before building.

## 📱 Screens
- Splash Screen (animated)
- Login / Register
- Dashboard (with quick actions)
- Ground Listing & Booking
- Calendar View
- Challenge Board (real-time)
- Team Management
- Score Wall
- Notifications
- Settings
- Admin Panel
- AI Features (Gemini)

## 🔒 Security
- Firebase Firestore rules in `firebase/firestore.rules`
- Realtime Database rules in `firebase/database.rules.json`

## 📄 License
MIT License
