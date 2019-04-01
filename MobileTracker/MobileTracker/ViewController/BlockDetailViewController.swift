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
    var block:BlockModel?
    
    
    
    // MARK:- Constants
    let sectionTitle = ["Block", "Transaction"]
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        guard let list = block?.result.confirmedTransactionList.first else {
            return
        }
        
        print(list.from)
        print(list.nid)
        print(list.signature)
        print(list.stepLimit)
        print(list.timestamp)
        print(list.to)
        print(list.txHash)
        print(list.value)
        print(list.version)
        
        
    }
}



extension BlockDetailViewController: UITableViewDelegate {
    func numberOfSections(in tableView: UITableView) -> Int {
        return self.sectionTitle.count
    }
    
    
    
    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return self.sectionTitle[section]
    }
}



extension BlockDetailViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        return UITableViewCell()
    }
    
    
}
