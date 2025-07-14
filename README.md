# Spring Boot Basic - Employee Management System

A comprehensive Spring Boot application demonstrating best practices for building RESTful APIs with proper exception handling, validation, and documentation.

## 🚀 Features

### Core Features
- **CRUD Operations**: Complete Create, Read, Update, Delete operations for Employee entities
- **RESTful API Design**: Follows REST conventions with proper HTTP methods and status codes
- **Input Validation**: Comprehensive validation using Bean Validation annotations
- **Exception Handling**: Global exception handling with standardized error responses
- **Database Integration**: JPA/Hibernate with H2 database for development
- **Logging**: Comprehensive logging for debugging and monitoring

### Advanced Features
- **Search & Filter**: Multiple search and filtering options
- **Pagination Support**: Ready for pagination implementation
- **Audit Fields**: Automatic timestamp tracking
- **Health Checks**: Application health monitoring
- **API Documentation**: Comprehensive endpoint documentation

## 🏗️ Architecture

The application follows a layered architecture pattern:

```
┌─────────────────┐
│   Controller    │  ← REST API endpoints
├─────────────────┤
│    Service      │  ← Business logic
├─────────────────┤
│   Repository    │  ← Data access
├─────────────────┤
│   Entity        │  ← Data model
└─────────────────┘
```

### Package Structure
```
src/main/java/in/masti/
├── SpringBootBasicApplication.java    # Main application class
├── controller/
│   └── EmployeeController.java        # REST API endpoints
├── service/
│   ├── EmployeeService.java           # Service interface
│   └── EmployeeServiceImpl.java       # Service implementation
├── repository/
│   └── EmployeeRepository.java        # Data access layer
├── entity/
│   └── Employee.java                  # JPA entity
├── constants/
│   └── RouteConstants.java            # Route constants
└── advice/
    ├── ExceptionAdvice.java           # Global exception handler
    └── exception/
        └── ErrorResponse.java         # Error response model
```

## 🛠️ Technology Stack

- **Java**: 17+
- **Spring Boot**: 3.x
- **Spring Data JPA**: Data access layer
- **Hibernate**: ORM framework
- **H2 Database**: In-memory database
- **Bean Validation**: Input validation
- **SLF4J + Logback**: Logging framework
- **Maven**: Build tool

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd spring-boot-basic
```

### 2. Build the Project
```bash
mvn clean install
```

### 3. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080/api`

### 4. Access H2 Database Console
- URL: `http://localhost:8080/api/h2-console`
- JDBC URL: `jdbc:h2:file:E:/h2database`
- Username: `test`
- Password: `test`

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api/employees
```

### Endpoints

#### 1. Create Employee
```http
POST /employees
Content-Type: application/json

{
  "name": "John Doe",
  "designation": "Developer",
  "dob": "1990-01-01",
  "company": "Tech Corp"
}
```

#### 2. Get All Employees
```http
GET /employees
```

#### 3. Get Employee by ID
```http
GET /employees/{id}
```

#### 4. Update Employee (PUT)
```http
PUT /employees/{id}
Content-Type: application/json

{
  "name": "John Doe Updated",
  "designation": "Senior Developer",
  "dob": "1990-01-01",
  "company": "Tech Corp"
}
```

#### 5. Partially Update Employee (PATCH)
```http
PATCH /employees/{id}
Content-Type: application/json

{
  "designation": "Senior Developer"
}
```

#### 6. Delete Employee
```http
DELETE /employees/{id}
```

#### 7. Get Employees by Designation
```http
GET /employees/designation/{designation}
```

#### 8. Get Employees by Company
```http
GET /employees/company/{company}
```

#### 9. Search Employees by Name
```http
GET /employees/search?name={name}
```

#### 10. Get Employees Born After Date
```http
GET /employees/born-after?date=1990-01-01
```

#### 11. Filter Employees by Designation and Company
```http
GET /employees/filter?designation=Developer&company=Tech Corp
```

#### 12. Count Employees by Designation
```http
GET /employees/count/designation/{designation}
```

#### 13. Check if Employee Exists by Name
```http
GET /employees/exists?name={name}
```

#### 14. Health Check
```http
GET /employees/health
```

## 🔧 Configuration

### Application Properties
Key configuration options in `application.properties`:

```properties
# Server Configuration
server.port=8080
server.servlet.context-path=/api

# Database Configuration
spring.datasource.url=jdbc:h2:file:E:/h2database
spring.datasource.username=test
spring.datasource.password=test

# JPA Configuration
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update

# Logging Configuration
logging.level.in.masti=DEBUG
```

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Manual Testing with curl

#### Create Employee
```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "designation": "Developer",
    "dob": "1990-01-01",
    "company": "Tech Corp"
  }'
```

#### Get All Employees
```bash
curl -X GET http://localhost:8080/api/employees
```

#### Get Employee by ID
```bash
curl -X GET http://localhost:8080/api/employees/101
```

## 🚨 Exception Handling

The application provides comprehensive exception handling with standardized error responses:

### Error Response Format
```json
{
  "timestamp": "2024-01-01 12:00:00",
  "status": 400,
  "message": "Validation failed",
  "details": "One or more fields have validation errors",
  "path": "/api/employees",
  "fieldErrors": [
    {
      "field": "name",
      "message": "Employee name is required",
      "rejectedValue": null
    }
  ]
}
```

### Common HTTP Status Codes
- `200 OK`: Successful operation
- `201 Created`: Resource created successfully
- `204 No Content`: Resource deleted successfully
- `400 Bad Request`: Invalid input or validation error
- `404 Not Found`: Resource not found
- `409 Conflict`: Data integrity violation
- `500 Internal Server Error`: Unexpected server error

## 📝 Validation Rules

### Employee Entity Validation
- **name**: Required, 2-20 characters, letters/spaces/hyphens only
- **designation**: Optional, max 50 characters
- **dob**: Optional, must be in the past
- **company**: Optional, max 100 characters

## 🔍 Logging

The application uses SLF4J with Logback for logging:

- **DEBUG**: Application-specific debug information
- **INFO**: General application flow
- **WARN**: Warning conditions
- **ERROR**: Error conditions

### Log Configuration
```properties
logging.level.in.masti=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

## 🏗️ Best Practices Implemented

### 1. **Layered Architecture**
- Clear separation of concerns
- Dependency injection
- Interface-based design

### 2. **RESTful Design**
- Proper HTTP methods
- Consistent URL patterns
- Appropriate status codes

### 3. **Exception Handling**
- Global exception handler
- Standardized error responses
- Proper logging

### 4. **Validation**
- Bean Validation annotations
- Custom validation logic
- Field-specific error messages

### 5. **Logging**
- Comprehensive logging
- Different log levels
- Structured log messages

### 6. **Configuration**
- Externalized configuration
- Profile-based settings
- Environment-specific properties

## 🚀 Deployment

### Development
```bash
mvn spring-boot:run
```

### Production
```bash
mvn clean package
java -jar target/spring-boot-basic-0.0.1-SNAPSHOT.jar
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License.

## 🆘 Support

For support and questions:
- Create an issue in the repository
- Check the documentation
- Review the code comments

## 🔄 Future Enhancements

- [ ] Add pagination support
- [ ] Implement caching
- [ ] Add authentication and authorization
- [ ] Create unit and integration tests
- [ ] Add API documentation with Swagger/OpenAPI
- [ ] Implement rate limiting
- [ ] Add database migrations
- [ ] Create Docker containerization 