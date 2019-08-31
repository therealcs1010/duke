class DukeException extends Exception {

    private Exception e = new Exception();
    DukeException(Exception e) {
        this.e = e;
        if (this.e instanceof IndexOutOfBoundsException) {
            System.out.println("OOPS!! Accessing an element that is out of bounds!");
        }
        if (this.e instanceof ArrayIndexOutOfBoundsException) {
            System.out.println("\tOOPS!!! The format is unreadable.\n");
        }
    }
}
