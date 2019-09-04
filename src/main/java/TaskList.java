import Classes.Deadline;
import Classes.Event;
import Classes.Task;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task> {

    private static Parser io;

    static {
        io = new Parser(System.in);
    }

    TaskList(ArrayList<Task> list) {
        super.addAll(list);
    }

    TaskList() {
        super(new ArrayList<Task>());
    }

    public void start(Actions currentAction) {
        switch(currentAction) {
            case LIST:
                print();
                break;
            case TODO:
                createTodo();
                break;
            case EVENT:
                createEvent();
                break;
            case DEADLINE:
                createDeadline();
                break;
            case DONE:
                setItem();
                break;
            case FIND:
                findItem();
                break;
            case DELETE:
                deleteItem();
                break;
            case ERROR:
                break;
        }
    }

    private void print() {
        int i = 1;
        for (Task x : super.subList(0,super.size())) {
            io.println("\t" + i + "." + x.toString());
            i++;
        }
    }

    private void createDeadline() {
        io.println("\tCreating Deadline...\n\tPlease enter activity to be done followed by /by .");
        String item;
        String [] arr;
        item = io.getLine();
        try {
            arr = item.split("/by ");
            arr[0] = arr[0].trim();
            Deadline newDeadline = new Deadline(arr[0], "D", arr[1]);
            super.add(newDeadline);
            io.println("\tNew task added:\n\t" + newDeadline.toString());
        } catch (ArrayIndexOutOfBoundsException e) {
            io.println("\tOOPS!!! The format is unreadable.\n" +
                    "\tPlease follow this format:\n" +
                    "\tdeadline [Event] /by [Deadline]");
        }

    }

    private void createEvent() {
        io.println("\tCreating Event...\n\tPlease enter activity to be done followed by /at .");
        String item;
        String [] arr;
        item = io.getLine();
        try {
            arr = item.split("/at ");
            arr[0] = arr[0].trim();
            Event newEvent = new Event(arr[0], "E", arr[1]);
            super.add(newEvent);
            io.println("\tNew task added:\n\t" + newEvent.toString());
        } catch (ArrayIndexOutOfBoundsException e) {
            io.println("\tOOPS!!! The format is unreadable.\n" +
                    "\tPlease follow this format:\n" +
                    "\tevent [Event] /at [Time period]");
        }

    }

    private void createTodo() {
        io.println("\tCreating to do...\n\tPlease enter activity to be done.");
        String item;
        String [] arr;
        item = io.getLine();
        item = item.trim();
        Task newTask = new Task(item, "T");
        super.add(newTask);
        io.println("\tNew task added:\n\t" + newTask.toString());
    }


    private void setItem() {
        io.println("\tWhich item no. do you want to set as done? : ");
        int val = io.getInt();
        val--;
        Task atHand = super.get(val);
        atHand.setDone();
        super.set(val, atHand);
        io.println("\tNoted. I've set task as done:\n\t" + atHand.toString());
    }

    private void deleteItem() {
        io.println("\tWhich item no. do you want to delete? : ");
        int val = io.getInt();
        val --;
        Task x = super.get(val);
        super.remove(val);
        io.println("\tNoted. I've removed this task:\n\t" + x.toString() + "\n\tNow you have " + super.size() + " tasks in your list");
    }

    private void findItem() {
        io.println("\tWhat word are you specifically looking for : ");
        String item = io.getWord();
        ArrayList<String> found = new ArrayList<String>();
        for (int x = 0; x < super.size(); x++) {
            Task i = super.get(x);
            String y = i.getDescription();
            if (y.contains(item)) {
                found.add(i.toString());
            }
        }

        io.println("\tHere are the matching tasks in your list.\n");
        int start = 1;
        for (String i : found) {
            io.println("\t" + start + "." + i);
            start ++;
        }
    }


}
