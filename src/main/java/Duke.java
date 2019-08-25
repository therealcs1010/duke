import Classes.Deadline;
import Classes.Event;
import Classes.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.PrintStream;

public class Duke {

    static ArrayList<Task> messages = new ArrayList<Task>();
    private static String formatLine = "    ____________________________________________________________";

    /**
     *
     * @param args
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     *
     * This main method prints the welcome message, then reads in the input from the file to determine the previous inputs,
     * The scanner class then reads in any input on the terminal before determining action.
     * Upon receiving "goodbye", it will update the file before terminating the program.
     *
     *
     */
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        printWelcome(); //prints the welcome message
        readFiles(); //reads in previous file input
        Scanner s = new Scanner(System.in); //Scans in input from cmd line
        while (true) {
            System.out.println(formatLine);
            String action = s.next(); //reads in string if there is any
            if (action.equals("bye")) {
                updateDatabase();
                printGoodbye();
                s.close();
                return;
            }
            System.out.println(formatLine);
            decideAction(action, s);

        }

    }

    private static void decideAction(String a, Scanner s) throws UnsupportedEncodingException {
        Actions currentAvailableActions = new Actions();
        PrintStream out = new PrintStream(System.out, false, StandardCharsets.UTF_8);

        if (!currentAvailableActions.available.contains(a)) {
            out.println("\tOOPS!!! I'm sorry, but I don't know what that means.");
            s.nextLine();
            return;
        }
        switch(a) {
            case "list":
                printList();
                break;

            case "done":
                int val = s.nextInt();
                val--;
                Task atHand = messages.get(val);
                atHand.setDone();
                messages.set(val, atHand);
                printMarkedAsDone(val);
                break;

            default:
                createClass(s, a);
                break;
        }
    }

    private static void createClass(Scanner s, String a) throws UnsupportedEncodingException {

        String item;
        String [] arr;
        item = s.nextLine();
        if (item.isBlank()) {
            System.out.println("\tOOPS!!! The description cannot be empty.");
            return;
        }
        switch(a) {
                case "todo" :
                    item = item.strip();
                    Task newTask = new Task(item, "T");
                    messages.add(newTask);
                    printAdded(newTask);
                    break;

                case "deadline" :
                    try {
                        arr = item.split("/by ");
                        arr[0] = arr[0].trim();
                        Deadline newDeadline = new Deadline(arr[0], "D", arr[1]);
                        messages.add(newDeadline);
                        printAdded(newDeadline);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("\tOOPS!!! The format is unreadable.\n" +
                                "\tPlease follow this format:\n" +
                                "\tdeadline [Event] /by [Deadline]");
                    }
                    break;

                case "event" :
                    try {
                        arr = item.split("/at ");
                        arr[0] = arr[0].trim();
                        Event newEvent = new Event(arr[0], "E", arr[1]);
                        messages.add(newEvent);
                        printAdded(newEvent);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("\tOOPS!!! The format is unreadable.\n" +
                                "\tPlease follow this format:\n" +
                                "\tevent [Event] /at [Time period]");
                    }
                    break;
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
            String input = s.nextLine();
            String [] arr = input.split("/");
            switch(arr[0]) {
                case "T" :
                    Task a = new Task(arr[2], "T");
                    if (arr[1].equals("1")){
                        a.setDone();
                    }
                    messages.add(a);
                    break;
                case "D" :
                    Deadline b = new Deadline(arr[2], "D", arr[3]);
                    if (arr[1].equals("1")) {
                        b.setDone();
                    }
                    messages.add(b);
                    break;
                case "E" :
                    Event c = new Event(arr[2], "E", arr[3]);
                    if (arr[1].equals("1")) {
                        c.setDone();
                    }
                    messages.add(c);
            }
        }
        s.close();
    }

    private static void updateDatabase() {
        try (FileWriter out = new FileWriter("src/duketask.txt", false)) {
            for (Task i : messages) {
                switch (i.checkType()) {
                    case "T" :
                        out.write("T/");
                        break;
                    case "D" :
                        out.write("D/");
                        break;
                    case "E" :
                        out.write("E/");
                        break;
                } //Checks the type of task

                if (i.checkDone())
                    out.write("1/");
                else
                    out.write("0/");

                out.write(i.getDescription());

                if (i.checkType().equals("D")) {
                    Deadline temp = (Deadline) i;
                    out.write("/" + temp.doneBy());
                }
                else if (i.checkType().equals("E")) {
                    Event temp = (Event) i;
                    out.write("/" + temp.doneAt());
                }

                out.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("\tUnable to store events in database");
        }
    }







    /**
     * Unimportant functions that are only for UX purposes.
     *
     * 1) Printing welcome message upon start of program
     * 2) Printing of goodbye message upon end of program
     * 3) Prints the list of tasks
     *
     */
    private static void printWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo + "\n\n");
        System.out.println("    ____________________________________________________________\n" +
                "     Hello! I'm Duke\n" +
                "     What can I do for you?");
    }

    private static void printGoodbye() {
        System.out.println("    ____________________________________________________________\n" +
                "     Bye. Hope to see you again soon!\n" +
                "    ____________________________________________________________");
    }

    private static void printList() throws UnsupportedEncodingException {
        int i = 1;
        PrintStream out = new PrintStream(System.out, true, "UTF-8");
        for (Task x : messages) {
            out.println("\t" + i + "." + x.toString());
            i++;
        }
    }

    private static void printMarkedAsDone(Integer val) throws UnsupportedEncodingException {
        PrintStream out = new PrintStream(System.out, true, "UTF-8");
        Task atHand = messages.get(val);
        out.println("\tNice! I've marked this task as done: \n\t" + atHand.toString());
    }
    private static void printAdded(Task newTask) throws UnsupportedEncodingException {
        PrintStream out = new PrintStream(System.out, true, "UTF-8");
        out.print("\tGot it. I've added this task:\n\t" + newTask.toString());
        out.println("\n"
                + "\tNow you have " + messages.size() + " tasks in the list.");
    }

}
