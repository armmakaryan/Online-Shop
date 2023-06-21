package am.smartCode.jdbc.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String userNotFound) {
        super(userNotFound);
    }

}
