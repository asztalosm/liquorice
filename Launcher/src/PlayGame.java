import GameClasses.GameClasses;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class PlayGame {
    public static void playOptions() throws IOException, InterruptedException {
        Main.scene = "Play Menu";
        Main.screen.clear();
        Main.screen.refresh();
        Main.formatter.printMulti(2, new FileReader("ascii-art/main-title.txt"), TextFormatter.PaddingAlignment.CENTER);
        Main.formatter.printSelectionMultiLine(15, List.of("Create new Save", "Load saves", "Back"), List.of("Create Save", "Load Saves", "Main Menu"), TextFormatter.PaddingAlignment.CENTER);
    }

    public static void createSave() throws IOException, InterruptedException {
        Main.scene = "Create Save";
        Main.screen.clear();
        Main.screen.refresh();
        Main.formatter.printMulti(2, new FileReader("ascii-art/main-title.txt"), TextFormatter.PaddingAlignment.CENTER);
        Main.formatter.printSingle(16, "What should be the name of your save?", TextFormatter.PaddingAlignment.CENTER);
        Main.formatter.askTextInputFileValidation(17, TextFormatter.PaddingAlignment.CENTER, "Play Menu", "Play Menu");
    }

    public static void loadSaves() throws IOException, InterruptedException {
        Main.scene = "Load Saves";
        Main.screen.clear();
        Main.screen.refresh();
        Main.formatter.printMulti(2, new FileReader("ascii-art/main-title.txt"), TextFormatter.PaddingAlignment.CENTER);
        Main.formatter.printSelectionMultiLine(16, List.of("Encounter Selection", "Character", "Exit"), List.of("Encounter Selection", "Character", "Save and Menu"), TextFormatter.PaddingAlignment.CENTER);
    }

    public static void testBattle() throws IOException, InterruptedException {
        Combat.battle(
            new GameClasses.Entity("L", 5, 5, 1, 1, "ascii-art/jane.txt", null, null), 
            List.of(
                new GameClasses.Entity("X", 5, 5, 1, 1, "ascii-art/jane.txt", null, null),
                new GameClasses.Entity("Y", 5, 5, 1, 1, "ascii-art/jane.txt", null, null),
                new GameClasses.Entity("Z", 5, 5, 1, 1, "ascii-art/jane.txt", null, null)
            )
        );
    }
}
