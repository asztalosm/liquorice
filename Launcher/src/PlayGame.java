import GameClasses.GameClasses;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Savepoint;
import java.util.List;

public class PlayGame {
    public static String fileName;
    public static String characterName;

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
        fileName = Main.formatter.askTextInputFileValidation(17, TextFormatter.PaddingAlignment.CENTER, "Play Menu", "Initialize Save");
        SceneController.loadScene("Initialize Save");
    }

    public static void initializeSave() throws IOException, InterruptedException {
        Main.scene = "Initialize Save";
        Main.screen.clear();
        Main.screen.refresh();
        Main.formatter.printMulti(2, new FileReader("ascii-art/main-title.txt"), TextFormatter.PaddingAlignment.CENTER);
        Main.formatter.printSingle(16, "What should be the name of your character?", TextFormatter.PaddingAlignment.CENTER);
        characterName = Main.formatter.askTextInput(17, TextFormatter.PaddingAlignment.CENTER, "Play Menu", "Load Saves");
        Saves.saveSave(SaveHandler.getFile(fileName), "eastern farms", characterName, 0, 0, 0);
        Saves.loadSave(SaveHandler.getFile(fileName));
    }

    public static void loadSaves() throws IOException, InterruptedException {
        Main.scene = "Load Saves";
        Main.screen.clear();
        Main.screen.refresh();
        Main.formatter.printMulti(2, new FileReader("ascii-art/main-title.txt"), TextFormatter.PaddingAlignment.CENTER);
        Main.formatter.printSaveSelection(16, SaveHandler.getSavesList(), TextFormatter.PaddingAlignment.CENTER);
    }

    public static void testBattle() throws IOException, InterruptedException {
        Main.scene = "Combat";
        Combat.battle(
            new GameClasses.Entity("Natsuki DDLC", 5, 10, 1, 2, "ascii-art/jane.txt", List.of(Weapons.TacticalAxe), null), 
            List.of(
                new GameClasses.Entity("Foo", 1, 3, 0, 1, "ascii-art/jane.txt", List.of(Weapons.Fists), new String[] {"runescape 2 rat"}),
                new GameClasses.Entity("Bar", 1, 3, 0, 1, "ascii-art/jane.txt", List.of(Weapons.ritualDagger), new String[] {"runescape 2 rat"}),
                new GameClasses.Entity("Tsar Nicholas II.", 1, 3, 0, 1, "ascii-art/jane.txt", List.of(Weapons.rat), new String[] {"runescape 2 rat"})
            )
        );
    }
}