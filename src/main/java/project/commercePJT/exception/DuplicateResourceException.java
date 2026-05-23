package project.commercePJT.exception;

import lombok.Getter;

@Getter
public class DuplicateResourceException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Long id;

    public DuplicateResourceException(ErrorCode errorCode, Long id) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.id = id;
    }
}
