package Classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Deadline extends Task {

    private String by;

    private Date myDeadline = new Date();

    public Deadline(String description, String type, String by) {
        super(description, type);
        this.by = by;
        try {
            setDate(this.by);
        } catch (ParseException e) {

        }
    }

    @Override
    public String toString() {
        return "[D][" + super.getStatusIcon() + "] " + super.getDescription() + " (by: " + this.by + ")";
    }

    public String doneBy() {
        return this.by;
    }

    private void setDate(String a) throws ParseException {
      SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy HHmm");
      this.myDeadline = f1.parse(this.by);
    }

}
