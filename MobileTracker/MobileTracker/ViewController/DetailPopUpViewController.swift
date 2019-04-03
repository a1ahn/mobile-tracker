//
//  DetailPopUpViewController.swift
//  MobileTracker
//
//  Created by Byunsangjin on 02/04/2019.
//  Copyright © 2019 Byunsangjin. All rights reserved.
//

import UIKit

class DetailPopUpViewController: UIViewController {
    // MARK:- Outlets
    @IBOutlet var titleLabel: UILabel!
    @IBOutlet var contentLabel: UILabel!
    @IBOutlet var popUpView: UIView!
    
    @IBOutlet var okButton: UIButton!
    @IBOutlet var dismissButton: UIButton!
    
    
    
    // MARK:- Methods
    override func viewDidLoad() {
        super.viewDidLoad()
        
         self.view.backgroundColor = UIColor.black.withAlphaComponent(0.6) // 배경 불투명
        
        self.popUpView.layer.cornerRadius = 20
        self.popUpView.clipsToBounds = true
        
        self.okButton.layer.cornerRadius = 10
        self.okButton.clipsToBounds = true
        
        self.dismissButton.layer.cornerRadius = 10
        self.dismissButton.clipsToBounds = true
    }
    
    
    
    func dismissPopUp() {
        self.removeFromParent()
        self.view.removeFromSuperview()
    }
    
    
    
    // MARK:- Actions
    @IBAction func okButtonClick(_ sender: Any) {
        // 문자열을 클립보드에 복사
        UIPasteboard.general.string = self.contentLabel.text
        
        self.dismissPopUp()
    }
    
    
    
    @IBAction func dismissButton(_ sender: Any) {
        self.dismissPopUp()
    }
}
