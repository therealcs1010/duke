public class Ui {
    protected Parser io;
    private Actions currentAction;
    private String activity;

    Ui() {
        io = new Parser();
    }

    public void retrieveData() {
        String input = io.getLine();
        String[] arr = input.split(" ", 2);
        try {
            currentAction = Actions.valueOf(arr[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            io.println("\tOOPS!!! I'm sorry, but I don't know what that means.");
        }
        try {
            activity = arr[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            activity = "";
        }

    }
    public Actions returnAction() {
        return this.currentAction;
    }
    public String returnActivity() {
        return this.activity;
    }


    public void printResult(String result) {
        io.printResult(result);
    }
}
