package launchVideo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class VideoPlayer extends Application {

    private int currentImageIndex = 0;
    private String[] avatarImages = {
            "file:path/to/avatar_red.png",   // Replace with actual file paths
            "file:path/to/avatar_blue.png",  // Replace with actual file paths
            "file:path/to/avatar_green.png"  // Replace with actual file paths
    };

    @Override
    public void start(Stage primaryStage) {
        // Absolute path to your video file
        String videoFilePath = "C:/Users/celin/Downloads/mazeVideo.mp4"; // Replace with the correct path

        // Check if the file exists
        File videoFile = new File(videoFilePath);
        if (!videoFile.exists()) {
            System.out.println("File not found: " + videoFilePath);
            return;
        }

        // Create Media object with correct URI
        Media media = new Media(videoFile.toURI().toString());

        // Create a MediaPlayer to control the media
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // Create a MediaView to display the video
        MediaView mediaView = new MediaView(mediaPlayer);

        // Create a layout pane and add the MediaView
        StackPane videoPane = new StackPane();
        videoPane.getChildren().add(mediaView);

        // Create a scene for the video
        Scene videoScene = new Scene(videoPane, 800, 600);

        // Set the size of the media view to fill the window
        mediaView.fitWidthProperty().bind(videoScene.widthProperty());
        mediaView.fitHeightProperty().bind(videoScene.heightProperty());

        // --- Avatar Selection Screen ---

        // Create a black background and a centered rectangle for the avatar
        StackPane avatarSelectionPane = new StackPane();
        avatarSelectionPane.setStyle("-fx-background-color: black;");

        // Create a rectangle for the avatar frame
        Rectangle avatarFrame = new Rectangle(400, 400); // Takes 50% of an 800px width
        avatarFrame.setFill(Color.LIGHTGRAY);

        // Create an ImageView to display the avatar
        ImageView avatarView = new ImageView(new Image(avatarImages[currentImageIndex]));
        avatarView.setFitWidth(300);
        avatarView.setFitHeight(300);

        // Add left and right arrows to change the avatar
        Text leftArrow = new Text("<");
        leftArrow.setFill(Color.WHITE);
        leftArrow.setStyle("-fx-font-size: 48;");

        Text rightArrow = new Text(">");
        rightArrow.setFill(Color.WHITE);
        rightArrow.setStyle("-fx-font-size: 48;");

        // Arrow functionality to switch avatar images
        leftArrow.setOnMouseClicked(e -> changeAvatar(-1, avatarView));
        rightArrow.setOnMouseClicked(e -> changeAvatar(1, avatarView));

        // HBox for the avatar and arrows
        HBox avatarBox = new HBox(20, leftArrow, avatarView, rightArrow);
        avatarBox.setAlignment(Pos.CENTER);

        // Create a text label below the rectangle
        Text label = new Text("Choose the color for the agent one");
        label.setFill(Color.WHITE);
        label.setStyle("-fx-font-size: 24;");

        // Arrange the avatar frame and label in a BorderPane
        BorderPane avatarScreen = new BorderPane();
        avatarScreen.setCenter(avatarBox);
        avatarScreen.setBottom(label);
        BorderPane.setAlignment(label, Pos.CENTER);
        BorderPane.setAlignment(avatarBox, Pos.CENTER);

        avatarSelectionPane.getChildren().addAll(avatarFrame, avatarScreen);

        // Create the avatar scene
        Scene avatarScene = new Scene(avatarSelectionPane, 800, 600);

        // Set the stage with the video scene first
        primaryStage.setScene(videoScene);
        primaryStage.setTitle("JavaFX MP4 Video Player with Avatar Change");
        primaryStage.show();

        // Start playing the video
        mediaPlayer.play();

        // Switch to avatar screen when Escape key is pressed
        videoScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                mediaPlayer.stop();
                primaryStage.setScene(avatarScene);
            }
        });
    }

    // Method to change the avatar image
    private void changeAvatar(int direction, ImageView avatarView) {
        currentImageIndex = (currentImageIndex + direction + avatarImages.length) % avatarImages.length;
        avatarView.setImage(new Image(avatarImages[currentImageIndex]));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
