# ✈️ Travel Itinerary Planner

<p align="center">
  <a href="LICENSE">
    <img src="https://img.shields.io/badge/License-MIT-green.svg" alt="License: MIT" />
  </a>
</p>

<div align="center">

  <a href="https://www.docker.com/">
    <img src="https://img.shields.io/badge/Dockerized-Yes-blue" alt="Dockerized" />
  </a>
  <a href="https://spring.io/projects/spring-boot">
    <img src="https://img.shields.io/badge/Backend-SpringBoot-6db33f?logo=spring" alt="Backend" />
  </a>
  <a href="https://reactjs.org/">
    <img src="https://img.shields.io/badge/Frontend-React-61DAFB?logo=react" alt="Frontend" />
  </a>
  <a href="https://www.postgresql.org/">
    <img src="https://img.shields.io/badge/Database-PostgreSQL-336791?logo=postgresql" alt="Database" />
  </a>
  <a href="https://docs.docker.com/compose/">
    <img src="https://img.shields.io/badge/Built%20with-Docker%20Compose-2496ED?logo=docker" alt="Built With Docker Compose" />
  </a>

</div>


A full-stack travel planning application that helps users create, organize, and manage trip itineraries. It supports trip scheduling, accommodation booking, activity planning, and integrates with travel APIs for real-time flight and hotel data.

---

## 📌 Features

- 🗓️ Create and manage multi-day itineraries  
- 🧭 Plan activities per destination 
- 🧩 Modular frontend/backend with Docker support  
- 📄 Swagger API documentation  
- 🛠️ Automatic database migrations via Flyway  

---

## 🛠️ Tech Stack

### Frontend
- React (with Vite) + TypeScript  
- Environment-based builds with `.env.dev` and `.env.prod`  
- Hot Reloading in development mode  

### Backend
- Java Spring Boot  
- Maven build system  
- PostgreSQL database  
- Flyway for automatic database migrations  
- Swagger/OpenAPI for API docs  
- Lombok for boilerplate reduction  

### DevOps
- Docker & Docker Compose  
- `.env` file-based secrets and configuration  
- Multi-stage Dockerfiles  
- Separate `dev` and `prod` Compose environments  

---

## 🚀 Getting Started

### 1. Clone the Repo

```bash
git clone https://github.com/YD-S/travel-itinerary-planner.git
cd travel-itinerary-planner
```

### 2. Create and Configure Environment Files

```bash
touch .env
```

Edit .env as needed:

env example:
```env
# === FRONTEND ===
VITE_API_URL=http://test:8080

# === BACKEND ===
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/travel_db
SPRING_DATASOURCE_USERNAME=test_user
SPRING_DATASOURCE_PASSWORD=test_pass

# === DATABASE ===
POSTGRES_DB=test_db
POSTGRES_USER=test_user
POSTGRES_PASSWORD=test_pass

# === JWT ===
JWT_SECRET_KEY=your_secret_key
JWT_EXPIRATION_TIME=3600 # in seconds
```

### 3. Create DB directory

```bash
mkdir db
```

## 🧪 Run in Development

```bash
docker compose -f docker-compose.dev.yml up --build
```

* Frontend: ``http://localhost:5173``
* Backend: ``http://localhost:8080``
* Database: ``localhost:5432``

Hot reload is enabled for both frontend and backend.

## 🚢 Run in Production

```bash
docker compose -f docker-compose.prod.yml up --build -d
```

* Only the frontend is exposed publicly (e.g., on port 80)
* Backend and DB run in an internal Docker network
* Hot reload and dev ports are disabled

In ``.env.prod``, ``VITE_API_URL`` must be ``http://backend:8080`` so the frontend container can communicate with the backend container inside the Docker network.

## 📘 API Documentation

Once the backend is running:
```bash
http://localhost:8080/swagger-ui/index.html
```

## 🔁 Database Migrations

Flyway handles automatic schema migrations.

They are applied automatically on Spring Boot startup.

## 🐳 Docker Notes

* env_file: is used in Compose files to load environment variables
* Backend and DB services are only exposed in development
* In production, they communicate over a private Docker network
* The frontend VITE_API_URL is injected at build time via .env and baked into the compiled app

## 📄 License

This project is licensed under the MIT License.

## 🙋‍♂️ Authors

Made with ❤️ by [Yash](https://github.com/YD-S) and [Charlotte](https://github.com/char-projects)

