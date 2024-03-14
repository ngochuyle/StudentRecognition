package com.HTW.StudentFaceRecognition.Utils;

import com.HTW.StudentFaceRecognition.Constant.FileConstant;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
@Log4j2
public class AIUltils {
    public static void trainModel(String imagePath, String pklPath, String studentID, String pythonPath) throws IOException, InterruptedException {
        // Build command to run Python script with path and parameters
        String[] cmd = {
                FileConstant.pythonInterpreter,
                pythonPath,     // Path to Python script
                imagePath,     // First argument to script: imgPath
                pklPath,     // Second argument to script: file pkl
                studentID      // Third argument to script
        };

        // create ProcessBuilder
        ProcessBuilder pb = new ProcessBuilder(cmd);
        Process process = pb.start();

        // read output from script Python
        BufferedReader bfr = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = bfr.readLine()) != null) {
            System.out.println(line);
        }

        // wait and recive output from Python
        int exitCode = process.waitFor();
        System.out.println("Exited with code: " + exitCode);
    }
    public static void deleteByNummerPy(String nummer) throws IOException, InterruptedException {
        String pythonPathPKL =FileConstant.EMBEDDINGS_PKL_PATH;
        String pythonPath = FileConstant.PYTHON_DELETE_BY_NUMMER_PATH;
        log.info("nummer:"+nummer);
        String[] cmd = {
                FileConstant.pythonInterpreter,
                pythonPath,
                pythonPathPKL,

                nummer
        };

        ProcessBuilder pb = new ProcessBuilder(cmd);
        Process process = pb.start();

        BufferedReader bfr = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = bfr.readLine()) != null) {
            log.info("line"+line);
        }

        int exitCode = process.waitFor();
        log.info("Exited with code: " + exitCode);

    }
    public static String searchByIMG() throws IOException, InterruptedException {
        String pythonPath= FileConstant.PYTHON_IDENTIFY_STUDENT_PATH;
        String imagePath= FileConstant.TEMP_IMG_PATH;
        String pklPath= FileConstant.EMBEDDINGS_PKL_PATH;
        String[] cmd = {
                FileConstant.pythonInterpreter,
                pythonPath,
                imagePath,
                pklPath,
        };
        ProcessBuilder pb = new ProcessBuilder(cmd);
        Process process = pb.start();

        BufferedReader bfr = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        String lastLine = "";
        while ((line = bfr.readLine()) != null) {
            lastLine = line;
        }
        int exitCode = process.waitFor();
        return lastLine;
    }
}
