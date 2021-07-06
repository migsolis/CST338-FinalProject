package msolis.program.com.cst338finalproject.ReqClasses;

import java.util.ArrayList;

import msolis.program.com.cst338finalproject.ReqClasses.Line;

// stores a location description or name of a plant and a list of all the lines in that plant.
public class Location {

    private String locationDescription;
    private ArrayList<Line> lines = new ArrayList<Line>();

    public Location(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public void setLines(ArrayList<Line> lines) {
        this.lines = lines;
    }

    public boolean addLine(Line line) {
        if(line == null)
            return false;
        lines.add(line);
        return true;
    }
}
