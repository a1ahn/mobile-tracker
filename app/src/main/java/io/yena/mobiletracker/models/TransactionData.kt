package io.yena.mobiletracker.models

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

class TransactionData(
    var version: String,
    var from: String,
    var to: String,
    var value: String,
    var stepLimit: String,
    var timestamp: String,
    var nid: String,
    var nonce: String,
    var txHash: String,
    var txIndex: String,
    var blockHeight: String,
    var blockHash: String,
    var signature: String,
    var dataType: String,
    var data: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    constructor(): this("", "", "", "", "", "", "", "", "",
        "", "", "", "", "", "")

    fun parseTransaction(jsonString: String): TransactionData {
        val jsonObj = JSONObject(jsonString)
        val result = TransactionData()

        try {
            with(jsonObj) {
                result.version = getString("version")
                result.from = getString("from")
                result.to = getString("to")
                result.stepLimit = getString("stepLimit")
                result.timestamp = getString("timestamp")
                result.nid = getString("nid")
                result.nonce = getString("nonce")
                result.txHash = getString("txHash")
                result.signature = getString("signature")
                result.data = getString("data")
                if (this.has("dataType")) result.dataType = getString("dataType")
                if (this.has("value")) result.value = getString("value")
                if (this.has("txIndex")) result.txIndex = getString("txIndex")
                if (this.has("blockHeight")) result.blockHeight = getString("blockHeight")
                if (this.has("blockHash")) result.blockHash = getString("blockHash")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(version)
        parcel.writeString(from)
        parcel.writeString(to)
        parcel.writeString(value)
        parcel.writeString(stepLimit)
        parcel.writeString(timestamp)
        parcel.writeString(nid)
        parcel.writeString(nonce)
        parcel.writeString(txHash)
        parcel.writeString(txIndex)
        parcel.writeString(blockHeight)
        parcel.writeString(blockHash)
        parcel.writeString(signature)
        parcel.writeString(dataType)
        parcel.writeString(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TransactionData> {
        override fun createFromParcel(parcel: Parcel): TransactionData {
            return TransactionData(parcel)
        }

        override fun newArray(size: Int): Array<TransactionData?> {
            return arrayOfNulls(size)
        }
    }
}

