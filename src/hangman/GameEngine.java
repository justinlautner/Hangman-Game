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

    static String getGameResults(){
        String feedback;
        if (losses > wins) {
            feedback = " You aren't very good at this are you...";
        }//end if
        else if (wins > losses) {
            feedback = "You are adept at cheating death...";
        }//end else if
        else {
            feedback = " Eh... You're average. How stale.";
        }
        return feedback + "\n" + "The words used so far have been: " + wordsUsed;
    }

    public static void main(String[] args)      {
        loadFile(input);

        chosenWord = wordSelection();
        blurredWord = chosenWord.replaceAll("[^ " + correctGuesses + "]", "*");
    }//end Main

    private static String wordSelection()    {
        //Choose a hangman word at random
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
        blurredWord = chosenWord.replaceAll("[^ " + correctGuesses + "]", "*");
        if (Character.isDigit(guess)){
            return "Please enter an alphabetic letter. ";
        }
        if (guesses.contains(guess)) {
            return "You have already guessed this character... Try another...";
        }//end if
        guesses.add(guess);
        //adds to correctGuesses and updates the blurred word
        if (chosenWord.contains("" + guess) && !chosenWord.equalsIgnoreCase(blurredWord)) {
            correctGuesses.append(guess);
            blurredWord = chosenWord.replaceAll("[^ " + correctGuesses + "]", "*");
            if (!chosenWord.equalsIgnoreCase(blurredWord)) {
                return "You have guessed correctly!";
            }//end if
        }//end if
        else {
            hangCount++;
            if (hangCount == 6){
                losses++;
                return "You have been hung! May you rest in peace..."
                        + "\n" + " Would you like to play again?";
            }
            return "Sorry, that letter is not in the word...";
        }//end else
        if (chosenWord.equalsIgnoreCase(blurredWord)) {
            // GAME OVER
            wins++;
            return "You have successfully avoided death! Yay surviving!"
                    + "\n" + " You had " + hangCount + " miss(es)."
                    + "\n" + " Would you like to play again?";
        }//end if
        return "Awaiting your attempt...";
    }//end guessAction

    static void GameOver(String playAgain)   {
        if (playAgain.contains("exit")){
            System.exit(0);
        }
        //User has optioned to continue playing, add word used to bank and clear all variables for a replay
        hangCount = 0;
        guesses.clear();
        correctGuesses.setLength(0);
        chosenWord = wordSelection();
        blurredWord = chosenWord.replaceAll("[^ " + correctGuesses + "]", "*");
    }

}
