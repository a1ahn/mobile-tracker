//
//  BlockModelByHash.swift
//  MobileTracker
//
//  Created by Byunsangjin on 02/04/2019.
//  Copyright © 2019 Byunsangjin. All rights reserved.
//

import Foundation

struct BlockModelByTxHash: Codable {
    struct ParamsInfo: Codable {
        var date: String?
        var time: String?
        var div : String?
        var value: String?
        
        enum CodingKeys: String, CodingKey {
            case date = "_date"
            case time = "_time"
            case div = "_div"
            case value = "_value"
        }
    }
    
    struct DataInfo: Codable {
        var method: String?
        var params: ParamsInfo?
    }
    
    struct ResultInfo: Codable {
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
        var data: DataInfo?
        var dataType: String?
    }
    
    var id: Int
    var jsonrpc: String
    var result: ResultInfo
}
