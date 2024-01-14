package sbankauthms.exception;

import sbankauthms.exception.base.InvalidStateException;

import java.io.Serial;

public class InvalidUsernameOrPasswordException extends InvalidStateException {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "INVALID_CIF_OR_PASSWORD";

    public InvalidUsernameOrPasswordException() {
        super(MESSAGE);
    }

}
