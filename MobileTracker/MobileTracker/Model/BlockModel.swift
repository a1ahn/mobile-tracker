//
//  BlockModel.swift
//  MobileTracker
//
//  Created by Byunsangjin on 29/03/2019.
//  Copyright © 2019 Byunsangjin. All rights reserved.
//

import Foundation

struct BlockModel: Codable {
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
        
        enum CodingKeys: String, CodingKey {
            case version, height, signature
            case prevBlockHash = "prev_block_hash"
            case merkleTreeRootHash = "merkle_tree_root_hash"
            case timeStamp = "time_stamp"
            case confirmedTransactionList = "confirmed_transaction_list"
            case blockHash = "block_hash"
            case peerId = "peer_id"
        }
    }
    
    var jsonrpc: String
    var id: Int
    var result: resultInfo
}
