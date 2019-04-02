//
//  Network.swift
//  MobileTracker
//
//  Created by Byunsangjin on 29/03/2019.
//  Copyright © 2019 Byunsangjin. All rights reserved.
//

import Foundation

class Network {
    let semaphore = DispatchSemaphore(value: 0)
    
    
    
    static var lastHeight = 0
    
    
    
    func sendRequest(request: Request, complition: (()->Void)? = nil) {
        var requestURL = URLRequest(url: URL(string: request.path)!)
        requestURL.httpMethod = "POST"
        
        var body = ["jsonrpc": request.jsonrpc, "method": request.method.rawValue, "id": request.id ] as [String : Any]
        if let params = request.params {
            body["params"] = params
        }
        
        do {
            let data = try JSONSerialization.data(withJSONObject: body, options: [])
            requestURL.httpBody = data
        } catch {}
        
        requestURL.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        let session = URLSession(configuration: .default)
        let task = session.dataTask(with: requestURL) { (data, response, error) in
            // 통신이 실패했을 떄
            if let e = error {
                print("error = \(e.localizedDescription)")
                return
            }
            
            // 응답 처리 로직
            if let block = try? API.decoder.decode(BlockModel.self, from: data!) {
                Network.lastHeight = block.result.height
                ViewController.blockList.append(block)
                print("height = \(block.result.height)")
                
                self.semaphore.signal()
            } else {
                print("error")
                Network.lastHeight = Network.lastHeight - 1 // 오류시 현재꺼 건너뛰기
                
                self.semaphore.signal()
            }
        }
        
        task.resume()
        self.semaphore.wait()
    }
    
    
    func txHashsendRequest(request: Request, complition: (()->Void)? = nil) {
        var requestURL = URLRequest(url: URL(string: request.path)!)
        requestURL.httpMethod = "POST"
        
        var body = ["jsonrpc": request.jsonrpc, "method": request.method.rawValue, "id": request.id ] as [String : Any]
        if let params = request.params {
            print(params)
            body["params"] = params
        }
        
        do {
            let data = try JSONSerialization.data(withJSONObject: body, options: [])
            requestURL.httpBody = data
        } catch {}
        
        requestURL.setValue("application/json", forHTTPHeaderField: "Content-Type")
        
        let session = URLSession(configuration: .default)
        let task = session.dataTask(with: requestURL) { (data, response, error) in
            // 통신이 실패했을 떄
            if let e = error {
                print("error = \(e.localizedDescription)")
                return
            }
            
            // 응답 처리 로직
            if let block = try? API.decoder.decode(BlockModelByTxHash.self, from: data!) {
                TxHashDetailViewController.txHashInfo = block
                
                self.semaphore.signal()
            } else {
                print("error")
                
                self.semaphore.signal()
            }
        }
        
        task.resume()
        self.semaphore.wait()
    }
}
