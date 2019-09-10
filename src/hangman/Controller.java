package hangman;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private TextField inputText;
    @FXML private Text wordText, guessesRemaining, resultText;
    @FXML private TextArea gameOverText;
    @FXML private ImageView hangmanImage;
    private static Image empty = new Image("/assets/Hangman_Empty.png",160,160,false,true),
            one = new Image("/assets/Hangman_One.png",160,160,false,true),
            two = new Image("/assets/Hangman_Two.png",160,160,false,true),
            three = new Image("/assets/Hangman_Three.png",160,160,false,true),
            four = new Image("/assets/Hangman_Four.png",160,160,false,true),
            five = new Image("/assets/Hangman_Five.png",160,160,false,true),
            six = new Image("/assets/Hangman_Six.png",160,160,false,true);

    public Controller() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        wordText.setText(GameEngine.getBlurredWord());
        hangmanImage.setImage(empty);
        guessesRemaining.setText(GameEngine.getGuessesRemaining());
        gameOverText.setVisible(false);
    }
    //TODO: Add null input exception
    //TODO: Scale GUI items with resizing of window pane
    public void input() {
        String rawInput = inputText.getText();
        //This if-else ensures that if the game is over, the program catches 'play' or 'exit' input instead of a letter
        if (gameOverText.isVisible()){
            if (rawInput.toLowerCase().contains("play") || rawInput.toLowerCase().contains("exit")){
                GameEngine.GameOver(rawInput);
                wordText.setText(GameEngine.getBlurredWord());
                guessesRemaining.setText(GameEngine.getGuessesRemaining());
                gameOverText.setVisible(false);
                guessesRemaining.setVisible(true);
                resultText.setVisible(true);
                resultText.setText("Awaiting your attempt...");
                inputText.setPromptText("Enter your guess here!");
            }//end if
        }//end if
        //Resume normal function here... Input char and update GUI accordingly
        else{
            char input = rawInput.charAt(0);
            resultText.setText(GameEngine.guessAction(input));
            wordText.setText(GameEngine.getBlurredWord());
            guessesRemaining.setText(GameEngine.getGuessesRemaining());
            if (!wordText.getText().contains("*") || guessesRemaining.getText().contains("0")){
                inputText.setPromptText("Enter 'play' or 'exit'");
                guessesRemaining.setVisible(false);
                resultText.setVisible(false);
                gameOverText.setVisible(true);
                gameOverText.setText(resultText.getText() + "\n" + GameEngine.getGameResults());
            }//end if
        }//end if
        //Change ImageView based on amount of incorrect answers
        int hangCount = GameEngine.getHangCount();
        switch (hangCount){
            case 1: hangmanImage.setImage(one);
                break;
            case 2: hangmanImage.setImage(two);
                break;
            case 3: hangmanImage.setImage(three);
                break;
            case 4: hangmanImage.setImage(four);
                break;
            case 5: hangmanImage.setImage(five);
                break;
            case 6: hangmanImage.setImage(six);
                break;
            default: hangmanImage.setImage(empty);
                break;
        }//end switch
        //Prevents having to click the text box after every input
        inputText.setText("");
        inputText.requestFocus();
    }//end input
}//end Controller
