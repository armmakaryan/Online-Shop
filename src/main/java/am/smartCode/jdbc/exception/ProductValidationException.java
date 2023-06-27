package am.smartCode.jdbc.exception;

public class ProductValidationException extends  RuntimeException{
    public ProductValidationException(String message) {
        super(message);
    }
}