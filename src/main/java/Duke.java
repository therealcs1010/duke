import Classes.Deadline;
import Classes.Event;
import Classes.Task;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.*;

public class Duke {
//    @Override
//    public void start(Stage stage) {
//        Label helloWorld = new Label("Hello World!");// Creating a new Label control
//        helloWorld.accessibleTextProperty
//        Scene scene = new Scene(helloWorld); // Setting the scene to be our Label
//        Scene scene2 = new Scene(new Label("WOWOWOWO"));
//        stage.setScene(scene); // Setting the stage to show our screen
//        stage.show();// Render the stage.
//        stage.setScene(scene2);
//        stage.show();
//    }
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

    public static void main(String[] args) throws FileNotFoundException {
        new Duke("src/duketask.txt").run();
        }

}
