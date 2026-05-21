import java.io.IOException;

public class SceneController {
    public static void loadScene(String scene) throws IOException, InterruptedException {
        switch (scene) {
            case "Main Menu" -> MainMenu.showMainMenu();
            case "Options Menu" -> Options.drawOptions();
            case "Initialization" -> Initialization.showInitialization();
            case "Play Menu" -> PlayGame.playOptions();
            case "Create Save" -> PlayGame.createSave();
            case "Exit" -> Main.exit();
            case "Saves Explorer" -> {
                SaveHandler.openExplorer();
                Options.drawOptions();
            }
            case "Delete Saves" -> Options.confirmDeletion();
            case "Confirm Deletion" -> {
                SaveHandler.deleteSaves();
                Options.drawOptions();
            }
            case "Load Saves" -> PlayGame.loadSaves();
            case "Encounter selection" -> PlayGame.testBattle();
        }
    }
}
