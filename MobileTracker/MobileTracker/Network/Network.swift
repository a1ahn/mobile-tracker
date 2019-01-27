//
//  Network.swift
//  MobileTracker
//
//  Created by Seungyeon Lee on 25/01/2019.
//  Copyright © 2019 Seungyeon Lee. All rights reserved.
//

import Foundation

public class Network {
    
    let semaphore = DispatchSemaphore(value: 0)
    
    private(set) static var _lastHeight: Int = 0
    private(set) static var lastHeight: Int {
        get {
            return _lastHeight
        }
        set(newVal) {
            _lastHeight = newVal
        }
    }
    
    func sendRequest(req: Requests) {
        var request = URLRequest(url: URL(string: req.provider)!)
        
        request.httpMethod = "POST"

        var body = ["jsonrpc": req.jsonrpc, "method": req.method.rawValue, "id": req.id] as [String : Any]

        if let param = req.params {
            body["params"] = param
        }

        do {
            let data = try JSONSerialization.data(withJSONObject: body, options: [])
            request.httpBody = data
            
        } catch {
            print("리턴 \(request)")
        }
        
        if request.value(forHTTPHeaderField: "Content-Type") == nil {
            request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        }

        let session = URLSession(configuration: .default)
        let task = session.dataTask(with: request) {(data, response, error) in
        
            if let data = data, let blockInfo = try? API.decoder.decode(BlockStruct.self, from: data) {
            
                Network.lastHeight = blockInfo.result.height
                ViewController.blockList.append(blockInfo)
                
                DispatchQueue.main.async {
                    NotificationCenter.default.post(name: .reload, object: nil)
                }
                self.semaphore.signal()
                
            } else{
                print("ERROR!")
            }
        }
        task.resume()
        self.semaphore.wait()
    }
    
    func reloadTableView() {
        NotificationCenter.default.post(name: .reload, object: nil)
    }
}

extension Notification.Name {
    static let reload = Notification.Name("reload")
}
