package me.myds.g2u.mobiletracker.viewholder;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import me.myds.g2u.mobiletracker.HashImage;
import me.myds.g2u.mobiletracker.data.Transaction;
import me.myds.g2u.mobiletracker.R;

public class TransactionViewHolder extends BaseRecyclerViewHolder<Transaction> {

    public HashImage hashImage;
    public TextView txtHash;
    public TextView txtFrom;
    public TextView txtTo;

    public TransactionViewHolder(@NonNull View itemView) {
        super(itemView);
        this.txtHash = itemView.findViewById(R.id.txtHash);
        this.txtFrom = itemView.findViewById(R.id.txtFrom);
        this.txtTo = itemView.findViewById(R.id.txtTo);
        this.hashImage = itemView.findViewById(R.id.hashImage);
    }

    @Override
    public void bindData(Transaction transaction) {
        this.txtHash.setText(transaction.getTxHash());
        this.txtFrom.setText(transaction.getFrom());
        this.txtTo.setText(transaction.getTo());
        this.hashImage.setHashString(transaction.getTxHash().substring(2));
    }
}
