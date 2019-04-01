//
//  API.swift
//  MobileTracker
//
//  Created by Byunsangjin on 29/03/2019.
//  Copyright © 2019 Byunsangjin. All rights reserved.
//

import Foundation

class API {
    public static let decoder: JSONDecoder = {
        let decoder = JSONDecoder()
        return decoder
    }()
    
    enum METHOD: String {
        case getLastBlock = "icx_getLastBlock" // Returns the last block information.
        case getBlockByHeight = "icx_getBlockByHeight" // Returns block information by block height.
    }
}
