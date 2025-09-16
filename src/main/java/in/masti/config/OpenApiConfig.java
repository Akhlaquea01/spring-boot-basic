package in.masti.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI Configuration
 * 
 * This class configures the OpenAPI 3 specification for the Employee Management API.
 * It provides metadata about the API, including title, description, version, and contact information.
 * 
 * Key Features:
 * - API information and metadata
 * - Server configuration
 * - Contact and license information
 * - Customizable API documentation
 * 
 * The generated documentation will be available at:
 * - Swagger UI: http://localhost:8080/swagger-ui.html
 * - OpenAPI JSON: http://localhost:8080/v3/api-docs
 * - OpenAPI YAML: http://localhost:8080/v3/api-docs.yaml
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configures the OpenAPI specification for the Employee Management API
     * 
     * @return OpenAPI object with API metadata and configuration
     */
    @Bean
    public OpenAPI employeeManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Employee Management API")
                        .description("""
                                A comprehensive REST API for managing employee data with full CRUD operations.
                                
                                ## Features
                                - Create, read, update, and delete employees
                                - Search and filter employees by various criteria
                                - Input validation and error handling
                                - Comprehensive logging and monitoring
                                
                                ## Employee Data Model
                                - **ID**: Auto-generated unique identifier
                                - **Name**: Employee full name (2-20 characters, letters only)
                                - **Designation**: Job title/role (max 50 characters)
                                - **Date of Birth**: Birth date (must be in the past)
                                - **Company**: Company name (max 100 characters)
                                
                                ## API Endpoints
                                - `POST /api/employees` - Create a new employee
                                - `GET /api/employees` - Get all employees
                                - `GET /api/employees/{id}` - Get employee by ID
                                - `PUT /api/employees/{id}` - Update employee completely
                                - `PATCH /api/employees/{id}` - Partially update employee
                                - `DELETE /api/employees/{id}` - Delete employee
                                - `GET /api/employees/designation/{designation}` - Get employees by designation
                                - `GET /api/employees/company/{company}` - Get employees by company
                                - `GET /api/employees/search?name={name}` - Search employees by name
                                - `GET /api/employees/born-after?date={date}` - Get employees born after date
                                - `GET /api/employees/filter?designation={designation}&company={company}` - Filter by both
                                - `GET /api/employees/count/designation/{designation}` - Count by designation
                                - `GET /api/employees/exists?name={name}` - Check if employee exists
                                - `GET /api/employees/health` - Health check endpoint
                                
                                ## Response Codes
                                - `200 OK` - Successful operation
                                - `201 Created` - Employee created successfully
                                - `204 No Content` - Employee deleted successfully
                                - `400 Bad Request` - Invalid input data
                                - `404 Not Found` - Employee not found
                                - `500 Internal Server Error` - Server error
                                
                                ## Validation Rules
                                - Name: Required, 2-20 characters, letters/spaces/hyphens only
                                - Designation: Optional, max 50 characters
                                - Date of Birth: Optional, must be in the past
                                - Company: Optional, max 100 characters
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Employee Management Team")
                                .email("support@employeemanagement.com")
                                .url("https://www.employeemanagement.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development Server"),
                        new Server()
                                .url("https://api.employeemanagement.com")
                                .description("Production Server")
                ));
    }
}
