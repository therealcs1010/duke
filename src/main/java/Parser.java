import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * This class handles the IO related to duke. It utilises the buffered reader to read in input, and uses an
 * outputstreamwriter to handle UTF-8 characters, in the case of the tick and crosses.
 */
class Parser extends PrintWriter {
    private Scanner s = new Scanner(System.in);

    public Parser(InputStream i) {
        super(new OutputStreamWriter(
                System.out, StandardCharsets.UTF_8), true);
        r = new BufferedReader(new InputStreamReader(i));
    }


    public int getInt() {
        return Integer.parseInt(nextToken());
    }

    public double getDouble() {
        return Double.parseDouble(nextToken());
    }

    public String getWord() {
        return nextToken();
    }

    public String getLine() {
        return this.s.nextLine();
    }

    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

    private String peekToken() {
        if (token == null)
            try {
                while (st == null || !st.hasMoreTokens()) {
                    line = r.readLine();
                    if (line == null) return null;
                    st = new StringTokenizer(line);
                }
                token = st.nextToken();
            } catch (IOException e) { }
        return token;
    }

    private String nextToken() {
        String ans = peekToken();
        token = null;
        return ans;
    }

}
