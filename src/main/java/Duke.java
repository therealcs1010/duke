import Classes.Deadline;
import Classes.Event;
import Classes.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {

    private static ArrayList<Task> messages = new ArrayList<Task>();
    private static PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    private static Scanner s = new Scanner(System.in); //Scans in input from cmd line
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
        new Printer(messages, "welcome"); //prints the welcome message
        readFiles(); //reads in previous file input

        while (true) {
            String formatLine = "    ____________________________________________________________";
            System.out.println(formatLine);
            String action = s.next(); //reads in string if there is any
            if (action.equals("bye")) {
                updateDatabase();
                new Printer(messages, "bye");
                s.close();
                return;
            }
            System.out.println(formatLine);
            decideAction(action);

        }

    }

    private static void decideAction(String a) throws UnsupportedEncodingException {
        Actions currentAction;
        try {
            currentAction = Actions.valueOf(a.toUpperCase());
        } catch (IllegalArgumentException e) {
            out.println("\tOOPS!!! I'm sorry, but I don't know what that means.");
            s.nextLine();
            return;
        }
        switch(currentAction) {
            case LIST:
                new Printer(messages, "list");
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

        }
    }

    private static void createDeadline() {
        String item;
        String [] arr;
        item = s.nextLine();
        try {
            arr = item.split("/by ");
            arr[0] = arr[0].trim();
            Deadline newDeadline = new Deadline(arr[0], "D", arr[1]);
            messages.add(newDeadline);
            // printAdded(newDeadline);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("\tOOPS!!! The format is unreadable.\n" +
                    "\tPlease follow this format:\n" +
                    "\tdeadline [Event] /by [Deadline]");
        }

    }

    private static void createEvent() {
        String item;
        String [] arr;
        item = s.nextLine();
        try {
            arr = item.split("/at ");
            arr[0] = arr[0].trim();
            Event newEvent = new Event(arr[0], "E", arr[1]);
            messages.add(newEvent);
            //  new Printer(out, messages, newEvent);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("\tOOPS!!! The format is unreadable.\n" +
                    "\tPlease follow this format:\n" +
                    "\tevent [Event] /at [Time period]");
        }

    }

    private static void createTodo() {
        String item;
        String [] arr;
        item = s.nextLine();
        item = item.strip();
        Task newTask = new Task(item, "T");
        messages.add(newTask);
        out.println("\tNew task added:\n\t" + newTask.toString());
    }

    private static void setItem() {
        int val = s.nextInt();
        val--;
        Task atHand = messages.get(val);
        atHand.setDone();
        messages.set(val, atHand);
        out.println("\tNoted. I've set task as done:\n\t" + atHand.toString());
    }

    private static void deleteItem() {
        int val = s.nextInt();
        val --;
        Task x = messages.get(val);
        messages.remove(val);
        out.println("\tNoted. I've removed this task:\n\t" + x.toString() + "\n\tNow you have " + messages.size() + " tasks in your list");
    }

    private static void findItem() {
        String item = s.next();
        ArrayList<String> found = new ArrayList<String>();
        for (Task i : messages) {
            String x = i.getDescription();
            if (x.contains(item)) {
                found.add(i.toString());
            }
        }

        out.println("\tHere are the matching tasks in your list.\n");
        int start = 1;
        for (String i : found) {
            out.println("\t" + start + "." + i);
            start ++;
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


}
