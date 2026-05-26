import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
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

        public SaveData() {}

        public String getScene() {return scene;}
        public String getName() {return name;}
        public int getNemesisPercentage() {return nemesisPercentage;}

        public void setScene(String scene) {this.scene = scene;}
        public void setName(String name) {this.name = name;}
        public void setNemesisPercentage(int nemesisPercentage) {this.nemesisPercentage = nemesisPercentage;}
    }

    public static void loadSave(File saveFile) throws IOException, InterruptedException {
        SaveData save = mapper.readValue(saveFile, SaveData.class);
        Main.characterName = save.getName();
        currentFile = saveFile;
        Globals.nemesisPercentage = save.getNemesisPercentage();
        SceneController.loadScene(save.getScene());
    }


    public static void saveSave(File saveFile, String scene, String name, int nemesisPercentage) throws IOException, InterruptedException {
        System.out.println("saveSave file: " + saveFile.getAbsoluteFile());
        System.out.println("saveSave file exists:" + saveFile.exists());
        SaveData save = new SaveData();
        save.setScene("Campaign selector");
        save.setName(name);
        save.setNemesisPercentage(nemesisPercentage);
        mapper.writeValue(saveFile, save);
    }
}