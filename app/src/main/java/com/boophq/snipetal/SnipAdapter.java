package com.boophq.snipetal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SnipAdapter extends RecyclerView.Adapter<SnipAdapter.SnipHolder> {
    private List<Snip> snips = new ArrayList<>();

    @NonNull
    @Override
    public SnipHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.snip_item, parent, false);
        return new SnipHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SnipHolder holder, int position) {
        Snip currentSnip = snips.get(position);
        holder.textViewSubject.setText(currentSnip.getSubject());
        holder.textViewContent.setText(currentSnip.getContent());
        holder.textViewPriority.setText(String.valueOf(currentSnip.getPriority()));
    }

    @Override
    public int getItemCount() {
        return snips.size();
    }

    public void setSnips(List<Snip> snips) {
        this.snips = snips;
        notifyDataSetChanged();
    }

    public Snip getSnipAt(int position) {
        return snips.get(position);
    }

    class SnipHolder extends RecyclerView.ViewHolder {
        private TextView textViewSubject;
        private TextView textViewContent;
        private TextView textViewPriority;

        public SnipHolder(@NonNull View itemView) {
            super(itemView);
            textViewSubject = itemView.findViewById(R.id.text_view_subject);
            textViewContent = itemView.findViewById(R.id.text_view_content);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
        }
    }
}