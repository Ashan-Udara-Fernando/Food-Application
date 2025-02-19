# 🍔 Grab Go - Food Delivery App

A fast and efficient food delivery app built with Kotlin and Firebase, featuring real-time updates, an interactive map, and an intuitive user interface.

## 🚀 Features

🔹 User Side:

* Seamless Login & Registration: Secure authentication for users.
* Browse & Order Food: View a variety of food items, check ratings, and add items to the cart.
* Live Order Tracking: Integrated map to track order locations in real-time.
* Smooth Navigation: Easily switch between categories using a navigation bar.

🔹 Admin Side:

* Manage Food Items: Add, update, and delete food items from the database.
* Update Map Locations: Modify delivery locations through the admin panel.
* Real-Time Data Sync: Firebase updates all order details, user data, and food items instantly.

## 📸 Screenshots

<div align="center"> 
<img src="https://github.com/user-attachments/assets/d0dd3d14-e227-4996-8a0d-606b40a4934f" width="170" alt="Intro Page"> 
<img src="https://github.com/user-attachments/assets/1d7cf637-d8fe-40cb-8d07-611684adf684" width="170" alt="Login Page">
<img src="https://github.com/user-attachments/assets/5c6d1c22-a0b7-46e5-a631-1f6dd7c05469" width="170" alt="Register Page">  
<img src="https://github.com/user-attachments/assets/e28fd812-5ee9-4fd9-9b66-aa59ec41399a" width="170" alt="Main Menu">
<img src="https://github.com/user-attachments/assets/6e06ebce-ee41-4727-83e9-d2ae96a4a559" width="170" alt="Detailed Menu">
<img src="https://github.com/user-attachments/assets/9a98bd6c-6bdc-4fee-9977-19247bdb30ad" width="170" alt="Cart Menu">
<img src="https://github.com/user-attachments/assets/44b1cb17-d72e-4d4b-ae5e-7106198a97d4" width="170" alt="Food List"> 
<img src="https://github.com/user-attachments/assets/8fc4ee0c-927f-4fe4-b3f2-5291c6be612e" width="170" alt="Map Integration"> <br> </div>

## 🛠 Getting Started

🔹  Install Android Studio
🔹 Clone the Repository
🔹 Setup Firebase Realtime Database
* Create a Firebase-ProjectVisit Firebase Console and create a new project.
* Enable Firebase Realtime Database: In your Firebase console, go to Realtime Database > Create Database > Select "Start in test mode" > Click Enable.
* Import Sample Data: Download database_firebase.json.

4.Configure Firebase in Your App
* Register Your App in Firebase: 
        1.Go to Project Settings > Your Apps > Click                   Add App (Android).
        2.Enter your package name (e.g., com.yourname.grabgo).
* Download & Add google-services.json
        Download the google-services.json file and place it inside the /app directory.
* Add Your SHA-1 Key
        Copy the SHA-1 key and add it to Firebase Console under Android App Settings

5.Add Dependencies
6.Final Step
* Run the app on an Android device or emulator.
