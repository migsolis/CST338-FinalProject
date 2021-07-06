package msolis.program.com.cst338finalproject.ReqClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

// Contains all the relevant information for a trouble call, along with accessors and mutators for
// all of the individual variables.
public class TroubleCall extends Task implements Trackable, Listable, Parcelable {
    private LocalDateTime startDateTime, endDateTime;
    private Duration callDuration;
    private Line line;
    private MachineCenter machine;
    private String issueDesc;
    private String solutionDesc;
    private String extReferenceNums;

    public TroubleCall() {
        super("", "");
        this.startDateTime = LocalDateTime.now();
        this.callDuration = Duration.ofMinutes(0);
        this.line = new Line("Temp Line", -1);
        this.machine = new MachineCenter("Temp Machine");
        issueDesc = "";
        solutionDesc = "";
        extReferenceNums = "";
    }

    public TroubleCall(String description, String comment) {
        super(description, comment);
        this.startDateTime = LocalDateTime.now();
        this.callDuration = Duration.ofMinutes(0);
        this.line = new Line("Temp Line", -1);
        this.machine = new MachineCenter("Temp Machine");
        issueDesc = "";
        solutionDesc = "";
        extReferenceNums = "";
    }

    public TroubleCall(Line line, MachineCenter machine){
        super("", "");
        this.startDateTime = LocalDateTime.now();
        this.callDuration = Duration.ofMinutes(0);
        this.line = line;
        this.machine = machine;
        issueDesc = "";
        solutionDesc = "";
        extReferenceNums = "";
    }

    public TroubleCall(LocalDateTime startDateTime, Line line, MachineCenter machine) {
        super();
        this.startDateTime = startDateTime;
        this.line = line;
        this.machine = machine;
        issueDesc = "";
        solutionDesc = "";
        extReferenceNums = "";
    }

    protected TroubleCall(Parcel in) {
        issueDesc = in.readString();
        solutionDesc = in.readString();
        extReferenceNums = in.readString();
    }

    public static final Creator<TroubleCall> CREATOR = new Creator<TroubleCall>() {
        @Override
        public TroubleCall createFromParcel(Parcel in) {
            return new TroubleCall(in);
        }

        @Override
        public TroubleCall[] newArray(int size) {
            return new TroubleCall[size];
        }
    };

    // returns the line on which the trouble call occured.
    public Line getLine() {
        return line;
    }

    // sets the line where the trouble call occured if it is not null.
    public boolean setLine(Line line) {
        if(line == null)
            return false;
        this.line = line;
        return true;
    }

    // returns the machine on which the trouble call was for.
    public MachineCenter getMachine() {
        return machine;
    }

    public boolean setMachine(MachineCenter machine) {
        if(machine == null)
            return false;
        this.machine = machine;
        return true;
    }

    // FOR FUTURE functionality returns duration of call in minutes.
    public long getCallDuration() {
        return callDuration.toMinutes();
    }

    // FOR FUTURE functionality sets the duration of the call.
    public boolean setCallDuration(long callDuration) {
        if(callDuration <= 0)
            return false;
        this.callDuration = Duration.ofMinutes(callDuration);
        return true;
    }

    // returns the description of the issue for which the trouble call was for.
    public String getIssueDesc() {
        return issueDesc;
    }

    // sets the issues for which the trouble call was for.
    public boolean setIssueDesc(String issueDesc) {
        if(issueDesc == null)
            return false;
        this.issueDesc = issueDesc;
        return true;
    }

    // returns the solution to the trouble call.
    public String getSolutionDesc() {
        return solutionDesc;
    }

    // sets the solution description
    public boolean setSolutionDesc(String solutionDesc) {
        if(solutionDesc == null)
            return false;
        this.solutionDesc = solutionDesc;
        return true;
    }

    // returns external reference information
    public String getExtReferenceNums() {
        return extReferenceNums;
    }

    public boolean setExtReferenceNums(String extReferenceNums) {
        if(extReferenceNums == null)
            return false;
        this.extReferenceNums = extReferenceNums;
        return true;
    }

    public String getCallDesc(){
//        if(line != null && machine != null)
//            return this.getDescription() + this.getComment();
        return "Line " + line.getLineNumber() + " " + machine.getDescription();
    }

    @Override
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    @Override
    public boolean setStartDateTime(LocalDateTime dateTime) {
        if (dateTime == null)
            return false;
        this.startDateTime = dateTime;
        return true;
    }

    @Override
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    @Override
    public boolean setEndDateTime(LocalDateTime dateTime) {
        if(dateTime == null)
            return false;
        this.endDateTime = dateTime;
        return true;
    }

    @Override
    public long getDurationMinutes() {
        return callDuration.toMinutes();
    }

    @Override
    public boolean createDateTimeStamp() {
        return false;
    }

    @Override
    public String toString() {
        return "TroubleCall{" +
                "Start Time: " + startDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)) +
                ", line " + line.getLineNumber() +
                ", " + machine.getDescription() +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(issueDesc);
        dest.writeString(solutionDesc);
        dest.writeString(extReferenceNums);
    }

    @Override
    public String itemDesc() {
        return "Start Time: " + startDateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)) + "\n" +
               "End Time: " + endDateTime.format(DateTimeFormatter.ofLocalizedTime((FormatStyle.SHORT))) + "\n" +
                "Line " + line.getLineNumber() + ", " + machine.itemDesc() + "\n\nIssue:\n" + issueDesc +
                "\n\nSolution:\n" + solutionDesc + "\n\nExternal Reference Numbers:\n" + extReferenceNums;
    }
}
