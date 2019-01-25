//
//  BlockTableViewCell.swift
//  MobileTracker
//
//  Created by Seungyeon Lee on 26/01/2019.
//  Copyright © 2019 Seungyeon Lee. All rights reserved.
//

import UIKit

class BlockTableViewCell: UITableViewCell {

    @IBOutlet weak var heightLabel: UILabel!
    @IBOutlet weak var timeStampLabel: UILabel!
    @IBOutlet weak var hashLabel: UILabel!
    @IBOutlet weak var prevHashLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
