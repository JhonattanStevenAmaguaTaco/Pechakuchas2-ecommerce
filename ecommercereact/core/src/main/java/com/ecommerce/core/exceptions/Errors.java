package com.ecommerce.core.exceptions;

public class Errors {
  // API errors
  public static final String API_REFUSED_CONNECTION = "API refused the connection.";
  public static final String API_RESPONSE_ERROR = "API responded with an unexpected status code: ";
  public static final String API_DATA_NOT_FOUND = "No data found from the API.";
  public static final String API_INVALID_RESPONSE = "Invalid response received from the API.";
  
  // Content Fragment errors
  public static final String CF_NOT_CREATED = "Could not create the Content Fragment.";
  public static final String CF_CREATION_FAILED = "Failed to create content fragment for: ";
  public static final String CF_TEMPLATE_NOT_FOUND = "FragmentTemplate not found: ";
  public static final String CF_PARENT_RESOURCE_NOT_FOUND = "Parent resource for Content Fragment not found: ";
  public static final String CF_ADAPTATION_FAILED = "Failed to adapt the resource to FragmentTemplate.";
  
  // Resource errors
  public static final String RESOURCE_NOT_FOUND = "Resource not found: ";
  public static final String RESOURCE_ADAPTATION_FAILED = "Failed to adapt resource: ";
  public static final String RESOURCE_COMMIT_FAILED = "Failed to commit the resource resolver changes.";
  public static final String RESOURCE_REVERT_FAILED = "Failed to revert resource resolver changes.";

  // Path errors
  public static final String INVALID_ROUTE = "Invalid route: ";
  public static final String UNAUTHORIZED_ACCESS = "Unauthorized access attempt detected.";
}
