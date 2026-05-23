package project.commercePJT.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Long id;

    public ResourceNotFoundException(Long id, ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.id = id;
        this.errorCode = errorCode;
    }
}
