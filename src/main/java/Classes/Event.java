package Classes;

public class Event extends Task {

    String at;
    public Event(String description, String type, String at) {
        super(description, type);
        this.at = at;
    }

    @Override
    public String toString() {
        return "[E][" + super.getStatusIcon() + "] " + super.getDescription() + " (at: " + this.at + ")";
    }
    public String doneAt() {
        return this.at;
    }

}
