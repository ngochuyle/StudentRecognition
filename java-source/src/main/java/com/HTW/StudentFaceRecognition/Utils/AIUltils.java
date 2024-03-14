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
        // Xây dựng lệnh để chạy script Python với đường dẫn và các tham số
        String[] cmd = {
                FileConstant.pythonInterpreter,  // Hoặc 'python3' tùy thuộc vào môi trường của bạn
                pythonPath,     // Đường dẫn đến script Python
                imagePath,     // Đối số thứ nhất cho script imgPath
                pklPath,     // Đối số thứ hai cho script file pkl
                studentID      // Đối số thứ ba cho script
        };

        // Tạo và thực thi quá trình với ProcessBuilder
        ProcessBuilder pb = new ProcessBuilder(cmd);
        Process process = pb.start();

        // Đọc output từ script Python
        BufferedReader bfr = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = bfr.readLine()) != null) {
            System.out.println(line);
        }

        // Đợi quá trình kết thúc và lấy mã kết thúc
        int exitCode = process.waitFor();
        System.out.println("Exited with code: " + exitCode);
    }
    public static void deleteByNummerPy(String nummer) throws IOException, InterruptedException {
        String pythonPathPKL =FileConstant.EMBEDDINGS_PKL_PATH;
        String pythonPath = FileConstant.PYTHON_DELETE_BY_NUMMER_PATH;
        log.info("nummer:"+nummer);
        String[] cmd = {
                FileConstant.pythonInterpreter,// Hoặc 'python3' tùy thuộc vào môi trường của bạn
                pythonPath,
                pythonPathPKL,     // Đường dẫn đến script Pyhon

                nummer      // Đối số thứ ba cho script
        };

        // Tạo và thực thi quá trình với ProcessBuilder
        ProcessBuilder pb = new ProcessBuilder(cmd);
        Process process = pb.start();

        // Đọc output từ script Python
        BufferedReader bfr = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = bfr.readLine()) != null) {
            log.info("line"+line);
        }

        // Đợi quá trình kết thúc và lấy mã kết thúc
        int exitCode = process.waitFor();
        log.info("Exited with code: " + exitCode);

    }
    public static String searchByIMG() throws IOException, InterruptedException {
        String pythonPath= FileConstant.PYTHON_IDENTIFY_STUDENT_PATH;
        String imagePath= FileConstant.TEMP_IMG_PATH;
        String pklPath= FileConstant.EMBEDDINGS_PKL_PATH;
        String[] cmd = {
                FileConstant.pythonInterpreter,  // Hoặc 'python3' tùy thuộc vào môi trường của bạn
                pythonPath,     // Đường dẫn đến script Python
                imagePath,     // Đối số thứ nhất cho script imgPath
                pklPath,     // Đối số thứ hai cho script file pkl
        };
        ProcessBuilder pb = new ProcessBuilder(cmd);
        Process process = pb.start();

        // Đọc output từ script Python
        BufferedReader bfr = new BufferedReader(new InputStreamReader(process.getInputStream()));
        //BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String line;
        String lastLine = "";
        while ((line = bfr.readLine()) != null) {
            lastLine = line; // Cập nhật dòng cuối cùng
        }
        //String s = "loi";
        /*while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }*/

        // Đợi quá trình kết thúc và lấy mã kết thúc
        int exitCode = process.waitFor();
        return lastLine;
    }
}
