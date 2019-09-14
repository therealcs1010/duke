import Classes.Deadline;
import Classes.Event;
import Classes.Task;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task> {

    String activity;

    TaskList(ArrayList<Task> list) {
        super.addAll(list);
    }

    TaskList() {
        super(new ArrayList<Task>());
    }

    public String start(Actions currentAction, String activity) {
        this.activity = activity;
        switch(currentAction) {
            case LIST:
                return print();
            case TODO:
                return createTodo();
            case EVENT:
                return createEvent();
            case DEADLINE:
                return createDeadline();
            case DONE:
                return setItem();
            case FIND:
                return findItem();
            case DELETE:
                return deleteItem();
            case BYE:
                return printBye();
            default:
                return "";
        }
    }

    private String printBye() {
        return "     Bye. Hope to see you again soon!";
    }

    private String print() {
        int i = 1;
        String toReturn = "";
        for (Task x : super.subList(0,super.size())) {
            toReturn += "\t" + i + "." + x.toString() + "\n";
            i++;
        }
        return toReturn;
    }

    private String createDeadline() {
        try {
            String[] arr = activity.split("/by ");
            Deadline newDeadline = new Deadline(arr[0], "D", arr[1]);
            super.add(newDeadline);
            return "\tNew task added:\n\t" + newDeadline.toString();
        } catch (ArrayIndexOutOfBoundsException e) {
            return "\tOOPS!!! The format is unreadable.\n" +
                    "\tPlease follow this format:\n" +
                    "\tdeadline [Event] /by [Deadline]";
        }

    }

    private String createEvent() {
        try {
            String[] arr = activity.split("/at ");
            arr[0] = arr[0].trim();
            Event newEvent = new Event(arr[0], "E", arr[1]);
            super.add(newEvent);
            return "\tNew task added:\n\t" + newEvent.toString();
        } catch (ArrayIndexOutOfBoundsException e) {
            return "\tOOPS!!! The format is unreadable.\n" +
                    "\tPlease follow this format:\n" +
                    "\tevent [Event] /at [Time period]";
        }

    }

    private String createTodo() {
        Task newTask = new Task(activity, "T");
        super.add(newTask);
        return "\tNew task added:\n\t" + newTask.toString();
    }


    private String setItem() {
        int val = Integer.parseInt(activity);
        val --;
        Task atHand = super.get(val);
        atHand.setDone();
        super.set(val, atHand);
        return "\tNoted. I've set task as done:\n\t" + atHand.toString() ;
    }

    private String deleteItem() {
        int val = Integer.parseInt(activity);
        val --;
        Task x = super.get(val);
        super.remove(val);
        return "\tNoted. I've removed this task:\n\t" + x.toString() + "\n\tNow you have " + super.size() + " tasks in your list";
    }

    private String findItem() {
        ArrayList<String> found = new ArrayList<String>();
        for (int x = 0; x < super.size(); x++) {
            Task i = super.get(x);
            String y = i.getDescription();
            if (y.contains(activity)) {
                found.add(i.toString());
            }
        }

        String result ="\tHere are the matching tasks in your list.\n";
        int start = 1;
        for (String i : found) {
            result += "\t" + start + "." + i + "\n";
            start ++;
        }
        return result;
    }


}
