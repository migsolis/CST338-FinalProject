package msolis.program.com.cst338finalproject.ReqClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import msolis.program.com.cst338finalproject.R;

public class GenericRecyclerAdapter<T extends Listable> extends RecyclerView.Adapter<GenericRecyclerAdapter.ViewHolder> {
    private final ArrayList<T> list;
    ItemClicked activity;
    private static final int LINE = 1;
    private static final int MACHINE = 2;
    private static final int CLOSED_TASK = 3;
    private int type;
    private int selected;
    private int prevSelected;

    public GenericRecyclerAdapter(Context context, ArrayList<T> list, int type, int selected) {
        this.list = list;
        activity = (ItemClicked) context;
        this.type = type;
        this.selected = selected;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvListItem;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvListItem = itemView.findViewById(R.id.tvListItem);

            if(type == LINE || type == MACHINE) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prevSelected = selected;
                        activity.onItemClicked(list.indexOf((T) v.getTag()), type);
                    }
                });
            }
        }
    }

    @NonNull
    @Override
    public GenericRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;

        if(type == LINE || type == MACHINE) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recycler, parent, false);
        }else if(type == CLOSED_TASK) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list, parent, false);
        }else
            v = new View(parent.getContext());
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericRecyclerAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(list.get(position));

        Listable item = (Listable) list.get(position);
        holder.tvListItem.setText(item.itemDesc());

        if(type == 1 || type == 2)
        if (prevSelected > 0 && prevSelected == position)
            holder.tvListItem.setBackgroundResource(R.color.colorPrimaryLight);
        if(position == selected){
            holder.tvListItem.setBackgroundResource(R.color.colorAccent);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
