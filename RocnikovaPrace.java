package com.example.zemepis;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RocnikovaPrace extends Application {

    //Uživatel
    Player user = new Player();

    //Pódium hry
    Stage primaryStage;

    GridPane gamePane = new GridPane();

    //Počítadlo bodů
    Label points = new Label("0");

    //Obrázek státu
    ImageView image = new ImageView();

    //Tlačítka s odpovědmi
    Button buttonA = new Button ();
    Button buttonB = new Button ();
    Button buttonC = new Button ();
    Button buttonD = new Button ();

    //Seznam států
    ArrayList<Country> countries = new ArrayList<>();

    //Seznam názvů státu
    ArrayList<String> countriesNames = new ArrayList<>();

    //Aktuálně vybraný stát
    Country actualCountry;

    //Začátek hry
    private void startGame() {
        primaryStage.setTitle ("Co je to za stát?");

        //Nadefinované státy, obrázky států a jejich názvy
        countries.add(new Country ("Austrálie", "Australia.jpg"));
        countries.add(new Country ("Rakousko", "Austria.jpg"));
        countries.add(new Country ("Bělorusko", "Belarus.jpg"));
        countries.add(new Country ("Brazílie", "Brazil.jpg"));
        countries.add(new Country ("Kanada", "Canada.jpg"));
        countries.add(new Country ("Čína", "China.jpg"));
        countries.add(new Country ("Chorvatsko", "Croatia.jpg"));
        countries.add(new Country ("Kypr", "Cyprus.jpg"));
        countries.add(new Country ("Dánsko", "Denmark.jpg"));
        countries.add(new Country ("Egypt", "Egypt.jpg"));
        countries.add(new Country ("Etiopie", "Ethiopia.jpg"));
        countries.add(new Country ("Francie", "France.jpg"));
        countries.add(new Country ("Německo", "Germany.jpg"));
        countries.add(new Country ("Řecko", "Greece.jpg"));
        countries.add(new Country ("Island", "Iceland.jpg"));
        countries.add(new Country ("Izrael", "Israel.jpg"));
        countries.add(new Country ("Japonsko", "Japan.jpg"));
        countries.add(new Country ("Libye", "Libya.jpg"));
        countries.add(new Country ("Mongolsko", "Mongolia.jpg"));
        countries.add(new Country ("Nizozemsko", "Netherlands.jpg"));
        countries.add(new Country ("Nový Zéland", "New Zealand.jpg"));
        countries.add(new Country ("Norsko", "Norway.jpg"));
        countries.add(new Country ("Polsko", "Poland.jpg"));
        countries.add(new Country ("Rusko", "Russia.jpg"));
        countries.add(new Country ("Saudská Arábie", "Saudi Arabia.jpg"));
        countries.add(new Country ("Jihoafrická republika", "South Africa.jpg"));
        countries.add(new Country ("Thajsko", "Thailand.jpg"));
        countries.add(new Country ("Turecko", "Turkey.jpg"));
        countries.add(new Country ("Velká Británie", "UK.jpg"));
        countries.add(new Country ("Vietnam", "Vietnam.jpg"));

        //Každýmu státu byl přidělen svůj název
        countries.forEach((country) -> countriesNames.add (country.getName()));

        prepareGameScene();
        newRound();

    }

    //Příprava hrací scény
    private void prepareGameScene() {

        //Rozložení úrovně
        gamePane.setPadding(new Insets (0, 0, 0, 25));
        gamePane.setVgap (20);
        gamePane.getColumnConstraints ().add(new ColumnConstraints (220));
        gamePane.getColumnConstraints ().add(new ColumnConstraints (220));
        gamePane.setStyle("-fx-background-color:#FFFFFF");


        //Uživatelské jméno
        Label username = new Label(user.getUsername());
        username.setFont (new Font (20));
        gamePane.add(username, 0,0);

        //Body uživatele
        gamePane.add(points, 1, 0);
        points.setFont (new Font (20));
        GridPane.setHalignment(points, HPos.RIGHT);

        //Obrázek
        image.setFitHeight(350);
        image.setFitWidth(350);
        image.setPreserveRatio(true);
        gamePane.add (image, 0, 1, 2, 2);
        GridPane.setHalignment (image, HPos.CENTER);


        //Nastavení tlačítek
        buttonA.setTextFill (Color.DARKBLUE);
        buttonB.setTextFill (Color.DARKBLUE);
        buttonC.setTextFill (Color.DARKBLUE);
        buttonD.setTextFill (Color.DARKBLUE);

        buttonA.setPrefWidth(200);
        buttonA.setPrefHeight (40);
        buttonA.setStyle ("-fx-background-color: #FF9900");
        buttonB.setPrefWidth(200);
        buttonB.setPrefHeight (40);
        buttonB.setStyle ("-fx-background-color: #FF9900");
        buttonC.setPrefWidth(200);
        buttonC.setPrefHeight (40);
        buttonC.setStyle ("-fx-background-color: #FF9900");
        buttonD.setPrefWidth(200);
        buttonD.setPrefHeight (40);
        buttonD.setStyle ("-fx-background-color: #FF9900");

        GridPane.setHalignment (buttonA, HPos.CENTER);
        GridPane.setHalignment (buttonB, HPos.CENTER);
        GridPane.setHalignment (buttonC, HPos.CENTER);
        GridPane.setHalignment (buttonD, HPos.CENTER);

        gamePane.add (buttonA, 0, 3);
        gamePane.add (buttonB, 1, 3);
        gamePane.add (buttonC, 0, 4);
        gamePane.add (buttonD, 1, 4);

        buttonA.setOnAction((ActionEvent actionEvent) -> { checkResults ((Button) actionEvent.getSource()); });
        buttonB.setOnAction((ActionEvent actionEvent) -> { checkResults ((Button) actionEvent.getSource()); });
        buttonC.setOnAction((ActionEvent actionEvent) -> { checkResults ((Button) actionEvent.getSource()); });
        buttonD.setOnAction((ActionEvent actionEvent) -> { checkResults ((Button) actionEvent.getSource()); });

        Scene gameScene = new Scene(gamePane);
        primaryStage.setScene (gameScene);

    }

    //Kontrola výsledků
    private void checkResults(Button b) {
        if(b.getText().equals(actualCountry.getName())) { //Pokud byl vybrán správný stát
            user.setPoints(user.getPoints ()+1);
            if(countries.size () > 0) { //Načte se nové kolo
                newRound ();

            } else { //Pokud uživatel dohrál hru
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Gratuluji. Dosáhli jste maximálního počtu bodů " + user.getPoints ());
                alert.showAndWait();
                System.exit(0);

            }

        } else { //Pokud byla vybrána špatná odpověď
            Alert alert = new Alert(Alert.AlertType.ERROR, "Špatná odpověď. Správná odpověd byla " + actualCountry.getName() + ". Dosáhli jste bodů: " + user.getPoints ());
            alert.showAndWait();
            System.exit(0);
        }

    }

    //Úrovně hry
    private void newRound() {

        //Zamíchá státy
        Collections.shuffle(countries);
        actualCountry = countries.get(0);
        countries.remove(0);

        //Zamíchá názvy států
        Collections.shuffle(countriesNames);

        ArrayList<String> roundNames = new ArrayList<>();
        roundNames.add(actualCountry.getName()); //Do pole roundNames si uložím stát příslušný k obrázku

        //Procházíme názvy států a hledáme 3 jiný názvy od toho aktuálního
        countriesNames.forEach((String name) -> {
            if(roundNames.size() == 4) { //Pole roundNames obsahuje čtyři názvy států
                return;
            }
            if(!name.equals(actualCountry.getName())) { //Pokud se název neshoduje s obrázkem, přidá se do pole
                roundNames.add(name);
            }
        });

        //Zamíchá 4 vybrané názvy států a vloží je do tlačítek
        Collections.shuffle(roundNames);

        buttonA.setText(roundNames.get(0));
        buttonB.setText(roundNames.get(1));
        buttonC.setText(roundNames.get(2));
        buttonD.setText(roundNames.get(3));


        image.setImage(new Image(actualCountry.getImage()));
        if(user.isHard ()) { //Pokud byla zvolena těžká obtížnost, obrázek se otočí
            image.setRotate (((new Random()).nextInt(3)+1)*90);
        }

        //Ukazatel bodů
        points.setText("Počet bodů: " + user.getPoints());
    }

    //Úvodní stránka
    private void showSettings() {

        primaryStage.setTitle ("Tvar státu");

        Label label = new Label ("Hádání státu"); //Popisek

        Text usernameText = new Text ("Vaše jméno: ");
        usernameText.setFont (new Font (20));

        Text level = new Text ("Úroveň hry");
        level.setFont(new Font(20));
        label.setFont (Font.font (null, FontWeight.EXTRA_BOLD, 40));

        //Tlačítko pro zahájení hry
        Button button = new Button ("Spustit hru");
        button.setPrefHeight(40);
        button.setPrefWidth (200);
        button.setFont (new Font (20));
        button.setTextFill (Color.DARKBLUE);
        button.setStyle ("-fx-background-color:#FF9900");

        ToggleGroup obtiznosti = new ToggleGroup ();

        //Obtížnosti
        RadioButton easy = new RadioButton ("Lehká");
        easy.setSelected (true);
        easy.setToggleGroup (obtiznosti);
        RadioButton hard = new RadioButton ("Težká");
        hard.setToggleGroup(obtiznosti);

        //Textové pole
        TextField textField1 = new TextField ();
        textField1.setMaxWidth (145);

        GridPane gridPane = new GridPane();

        //po Gridpanu rozmístím text, tlačítka a textové pole
        GridPane.setHalignment (label, HPos.CENTER);
        GridPane.setHalignment (usernameText, HPos.CENTER);
        GridPane.setHalignment (textField1, HPos.LEFT);
        GridPane.setHalignment (level, HPos.CENTER);
        GridPane.setHalignment (easy, HPos.CENTER);
        GridPane.setHalignment (hard, HPos.CENTER);
        GridPane.setHalignment (level, HPos.CENTER);
        GridPane.setHalignment (button, HPos.CENTER);

        gridPane.setAlignment (Pos.TOP_CENTER);
        gridPane.setPadding(new Insets (30, 0, 0, 15));
        gridPane.setVgap (50);
        gridPane.getColumnConstraints ().add(new ColumnConstraints (220));
        gridPane.getColumnConstraints ().add(new ColumnConstraints (220));
        gridPane.setStyle("-fx-background-color:#FFFFFF");



        button.setOnAction (new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(textField1.getText().isEmpty()) { //Když je textové pole prázdné
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Zadejte své uživatelské jméno prosím");
                    alert.show();
                    return;
                }
                user.setHard(hard.isSelected());
                user.setUsername (textField1.getText());

                startGame();
            }
        });

        //Text, tlačítka a textové pole mají každá svoji lokaci po úvodní stránce
        gridPane.add (label, 0, 0, 2, 1);
        gridPane.add (usernameText, 0, 1);
        gridPane.add (textField1,1, 1);
        gridPane.add (level, 0, 2, 2, 1);
        gridPane.add (easy, 0, 3);
        gridPane.add (hard, 1, 3);
        gridPane.add (button, 0, 5, 2, 1);

        Scene scene = new Scene (gridPane);

        primaryStage.setScene (scene);
        primaryStage.show();
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setResizable (false);
        primaryStage.setHeight(600);
        primaryStage.setWidth(500);
        showSettings();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
