package msolis.program.com.cst338finalproject;

import java.util.ArrayList;

import msolis.program.com.cst338finalproject.ReqClasses.Line;
import msolis.program.com.cst338finalproject.ReqClasses.Location;
import msolis.program.com.cst338finalproject.ReqClasses.MachineCenter;
import msolis.program.com.cst338finalproject.ReqClasses.TroubleCall;

// Stores all of the data for the app.
public class Model {

    // Contains multiple lists to allow for and unknown number of entries.
    private int[] lineNums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private String[] machDescs = {"Depal", "Filler", "Corker", "Capper", "Foiler", "Labeler",
            "Packer", "Sealer", "Printer", "Palletizer"};
    private static ArrayList<Line> lines = new ArrayList<Line>();
//    private static ArrayList<Location> locations = new ArrayList<Location>();
    private static ArrayList<TroubleCall> tasks = new ArrayList<TroubleCall>();
    private static ArrayList<TroubleCall> openTasks = new ArrayList<TroubleCall>();
    private static boolean testItemsCreated = false;

//    private static final String TAG= "Model";

    Model(){
        if(testItemsCreated == false)
            initTestModel();
    }

    // generates test data such as lines and machines to test app functionality.
    private void initTestModel(){
        Location location1Test = new Location("Winery");

        for(int line: lineNums){
            Line tempLine = new Line("Test Winery", line);

            for(String machDesc: machDescs){
                MachineCenter tempMach = new MachineCenter(machDesc);
                tempLine.addMachine(tempMach);
            }

            lines.add(tempLine);
        }

        location1Test.setLines(lines);
        for (int i = 0; i < 3; i ++){
            TroubleCall temp = new TroubleCall(lines.get(i),lines.get(i).getMachine(i));

           openTasks.add(temp);
//           tasks.add(temp);
        }

        testItemsCreated = true;
    }

    // returns the array list of lines.
    public ArrayList<Line> getLines(){
        return new ArrayList<Line>(lines);
    }

    public boolean addLine(Line newLine){
        if(newLine == null)
            return false;
        this.lines.add(newLine);
        return true;
    }

    // returns the line specified by the argument passed
    public Line getLine(int index){
        if(index < 0 || index > lines.size())
            return new Line("Invalid", -1);
        return lines.get(index);
    }

    // returns the array list of closed calls.
    public ArrayList<TroubleCall> getTasks(){
        return tasks;
    }

    // used to add a call to the closed calls array list.
    public boolean addTask(TroubleCall newTask){
        if(newTask == null)
            return false;
        this.tasks.add(newTask);
        return true;
    }

    // returns the array list of open calls.
    public ArrayList<TroubleCall> getOpenTasks(){
        return openTasks;
    }

    // used to add a call to the open calls array list.
    public boolean addOpenTask(TroubleCall newTask){
        if(newTask == null)
            return false;
        this.openTasks.add(newTask);
        return true;
    }

    // returns a specific open call.
    public TroubleCall getOpenTask(int index){
        if(index < 0 || index > openTasks.size())
            return new TroubleCall("Invalid index","");
        return openTasks.get(index);
    }

    // used to remove and return a specific call from the open calls list.
    public TroubleCall removeOpenTask(int index){
//        Log.d(TAG, "Remove open task in position " + index);
        return openTasks.remove(index);
    }
}
