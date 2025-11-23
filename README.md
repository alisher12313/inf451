# 🚀 INF451 — Two Microservices Backend

<div align="center">

### Attendance Tracking + Assignment Submission System

[![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![H2](https://img.shields.io/badge/H2-0000BB?style=for-the-badge&logo=h2&logoColor=white)](https://www.h2database.com/)
[![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)](https://www.mongodb.com/)

*Микросервисная архитектура для управления учебным процессом*

</div>

---

## 📖 О проекте

Данный проект состоит из **двух независимых микросервисов**, реализующих систему учебного контроля:
- 📊 **Посещаемость занятий**
- 📝 **Управление заданиями со сдачей файлов**

Проект разработан как часть курса **INF451** и демонстрирует навыки работы с:
- Микросервисной архитектурой
- Spring Data JPA
- DTO-моделями и маппингом
- Валидацией данных
- Загрузкой и хранением файлов
- Централизованной обработкой исключений

---

## 📁 Структура проекта

```plaintext
📦 INF451-Microservices
 ┣ 📂 attendance-service
 ┃ ┣ 📂 controller          # REST контроллеры
 ┃ ┣ 📂 dto                 # Data Transfer Objects
 ┃ ┣ 📂 entity              # JPA сущности
 ┃ ┣ 📂 repository          # Spring Data репозитории
 ┃ ┣ 📂 service             # Бизнес-логика
 ┃ ┗ 📂 resources           # application.properties, data.sql
 ┃
 ┣ 📂 assignment-service
 ┃ ┣ 📂 controller          # REST контроллеры
 ┃ ┣ 📂 dto                 # Data Transfer Objects
 ┃ ┣ 📂 entity              # JPA сущности
 ┃ ┣ 📂 mapper              # Entity ↔ DTO маппинг
 ┃ ┣ 📂 repository          # Spring Data репозитории
 ┃ ┣ 📂 service             # Бизнес-логика
 ┃ ┗ 📂 resources           # MongoDB GridFS конфигурация
 ┃
 ┗ 📄 README.md
```

---

## 🎯 Микросервис 1: Attendance Service

<div align="center">

### 📊 Управление посещаемостью

</div>

### ✨ Функциональность

| Возможность | Описание |
|-------------|----------|
| 📚 **Управление курсами** | Создание и получение информации о курсах |
| 🗓️ **Управление сессиями** | Работа с занятиями (сессиями курса) |
| ✅ **Автогенерация записей** | Автоматическое создание записей посещаемости |
| 👤 **Отметка присутствия** | Фиксация присутствия/отсутствия студентов |
| ⏰ **Временные ограничения** | Нельзя отмечать занятия старше 7 дней |

### 🛠️ Технологический стек

```
☕ Java 17+
🍃 Spring Boot
🌐 Spring Web
💾 Spring Data JPA
🗄️ H2 Database (In-Memory)
🔧 Lombok
📝 Slf4j Logging
⚠️ GlobalExceptionHandler
```

### 🔌 REST API Endpoints

#### 1️⃣ Получить информацию о курсе

```http
GET /attendance/getCourse/{id}
```

**Параметры:**
- `id` (Path) — ID курса

**Ответ:** `200 OK`
```json
{
  "id": "uuid",
  "name": "INF451",
  "description": "Backend Development"
}
```

---

#### 2️⃣ Получить посещаемость по сессии

```http
GET /attendance/session/{sessionId}?studentIds=id1,id2,id3
```

**Параметры:**
- `sessionId` (Path) — ID сессии
- `studentIds` (Query) — Список ID студентов (через запятую)

**Ответ:** `200 OK`
```json
[
  {
    "studentId": "uuid",
    "sessionId": "uuid",
    "status": "PRESENT",
    "timestamp": "2025-01-15T10:30:00"
  }
]
```

---

#### 3️⃣ Отметить студента

```http
POST /attendance
Content-Type: application/json
```

**Тело запроса:**
```json
{
  "studentId": "550e8400-e29b-41d4-a716-446655440000",
  "sessionId": "660e8400-e29b-41d4-a716-446655440001",
  "status": "PRESENT"
}
```

**Статусы:**
- `PRESENT` — Присутствует
- `ABSENT` — Отсутствует
- `LATE` — Опоздал

**Ответ:** `201 Created`

---

## 📝 Микросервис 2: Assignment & Submission Service

<div align="center">

### 📦 Управление заданиями и файлами

</div>

### ✨ Функциональность

| Возможность | Описание |
|-------------|----------|
| 📋 **Создание заданий** | Преподаватель создает задание с дедлайном |
| 👥 **Привязка к студентам** | Назначение задания группе студентов |
| 📤 **Отправка решений** | Студенты загружают файлы с решениями |
| 💾 **Хранение в GridFS** | Файлы хранятся в MongoDB GridFS |
| ⏱️ **Определение статуса** | Автоматическая маркировка: вовремя/поздно |
| 🔄 **Обновление статуса** | Динамическое обновление состояния заданий |

### 🛠️ Технологический стек

```
☕ Java 17+
🍃 Spring Boot
🌐 Spring Web
💾 Spring Data JPA
🍃 Spring Data MongoDB
📁 MongoDB GridFS
📤 Multipart File Upload
🔧 Lombok
⚠️ GlobalExceptionHandler
```

### 🔌 REST API Endpoints — Assignments

#### 1️⃣ Создать задание

```http
POST /uploadAssignment/create?teacherId={uuid}
Content-Type: application/json
```

**Тело запроса (AssignmentRequestDto):**
```json
{
  "title": "Homework 1: Microservices",
  "description": "Implement two microservices",
  "dueDate": "2025-02-01T23:59:59",
  "studentIds": [
    "550e8400-e29b-41d4-a716-446655440000",
    "660e8400-e29b-41d4-a716-446655440001"
  ]
}
```

**Ответ:** `201 Created`

---

#### 2️⃣ Получить все задания

```http
GET /uploadAssignment/getAll
```

**Ответ:** `200 OK`
```json
[
  {
    "id": "uuid",
    "title": "Homework 1",
    "description": "...",
    "dueDate": "2025-02-01T23:59:59",
    "status": "ACTIVE"
  }
]
```

---

#### 3️⃣ Получить задания преподавателя

```http
GET /uploadAssignment/getByTeacher?teacherId={uuid}
```

**Ответ:** `200 OK` — список заданий преподавателя

---

### 🔌 REST API Endpoints — Submissions

#### 1️⃣ Отправить файл (решение)

```http
POST /submit/assignment
Content-Type: multipart/form-data
```

**Form-Data параметры:**
```
studentId: 550e8400-e29b-41d4-a716-446655440000
assignmentId: 660e8400-e29b-41d4-a716-446655440001
file: solution.pdf (MultipartFile)
```

**Ответ:** `201 Created`
```json
{
  "submissionId": "uuid",
  "status": "ON_TIME",
  "submittedAt": "2025-01-20T14:30:00"
}
```

---

#### 2️⃣ Скачать файл

```http
GET /submit/{submissionId}/file
```

**Ответ:** `200 OK`
- Content-Type: `application/octet-stream`
- Файл в бинарном формате

---

#### 3️⃣ Получить все отправки по заданию

```http
GET /submit/assignment/{assignmentId}
```

**Ответ:** `200 OK`
```json
[
  {
    "submissionId": "uuid",
    "studentId": "uuid",
    "status": "ON_TIME",
    "submittedAt": "2025-01-20T14:30:00"
  },
  {
    "submissionId": "uuid",
    "studentId": "uuid",
    "status": "LATE",
    "submittedAt": "2025-02-02T10:00:00"
  }
]
```

---

## 🛡️ Технические детали

### 🔒 Валидация посещаемости

Сервис **Attendance Service** применяет следующие правила:

| ❌ Запрещено | Причина |
|--------------|---------|
| Отмечать будущие занятия | Нельзя отметить то, что еще не произошло |
| Отмечать занятия старше 7 дней | Срок давности — максимум 7 дней |

### 🔒 Валидация отправки заданий

Сервис **Assignment Service** применяет следующие правила:

| ❌ Запрещено | Действие |
|--------------|----------|
| Повторная отправка | Студент может отправить задание только один раз |
| Отправка после дедлайна | Файл принимается, но помечается как `LATE` |

### ⚠️ Централизованная обработка ошибок

Оба микросервиса используют **GlobalExceptionHandler** для унифицированной обработки ошибок:

| Тип исключения | HTTP статус | Описание |
|----------------|-------------|----------|
| `IllegalArgumentException` | 400 Bad Request | Некорректные данные запроса |
| `FileNotFoundException` | 404 Not Found | Файл или ресурс не найден |
| `DuplicateSubmissionException` | 409 Conflict | Попытка повторной отправки |
| Прочие ошибки | 500 Internal Server Error | Внутренняя ошибка сервера |

**Формат ответа об ошибке:**
```json
{
  "timestamp": "2025-01-20T15:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Session is older than 7 days",
  "path": "/attendance"
}
```

---

## 🧪 Тестирование

### 🎯 Attendance Service

1. **Запустите сервис**
   ```bash
   cd attendance-service
   ./mvnw spring-boot:run
   ```

2. **Проверьте работу**
   ```bash
   # Получить курс
   curl http://localhost:8080/attendance/getCourse/1
   
   # Получить посещаемость
   curl "http://localhost:8080/attendance/session/1?studentIds=uuid1,uuid2"
   
   # Отметить студента
   curl -X POST http://localhost:8080/attendance \
     -H "Content-Type: application/json" \
     -d '{"studentId":"uuid","sessionId":"uuid","status":"PRESENT"}'
   ```

### 📝 Assignment Service

1. **Запустите MongoDB**
   ```bash
   docker run -d -p 27017:27017 --name mongodb mongo:latest
   ```

2. **Запустите сервис**
   ```bash
   cd assignment-service
   ./mvnw spring-boot:run
   ```

3. **Проверьте работу**
   ```bash
   # Создать задание
   curl -X POST "http://localhost:8081/uploadAssignment/create?teacherId=uuid" \
     -H "Content-Type: application/json" \
     -d '{"title":"HW1","description":"...","dueDate":"2025-02-01T23:59:59","studentIds":[]}'
   
   # Отправить файл
   curl -X POST http://localhost:8081/submit/assignment \
     -F "studentId=uuid" \
     -F "assignmentId=uuid" \
     -F "file=@solution.pdf"
   
   # Скачать файл
   curl http://localhost:8081/submit/{submissionId}/file -o downloaded.pdf
   ```

---

## 🚀 Запуск проекта

### Предварительные требования

```bash
☕ Java 17 или выше
🗄️ MongoDB (для assignment-service)
📦 Maven 3.6+
```

### Быстрый старт

```bash
# Клонировать репозиторий
git clone https://github.com/your-username/inf451-microservices.git
cd inf451-microservices

# Запустить Attendance Service
cd attendance-service
./mvnw spring-boot:run

# В новом терминале: запустить MongoDB
docker run -d -p 27017:27017 mongo:latest

# В новом терминале: запустить Assignment Service
cd assignment-service
./mvnw spring-boot:run
```

**Сервисы будут доступны:**
- 📊 Attendance Service: `http://localhost:8080`
- 📝 Assignment Service: `http://localhost:8081`

---

## 🎯 Roadmap (Будущие улучшения)

- [ ] 🐳 Докеризация обоих микросервисов
- [ ] 🗄️ Замена H2 на PostgreSQL для production
- [ ] 📚 Интеграция Swagger/OpenAPI документации
- [ ] 🌐 API Gateway для единой точки входа
- [ ] 🔐 JWT аутентификация и авторизация
- [ ] ⚡ Redis для кэширования
- [ ] 📊 Grafana + Prometheus мониторинг
- [ ] 🎨 Frontend панель (React/Vue.js)
- [ ] 🧪 Unit и Integration тесты (JUnit 5, Testcontainers)
- [ ] 📨 Уведомления по email/Telegram

---

## 👤 Автор

<div align="center">

**INF451 — Backend Microservices Project**

Разработано с ❤️ **Abden Alisher**

[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/alisher12313)

</div>

---

## 📄 Лицензия

Этот проект создан в **образовательных целях** в рамках курса **INF451**.

---

<div align="center">

**⭐ Если проект был полезен, поставьте звезду! ⭐**

Made with ☕ and 🍃 Spring Boot

</div>