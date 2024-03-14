package com.HTW.StudentFaceRecognition.Utils;

import com.HTW.StudentFaceRecognition.Constant.FileConstant;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUItils {
    public static boolean deleteDirectory(String path) {
        File directoryToBeDeleted = new File(path);
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file.getPath());
            }
        }
        return directoryToBeDeleted.delete();
    }
    public static void saveImage(MultipartFile imageFile) throws IOException {
        String path = FileConstant.TEMP_PATH;
        Path destinationPath = Paths.get(path).resolve("temp.jpg");
        Files.createDirectories(destinationPath.getParent());
        Files.copy(imageFile.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
