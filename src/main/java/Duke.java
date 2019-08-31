import Classes.Deadline;
import Classes.Event;
import Classes.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.PrintStream;

public class Duke {

    private static ArrayList<Task> messages = new ArrayList<>();
    private static PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
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
        new Printer(out, messages, "welcome"); //prints the welcome message
        readFiles(); //reads in previous file input
        Scanner s = new Scanner(System.in); //Scans in input from cmd line
        while (true) {
            String formatLine = "    ____________________________________________________________";
            out.println(formatLine);

            String action = s.next(); //reads in string if there is any
            if (action.equals("bye")) {
                updateDatabase();
                new Printer(out, messages, "bye");
                s.close();
                return;
            }

            out.println(formatLine);

            decideAction(action, s);

        }

    }


    private static void decideAction(String a, Scanner s) throws UnsupportedEncodingException {
        Actions currentAvailableActions = new Actions();

        if (!currentAvailableActions.available.contains(a)) {
            out.println("\tOOPS!!! I'm sorry, but I don't know what that means.");
            s.nextLine();
            return;
        }

        int val;

        switch(a) {
            case "list":
                new Printer(out, messages, "list");
                break;

            case "done":
                val = s.nextInt();
                val--;
                Task atHand = messages.get(val);
                atHand.setDone();
                messages.set(val, atHand);
                new Printer(out, messages, val);
                break;

            case "delete":
                val = s.nextInt();
                val --;

                try {
                    Task curr = messages.get(val);
                    messages.remove(val);
                    new Printer(out, messages, curr,"delete");
                } catch (Exception e) {
                    new DukeException(e);
                }
                break;
            default:
                createClass(s, a);
                break;
        }
    }


    /**
     *
     *  Important functions fundamental to the program IO.
     *
     */

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
                new Printer(out, messages, newTask, "added");
                break;

            case "deadline" :
                try {
                    arr = item.split("/by ");
                    arr[0] = arr[0].trim();
                    Deadline newDeadline = new Deadline(arr[0], "D", arr[1]);
                    messages.add(newDeadline);
                    new Printer(out, messages, newDeadline, "added");
                } catch (Exception e) {
                    new DukeException(e);
                }
                break;

            case "event" :
                try {
                    arr = item.split("/at ");
                    arr[0] = arr[0].trim();
                    Event newEvent = new Event(arr[0], "E", arr[1]);
                    messages.add(newEvent);
                    new Printer(out, messages, newEvent, "added");
                } catch (Exception e) {
                    new DukeException(e);
                }
                break;
        }
    }

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
            System.out.println("\tUnable to store events in database");
        }
    }


}
