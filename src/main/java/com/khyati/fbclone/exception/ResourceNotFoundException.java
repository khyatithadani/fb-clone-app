package com.khyati.fbclone.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
  private String resourceName;
  // private String fieldName;
  // private Object fieldValue;

  public ResourceNotFoundException(String resourceName) {
    super(String.format("not found with %s = ", resourceName));
    this.resourceName = resourceName;
    // this.fieldName = fieldName;
    // this.fieldValue = fieldValue;
  }

  public String getResourceName() {
    return resourceName;
  }

  // public String getFieldName() {
  // return fieldName;
  // }

  // public Object getFieldValue() {
  // return fieldValue;
  // }
}