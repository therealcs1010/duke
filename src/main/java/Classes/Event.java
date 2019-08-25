package Classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event extends Task {

    String at;
    Date myTime = new Date();
    public Event(String description, String type, String at) {
        super(description, type);
        this.at = at;
        try {
            setDate(this.at);
        } catch (ParseException e) {
        }
    }

    @Override
    public String toString() {
        return "[E][" + super.getStatusIcon() + "] " + super.getDescription() + " (at: " + this.at + ")";
    }
    public String doneAt() {
        return this.at;
    }

    private void setDate(String a) throws ParseException {
        SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy HHmm");
        this.myTime = f1.parse(this.at);
    }

}
