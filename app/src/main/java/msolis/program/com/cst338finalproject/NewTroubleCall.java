package msolis.program.com.cst338finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import msolis.program.com.cst338finalproject.ReqClasses.ItemClicked;
import msolis.program.com.cst338finalproject.ReqClasses.Line;
import msolis.program.com.cst338finalproject.ReqClasses.GenericRecyclerAdapter;
import msolis.program.com.cst338finalproject.ReqClasses.MachineCenter;
import msolis.program.com.cst338finalproject.ReqClasses.TroubleCall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class NewTroubleCall extends AppCompatActivity implements ItemClicked {

    RecyclerView recyclerView;
//    RecyclerView.Adapter listAdapter;
    GenericRecyclerAdapter<Line> lineListAdapter;
    GenericRecyclerAdapter<MachineCenter> machListAdapter;
    RecyclerView.LayoutManager layoutManager;
    Model model;
    TroubleCall call;

    Button btnSetStartTime, btnSetEndTime, btnSetLine, btnSetMach, btnSaveCall;
    EditText etIssueDesc, etSolutionDesc,etExtRef;

    private static final int LINE = 1;
    private static final int MACHINE = 2;
    private static final int EDIT_TROUBLE_CALL = 200;
    private int lineSelected = -1;
    private int machSelected = -1;
    private boolean taskIsComplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trouble_call);

        model = new Model();
        call = new TroubleCall();
        call.setStartDateTime((LocalDateTime) getIntent().getSerializableExtra("start date"));

        // For future functionality to allow the user to edit existing calls.
        if(getIntent().getIntExtra("requestCode", -1) == EDIT_TROUBLE_CALL){

            call.setEndDateTime((LocalDateTime)getIntent().getSerializableExtra("end date"));
            call.setLine(model.getLines().get(getIntent().getIntExtra("line", -1)));
            call.setMachine(call.getLine().getMachine(getIntent().getIntExtra("mach", -1)));
            call.setIssueDesc(getIntent().getStringExtra("issue"));
            call.setSolutionDesc(getIntent().getStringExtra("solution"));
            call.setExtReferenceNums(getIntent().getStringExtra("extRef"));
        }

        init();

        recyclerView = findViewById(R.id.rvItems);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btnSetEndTime.setText(R.string.end_task);

        setListeners();
    }

    // Links the variable to the UI components
    public void init(){
        btnSetStartTime = findViewById(R.id.btnSetStartTime);
        btnSetEndTime = findViewById(R.id.btnSetEndTime);
        btnSetLine = findViewById(R.id.btnSetLine);
        btnSetMach = findViewById(R.id.btnSetMach);
        btnSaveCall = findViewById(R.id.btnSaveCall);
        etIssueDesc = findViewById(R.id.etIssueDesc);
        etSolutionDesc = findViewById(R.id.etSolutionDesc);
        etExtRef = findViewById(R.id.etExtRef);
    }

    // Where all the listeners are set for buttons on the new trouble call screen.
    public void setListeners(){

        // when the end button is changes the text to task complete and sets the end
        // time to the current time.
        btnSetEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!taskIsComplete) {
                    btnSetEndTime.setText(R.string.task_complete);
                    call.setEndDateTime(LocalDateTime.now());
                }
            }
        });

        // Toggles the visibility of the recycler view when the set line button is pressed and creates
        // the instance of the recycler view being used to display the lines.
        btnSetLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerView.getVisibility() == View.VISIBLE){
                    recyclerView.setVisibility(View.GONE);
                    return;
                }

                // list of lines that will be passed to the generic adapter.
                ArrayList<Line> lines = model.getLines();

                lineListAdapter = new GenericRecyclerAdapter<Line>(NewTroubleCall.this, lines, LINE, lineSelected);

                recyclerView.setAdapter(lineListAdapter);

                recyclerView.setVisibility(View.VISIBLE);
            }


        });

        // If a line is selected then this will toggles the visibility of the recycler view when the set line button is pressed and creates
        // the instance of the recycler view being used to display the machines.
        btnSetMach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(call.getLine().getLineNumber() < 0)
                    return;

                if(recyclerView.getVisibility() == View.VISIBLE){
                    recyclerView.setVisibility(View.GONE);
                    return;
                }

                // List of machines that will be passed to the generic adapter.
                ArrayList<MachineCenter> machines = call.getLine().getMachines();

                machListAdapter = new GenericRecyclerAdapter<MachineCenter>(NewTroubleCall.this, machines, MACHINE, machSelected);

                recyclerView.setAdapter(machListAdapter);

                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        // creates and intents and puts in the data gather in this activity and sets the result code
        // and finished the activity.
        btnSaveCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                call.setIssueDesc(etIssueDesc.getText().toString());
                call.setSolutionDesc(etSolutionDesc.getText().toString());
                call.setExtReferenceNums(etExtRef.getText().toString());

                Intent intent = new Intent();
                intent.putExtra("start date", call.getStartDateTime());
                intent.putExtra("end date", call.getEndDateTime());
                intent.putExtra("line", model.getLines().indexOf(call.getLine()));
                intent.putExtra("mach", call.getLine().getMachines().indexOf(call.getMachine()));
                intent.putExtra("issue", call.getIssueDesc());
                intent.putExtra("solution", call.getSolutionDesc());
                intent.putExtra("extRef", call.getExtReferenceNums());

                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }

    // sets which line or machine center was selected when an item on the recycler list was pressed.
    // which one is set depends on the type argument passed to the method.
    @Override
    public void onItemClicked(int index, int type) {
        if(type == LINE){
            lineSelected = index;
            call.setLine(model.getLine(index));
            btnSetLine.setText(call.getLine().itemDesc());
            lineListAdapter.notifyDataSetChanged();
        }
        else if(type == MACHINE){
            machSelected = index;
            call.setMachine((call.getLine().getMachine(index)));
            btnSetMach.setText(call.getMachine().itemDesc());
            machListAdapter.notifyDataSetChanged();
        }

        recyclerView.setVisibility(View.GONE);
    }
}
