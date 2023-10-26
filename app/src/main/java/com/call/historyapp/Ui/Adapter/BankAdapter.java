package com.call.historyapp.Ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.call.historyapp.Model.BankModel;
import com.call.historyapp.R;

import java.util.ArrayList;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.MyViewHolder> {
    private final Context con;
    private final ArrayList<BankModel> bank;
    private final BankCLick bankCLick;

    public BankAdapter(Context context, ArrayList<BankModel> bankList, BankCLick bankCLick) {
        this.con = context;
        this.bank = bankList;
        this.bankCLick = bankCLick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(con).inflate(R.layout.layout_item_bank, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TvBankName.setText(bank.get(position).getBankName());
        holder.IvBankIcon.setImageResource(bank.get(position).getBankIcon());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bankCLick.BankListen(position, bank.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return bank.size();
    }

    public interface BankCLick {
        void BankListen(int position, BankModel bankModel);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView TvBankName;
        private final ImageView IvBankIcon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TvBankName = itemView.findViewById(R.id.TvBankName);
            IvBankIcon = itemView.findViewById(R.id.IvBankIcon);
        }
    }
}
