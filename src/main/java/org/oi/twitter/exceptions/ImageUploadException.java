package org.oi.twitter.exceptions;

public class ImageUploadException extends RuntimeException {

    public ImageUploadException(String message) {
        super(message);
    }
    
    public ImageUploadException(String message, Throwable e) {
        super(message, e);
    }
    
}
