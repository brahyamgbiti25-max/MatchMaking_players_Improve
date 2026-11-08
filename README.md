![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)

# 🎮 MatchMaking Backend Service

A robust, secure Spring Boot backend application designed to handle player registration, email account verification, password resets, and an automated skill-based matchmaking queue system using robust database relations and concurrency handling.

---

## 🏗️ Architecture & Core Features

* **JWT-Based Stateless Security:** Custom security filter chain using `OncePerRequestFilter` to secure endpoints, extract roles, and validate tokens without keeping server-side sessions.
* **Skill-Based Matchmaking Queue:** Dynamically pairs users based on a skill rating window (+/- 150 points) and queue timing order.
* **Concurrency Control:** Utilizes Pessimistic Locking (`@Lock(LockModeType.PESSIMISTIC_WRITE)`) at the database level to prevent race conditions when multiple players search for a match simultaneously.
* **Email Verification & Account Management:** Integrated `JavaMailSender` to dispatch secure token-based verification links for signup confirmation and password recovery.
* **Database Management:** Uses Hibernate ORM with auto schema updates and connection pooling for MySQL.

---

## 📋 Prerequisites

Before running this project, ensure you have the following installed on your local machine:
* **[Git](https://git-scm.com/)**: To clone the repository.
* **[Docker Desktop](https://www.docker.com/products/docker-desktop/)**: To containerize and run the application and database stacks seamlessly.
* *(Note: You do NOT need to install Java or MySQL natively if running through Docker).*

---

## ⚙️ Environment Configuration

Create a `.env` file in your root project directory to securely pass your credentials without exposing them in your codebase:

```env
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=your_db_password
JWT_SECRET=YourSuperSecretJwtKeyHere
SPRING_MAIL_USERNAME=your-email@gmail.com
SPRING_MAIL_PASSWORD=your-gmail-app-password
APP_FRONTEND_URL=http://localhost:4200
```

---

## 🌐 API Endpoint Reference

### Authentication & Account Management (Public & Secured)
| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/bra/auth/signup` | Registers a new user and sends an email verification token | Public |
| `GET` | `/api/bra/auth/validate-email` | Checks if an email is available or already exists | Public |
| `GET` | `/api/bra/auth/verify-email` | Verifies the user account using a token string | Public |
| `POST` | `/api/bra/auth/login` | Authenticates user and returns a signed JWT token | Public |
| `POST` | `/api/bra/auth/resend-verification` | Resends a new verification token to the user's email | Public |
| `GET` | `/api/bra/auth/forgot-password` | Generates and emails a password reset token | Public |
| `POST` | `/api/bra/auth/reset-password` | Updates user password using the reset token | Public |
| `POST` | `/api/bra/auth/changed-password` | Changes password for an actively logged-in user | 🔒 Secured |
| `GET` | `/api/bra/auth/current-user` | Returns profile details of the currently authenticated user | 🔒 Secured |

### Matchmaking System (Secured)
*Note: All matchmaking endpoints require a valid JWT passed via the `Authorization: Bearer <token>` header.*

| Method | Endpoint | Description | Access |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/matchmaking/find` | Enters the user into the queue or pairs them with a skill-matched opponent | 🔒 Secured |
| `POST` | `/api/matchmaking/cancel` | Cancels an active search or match ticket | 🔒 Secured |

---

## 🛠️ Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 3
* **Security:** Spring Security, JSON Web Tokens (jjwt)
* **Database:** MySQL 8.0, Spring Data JPA / Hibernate
* **Utilities:** Lombok, Jakarta Validation, JavaMailSender
