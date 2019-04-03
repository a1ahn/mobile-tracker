//
//  DateUtils.swift
//  MobileTracker
//
//  Created by Byunsangjin on 01/04/2019.
//  Copyright © 2019 Byunsangjin. All rights reserved.
//

import Foundation

extension Double {
    func doubleToDateString() -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
        
        let _time = self / 1000000.0 // timestamp가 마이크로 초라서 10^-6을 나누어 준다.
        let date = Date(timeIntervalSince1970: _time)
        
        let dateString = dateFormatter.string(from: date)
        
        return dateString
    }
}

