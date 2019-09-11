import Classes.Deadline;
import Classes.Event;
import Classes.Task;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.*;

public class Duke {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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
