package Classes;

public class Task {

    private String description;
    private boolean isDone;
    private String type;

    public Task(String description, String type) {  //Constructor which sets up the description of the task and sets it to undone
        this.description = description;
        this.type = type;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    public String getDescription() {
        return this.description; //returns the description
    }

    public void setDone() {
        this.isDone = true; //set as done
    }

    public boolean checkDone() {
        return this.isDone; //checks if done.
    }
    public String checkType() {
        return this.type;
    }
    @Override
    public String toString() {
        return "[T][" + this.getStatusIcon() + "] " + this.getDescription();
    }
    //...
}
