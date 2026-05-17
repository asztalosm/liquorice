
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public record TextFormatter(TextGraphics tg, int cols) {

    //region enums and global variables
    public enum PaddingAlignment { //Alignment for where the text will be shown on the terminal screen
        LEFT, CENTER, RIGHT
    }

    /*public enum TextAlignment { //Maybe I'll do this if we need to float text to the left or right
        LEFT, CENTER, RIGHT
    }
     */

    public enum BorderStyle {
        //add more if needed
        SINGLE, // ┌ ─ ┐ │ └ ┘
        DOUBLE, // ╔ ═ ╗ ║ ╚ ╝
        ROUNDED // ╭ ─ ╮ │ ╰ ╯
    }

    //endregion

    //region functions that return values
    public int getStartColumn(String line, PaddingAlignment paddingAlignment) {
        return switch (paddingAlignment) {
            case LEFT -> 0;
            case CENTER -> ((cols / 2) - (line.length() / 2));
            case RIGHT -> cols - line.length();
        };
    }

    public List<String> getBorderStyle(BorderStyle borderStyle) {
        return switch (borderStyle) {
            case SINGLE -> Arrays.asList("┌", "─", "┐", "│", "└", "┘");
            case DOUBLE -> Arrays.asList("╔", "═", "╗", "║", "╚", "╝");
            case ROUNDED -> Arrays.asList("╭", "─", "╮", "│", "╰", "╯");
        };
    }

    public String getLongestElementLength(List<String> list) {
        String longest = null;
        if (!list.isEmpty()) {
            longest = list.stream().max(Comparator.comparingInt(String::length)).get();
        }
        return longest;
    }
    //endregion

    //region single print functions and overrides
    public void printSingle(int row, String line, PaddingAlignment paddingAlignment) throws IOException {
        tg.putString(getStartColumn(line, paddingAlignment), row, line);
        Main.screen.refresh();
    }
    public void printSingle(int row, String line, PaddingAlignment paddingAlignment, BorderStyle borderStyle) throws IOException {
        List<String> borderCharacters = getBorderStyle(borderStyle);

        //creates the lines for the string seperately
        String borderTop = borderCharacters.getFirst();
        String borderBottom = borderCharacters.get(4);
        for (int i = 0; i < line.length(); i++) {
            borderTop = borderTop.concat(borderCharacters.get(1));
            borderBottom = borderBottom.concat(borderCharacters.get(1));
        }
        borderTop = borderTop.concat(borderCharacters.get(2));
        borderBottom = borderBottom.concat(borderCharacters.get(5));
        String middle = borderCharacters.get(3) + line +  borderCharacters.get(3);

        //does the printing
        tg.putString(getStartColumn(line, paddingAlignment)-1, row-1, borderTop);
        tg.putString(getStartColumn(line,paddingAlignment)-1, row, middle);
        tg.putString(getStartColumn(line,paddingAlignment)-1, row+1, borderBottom);
        Main.screen.refresh();
    }

    //endregion

    //region multi print functions and overrides
    public void printMulti(int startRow, String text, PaddingAlignment paddingAlignment) throws IOException {
        String[] lines = text.split("\n");
        int currentLine = startRow;
        for (String line : lines) {
            tg.putString(getStartColumn(text, paddingAlignment), currentLine, line);
            currentLine++;
        }
        Main.screen.refresh();
    }

    public void printMulti(int startRow, List<String> lines, PaddingAlignment paddingAlignment) throws IOException {
        int currentLine = startRow;
        for (String line : lines) {
            tg.putString(getStartColumn(line, paddingAlignment), currentLine, line);
            currentLine++;
        }
        Main.screen.refresh();
    }

    public void printMulti(int startRow, List<String> lines, PaddingAlignment paddingAlignment, BorderStyle borderStyle, int verticalPadding) throws IOException {
        List<String> borderCharacters = getBorderStyle(borderStyle);
        String longest = getLongestElementLength(lines);
        int longestLength = longest.length();
        int borderStartCol = getStartColumn(longest, paddingAlignment) - 1;

        //creates top and bottom border as strings
        String borderTop = borderCharacters.getFirst();
        String borderBottom = borderCharacters.get(4);
        for (int i = 0; i < longestLength; i++) {
            borderTop = borderTop.concat(borderCharacters.get(1));
            borderBottom = borderBottom.concat(borderCharacters.get(1));
        }
        borderTop = borderTop.concat(borderCharacters.get(2));
        borderBottom = borderBottom.concat(borderCharacters.get(5));

        tg.putString(borderStartCol, startRow - 1, borderTop);
        int currentLine = startRow;
        for (String line : lines) {
            int totalPadding = longestLength - line.length();
            int padLeft = totalPadding / 2;
            int padRight = totalPadding - padLeft;
            String paddedLine = borderCharacters.get(3)
                    + " ".repeat(padLeft) + line + " ".repeat(padRight)
                    + borderCharacters.get(3);

            tg.putString(borderStartCol, currentLine, paddedLine);

            if (verticalPadding != 0 && (!line.equals(lines.getLast()))) {
                currentLine++;
                String emptyLine = borderCharacters.get(3)
                        + " ".repeat(longestLength)
                        + borderCharacters.get(3);
                tg.putString(borderStartCol, currentLine, emptyLine);
            }
            currentLine++;
        }
        tg.putString(borderStartCol, currentLine, borderBottom);
        Main.screen.refresh();
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
        Main.screen.refresh();
    }
    //endregion asdasd

    //region draw functions

    public void drawOptions(int startRow, List<String> options, PaddingAlignment paddingAlignment, int selection) {
        int currentRow = startRow;
        for (int i = 0; i < options.size(); i++) {
            if (selection == i) {
                tg.putString(getStartColumn(options.get(i), paddingAlignment)-2, currentRow,"> " + options.get(i));
            } else {
                tg.putString(getStartColumn(options.get(i), paddingAlignment)-2, currentRow, "  " + options.get(i));
            }
            currentRow++;
        }
    }

    //endregion

    //region Option printing

    public void printOptionsInLine(int row, List<String> options, PaddingAlignment paddingAlignment, int padding) {
        for (String option : options) {

            for (int i = 0; i < padding; i++) {
                option = " ".concat(option);
                option = option.concat(" ");
            }
            tg.putString(getStartColumn(option, paddingAlignment) - (padding / 2), row, option);
        } //this is broken as fuck and I don't have the brain capacity to fix it, I'll do multi line instead
    }

    public void printSelectionMultiLine(int startRow, List<String> options, List<String> sceneOptions, PaddingAlignment paddingAlignment) throws IOException, InterruptedException {
        //bit fucked, but now we have 2 lists, one is for displaying text, one is for scene controller options, scenecontroller will handle most of the bullshit with just a switch case, this way we don't have to hardcode anything
        int selection = 0;
        drawOptions(startRow, options, paddingAlignment, selection);
        Main.screen.refresh();

        while (true) {
            KeyStroke key = Main.screen.readInput();
            if (key == null) continue;
            switch (key.getKeyType()) {
                case ArrowUp -> selection = Math.max(0, selection - 1);
                case ArrowDown -> selection = Math.min(sceneOptions.size() -1 , selection + 1);
                case Enter -> {
                    SceneController.loadScene(sceneOptions.get(selection));
                    return;
                }
                case Escape -> Main.exit();
            }
            drawOptions(startRow, options, paddingAlignment, selection);
            Main.screen.refresh();
        }
    }
    //endregion

    //region askInput stuff

    public void askTextInput(int row, PaddingAlignment paddingAlignment, String previousScene, String nextScene) throws IOException, InterruptedException {
        StringBuilder input = new StringBuilder();
        tg.putString(getStartColumn("", paddingAlignment), row, "> ");
        Main.screen.refresh();
        while (true) {
            Main.screen.refresh();
            // draw current input centered
            KeyStroke key = Main.screen.readInput();
            if (key == null) continue;
            switch (key.getKeyType()) {
                case Character -> {
                    if (!key.getCharacter().equals(' ')) {
                        input.append(key.getCharacter());
                    }
                }
                case Backspace -> {
                    if (!input.isEmpty()) {
                        input.deleteCharAt(input.length() - 1);
                    }
                }
                case Enter -> {
                    SaveHandler.createNewSave(input.toString());
                    SceneController.loadScene(nextScene);
                    return;
                }
                case Escape -> SceneController.loadScene(previousScene); // null signals canceled input
            }
            String display = input.toString();
            String line = "> " + display + "_";
            String cleared = " ".repeat(line.length() + 1);
            tg.putString(getStartColumn(cleared, paddingAlignment), row, cleared); // clear old text
            tg.putString(getStartColumn(line, paddingAlignment), row, line);
        }
    }
    //endregion
}

