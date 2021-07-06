package msolis.program.com.cst338finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import msolis.program.com.cst338finalproject.ReqClasses.GenericRecyclerAdapter;
import msolis.program.com.cst338finalproject.ReqClasses.ItemClicked;
import msolis.program.com.cst338finalproject.ReqClasses.TaskAdapter;
import msolis.program.com.cst338finalproject.ReqClasses.TroubleCall;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity implements ItemClicked {

    private RecyclerView recyclerView, rvClosedTasks;
    private RecyclerView.Adapter taskAdapter, closedTaskAdapter;
    private RecyclerView.LayoutManager layoutManager, closedTaskLayout;

    private Button btnNewTroubleCall, btnViewCalls;


    private Model model;
    private TroubleCall tempCall;
    private final int NEW_TROUBLE_CALL = 100;
//    private final int EDIT_TROUBLE_CALL = 200;
    private final int CLOSED_TASKS = 3;
//    private int selected = -1;
//    private static final String TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = new Model();

        // Linking all of the UI components to the variables that are used in this activity.
        btnNewTroubleCall = findViewById(R.id.btnNewTroubleCall);
        btnViewCalls = findViewById(R.id.btnViewCalls);
        recyclerView = findViewById(R.id.rvOpenTasks);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        taskAdapter = new TaskAdapter(this, model.getOpenTasks(), -1, -1);
        recyclerView.setAdapter(taskAdapter);

        rvClosedTasks = findViewById(R.id.rvClosedTasks);
        rvClosedTasks.setHasFixedSize(true);
        closedTaskLayout = new LinearLayoutManager(this);
        rvClosedTasks.setLayoutManager(closedTaskLayout);
        closedTaskAdapter = new GenericRecyclerAdapter(this, model.getTasks(), CLOSED_TASKS, -1);
        rvClosedTasks.setAdapter(closedTaskAdapter);

        // When the button is pressed it will create an itents and starts the NewTroubleCall activity.
        btnNewTroubleCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            tempCall = new TroubleCall();
            Intent intent = new Intent(MainActivity.this, msolis.program.com.cst338finalproject.NewTroubleCall.class);
            intent.putExtra("start date", tempCall.getStartDateTime());

            startActivityForResult(intent, NEW_TROUBLE_CALL);
            }
        });

        // Toggles between the displaying open trouble calls and closed trouble calls.
        btnViewCalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerView.getVisibility() == View.VISIBLE){
                    recyclerView.setVisibility(View.GONE);
                    rvClosedTasks.setVisibility(View.VISIBLE);
                    btnViewCalls.setText(R.string.view_open_calls);
                }
                else{
                    recyclerView.setVisibility(View.VISIBLE);
                    rvClosedTasks.setVisibility(View.GONE);
                    btnViewCalls.setText(R.string.view_closed_calls);
                }
                closedTaskAdapter.notifyDataSetChanged();
            }
        });
    }

    // This method is called when the user presses the end call button on one of the cards that displays open calls.
    // Moves the trouble call from the open tasks array list to the tasks array list.
    @Override
    public void onItemClicked(int index, int type) {
        if(isComplete(model.getOpenTask(index))){
            model.addTask(model.removeOpenTask(index));
        }
        taskAdapter.notifyDataSetChanged();
        closedTaskAdapter.notifyDataSetChanged();

        //This was meant to allow the user to edit calls to update information.
//        TroubleCall tempCall = (TroubleCall) v.getTag();
//        Intent intent = new Intent(v.getContext(), msolis.program.com.cst338finalproject.NewTroubleCall.class);
//        intent.putExtra("start date", tempCall.getStartDateTime());
//        intent.putExtra("end date", tempCall.getEndDateTime());
//        intent.putExtra("line", model.getLines().indexOf(tempCall.getLine()));
//        intent.putExtra("mach", tempCall.getLine().getMachines().indexOf(tempCall.getMachine()));
//        intent.putExtra("issue", tempCall.getIssueDesc());
//        intent.putExtra("solution", tempCall.getSolutionDesc());
//        intent.putExtra("extRef", tempCall.getExtReferenceNums());
//        intent.putExtra("requestCode", EDIT_TROUBLE_CALL);
//        startActivityForResult(intent, EDIT_TROUBLE_CALL);
    }

    // This runs when the back or save call button is pressed from the new trouble call activity.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Checks to make sure that it's returning from the new trouble call activity, using the request code.
        if(requestCode == NEW_TROUBLE_CALL){

            // If the save button is pressed the new trouble call activity will return a result_ok
            // and save get the data passed using the intent created in the new trouble call activity.
            if(resultCode == RESULT_OK){
                int line = data.getIntExtra("line", -1);
                System.out.println("line is " + line);
                if(line >= 0);
                    tempCall.setLine(model.getLine(line));

                int mach = data.getIntExtra("mach", -1);
                if(tempCall.getLine().getLineNumber() != 101 && mach >= 0)
                    tempCall.setMachine(tempCall.getLine().getMachine(mach));

                tempCall.setIssueDesc(data.getStringExtra("issue"));
                tempCall.setSolutionDesc(data.getStringExtra("solution"));
                tempCall.setExtReferenceNums(data.getStringExtra("extRef"));
                tempCall.setStartDateTime((LocalDateTime) data.getSerializableExtra("start date"));
                tempCall.setEndDateTime((LocalDateTime) data.getSerializableExtra("end date"));

                if (isComplete(tempCall))
                    model.addTask(tempCall);
                else
                    model.addOpenTask(tempCall);
            }

            // This runs if the back button is pressed instead of saving the call. It only displays a
            // Toast stating the call was not saved.
            if(resultCode == RESULT_CANCELED || data == null){
//                tempCall.setLine(model.getLine(6));
//                tempCall.setMachine(tempCall.getLine().getMachine(6));
                Toast.makeText(MainActivity.this, "New call not saved", Toast.LENGTH_SHORT).show();
            }

            taskAdapter.notifyDataSetChanged();
            closedTaskAdapter.notifyDataSetChanged();
        }
    }

    // Short method to check if trouble call was complete by checking if the end date is null.
    // Took the opportunity to use the conditional operator.
    private boolean isComplete(TroubleCall call){
        return call.getEndDateTime() == null ? false: true;
    }
}
