package com.example.demo.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class CustomException extends WebApplicationException {
    public CustomException(String message, int status) {
        super (Response.status (status).entity (message).build ());
    }
}
