# 🐾 Pet Shop Management System

This is a **Pet Shop Management System** desktop application developed using **JavaFX**. It is designed to help manage pets, inventory, customers, and sales operations efficiently within a pet shop.

## 📌 Features

- 🐶 Add, edit, and delete pet details (e.g. name, type, price, description)
- 📦 Manage pet categories (Dogs, Cats, Birds, etc.)
- 🧾 Customer registration and management
- 💰 Billing and sales system
- 📈 View daily/monthly sales reports
- 🔐 User authentication (Admin login)

## 🛠️ Technologies Used

- **Java** (JDK 11+)
- **JavaFX**
- **MySQL** (Database)
- **Scene Builder** (For designing FXML interfaces)
- **JDBC** (Java Database Connectivity)

## 🗃️ Database Structure

- **Tables:**
  - `users` – for login credentials
  - `pets` – details of all pets
  - `categories` – pet types/categories
  - `customers` – registered customers
  - `sales` – records of transactions

> You’ll find the SQL file for creating the database in the `database/` folder.

## 🚀 How to Run

1. **Clone this repository:**
   ```bash
   git clone https://github.com/yourusername/pet-shop-management.git
   cd pet-shop-management
