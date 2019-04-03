//
//  DatabaseViewController.swift
//  MobileTracker
//
//  Created by Byunsangjin on 03/04/2019.
//  Copyright © 2019 Byunsangjin. All rights reserved.
//

import UIKit
import CoreData

class DatabaseViewController: UIViewController {
    @IBOutlet var tableView: UITableView!
    // MARK:- Variables
    lazy var blockArray: [BlockDBModel] = {
        return DBManager.shared.fetch()
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        blockArray = DBManager.shared.fetch()
        self.tableView.reloadData()
    }
}



extension DatabaseViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let blockDetailVC = self.storyboard?.instantiateViewController(withIdentifier: "BlockDetailViewController") as! BlockDetailViewController
        
        let block = self.blockArray[indexPath.row]
        
        let network = Network()
        let request = Request(method: .getBlockByHeight, params: ["height": "0x" + String(block.height!, radix: 16)])
        let blockModel = network.atHeightRequest(request: request)
        
        blockDetailVC.block = blockModel
        
        self.navigationController?.pushViewController(blockDetailVC, animated: true)
    }
    
    
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        let block = self.blockArray[indexPath.row]
        
        DBManager.shared.delete(block.objectID!) {
            self.blockArray.remove(at: indexPath.row)
            self.tableView.reloadData()
        }
    }
}



extension DatabaseViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.blockArray.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let blockCell = tableView.dequeueReusableCell(withIdentifier: "DatabaseCell") as! DatabaseCell
        
        let block = self.blockArray[indexPath.row]
        blockCell.hashLabel.text = block.blockHash
        
        return blockCell
    }
    
    
}


class DatabaseCell: UITableViewCell {
    @IBOutlet var hashLabel: UILabel!
    
}
