package kcl.group.api.backendservice.exception;

public class FileException extends Exception {

    public FileException(String exception){
        super(exception);
    }

    public FileException(String exception, Throwable throwable) {
        super(exception, throwable);
    }
}
