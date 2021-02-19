package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoandroid.OnChecklistClickListener;
import com.example.todoandroid.OnChecklistLongClickListener;
import com.example.todoandroid.R;

import java.util.ArrayList;

import models.Checklist;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ChecklistViewHolder> {

    private Context context;
    private ArrayList<Checklist> checklists;
    private OnChecklistClickListener clickListener;
    private OnChecklistLongClickListener longClickListener;

    public ChecklistAdapter(Context context, ArrayList<Checklist> checklists, OnChecklistClickListener clickListener, OnChecklistLongClickListener longClickListener){
        this.context = context;
        this.checklists = checklists;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ChecklistAdapter.ChecklistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.checklist_item, null, false);
        return new ChecklistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChecklistViewHolder holder, int position) {
        holder.bind(checklists.get(position), clickListener, longClickListener);
    }

    @Override
    public int getItemCount() {
        return checklists.size();
    }

    public static class ChecklistViewHolder extends RecyclerView.ViewHolder{
        CheckBox chkChecklistItem;

        public ChecklistViewHolder(@NonNull View itemView) {
            super(itemView);
            chkChecklistItem = itemView.findViewById(R.id.chkChecklistItem);
        }

        public void bind(Checklist checklist, OnChecklistClickListener clickListener, OnChecklistLongClickListener longClickListener){
            chkChecklistItem.setText(checklist.getTitle());
            chkChecklistItem.setChecked(checklist.getCompletion());


            chkChecklistItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longClickListener.onChecklistLongClickListener(checklist);
                    return false;
                }
            });


        }
    }
}
