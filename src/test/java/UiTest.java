public class UiTest {

    private ParserTest io;

    private ActionsTest currentAction;

    UiTest() {
        io = new ParserTest(System.in);
    }



    public ActionsTest retrieveData() {
        String a = io.getLine();
        a = a.split(" ")[0];
        try {
            currentAction = ActionsTest.valueOf(a.toUpperCase());
        } catch (IllegalArgumentException e) {
            io.println("\tOOPS!!! I'm sorry, but I don't know what that means.");
            io.getLine();
        }
        return currentAction;


    }

    public void showLoadingError() {
        io.println("\tNew List will be created");
//        io.flush();
    }

    public void showLoaded() {
        io.println("\tList created");
//        io.flush();
    }

    public void promptAction() {
        io.println("\n\tWhat would you like to do?");
//        io.flush();
    }
}
