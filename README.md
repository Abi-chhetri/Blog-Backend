# TCG's Blog — Backend

A Spring Boot REST API for an AI-powered blogging platform. The backend handles user authentication, blog CRUD, AI-generated content, and automated email notifications, backed by MongoDB.

## Features

- **Authentication & Security**
  - JWT-based stateless authentication (Spring Security + `jjwt`)
  - BCrypt password hashing
  - OTP email verification during sign-up
  - Role-based access control (`ADMIN` / `USER`) on protected routes

- **Blog Management**
  - Create, update, delete, and fetch blogs scoped to the authenticated user
  - Public endpoint to fetch all blogs
  - Genre tagging across 40+ topics (AI, Cloud Computing, Cybersecurity, Web Development, etc.)

- **AI-Generated Content**
  - Scheduled jobs call the OpenRouter API (Mistral 7B Instruct) to auto-generate new blog posts across topic clusters, twice daily
  - Generated posts are parsed from the model's JSON response and saved directly to MongoDB
  - A separate scheduled job automatically removes AI-generated posts older than 30 days, keeping the content fresh

- **User Management**
  - Update username/password, choose genre preferences, subscribe/unsubscribe to news updates
  - Profile picture upload (multipart file upload, stored locally under `/uploads`)

- **Notifications**
  - Welcome email on sign-up
  - OTP verification email
  - Subscribe/unsubscribe confirmation emails
  - Daily digest email to subscribed users (scheduled)

- **Learning Zone**
  - A separate content module with its own role-based access rules

- **Application Cache**
  - In-memory cache of key-value config (e.g. external API endpoints) loaded from MongoDB on startup and refreshed every 10 minutes

- **Health Check**
  - Simple endpoint to confirm the service is running

## Tech Stack

| Component        | Technology                          |
|-------------------|--------------------------------------|
| Language          | Java 17                              |
| Framework         | Spring Boot 3.5.5                    |
| Security          | Spring Security, JWT (`jjwt`)        |
| Database          | MongoDB (Spring Data MongoDB)        |
| Email             | Spring Boot Starter Mail (SMTP/Gmail)|
| AI Integration    | OpenRouter API (Mistral 7B Instruct) |
| Build Tool        | Maven                                |
| Utilities         | Lombok                               |

## Project Structure

```
Blog-Backend/
├─ src/main/java/TheChhetriGroup/Blog/
│  ├─ controller/         # REST controllers (Blog, User, SignUp, LearningZone, HealthCheck)
│  ├─ services/           # Business logic (Blog, User, Email, AI content fetch)
│  ├─ repository/         # Spring Data MongoDB repositories
│  ├─ entity/             # MongoDB document models (Blog, User, LearningZone, ApplicationCache)
│  ├─ pojoForAiBlog/      # Response models for the OpenRouter API
│  ├─ schedulers/         # Scheduled jobs (AI content fetch, cleanup, cache refresh, notifications)
│  ├─ applicationCache/   # In-memory config cache
│  ├─ configuration/      # Security, REST template, and web configuration
│  ├─ jwtFilter/          # JWT request filter
│  ├─ Utils/              # JWT utility class
│  └─ BlogApplication.java
├─ uploads/               # Uploaded profile pictures
└─ pom.xml
```

## API Overview

| Endpoint                          | Description                                  |
|------------------------------------|-----------------------------------------------|
| `POST /sign-up`                    | Register a new user, sends a welcome email    |
| `POST /sign-up/login`              | Authenticate and receive a JWT                |
| `POST /sign-up/otp`                | Generate and email an OTP                     |
| `GET /health-check`                | Service health check                          |
| `GET /blogs` / `GET /blogs/all`    | Get the current user's blogs / all blogs      |
| `POST /blogs`                      | Create a new blog post                        |
| `PUT /blogs/id/{id}`               | Update a blog post                            |
| `DELETE /blogs/id/{id}`            | Delete a blog post                            |
| `GET /users/getUserDetails`        | Get the authenticated user's profile          |
| `PUT /users/updatePassword`        | Change password                               |
| `PUT /users/updateUserName`        | Change username                               |
| `POST /users/upload`               | Upload a profile picture                      |
| `PUT /users/newsUpdate`            | Subscribe/unsubscribe from email updates      |
| `GET /Learning-Zone`               | Get Learning Zone content (role-restricted)   |

## Getting Started

### Prerequisites
- Java 17
- Maven
- A running MongoDB instance
- An OpenRouter API key
- An SMTP-capable email account (e.g. Gmail with an app password)

### Environment Variables
The application reads the following from the environment:

| Variable      | Purpose                              |
|----------------|----------------------------------------|
| `MONGO_URI`    | MongoDB connection string              |
| `EMAIL`        | SMTP username (sender email address)   |
| `PASSWORD`     | SMTP password / app password           |
| `SECRET_KEY`   | Secret key used to sign JWTs           |
| `API_KEY`      | OpenRouter API key                     |

### Run locally

```bash
git clone https://github.com/Abi-chhetri/Blog-Backend.git
cd Blog-Backend
./mvnw spring-boot:run
```

## License

Free to use.
