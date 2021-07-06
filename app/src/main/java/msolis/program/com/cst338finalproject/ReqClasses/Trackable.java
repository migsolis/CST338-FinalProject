package msolis.program.com.cst338finalproject.ReqClasses;

// This interface will be used in tasks that need to keep track of time work, this will work with
// multithreading to run a timer in the background that can be paused and restarted.
public interface Trackable {

    public abstract long getDurationMinutes();

    public abstract boolean createDateTimeStamp();

}
