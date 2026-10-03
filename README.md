# 📚 JavaFX Student & Data Record Manager

A desktop application built with **JavaFX** and **Object-Oriented Programming (OOP)** principles. The system provides an interactive graphical user interface (GUI) to manage student records, custom data entries, calculate statistical averages, display dynamic animal trivia, and persist data using object serialization and text file exports.

---

## 🚀 Features

*   **Student Record Management:** Add, delete, and view student records containing names, last names, and ages.
*   **Custom Data Tracking:** Manage separate custom data sets with dedicated input forms and list views.
*   **Data Persistence:** Automatically saves and loads records locally using Java Object Serialization (`.dat` files) and exports structured summaries to plain text files.
*   **Statistical Operations:** Calculates and displays the average age of all registered students dynamically via dialog messages.
*   **Modular Architecture:** 
    *   Utilizes **Abstract Classes** (`BaseUserModal`) for dynamic user input handling.
    *   Implements **Interfaces** (`AnimalTriviaUtility`) for utility features like random animal information generation.
*   **Interactive JavaFX GUI:** Clean grid and box layouts built with list views, event handlers, and multi-stage windows.

---

## 📂 Project Structure

```text
├── application/
│   ├── Main.java              # JavaFX entry point, GUI layout, file input/output handlers
│   ├── StudentManager.java    # Subclass extending the abstract user modal
│   ├── BaseUserModal.java     # Abstract class managing user identification input
│   └── AnimalTriviaUtility.java # Interface providing random animal trivia generation
└── README.md                  # Project documentation
