package application;

import java.util.Random;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public interface AnimalTriviaUtility {

    public static void openRandomAnimalInfoFrame() {
        Stage randomAnimalInfoStage = new Stage();
        randomAnimalInfoStage.setTitle("Random Animal Information");

        Label animalInfoLabel = new Label(generateRandomAnimalInfo());

        VBox vbox = new VBox(11);
        vbox.setPadding(new Insets(22));
        vbox.getChildren().addAll(animalInfoLabel);

        Scene scene = new Scene(vbox, 431, 322);
        randomAnimalInfoStage.setScene(scene);
        randomAnimalInfoStage.show();
    }

    public static String generateRandomAnimalInfo() {
        String[] animals = {"Lion", "Elephant", "Tiger", "Giraffe", "Zebra", "Hippo", "Gorilla", "Penguin", "Kangaroo", "Cheetah"};
        String[] facts = {
            "Lions are the only cats that live in groups.", 
            "Elephants are the world's largest land animals.",
            "Tigers have striped skin as well as their fur.", 
            "Giraffes only need to drink once every few days.",
            "Zebras are black with white stripes.", 
            "Hippos can run faster than humans.", 
            "Gorillas can catch human colds and other illnesses.",
            "Penguins can drink sea water.", 
            "Kangaroos cannot walk backwards.", 
            "Cheetahs are the fastest land animals."
        };

        Random rand = new Random();
        int index = rand.nextInt(animals.length);
        String animal = animals[index];
        String fact = facts[index];
        return "Animal: " + animal + "\nFact: " + fact;
    }
}
