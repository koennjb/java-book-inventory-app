package becker;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import numberlist.InvalidIndexException;
import numberlist.objectlist.Money;
import numberlist.primitivelist.IntegerArrayList;

/**
 * The presentation layer class for nozamA. Uses a JavaFX GUI for front end
 * interaction. Validates and passes user input to the BookCollection class to
 * interact with the business layer and data.
 *
 * @author Koenn Becker as Coding Manager.
 */
public class CourseProject extends Application {

    //Inventory collection class
    private BookCollection inventory;
    //How many items to display per page when browsing
    private final int ITEMS_PER_PAGE = 9;
    //Create root border pane
    private BorderPane root = new BorderPane();
    // Tile pane to display all books in browse
    private TilePane itemTile;
    // Split pane used for edit window
    private SplitPane editSplit;
    // Bottom half of browse window when the edit pane is down
    private BorderPane browseBorderPane;

    // Text fields for editing are contained inside of VBox.
    private TextField titleEditField = new TextField();
    private TextField authorEditField = new TextField();
    private TextField yearEditField = new TextField();
    private TextField isbnEditField = new TextField();
    private TextField dollarsEditField = new TextField();
    private TextField centsEditField = new TextField();
    private TextField quantityEditField = new TextField();
    private String imageEditPath;

    // Save and close button made class level so other event handler methods can
    // change this button being the default button(fired when enter is pressed)
    private Button saveClose;

    // The index of the current book being edited in the array
    private int editIndex;

    // made class level so file choosers can access the parent stage.
    private Stage primaryStage;

    // Image thumbnail for editing page
    ImageView editPaneImgView;

    /**
     * The start method that is run on initial program start.
     *
     * @param primaryStage Takes the main stage to display as a parameter
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        inventory = new BookCollection();
        //Load file
        inventory.loadFile();
        //Top menu
        HBox topNav = makeTopNav();
        //Left menu w/ buttons
        VBox leftNav = makeLeftNav();
        // Add item page
        FlowPane addPage = makeAddPage();

        root.setTop(topNav);
        root.setLeft(leftNav);
        root.setCenter(makeCenterPane());

        Scene scene = new Scene(root, 1080, 700);
        scene.getStylesheets().add("becker/Styles.css");

        primaryStage.setScene(scene);
        setStage(primaryStage);
        primaryStage.show();

        // Make splitpane divider invisible.
        editSplit.lookupAll(".split-pane-divider").stream()
                .forEach(div -> div.setMouseTransparent(true));
    }

    /**
     * Sets the stages width, height and name
     *
     * @param primaryStage The stage to be modified
     */
    public void setStage(Stage primaryStage) {
        primaryStage.setTitle("nozamA  | Bookstore Inventory Tool");
        primaryStage.setMinWidth(730);
        primaryStage.setMinHeight(575);
    }

    /**
     * Creates the navbar located at the top of the window
     *
     * @return The HBox for the navbar
     */
    public HBox makeTopNav() {
        HBox topNav = new HBox();
        topNav.getStyleClass().addAll("align-left",
                "bg-dk-grey",
                "padding-10",
                "padding-left-10");
        Image logoImg = new Image("becker/images/logo.png");
        ImageView logo = new ImageView(logoImg);
        logo.setPreserveRatio(true);

        topNav.getChildren().add(logo);
        return topNav;
    }

    /**
     * Creates the left navbar with the front-end buttons
     *
     * @return The left navbar as a VBox
     */
    public VBox makeLeftNav() {
        VBox leftNav = new VBox();
        leftNav.prefWidthProperty().bind(root.widthProperty());
        leftNav.setMaxWidth(250);
        leftNav.getStyleClass().addAll("bg-white",
                "align-center",
                "spacing-15");

        DropShadow shadow = new DropShadow();
        shadow.setHeight(0);
        shadow.setColor(Color.web("#e8e8e8"));
        shadow.setOffsetX(7);
        leftNav.setEffect(shadow);

        //Browse Button
        Button browse = new Button();
        browse.setPrefWidth(125);
        browse.getStyleClass().add("raised-button");
        browse.setText("Browse");
        browse.getStyleClass().add("h2");
        browse.setOnAction(e -> browseButtonEventHandler());
        leftNav.getChildren().add(browse);

        //Add Button
        Button add = new Button();
        add.setPrefWidth(125);
        add.getStyleClass().add("raised-button");
        add.setText("Add Inventory");
        add.setWrapText(true);
        add.setTextAlignment(TextAlignment.CENTER);
        add.getStyleClass().add("h2");
        add.setOnAction(event -> addButtonEventHandler());
        leftNav.getChildren().add(add);

        //Statistics Button
        Button statistics = new Button();
        statistics.setPrefWidth(125);
        statistics.getStyleClass().add("raised-button");
        statistics.setText("Statistics");
        statistics.getStyleClass().add("h2");
        statistics.setOnAction(e -> statsButtonEventHandler());
        leftNav.getChildren().add(statistics);

        return leftNav;
    }

    /**
     * Creates the center pane for browse that contains all products and the
     * edit pane that appears when a user clicks an item.
     *
     * @return The SplitPane that contains the products
     */
    public SplitPane makeCenterPane() {
        editSplit = new SplitPane();
        editSplit.setOrientation(Orientation.VERTICAL);
        editSplit.getItems().add(makeEditPane());
        editSplit.getItems().add(makeBrowsePage());
        editSplit.setDividerPositions(0);
        editSplit.setId("browse");
        return editSplit;
    }

    /**
     * This method adds the search bar to the top of the pages. This is also so
     * you can change the content of the center browse window without recreating
     * the search bar.
     *
     * @return The pagination pages
     */
    public BorderPane makeBrowsePage() {
        browseBorderPane = new BorderPane();
        browseBorderPane.setTop(makeSearchBar());
        browseBorderPane.setCenter(makePages());
        return browseBorderPane;
    }

    /**
     * This method creates the Pagination object to make as many pages necessary
     * to display all books in the collection.
     *
     * @return Pagination - the pagination object complete with all pages
     * necessary to display all books in the collection.
     */
    public Pagination makePages() {
        Pagination pages = new Pagination(getPageCount());
        pages.setId("browse");
        pages.setMaxPageIndicatorCount(15);
        pages.setPageFactory((Integer pageIndex) -> createPage(pageIndex));
        return pages;
    }

    /**
     * Creates a single page for the products, this method is called multiple
     * times by the makePages method until all books are displayed.
     *
     * @param pgNum The current page
     * @return The page as a ScrollPane
     */
    private ScrollPane createPage(int pgNum) {

        //Grid for items
        itemTile = new TilePane();
        //Horizontal spacing between items
        itemTile.setHgap(25);
        //Vertical spacing between items
        itemTile.setVgap(20);
        //Css class
        itemTile.getStyleClass().addAll("align-center", "padding-25");

        // Calculate how many pages needed and how many items to display per page.
        int currentIndex = pgNum * ITEMS_PER_PAGE;
        int itemsMissing = 0;
        if (inventory.getCount() % ITEMS_PER_PAGE != 0 && pgNum == getPageCount() - 1) {
            itemsMissing = ITEMS_PER_PAGE - (inventory.getCount() % ITEMS_PER_PAGE);
        }

        // Loop to add all items from inventory across pages
        for (int i = currentIndex; i < (pgNum * ITEMS_PER_PAGE + ITEMS_PER_PAGE) - itemsMissing; i++) {
            // Item container
            VBox itemContainer = makeItem(i);
            itemTile.getChildren().add(itemContainer);
        }

        ScrollPane centerScroll = new ScrollPane();
        //Setup scroll pane settings.
        centerScroll.getStyleClass().add("align-center");
        InnerShadow shadow = new InnerShadow();
        shadow.setHeight(0);
        shadow.setColor(Color.web("#e8e8e8"));
        shadow.setOffsetX(7);
        centerScroll.setEffect(shadow);
        centerScroll.setFitToWidth(true);
        centerScroll.setMinWidth(300);
        centerScroll.setContent(itemTile);

        itemTile.setOnScroll(event -> {
            double deltaY = event.getDeltaY() * 1.25; // *3 to make the scrolling a bit faster
            double width = centerScroll.getContent().getBoundsInLocal().getWidth();
            double vvalue = centerScroll.getVvalue();
            centerScroll.setVvalue(vvalue + -deltaY / width); // deltaY/width to make the scrolling equally fast regardless of the actual width of the component
        });

        return centerScroll;
    }

    /**
     * Creates the individual product to be viewed on the browse.
     *
     * @param index The index of the product to create
     * @return The product as a VBox
     */
    public VBox makeItem(int index) {
        // Item container
        VBox itemContainer = new VBox();
        itemContainer.getStyleClass().add("border-transparent");
        itemContainer.setOnMouseEntered(e -> itemContainer.getStyleClass().add("border-black"));
        itemContainer.setOnMouseExited(e -> itemContainer.getStyleClass().remove("border-black"));
        itemContainer.setOnMouseClicked(e -> {
            try {
                itemEventHandler(e);
            }
            catch (InvalidIndexException ex) {
                Logger.getLogger(CourseProject.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        itemContainer.setId(Integer.toString(index));

        itemContainer.getStyleClass().add("align-center");
        itemContainer.setAlignment(Pos.CENTER);

        Image thumbnail = new Image(inventory.getImage(index));

        ImageView view = new ImageView(thumbnail);
        view.setPreserveRatio(true);
        view.setFitHeight(180);
        itemContainer.getChildren().add(view);

        Label title = new Label();
        title.setTooltip(new Tooltip(inventory.getTitle(index) + " by " + inventory.getAuthor(index)));
        title.setAlignment(Pos.CENTER);
        title.setPrefWidth(180);
        title.getStyleClass().add("item-header");
        title.setText(inventory.getTitle(index) + " by " + inventory.getAuthor(index));

        Label quantity = new Label();
        try {
            quantity.setText("Qty - " + inventory.getQuantity(index));
        }
        catch (InvalidIndexException ex) {
            Logger.getLogger(CourseProject.class.getName()).log(Level.SEVERE, null, ex);
        }
        quantity.getStyleClass().add("bold");
        itemContainer.getChildren().add(title);
        itemContainer.getChildren().add(quantity);
        return itemContainer;
    }

    /**
     * Makes the horizontal search bar at the top of the browse window that the
     * user can search or sort the books.
     *
     * @return HBox - the horizontal search bar at the top of the browse window
     * that the user can search or sort the books.
     */
    public HBox makeSearchBar() {
        HBox bar = new HBox();
        bar.getStyleClass().addAll("align-center", "padding-5", "spacing-10", "bg-white");

        //Search field
        TextField search = new TextField();
        search.setPromptText("Search");

        //Go Button
        Button go = new Button("Go");
        go.setOnAction(e -> makeSearchResults(search.getText()));

        ComboBox order = new ComboBox();
        order.setPromptText("Ascending/Descending:");
        order.getItems().addAll("Ascending", "Descending");
        order.getSelectionModel().selectFirst();
        order.setEditable(false);

        ComboBox sort = new ComboBox();
        sort.setPromptText("Sort by:");
        sort.getItems().addAll("Title", "Author", "Year", "Quantity", "Price");
        sort.setEditable(false);

        //Sort Button
        Button sortButton = new Button("Sort");
        sortButton.setOnAction(e -> sort((String) sort.getSelectionModel().getSelectedItem(),
                (String) order.getSelectionModel().getSelectedItem()));

        //Sort dropdown
        bar.getChildren().addAll(search, go, sort, order, sortButton);
        return bar;
    }

    /**
     * This method displays the search results in their own tile pane so the
     * user can view the search results. If no book was found with the matching
     * title, the window shows a message to let the user know.
     *
     * @param key the book title to search for.
     */
    public void makeSearchResults(String key) {
        IntegerArrayList results = inventory.searchTitle(key);
        if (key.isEmpty()) {
            browseButtonEventHandler();
        }
        else if (results.getCount() > 0) {

            //Grid for items
            TilePane searchResults = new TilePane();
            //Horizontal spacing between items
            searchResults.setHgap(25);
            //Vertical spacing between items
            searchResults.setVgap(20);
            //Css class
            searchResults.getStyleClass().addAll("align-center", "padding-25");

            // Loop to add all items from inventory across pages
            for (int i = 0; i < results.getCount(); i++) {
                // Item container
                VBox itemContainer;
                try {
                    itemContainer = makeItem((int) results.get(i));
                    searchResults.getChildren().add(itemContainer);
                }
                catch (InvalidIndexException ex) {
                    Logger.getLogger(CourseProject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            ScrollPane centerScroll = new ScrollPane();
            //Setup scroll pane settings.
            centerScroll.getStyleClass().add("align-center");
            InnerShadow shadow = new InnerShadow();
            shadow.setHeight(0);
            shadow.setColor(Color.web("#e8e8e8"));
            shadow.setOffsetX(7);
            centerScroll.setEffect(shadow);
            centerScroll.setFitToWidth(true);
            centerScroll.setMinWidth(300);
            centerScroll.setContent(searchResults);
            centerScroll.setId("search");

            browseBorderPane.setCenter(centerScroll);
        }
        else {
            browseBorderPane.setCenter(new Label("Sorry, we couldn't find the book " + "\"" + key + "\""));
        }
    }

    /**
     * Creates the pane with all the product info for the user to edit.
     *
     * @return The edit pane as a BorderPane
     */
    public BorderPane makeEditPane() {
        BorderPane editPane = new BorderPane();
        editPane.getStyleClass().addAll("padding-10", "bg-white");
        editPane.setMinHeight(0);
        editPane.setMaxHeight(0);

        //Text fields
        VBox textFields = new VBox();
        textFields.getStyleClass().add("spacing-10");

        HBox titleRow = makeEditRow("Title", titleEditField);

        HBox authorRow = makeEditRow("Author", authorEditField);

        HBox yearRow = makeEditRow("Year", yearEditField);

        HBox isbnRow = makeEditRow("ISBN", isbnEditField);

        HBox priceRow = makePriceEditRow();

        HBox quantityRow = makeEditRow("Quantity", quantityEditField);

        //Add all text fields to the edit pane
        textFields.getChildren().addAll(titleRow, authorRow, yearRow, isbnRow,
                priceRow, quantityRow);

        Image thumbnail = new Image("becker/images/placeholder.jpg");

        Label hoverLabel = new Label("Change");
        hoverLabel.getStyleClass().add("h3");

        StackPane imagePane = new StackPane();
        editPaneImgView = new ImageView(thumbnail);
        editPaneImgView.setPreserveRatio(true);
        editPaneImgView.setFitHeight(170);
        editPaneImgView.getStyleClass().add("border-transparent");
        imagePane.setOnMouseEntered(e -> {
            imagePane.getChildren().add(hoverLabel);
            editPaneImgView.setEffect(new InnerShadow(200, Color.GRAY));
        });
        imagePane.setOnMouseExited(e -> {
            imagePane.getChildren().remove(1);
            editPaneImgView.setEffect(null);
        });
        imagePane.setOnMouseClicked(e -> getNewImage());
        imagePane.getChildren().add(editPaneImgView);

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.BASELINE_RIGHT);
        buttons.getStyleClass().add("spacing-10");

        //Save and Close Button
        saveClose = new Button("Save");
        saveClose.getStyleClass().addAll("icon-button", "h2");
        saveClose.setOnAction(e -> saveAndClose());
        buttons.getChildren().add(saveClose);

        //Remove Button
        Button remove = new Button("Remove");
        remove.getStyleClass().addAll("icon-button", "h2");
        remove.setOnAction(e -> remove(editIndex));
        buttons.getChildren().add(remove);

        //Close Button
        Button close = new Button("Close");
        close.getStyleClass().addAll("icon-button", "h2");
        close.setOnAction(e -> editUp());
        buttons.getChildren().add(close);

        //Directions label
        HBox topLabel = new HBox();
        topLabel.setAlignment(Pos.TOP_LEFT);
        Label help = new Label("Edit details:");
        help.getStyleClass().add("lg-black");
        topLabel.getChildren().add(help);

        editPane.setLeft(textFields);
        editPane.setRight(imagePane);
        editPane.setTop(topLabel);
        editPane.setBottom(buttons);

        return editPane;
    }

    /**
     * Brings the edit pane down from the top of the window.
     */
    public void editDown() {
        ((BorderPane) editSplit.getItems().get(0)).setMaxHeight(255);
        KeyValue keyValue = new KeyValue(editSplit.getDividers().get(0).positionProperty(), 0.42);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), keyValue));
        timeline.play();
        timeline.setOnFinished(e -> {
            ((BorderPane) editSplit.getItems().get(0)).setMinHeight(255);
        });

        saveClose.setDefaultButton(true);

    }

    /**
     * Hides the edit pane by collapsing it to the top of the window.
     */
    public void editUp() {
        ((BorderPane) editSplit.getItems().get(0)).setMinHeight(0);
        KeyValue keyValue = new KeyValue(editSplit.getDividers().get(0).positionProperty(), 0);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), keyValue));
        timeline.play();
        timeline.setOnFinished(e -> {
            ((BorderPane) editSplit.getItems().get(0)).setMaxHeight(0);
        });

        saveClose.setDefaultButton(false);

    }

    /**
     * The rows for each edit box on the edit pane (except for price)
     *
     * @param title The String that is associated with the product for the label
     * @param field The TextField associated with the product to be edited
     * @return The edit row as an HBox
     */
    public HBox makeEditRow(String title, TextField field) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label(title + ": ");
        label.setMinWidth(60);
        field.setMinWidth(250);
        field.setText(title);
        row.getChildren().addAll(label, field);

        return row;
    }

    /**
     * The rows for price edit box on the edit pane
     *
     * @return The editing price row as an HBox
     */
    public HBox makePriceEditRow() {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(10);
        Label dollars = new Label("Dollars: ");
        dollars.setMinWidth(50);
        dollarsEditField.setPrefWidth(125);
        Label cents = new Label("Cents: ");
        cents.setMinWidth(10);
        centsEditField.setPrefWidth(70);
        row.getChildren().addAll(dollars, dollarsEditField, cents, centsEditField);

        return row;
    }

    /**
     * Creates the add page where user can add new books.
     *
     * @return The pane where users can create new books as a FlowPane
     */
    public FlowPane makeAddPage() {
        //GET ADD BUTTONS

        // Heading Label
        Label heading = new Label("Add a New Book to Inventory:");
        heading.getStyleClass().add("h1-black");

        // Get text fields
        titleEditField = makeAddField("Title");
        authorEditField = makeAddField("Author");
        yearEditField = makeAddField("Year");
        isbnEditField = makeAddField("Isbn");
        dollarsEditField = makeAddField("Dollars");
        centsEditField = makeAddField("Cents");
        quantityEditField = makeAddField("Quantity");
        dollarsEditField.setPrefWidth(128);
        centsEditField.setPrefWidth(128);
        quantityEditField.setPrefWidth(128);

        Button clear = new Button("Clear");
        clear.getStyleClass().addAll("raised-button", "h2");
        clear.setPrefWidth(128);
        clear.setOnAction(e -> {
            titleEditField.clear();
            authorEditField.clear();
            yearEditField.clear();
            isbnEditField.clear();
            dollarsEditField.clear();
            centsEditField.clear();
            quantityEditField.clear();
        });

        Button cancel = new Button("Cancel");
        cancel.getStyleClass().addAll("raised-button", "h2");
        cancel.setOnAction(e -> browseButtonEventHandler());
        cancel.setPrefWidth(128);

        Button submit = new Button("Submit");
        submit.getStyleClass().addAll("raised-button", "h2");
        submit.setPrefWidth(128);
        submit.setOnAction(e -> validateAddBookInput());

        FlowPane addPage = new FlowPane();
        addPage.getStyleClass().addAll("align-center");
        addPage.setOrientation(Orientation.HORIZONTAL);
        addPage.setMaxWidth(415);
        addPage.setHgap(15);
        addPage.setVgap(15);
        addPage.setId("add");
        addPage.getChildren().addAll(heading, titleEditField, authorEditField, yearEditField,
                isbnEditField,
                dollarsEditField,
                centsEditField,
                quantityEditField,
                clear,
                cancel,
                submit);

        return addPage;
    }

    /**
     * Creates new TextField for the add page
     *
     * @param title The title of the TextField
     * @return The TextField for the user to input a new book into
     */
    public TextField makeAddField(String title) {
        TextField field = new TextField();
        field.setPromptText(title);
        field.setPrefWidth(200);

        return field;
    }

    /**
     * Adds a new book to the array and writes it to the file.
     *
     * @param title The title of the book
     * @param author The name of the author
     * @param isbn The Isbn of the book
     * @param year The publication year of the book
     * @param price The price of the book
     * @param quantity The amount of the book in stock
     */
    private void addNewBook(String title, String author, String isbn, int year,
            Money price, int quantity) {

        try {
            String fileStr = "becker/images/" + "placeholder.jpg";
            inventory.addBook(title, author, isbn, year, quantity, price, fileStr);
            inventory.appendFile();

            //Success alert
            Alert successAlert = new Alert(AlertType.INFORMATION);
            successAlert.setTitle("Successfully Added");
            successAlert.setHeaderText(title + " was successfully added");
            successAlert.setContentText("The changes should be shown on the 'Browse' page."
                    + " If there are any issues try restarting the program.");
            successAlert.showAndWait();

        }
        catch (Exception e) {
            //If remove failed
            Alert failAlert = new Alert(AlertType.ERROR);
            failAlert.setTitle("Error Adding");
            failAlert.setHeaderText("Oops! It looks like there's an error adding that book.");
            failAlert.setContentText("Please try again, if the problem persists, try restarting the program");
            failAlert.showAndWait();
        }

    }

    /**
     * Removes the book from the array
     *
     * @param index the index to remove book from
     */
    public void remove(int index) {

        //Prompts the user asking if they're sure they want to remove the item
        Alert confirmAlert = new Alert(AlertType.CONFIRMATION, "There is no way to undo this. Are you sure you want to remove?",
                ButtonType.YES, ButtonType.CANCEL);
        confirmAlert.setTitle("Remove Book");
        confirmAlert.setHeaderText("Delete " + inventory.getTitle(index) + "?");
        confirmAlert.showAndWait();

        if (confirmAlert.getResult() == ButtonType.YES) {
            //Call the BookCollection remove method that returns a boolean
            if (inventory.removeAt(index)) {
                String titleToRemove = titleEditField.getText();
                //If remove was successful
                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("Successfully Removed");
                successAlert.setHeaderText(titleToRemove + " was successfully removed");
                successAlert.setContentText("The changes should be shown on the 'Browse' page."
                        + " If there are any issues try restarting the program.");
                successAlert.showAndWait();

                //Refresh the browse section to show changes
                root.setCenter(makeCenterPane());
            }
            else {
                //If remove failed
                Alert failAlert = new Alert(AlertType.ERROR);
                failAlert.setTitle("Error Removing");
                failAlert.setHeaderText("Oops! It looks like there's an error removing that book.");
                failAlert.setContentText("Please try again, if the problem persists, try restarting the program");
                failAlert.showAndWait();
            }
        }
        else {

        }
    }

    /**
     * Makes the FlowPane containing the statistics in it.
     *
     * @return FlowPane to display the statistics in.
     */
    public FlowPane makeStatsPage() {
        //Calculate the stats
        inventory.getStats();

        //Create graph
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis(0, inventory.getHighestBookCount() + 1, 1);
        final BarChart<String, Number> chart
                = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Books Per Author");
        xAxis.setLabel("Author");
        yAxis.setLabel("# Of Books");
        yAxis.setTickUnit(1);

        XYChart.Series author = new XYChart.Series();

        //New Borderpane to hold the chart
        author.setName("Books Per Author");
        for (int i = 0; i < inventory.getNumberOfAuthors(); i++) {
            author.getData().add(new XYChart.Data(inventory.getAuthorIndex(i), inventory.getAuthorBookCount(i)));
        }

        //Pane layout
        FlowPane statsPane = new FlowPane();
        statsPane.getStyleClass().addAll("align-center");
        statsPane.setOrientation(Orientation.HORIZONTAL);
        statsPane.setHgap(15);
        statsPane.setVgap(15);
        statsPane.setId("stats");

        //Change the size of the chart
        chart.setPrefSize(700, 500);

        //Add all author data to chart
        chart.getData().addAll(author);
        //Add chart to flowpane
        statsPane.getChildren().add(chart);
        //Add inventory Hbox to flowpane
        statsPane.getChildren().add(getBookStats());
        //Return flowpane
        return statsPane;

    }

    /**
     * Gets the Min quantity, max quantity and average quantity book stats and
     * returns the HBox that contains them.
     *
     * @return HBox to display the statistics in.
     */
    public HBox getBookStats() {
        //Create the labels
        Label lowName = new Label();
        Label highName = new Label();
        Label lowCount = new Label();
        Label highCount = new Label();
        Label avgCount = new Label();

        Label lowTitle = new Label("Book With Least Quantity:");
        Label highTitle = new Label("Book With Most Quantity:");
        Label avgTitle = new Label("Average Quantity of Books in Inventory");

        try {
            //Make sure the array exists
            if (inventory.inventoryStats() != null) {
                double[] bookArr = inventory.inventoryStats();
                //Set label texts
                lowName.setText(inventory.getTitle((int) bookArr[0]));
                lowCount.setText(String.valueOf(inventory.getQuantity((int) bookArr[0])) + " In Stock");
                //The highest quantity book
                highName.setText(inventory.getTitle((int) bookArr[1]));
                highCount.setText(String.valueOf(inventory.getQuantity((int) bookArr[1])) + " In Stock");
                //Get the average quantity of the books
                avgCount.setText(String.valueOf(bookArr[2]) + " Books");
            }
            else {
                System.out.println("There was an error trying to calculate inventory stats");

                lowName.setText("Error");
                lowCount.setText("Error");
                highName.setText("Error");
                highCount.setText("Error");
                avgCount.setText("Error");
            }

        }
        catch (InvalidIndexException iie) {
            System.out.println("Error Trying To Get Info For Label For Inventory Stats");
        }

        //Create the hbox to display
        HBox inventoryStocks = new HBox();

        //Style the labels
        VBox low = new VBox(lowTitle, lowName, lowCount);
        low.getStyleClass().addAll("padding-10", "bg-white", "align-center");

        VBox high = new VBox(highTitle, highName, highCount);
        high.getStyleClass().addAll("padding-10", "bg-white", "align-center");

        VBox avg = new VBox(avgTitle, avgCount);
        avg.getStyleClass().addAll("padding-10", "bg-white", "align-center");

        //Style the hbox
//        inventoryStocks.getStyleClass().addAll("padding-10", "bg-white");
        //Fill the hbox
        inventoryStocks.getChildren().addAll(low, high, avg);
        //Return a HBOX with the labels and things being displayed
        return inventoryStocks;
    }

    /**
     * This method determines the field to be sorted by and calls the
     * appropriate method from the business class. It then refreshes the browse
     * window to display the sorted collection.
     *
     * @param sortBy the field to sort the books by. e.g Title.
     * @param order the order to be sorted, ascending/descending
     */
    public void sort(String sortBy, String order) {

        if (sortBy != null) {
            switch (sortBy) {
                case "Title": {
                    if (order.equals("Ascending")) {
                        inventory.sortByTitleAscending();
                    }
                    else {
                        inventory.sortByTitleDescending();
                    }
                    break;
                }
                case "Author": {
                    if (order.equals("Ascending")) {
                        inventory.sortByAuthorAscending();
                    }
                    else {
                        inventory.sortByAuthorDescending();
                    }
                    break;
                }
                case "Year": {
                    if (order.equals("Ascending")) {
                        inventory.sortByYearAscending();
                    }
                    else {
                        inventory.sortByYearDescending();
                    }
                    break;
                }

                case "Quantity": {
                    if (order.equals("Ascending")) {
                        inventory.sortByQuantityAscending();
                    }
                    else {
                        inventory.sortByQuantityDescending();
                    }
                    break;
                }

                case "Price": {
                    if (order.equals("Ascending")) {
                        inventory.sortByPriceAscending();
                    }
                    else {
                        inventory.sortByPriceDescending();
                    }
                    break;
                }
            }
            browseBorderPane.setCenter(makePages());
        }
    }

    /**
     * Prompts the user to select an image for the book that they have added
     */
    public void getNewImage() {
//        ClassLoader loader = getClass().getClassLoader();
        //Default image path
        String defaultOutput = ("becker/images/" + "placeholder.jpg");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Please Select The Location Of The Image File.");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        //Tries to get the path of the image
        if (selectedFile != null) {
            try {
                imageEditPath = selectedFile.toURI().toURL().toExternalForm();
                editPaneImgView.setImage(new Image(imageEditPath));
            }
            catch (IOException ex) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Invalid Image Location");
                alert.setHeaderText("Oops! It looks like the image you selected cannot be found.");
                alert.setContentText("The default placeholder image will be used instead,"
                        + "please try again.");

                alert.showAndWait();
                imageEditPath = defaultOutput;
            }
        }
    }

    /**
     * Gets the number of pages
     *
     * @return The number of pages
     */
    public int getPageCount() {
        return (inventory.getCount() + ITEMS_PER_PAGE - 1) / ITEMS_PER_PAGE;
    }

    /**
     * The event handler that gets assigned to every item when it is made in the
     * makeItem method. It will show the edit pane with all of the selected
     * items details.
     *
     * @param e The MouseEvent
     * @throws InvalidIndexException If any of the product's info goes beyond
     * the bounds of the array.
     */
    public void itemEventHandler(MouseEvent e) throws InvalidIndexException {
        VBox sourceItem = (VBox) e.getSource();
        int index = Integer.valueOf(sourceItem.getId());
        editIndex = index;

        titleEditField.setText(inventory.getTitle(index));
        authorEditField.setText(inventory.getAuthor(index));
        yearEditField.setText(String.valueOf(inventory.getYear(index)));
        isbnEditField.setText(String.valueOf(inventory.getIsbn(index)));
        Money temp = inventory.getPrice(index);
        dollarsEditField.setText(String.valueOf(temp.getDollars()));
        centsEditField.setText(String.valueOf(temp.getCents()));
        quantityEditField.setText(String.valueOf(inventory.getQuantity(index)));

        editPaneImgView.setImage(new Image(inventory.getImage(index)));
        imageEditPath = inventory.getImage(index);
        editDown();
    }

    /**
     * Displays statistics in the center of the program.
     */
    public void statsButtonEventHandler() {
        if (!root.getCenter().getId().equals("stats")) {
            root.setCenter(makeStatsPage());
        }
    }

    /**
     * Sets add to the center of the program.
     */
    public void addButtonEventHandler() {
        if (!root.getCenter().getId().equals("add")) {
            root.setCenter(makeAddPage());
        }
    }

    /**
     * Sets browse to the center of the program.
     */
    public void browseButtonEventHandler() {
        if (!root.getCenter().getId().equals("browse") || !browseBorderPane.getCenter().getId().equals("browse")) {
            root.setCenter(makeCenterPane());
        }
        else if (editSplit.getDividers().get(0).getPosition() > 0) {
            editUp();
        }
    }

    /**
     * Attempts to save the user inputted data into the array overwriting the
     * previous data. If any inputted data is invalid it will inform the user.
     */
    private void saveAndClose() {

        try {
            String title = titleEditField.getText();
            String author = authorEditField.getText();
            int year = Integer.valueOf(yearEditField.getText());
            String isbn = (Long.valueOf(isbnEditField.getText())).toString();
            Money price = new Money(Long.valueOf(dollarsEditField.getText()), Byte.valueOf(centsEditField.getText()));
            int quantity = Integer.valueOf(quantityEditField.getText());

            boolean changesMade = true;
            changesMade = !title.equals(inventory.getTitle(editIndex))
                    || !author.equals(inventory.getAuthor(editIndex))
                    || !(year == inventory.getYear(editIndex))
                    || !isbn.equals(inventory.getIsbn(editIndex))
                    || !(price.getDollars() == ((Money) inventory.getPrice(editIndex)).getDollars())
                    || !(price.getCents() == ((Money) inventory.getPrice(editIndex)).getCents())
                    || !(quantity == inventory.getQuantity(editIndex))
                    || !imageEditPath.equals(inventory.getImage(editIndex));
            if (title.isEmpty() || author.isEmpty() || year < 0 || isbn.startsWith("-")
                    || price.getDollars() < 0 || price.getCents() < 0 || quantity < 0) {
                throw new NumberFormatException();

            }

            if (changesMade) {
                inventory.setTitle(editIndex, title);
                inventory.setAuthor(editIndex, author);
                inventory.setYear(editIndex, year);
                inventory.setIsbn(editIndex, isbn);
                inventory.setPrice(editIndex, price);
                inventory.setQuantity(editIndex, quantity);
                inventory.setImage(editIndex, imageEditPath);

                if (inventory.saveFile()) {
                    //Alerts the user that the save was succesful
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Successfuly Saved");
                    alert.setHeaderText("Changes Have Been Succesfully Saved");
                    alert.setContentText("The changes should be shown on the 'Browse' page."
                            + " If there are any issues try restarting the program.");
                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error With File");
                    alert.setHeaderText("Oops! It looks like we're having trouble with the saving file");
                    alert.setContentText("Please try restarting the program and making sure that the books.nza file isn't open elsewhere.");
                    alert.showAndWait();
                }
                itemTile.getChildren().set(editIndex, makeItem(editIndex));
            }

        }
        catch (NumberFormatException nfe) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Input Error");
            alert.setHeaderText("Oops! It looks like there's an error with your input.");
            alert.setContentText("Please make sure no fields are empty and that year,"
                    + " dollars, cents, and quantity fields only have positive numbers.");

            alert.showAndWait();
        }
        catch (InvalidIndexException ex) {
            System.out.println("saveAndClose() Invalid Index Exception");
        }

    }

    /**
     * Validates the user input to prevent program crashes. It will check to see
     * if any fields are empty, or if there are negative numbers for the numeric
     * fields.
     *
     * @return boolean - true if input is good, false otherwise.
     */
    private boolean validateAddBookInput() {
        try {
            String title = titleEditField.getText();
            String author = authorEditField.getText();
            int year = Integer.valueOf(yearEditField.getText());
            String isbn = isbnEditField.getText();
            Money price = new Money(Long.valueOf(dollarsEditField.getText()), Byte.valueOf(centsEditField.getText()));
            int quantity = Integer.valueOf(quantityEditField.getText());

            if (title.isEmpty() || author.isEmpty() || year < 0 || isbn.startsWith("-")
                    || price.getDollars() < 0 || price.getCents() < 0 || quantity < 0) {
                throw new NumberFormatException();
            }
            else {
                addNewBook(title, author, isbn, year, price, quantity);
                return true;
            }
        }
        catch (NumberFormatException nfe) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Input Error");
            alert.setHeaderText("Oops! It looks like there's an error with your input.");
            alert.setContentText("Please make sure no fields are empty and that year,"
                    + " dollars, cents, and quantity fields only have positive numbers.");

            alert.showAndWait();
            return false;
        }
    }
}
