/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senen.articulos.exception;

import com.senen.articulos.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author senen
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handle(Exception ex) {

        String message = ex.getMessage();

        if (message.contains("ERROR:")) {
            message = message.substring(message.indexOf("ERROR:") + 6)
                         .split("\n")[0]
                         .trim();
        }

        return ResponseEntity.badRequest().body(
            new ApiResponse<>(false, message, HttpStatus.BAD_REQUEST, null)
        );
    }
}
