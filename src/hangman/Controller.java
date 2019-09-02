package hangman;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private TextField inputText;
    @FXML private Text wordText, guessesRemaining, resultText;
    @FXML private TextArea gameOverText;

    public Controller() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        wordText.setText(GameEngine.getBlurredWord());
        guessesRemaining.setText(GameEngine.getGuessesRemaining());
        gameOverText.setVisible(false);
    }
    //TODO: Add null input exception
    //TODO: Format GUI
    //TODO: Change how enter button affects textfield focusing
    public void input() {
        String rawInput = inputText.getText();
        if (gameOverText.isVisible()){
            if (rawInput.toLowerCase().contains("play") || rawInput.toLowerCase().contains("exit")){
                GameEngine.GameOver(rawInput);
                wordText.setText(GameEngine.getBlurredWord());
                guessesRemaining.setText(GameEngine.getGuessesRemaining());
                gameOverText.setVisible(false);
                guessesRemaining.setVisible(true);
                resultText.setVisible(true);
                resultText.setText("Awaiting your attempt...");
                inputText.setText("");
                inputText.setPromptText("Enter your guess here!");
            }
        }
        else{
            char input = rawInput.charAt(0);
            inputText.setText("");
            resultText.setText(GameEngine.guessAction(input));
            wordText.setText(GameEngine.getBlurredWord());
            guessesRemaining.setText(GameEngine.getGuessesRemaining());

            if (resultText.getText().contains("Would you like to play again?")){
                inputText.setPromptText("Enter 'play' or 'exit'");
            }
            if (!wordText.getText().contains("*") || guessesRemaining.getText().contains("0")){
                //wordText.setVisible(false);
                guessesRemaining.setVisible(false);
                resultText.setVisible(false);
                gameOverText.setVisible(true);
                gameOverText.setText(resultText.getText() + "\n" + GameEngine.getGameResults());
            }
        }
    }
}
