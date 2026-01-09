🏨 Hotel Reservation System

A Spring Boot–based Hotel Reservation System designed to manage rooms, clients, and reservations with secure authentication, clean architecture, and a professional user interface.

🚀 Features

Client, Room, and Reservation management (CRUD operations)

Secure JWT-based authentication and role-based authorization

Search functionality for clients and reservations

PDF invoice generation for reservations

Global exception handling with custom error pages

Server-side validation and logging

Responsive UI using Thymeleaf

🛠️ Tech Stack

Backend: Java, Spring Boot, Spring MVC

Security: Spring Security, JWT

ORM: Hibernate, JPA

Database: MySQL

Frontend: Thymeleaf, HTML, CSS

Build Tool: Maven

Version Control: Git

🏗️ Project Architecture

Controller Layer – Handles HTTP requests and REST APIs

Service Layer – Business logic implementation

Repository Layer – Database interaction using JPA

Security Layer – JWT authentication and authorization

Exception Handling – Centralized using @ControllerAdvice

📂 Modules

Client Module – Manage client details

Room Module – Manage room availability and details

Reservation Module – Create and manage reservations
