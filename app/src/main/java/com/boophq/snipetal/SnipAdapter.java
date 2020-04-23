package com.boophq.snipetal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class SnipAdapter extends ListAdapter<Snip, SnipAdapter.SnipHolder> {
    private OnItemClickListener listener;

    public SnipAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Snip> DIFF_CALLBACK = new DiffUtil.ItemCallback<Snip>() {
        @Override
        public boolean areItemsTheSame(@NonNull Snip oldItem, @NonNull Snip newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Snip oldItem, @NonNull Snip newItem) {
            return oldItem.getSubject().equals(newItem.getSubject()) &&
                    oldItem.getContent().equals(newItem.getContent()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public SnipHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.snip_item, parent, false);
        return new SnipHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SnipHolder holder, int position) {
        Snip currentSnip = getItem(position);
        holder.textViewSubject.setText(currentSnip.getSubject());
        holder.textViewContent.setText(currentSnip.getContent());
        holder.textViewPriority.setText(String.valueOf(currentSnip.getPriority()));
    }

    public Snip getSnipAt(int position) {
        return getItem(position);
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(Snip snip);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}