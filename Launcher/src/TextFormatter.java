import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public record TextFormatter(TextGraphics tg, int cols) {
    public enum PaddingAlignment { //Alignment for where the text will be shown on the terminal screen
        LEFT, CENTER, RIGHT
    }

    /*public enum TextAlignment { //Maybe I'll do this if we need to float text to the left or right
        LEFT, CENTER, RIGHT
    }
     */

    public int getStartColumn(String line, PaddingAlignment paddingAlignment) {
        return switch (paddingAlignment) {
            case LEFT -> 0;
            case CENTER -> ((cols / 2) - (line.length() / 2));
            case RIGHT -> cols - line.length();
        };
    }

    public void printSingle(int row, String line, PaddingAlignment paddingAlignment) {
        tg.putString(getStartColumn(line, paddingAlignment), row, line);
    }

    public void printMulti(int startRow, String text, PaddingAlignment paddingAlignment) {
        String[] lines = text.split("\n");
        int currentLine = startRow;
        for (String line : lines) {
            tg.putString(getStartColumn(text, paddingAlignment), currentLine, line);
            currentLine++;
        }
    }

    public void printMulti(int startRow, List<String> lines, PaddingAlignment paddingAlignment) {
        int currentLine = startRow;
        for (String line : lines) {
            tg.putString(getStartColumn(line, paddingAlignment), currentLine, line);
            currentLine++;
        }
    }

    public void printMulti(int startRow, FileReader file, PaddingAlignment paddingAlignment) throws IOException {
        int currentLine = startRow;
        try (BufferedReader br = new BufferedReader(file)) {
            String line;
            while ((line = br.readLine()) != null) {
                tg.putString(getStartColumn(line, paddingAlignment), currentLine, line);
                currentLine++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    public void printOptionsInLine(int row, List<String> options, PaddingAlignment paddingAlignment, int padding) {
        for (String option : options) {

            for (int i = 0; i < padding; i++) {
                option = " ".concat(option);
                option = option.concat(" ");
            }
            tg.putString(getStartColumn(option, paddingAlignment) - (padding / 2), row, option);
        } //this is broken as fuck and I don't have the brain capacity to fix it, i'll do multi line instead
    }

    public void printSelectionMultiLine(int startRow, List<String> options, PaddingAlignment paddingAlignment) throws IOException {
        int currentRow = startRow;
        int selection = 0;
        int maxSelection = options.size();
        for (String option : options) {
            tg.putString(getStartColumn(option, paddingAlignment), currentRow, option);
            currentRow++;
        }
        while (Main.scene.equals("Main Menu")) {
            KeyStroke key = Main.screen.readInput();
            //if (key.getKeyType() == KeyType.ArrowUp) {
            //}
            System.out.println(key);
        }
    }


}
