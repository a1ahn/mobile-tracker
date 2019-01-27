//
//  API.swift
//  MobileTracker
//
//  Created by Seungyeon Lee on 25/01/2019.
//  Copyright © 2019 Seungyeon Lee. All rights reserved.
//
//
import Foundation

class API {
    public static let decoder: JSONDecoder = {
        let decoder = JSONDecoder()
        decoder.keyDecodingStrategy = .convertFromSnakeCase
        return decoder
    }()
    
}

extension API {
    public enum METHOD: String {
        case getLastBlock = "icx_getLastBlock"
        case getBlockByHeight = "icx_getBlockByHeight"
        case getBlockByHash = "icx_getBlockByHash"
    }
}
