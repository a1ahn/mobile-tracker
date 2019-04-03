//
//  TransactionDBModel.swift
//  MobileTracker
//
//  Created by Byunsangjin on 03/04/2019.
//  Copyright © 2019 Byunsangjin. All rights reserved.
//

import Foundation
import CoreData

struct TransactionDBModel {
    var blockHash: String?
    var from: String?
    var to: String?
    var txHash: String?
    
    var objectID: NSManagedObjectID?
}
