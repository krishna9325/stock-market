# 📈 Stock Market Breakout App

<p align="center">
  <!-- Replace with actual image paths -->
  <img src="screenshots/stock.jpg" alt="Home Screen" width="250"/>
</p>

---

## 🚀 Overview

A full-featured **Stock Market Mobile Application** that shows real-time stock breakouts (intraday, swing, indices, etc.).  
Users receive **push notifications** whenever new breakout information is published, and they can view interactive breakout charts directly inside the app.  

The app also provides a **progress tracking section**, where users can:  
- Add their own entries  
- Upload images  
- Track profit/loss  

> 🔒 Note: The main admin application used to push breakout information is **internal-only** and not exposed to end users.

---

## ✨ Features

- 🔑 **User Authentication**  
- 📊 **Real-time breakout charts** (intraday, swing, indices)  
- 🔔 **Push notifications** (via Firebase FCM)  
- 📈 **Progress tracking** (add/update/modify entries)  
- 🖼️ **Upload and view images** with entries  
- 💹 **Profit/Loss tracking**  
- ⚡ **Offline-first architecture** (Room Persistence Library)  

---

## 🏗️ Tech Stack

- **Frontend (Mobile App):** Android (Java/Kotlin)  
- **Architecture:** MVVM  
- **Database:** Room Persistence Library  
- **Backend/Storage:** Firebase Datastore  
- **Notifications:** Firebase Cloud Messaging (FCM)  

---

## 📱 Application Flow

1. Admin adds new breakout details via a private app (not public).  
2. Information is pushed to Firebase.  
3. Users receive a **real-time notification**.  
4. Users can view **breakout charts** and update their **progress tracker**.  
