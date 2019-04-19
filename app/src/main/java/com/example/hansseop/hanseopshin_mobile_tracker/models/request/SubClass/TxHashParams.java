package com.example.hansseop.hanseopshin_mobile_tracker.models.request.SubClass;

import java.io.Serializable;

public class TxHashParams implements Serializable {
    String txHash;

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public TxHashParams(String txHash) {

        this.txHash = txHash;
    }
}
