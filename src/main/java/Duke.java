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
    private Parser parse;
    /**
     * Sets up the different Objects required to run duke.
     * @param filePath filepath is the file location where duketask.txt is located
     * @throws FileNotFoundException in the event that the file isnt found
     *
     */
    private Duke(String filePath) throws FileNotFoundException {
        ui = new Ui();
        storage = new Storage(filePath);
        parse = new Parser();
        parse.printLine();
        parse.printHello();

        try {
            tasks = new TaskList(storage.load());
        } catch (Exception e) {
            ui.io.showLoadingError();
            tasks = new TaskList();
        }
    }

    private void run() {
        Actions action;
        do {
            parse.printLine();
            ui.retrieveData();
            action = ui.returnAction();
            String fullCommand = ui.returnActivity();
            String result = tasks.start(action, fullCommand);
            parse.printLine();
            parse.printResult(result);
        } while (action != Actions.BYE);
        storage.updateDatabase(tasks);
        parse.close();
    }

    public static void main(String[] args) throws IOException {
        String workingDir = System.getProperty("user.dir") + File.separator + "duketask.txt";
        File file = new File(workingDir);
        file.createNewFile();
        new Duke(workingDir).run();
        }

}
