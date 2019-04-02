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
    
    @IBOutlet var pickBarButton: UIBarButtonItem!
    
    
    
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
    var isPick = false
    
    
    
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
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        print("touch")
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        print("end")
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
        }
    }
    
    
    
    @objc func cancelBarButtonClick() {
        self.isPick = !self.isPick
        
        self.pickBarButton.title = "선택"
        self.navigationItem.leftBarButtonItem = nil
        
        self.tableView.allowsSelection = true // 선택 화면에서 테이블 셀 선택 허용
        
        self.tableView.reloadData()
    }
    

    
    // MARK:- Actions
    @IBAction func checkBoxClick(_ sender: UIButton) {
        print("selected = \(sender.isSelected)")
        if sender.isSelected { // 선택 되었다면
            sender.setBackgroundImage(UIImage(named: "uncheckedBox"), for: .normal)
            sender.isSelected = false
        } else { // 선택 안되었을 떄
            sender.setBackgroundImage(UIImage(named: "checkedBox"), for: .normal)
            sender.isSelected = true
            
            print(sender.tag)
        }
    }
    
    
    
    @IBAction func pickClick(_ sender: Any) {
        self.isPick = !self.isPick // isPick toggle
        self.tableView.reloadData()
        
        if self.isPick {
            self.pickBarButton.title = "저장"
            
            let leftBarButton = UIBarButtonItem(title: "취소", style: .plain, target: self, action: #selector(cancelBarButtonClick))
            self.navigationItem.leftBarButtonItem = leftBarButton
            
            self.tableView.allowsSelection = false // 선택 화면에서 테이블 셀 선택 불가
        } else {
            self.pickBarButton.title = "선택"
            self.navigationItem.leftBarButtonItem = nil
            
            self.tableView.allowsSelection = true // 선택 화면에서 테이블 셀 선택 허용
        }
    }
}



extension ViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let blockDetailVC = self.storyboard?.instantiateViewController(withIdentifier: "BlockDetailViewController") as! BlockDetailViewController
        
        blockDetailVC.block = ViewController.blockList[indexPath.row]
        
        self.navigationController?.pushViewController(blockDetailVC, animated: true)
    }
}



extension ViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return ViewController.blockList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "blockCell") as! blockCell
        let blockResult = ViewController.blockList[indexPath.row].result
        
        if isPick {
            cell.checkBoxView.isHidden = false
            cell.titleConstraint.constant = 61
            cell.timeConstraint.constant = 61
        } else {
            cell.checkBoxView.isHidden = true
            cell.titleConstraint.constant = 10
            cell.timeConstraint.constant = 10
        }
        
        let hash = String(blockResult.blockHash)
        cell.hashLabel.text = hash
        
        cell.checkBox.tag = indexPath.row
        
        return cell
    }
}


class blockCell: UITableViewCell {
    @IBOutlet var hashLabel: UILabel!
    @IBOutlet var checkBox: UIButton!
    
    @IBOutlet var titleConstraint: NSLayoutConstraint!
    @IBOutlet var timeConstraint: NSLayoutConstraint!
    
    @IBOutlet var checkBoxView: UIView!
}

