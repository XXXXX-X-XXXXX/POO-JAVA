import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Region;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.text.TextAlignment;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.PasswordField;



public class BarApp extends Application {

    private StackPane createSlotsRoot(Runnable onBack) {
        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #2d1b00, #1a1a2e);");

        Label title = new Label("🎰 Machine à sous");
        title.setStyle("-fx-font-size: 34px; -fx-text-fill: #ffd369; -fx-font-weight: bold;");

        String[] symbols = {"🍒", "⭐", "💎"};

        Label r1 = new Label("🍒");
        Label r2 = new Label("💎");
        Label r3 = new Label("⭐");
        String reelStyle = "-fx-font-size: 80px; -fx-text-fill: #ffffff;";
        r1.setStyle(reelStyle);
        r2.setStyle(reelStyle);
        r3.setStyle(reelStyle);

        HBox reels = new HBox(25, r1, r2, r3);
        reels.setAlignment(Pos.CENTER);

        Label status = new Label("Mise 2.00€ (exemple). Clique SPIN !");
        status.setStyle("-fx-font-size: 14px; -fx-text-fill: #eeeeee;");

        Button spinBtn = new Button("SPIN");
        spinBtn.setStyle("-fx-font-size: 18px; -fx-padding: 10px 30px; "
                + "-fx-background-color: linear-gradient(to right, #ffcc00, #ff9900); "
                + "-fx-text-fill: #202020; -fx-background-radius: 30px; -fx-font-weight: bold;");

        Button backBtn = new Button("Retour");
        backBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 22px; "
                + "-fx-background-color: #444444; -fx-text-fill: #f5f5f5; -fx-background-radius: 30px;");

        spinBtn.setOnAction(e -> {
            spinBtn.setDisable(true);
            status.setText("🎲 Ça tourne...");

            Timeline t1 = new Timeline(new KeyFrame(Duration.millis(80), ev -> r1.setText(symbols[ThreadLocalRandom.current().nextInt(symbols.length)])));
            Timeline t2 = new Timeline(new KeyFrame(Duration.millis(80), ev -> r2.setText(symbols[ThreadLocalRandom.current().nextInt(symbols.length)])));
            Timeline t3 = new Timeline(new KeyFrame(Duration.millis(80), ev -> r3.setText(symbols[ThreadLocalRandom.current().nextInt(symbols.length)])));

            t1.setCycleCount(Animation.INDEFINITE);
            t2.setCycleCount(Animation.INDEFINITE);
            t3.setCycleCount(Animation.INDEFINITE);

            t1.play(); t2.play(); t3.play();

            PauseTransition stop1 = new PauseTransition(Duration.seconds(1.0));
            stop1.setOnFinished(ev -> t1.stop());
            stop1.play();

            PauseTransition stop2 = new PauseTransition(Duration.seconds(1.5));
            stop2.setOnFinished(ev -> t2.stop());
            stop2.play();

            PauseTransition stop3 = new PauseTransition(Duration.seconds(2.0));
            stop3.setOnFinished(ev -> {
                t3.stop();

                String a = r1.getText();
                String b = r2.getText();
                String c = r3.getText();

                if (a.equals(b) && b.equals(c)) {
                    status.setText("💰 Jackpot ! (" + a + a + a + ")");
                    // Ici plus tard tu crédites de l'argent
                } else {
                    status.setText("Perdu... (" + a + " " + b + " " + c + ")");
                }

                spinBtn.setDisable(false);
            });
            stop3.play();
        });

        backBtn.setOnAction(e -> onBack.run());

        HBox buttons = new HBox(12, spinBtn, backBtn);
        buttons.setAlignment(Pos.CENTER);

        VBox box = new VBox(22, title, reels, status, buttons);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-padding: 40px;");
        box.setMaxWidth(900);

        background.getChildren().add(box);
        return background;
    }


    private boolean casinoUnlocked = false;
    private static final String CASINO_CODE = "Antho";



    private StackPane createPaymentRoot(Drink drink, Runnable onPaid, Runnable onCancel) {
        double amount = drink.getPrice();

        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #111111, #1a1a2e);");

        Label title = new Label("Paiement");
        title.setStyle("-fx-font-size: 32px; -fx-text-fill: #ffd369; -fx-font-weight: bold;");

        Label total = new Label("Montant à payer : " + String.format("%.2f€", amount));
        total.setStyle("-fx-font-size: 16px; -fx-text-fill: #eeeeee;");

        // (optionnel) afficher le solde si c’est une carte
        String balanceText = "";
        if (client.getPaymentOptions() instanceof CreditCardPayment) {
            CreditCardPayment cc = (CreditCardPayment) client.getPaymentOptions();
            balanceText = "Solde carte : " + String.format("%.2f€", cc.getBalance());
        }
        Label balance = new Label(balanceText);
        balance.setStyle("-fx-font-size: 14px; -fx-text-fill: #dddddd;");

        // Champ secret (visible uniquement si boisson = CasiTail)
        PasswordField codeField = new PasswordField();
        codeField.setPromptText("Code VIP (optionnel)");
        codeField.setMaxWidth(260);
        codeField.setVisible(false);
        codeField.setManaged(false);

        if (drink.getName() != null && drink.getName().equalsIgnoreCase("CasiTail")) {
            codeField.setVisible(true);
            codeField.setManaged(true);
        }

        Label status = new Label("");
        status.setStyle("-fx-font-size: 14px; -fx-text-fill: #ffcc00; -fx-font-weight: bold;");

        Button payBtn = new Button("Payer");
        payBtn.setStyle("-fx-font-size: 16px; -fx-padding: 10px 25px; "
                + "-fx-background-color: linear-gradient(to right, #4ecdc4, #44a08d); "
                + "-fx-text-fill: white; -fx-background-radius: 30px; -fx-font-weight: bold;");

        Button cancelBtn = new Button("Annuler");
        cancelBtn.setStyle("-fx-font-size: 14px; -fx-padding: 8px 20px; "
                + "-fx-background-color: #444444; -fx-text-fill: #f5f5f5; -fx-background-radius: 30px;");

        payBtn.setOnAction(e -> {
            boolean ok = client.pay(amount);
            if (!ok) {
                status.setText("Paiement refusé : solde insuffisant.");
                return;
            }

            // Easter egg : si c’est CasiTail + code OK => casino direct
            String code = codeField.getText() == null ? "" : codeField.getText().trim();
            if (codeField.isVisible() && CASINO_CODE.equalsIgnoreCase(code)) {
                status.setText("✅ Code accepté... accès casino !");
                PauseTransition pause = new PauseTransition(Duration.seconds(0.8));
                pause.setOnFinished(ev -> switchRootWithFade(
                        createSlotsRoot(() -> switchRootWithFade(createBarMenuRoot()))
                ));
                pause.play();
                return;
            }

            status.setText("Paiement accepté !");
            PauseTransition pause = new PauseTransition(Duration.seconds(0.8));
            pause.setOnFinished(ev -> onPaid.run());
            pause.play();
        });

        cancelBtn.setOnAction(e -> onCancel.run());

        HBox buttons = new HBox(12, payBtn, cancelBtn);
        buttons.setAlignment(Pos.CENTER);

        // NOTE: codeField est inséré dans le VBox (entre balance et status)
        VBox box = new VBox(18, title, total, balance, codeField, status, buttons);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-padding: 40px;");
        box.setMaxWidth(800);

        background.getChildren().add(box);
        return background;
    }



    private void setupDrinkListCellFactory(ListView<Drink> drinkList) {
        drinkList.setCellFactory(lv -> new ListCell<Drink>() {
            @Override
            protected void updateItem(Drink drink, boolean empty) {
                super.updateItem(drink, empty);

                if (empty || drink == null) {
                    setText(null);
                    return;
                }

                String organicLabel = drink.isOrganic() ? " 🌿 Organic" : "";
                setText(getDrinkEmoji(drink) + " " + drink.getName()
                        + " - " + String.format("%.2f€", drink.getPrice()) + organicLabel
                        + " - Stock: " + drink.getStock());
            }
        });
    }


    private void consumeOneAndRestockIfNeeded(Drink drink, ListView<Drink> drinkList) {
        drink.decrementStock();
        drinkList.refresh(); // voir le stock baisser

        if (drink.getStock() == 0) {
            PauseTransition pause = new PauseTransition(Duration.seconds(30));
            pause.setOnFinished(e -> {
                drink.restock();
                drinkList.refresh(); // voir le stock remonter
            });
            pause.play();
        }
    }



    private StackPane createVomitRoot() {
        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #111111, #1a1a2e);");

        Label title = new Label("🤢 Tu vomis... le videur arrive !");
        title.setStyle("-fx-font-size: 30px; -fx-text-fill: #ff6b6b; -fx-font-weight: bold;");

        Label info = new Label("Tu es expulsé du bar.");
        info.setStyle("-fx-font-size: 14px; -fx-text-fill: #eeeeee;");

        VBox box = new VBox(15, title, info);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-padding: 40px;");
        background.getChildren().add(box);

        PauseTransition pause = new PauseTransition(Duration.seconds(2.5));
        pause.setOnFinished(e -> {
            drunkLevel = 0;           // reset (ou baisse seulement)
            selectedWaiter = null;    // optionnel
            switchRootWithFade(createMainMenuRoot()); // ou écran dehors du bar
        });
        pause.play();

        return background;
    }


    private int drunkLevel = 0;          // 0..100
    private static final int VOMIT_AT = 100;


    private double arrivalDelaySecondsLikeBartender(Barman barman) {
        int speed = barman.getSpeed(); // 1..10
        return Math.max(2.0, 7.0 - (speed * 0.5)); // comme tu voulais plus lent
    }

    private double preparationDelaySeconds(Barman barman) {
        int speed = barman.getSpeed(); // 1..10
        return Math.max(2.5, 8.0 - (speed * 0.55));
    }


    private StackPane createCounterPreparingRoot(Barman barman, String drinkLabel) {
        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #2d1b00, #1a1a2e);");

        double prep = preparationDelaySeconds(barman);

        Label title = new Label("🧑‍🍳 " + barman.getName() + " prépare ta boisson...");
        title.setStyle("-fx-font-size: 30px; -fx-text-fill: #ffd369; -fx-font-weight: bold;");

        Label drink = new Label("Boisson : " + drinkLabel);
        drink.setStyle("-fx-font-size: 16px; -fx-text-fill: #eeeeee;");

        Label info = new Label("⏳ Préparation : " + String.format("%.1f", prep) + "s (⚡ " + barman.getSpeed() + "/10)");
        info.setStyle("-fx-font-size: 14px; -fx-text-fill: #eeeeee;");

        VBox box = new VBox(15, title, drink, info);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-padding: 40px;");
        background.getChildren().add(box);

        PauseTransition pause = new PauseTransition(Duration.seconds(prep));
        pause.setOnFinished(e -> switchRootWithFade(createDrinkingRoot(drinkLabel, "comptoir")));
        pause.play();

        return background;
    }


    private StackPane createCounterOrderRoot(Barman barman) {
        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #2d1b00, #1a1a2e);");

        Label title = new Label("🧑‍🍳 " + barman.getName() + " : \"Tu prends quoi ?\"");
        title.setStyle("-fx-font-size: 28px; -fx-text-fill: #ffd369; -fx-font-weight: bold;");

        ListView<Drink> drinkList = new ListView<>();

        for (Drink drink : bar.getMenu()) {
            if (!client.canOrderAlcohol() && drink.isAlcoholic()) continue;
            drinkList.getItems().add(drink);
        }

        setupDrinkListCellFactory(drinkList);

        drinkList.setMaxHeight(240);

        Label info = new Label("Sélectionne une boisson puis clique sur Commander.");
        info.setStyle("-fx-font-size: 14px; -fx-text-fill: #eeeeee;");

        Button orderButton = new Button("🛎️ Commander");
        orderButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px 25px;" +
                "-fx-background-color: linear-gradient(to right, #ffcc00, #ff9900);" +
                "-fx-text-fill: #202020; -fx-background-radius: 30px; -fx-font-weight: bold;");

        Button backButton = new Button("⬅️ Se lever");
        backButton.setStyle("-fx-font-size: 14px; -fx-padding: 8px 20px;" +
                "-fx-background-color: #444444; -fx-text-fill: #f5f5f5;" +
                "-fx-background-radius: 30px;");

        orderButton.setOnAction(e -> {
            Drink selectedDrink = drinkList.getSelectionModel().getSelectedItem();
            if (selectedDrink == null) {
                info.setText("⚠️ Choisis une boisson !");
                return;
            }

            if (selectedDrink.getStock() <= 0) {
                info.setText("Rupture de stock, réappro dans 30s.");
                return;
            }

            double amount = selectedDrink.getPrice();

            switchRootWithFade(createPaymentRoot(
                    selectedDrink,
                    () -> { // payé -> là seulement on enlève le stock et on continue
                        consumeOneAndRestockIfNeeded(selectedDrink, drinkList);
                        selectedDrinkLabel = selectedDrink.getName();
                        switchRootWithFade(createCounterPreparingRoot(barman, selectedDrinkLabel));
                    },
                    () -> { // annulé -> retour au menu comptoir
                        switchRootWithFade(createCounterOrderRoot(barman));
                    }
            ));

        });

        backButton.setOnAction(e -> switchRootWithFade(createBarMenuRoot()));

        HBox buttons = new HBox(12, orderButton, backButton);
        buttons.setAlignment(Pos.CENTER);

        VBox box = new VBox(18, title, drinkList, info, buttons);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-padding: 40px;");
        box.setMaxWidth(900);

        background.getChildren().add(box);
        return background;
    }


    private StackPane createCounterBartenderArrivesRoot() {
        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #2d1b00, #1a1a2e);");

        Barman barman = getBartender();
        if (barman == null) {
            return createMessageRoot("❌ Aucun barman disponible.", () -> switchRootWithFade(createBarMenuRoot()));
        }

        double delay = arrivalDelaySecondsLikeBartender(barman);

        Label title = new Label("🍸 " + barman.getName() + " arrive au comptoir...");
        title.setStyle("-fx-font-size: 32px; -fx-text-fill: #ffd369; -fx-font-weight: bold;");

        Label info = new Label("⏳ " + String.format("%.1f", delay) + "s (⚡ " + barman.getSpeed() + "/10)");
        info.setStyle("-fx-font-size: 14px; -fx-text-fill: #eeeeee;");

        VBox box = new VBox(15, title, info);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-padding: 40px;");
        background.getChildren().add(box);

        PauseTransition pause = new PauseTransition(Duration.seconds(delay));
        pause.setOnFinished(e -> switchRootWithFade(createCounterOrderRoot(barman)));
        pause.play();

        return background;
    }


    private Barman getBartender() {
        for (Employee e : bar.getStaff()) {
            if (e instanceof Barman b) return b;
        }
        return null;
    }


    private StackPane createBouncerAcceptedRoot(Bouncer bouncer) {
        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #111111, #1a1a2e);");

        Label title = new Label("✅ " + bouncer.getName() + " : \"Vas-y, tu peux entrer.\"");
        title.setStyle("-fx-font-size: 28px; -fx-text-fill: #4ecdc4; -fx-font-weight: bold;");

        Label info = new Label("Bienvenue au CasiBAR !");
        info.setStyle("-fx-font-size: 14px; -fx-text-fill: #eeeeee;");

        background.getChildren().add(new VBox(15, title, info));
        ((VBox) background.getChildren().getFirst()).setAlignment(Pos.CENTER);

        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(e -> switchRootWithFade(createBarMenuRoot()));
        pause.play();

        return background;
    }


    private StackPane createBouncerRefusedRoot(Bouncer bouncer) {
        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #111111, #1a1a2e);");

        Label title = new Label("🚫 " + bouncer.getName() + " : \"Tu rentres pas.\"");
        title.setStyle("-fx-font-size: 30px; -fx-text-fill: #ff6b6b; -fx-font-weight: bold;");

        Button retry = new Button("🔁 Re-tenter avec l'autre videur");
        retry.setStyle("-fx-font-size: 16px; -fx-padding: 10px 25px; -fx-background-color: #444444; -fx-text-fill: white; -fx-background-radius: 30px; -fx-font-weight: bold;");

        Button leave = new Button("🚪 Partir");
        leave.setStyle("-fx-font-size: 16px; -fx-padding: 10px 25px; -fx-background-color: #333333; -fx-text-fill: #f5f5f5; -fx-background-radius: 30px;");

        retry.setOnAction(e -> switchRootWithFade(createBouncerSelectionRoot()));
        leave.setOnAction(e -> switchRootWithFade(createMainMenuRoot()));

        HBox buttons = new HBox(12, retry, leave);
        buttons.setAlignment(Pos.CENTER);

        VBox content = new VBox(25, title, buttons);
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-padding: 40px;");

        background.getChildren().add(content);
        return background;
    }


    private StackPane createBouncerDecisionRoot(Bouncer bouncer) {
        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #111111, #1a1a2e);");

        int refuse = refuseChancePercent(bouncer);

        Label title = new Label("⏳ " + bouncer.getName() + " vérifie...");
        title.setStyle("-fx-font-size: 30px; -fx-text-fill: #ffd369; -fx-font-weight: bold;");

        Label info = new Label("Risque de se faire recaler: " + refuse + "%");
        info.setStyle("-fx-font-size: 14px; -fx-text-fill: #eeeeee;");

        VBox box = new VBox(15, title, info);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-padding: 40px;");

        background.getChildren().add(box);

        // délai visible (augmente si tu veux)
        PauseTransition pause = new PauseTransition(Duration.seconds(2.5));
        pause.setOnFinished(e -> {
            boolean refused = bouncerRefuses(bouncer);
            if (refused) {
                switchRootWithFade(createBouncerRefusedRoot(bouncer));
            } else {
                switchRootWithFade(createBouncerAcceptedRoot(bouncer));
            }
        });
        pause.play();

        return background;
    }


    private StackPane createBouncerCheckRoot(Bouncer bouncer) {
        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #111111, #1a1a2e);");

        Label title = new Label("🛑 " + bouncer.getName() + " : \"Prénom et âge.\"");
        title.setStyle("-fx-font-size: 28px; -fx-text-fill: #ffd369; -fx-font-weight: bold;");

        Label subtitle = new Label("Recopie tes infos pour entrer.");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #eeeeee;");

        TextField nameField = new TextField();
        nameField.setPromptText("Prénom");
        TextField ageField = new TextField();
        ageField.setPromptText("Âge");

        nameField.setMaxWidth(320);
        ageField.setMaxWidth(320);

        Label error = new Label("");
        error.setStyle("-fx-font-size: 13px; -fx-text-fill: #ff6b6b; -fx-font-weight: bold;");

        Button validate = new Button("✅ Donner mes infos");
        validate.setStyle("-fx-font-size: 16px; -fx-padding: 10px 25px; -fx-background-color: linear-gradient(to right, #ffcc00, #ff9900); -fx-text-fill: #202020; -fx-background-radius: 30px; -fx-font-weight: bold;");

        Button back = new Button("⬅️ Retour");
        back.setStyle("-fx-font-size: 14px; -fx-padding: 8px 20px; -fx-background-color: #333333; -fx-text-fill: #f5f5f5; -fx-background-radius: 20px;");

        validate.setOnAction(e -> {
            String n = nameField.getText().trim();
            String a = ageField.getText().trim();

            // vérifie que ça correspond au joueur créé
            if (n.isEmpty() || a.isEmpty()) { error.setText("⚠️ Remplis les deux champs."); return; }

            int age;
            try {
                age = Integer.parseInt(a);
            } catch (NumberFormatException ex) {
                error.setText("⚠️ Âge invalide.");
                return;
            }

            if (!n.equals(client.getName()) || age != client.getAge()) {
                error.setText("❌ Infos incorrectes : recalé.");
                return;
            }

            // infos correctes -> écran délai + décision
            switchRootWithFade(createBouncerDecisionRoot(bouncer));
        });

        back.setOnAction(e -> switchRootWithFade(createBouncerSelectionRoot()));

        VBox fields = new VBox(10, nameField, ageField, error);
        fields.setAlignment(Pos.CENTER);

        HBox buttons = new HBox(12, validate, back);
        buttons.setAlignment(Pos.CENTER);

        VBox content = new VBox(20, title, subtitle, fields, buttons);
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-padding: 40px;");
        content.setMaxWidth(800);

        background.getChildren().add(content);
        return background;
    }


    private StackPane createBouncerSelectionRoot() {
        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #111111, #1a1a2e);");

        Label title = new Label("🛑 Les videurs t'arrêtent !");
        title.setStyle("-fx-font-size: 34px; -fx-text-fill: #ffd369; -fx-font-weight: bold;");

        Label subtitle = new Label("Choisis quel videur te contrôle.");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #eeeeee;");

        VBox box = new VBox(15);
        box.setAlignment(Pos.CENTER);

        for (Bouncer b : getBouncers()) {
            int refuse = refuseChancePercent(b);

            Button btn = new Button(
                    "🧍 " + b.getName() +
                            "\n⭐ Qualité: " + b.getQuality() + "/10" +
                            "\n🚫 Risque de se faire recaler: " + refuse + "%"
            );
            btn.setStyle(
                    "-fx-font-size: 14px;" +
                            "-fx-padding: 15px 30px;" +
                            "-fx-background-color: #444444;" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 20px;" +
                            "-fx-cursor: hand;" +
                            "-fx-text-alignment: left;"
            );

            btn.setOnAction(e -> {
                selectedBouncer = b;
                switchRootWithFade(createBouncerCheckRoot(b));
            });

            box.getChildren().add(btn);
        }

        Button back = new Button("⬅️ Retour");
        back.setStyle("-fx-font-size: 14px; -fx-padding: 8px 20px; -fx-background-color: #333333; -fx-text-fill: #f5f5f5; -fx-background-radius: 20px;");
        back.setOnAction(e -> switchRootWithFade(createMainMenuRoot()));

        VBox content = new VBox(20, title, subtitle, box, back);
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-padding: 40px;");

        background.getChildren().add(content);
        return background;
    }


    private int refuseChancePercent(Bouncer bouncer) {
        // même logique que serveur : qualité basse = + de refus
        return Math.min(100, Math.max(0, (10 - bouncer.getQuality()) * 10)); // 2/10 => 80% refus
    }

    private boolean bouncerRefuses(Bouncer bouncer) {
        int chance = refuseChancePercent(bouncer);
        int roll = ThreadLocalRandom.current().nextInt(100); // 0..99
        return roll < chance;
    }

    private List<Bouncer> getBouncers() {
        List<Bouncer> list = new ArrayList<>();
        for (Employee e : bar.getStaff()) {
            if (e instanceof Bouncer b) list.add(b);
        }
        return list;
    }


    private Bouncer selectedBouncer;

    private Stage primaryStage;
    private Scene mainScene;

    private Client client;
    private Bar bar;

    private String lastLocation;          // "table" ou "comptoir"
    private Waiter selectedWaiter;        // serveur choisi
    private String selectedDrinkLabel;    // boisson choisie (texte affiché)

    public static void main(String[] args) {
        launch(args);
    }

    // ----------------------------
    // Helpers: navigation + stats
    // ----------------------------

    private void switchRootWithFade(Parent nextRoot) {
        Parent currentRoot = mainScene.getRoot();

        FadeTransition fadeOut = new FadeTransition(Duration.millis(220), currentRoot);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(e -> {
            mainScene.setRoot(nextRoot);
            nextRoot.setOpacity(0);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(220), nextRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();

            primaryStage.setMaximized(true);
        });

        fadeOut.play();
    }

    private void switchRootWithZoom(Parent nextRoot) {
        Parent currentRoot = mainScene.getRoot();

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(600), currentRoot);
        scaleOut.setToX(1.5);
        scaleOut.setToY(1.5);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(600), currentRoot);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(e -> {
            currentRoot.setScaleX(1.0);
            currentRoot.setScaleY(1.0);

            mainScene.setRoot(nextRoot);
            nextRoot.setOpacity(0);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(400), nextRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();

            primaryStage.setMaximized(true);
        });

        scaleOut.play();
        fadeOut.play();
    }

    private int spillChancePercent(Waiter waiter) {
        return Math.max(0, (10 - waiter.getQuality()) * 6);
    }

    private boolean traySpillHappens(Waiter waiter) {
        int chancePercent = spillChancePercent(waiter);
        int roll = ThreadLocalRandom.current().nextInt(100);
        return roll < chancePercent;
    }

    /** Convertit la vitesse (1..10) en temps d'attente (secondes). */
    private double arrivalDelaySeconds(Waiter waiter) {
        int speed = waiter.getSpeed();  // 1..10
        // speed 10 => ~2.0s ; speed 1 => ~6.5s
        return Math.max(2.0, 7.0 - (speed * 0.5));
    }

    private List<Waiter> getWaiters() {
        List<Waiter> list = new ArrayList<>();
        for (Employee e : bar.getStaff()) {
            if (e instanceof Waiter w) list.add(w);
        }
        return list;
    }

    private Waiter pickRandomWaiter() {
        List<Waiter> waiters = getWaiters();
        if (waiters.isEmpty()) return null;
        return waiters.get(ThreadLocalRandom.current().nextInt(waiters.size()));
    }

    // ----------------------------
    // JavaFX lifecycle
    // ----------------------------

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.bar = new Bar();

        StackPane root = createCharacterCreationRoot();
        mainScene = new Scene(root);

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("CasiBAR");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    // ----------------------------
    // Root 0 : Création joueur
    // ----------------------------

    private StackPane createCharacterCreationRoot() {
        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #0f0c29, #302b63, #24243e);");

        Label title = new Label("✨ Création de personnage ✨");
        title.setStyle("-fx-font-size: 36px; -fx-text-fill: #ffd369; -fx-font-weight: bold;" +
                "-fx-effect: dropshadow(gaussian, black, 15, 0.7, 0, 0);");

        Label subtitle = new Label("Entre tes informations pour commencer l'aventure");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #eeeeee;");

        Label nameLabel = new Label("Prénom :");
        nameLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #ffd369; -fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Entre ton Prénom");
        nameField.setStyle("-fx-font-size: 14px; -fx-padding: 8px;" +
                "-fx-background-color: rgba(255, 255, 255, 0.1);" +
                "-fx-text-fill: white; -fx-prompt-text-fill: rgba(255, 255, 255, 0.5);" +
                "-fx-background-radius: 10px; -fx-border-color: #ffd369;" +
                "-fx-border-radius: 10px; -fx-border-width: 2px;");
        nameField.setMaxWidth(320);

        Label ageLabel = new Label("Âge :");
        ageLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #ffd369; -fx-font-weight: bold;");

        TextField ageField = new TextField();
        ageField.setPromptText("Entre ton âge");
        ageField.setStyle("-fx-font-size: 14px; -fx-padding: 8px;" +
                "-fx-background-color: rgba(255, 255, 255, 0.1);" +
                "-fx-text-fill: white; -fx-prompt-text-fill: rgba(255, 255, 255, 0.5);" +
                "-fx-background-radius: 10px; -fx-border-color: #ffd369;" +
                "-fx-border-radius: 10px; -fx-border-width: 2px;");
        ageField.setMaxWidth(320);

        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #ff6b6b; -fx-font-weight: bold;");
        errorLabel.setVisible(false);

        Button startButton = new Button("🎉 Commencer l'aventure 🍺");
        startButton.setStyle("-fx-font-size: 18px; -fx-padding: 12px 35px;" +
                "-fx-background-color: linear-gradient(to right, #ffcc00, #ff9900);" +
                "-fx-text-fill: #202020; -fx-background-radius: 30px;" +
                "-fx-font-weight: bold; -fx-cursor: hand;");

        startButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();

            if (name.isEmpty()) { errorLabel.setText("⚠️ Le nom ne peut pas être vide !"); errorLabel.setVisible(true); return; }
            if (!Character.isUpperCase(name.charAt(0))) { errorLabel.setText("⚠️ Le nom doit commencer par une majuscule !"); errorLabel.setVisible(true); return; }
            if (!name.matches("[A-Z][a-z]*")) { errorLabel.setText("⚠️ Le nom ne doit contenir que des lettres !"); errorLabel.setVisible(true); return; }

            try {
                int age = Integer.parseInt(ageText);
                if (age < 15 || age > 122) { errorLabel.setText("⚠️ L'âge doit être entre 15 et 122 ans !"); errorLabel.setVisible(true); return; }

                client = new Client(name, age);
                switchRootWithFade(createMainMenuRoot());
            } catch (NumberFormatException ex) {
                errorLabel.setText("⚠️ L'âge doit être un nombre valide !");
                errorLabel.setVisible(true);
            }
        });

        VBox formBox = new VBox(10, nameLabel, nameField, ageLabel, ageField);
        formBox.setAlignment(Pos.CENTER);

        VBox contentBox = new VBox(25, title, subtitle, formBox, errorLabel, startButton);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setStyle("-fx-padding: 40px;");

        background.getChildren().add(contentBox);
        return background;
    }

    // ----------------------------
    // Root 1 : Menu principal
    // ----------------------------

    private StackPane createMainMenuRoot() {
        StackPane background = new StackPane();

        // Même fond que le reste du jeu
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #2d1b00, #1a1a2e);");

        Label title = new Label("CasiBAR");
        title.setStyle(
                "-fx-font-size: 84px;" +
                        "-fx-text-fill: #ffd369;" +
                        "-fx-font-weight: bold;" +
                        "-fx-effect: dropshadow(gaussian, black, 18, 0.7, 0, 0);"
        );

        Label subtitle = new Label(
                "👋 Salut " + client.getName() + " !\n" +
                        "🌙 Fin de journée... envie d’un verre ?\n" +
                        "🍹 Choisis : entrer au CasiBAR ou rentrer chez toi."
        );
        subtitle.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-text-fill: #eeeeee;"
        );

        // >>> Alignement du texte (important)
        subtitle.setMaxWidth(720);
        subtitle.setMinWidth(720);                    // largeur fixe => vrai centrage visuel
        subtitle.setWrapText(true);
        subtitle.setAlignment(Pos.CENTER);            // centre le contenu du Label
        subtitle.setTextAlignment(TextAlignment.CENTER); // centre chaque ligne
        // <<<

        Button enterButton = new Button("🚪 Entrer dans le bar");
        enterButton.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-padding: 12px 35px;" +
                        "-fx-background-color: linear-gradient(to right, #ffcc00, #ff9900);" +
                        "-fx-text-fill: #202020;" +
                        "-fx-background-radius: 30px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-cursor: hand;"
        );

        Button quitButton = new Button("🏠 Rentrer chez moi");
        quitButton.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-padding: 10px 30px;" +
                        "-fx-background-color: #444444;" +
                        "-fx-text-fill: #f5f5f5;" +
                        "-fx-background-radius: 30px;" +
                        "-fx-cursor: hand;"
        );

        // Largeur fixe => boutons alignés
        enterButton.setMinWidth(340);
        quitButton.setMinWidth(340);

        enterButton.setOnAction(e -> switchRootWithZoom(createBouncerSelectionRoot()));
        quitButton.setOnAction(e -> primaryStage.close());

        VBox buttonBox = new VBox(14, enterButton, quitButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setFillWidth(true);

        Region spacer1 = new Region();
        spacer1.setMinHeight(18);

        Region spacer2 = new Region();
        spacer2.setMinHeight(26);

        VBox contentBox = new VBox(0, title, spacer1, subtitle, spacer2, buttonBox);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setFillWidth(true);
        contentBox.setStyle("-fx-padding: 55px 40px 40px 40px;");
        contentBox.setMaxWidth(900);

        background.getChildren().add(contentBox);
        return background;
    }




    // ----------------------------
    // Root 2 : Choix comptoir/table + partir
    // ----------------------------

    private StackPane createBarMenuRoot() {
        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #2d1b00, #1a1a2e);");

        Label welcomeLabel = new Label("🍸 Bienvenue au CasiBAR ! 🍸");
        welcomeLabel.setStyle("-fx-font-size: 38px; -fx-text-fill: #ffd369; -fx-font-weight: bold;" +
                "-fx-effect: dropshadow(gaussian, black, 15, 0.7, 0, 0);");

        Label descriptionLabel = new Label("Où veux-tu t'installer ?");
        descriptionLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #eeeeee; -fx-text-alignment: center;");

        Button barCounterButton = new Button("🪑 Se mettre au comptoir");
        barCounterButton.setStyle("-fx-font-size: 18px; -fx-padding: 12px 35px;" +
                "-fx-background-color: linear-gradient(to right, #ff6b6b, #ee5a6f);" +
                "-fx-text-fill: white; -fx-background-radius: 30px;" +
                "-fx-font-weight: bold; -fx-cursor: hand;");

        Button tableButton = new Button("🍽️ S'asseoir à une table");
        tableButton.setStyle("-fx-font-size: 18px; -fx-padding: 12px 35px;" +
                "-fx-background-color: linear-gradient(to right, #4ecdc4, #44a08d);" +
                "-fx-text-fill: white; -fx-background-radius: 30px;" +
                "-fx-font-weight: bold; -fx-cursor: hand;");

        Button leaveButton = new Button("🚪 Partir");
        leaveButton.setStyle("-fx-font-size: 14px; -fx-padding: 8px 20px;" +
                "-fx-background-color: #444444; -fx-text-fill: #f5f5f5;" +
                "-fx-background-radius: 30px; -fx-cursor: hand;");

        barCounterButton.setOnAction(e -> switchRootWithFade(createCounterBartenderArrivesRoot()));
        tableButton.setOnAction(e -> switchRootWithFade(createSeatingRoot("table")));
        leaveButton.setOnAction(e -> switchRootWithFade(createMainMenuRoot()));

        VBox buttonBox = new VBox(15, barCounterButton, tableButton, leaveButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox contentBox = new VBox(30, welcomeLabel, descriptionLabel, buttonBox);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setStyle("-fx-padding: 40px;");

        background.getChildren().add(contentBox);
        return background;
    }

    // ----------------------------
    // Root 3 : Assis (menu + choisir serveur)
    // -> ici ON NE COMMANDE PAS DIRECTEMENT
    // ----------------------------

    private StackPane createSeatingRoot(String location) {
        lastLocation = location;

        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #2d1b00, #1a1a2e);");

        String emoji = location.equals("comptoir") ? "🪑" : "🍽️";
        String locationText = location.equals("comptoir") ? "au comptoir" : "à une table";

        Label locationLabel = new Label(emoji + " Tu es installé " + locationText);
        locationLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: #ffd369; -fx-font-weight: bold;" +
                "-fx-effect: dropshadow(gaussian, black, 10, 0.7, 0, 0);");

        Label menuTitle = new Label("📋 Menu des boissons (aperçu)");
        menuTitle.setStyle("-fx-font-size: 20px; -fx-text-fill: #4ecdc4; -fx-font-weight: bold;");

        ListView<Drink> drinkList = new ListView<>();

        for (Drink drink : bar.getMenu()) {
            if (!client.canOrderAlcohol() && drink.isAlcoholic()) continue;
            drinkList.getItems().add(drink);
        }

        setupDrinkListCellFactory(drinkList);


        drinkList.setMaxHeight(220);

        Label waiterInfo = new Label(
                selectedWaiter == null
                        ? "Serveur : aucun (si tu commandes, un serveur sera choisi au hasard)"
                        : "Serveur : " + selectedWaiter.getName() + " (⚡ " + selectedWaiter.getSpeed() + "/10 | ⭐ " + selectedWaiter.getQuality() + "/10)"
        );
        waiterInfo.setStyle("-fx-font-size: 14px; -fx-text-fill: #eeeeee;");

        Button chooseWaiterButton = new Button("📣 Choisir un serveur");
        chooseWaiterButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px 25px;" +
                "-fx-background-color: linear-gradient(to right, #4ecdc4, #44a08d);" +
                "-fx-text-fill: white; -fx-background-radius: 30px;" +
                "-fx-font-weight: bold; -fx-cursor: hand;");

        Button orderFlowButton = new Button("🛎️ Passer commande");
        orderFlowButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px 25px;" +
                "-fx-background-color: linear-gradient(to right, #ffcc00, #ff9900);" +
                "-fx-text-fill: #202020; -fx-background-radius: 30px;" +
                "-fx-font-weight: bold; -fx-cursor: hand;");

        Button standUpButton = new Button("🚶 Se lever");
        standUpButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px 25px;" +
                "-fx-background-color: #444444; -fx-text-fill: #f5f5f5;" +
                "-fx-background-radius: 30px; -fx-cursor: hand;");

        chooseWaiterButton.setOnAction(e -> switchRootWithFade(createWaiterSelectionRoot(location)));

        orderFlowButton.setOnAction(e -> {
            // si pas choisi, on prend au hasard (donc pas toujours Jade)
            if (selectedWaiter == null) {
                selectedWaiter = pickRandomWaiter();
            }
            if (selectedWaiter == null) {
                // aucun serveur dans le staff
                switchRootWithFade(createMessageRoot("❌ Aucun serveur disponible.", () -> switchRootWithFade(createSeatingRoot(location))));
                return;
            }
            switchRootWithFade(createWaiterArrivesRoot(location, selectedWaiter));
        });

        standUpButton.setOnAction(e -> switchRootWithFade(createBarMenuRoot()));

        HBox buttons = new HBox(15, chooseWaiterButton, orderFlowButton, standUpButton);
        buttons.setAlignment(Pos.CENTER);

        VBox contentBox = new VBox(18, locationLabel, menuTitle, drinkList, waiterInfo, buttons);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setStyle("-fx-padding: 40px;");
        contentBox.setMaxWidth(800);

        background.getChildren().add(contentBox);
        return background;
    }

    // ----------------------------
    // Root 4 : Choix serveur
    // ----------------------------

    private StackPane createWaiterSelectionRoot(String location) {
        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #2d1b00, #1a1a2e);");

        Label title = new Label("👥 Choisir ton serveur");
        title.setStyle("-fx-font-size: 28px; -fx-text-fill: #ffd369; -fx-font-weight: bold;" +
                "-fx-effect: dropshadow(gaussian, black, 10, 0.7, 0, 0);");

        Label subtitle = new Label("Vitesse = temps d'arrivée | Qualité = risque d'accident");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #eeeeee;");

        Label status = new Label("Choisis un serveur.");
        status.setStyle("-fx-font-size: 14px; -fx-text-fill: #eeeeee;");

        VBox waitersBox = new VBox(15);
        waitersBox.setAlignment(Pos.CENTER);

        for (Waiter waiter : getWaiters()) {
            int risk = spillChancePercent(waiter);
            Button b = new Button(
                    "👤 " + waiter.getName() +
                            "\n⚡ Vitesse: " + waiter.getSpeed() + "/10" +
                            "\n⭐ Qualité: " + waiter.getQuality() + "/10" +
                            "\n💥 Risque: " + risk + "% renverser"
            );
            b.setStyle("-fx-font-size: 14px; -fx-padding: 15px 30px;" +
                    "-fx-background-color: linear-gradient(to right, #4ecdc4, #44a08d);" +
                    "-fx-text-fill: white; -fx-background-radius: 20px;" +
                    "-fx-cursor: hand; -fx-text-alignment: left;");

            b.setOnAction(ev -> {
                selectedWaiter = waiter;
                status.setText("✅ Serveur sélectionné : " + waiter.getName());
                PauseTransition pause = new PauseTransition(Duration.seconds(0.8));
                pause.setOnFinished(x -> switchRootWithFade(createSeatingRoot(location)));
                pause.play();
            });

            waitersBox.getChildren().add(b);
        }

        Button back = new Button("⬅️ Retour");
        back.setStyle("-fx-font-size: 14px; -fx-padding: 8px 20px;" +
                "-fx-background-color: #444444; -fx-text-fill: #f5f5f5;" +
                "-fx-background-radius: 20px; -fx-cursor: hand;");
        back.setOnAction(e -> switchRootWithFade(createSeatingRoot(location)));

        VBox content = new VBox(20, title, subtitle, status, waitersBox, back);
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-padding: 40px;");

        background.getChildren().add(content);
        return background;
    }

    // ----------------------------
    // Root 5 : (nom) arrive...
    // ----------------------------

    private StackPane createWaiterArrivesRoot(String location, Waiter waiter) {
        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #2d1b00, #1a1a2e);");

        double delay = arrivalDelaySeconds(waiter);

        Label title = new Label("📣 " + waiter.getName() + " arrive...");
        title.setStyle("-fx-font-size: 32px; -fx-text-fill: #ffd369; -fx-font-weight: bold;" +
                "-fx-effect: dropshadow(gaussian, black, 12, 0.7, 0, 0);");

        Label info = new Label("⏳ Temps estimé : " + String.format("%.1f", delay) + "s (⚡ " + waiter.getSpeed() + "/10)");
        info.setStyle("-fx-font-size: 14px; -fx-text-fill: #eeeeee;");

        VBox box = new VBox(15, title, info);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-padding: 40px;");

        background.getChildren().add(box);

        PauseTransition pause = new PauseTransition(Duration.seconds(delay));
        pause.setOnFinished(e -> switchRootWithFade(createWaiterTakesOrderRoot(location, waiter)));
        pause.play();

        return background;
    }

    // ----------------------------
    // Root 6 : (nom) prend votre commande (interface de commande)
    // ----------------------------

    private StackPane createWaiterTakesOrderRoot(String location, Waiter waiter) {
        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #2d1b00, #1a1a2e);");

        Label title = new Label("📝 " + waiter.getName() + " prend votre commande");
        title.setStyle("-fx-font-size: 28px; -fx-text-fill: #ffd369; -fx-font-weight: bold;" +
                "-fx-effect: dropshadow(gaussian, black, 10, 0.7, 0, 0);");

        Label subtitle = new Label("Choisis une boisson puis clique sur Commander.");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #eeeeee;");

        ListView<Drink> drinkList = new ListView<>();

        for (Drink drink : bar.getMenu()) {
            if (!client.canOrderAlcohol() && drink.isAlcoholic()) continue;
            drinkList.getItems().add(drink);
        }

        drinkList.setCellFactory(lv -> new ListCell<Drink>() {
            @Override
            protected void updateItem(Drink drink, boolean empty) {
                super.updateItem(drink, empty);
                if (empty || drink == null) { setText(null); return; }

                String organicLabel = drink.isOrganic() ? " 🌿 Organic" : "";
                setText(getDrinkEmoji(drink) + " " + drink.getName()
                        + " - " + String.format("%.2f€", drink.getPrice()) + organicLabel
                        + " - Stock: " + drink.getStock());
            }
        });



        drinkList.setMaxHeight(240);

        Label info = new Label("");
        info.setStyle("-fx-font-size: 14px; -fx-text-fill: #ffcc00; -fx-font-weight: bold;");

        Button orderButton = new Button("🛎️ Commander");
        orderButton.setStyle("-fx-font-size: 16px; -fx-padding: 10px 25px;" +
                "-fx-background-color: linear-gradient(to right, #ffcc00, #ff9900);" +
                "-fx-text-fill: #202020; -fx-background-radius: 30px;" +
                "-fx-font-weight: bold; -fx-cursor: hand;");

        Button cancelButton = new Button("⬅️ Annuler");
        cancelButton.setStyle("-fx-font-size: 14px; -fx-padding: 8px 20px;" +
                "-fx-background-color: #444444; -fx-text-fill: #f5f5f5;" +
                "-fx-background-radius: 30px; -fx-cursor: hand;");

        orderButton.setOnAction(e -> {
            Drink selectedDrink = drinkList.getSelectionModel().getSelectedItem();
            if (selectedDrink == null) {
                info.setText("⚠️ Choisis une boisson !");
                return;
            }

            if (selectedDrink.getStock() <= 0) {
                info.setText("Rupture de stock, réappro dans 30s.");
                return;
            }

            double amount = selectedDrink.getPrice();

            switchRootWithFade(createPaymentRoot(
                    selectedDrink,
                    () -> { // payé
                        consumeOneAndRestockIfNeeded(selectedDrink, drinkList);
                        selectedDrinkLabel = selectedDrink.getName();
                        switchRootWithFade(createWaiterBringsDrinkRoot(location, waiter, selectedDrinkLabel));
                    },
                    () -> { // annulé
                        switchRootWithFade(createWaiterTakesOrderRoot(location, waiter));
                    }
            ));


        });




        cancelButton.setOnAction(e -> switchRootWithFade(createSeatingRoot(location)));

        HBox buttons = new HBox(12, orderButton, cancelButton);
        buttons.setAlignment(Pos.CENTER);

        VBox box = new VBox(18, title, subtitle, drinkList, info, buttons);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-padding: 40px;");
        box.setMaxWidth(800);

        background.getChildren().add(box);
        return background;
    }

    // ----------------------------
    // Root 7 : (nom) apporte la boisson (délai + risque renverser)
    // ----------------------------

    private StackPane createWaiterBringsDrinkRoot(String location, Waiter waiter, String drinkLabel) {
        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #2d1b00, #1a1a2e);");

        double delay = arrivalDelaySeconds(waiter);
        int risk = spillChancePercent(waiter);

        Label title = new Label("🍹 " + waiter.getName() + " apporte votre boisson...");
        title.setStyle("-fx-font-size: 30px; -fx-text-fill: #ffd369; -fx-font-weight: bold;" +
                "-fx-effect: dropshadow(gaussian, black, 12, 0.7, 0, 0);");

        Label drink = new Label("Boisson : " + drinkLabel);
        drink.setStyle("-fx-font-size: 16px; -fx-text-fill: #eeeeee;");

        Label info = new Label("⏳ " + String.format("%.1f", delay) + "s | 💥 Risque: " + risk + "%");
        info.setStyle("-fx-font-size: 14px; -fx-text-fill: #eeeeee;");

        VBox box = new VBox(15, title, drink, info);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-padding: 40px;");
        background.getChildren().add(box);

        PauseTransition pause = new PauseTransition(Duration.seconds(delay));
        pause.setOnFinished(e -> {
            boolean spill = traySpillHappens(waiter);
            if (spill) {
                switchRootWithFade(createSpilledRoot(location, waiter, drinkLabel));
            } else {
                switchRootWithFade(createDrinkingRoot(drinkLabel, location));
            }
        });
        pause.play();

        return background;
    }

    // ----------------------------
    // Root 8 : plateau renversé
    // ----------------------------

    private StackPane createSpilledRoot(String location, Waiter waiter, String drinkLabel) {
        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #2d1b00, #1a1a2e);");

        Label title = new Label("💥 Oups... " + waiter.getName() + " renverse le plateau !");
        title.setStyle("-fx-font-size: 28px; -fx-text-fill: #ff6b6b; -fx-font-weight: bold;" +
                "-fx-effect: dropshadow(gaussian, black, 10, 0.7, 0, 0);");

        Label info = new Label("Boisson perdue : " + drinkLabel);
        info.setStyle("-fx-font-size: 14px; -fx-text-fill: #eeeeee;");

        Button back = new Button("🔁 Refaire une commande");
        back.setStyle("-fx-font-size: 16px; -fx-padding: 10px 25px;" +
                "-fx-background-color: linear-gradient(to right, #ffcc00, #ff9900);" +
                "-fx-text-fill: #202020; -fx-background-radius: 30px;" +
                "-fx-font-weight: bold; -fx-cursor: hand;");
        back.setOnAction(e -> switchRootWithFade(createWaiterTakesOrderRoot(location, waiter)));

        Button standUp = new Button("🚶 Se lever");
        standUp.setStyle("-fx-font-size: 16px; -fx-padding: 10px 25px;" +
                "-fx-background-color: #444444; -fx-text-fill: #f5f5f5;" +
                "-fx-background-radius: 30px; -fx-cursor: hand;");
        standUp.setOnAction(e -> switchRootWithFade(createBarMenuRoot()));

        HBox buttons = new HBox(12, back, standUp);
        buttons.setAlignment(Pos.CENTER);

        VBox box = new VBox(18, title, info, buttons);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-padding: 40px;");
        background.getChildren().add(box);

        return background;
    }

    // ----------------------------
    // Root 9 : boire le verre + choix après
    // ----------------------------

    private StackPane createDrinkingRoot(String drinkLabel, String location) {
        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #2d1b00, #1a1a2e);");

        Label title = new Label("🥂 Tu bois ton verre...");
        title.setStyle("-fx-font-size: 32px; -fx-text-fill: #ffd369; -fx-font-weight: bold;" +
                "-fx-effect: dropshadow(gaussian, black, 12, 0.7, 0, 0);");

        Label drinkText = new Label("Boisson : " + drinkLabel);
        drinkText.setStyle("-fx-font-size: 16px; -fx-text-fill: #eeeeee;");

        String w = (selectedWaiter == null) ? "(aucun serveur)" : selectedWaiter.getName();
        Label waiterText = new Label("Serveur : " + w);
        waiterText.setStyle("-fx-font-size: 14px; -fx-text-fill: #dddddd;");

        Label status = new Label("⏳ En cours...");
        status.setStyle("-fx-font-size: 14px; -fx-text-fill: #4ecdc4; -fx-font-weight: bold;");

        // >>> AJOUT : barre de sobriété (1.0 = sobre, 0.0 = très saoul)
        ProgressBar sobrietyBar = new ProgressBar(1.0 - (drunkLevel / 100.0));
        sobrietyBar.setMaxWidth(300);
        // <<< FIN AJOUT

        Button againButton = new Button("🍹 Reprendre un verre");
        Button standUpButton = new Button("🚶 Se lever");
        Button leaveButton = new Button("🚪 Partir");

        String commonStyle = "-fx-font-size: 16px; -fx-padding: 10px 25px; -fx-background-radius: 30px; -fx-font-weight: bold; -fx-cursor: hand;";
        againButton.setStyle(commonStyle + "-fx-background-color: linear-gradient(to right, #4ecdc4, #44a08d); -fx-text-fill: white;");
        standUpButton.setStyle(commonStyle + "-fx-background-color: #444444; -fx-text-fill: #f5f5f5;");
        leaveButton.setStyle(commonStyle + "-fx-background-color: linear-gradient(to right, #ff6b6b, #ee5a6f); -fx-text-fill: white;");

        againButton.setDisable(true);
        standUpButton.setDisable(true);
        leaveButton.setDisable(true);

        PauseTransition drinkingTime = new PauseTransition(Duration.seconds(2.5));
        drinkingTime.setOnFinished(e -> {

            // Le verre est fini -> si c'est de l'eau, on remonte la sobriété
            String lower = drinkLabel.toLowerCase();

            if (lower.contains("eau") || lower.contains("water")) {
                drunkLevel = Math.max(0, drunkLevel - 35);   // eau => baisse l'alcool (donc barre remonte)
            } else {
                drunkLevel = Math.min(100, drunkLevel + 25); // autres => comme avant
            }


            // >>> AJOUT : mise à jour barre
            sobrietyBar.setProgress(1.0 - (drunkLevel / 100.0));
            // <<< FIN AJOUT

            // Trop saoul -> vomit et expulsion
            if (drunkLevel >= VOMIT_AT) {
                switchRootWithFade(createVomitRoot());
                return;
            }

            // Sinon normal : on réactive les choix
            status.setText("✅ Verre terminé !");
            againButton.setDisable(false);
            standUpButton.setDisable(false);
            leaveButton.setDisable(false);
        });
        drinkingTime.play();

        againButton.setOnAction(e -> {
            if ("comptoir".equals(location)) {
                switchRootWithFade(createCounterOrderRoot(getBartender())); // ou createCounterBartenderArrivesRoot()
            } else {
                switchRootWithFade(createSeatingRoot(location)); // table
            }
        });
        standUpButton.setOnAction(e -> switchRootWithFade(createBarMenuRoot()));
        leaveButton.setOnAction(e -> switchRootWithFade(createMainMenuRoot()));

        HBox buttons = new HBox(15, againButton, standUpButton, leaveButton);
        buttons.setAlignment(Pos.CENTER);

        // >>> MODIF : on insère sobrietyBar dans le VBox
        VBox box = new VBox(20, title, drinkText, waiterText, sobrietyBar, status, buttons);
        // <<< FIN MODIF

        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-padding: 40px;");
        box.setMaxWidth(900);

        background.getChildren().add(box);
        return background;
    }

    // ----------------------------
    // Petit écran message (utilitaire)
    // ----------------------------

    private StackPane createMessageRoot(String message, Runnable onBack) {
        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: linear-gradient(to bottom, #2d1b00, #1a1a2e);");

        Label title = new Label(message);
        title.setStyle("-fx-font-size: 24px; -fx-text-fill: #ffd369; -fx-font-weight: bold;");

        Button back = new Button("⬅️ Retour");
        back.setStyle("-fx-font-size: 14px; -fx-padding: 8px 20px;" +
                "-fx-background-color: #444444; -fx-text-fill: #f5f5f5;" +
                "-fx-background-radius: 20px; -fx-cursor: hand;");
        back.setOnAction(e -> onBack.run());

        VBox box = new VBox(20, title, back);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-padding: 40px;");
        background.getChildren().add(box);

        return background;
    }

    // ----------------------------
    // Emoji helper
    // ----------------------------

    private String getDrinkEmoji(Drink drink) {
        String name = drink.getName().toLowerCase();
        if (name.contains("coca")) return "🥤";
        if (name.contains("orange") || name.contains("juice")) return "🧃";
        if (name.contains("mojito")) return "🍹";
        if (name.contains("margarita")) return "🍸";
        if (name.contains("punch")) return "🍹";
        if (name.contains("virgin")) return "🧃";
        if (name.contains("eau") || name.contains("water")) return "💧";

        return "🍹";
    }


}
