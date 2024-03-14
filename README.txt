Bevor Sie das Projekt ausführen, stellen Sie bitte sicher, dass die folgenden Umgebungen installiert sind:
1. Java:
- Java. Empfohlene Version: Java 17.
- Maven installieren.
2. Python:
- Python3
3. Frontend:
-NodeJS
-npm
-Yarn
4. MySQL:
Bitte stellen Sie sicher, dass das Root-Konto das Passwort 12345 hat (das Passwort ist in der JAR-Datei verpackt). Der Quellcode kann in der Datei application.properties angepasst werden.

Anweisungen zum Ausführen des Projekts:
Um das Projekt auszuführen, befolgen Sie bitte diese Schritte:
- Führen Sie Serve Java aus: Gehen Sie zum Verzeichnispfad: ./server-anwendung/
java -jar StudentFaceRecognition-0.0.2.jar
- Führen Sie den Client aus:
cd ./template/my-project/
yarn run dev

Das Programm verfügt über integrierte Bibliotheken. Wenn dies fehlschlägt, müssen Sie möglicherweise einen Neuaufbau durchführen.
1. Python: Gehen Sie in das Verzeichnis ./python.
- python -m venv venv
- venv\Scripts\activate
- pip install -r Anforderungen.txt
2. Client: Gehen Sie zum Ordner template/my-project:
-Yarn install
-Yarn run dev
3. Servieren: Gehen Sie in das Verzeichnis java/
- mvn clean install
- Kopieren Sie die .jar-Datei im neu generierten Ziel in das ursprüngliche Verzeichnis. Gehen Sie wie oben beschrieben vor.
Hinweis: Stellen Sie beim Hinzufügen neue Student sicher, dass jede Student 3 Fotos hat und keine Student das gleiche Foto wie ein andere Student hat. Zu Testzwecken habe ich sNummer auch einen Bildordner erstellt. Zu finden unter ./server-anwendung/img/testIMG.
Darüber hinaus hat Datenbank derzeit Studierende: s79547 – s79550.
Administratorkonto: Benutzername: admin, Passwort: 123456.
