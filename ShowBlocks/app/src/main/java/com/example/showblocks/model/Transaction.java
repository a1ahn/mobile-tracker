package com.example.showblocks.model;

public class Transaction {
    private String from;
    private String to;
    private String version;
    private String nid;
    private String stepLimit;
    private String timestamp;
    private String nonce;
    private String dataType;
    private String data;
    private String signature;
    private String txHash;

    public Transaction(String from, String to, String version, String nid, String stepLimit, String timestamp, String nonce, String dataType, String data, String signature, String txHash) {
        this.from = from;
        this.to = to;
        this.version = version;
        this.nid = nid;
        this.stepLimit = stepLimit;
        this.timestamp = timestamp;
        this.nonce = nonce;
        this.dataType = dataType;
        this.data = data;
        this.signature = signature;
        this.txHash = txHash;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getStepLimit() {
        return stepLimit;
    }

    public void setStepLimit(String stepLimit) {
        this.stepLimit = stepLimit;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }
}
