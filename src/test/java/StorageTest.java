import Classes.DeadlineTest;
import Classes.EventTest;
import Classes.TaskTest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class StorageTest {
    private static ArrayList<TaskTest> messages = new ArrayList<TaskTest>();
    private String filepath;

    StorageTest(String filepath) throws FileNotFoundException {
        this.filepath = filepath;
        FileReader myTasks = new FileReader(filepath);
        Scanner s = new Scanner(myTasks);
        while (s.hasNextLine()) {
            String input = s.nextLine();
            String [] arr = input.split("/");
            switch(arr[0]) {
                case "T" :
                    TaskTest a = new TaskTest(arr[2], "T");
                    if (arr[1].equals("1")){
                        a.setDone();
                    }
                    messages.add(a);
                    break;
                case "D" :
                    DeadlineTest b = new DeadlineTest(arr[2], "D", arr[3]);
                    if (arr[1].equals("1")) {
                        b.setDone();
                    }
                    messages.add(b);
                    break;
                case "E" :
                    EventTest c = new EventTest(arr[2], "E", arr[3]);
                    if (arr[1].equals("1")) {
                        c.setDone();
                    }
                    messages.add(c);
            }
        }
        s.close();
    }

    public void updateDatabase(ArrayList<TaskTest> tasks)
    {
        try (FileWriter out = new FileWriter(filepath, false)) {
            for (TaskTest i : tasks) {
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
                    DeadlineTest temp = (DeadlineTest) i;
                    out.write("/" + temp.doneBy());
                }
                else if (i.checkType().equals("E")) {
                    EventTest temp = (EventTest) i;
                    out.write("/" + temp.doneAt());
                }

                out.write("\n");
            }
        } catch (IOException e) {
            System.out.println("\tUnable to store events in database");
        }
    }

    public ArrayList<TaskTest> load(){
        return messages;
    }

}
