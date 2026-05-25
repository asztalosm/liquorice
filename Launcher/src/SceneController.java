import java.io.IOException;

public class SceneController {
    public static void loadScene(String scene) throws IOException, InterruptedException {
        switch (scene.toLowerCase()) {
            case "main menu" -> MainMenu.showMainMenu();
            case "options menu" -> Options.drawOptions();
            case "initialization" -> Initialization.showInitialization();
            case "play menu" -> PlayGame.playOptions();
            case "create save" -> PlayGame.createSave();
            case "exit" -> Main.exit();
            case "saves explorer" -> {
                SaveHandler.openExplorer();
                Options.drawOptions();
            }
            case "delete saves" -> Options.confirmDeletion();
            case "confirm deletion" -> {
                SaveHandler.deleteSaves();
                Options.drawOptions();
            }
            case "load saves" -> PlayGame.loadSaves();
            case "campaign selector" -> Campaign.CampaignSelector();
            case "test" -> PlayGame.testBattle();
            case "initialize save" -> PlayGame.initializeSave();
            case "northern farms" -> CampaignHandler.east();
            case "eastern farms" -> CampaignHandler.east();
            case "western farms" -> CampaignHandler.east();
        }
    }
}