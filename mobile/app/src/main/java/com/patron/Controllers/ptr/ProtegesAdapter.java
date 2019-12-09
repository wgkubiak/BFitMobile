package com.patron.Controllers.ptr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.patron.R;

import java.util.List;

public class ProtegesAdapter extends RecyclerView.Adapter<ProtegesAdapter.ProtegesViewHolder> {

    private Context mCtx;
    private List<Protege> protegesList;

    public ProtegesAdapter(Context mCtx, List<Protege> protegesList) {
        this.mCtx = mCtx;
        this.protegesList = protegesList;
    }

    @NonNull
    @Override
    public ProtegesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.proteges_list, null);

        return new ProtegesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProtegesViewHolder holder, int position) {
        Protege protege = protegesList.get(position);

        holder.textViewFirstname.setText(protege.getFirstname());
        holder.textViewLastname.setText(protege.getLastname());
        holder.textViewAge.setText(protege.getAge());
        holder.textViewPhone.setText(protege.getPhone());
    }

    @Override
    public int getItemCount() {
        return protegesList.size();
    }

    class ProtegesViewHolder extends RecyclerView.ViewHolder{

        TextView textViewFirstname, textViewLastname, textViewAge, textViewPhone;

        public ProtegesViewHolder(View v) {
            super(v);

            textViewFirstname = v.findViewById(R.id.textViewFirstname);
            textViewLastname = v.findViewById(R.id.textViewLastname);
            textViewAge = v.findViewById(R.id.textViewAge);
            textViewPhone = v.findViewById(R.id.textViewPhone);

        }
    }
}
