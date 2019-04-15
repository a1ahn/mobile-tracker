package me.myds.g2u.mobiletracker.IconRPC;

public class rpcRequestException extends Exception {
    public int code;
    public String message;

    public rpcRequestException(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.code + ": " + this.message;
    }
}
