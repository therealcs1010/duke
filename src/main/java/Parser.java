import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * This class handles the IO related to duke. It utilises the buffered reader to read in input, and uses an
 * printwriter to handle UTF-8 characters, in the case of the tick and crosses.
 */
class Parser extends PrintWriter {
    private Scanner s = new Scanner(System.in);

    Parser() {
        super(new OutputStreamWriter(System.out, StandardCharsets.UTF_8), true);
    }

    int getInt() {
        int val = s.nextInt();
        s.nextLine();
        return val;
    }
    String getLine() {
        String val = s.nextLine();
        return val;
    }
    String getWord() {
        String val = s.next();
        s.nextLine();
        return val;
    }

    void closeParser() {
        s.close();
    }

    void printHello() {
        super.println("     Hello! I'm Duke\n" +
                "     What can I do for you?");
    }
    void printLine() {
        super.println("    ____________________________________________________________");
    }

    public void showLoadingError() {
        super.println("\tNew List will be created");
    }

    public void showLoaded() {
        super.println("\tList created");
    }

    public void promptAction() {
        super.println("\tWhat would you like to do?");
    }

    public void printResult(String result) {
        super.println(result);
    }
}
