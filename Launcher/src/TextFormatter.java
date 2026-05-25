import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public record TextFormatter(TextGraphics tg, int cols) {
    // public static void main(String[] args) {
    //     ColorMap.put("YELLOW", TextColor.ANSI.YELLOW);
    //     ColorMap.put("WHITE", TextColor.ANSI.WHITE);
    // }

    //region enums and global variables
    public enum PaddingAlignment { //Alignment for where the text will be shown on the terminal screen
        LEFT, CENTER, RIGHT
    }

    public enum TextAlignment { //Maybe I'll do this if we need to float text to the left or right
        LEFT, CENTER, RIGHT
    }

    public enum BorderStyle {
        //add more if needed
        SINGLE, // ┌ ─ ┐ │ └ ┘
        DOUBLE, // ╔ ═ ╗ ║ ╚ ╝
        ROUNDED // ╭ ─ ╮ │ ╰ ╯
    }

    // public static HashMap<String, TextColor.ANSI> ColorMap = new HashMap<>();
    //endregion

    //region functions that return values
    public int getStartColumn(String line, PaddingAlignment paddingAlignment) {
        return switch (paddingAlignment) {
            case LEFT -> 0;
            case CENTER -> ((cols / 2) - (line.length() / 2));
            case RIGHT -> cols - line.length();
        };
    }

    public static List<String> getBorderStyle(BorderStyle borderStyle) {
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

    private static List<String> getGreaterList(List<List<String>> lists) {
        List<String> greatest = new ArrayList<>();
        for (List<String> li : lists) {
            if (li.size() > greatest.size()) {
                greatest = li;
            }
        }
        return greatest;
    }
    //endregion

    //region single print functions and overrides
    public void printSingle(int row, String line, PaddingAlignment paddingAlignment) throws IOException {
        tg.putString(getStartColumn(line, paddingAlignment), row, line);
        Main.screen.refresh();
    }

/*
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
*/
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

    /*
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
    */
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
                //case Escape -> Main.exit(); idk how much this will break stuff, but I hope this is better ?
            }
            drawOptions(startRow, options, paddingAlignment, selection);
            Main.screen.refresh();
        }
    }

    public void printSaveSelection(int startRow, List<File> saves, PaddingAlignment paddingAlignment) throws IOException, InterruptedException {
        int selection = 0;
        List<String> displayedSaveTexts = new ArrayList<>();
        for (File save : saves) {
            long lastModification = save.lastModified();
            DateFormat sdf = new SimpleDateFormat("yyyy MM dd - hh:mm a");
            displayedSaveTexts.add(save.getName() + sdf.format(lastModification));
        }
        drawOptions(startRow, displayedSaveTexts, paddingAlignment, selection);
        Main.screen.refresh();

        while (true) {
            KeyStroke key = Main.screen.readInput();
            if (key == null) continue;
            switch (key.getKeyType()) {
                case ArrowUp -> selection = Math.max(0, selection - 1);
                case ArrowDown -> selection = Math.min(saves.size() -1 , selection + 1);
                case Enter -> {
                    Saves.loadSave(saves.get(selection));
                    return;
                }
                case Escape -> {
                    SceneController.loadScene("Play Menu");
                    return;
                }
            }
            drawOptions(startRow, displayedSaveTexts, paddingAlignment, selection);
            Main.screen.refresh();
        }

    }

    //endregion

    //region askInput stuff

    public String askTextInput(int row, PaddingAlignment paddingAlignment, String previousScene, String nextScene) throws IOException, InterruptedException {
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
                    SceneController.loadScene(nextScene);
                    return input.toString();
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

    public String promptTextInput(int row, PaddingAlignment paddingAlignment, String prompt) throws IOException, InterruptedException {
        StringBuilder input = new StringBuilder();
        tg.putString(getStartColumn(prompt, paddingAlignment), row, prompt);
        Main.screen.refresh();
        while (true) {
            Main.screen.refresh();
            // String cleared = " ".repeat(prompt.concat(input.toString()).length() + prompt.length());
            String cleared = " ".repeat(cols);
            tg.putString(getStartColumn(cleared, paddingAlignment), row, cleared); // clear old text
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
                    return input.toString();
                } // null signals canceled input
                default -> {
                }
            }
            String line = prompt.concat(input.toString());
            // String cleared = " ".repeat(line.length() + 1);
            tg.putString(getStartColumn(cleared, paddingAlignment), row, cleared); // clear old text
            tg.putString(getStartColumn(prompt, paddingAlignment), row, line);
        }
    }

    public void askTextInputFileValidation(int row, PaddingAlignment paddingAlignment, String previousScene, String nextScene) throws IOException, InterruptedException {
        //same as previous but it checks for pre-existing files
        StringBuilder input = new StringBuilder();
        tg.putString(getStartColumn("", paddingAlignment), row, "> ");
        Main.screen.refresh();
        boolean canCreate = false;
        while (true) {
            Main.screen.refresh();
            // draw current input centered
            KeyStroke key = Main.screen.readInput();
            if (key == null) continue;
            switch (key.getKeyType()) {
                case Character -> {
                    if (!key.getCharacter().equals(' ')) {
                        input.append(key.getCharacter());
                        if (SaveHandler.checkFileExists(input.toString().toLowerCase())) {
                            tg.putString(0, row+1, " ".repeat(cols));
                            String error = "ERROR: A save with this name already exists";
                            tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                            tg.putString(getStartColumn(error, PaddingAlignment.CENTER), row + 1, error);
                            tg.setForegroundColor(TextColor.ANSI.DEFAULT);
                            Main.screen.refresh();
                        } else {
                            tg.putString(0, row+1, " ".repeat(cols));
                        }
                        Main.screen.refresh();
                    }
                }
                case Backspace -> {
                    if (!input.isEmpty()) {
                        input.deleteCharAt(input.length() - 1);
                    }
                    if (input.toString().isEmpty()) {
                        tg.putString(0, row+1, " ".repeat(cols));
                        String error = "ERROR: Save name cannot be empty";
                        tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                        tg.putString( getStartColumn(error, PaddingAlignment.CENTER),row+1, error);
                        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
                    } else if (SaveHandler.checkFileExists(input.toString().toLowerCase())) {
                        tg.putString(0, row+1, " ".repeat(cols));
                        String error = "ERROR: A save with this name already exists";
                        tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                        tg.putString( getStartColumn(error, PaddingAlignment.CENTER), row+1, error);
                        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
                    } else {
                        tg.putString(0, row+1, " ".repeat(cols));
                    }
                    Main.screen.refresh();
                }
                case Enter -> {
                    if (input.toString().isEmpty()) {
                        String error = "ERROR: Save name cannot be empty";
                        tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                        tg.putString( getStartColumn(error, PaddingAlignment.CENTER),row+1, error, SGR.BOLD);
                        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
                        Main.screen.refresh();
                        continue;
                    }
                    if (SaveHandler.checkFileExists(input.toString().toLowerCase())) {
                        String error = "ERROR: A save with this name already exists";
                        tg.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
                        tg.putString( getStartColumn(error, PaddingAlignment.CENTER), row+1, error, SGR.BOLD);
                        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
                        Main.screen.refresh();
                        continue;
                    } else {
                        SaveHandler.createNewSave(input.toString());
                        SceneController.loadScene(nextScene);
                    }
                    Main.screen.refresh();
                    return;
                }
                case Escape -> SceneController.loadScene(previousScene); // null signals canceled input
            }
            String display = input.toString();
            String line = "> " + display + "_";
            String cleared = " ".repeat(line.length() + 1);
            tg.putString(getStartColumn(cleared, paddingAlignment), row, cleared); // clear old text
            tg.putString(getStartColumn(line, paddingAlignment), row, line);
            Main.screen.refresh();
        }
    }

    //endregion
    
    //region art loader
    public List<String> loadArt(String path) {
        List<String> product = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                product.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + path);
        }
        return product;
    }
    //endregion

    //region connect multi
    public List<String> connectAsciiArt(List<List<String>> texts) {
        return connectAsciiArt(texts, 6);
    }

    public List<String> connectAsciiArt(List<List<String>> texts, int p) {
        List<String> product = new ArrayList<>();
        List<String> maxList = getGreaterList(texts);
        int maxRows = maxList.size();
        
        for (int ii = 0; ii < maxRows; ii++) {
            StringBuilder subproduct = new StringBuilder();
            for (int i = 0; i < texts.size(); i++) {
                List<String> text = texts.get(i);
                String padding = (i < texts.size() - 1) ? " ".repeat(p) : "";
                try {
                    subproduct.append(text.get(ii)).append(padding);
                } catch (IndexOutOfBoundsException e) {
                    if (!text.isEmpty()) {
                        subproduct.append(" ".repeat(text.get(0).length())).append(padding);
                    }
                }
            }
            product.add(subproduct.toString());
        }
        return product;
    }
    //endregion

    //region create box
    //I know that we have a box printing function already but I need this for the combat menus
    public List<String> createBox(int w, int h, List<String> content, BorderStyle bs) {

        if (content.size() > h) {
            h = content.size();
        }
        for (String line : content) {
            if (line.length() > w) {
                w = line.length();
            }
        }

        List<String> box = new ArrayList<>();
        box.add(getBorderStyle(bs).get(0) + getBorderStyle(bs).get(1) + getBorderStyle(bs).get(1).repeat(w) + getBorderStyle(bs).get(1) + getBorderStyle(bs).get(2));

        for (int i = 0; i < h + 1; i++) {
            String line;
            if (i == h) {
                line = getBorderStyle(bs).get(4) + getBorderStyle(bs).get(1) + getBorderStyle(bs).get(1).repeat(w) + getBorderStyle(bs).get(1) + getBorderStyle(bs).get(5);
            } else {
                try {
                    String content_line = content.get(i);
                    line = getBorderStyle(bs).get(3)+" " + (content_line + " ".repeat(Math.max(0, w - content_line.length()))) + " "+getBorderStyle(bs).get(3);
                } catch (IndexOutOfBoundsException e) {
                    line = getBorderStyle(bs).get(3)+" " + " ".repeat(w) + " "+getBorderStyle(bs).get(3);
                }
            }
            box.add(line);
        }
        return box;
    }
    //endregion
}

