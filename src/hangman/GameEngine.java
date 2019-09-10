package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class GameEngine {

    private static final ArrayList<String> hangman = new ArrayList<>();
    private static int hangCount = 0;
    private static ArrayList<Character> guesses = new ArrayList<>();
    private static StringBuilder correctGuesses = new StringBuilder();
    private static ArrayList<String> wordsUsed = new ArrayList<>();
    private static String chosenWord;
    private static String blurredWord;
    private static int wins;
    private static int losses;
    private static Scanner input = new Scanner(System.in);

    static String getBlurredWord(){
        return blurredWord;
    }

    static String getGuessesRemaining(){
        return "You have " + Math.abs(hangCount - 6) + " incorrect guess(es) remaining before you lose...";
    }

    static int getHangCount(){
        return hangCount;
    }

    static String getGameResults(){
        String feedback;
        feedback = "You have " + wins + " wins" + " and " + losses + " losses.";
        return feedback + "\n" + "The words used so far have been: " + wordsUsed + "\n" + "Enter 'play' or 'exit'";
    }//end getGameResults

    GameEngine()      {
        loadFile(input);

        chosenWord = wordSelection();
        blurredWord = chosenWord.replaceAll("[^ " + correctGuesses + "]", "*");
    }//end Main

    private static String wordSelection()    {
        //Choose a hangman word at random from list
        double i = (Math.random() * hangman.size());
        int j = (int )Math.floor(i);
        String word = hangman.get(j);
        wordsUsed.add(word);
        return word;
    }//end wordSelection

    private static void loadFile(Scanner input)      {
        //load array of hangman words to choose from
        try{
            File file = new File("src/assets/hangman.txt");
            input = new Scanner(file);
            while (input.hasNext()) {
                hangman.add(input.next());
            }//end while
            input.close();
        }   catch(FileNotFoundException e)  {
            System.out.println("The file \"hangman.txt\" is missing. Aborting...");
            System.exit(0);
        }//end catch
    }//end loadFile

    static String guessAction(char guess)    {
        //Create blurred word to show to user
        blurredWord = chosenWord.replaceAll("[^ " + correctGuesses + "]", "*");
        //Input error catching
        if (Character.isDigit(guess)){
            return "Please enter an alphabetic letter. ";
        }//end if
        if (guesses.contains(guess)) {
            return "You have already guessed '"+ guess + "'... Try another...";
        }//end if
        guesses.add(guess);
        //If your guess is correct, make sure you cannot guess it again
        if (chosenWord.contains("" + guess) && !chosenWord.equalsIgnoreCase(blurredWord)) {
            correctGuesses.append(guess);
            blurredWord = chosenWord.replaceAll("[^ " + correctGuesses + "]", "*");
            if (!chosenWord.equalsIgnoreCase(blurredWord)) {
                return "'" + guess + "' is in the word. You have guessed correctly!";
            }//end if
        }//end if
        //Your guess is incorrect, end game or lessen guesses
        else {
            hangCount++;
            if (hangCount == 6){
                losses++;
                return "You have been hung! May you rest in peace..."
                        + "\n" + "Would you like to play again?";
            }//end if
            return "Sorry, '" + guess + "' is not in the word...";
        }//end else
        //If you guess the word in its entirety, win sequence
        if (chosenWord.equalsIgnoreCase(blurredWord)) {
            // GAME OVER
            wins++;
            return "You have successfully avoided death! Yay surviving!"
                    + "\n" + "You had " + hangCount + " miss(es)."
                    + "\n" + "Would you like to play again?";
        }//end if
        return "Awaiting your attempt...";
    }//end guessAction

    static void GameOver(String playAgain)   {
        //If user chose to exit, do so here
        if (playAgain.contains("exit")){
            System.exit(0);
        }//end if
        //User has optioned to continue playing, add word used to bank and clear all variables for a replay
        hangCount = 0;
        guesses.clear();
        correctGuesses.setLength(0);
        chosenWord = wordSelection();
        blurredWord = chosenWord.replaceAll("[^ " + correctGuesses + "]", "*");
    }//end GameOver

}//end GameEnding class
