package com.example.hansseop.hanseopshin_mobile_tracker.models.response.Class;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {
    String version;
    String prev_block_hash;
    String merkle_tree_root_hash;
    Long time_stamp;
    List<Transaction> confirmed_transaction_list;
    String block_hash;
    int height;
    String peer_id;
    String signature;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPrev_block_hash() {
        return prev_block_hash;
    }

    public void setPrev_block_hash(String prev_block_hash) {
        this.prev_block_hash = prev_block_hash;
    }

    public String getMerkle_tree_root_hash() {
        return merkle_tree_root_hash;
    }

    public void setMerkle_tree_root_hash(String merkle_tree_root_hash) {
        this.merkle_tree_root_hash = merkle_tree_root_hash;
    }

    public Long getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(Long time_stamp) {
        this.time_stamp = time_stamp;
    }

    public List<Transaction> getConfirmed_transaction_list() {
        return confirmed_transaction_list;
    }

    public void setConfirmed_transaction_list(List<Transaction> confirmed_transaction_list) {
        this.confirmed_transaction_list = confirmed_transaction_list;
    }

    public String getBlock_hash() {
        return block_hash;
    }

    public void setBlock_hash(String block_hash) {
        this.block_hash = block_hash;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getPeer_id() {
        return peer_id;
    }

    public void setPeer_id(String peer_id) {
        this.peer_id = peer_id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Result(String version, String prev_block_hash, String merkle_tree_root_hash, Long time_stamp, List<Transaction> confirmed_transaction_list, String block_hash, int height, String peer_id, String signature) {

        this.version = version;
        this.prev_block_hash = prev_block_hash;
        this.merkle_tree_root_hash = merkle_tree_root_hash;
        this.time_stamp = time_stamp;
        this.confirmed_transaction_list = confirmed_transaction_list;
        this.block_hash = block_hash;
        this.height = height;
        this.peer_id = peer_id;
        this.signature = signature;
    }
}
