package adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maid.crpyto3.Bitcoin;
import com.maid.crpyto3.R;
import com.maid.crpyto3.coinName;

import org.w3c.dom.Text;

import java.util.ArrayList;

import Model.model;

public class adapter extends RecyclerView.Adapter<adapter.viewHolder> {
    ArrayList<model> list;
    Context context;

    public adapter(ArrayList<model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout1,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        model model = list.get(position);
        holder.inr.setText(String.valueOf(model.getInr()));
        holder.usd.setText(String.valueOf(model.getUsd()));
        holder.coin.setText(model.getCn());
        holder.coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Bitcoin.class);
                i.putExtra("Name of the coin",model.getCn());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //imp line always add this
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView inr;
        TextView usd;
        TextView coin;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            inr =itemView.findViewById(R.id.inr);
            usd = itemView.findViewById(R.id.usd);
            coin = itemView.findViewById(R.id.coin);
        }
    }
}
