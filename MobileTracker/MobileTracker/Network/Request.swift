//
//  Request.swift
//  MobileTracker
//
//  Created by Byunsangjin on 29/03/2019.
//  Copyright © 2019 Byunsangjin. All rights reserved.
//

import Foundation

class Request {
    let jsonrpc = "2.0"
    let path = "https://ctz.solidwallet.io/api/v3"
    let id: Int = 1234
    
    let method: API.METHOD
    let params: [String: Any]?
    
    init(method: API.METHOD, params: [String: Any]?) {
        self.method = method
        self.params = params
    }
}
