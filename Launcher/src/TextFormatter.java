import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Timer;

public class TextFormatter {
    private TextGraphics tg;
    private int cols;

    public enum PaddingAlignment { //Alignment for where the text will be shown on the terminal screen
        LEFT, CENTER, RIGHT
    }
    public enum TextAlignment { //Maybe I'll do this if we need to float text to the left or right
        LEFT, CENTER, RIGHT
    }

    public TextFormatter(TextGraphics tg, int cols) {
        this.tg = tg;
        this.cols = cols;
    }

    public void printSingle(int row, String text, PaddingAlignment paddingAlignment) {
        int start = switch (paddingAlignment) {
            case LEFT -> 0;
            case CENTER -> ((cols /2) - (text.length() / 2));
            case RIGHT -> cols-text.length();
        };
        tg.putString(start, row, text);
    }

    public void printMulti(int startRow, String text, PaddingAlignment paddingAlignment) {
        String[] lines = text.split("\n");
        int currentLine = startRow;
        for (String line : lines) {
            int start = switch (paddingAlignment) {
                case LEFT -> 0;
                case CENTER -> ((cols /2) - (line.length() / 2));
                case RIGHT -> cols-text.length();
            };
            tg.putString(start, currentLine, line);
            currentLine++;
        }
    }

    public void printMulti(int startRow, List<String> lines, PaddingAlignment paddingAlignment) {
        int currentLine = startRow;
        for (String line : lines) {
            int start = switch (paddingAlignment) {
                case LEFT -> 0;
                case CENTER -> ((cols /2) - (line.length() / 2));
                case RIGHT -> cols - line.length();
            };
            tg.putString(start, currentLine, line);
            currentLine++;
        }
    }

    public void printMulti(int startRow, FileReader file, PaddingAlignment paddingAlignment) throws IOException {
        int currentLine = startRow;
        try (BufferedReader br = new BufferedReader(file)) {
            String line;
            while ((line = br.readLine()) != null) {
                int start = switch (paddingAlignment) {
                    case LEFT -> 0;
                    case CENTER -> ((cols /2) - (line.length() / 2));
                    case RIGHT -> cols - line.length();
                };
                tg.putString(start, currentLine, line);
                currentLine++;
            }
        }
        catch (IOException e ) {
            System.out.println("Error reading file");
        }
    }

}
