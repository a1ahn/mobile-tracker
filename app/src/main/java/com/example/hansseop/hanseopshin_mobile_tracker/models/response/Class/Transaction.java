package com.example.hansseop.hanseopshin_mobile_tracker.models.response.Class;

import java.io.Serializable;

public class Transaction implements Serializable {
//    Data data;
    String stepLimit;
    String signature;
    String dataType;
    String nid;
    String from;
    String to;
    String version;
    String nonce;
    String timestamp;
    String txHash;
    String txIndex;
    String blockHeight;
    String blockHash;

//    public Data getData() {
//        return data;
//    }
//
//    public void setData(Data data) {
//        this.data = data;
//    }

    public String getStepLimit() {
        return stepLimit;
    }

    public void setStepLimit(String stepLimit) {
        this.stepLimit = stepLimit;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
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

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public Transaction(Data data, String stepLimit, String signature, String dataType, String nid, String from, String to, String version, String nonce, String timestamp, String txHash) {

//        this.data = data;
        this.stepLimit = stepLimit;
        this.signature = signature;
        this.dataType = dataType;
        this.nid = nid;
        this.from = from;
        this.to = to;
        this.version = version;
        this.nonce = nonce;
        this.timestamp = timestamp;
        this.txHash = txHash;
    }
}
