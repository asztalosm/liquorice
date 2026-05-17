import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class SaveHandler {
    public static String userHome = System.getProperty("user.home");
    public static Path saveDirectory = Paths.get(userHome, "Documents", "Liquorice");
    public static void createSavesFolder() {
        if (!Files.exists(saveDirectory)) {
            try {
                Files.createDirectories(saveDirectory);
            } catch (IOException e) {
                System.out.println("\u001B[31mError: Failed to create save directory: " + e.getMessage() + "\u001B[0m");
            }
        }
    }


    public static void createNewSave(String characterName) throws IOException {
        createSavesFolder();
        Date d1 = new Date();
        String formatted = new SimpleDateFormat("yyyy-MM-dd_hh-mma").format(d1);
        Files.createFile(Path.of(saveDirectory.toString() + "\\" + characterName + " " + formatted));

    }

    public static void deleteSaves() {
        File folder = new File(saveDirectory.toUri());
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            file.delete();
        }
    }

    public static void openExplorer() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("explorer.exe", saveDirectory.toString());
        Process process = pb.start();
    }
}
