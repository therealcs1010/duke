import Classes.Deadline;
import Classes.Event;
import Classes.Task;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class handles the storing function, mainly in the beginning and at the end of the function. At the beginning, the
 * text file is processed and converted to an arraylist. This arraylist then gets passed to the main duke class. At the end,
 * an arraylist is read and stored as input in the text file.
 */
public class Storage {
    private static ArrayList<Task> messages = new ArrayList<Task>();
    private String filepath;

    /**
     *
     * @param filepath This is the path of the duketask txt file.
     * @throws FileNotFoundException This is in the event where the file is not found and has been deleted.
     * This initialises the filepath as a string, before reading in details such as the tasklist.
     *
     */
    Storage(String filepath) throws FileNotFoundException {
        this.filepath = filepath;
        FileReader myTasks = new FileReader(filepath);
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

    /**
     *
     * @param tasks this is an arraylist consisting of the current values of the user.
     *              This function updates the txt file to be the most current up-to-date arraylist.
     */
    public void updateDatabase(ArrayList<Task> tasks)
    {
        try (FileWriter out = new FileWriter(filepath, false)) {
            for (Task i : tasks) {
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

    public ArrayList<Task> load(){
        return messages;
    }

}
