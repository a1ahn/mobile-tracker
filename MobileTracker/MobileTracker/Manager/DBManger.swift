//
//  DBManger.swift
//  MobileTracker
//
//  Created by Byunsangjin on 03/04/2019.
//  Copyright © 2019 Byunsangjin. All rights reserved.
//

import UIKit
import CoreData

class DBManager {
    // MARK:- Variables
    lazy var context: NSManagedObjectContext = {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        return appDelegate.persistentContainer.viewContext
    }()
    
    
    
    // MARK:- Constants
    static let shared = DBManager()
    
    func fetch() -> [BlockDBModel] {
        var blockList = [BlockDBModel]()
        
        // 요청 객체 생성
        let fetchRequest : NSFetchRequest<BlockMO> = BlockMO.fetchRequest()
        
        // 데이터 가져오기
        let result = try! context.fetch(fetchRequest)
        
        for record in result {
            var block = BlockDBModel()
            
            block.blockHash = record.blockHash
            block.height = Int(record.height)
            block.merkleTreeRootHash = record.merkleTreeRootHash
            block.prevBlockHash = record.prevBlockHash
            block.signature = record.signature
            block.objectID = record.objectID
            
            blockList.append(block)
        }
        
        return blockList
    }
    
    
    
//    func fetchTransaction(hash: String? = nil) -> [TransactionDBModel] {
//        var transactionList = [TransactionDBModel]()
//        
//        // 요청 객체 생성
//        let fetchRequest : NSFetchRequest<TransactionMO> = TransactionMO.fetchRequest()
//        
//        if let blockHash = hash {
//            fetchRequest.predicate = NSPredicate(format: "blockHash = %@", blockHash)
//        }
//        
//        // 데이터 가져오기
//        let result = try! context.fetch(fetchRequest)
//        
//        for record in result {
//            var transaction = TransactionDBModel()
//            
//            transaction.blockHash = record.blockHash
//            transaction.from = record.from
//            transaction.to = record.to
//            transaction.txHash = record.txHash
//            transaction.objectID = record.objectID
//            
//            transactionList.append(transaction)
//        }
//        
//        return transactionList
//    }
    
    
    
    func insert(block: BlockModel) -> Bool {
        let blockObject = NSEntityDescription.insertNewObject(forEntityName: "Block", into: context)
        
        blockObject.setValue(block.result.blockHash, forKey: "blockHash")
        blockObject.setValue(block.result.height, forKey: "height")
        blockObject.setValue(block.result.merkleTreeRootHash, forKey: "merkleTreeRootHash")
        blockObject.setValue(block.result.prevBlockHash, forKey: "prevBlockHash")
        blockObject.setValue(block.result.signature, forKey: "signature")
        
        do {
            try context.save()
            return true
        } catch {
            context.rollback()
            return false
        }
    }
    
//    func insertTransaction(block: BlockModel) -> Bool {
//        let transactionObject = NSEntityDescription.insertNewObject(forEntityName: "Transaction", into: context)
//
//        for transaction in block.result.confirmedTransactionList {
//            transactionObject.setValue(block.result.blockHash, forKey: "blockHash")
//            transactionObject.setValue(transaction.txHash, forKey: "txHash")
//            transactionObject.setValue(transaction.to, forKey: "to")
//            transactionObject.setValue(transaction.from, forKey: "from")
//        }
//
//        do {
//            try context.save()
//            return true
//        } catch {
//            context.rollback()
//            return false
//        }
//    }
    
    
    
    func delete(_ objectID : NSManagedObjectID, complition: (()->Void)? = nil) -> Bool {
        // 삭제할 객체를 찾아, 컨텍스트에서 삭제한다.
        let object = self.context.object(with: objectID)
        self.context.delete(object)
        
        do {
            // 삭제된 내역을 영구저장소에 반영한다.
            try self.context.save()
            complition?()
            return true
        } catch let e as NSError {
            NSLog("An error has occurred : %s", e.localizedDescription)
            return false
        }
    }
}
