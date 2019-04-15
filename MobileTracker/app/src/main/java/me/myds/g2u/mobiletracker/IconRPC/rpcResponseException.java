package me.myds.g2u.mobiletracker.IconRPC;

public class rpcResponseException extends Exception {
    public int code;
    public String message;

    public rpcResponseException(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.code + ": " + this.message;
    }
}