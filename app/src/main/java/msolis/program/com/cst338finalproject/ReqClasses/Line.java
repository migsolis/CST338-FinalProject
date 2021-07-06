package msolis.program.com.cst338finalproject.ReqClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Line implements Listable, Parcelable {

    private String locationDescription;
    private int lineNumber;
    private String lineDescription;
    private String lineID;
    private ArrayList<MachineCenter> machines = new ArrayList<MachineCenter>();

    public Line(String locationDescription, int lineNumber) {
        this.locationDescription = locationDescription;
        this.lineNumber = lineNumber;
    }

    public Line(String locationDescription, int lineNumber, String lineDescription) {
        this.locationDescription = locationDescription;
        this.lineNumber = lineNumber;
        this.lineDescription = lineDescription;
    }

    protected Line(Parcel in) {
        locationDescription = in.readString();
        lineNumber = in.readInt();
        lineDescription = in.readString();
        lineID = in.readString();
    }

    public static final Creator<Line> CREATOR = new Creator<Line>() {
        @Override
        public Line createFromParcel(Parcel in) {
            return new Line(in);
        }

        @Override
        public Line[] newArray(int size) {
            return new Line[size];
        }
    };

    public MachineCenter getMachine(int index){
        return machines.get(index);
    }

    public boolean addMachine(MachineCenter newMach){
        if(newMach == null)
            return false;
        machines.add(newMach);
        return true;
    }

    public ArrayList<MachineCenter> getMachines(){
        return new ArrayList<MachineCenter>(machines);
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public boolean setLocationDescription(String locationDescription) {
        if (locationDescription == null || locationDescription.equals(""))
            return false;
        this.locationDescription = locationDescription;
        return true;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public boolean setLineNumber(int lineNumber) {
        if (lineNumber <= 0)
            return false;
        this.lineNumber = lineNumber;
        return true;
    }

    public String getLineDescription() {
        return lineDescription;
    }

    public boolean setLineDescription(String lineDescription) {
        if (lineDescription == null || lineDescription.equals(""))
            return false;
        this.lineDescription = lineDescription;
        return true;
    }

    public String getLineID() {
        return lineID;
    }

    public boolean setLineID(String lineID) {
        if (lineID == null || lineID.equals(""))
            return false;
        this.lineID = lineID;
        return true;
    }

    @Override
    public String itemDesc() {
        return "Line " + lineNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(locationDescription);
        dest.writeInt(lineNumber);
        dest.writeString(lineDescription);
        dest.writeString(lineID);
    }
}
