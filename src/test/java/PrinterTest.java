import Classes.TaskTest;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PrinterTest {
    private static PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    public PrinterTest(ArrayList<TaskTest> messages, String a) {
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
                for (TaskTest x : messages) {
                    out.println("\t" + i + "." + x.toString());
                    i++;
                }
                break;

        }
    }

}
