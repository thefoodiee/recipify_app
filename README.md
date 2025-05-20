# Recipify

A modern Android recipe application built with **Jetpack Compose**, leveraging **Dagger Hilt** for dependency injection, **Firebase** for backend services, and the **Spoonacular API** for accessing a vast library of recipes. This app allows users to browse and search their favorite recipes in a beautiful and responsive UI.

---

## ✨ Features

- 🔍 **Search Recipes** – Find meals by ingredients, name, or category using the Spoonacular API  
- 📝 **Detailed Recipe View** – View instructions, ingredients and preparation time 
- 🔥 **Modern UI** – Clean and responsive interface using Jetpack Compose  
- 🔐 **User Authentication** – Firebase Auth integration for login/signup  

---

## 🛠️ Tech Stack

| Layer        | Technology              |
|--------------|-------------------------|
| UI           | Jetpack Compose         |
| DI           | Dagger Hilt             |
| Backend      | Firebase (Auth, Firestore) |
| Data Source  | Spoonacular API         |
| Architecture | MVVM                    |
| Language     | Kotlin                  |

---

## 📸 Screenshots

![Screens_1](https://github.com/thefoodiee/recipify_app/blob/main/screenshots/screens_1.png?raw=true)
![Screens_2](https://github.com/thefoodiee/recipify_app/blob/main/screenshots/screens_2.png?raw=true)

---
## 🔧 Setup & Installation
⚠️ This app requires JDK 11 or above. I used JDK 17 on KDE Neon (Ubuntu LTS)

0. **Get JDK 11 or newer (17 recommended) and configure Android Studio to use it.**

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/recipe-app.git
   cd recipe-app
2. **Set up Firebase:**

- Create a Firebase project.
- Enable Firebase Authentication (Email/Password Sign-In).
- Add your google-services.json to the `app/` directory.

3. **Get Spoonacular API Key**
- Get Spoonacular API Key from [here](https://spoonacular.com/food-api) and add it to `app/keys.properties`
4. **Build & Run**
- Open the project in Android Studio.

- Sync Gradle, build, and run the app on your emulator or physical device. 



