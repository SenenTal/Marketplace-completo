/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senen.articulos.exception;

/**
 *
 * @author senen
 */
public class DatabaseOperationException extends RuntimeException {

    public DatabaseOperationException(String message){
        super(message);
    }
    public DatabaseOperationException(String message,Throwable cause) {
        super(message, cause);
    }

}
