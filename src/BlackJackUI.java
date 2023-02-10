import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;
import javafx.beans.binding.Bindings;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.text.*;

public class BlackJackUI extends Application {

    private static final String[] RANKS = { "ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen",
            "king" };

    private static final String[] SUITS = { "spades", "hearts", "diamonds", "clubs" };

    private static final int[] POINT_VALUES = { 11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10 };

    private Stage stage;
    private Scene scene;
    private VBox root;

    // name stuff
    // private HBox topRow;
    // private Label playerName;

    // where cards go
    private HBox cardRow;
    private List<ImageView> cardHolder = new ArrayList<ImageView>();

    // total and buttons
    private HBox bottomRow;
    private Label total;
    private Button stand;
    private Button hit;

    private Deck deck = new Deck(RANKS, SUITS, POINT_VALUES);
    private Player one = new Player("Brett", 1);
    private Player dealer = new Player("", 0);
    private List<Card> oneCards = new ArrayList<Card>();
    private List<Card> dealerCards = new ArrayList<Card>();

    public BlackJackUI() {
        this.stage = null;
        this.scene = null;
        this.root = new VBox(3);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BlackJackUI app = new BlackJackUI();
        System.out.println(deck);
        this.stage = stage;

        Card dealt = deck.deal();
        oneCards.add(dealt);
        if (dealt.pointValue() == 11)
            one.addAce();
        one.add(dealt.pointValue());
        dealt = deck.deal();
        dealerCards.add(dealt);
        if (dealt.pointValue() == 11)
            dealer.addAce();
        dealer.add(dealt.pointValue());
        dealt = deck.deal();
        oneCards.add(dealt);
        if (dealt.pointValue() == 11)
            one.addAce();
        one.add(dealt.pointValue());
        dealt = deck.deal();
        dealerCards.add(dealt);
        if (dealt.pointValue() == 11)
            dealer.addAce();
        dealer.add(dealt.pointValue());

        // topRow
        // topRow = new HBox(4);
        // playerName = new Label(Player.getName());
        // topRow.setPadding(new Insets(5, 2, 2, 5));
        // topRow.getChildren().addAll(playerName);

        // test
        for (int i = 0; i < 2; i++)
            cardHolder.add(new ImageView(oneCards.get(i).url()));

        // cardRow
        cardRow = new HBox(4);
        cardRow.setPadding(new Insets(100));
        for (int i = 0; i < cardHolder.size(); i++)
            cardRow.getChildren().add(cardHolder.get(i));

        // bottomRow
        bottomRow = new HBox(4);
        total = new Label("Total = " + one.total());
        total.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        stand = new Button("Stand");
        stand.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        stand.setPadding(new Insets(5, 5, 5, 5));
        hit = new Button("Hit");
        hit.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        hit.setPadding(new Insets(5, 5, 5, 5));
        bottomRow.getChildren().addAll(total, stand, hit);

        // adding all to root
        root.getChildren().addAll(cardRow, bottomRow);
        root.setSpacing(0);
        // buttons
        stand.setOnAction(this::stand);
        hit.setOnAction(this::hit);
        // setting scene
        this.scene = new Scene(this.root);
        this.stage.setOnCloseRequest(event -> Platform.exit());
        this.stage.setTitle("Casino");
        this.stage.setScene(this.scene);
        this.stage.sizeToScene();
        this.stage.show();
        Platform.runLater(() -> this.stage.setResizable(false));
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    private void stand(ActionEvent a) {
        System.out.println(deck);
        hit.setDisable(true);
        stand.setDisable(true);
        while ((dealer.total() < 17) || (dealer.total() > 17 && dealer.getAce() != 0)) {
            if (dealer.total() > 17 && dealer.getAce() > 0) {
                dealer.subTotal(10);
                dealer.subAce();
            }
            Card dealt = deck.deal();
            dealerCards.add(dealt);
            if (dealt.pointValue() == 11)
                dealer.addAce();
        }
        if (one.total() > dealer.total()) {
            System.out.println("YOU WIN");
        } else if (one.total() == dealer.total()) {
            System.out.println("TIE, Take Back Chips");
        } else {
            System.out.println("YOU LOOSE");
        }
    }

    private void hit(ActionEvent a) {
        System.out.println(deck);
        Card dealt = deck.deal();
        oneCards.add(dealt);
        if (dealt.pointValue() == 11)
            one.addAce();
        one.add(dealt.pointValue());
        if (one.total() > 21 && one.getAce() > 0) {
            one.subTotal(10);
            one.subAce();
        } else if (one.total() > 21) {
            System.out.println("BUST");
            System.out.println("YOU LOOSE");
            // System.exit(1);
        }
    }
}
