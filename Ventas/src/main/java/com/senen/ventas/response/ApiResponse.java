/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senen.ventas.response;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;

/**
 *
 * @author senen
 * @param <T>
 */
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private LocalDateTime timestamp;
    private HttpStatus status;
    private T data;
    
    public ApiResponse(){
        
    }
    
    public ApiResponse(boolean success, String message, HttpStatus status, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = ZonedDateTime.now(ZoneId.of("America/Mazatlan")).toLocalDateTime();
        this.status = status;
    }
    
    //En caso de que se borré o que el objeto esté vacío o null
    public ApiResponse(boolean success, String message, HttpStatus status){
        this.success = success;
        this.message = message;
        this.timestamp = ZonedDateTime.now(ZoneId.of("America/Mazatlan")).toLocalDateTime();
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        timestamp = ZonedDateTime.now(ZoneId.of("America/Mazatlan")).toLocalDateTime();
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
}