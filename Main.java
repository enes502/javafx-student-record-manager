package application;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    StudentManager studentManager = new StudentManager();

    public List<StudentRecord> records = new ArrayList<>();
    public List<OtherData> otherDataList = new ArrayList<>();
    public ListView<String> RLV; // recordListView
    public ListView<String> ODLV; // otherDataListView

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(studentManager.getUserName());
        
        Button MRB = new Button("Manage Records");
        MRB.setOnAction(e -> openAddRecordFrame());
        
        Button RAIB = new Button("Random Animal Info");
        RAIB.setOnAction(e -> AnimalTriviaUtility.openRandomAnimalInfoFrame());
        
        Button ODB = new Button("Other Data");
        ODB.setOnAction(e -> openOtherDataDialog());
        
        Button DRB = new Button("Delete Record");
        DRB.setOnAction(e -> deleteStudentRecord());
        
        Button DODB = new Button("Delete Other Data");
        DODB.setOnAction(e -> deleteOtherDataRecord());
        
        Button calcAgeBtn = new Button("Calculate Average Age");
        calcAgeBtn.setOnAction(e -> calculateAverageAge());

        Button STFB = new Button("Save Text File");
        STFB.setOnAction(e -> {
            saveToTextFile("alperen.txt");
        });

        RLV = new ListView<>();
        RLV.setPrefSize(345, 212);

        ODLV = new ListView<>();
        ODLV.setPrefSize(313, 234);

        loadRecordsFromFile();
        loadOtherDataFromFile();

        VBox vbox = new VBox(11);
        vbox.setPadding(new Insets(22));
        vbox.getChildren().addAll(MRB, RAIB, ODB, RLV, DRB, ODLV, DODB, calcAgeBtn, STFB);

        Scene scene = new Scene(vbox, 413, 523);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openAddRecordFrame() {
        Stage addStage = new Stage();
        addStage.setTitle("Add Student Record");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(21));
        grid.setVgap(11);
        grid.setHgap(11);

        TextField nameField = new TextField();
        TextField lastNameField = new TextField();
        TextField ageField = new TextField();

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("Age:"), 0, 2);
        grid.add(ageField, 1, 2);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String lastName = lastNameField.getText();
            int age = Integer.parseInt(ageField.getText());

            StudentRecord record = new StudentRecord(name, lastName, age);
            records.add(record);
            saveRecordsToFile();
            updateListView();
            addStage.close();
        });

        grid.add(addButton, 1, 3);

        Scene scene = new Scene(grid, 456, 253);
        addStage.setScene(scene);
        addStage.show();
    }
    
    private void openOtherDataDialog() {
        Stage otherDataStage = new Stage();
        otherDataStage.setTitle("Other Data");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        TextField TF1 = new TextField();
        TextField TF2 = new TextField();

        grid.add(new Label("Name:"), 0, 0);
        grid.add(TF1, 1, 0);
        grid.add(new Label("Last name:"), 0, 1);
        grid.add(TF2, 1, 1);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            String value1 = TF1.getText();
            String value2 = TF2.getText();

            OtherData otherData = new OtherData(value1, value2);
            otherDataList.add(otherData);
            saveOtherDataToFile();
            updateOtherDataListView();
            otherDataStage.close();
        });

        grid.add(addButton, 1, 4);

        Scene scene = new Scene(grid, 482, 257);
        otherDataStage.setScene(scene);
        otherDataStage.show();
    }
    
    private void deleteStudentRecord() {
        int SI = RLV.getSelectionModel().getSelectedIndex();
        if (SI >= 0) {
            records.remove(SI);
            saveRecordsToFile();
            updateListView();
        }
    }
    
    private void deleteOtherDataRecord() {
        int SI = ODLV.getSelectionModel().getSelectedIndex();
        if (SI >= 0) {
            otherDataList.remove(SI);
            saveOtherDataToFile();
            updateOtherDataListView();
        }
    }
    
    private void calculateAverageAge() {
        if (records.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No records.");
            return;
        }
        
        int TA = 0;
        for (StudentRecord R : records) {
            TA += R.getAge();
        }

        double AA = (double) TA / records.size();

        JOptionPane.showMessageDialog(null, "Average Age: " + String.format("%.2f", AA));
    }

    private void updateListView() {
        List<String> Kisa = new ArrayList<>();
        for (StudentRecord record : records) {
            Kisa.add(record.getName() + " " + record.getLastName() + " - Age: " + record.getAge());
        }
        RLV.setItems(FXCollections.observableArrayList(Kisa));
    }

    private void updateOtherDataListView() {
        List<String> Kisa = new ArrayList<>();
        for (OtherData D : otherDataList) {
            Kisa.add(D.getValue1() + " - " + D.getValue2());
        }
        ODLV.setItems(FXCollections.observableArrayList(Kisa));
    }

    private void saveRecordsToFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("records.dat"))) {
            outputStream.writeObject(records);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveOtherDataToFile() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("otherdata.dat"))) {
            outputStream.writeObject(otherDataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadRecordsFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("records.dat"))) {
            records = (List<StudentRecord>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadOtherDataFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("otherdata.dat"))) {
            otherDataList = (List<OtherData>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveToTextFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Student Records:\n");
            for (StudentRecord record : records) {
                writer.write(record.getName() + " " + record.getLastName() + " - Age: " + record.getAge() + "\n");
            }

            writer.write("\nOther Data:\n");
            for (OtherData data : otherDataList) {
                writer.write(data.getValue1() + " - " + data.getValue2() + "\n");
            }

            System.out.println("Text file saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class StudentRecord implements Serializable {
        private String name;
        private String lastName;
        private int age;

        public StudentRecord(String name, String lastName, int age) {
            this.name = name;
            this.lastName = lastName;
            this.age = age;
        }

        public String getName() { return name; }
        public String getLastName() { return lastName; }
        public int getAge() { return age; }
    }

    public static class OtherData implements Serializable {
        public String TF1;
        public String TF2;

        public OtherData(String value1, String value2) {
            this.TF1 = value1;
            this.TF2 = value2;
        }

        public String getValue1() { return TF1; }
        public String getValue2() { return TF2; }
    }
}
