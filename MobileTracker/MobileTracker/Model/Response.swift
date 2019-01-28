//
//  Response.swift
//  MobileTracker
//
//  Created by Seungyeon Lee on 23/01/2019.
//  Copyright © 2019 Seungyeon Lee. All rights reserved.
//

import Foundation

struct BlockStruct: Codable {
    struct paramsInfo: Codable {
        var roomNo: String?
        var targetDate: String?
        var targetTime: String?
    }
    struct dataInfo: Codable {
        var method: String?
        var params: paramsInfo?
    }
    struct TransactionInfo: Codable {
        var from: String
        var nid: String
        var signature: String
        var stepLimit: String
        var timestamp: String
        var to: String
        var txHash: String
        var value: String?
        var version: String
        // canceled
        var nonce: String?
        var data: dataInfo?
        var dataType: String?
    }
    
    struct resultInfo: Codable {
        var version: String
        var prevBlockHash: String
        var merkleTreeRootHash: String
        var timeStamp: Double
        var confirmedTransactionList: [TransactionInfo]
        var blockHash: String
        var height: Int
        var peerId: String
        var signature: String
    }

    var jsonrpc: String
    var id: Int
    var result: resultInfo
}
