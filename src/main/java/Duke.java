import java.io.*;
import java.util.*;
import java.awt.*;
import java.io.PrintStream;

public class Duke {

    private static ArrayList<Task> messages = new ArrayList<Task>();
    private static String formatLine = "    ____________________________________________________________";

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        printWelcome(); //prints the welcome message
        readFiles(); //reads in previous file input
        Scanner s = new Scanner(System.in); //Scans in input from cmd line
        while (true) {
            String a = s.nextLine(); //reads in string if there is any
            switch(a) {
                case "bye" :
                    printGoodbye(); //prints the goodbye message
                    updateDatabase(); //updates the database of to-do list
                    return;
                case "list" :
                    printList();
                    break;
                default :
                    if (a.contains("done ")) {
                        Integer val = Integer.parseInt(a.replaceAll("[\\D]", ""));
                        val --;
                        Task atHand = messages.get(val);
                        atHand.setDone();
                        messages.set(val, atHand);
                        printMarkedAsDone(val);
                    }
                    else {
                        System.out.println(formatLine + "\n\tadded: " + a + "\n" + formatLine);
                        Task newTask = new Task(a);
                        messages.add(newTask);
                    }
            }
        }

    }



    /**
     *
     *  Important functions fundamental to the program IO.
     *
     */
    private static void readFiles() throws FileNotFoundException {
        FileReader myTasks = new FileReader("src/duketask.txt");
        Scanner s = new Scanner(myTasks);
        while (s.hasNextLine()) {
            int isDone = s.nextInt();
            String input = s.nextLine();
            input = input.trim();
            Task curr = new Task(input);
            if (isDone == 1) {
                curr.setDone();
            }
            messages.add(curr);
        }
        s.close();
    }

    private static void updateDatabase() {
        try (FileWriter out = new FileWriter("src/duketask.txt", false)) {
            for (Task i : messages) {
                if (i.checkDone())
                    out.write("1 ");
                else
                    out.write("0 ");

                out.write(i.getDescription());

                out.write("\n");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Unimportant functions that are only for UX purposes.
     *
     * 1) Printing welcome message upon start of program
     * 2) Printing of goodbye message upon end of program
     * 3) Prints the list of tasks
     */
    public static void printWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo + "\n\n");
        System.out.println("    ____________________________________________________________\n" +
                "     Hello! I'm Duke\n" +
                "     What can I do for you?\n" +
                "    ____________________________________________________________");
    }

    private static void printGoodbye() {
        System.out.println("    ____________________________________________________________\n" +
                "     Bye. Hope to see you again soon!\n" +
                "    ____________________________________________________________");
    }

    private static void printList() throws UnsupportedEncodingException {
        int i = 1;
        PrintStream out = new PrintStream(System.out, true, "UTF-8");
        out.println(formatLine);
        for (Task x : messages) {
            out.println("\t" + i + ".[" + x.getStatusIcon() + "] " + x.getDescription());
            i++;
        }
        System.out.println(formatLine);
    }

    private static void printMarkedAsDone(Integer val) throws UnsupportedEncodingException {
        PrintStream out = new PrintStream(System.out, true, "UTF-8");
    out.println(formatLine + "\n" +
            "\tNice! I've marked this task as done: \n\t[" + messages.get(val).getStatusIcon() + "] " + messages.get(val).getDescription() + "\n" + formatLine);
    }

}
