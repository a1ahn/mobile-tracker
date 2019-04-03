//
//  Array+remove.swift
//  MobileTracker
//
//  Created by Byunsangjin on 03/04/2019.
//  Copyright © 2019 Byunsangjin. All rights reserved.
//

import Foundation

extension Array {
    mutating func removeAtBlock(at: BlockModel) {
        let blockList = self as! [BlockModel]
        var count = 0
        
        for block  in blockList {
            if block.result.blockHash == at.result.blockHash {
                self.remove(at: count)
            }
            
            count += 1
        }
    }
}
