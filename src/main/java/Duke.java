import Classes.Deadline;
import Classes.Event;
import Classes.Task;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.*;

/**
 * This is the main code which will make use of the different classes to work together. It reads in duketastk.txt from the user
 * directory before running it in duke. Duke then processes input accordingly until "bye" is read. It then prints out "Bye" before
 * terminating.
 */
public class Duke {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     *
     * @param filePath filepath is the file location where duketask.txt is located
     * @throws FileNotFoundException in the event that the file isnt found
     *  This function mainly just sets up the different parts of duke that enable it to work together.
     *
     */
    private Duke(String filePath) throws FileNotFoundException {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
            //ui.showLoaded();
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * This takes in an action, then proceeds to pick an action according to the input. This action gets passed into task
     * where something will be done, e.g creating a todo task.
     *
     */
    private void run() {
        Actions action;
        do {
            ui.promptAction();
            action = ui.retrieveData();
            tasks.start(action);
        } while (action != Actions.BYE);
        storage.updateDatabase(tasks);
    }

    public static void main(String[] args) throws IOException {
        String filename = "duketask.txt";
        String workingDir = System.getProperty("user.dir");
        String abs = workingDir + File.separator + filename;
        File file = new File(abs);
        file.createNewFile();
        new Duke(abs).run();
        }

}
