import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Options {
    public static void drawOptions() throws IOException, InterruptedException {
        Main.scene = "Options";
        Main.screen.clear();
        Main.screen.refresh();
        Main.formatter.printMulti(2, new FileReader("ascii-art/main-title.txt"), TextFormatter.PaddingAlignment.CENTER);
        Main.formatter.printSelectionMultiLine(15, List.of("Open saves folder in explorer", "Delete all saves", "Change UI Size", "Back"), List.of("Saves Explorer", "Delete Saves", "Initialization", "Main Menu"), TextFormatter.PaddingAlignment.CENTER);
    }

    public static void confirmDeletion() throws IOException, InterruptedException {
        Main.scene = "Confirm Deletion";
        Main.screen.clear();
        Main.screen.refresh();
        Main.formatter.printMulti(2, new FileReader("ascii-art/main-title.txt"), TextFormatter.PaddingAlignment.CENTER);
        Main.formatter.printSingle(16, "Are you sure you want to delete all of your saves? This is unrecoverable", TextFormatter.PaddingAlignment.CENTER);
        Main.formatter.printSelectionMultiLine(17, List.of("Yes", "Nevermind"), List.of("Confirm Deletion", "Options Menu"), TextFormatter.PaddingAlignment.CENTER);
    }
}