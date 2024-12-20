package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import jakarta.servlet.http.Part;

public class FileUtils {
    public static String saveFile(Part filePart) throws IOException {
        // Définir le chemin où l'image sera enregistrée
        String uploadDirectory = "/path/to/uploads";
        File uploadDir = new File(uploadDirectory);
        
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String filePath = uploadDirectory + File.separator + fileName;
        
        filePart.write(filePath);
        
        return filePath; // Retourner le chemin relatif de l'image
    }
}
