import Classes.Task;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Printer {
    private static PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    public Printer(ArrayList<Task> messages, String a) {
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

}
