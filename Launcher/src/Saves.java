import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;

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
        SceneController.loadScene(save.getScene());
        System.out.println(save.getName());
    }

    public static void saveSave(File saveFile, String scene, String name) throws IOException {
        SaveData save = new SaveData();
        save.setName(name);
        save.setScene(scene);
        mapper.writeValue(saveFile, save);
    }
}