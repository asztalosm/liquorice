import com.googlecode.lanterna.input.KeyStroke;

import java.io.FileReader;
import java.io.IOException;

public class Initialization {
    public static void showInitialization() throws IOException, InterruptedException {
        Main.scene = "Initialization";
        Main.screen.clear();
        Main.screen.refresh();
        Main.formatter.printMulti(2, new FileReader("ascii-art/main-title.txt"), TextFormatter.PaddingAlignment.CENTER);
        Main.formatter.printSingle(25, "Press the up and down arrows to enlarge/shrink the UI (Current size: " + Main.uiSize + ") This text should be in the upper half of the terminal.", TextFormatter.PaddingAlignment.CENTER);
        Main.formatter.printSingle(26, "Press Enter to continue", TextFormatter.PaddingAlignment.CENTER);
        Main.screen.refresh();

        //create saves folder on first launch
        SaveHandler.createSavesFolder();

        while (true) {
            KeyStroke key = Main.screen.readInput();
            if (key == null) continue;
            switch (key.getKeyType()) {
                case Escape -> { return; }
                case ArrowDown -> {
                    Main.screen.stopScreen();
                    Main.terminal.dispose();
                    Main.uiSize++;
                    Main.main(null);
                }
                case ArrowUp -> {
                    Main.screen.stopScreen();
                    Main.terminal.dispose();
                    Main.uiSize--;
                    Main.main(null);
                }
                case Enter -> {
                    SceneController.loadScene("Main Menu");
                    return;
                }
            }
        }
    }
}