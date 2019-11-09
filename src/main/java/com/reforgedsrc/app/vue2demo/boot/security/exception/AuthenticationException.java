
package com.reforgedsrc.app.vue2demo.boot.security.exception;

public class AuthenticationException extends RuntimeException {

    private static final long serialVersionUID = -3893540221285034773L;

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}