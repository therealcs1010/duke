import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DukeTest {
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
    private StorageTest storage;
    private TaskListTest tasks;
    private UiTest ui;

    private DukeTest(String filePath) throws FileNotFoundException {
        ui = new UiTest();
        storage = new StorageTest(filePath);
        try {
            tasks = new TaskListTest(storage.load());
            //ui.showLoaded();
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskListTest();
        }
    }

    private void run() {
        ActionsTest action;
        do {
            ui.promptAction();
            action = ui.retrieveData();
            tasks.start(action);
        } while (action != ActionsTest.BYE);
        storage.updateDatabase(tasks);
    }

    public static void main(String[] args) throws IOException {
        String filename = "duketask.txt";
        String workingDir = System.getProperty("user.dir");
        String abs = workingDir + File.separator + filename;
        File file = new File(abs);
        file.createNewFile();
        new DukeTest(abs).run();
        }

}
