//
//  BlockDBModel.swift
//  MobileTracker
//
//  Created by Byunsangjin on 03/04/2019.
//  Copyright © 2019 Byunsangjin. All rights reserved.
//

import Foundation
import CoreData

struct BlockDBModel {
    var blockHash: String?
    var height: Int?
    var merkleTreeRootHash: String?
    var prevBlockHash: String?
    var signature: String?
    
    var objectID: NSManagedObjectID?
}
