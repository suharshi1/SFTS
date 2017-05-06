package com.SFTS.Token;

public interface Token {

    public void setMessage(String message);

    public String getMessage();

    public void setCode(int code);

    public int getCode();

    public static final int SUCCESS = 200;
    public static final int RECORD_CREATED = 201;
    public static final int RECORD_ACCEPTED = 202;
    public static final int PARTIAL_SUCCESS = 203;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int NOT_FOUND = 404;
    public static final int NOT_ALLOWED = 405;
    public static final int CONFLICT = 406;
    public static final int GONE = 410;
    public static final int PRECONDITION_FAILED = 412;
    public static final int INTE_SERVER_ERROR = 500;

}
