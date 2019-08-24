import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Duke {

    private static ArrayList<String> messages = new ArrayList<String>();
    public static void main(String[] args) throws FileNotFoundException {
        printWelcome();
        FileReader myTasks = new FileReader("src/duketask.txt");
        Scanner s = new Scanner(myTasks);
        while (s.hasNextLine()) {
            String input = s.nextLine();
            System.out.println("\tDuke read : " + input);
            messages.add(input);
        }
        s.close();
        s = new Scanner(System.in);
        while (true) {
            String a = s.nextLine();
            System.out.println(a);
            messages.add(a);
            if (a.equals("bye"))
            {
                updateDatabase();
                return;
            }
        }

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

    public static void printWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
    }
}
