# BookFinder 📚🔍

**BookFinder** is a Java-based application designed to explore and manage an impressive collection of over 70,000 free books from Project Gutenberg. Powered by the powerful **Gutendex API**, this application allows you to search for books by title, view author details, and manage a local database with book and author information.

---

## 🚀 Key Features

- **Search for books by title**: Easily find books from Project Gutenberg by searching for specific titles.
- **Manage book records**: Store and view details like title, author(s), language(s), and download statistics.
- **Browse authors**: Get a list of registered authors, including their birth and death years.
- **Search authors by living year**: Discover which authors were alive in a specific year.
- **Filter books by language**: Filter books by language and see how many titles are available in that language.

---

## 🛠️ Technologies Used

This project uses modern and robust technologies to ensure efficient performance and a great experience:

- **Java**: Main programming language for the backend.
- **Spring Framework**: Core structure to manage the application's business logic.
- **PostgreSQL**: Database to store book and author information.
- **Gutendex API**: A free API to explore Project Gutenberg's extensive collection.

---

## ⚡ Getting Started

### Prerequisites

Before running the project, make sure you have the following installed:

1. **Java 8 or higher**: Ensure you have Java correctly installed.
2. **Spring Boot**: Set up your Spring Boot environment.
3. **PostgreSQL**: Install PostgreSQL and create a database named `DB_Libros`.
4. **Gutendex API**: Check the Gutendex API documentation for more details on the available endpoints.

### Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/EmersonJPJ/BookFinder.git
    ```

2. Set up the PostgreSQL database:
    ```sql
    CREATE DATABASE "DB_Libros" WITH OWNER = postgres ENCODING = 'UTF8';
    ```

3. Update your **`application.properties`** with the correct database connection details:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/DB_Libros
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    ```

4. Run the application:
    ```bash
    mvn spring-boot:run
    ```

### Usage

Once the application is running, interact with it via the console. Here's an overview of the menu options:

1. **Search for a book by title**: Input the title of the book, and the application will fetch the details from the Gutendex API and store them in the database.
2. **List all registered books**: View all books currently stored in your local database.
3. **List all registered authors**: Get a list of authors along with their books.
4. **List authors alive in a specific year**: Input a year to see which authors were alive during that time.
5. **Filter books by language**: Search for books available in a specific language and view how many are registered in the database.

---

### Example

Here's what the output might look like when searching for a book:
```bash
Enter the book title you want to search for: Don Quixote

Book details found:
Title: Don Quixote
Author(s): Miguel de Cervantes
Language(s): Spanish
Downloads: 5000
This book has been stored in the database.
```

---

## 🤝 Contributing

Contributions are always welcome! If you have suggestions, improvements, or bug fixes, feel free to open an issue or create a pull request. 

## ✨ Contact  

Feel free to reach out for any questions or suggestions!  

- 📧 **Email**: emerson04vade@gmail.com  
- 💻 **GitHub**: EmersonJPJ

