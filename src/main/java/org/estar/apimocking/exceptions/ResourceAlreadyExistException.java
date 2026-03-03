package org.estar.apimocking.exceptions;

public class ResourceAlreadyExistException extends RuntimeException{
    public ResourceAlreadyExistException(String msg)
    {
        super(msg);
    }
}
