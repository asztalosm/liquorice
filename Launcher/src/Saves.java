import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.io.File;
import java.io.IOException;

public class Saves {

    private static final ObjectMapper mapper = new ObjectMapper();
    public static File currentFile;
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class SaveData {
        private String scene;
        private String name;
        private int nemesisPercentage;
        private int money;
        private int progress;

        public SaveData() {}

        public String getScene() {return scene;}
        public String getName() {return name;}
        public int getNemesisPercentage() {return nemesisPercentage;}
        public int getMoney() {return money;}
        public int getProgress() {return progress;}

        public void setScene(String scene) {this.scene = scene;}
        public void setName(String name) {this.name = name;}
        public void setNemesisPercentage(int nemesisPercentage) {this.nemesisPercentage = nemesisPercentage;}
        public void setMoney(int money) {this.money = money;}
        public void setProgress(int progress) {this.progress = progress;}
    }

    public static void loadSave(File saveFile) throws IOException, InterruptedException {
        SaveData save = mapper.readValue(saveFile, SaveData.class);
        Main.characterName = save.getName();
        currentFile = saveFile;
        Globals.nemesisPercentage = save.getNemesisPercentage();
        Globals.money = save.getMoney();
        Globals.progress = save.getProgress();
        SceneController.loadScene(save.getScene());
    }


    public static void saveSave(File saveFile, String scene, String name, int nemesisPercentage, int money, int progress) throws IOException, InterruptedException {
        System.out.println("saveSave file: " + saveFile.getAbsoluteFile());
        System.out.println("saveSave file exists:" + saveFile.exists());
        SaveData save = new SaveData();
        Main.screen.clear();
        Main.screen.refresh();
        Main.formatter.alert(20, List.of("Save file of "+name+" updated."));
        save.setScene("Campaign selector");
        save.setName(name);
        save.setNemesisPercentage(nemesisPercentage);
        save.setMoney(money);
        save.setProgress(progress);
        mapper.writeValue(saveFile, save);
    }
}