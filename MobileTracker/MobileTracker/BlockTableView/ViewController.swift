//
//  ViewController.swift
//  MobileTracker
//
//  Created by Seungyeon Lee on 21/01/2019.
//  Copyright © 2019 Seungyeon Lee. All rights reserved.
//

import Foundation
import UIKit

class ViewController: UIViewController {
    
    @IBOutlet weak var tableView: UITableView!
    
    private var blockList = [BlockStruct]()
    private var lastBlockHeight: Int = 0
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view, typivarly from a nib.
        dataTask(url: "https://ctz.solidwallet.io/api/v3", method: "icx_getLastBlock", params: nil)

    }
    
    
    func dataTask(url: String, method: String, params: [String: Any]?) {
        print("데이터 받는다!!")
        var request = URLRequest(url: URL(string: url)!)
        request.httpMethod = "POST"
        
        var req = ["jsonrpc": "2.0", "method": method, "id": Int(arc4random_uniform(9999))] as [String: Any]
        
        if let param = params {
            req["params"] = param
        }
        
        do {
            let data = try JSONSerialization.data(withJSONObject: req, options: [])
            request.httpBody = data
        } catch {
            print("리턴 \(request)")
        }
        if request.value(forHTTPHeaderField: "Content-Type") == nil {
            request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        }
        
        
        let task = URLSession.shared.dataTask(with: request) {(data, response, error) in
            let decoder = JSONDecoder()
            decoder.keyDecodingStrategy = .convertFromSnakeCase
            
            let json = try! JSONSerialization.jsonObject(with: data!, options: []) as! [String : Any]
            print(json)
            
            if let data = data, let blockInfo = try? decoder.decode(BlockStruct.self, from: data) {
                self.blockList.append(blockInfo)

                print("성공")
                print(blockInfo)
                print("블락 몇개? \(self.blockList.count)")
                self.lastBlockHeight = blockInfo.result.height

                DispatchQueue.main.async {
                    self.tableView.reloadData()
                }

            } else{
                print("ERROR!! \(String(describing: response))")
            }
        }
        task.resume()
    }
}

extension ViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return blockList.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        if let blockCell = cell as? ViewControllerTableViewCell {
            blockCell.blockHashLabel.text = blockList[indexPath.row].result.blockHash
        }
        return cell
    }
    
}

extension ViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print("눌렀다")
        
        self.performSegue(withIdentifier: "goDetailView", sender: blockList[indexPath.row])
        tableView.deselectRow(at: indexPath, animated: true)
        
    }

//    }

//    // 셀 높이 설정
//    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
//        if indexPath.section == 0 {
//            return 150
//        } else {
//            return UITableView.automaticDimension
//        }
//    }
}

extension ViewController {
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if (segue.identifier == "goDetailView") {
            let vc = segue.destination as! DetailViewController
            vc.blockInfo = sender as? BlockStruct
        }
        
    }
}



