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

        holder.textViewName.setText(protege.getFirstname() + " " + protege.getLastname());
        holder.textViewWeight.setText(protege.getWeight());
        holder.textViewGlucose.setText(protege.getGlucose());
        holder.textViewPressure.setText(protege.getPressure());
    }

    @Override
    public int getItemCount() {
        return protegesList.size();
    }

    class ProtegesViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName, textViewWeight, textViewGlucose, textViewPressure;

        public ProtegesViewHolder(View v) {
            super(v);

            textViewName = v.findViewById(R.id.textViewName);
            textViewWeight = v.findViewById(R.id.textViewWeight);
            textViewGlucose = v.findViewById(R.id.textViewGlucose);
            textViewPressure = v.findViewById(R.id.textViewPressure);
        }
    }
}
