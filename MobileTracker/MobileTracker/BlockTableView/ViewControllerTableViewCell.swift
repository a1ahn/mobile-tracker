//
//  ViewControllerTableViewCell.swift
//  MobileTracker
//
//  Created by Seungyeon Lee on 25/01/2019.
//  Copyright © 2019 Seungyeon Lee. All rights reserved.
//

import UIKit

class ViewControllerTableViewCell: UITableViewCell {
    
    @IBOutlet weak var blockHashLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
