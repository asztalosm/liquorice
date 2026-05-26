import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class Saves {
    private static final ObjectMapper mapper = new ObjectMapper();

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SaveData {
        private String scene;
        private String name;

        public SaveData() {}

        public String getScene() {return scene;}
        public String getName() {return name;}

        public void setScene(String scene) {this.scene = scene;}
        public void setName(String name) {this.name = name;}
    }

    public static void loadSave(File saveFile) throws IOException, InterruptedException {
        SaveData save = mapper.readValue(saveFile, SaveData.class);
        Main.characterName = save.getName();
        SceneController.loadScene(save.getScene());
    }


    public static void saveSave(File saveFile, String scene, String name) throws IOException, InterruptedException {
        System.out.println("saveSave file: " + saveFile.getAbsoluteFile());
        System.out.println("saveSave file exists:" + saveFile.exists());
        SaveData save = new SaveData();
        save.setScene("eastern farms");
        save.setName(name);
        mapper.writeValue(saveFile, save);
        loadSave(saveFile);
    }
}