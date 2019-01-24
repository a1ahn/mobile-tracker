package com.example.showblocks.model;

import org.json.JSONArray;

public class Result {
    private String version;
    private String prevBlockHash;
    private String merkleTreeRootHash;
    private long timeStamp;
    private JSONArray confirmedTransactionList;
    private String blockHash;
    private int height;
    private String peerId;
    private String signature;

    public Result(String version, String prevBlockHash, String merkleTreeRootHash, long timeStamp, JSONArray confirmedTransactionList, String blockHash, int height, String peerId, String signature) {
        this.version = version;
        this.prevBlockHash = prevBlockHash;
        this.merkleTreeRootHash = merkleTreeRootHash;
        this.timeStamp = timeStamp;
        this.confirmedTransactionList = confirmedTransactionList;
        this.blockHash = blockHash;
        this.height = height;
        this.peerId = peerId;
        this.signature = signature;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPrevBlockHash() {
        return prevBlockHash;
    }

    public void setPrevBlockHash(String prevBlockHash) {
        this.prevBlockHash = prevBlockHash;
    }

    public String getMerkleTreeRootHash() {
        return merkleTreeRootHash;
    }

    public void setMerkleTreeRootHash(String merkleTreeRootHash) {
        this.merkleTreeRootHash = merkleTreeRootHash;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public JSONArray getConfirmedTransactionList() {
        return confirmedTransactionList;
    }

    public void setConfirmedTransactionList(JSONArray confirmedTransactionList) {
        this.confirmedTransactionList = confirmedTransactionList;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getPeerId() {
        return peerId;
    }

    public void setPeerId(String peerId) {
        this.peerId = peerId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "Result{" +
                "version='" + version + '\'' +
                ", prevBlockHash='" + prevBlockHash + '\'' +
                ", merkleTreeRootHash='" + merkleTreeRootHash + '\'' +
                ", timeStamp=" + timeStamp +
                ", confirmedTransactionList=" + confirmedTransactionList +
                ", blockHash='" + blockHash + '\'' +
                ", height=" + height +
                ", peerId='" + peerId + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
