//
//  ViewController.swift
//  MobileTracker
//
//  Created by Byunsangjin on 28/03/2019.
//  Copyright © 2019 Byunsangjin. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    // MARK:- Outlets
    @IBOutlet var tableView: UITableView!
    @IBOutlet var indicator: UIActivityIndicatorView!
    
    
    
    // MARK:- Variables
    static var blockList = [BlockModel]()
    var refreshFlag: Bool = false {
        willSet(new) {
            if new {
                print("start")
                self.indicator.isHidden = false
            } else {
                print("stop")
                DispatchQueue.main.async {
                    self.indicator.isHidden = true
                    self.tableView.reloadData()
                }
            }
        }
    }
    
    
    
    // MARK:- Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.indicator.startAnimating()
        
        let network = Network()
        
        let request = Request(method: .getLastBlock, params: nil)
        network.sendRequest(request: request)
        
        for _ in 1...9 {
            let req = Request(method: .getBlockByHeight, params: ["height": "0x" + String(Network.lastHeight-1, radix: 16)])
            
            network.sendRequest(request: req)
        }
    }
    
    
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        let offsetY = scrollView.contentOffset.y
        let contentHeight = scrollView.contentSize.height
        
        if offsetY > (contentHeight - scrollView.frame.height) {
            if !refreshFlag {
                self.pullToRefresh()
            }
        }
    }
    
    
    
    func pullToRefresh() {
        refreshFlag = true
        
        // 비동기 처리
        DispatchQueue.global().asyncAfter(deadline: .now() + 0.25) {
            for _ in 1...10 {
                let network = Network()
                let req = Request(method: .getBlockByHeight, params: ["height": "0x" + String(Network.lastHeight-1, radix: 16)])
                
                network.sendRequest(request: req)
            }
            self.refreshFlag = false
//            self.tableView.reloadData()
        }
    }
    

}



extension ViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 80
    }
}


extension ViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return ViewController.blockList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "blockCell") as! blockCell
        
        cell.heightLabel.text = String(ViewController.blockList[indexPath.row].result.blockHash)
        
        return cell
    }
}


class blockCell: UITableViewCell {
    @IBOutlet var heightLabel: UILabel!
}

