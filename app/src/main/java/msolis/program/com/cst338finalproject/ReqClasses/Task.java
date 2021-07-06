package msolis.program.com.cst338finalproject.ReqClasses;

import java.time.*;

// a short task class like something on a todo list.
public abstract class Task {
    private String description;
    private String comment;
    private LocalDateTime createdDateTime;

    Task(){
        description = "";
        comment = "";
        createdDateTime = LocalDateTime.now();
    }

    Task(String description, String comment){
        this.description = description;
        this.comment = comment;
        createdDateTime = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public boolean setDescription(String description) {
        if(description == null)
            return false;
        this.description = description;
        return true;
    }

    public String getComment() {
        return comment;
    }

    public boolean setComment(String comment) {
        if(comment == null)
            return false;
        this.comment = comment;
        return true;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public abstract LocalDateTime getStartDateTime();

    public abstract boolean setStartDateTime(LocalDateTime dateTime);

    public abstract LocalDateTime getEndDateTime();

    public abstract boolean setEndDateTime(LocalDateTime dateTime);
}
