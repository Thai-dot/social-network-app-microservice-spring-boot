package com.thaidot.profile.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1009, "User not found", HttpStatus.NOT_FOUND), ALREADY_ACCEPTED(1010, "Friend request is already " +
            "accepted", HttpStatus.BAD_REQUEST), REQUEST_NOT_FOUND(1011, "Friend request not found",
            HttpStatus.NOT_FOUND), ALREADY_FRIENDS(1012, "Already friend", HttpStatus.BAD_REQUEST),
    FRIENDSHIP_NOT_EXISTED(1013, "Friendship not existed", HttpStatus.BAD_REQUEST), CANNOT_FOLLOW_YOURSELF(1014, "Cannot follow/unfollow yourself", HttpStatus.BAD_REQUEST),
    ALREADY_FOLLOWING(1015, "Already Following User", HttpStatus.BAD_REQUEST),
    ALREADY_UNFOLLOWING(1016, "Already Unfollowing User", HttpStatus.BAD_REQUEST), FRIEND_REQUEST_EXISTED(1017, "Friend request existed", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
