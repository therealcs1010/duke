import Classes.Task;

import java.io.PrintStream;
import java.util.ArrayList;

public class Printer {
    Printer(PrintStream out, ArrayList<Task> messages, String a) {
        switch (a) {
            case "welcome" :
                out.println("    ____________________________________________________________\n" +
                        "     Hello! I'm Duke\n" +
                        "     What can I do for you?");
                break;
            case "bye" :
                out.println("    ____________________________________________________________\n" +
                        "     Bye. Hope to see you again soon!\n" +
                        "    ____________________________________________________________");
                break;
            case "list" :
                int i = 1;
                for (Task x : messages) {
                    out.println("\t" + i + "." + x.toString());
                    i++;
                }
                break;

        }
    }
    Printer(PrintStream out, ArrayList<Task> messages, int val) {
        try {
            Task atHand = messages.get(val);
            out.println("\tNice! I've marked this task as done: \n\t" + atHand.toString());
        } catch (Exception e) {
            new DukeException(e);
        }
    }
    Printer(PrintStream out, ArrayList<Task> messages, Task curr, String a) {
        switch(a) {
            case "added" :
                out.print("\tGot it. I've added this task:\n\t" + curr.toString());
                out.println("\n"
                        + "\tNow you have " + messages.size() + " tasks in the list.");
                break;
            case "deleted" :
                out.println("\tNoted. I've removed this task :\n\t" + curr.toString() + "\n\tNow you have " + messages.size() + " tasks in the list");
                break;
        }
    }
}
