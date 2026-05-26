import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MainMenu {
    public static void showMainMenu() throws IOException, InterruptedException {
        Main.scene = "Main Menu";
        Main.screen.clear();
        Main.formatter.printMulti(2, new FileReader("ascii-art/main-title.txt"), TextFormatter.PaddingAlignment.CENTER);
        Main.formatter.printSelectionMultiLine(15, List.of("Play", "Settings", "Exit"), List.of("Play Menu", "Options Menu", "Exit"), TextFormatter.PaddingAlignment.CENTER);
    }
}