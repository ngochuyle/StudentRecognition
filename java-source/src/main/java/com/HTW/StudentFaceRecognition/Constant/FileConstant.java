package com.HTW.StudentFaceRecognition.Constant;

import lombok.extern.log4j.Log4j2;

import java.io.File;

public class FileConstant {
    static String  currentPath = new File(".").getAbsolutePath();
    public static final String BASE_PATH = currentPath+"/Student/";
    public static final String IMAGES_DIR = "OriginalImages";
    public static final String EMBEDDINGS_PKL_PATH=BASE_PATH+"Recognizer/embeddings.pkl";
    public static final String TEMP_IMG_PATH=BASE_PATH+"IMGtemp/temp.jpg";
    public static final String PYTHON_Face_Reconition_PATH=currentPath+"/python/FaceReconition.py";
    public  static final String PYTHON_IDENTIFY_STUDENT_PATH=currentPath+"/python/identify_student.py";
    public static final  String PYTHON_DELETE_BY_NUMMER_PATH=currentPath+"/python/deleteByNummer.py";
    public static final String pythonInterpreter = currentPath+"/python/venv/Scripts/python.exe";

    public static final String TEMP_PATH =BASE_PATH+"IMGtemp/";

    public static void main(String[] args) {
        System.out.println(currentPath);
    }
}
