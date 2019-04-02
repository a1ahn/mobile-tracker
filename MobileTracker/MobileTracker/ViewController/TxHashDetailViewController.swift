//
//  TxHashDetailViewController.swift
//  MobileTracker
//
//  Created by Byunsangjin on 02/04/2019.
//  Copyright © 2019 Byunsangjin. All rights reserved.
//

import UIKit

class TxHashDetailViewController: UIViewController {
    // MARK:- Variables
    var txHash: String?
    static var txHashInfo: BlockModelByTxHash?
    var txHashInfoArray = [String]()
    
    
    
    // MARK:- Constants
    let txHashInfoTitle = ["TxHash", "From", "To", "signature", "Time Stamp"]
    
    
    
    // MARK:- Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let network = Network()
        let req = Request(method: .getTransactionByHash, params: ["txHash": self.txHash!])
        network.txHashsendRequest(request: req)
        
        self.txHashInfoArray.append((TxHashDetailViewController.txHashInfo?.result.txHash)!)
        self.txHashInfoArray.append((TxHashDetailViewController.txHashInfo?.result.from)!)
        self.txHashInfoArray.append((TxHashDetailViewController.txHashInfo?.result.to)!)
        self.txHashInfoArray.append((TxHashDetailViewController.txHashInfo?.result.signature)!)
        
        let time = Double((TxHashDetailViewController.txHashInfo?.result.timestamp)!)
        self.txHashInfoArray.append((time?.doubleToDateString())!)
    }
}



extension TxHashDetailViewController: UITableViewDelegate {
    
}



extension TxHashDetailViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.txHashInfoTitle.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "TxHashCell") as! TxHashCell
        
        cell.titleLabel.text = self.txHashInfoTitle[indexPath.row]
        cell.contentLabel.text = self.txHashInfoArray[indexPath.row]
        
        return cell
    }
    
    
    
}



class TxHashCell: UITableViewCell {
    @IBOutlet var titleLabel: UILabel!
    @IBOutlet var contentLabel: UILabel!
}
