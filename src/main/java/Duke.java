import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Duke {

    private static ArrayList<String> messages = new ArrayList<String>();
    private static String formatLine = "    ____________________________________________________________";

    public static void main(String[] args) throws FileNotFoundException {
        printWelcome(); //prints the welcome message
        readFiles(); //reads in previous file input
        Scanner s = new Scanner(System.in); //Scans in input from cmd line
        while (true) {
            String a = s.nextLine(); //reads in string if there is any
            if (a.equals("bye"))
            {
                printGoodbye(); //prints the goodbye message
                updateDatabase(); //updates the database of to-do list
                return;
            }
            else {
                System.out.println(formatLine + "\n\t" + a + "\n" + formatLine);
                messages.add(a);
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
            String input = s.nextLine();
            System.out.println("\tDuke read : " + input);
            messages.add(input);
        }
        s.close();
    }

    private static void updateDatabase() {
        try (FileWriter out = new FileWriter("src/duketask.txt", false)) {
            for (String i : messages) {
                out.write(i);
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
     *
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
}
