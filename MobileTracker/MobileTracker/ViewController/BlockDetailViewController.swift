//
//  BlockDetailViewController.swift
//  MobileTracker
//
//  Created by Byunsangjin on 01/04/2019.
//  Copyright © 2019 Byunsangjin. All rights reserved.
//

import UIKit

class BlockDetailViewController: UIViewController {
    // MARK:- Outlets
    @IBOutlet var tableView: UITableView!
    
    
    
    // MARK:- Variables
    var block: BlockModel?
    var blockInfoArray = [String]() // block info array
    var txHashArray = [BlockModel.TransactionInfo]()
    
    
    
    // MARK:- Constants
    let sectionTitle = ["Block Info", "Transactions"]
    let blockInfoTitle = ["Block Hash", "Prev Hash", "MTR Hash", "Signature", "Block Height"]
    
    
    
    // MARK:- Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        
        guard let blockResult = block?.result else {
            return
        }
        
        self.blockInfoArray.append(blockResult.blockHash)
        self.blockInfoArray.append(blockResult.prevBlockHash)
        self.blockInfoArray.append(blockResult.merkleTreeRootHash)
        self.blockInfoArray.append(blockResult.signature)
        self.blockInfoArray.append(String(blockResult.height))
        
        for transaction in blockResult.confirmedTransactionList {
            txHashArray.append(transaction)
        }
    }
}



extension BlockDetailViewController: UITableViewDelegate {
    func numberOfSections(in tableView: UITableView) -> Int {
        return self.sectionTitle.count
    }
    
    
    
    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return self.sectionTitle[section]
    }
    
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if indexPath.section == 1 {
            let txHashDetailVC = self.storyboard?.instantiateViewController(withIdentifier: "TxHashDetailViewController") as! TxHashDetailViewController
            txHashDetailVC.txHash = self.txHashArray[indexPath.row].txHash
            
            self.navigationController?.pushViewController(txHashDetailVC, animated: true)
        }
    }
}



extension BlockDetailViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        switch section {
        case 0:
            return self.blockInfoTitle.count
        case 1:
            return self.txHashArray.count
        default:
            return 0
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        switch indexPath.section {
        case 0: // block detail section
            let blockDetailCell = tableView.dequeueReusableCell(withIdentifier: "BlockDetailCell") as! BlockDetailCell
            
            blockDetailCell.titleLabel.text = self.blockInfoTitle[indexPath.row]
            blockDetailCell.contentLabel.text = self.blockInfoArray[indexPath.row]
            
            
            
            return blockDetailCell
            
        
        default: // transactions section
            let transactionCell = tableView.dequeueReusableCell(withIdentifier: "TransactionCell") as! TransactionCell
            
            transactionCell.txHashLabel.text = self.txHashArray[indexPath.row].txHash
            transactionCell.fromLabel.text = self.txHashArray[indexPath.row].from
            transactionCell.toLabel.text = self.txHashArray[indexPath.row].to
            
            return transactionCell
        }
    }
    
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        switch indexPath.section {
        case 0:
            return 50
        default:
            return 120
        }
    }
    
}



class BlockDetailCell: UITableViewCell {
    @IBOutlet var titleLabel: UILabel!
    @IBOutlet var contentLabel: UILabel!
}



class TransactionCell: UITableViewCell {
    @IBOutlet var txHashLabel: UILabel!
    @IBOutlet var fromLabel: UILabel!
    @IBOutlet var toLabel: UILabel!
}
