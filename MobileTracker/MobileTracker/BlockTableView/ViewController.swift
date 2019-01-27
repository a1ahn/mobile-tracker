//
//  ViewController.swift
//  MobileTracker
//
//  Created by Seungyeon Lee on 21/01/2019.
//  Copyright © 2019 Seungyeon Lee. All rights reserved.
//

import Foundation
import UIKit
import Network
class ViewController: UIViewController {
    
    @IBOutlet weak var tableView: UITableView!
    
    public static var blockList = [BlockStruct]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typivarly from a nib.
        self.navigationItem.title = "ICON Tracker"

        let network = Network()
        
        let req = Requests(method: .getLastBlock, params: nil)
        network.sendRequest(req: req)
        
        for _ in 1...9 {
            let a = Requests(method: .getBlockByHeight, params: ["height": "0x" + String(Network._lastHeight-1, radix: 16)])
            network.sendRequest(req: a)
        }

        ViewController.blockList.sort { $0.result.height < $1.result.height }
        
        NotificationCenter.default.addObserver(self, selector: #selector(reloadTableData), name: .reload, object: nil)
    }
    
    @objc func reloadTableData(notification: NSNotification) {
        self.tableView.reloadData()
    }
}

extension ViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return ViewController.blockList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        
        if let blockCell = cell as? ViewControllerTableViewCell {
            
            let info = ViewController.blockList[indexPath.row].result
            blockCell.blockHashLabel.text = info.blockHash
            let date = timeStampToDate(timestamp: info.timeStamp / 1000000.0)
            blockCell.timestampLabel.text = date
            blockCell.blockHeightLabel.text = String(info.height)
        }
        return cell
    }
    
//    func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
//
//        cell.alpha = 0
//
//        UIView.animate(
//            withDuration: 0.5,
//            delay: 0.05 * Double(indexPath.row),
//            animations: {
//                cell.alpha = 1
//        })
//    }
}

extension ViewController: UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        self.performSegue(withIdentifier: "goDetailView", sender: ViewController.blockList[indexPath.row])
    }

    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if indexPath.section == 0 {
            return 115
        } else {
            return UITableView.automaticDimension
        }
    }
}

extension ViewController {
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if (segue.identifier == "goDetailView") {
            let vc = segue.destination as! DetailViewController
            vc.blockInfo = sender as? BlockStruct
        }
    }
    
    public func timeStampToDate(timestamp: Double) -> String {
        let date = Date(timeIntervalSince1970: timestamp)
        
        let dateFormatter = DateFormatter()
        dateFormatter.locale = NSLocale.current
        dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
        let result = dateFormatter.string(from: date)
        return result
        
    }
}
