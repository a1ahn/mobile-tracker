//
//  Request.swift
//  MobileTracker
//
//  Created by Seungyeon Lee on 26/01/2019.
//  Copyright © 2019 Seungyeon Lee. All rights reserved.
//

import Foundation

class Requests {
    let jsonrpc = "2.0"
    let provider = "https://ctz.solidwallet.io/api/v3"
    let id: Int
    //= Int(arc4random_uniform(9999))
    
    let method: API.METHOD
    let params: [String: Any]?
    
    init(method: API.METHOD, params: [String: Any]?) {
        self.id = Int(arc4random_uniform(9999))
        self.method = method
        self.params = params
    }
    


}
