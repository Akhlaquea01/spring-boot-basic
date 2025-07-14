package in.masti.advice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Error Response Class
 * 
 * This class represents a standardized error response structure for the API.
 * It provides consistent error information across all endpoints.
 * 
 * Key Features:
 * - Timestamp for when the error occurred
 * - HTTP status code
 * - Error message and details
 * - Optional field-specific validation errors
 * - Consistent JSON structure
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    
    /**
     * Timestamp when the error occurred
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    
    /**
     * HTTP status code
     */
    private int status;
    
    /**
     * Error message
     */
    private String message;
    
    /**
     * Detailed error description
     */
    private String details;
    
    /**
     * Request path that caused the error
     */
    private String path;
    
    /**
     * List of field-specific validation errors
     */
    private List<FieldError> fieldErrors;

    /**
     * Default constructor
     */
    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructor with basic error information
     * 
     * @param status HTTP status code
     * @param message Error message
     * @param details Detailed error description
     * @param path Request path
     */
    public ErrorResponse(int status, String message, String details, String path) {
        this();
        this.status = status;
        this.message = message;
        this.details = details;
        this.path = path;
    }

    /**
     * Constructor with validation errors
     * 
     * @param status HTTP status code
     * @param message Error message
     * @param details Detailed error description
     * @param path Request path
     * @param fieldErrors List of field-specific validation errors
     */
    public ErrorResponse(int status, String message, String details, String path, List<FieldError> fieldErrors) {
        this(status, message, details, path);
        this.fieldErrors = fieldErrors;
    }

    // Getter and Setter methods
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    /**
     * Field Error Class
     * 
     * Represents a field-specific validation error
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldError {
        private String field;
        private String message;
        private Object rejectedValue;

        public FieldError() {
        }

        public FieldError(String field, String message, Object rejectedValue) {
            this.field = field;
            this.message = message;
            this.rejectedValue = rejectedValue;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getRejectedValue() {
            return rejectedValue;
        }

        public void setRejectedValue(Object rejectedValue) {
            this.rejectedValue = rejectedValue;
        }
    }

    /**
     * Override toString method for better debugging
     */
    @Override
    public String toString() {
        return "ErrorResponse{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", details='" + details + '\'' +
                ", path='" + path + '\'' +
                ", fieldErrors=" + fieldErrors +
                '}';
    }
}
