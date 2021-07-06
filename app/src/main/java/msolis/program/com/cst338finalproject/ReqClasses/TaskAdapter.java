package msolis.program.com.cst338finalproject.ReqClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import msolis.program.com.cst338finalproject.MainActivity;
import msolis.program.com.cst338finalproject.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList<TroubleCall> callsList;
    ItemClicked activity;

    private int type;
    private int selected;

    public TaskAdapter(Context context, ArrayList<TroubleCall> callsList, int type, int selected) {
        if(callsList == null || callsList.isEmpty())
            return;
        this.callsList = callsList;
        activity = (ItemClicked) context;
        this.type = type;
        this.selected = selected;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvDateTime, tvCallDesc;
        Button btnStartTimer, btnEndCall;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            // Links the variables to the UI components that will be used in the recycler view.
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvCallDesc = itemView.findViewById(R.id.tvCallDesc);
            btnStartTimer = itemView.findViewById(R.id.btnNewTroubleCall);
            btnEndCall = itemView.findViewById(R.id.btnEndCall);

            itemView.setOnClickListener(this);
        }

        // This method runs whenever an item in the recycler view is pressed.
        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Item pressed: " + ((TroubleCall)v.getTag()).
                    getLine().itemDesc(), Toast.LENGTH_SHORT).show();
        }
    }

    // This is where you pick what layout you want to use in the recycler view.
    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.
                activity_create_trouble_call_button, parent, false);

        return new ViewHolder(v);
    }

    // This method runs once for every item on the list that the recycler view is created from.
    @Override
    public void onBindViewHolder(@NonNull final TaskAdapter.ViewHolder holder, final int position) {
        holder.itemView.setTag(callsList.get(position));
        holder.btnEndCall.setTag(callsList.get(position));
        holder.btnEndCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            callsList.get(position).setEndDateTime(LocalDateTime.now());

            String tempDateTime = holder.tvDateTime.getText().toString() + ", End " +
                    callsList.get(position).getEndDateTime().toLocalTime().format(DateTimeFormatter.
                            ofLocalizedTime(FormatStyle.SHORT));

            holder.tvDateTime.setText(tempDateTime);

            Toast.makeText(v.getContext(), "Open Call " + (position + 1) + " Ended , " +
                    callsList.get(position).getEndDateTime().toLocalTime().format(DateTimeFormatter.
                            ofLocalizedTime(FormatStyle.SHORT)), Toast.LENGTH_SHORT).show();

            activity.onItemClicked(position,-1);
            }
        });

        holder.tvDateTime.setText(callsList.get(position).getStartDateTime().format(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
        holder.tvCallDesc.setText(callsList.get(position).getCallDesc());
        //holder.btnStartTimer.setOnClickListener();
    }

    // Gets the number of items in the list and is used to detemine how many times onBindViewHolder will run.
    @Override
    public int getItemCount() {
        return callsList.size();
    }
}
