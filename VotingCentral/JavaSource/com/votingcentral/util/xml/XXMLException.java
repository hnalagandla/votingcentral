package com.votingcentral.util.xml;

public class XXMLException extends Exception {

    public static int INVALID_PATH = 1;
    public static int NON_EXISTING_NODE = 2;
    public static int AMBIGUOUS_PATH = 3;
    public static int EMPTY_PATH = 4;
    public static int INVALID_OPERATION = 5;

    private int code;

    public XXMLException(int code, String message, Throwable t) {
        super(message, t);
        this.code = code;
    }

    public XXMLException(String message) {
        super(message);
    }

    public XXMLException(String message, Exception nested) {
        super(message, nested);
    }

    public String toString() {
        return super.toString();
    }

    public int getCode() {
        return code;
    }
}
