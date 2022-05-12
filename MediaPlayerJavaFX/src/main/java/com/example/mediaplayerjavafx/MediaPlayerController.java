package com.example.mediaplayerjavafx;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;


public class MediaPlayerController implements Initializable {

    @FXML
    private VBox vBoxParent;

    @FXML
    private MediaView mvVideo;

    @FXML
    private HBox playbackTimeHBox;

    @FXML
    private Label playbackTime;

    @FXML
    private Label playbackSpeed;

    @FXML
    private Slider sliderTime;

    @FXML
    private Label sliderTotalTime;

    @FXML
    private HBox hBoxControls;

    @FXML
    private HBox leftBlankHBox;

    @FXML
    private HBox mainControlsHBox;

    @FXML
    private Button rewindBtn;

    @FXML
    private Button pausePlayBtn;
    @FXML
    private Button stopBtn;
    @FXML
    private Button fastForwardBtn;

    @FXML
    private HBox volumeContolHBox;

    @FXML
    private Label volumeLabel;

    @FXML
    private Slider volumeSlider;

    private boolean atEndOfVideo = false;
    private boolean isPlaying = true;
    private boolean isMuted = true;

    private File fileVideo;
    private Media mediaVideo;
    private MediaPlayer medplyVideo;

    private ImageView ivPlay;
    private ImageView ivPause;
    private ImageView ivReplay;
    private ImageView ivStop;
    private ImageView ivFForward;
    private ImageView ivRewind;

    private ImageView ivSelection;

    private ImageView ivVolumeOn;
    private ImageView ivVolumeMute;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        final int IV_Size = 20;

        fileVideo = new File("src/main/resources/Media/UltronFight.mp4");
        mediaVideo = new Media(fileVideo.toURI().toString());
        medplyVideo = new MediaPlayer(mediaVideo);
        mvVideo.setMediaPlayer(medplyVideo);

        Image playButton = new Image("UI_Images/play.png");
        ivPlay = new ImageView(playButton);
        ivPlay.setFitHeight(IV_Size);
        ivPlay.setFitWidth(IV_Size);

        Image stopButton = new Image("UI_Images/stop.png");
        ivStop = new ImageView(stopButton);
        ivStop.setFitHeight(IV_Size);
        ivStop.setFitWidth(IV_Size);

        Image pauseButton = new Image("UI_Images/pause.png");
        ivPause = new ImageView(pauseButton);
        ivPause.setFitHeight(IV_Size);
        ivPause.setFitWidth(IV_Size);

        Image replayButton = new Image("UI_Images/repeat.png");
        ivReplay = new ImageView(replayButton);
        ivReplay.setFitHeight(IV_Size);
        ivReplay.setFitWidth(IV_Size);

        Image fastForwardButton = new Image("UI_Images/fast.png");
        ivFForward = new ImageView(fastForwardButton);
        ivFForward.setFitHeight(IV_Size);
        ivFForward.setFitWidth(IV_Size);

        Image rewindButton = new Image("UI_Images/slow.png");
        ivRewind = new ImageView(rewindButton);
        ivRewind.setFitHeight(IV_Size);
        ivRewind.setFitWidth(IV_Size);

        Image fullSpeaker = new Image("UI_Images/soundOn.png");
        ivVolumeOn = new ImageView(fullSpeaker);
        ivVolumeOn.setFitHeight(15);
        ivVolumeOn.setFitWidth(15);

        Image muteSpeaker = new Image("UI_Images/soundMute.png");
        ivVolumeMute = new ImageView(muteSpeaker);
        ivVolumeMute.setFitHeight(15);
        ivVolumeMute.setFitWidth(15);

        pausePlayBtn.setGraphic(ivPlay);
        fastForwardBtn.setGraphic(ivFForward);
        rewindBtn.setGraphic(ivRewind);
        stopBtn.setGraphic(ivStop);
        playbackSpeed.setText("");
        volumeLabel.setGraphic(ivVolumeOn);

        //pausePlay button event handler
        pausePlayBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {;
                Button buttonPlay = (Button) actionEvent.getSource();

                if (atEndOfVideo) {
                    sliderTime.setValue(0);
                    atEndOfVideo = false;
                    isPlaying = false;
                    medplyVideo.setRate(1.0);
                    playbackSpeed.setText("");
                    pausePlayBtn.setGraphic(ivPause);
                }

                if (isPlaying) {
                    buttonPlay.setGraphic(ivPlay);
                    medplyVideo.pause();
                    medplyVideo.setRate(1.0);
                    playbackSpeed.setText("");
                    isPlaying = false;
                } else {
                    buttonPlay.setGraphic(ivPause);
                    medplyVideo.play();
                    medplyVideo.setRate(1.0);
                    playbackSpeed.setText("");
                    isPlaying = true;
                }


                /*if((sliderTime.getValue() <= 0) && isPlaying){
                    pausePlayBtn.setGraphic(ivPause);
                }*/
            }
        });

        //Volume Slider listener
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                medplyVideo.setVolume(volumeSlider.getValue());
                if (medplyVideo.getVolume() != 0.0) {
                    volumeLabel.setGraphic(ivVolumeOn);
                    isMuted = false;
                } else {
                    volumeLabel.setGraphic(ivVolumeMute);
                    isMuted = true;
                }
            }
        });

        //Changing of volume icon based on click
        volumeLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(isMuted){
                    volumeLabel.setGraphic(ivVolumeOn);
                    volumeSlider.setValue(0.5);
                    isMuted = false;
                } else {
                    volumeLabel.setGraphic(ivVolumeMute);
                    volumeSlider.setValue(0);
                    isMuted = true;
                }
            }
        });

        //Resizing mediaview size proportionate to window size
        vBoxParent.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observableValue, Scene oldScene, Scene newScene) {
                if(oldScene == null && newScene != null){
                    mvVideo.fitHeightProperty().bind(newScene.heightProperty().subtract(hBoxControls.heightProperty().add(20)));
                }
            }
        });

        //Maximum time slider duration
        medplyVideo.totalDurationProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration oldDuration, Duration newDuration) {
                sliderTime.setMax(newDuration.toSeconds());
                sliderTotalTime.setText(getTime(newDuration));
            }
        });

        sliderTime.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean wasChanging, Boolean isChanging) {
                if(!isChanging){
                    medplyVideo.seek(Duration.seconds(sliderTime.getValue()));
                }
            }
        });

        sliderTime.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                double currentTime = medplyVideo.getCurrentTime().toSeconds();
                if(Math.abs(currentTime - newValue.doubleValue()) > 0.5){
                    medplyVideo.seek(Duration.seconds(newValue.doubleValue()));
                }
                labelMatchedEndVideo(playbackTime.getText(), sliderTotalTime.getText());
            }
        });

        medplyVideo.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration oldTime, Duration newTime) {
                if(!sliderTime.isValueChanging()){
                    sliderTime.setValue(newTime.toSeconds());
                }
                labelMatchedEndVideo(playbackTime.getText(), sliderTotalTime.getText());
            }
        });

        //Stop button event listener
        stopBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(isPlaying){
                    medplyVideo.stop();
                    pausePlayBtn.setGraphic(ivPlay);
                    playbackSpeed.setText("");
                    isPlaying = false;
                } else {
                    pausePlayBtn.setGraphic(ivPause);
                    playbackSpeed.setText("");
                    isPlaying = true;
                }

            }
        });

        //fast forward button event listener
        fastForwardBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(isPlaying){
                    medplyVideo.setRate(2.0);
                    playbackSpeed.setText("2x");
                } else {
                    medplyVideo.setRate(1.0);
                }
                isPlaying = true;
            }
        });

        //slow playback button event listener
        rewindBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(isPlaying){
                    medplyVideo.setRate(0.5);
                    playbackSpeed.setText("0.5x");
                } else {
                    medplyVideo.setRate(1.0);
                }
                isPlaying = true;
            }
        });

        bindCurrentTimeLabel();
    }

    //binding current time function
    public void bindCurrentTimeLabel() {
        playbackTime.textProperty().bind(Bindings.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getTime(medplyVideo.getCurrentTime()) + "/";
            }
        }, medplyVideo.currentTimeProperty()));
    }

    //binding time duration function
    public String getTime(Duration time) {
        int hours = (int) time.toHours();
        int minutes = (int) time.toMinutes();
        int seconds = (int) time.toSeconds();

        if (seconds > 59) seconds = seconds % 60;
        if (minutes > 59) minutes = minutes % 60;
        if (hours > 60) hours = hours % 60;

        if (hours > 0) return String.format("%d:%02d:%02", hours, minutes, seconds);
        else return String.format("%02d:%02d", minutes, seconds);
    }

    public void labelMatchedEndVideo(String labelTime, String labelTotalTime){
        for(int i = 0; i < labelTotalTime.length(); i++){
            if (labelTime.charAt(i) != labelTotalTime.charAt(i)){
                atEndOfVideo = false;
                if(isPlaying){ pausePlayBtn.setGraphic(ivPause);}
                else {pausePlayBtn.setGraphic(ivPlay);}
                break;
            }
            else{
                atEndOfVideo = true;
                pausePlayBtn.setGraphic(ivReplay);
            }
        }
    }


}

