# StudentRecognition
Welcome to my project repository!

This project is an application that integrates knowledge acquired at HTW (University of Applied Sciences) along with insights gained during internship periods. My focus lies in addressing a specific issue: "student attendance tracking through facial recognition." I've developed a website to fulfill this function, incorporating applications and features for student management, exams, and facial recognition-based attendance tracking.
## Table of Contents
- [Algorithm Overview](#algorithm-overview)
- [Technologies Used](#technologies-used)
- [Project Deployment](#project-deployment)
  - [Project Environment Setup](#project-environment-setup)
  - [Install](#install)
  - [Running the Project Instructions](#running-the-project-instructions)
- [Contact Information](#contact-information)

## Algorithm Overview:

I tackle the facial recognition task using the following algorithms:
- MTCNN: Detects faces in images.
- Facenetz: Extracts features into vectors.
- Euclidean space: Compares two vectors.
Detailed algorithmic theory can be found in ./Bachelorarbeit.pdf.

## Technologies Used:

In addition to facial recognition theories, essential technologies for web application development include:
- Frontend: VueJS, VueX, Vue Router (located in ./template/).
- Python and Pytorch library: Implements facial recognition algorithms (located in ./server-anwendung/python/).
- Database: MySQL.
- Backend: Java along with technologies such as Java Spring Boot and Spring Security.
During backend development, emphasis was placed on object-oriented programming, MVC model for designing and creating APIs to serve the web application. Additionally, the project incorporates communication mechanisms between Java and Python via ProcessBuilder. I also apply simple design patterns like Singleton pattern and Builder.

## Project Deployment:

### Project Environment Setup

Before running the project, please ensure that the following environments are installed:

1. Java:
   - Java. Recommended Version: Java 17.
   - Install Maven.

2. Python:
   - Python3.

3. Frontend:
   - NodeJS.
   - npm.
   - Yarn.

4. MySQL:
   Please ensure that the root account has the password 12345 (the password is packed in the JAR file). The source code can be adjusted in the application.properties file.

### Install
1. Python: Navigate to the ./python directory.
   ```bash
   python -m venv venv
   venv\Scripts\activate
   pip install -r requirements.txt
2. Client: Navigate to the ./template/my-project directory:
   ```bash
   Yarn install
   Yarn run dev
4. Server: Navigate to the java/ directory:
   ```bash
    mvn clean install
Copy the .jar file in the newly generated target to the original directory. Follow the steps as described above.

Note: When adding new students, ensure that each student has 3 photos, and no student has the same photo as another student. For testing purposes, I have also created an image folder for the student ID. It can be found under ./server-anwendung/img/testIMG.
Furthermore, the database currently has students: s79547 – s79550.
Administrator account: Username: admin, Password: 123456.

### Running the Project Instructions:

To run the project, please follow these steps:
- Run Java Serve: Navigate to the directory path: ./server-anwendung/
  ```bash
  java -jar StudentFaceRecognition-0.0.2.jar
- Run the Client:
  ```bash
  cd ./template/my-project/
  yarn run dev

## Contact Information:

For direct communication, feel free to reach out via GitHub. For any inquiries, please email me at ngochuyle191@gmail.com.

Thank you for your interest in my project!
