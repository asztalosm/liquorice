import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Terminal {
    // ANSI color codes
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BLACK_BG = "\u001B[40m";
    public static final String WHITE_BG = "\u001B[47m";

    private static final int DEFAULT_TERMINAL_WIDTH = 160;
    private static final int DEFAULT_TERMINAL_HEIGHT = 48;
    private static final Scanner scanner = new Scanner(System.in);

    public static void terminalLicense() {
        System.out.println("Tamaz made ts ig");
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

    public static int getTerminalWidth() {
        try {
            String[] cmd = { "cmd.exe", "/c", "mode con" };
            Process process = Runtime.getRuntime().exec(cmd);
            Scanner s = new Scanner(process.getInputStream()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            s.close();
            
            for (String line : result.split("\n")) {
                if (line.contains("Columns:")) {
                    String[] parts = line.split(":");
                    if (parts.length > 1) {
                        return Integer.parseInt(parts[1].trim());
                    }
                }
            }
        } catch (Exception e) {
            // Fall back to default
        }
        return DEFAULT_TERMINAL_WIDTH;
    }

    public static int getTerminalHeight() {
        try {
            String[] cmd = { "cmd.exe", "/c", "mode con" };
            Process process = Runtime.getRuntime().exec(cmd);
            Scanner s = new Scanner(process.getInputStream()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            s.close();
            
            for (String line : result.split("\n")) {
                if (line.contains("Lines:")) {
                    String[] parts = line.split(":");
                    if (parts.length > 1) {
                        return Integer.parseInt(parts[1].trim());
                    }
                }
            }
        } catch (Exception e) {
            // Fall back to default
        }
        return DEFAULT_TERMINAL_HEIGHT;
    }

    public static void refresh() {
        String spaces = " ".repeat(getTerminalWidth());
        System.out.print(spaces + "\r");
        System.out.flush();
    }

    public static void clearScreen() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < getTerminalHeight(); i++) {
                System.out.println();
            }
        }
    }

    public static String centerText(String line) {
        return centerText(line, 0);
    }

    public static String centerText(String line, int padding) {
        int screenWidth = getTerminalWidth();
        if (padding == 0) {
            padding = (screenWidth - line.length()) / 2;
        }

        if (padding > 0) {
            return " ".repeat(padding) + line;
        } else {
            return line;
        }
    }

    public static String centeredInput(String prompt) {
        return centeredInput(prompt, true);
    }

    public static String centeredInput(String prompt, boolean clear) {
        String centeredPrompt = centerText(prompt);
        System.out.print(centeredPrompt);
        System.out.flush();
        String userInput = scanner.nextLine().trim();
        if (clear) {
            System.out.print("\033[F\033[K");
            System.out.flush();
        }
        return userInput;
    }

    public static void shortAlert(String message) {
        alert(message, 0.02, true, 1, false);
    }

    public static void alert(String message, double sleepTime, boolean clear, double lingerTime, boolean framed) {
        String end = "\r";
        for (int l = 0; l < message.length(); l++) {
            String frame1 = "";
            String frame2 = "";
            if (framed) {
                frame1 = "[";
                frame2 = " ".repeat(Math.max(0, message.length() - l - 1)) + "]";
            }
            if (l == message.length() - 1) {
                end = "\n";
            }
            System.out.print(centerText(frame1 + message.substring(0, l + 1) + frame2) + end);
            System.out.flush();
            try {
                Thread.sleep((long) (sleepTime * 1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        try {
            Thread.sleep((long) (lingerTime * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (clear) {
            refresh();
        }
    }

    public static void printAsciiArt(List<String> text) {
        printAsciiArt(text, true);
    }

    public static void printAsciiArt(List<String> text, boolean center) {
        if (center) {
            for (String line : text) {
                System.out.println(centerText(line));
            }
        } else {
            for (String line : text) {
                System.out.println(line);
            }
        }
    }

    public static void event(List<String> text, String eventFunc) {
        event(text, eventFunc, true);
    }

    public static void event(List<String> text, String eventFunc, boolean clear) {
        for (String line : text) {
            if (!line.isEmpty()) {
                line = line.replace("BLANK", "");
                String finalLine = line;
                for (int l = 0; l < finalLine.length(); l++) {
                    System.out.print(centerText(finalLine.substring(0, l + 1)) + "\r");
                    System.out.flush();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                centeredInput("[ENTER]");
                refresh();
                System.out.println();
            }
        }
        // Execute event function - stub for now
        // exec(eventFunc);
        if (clear) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (Exception e) {
                // Fall back to println approach
                for (int i = 0; i < getTerminalHeight(); i++) {
                    System.out.println();
                }
            }
        }
    }

    public static List<String> connectAsciiArt(List<List<String>> texts) {
        return connectAsciiArt(texts, 6);
    }

    public static List<String> connectAsciiArt(List<List<String>> texts, int p) {
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

    public static List<String> createBox(int w, int h, List<String> content) {
        return createBox(w, h, content, 0, 0);
    }

    public static List<String> createBox(int w, int h, List<String> content, int boxType, int border) {
        String[][] boxTypes = {
            { "┐", "┌", "└", "┘" },  // type 0: normal
            { " ", " ", " ", " " },  // type 1: nothing
            { ">", "<", "<", ">" }   // type 2: arrow
        };

        String[][] borderTypes = {
            { "|", "─"},  // type 0: normal
            { " ", " ", " ", " " },  // type 1: nothing
        };

        String ur = boxTypes[boxType % 3][0];
        String ul = boxTypes[boxType % 3][1];
        String ll = boxTypes[boxType % 3][2];
        String lr = boxTypes[boxType % 3][3];

        if (content.size() > h) {
            h = content.size();
        }
        for (String line : content) {
            if (line.length() > w) {
                w = line.length();
            }
        }

        List<String> box = new ArrayList<>();
        box.add(ul + borderTypes[border][1] + borderTypes[border][1].repeat(w) + borderTypes[border][1] + ur);

        for (int i = 0; i < h + 1; i++) {
            String line;
            if (i == h) {
                line = ll + borderTypes[border][1] + borderTypes[border][1].repeat(w) + borderTypes[border][1] + lr;
            } else {
                try {
                    String content_line = content.get(i);
                    line = borderTypes[border][0]+" " + (content_line + " ".repeat(Math.max(0, w - content_line.length()))) + " "+borderTypes[border][0];
                } catch (IndexOutOfBoundsException e) {
                    line = borderTypes[border][0]+" " + " ".repeat(w) + " "+borderTypes[border][0];
                }
            }
            box.add(line);
        }
        return box;
    }

    public static void invertScreen() {
        invertScreen(false);
    }

    public static void invertScreen(boolean clear) {
        if (!clear) {
            System.out.print(BLACK);
            System.out.print(WHITE_BG);
        } else {
            System.out.print(RESET);
        }
        for (int i = 0; i < getTerminalHeight(); i++) {
            for (int j = 0; j < getTerminalWidth(); j++) {
                System.out.print(" ");
            }
            System.out.println();
        }
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            // Fallback
        }
    }

    public static List<String> loadArt(String path) {
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

    public static void soutx(String str, int times) {
        soutx(str, times, "\n");
    }

    public static void soutx(String str, int times, String end) {
        for (int i = 0; i < times; i++) {
            System.out.println(str+end);
        }
    }
}
