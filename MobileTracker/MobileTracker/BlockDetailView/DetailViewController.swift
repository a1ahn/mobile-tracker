//
//  DetailViewController.swift
//  MobileTracker
//
//  Created by Seungyeon Lee on 25/01/2019.
//  Copyright © 2019 Seungyeon Lee. All rights reserved.
//

import UIKit

class DetailViewController: UIViewController {

    @IBOutlet weak var tableView: UITableView!
    
    let sectionHeaderTitle: [String] = ["Block Info", "Transactions"]

    var blockInfo: BlockStruct?

    override func viewDidLoad() {
        super.viewDidLoad()
        
        // 테이블 뷰 셀 선택 않도록 설정
        tableView.allowsSelection = false

        // Do any additional setup after loading the view.
    }
    
    public func timeStampToDate(timestamp: Double) -> String {
        let date = Date(timeIntervalSince1970: timestamp)
        
        let dateFormatter = DateFormatter()
        dateFormatter.locale = NSLocale.current
        dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
        let result = dateFormatter.string(from: date)
        return result
        
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}

extension DetailViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        switch section {
        case 0: return 1
        case 1: return blockInfo?.result.confirmedTransactionList.count ?? 0
        default:
            return 0
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        switch indexPath.section {
        case 0:
            let blockCell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
            if let cell = blockCell as? BlockTableViewCell, let block = blockInfo {
                let result = block.result

                cell.heightLabel.text = String(result.height)

                let date = timeStampToDate(timestamp: result.timeStamp / 1000000.0)
                cell.timeStampLabel.text = date
                cell.hashLabel.text = result.blockHash
                cell.prevHashLabel.text = result.prevBlockHash
            }
            return blockCell
            
        default:
            let transactionCell = tableView.dequeueReusableCell(withIdentifier: "cell2", for: indexPath)
            if let cell = transactionCell as? TransactionTableViewCell, let block = blockInfo {
                let transactionInfo = block.result.confirmedTransactionList[indexPath.row]
                
                cell.txHashLabel.text = transactionInfo.txHash
                cell.fromLabel.text = transactionInfo.from
                cell.toLabel.text = transactionInfo.to
                cell.amountLabel.text = transactionInfo.value
                cell.txFeeLabel.text = transactionInfo.stepLimit
                cell.methodLabel.text = transactionInfo.data?.method ?? "transfer"
            }
            return transactionCell
        }
    }
}

extension DetailViewController: UITableViewDelegate {
    func numberOfSections(in tableView: UITableView) -> Int {
        return sectionHeaderTitle.count
    }
    
    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return sectionHeaderTitle[section]
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if indexPath.section == 0 {
            return 175
        } else if indexPath.section == 1 {
            return 193
        } else {
            return UITableView.automaticDimension
        }
    }
}
