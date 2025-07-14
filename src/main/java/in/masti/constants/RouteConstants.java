package in.masti.constants;

/**
 * Route constants for centralized route management
 */
public final class RouteConstants {
    
    // Private constructor to prevent instantiation
    private RouteConstants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    // Employee routes
    public static final String EMPLOYEE_BASE = "/employees";
    public static final String EMPLOYEE_CREATE = "/create";
} 