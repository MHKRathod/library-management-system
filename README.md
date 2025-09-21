📝 Overview

This project is a Library Management System implemented in Java.
It demonstrates Object-Oriented Programming (OOP), SOLID principles, and design patterns.

The system allows librarians to manage books, patrons, and the lending process, with additional features like recommendations and search strategies.

🚀 Features
✅ Core Features

Book Management:
Add, remove, and update books.
Search books by Title, Author, or ISBN (Strategy Pattern).

Patron Management
Add and update patron info.
Track borrowing history.

Lending Process:
Checkout and return books.
Prevent double checkout.
Generate lending records with due/return dates.

Inventory Management
Track available and borrowed books.

🔧 Extensions
Recommendation System:
Suggests books based on popularity and patron history.

Reservation System (scaffolded):
Can be extended to notify patrons when reserved books are available.

Multi-branch Support (future-ready):
Library class can be extended for multiple branches.

🏗️ Design & Principles
OOP
Encapsulation → Classes like Book, Patron, LendingRecord hold their own data and behavior.
Abstraction → Services (Library, LendingService, RecommendationService) expose clear APIs.
Polymorphism → Search strategies (SearchByTitle, SearchByAuthor, SearchByISBN).
Inheritance/Composition → Strategy pattern via SearchStrategy interface.

SOLID
SRP → Each service (Library, LendingService, RecommendationService) has one responsibility.
OCP → New search strategies can be added without modifying existing code.
DIP → Services depend on abstractions (SearchStrategy).

Design Patterns
Strategy Pattern → For book searching (title/author/ISBN).
Observer-ready → Reservation/notification system can be plugged in later.

Collections & Utilities
Java Collections → List, Map, Set, Optional, Streams.
Logging → Java Logger in LendingService.

📊 Class Relationships

Here’s how the classes are connected:

Library
Manages all Book and Patron objects.
Uses a search strategy for flexible book search.

Patron
Holds personal info, borrowed history, and active checkouts.

Book
Stores ISBN, title, author, year, availability, and borrow count.

LendingRecord
Represents a checkout transaction with due/return dates.

LendingService
Handles checkout/return logic.
Updates Book, Patron, and creates LendingRecord.

RecommendationService

Suggests books based on popularity and user history.

Search Strategies
SearchByTitle, SearchByAuthor, SearchByISBN implement SearchStrategy.
