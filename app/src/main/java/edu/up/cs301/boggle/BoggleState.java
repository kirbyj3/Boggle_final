package edu.up.cs301.boggle;


import android.app.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import edu.up.cs301.game.infoMsg.GameState;

/**
 * This is our Game State class. This is where all the methods are for Boggle to run.
 *
 * @author Charles Rayner
 * @author Michael Waitt
 * @author Jacob Kirby
 * @version March 2016
 */
public class BoggleState extends GameState {

    private static HashSet<String> dictionary = null; //the dictionary of words
    public int arrayIndex;//int for the index, used in the Local Game
    private int playerTurn; //tells which players turn it is
    private int player1Score; //tracks the score of player1
    private int player2Score; //tracks the score of player2
    //NETWORK
    private String[] currentWord = new String[2]; //the current word the player is making
    private boolean timer; //true if the timer is running, false if timer has stopped
    private ArrayList<String> wordBank1; //the current words in the word bank
    private ArrayList<String> wordBank2; //the current words in the word bank
    private String[][] gameBoard1 = new String[4][4]; //array of all the letters on the board
    private String[][] gameBoard2 = new String[4][4]; //array of all the letters on the board
    private boolean[][] visited = new boolean[4][4];//tells weather a tile has been searched already
   //BUG
    private int[][] selectedLetters1 = new int[20][2]; //list of selected letters positions
    private int[][] selectedLetters2 = new int[20][2]; //list of selected letters positions

    private ArrayList<String> compUsedWords = new ArrayList<String>(); //list of all words computer uses in game
    private String curLetter; //the current letter that is selected
    private int curLetterRow; //current row of letter selected
    private int curLetterCol;
    private int secondsLeft;  //tells how much time is left on the timer
    //private String compPrevWord = ""; //computer's previously played word
    private int gameOver; //determines if game is over

    /**
     * The BoggleState constructor. The heart and soul of Boggle. Constructs the gameState of Boggle.
     */
    public BoggleState() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                visited[x][y] = false;
            }
        }
        gameOver = 0;
        arrayIndex = 1;
        playerTurn = 0;
        player1Score = 0;
        player2Score = 0;
        //NETWORK
        for (int i = 0; i < 2; i++) {
            currentWord[i] = "";
        }
        timer = true;
        curLetter = "a";
        curLetterRow = 4; //4 means null
        curLetterCol = 4; // 4 means null
        secondsLeft = 180; // 3 minutes of play

        //Assigns random letters to the tiles
        Random r1 = new Random();
        char c1 = (char) (r1.nextInt(26) + 'A');
        Random r2 = new Random();
        char c2 = (char) (r2.nextInt(26) + 'A');
        Random r3 = new Random();
        char c3 = (char) (r3.nextInt(26) + 'A');
        Random r4 = new Random();
        char c4 = (char) (r4.nextInt(26) + 'A');
        Random r5 = new Random();
        char c5 = (char) (r5.nextInt(26) + 'A');
        Random r6 = new Random();
        char c6 = (char) (r6.nextInt(26) + 'A');
        Random r7 = new Random();
        char c7 = (char) (r7.nextInt(26) + 'A');
        Random r8 = new Random();
        char c8 = (char) (r8.nextInt(26) + 'A');
        Random r9 = new Random();
        char c9 = (char) (r9.nextInt(26) + 'A');
        Random r10 = new Random();
        char c10 = (char) (r10.nextInt(26) + 'A');
        Random r11 = new Random();
        char c11 = (char) (r11.nextInt(26) + 'A');
        Random r12 = new Random();
        char c12 = (char) (r12.nextInt(26) + 'A');
        Random r13 = new Random();
        char c13 = (char) (r13.nextInt(26) + 'A');
        Random r14 = new Random();
        char c14 = (char) (r14.nextInt(26) + 'A');
        Random r15 = new Random();
        char c15 = (char) (r15.nextInt(26) + 'A');
        Random r16 = new Random();
        char c16 = (char) (r16.nextInt(26) + 'A');

        gameBoard1[0][0] = ("" + c1);
        gameBoard1[1][0] = ("" + c2);
        gameBoard1[2][0] = ("" + c3);
        gameBoard1[3][0] = ("" + c4);
        gameBoard1[0][1] = ("" + c5);
        gameBoard1[1][1] = ("" + c6);
        gameBoard1[2][1] = ("" + c7);
        gameBoard1[3][1] = ("" + c8);
        gameBoard1[0][2] = ("" + c9);
        gameBoard1[1][2] = ("" + c10);
        gameBoard1[2][2] = ("" + c11);
        gameBoard1[3][2] = ("" + c12);
        gameBoard1[0][3] = ("" + c13);
        gameBoard1[1][3] = ("" + c14);
        gameBoard1[2][3] = ("" + c15);
        gameBoard1[3][3] = ("" + c16);

        //makes sure all Q tiles turn into QU tiles
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (gameBoard1[i][j].equals("Q")) {
                    gameBoard1[i][j] = ("Qu");
                }
            }
        }
        //forces some of the tiles to be vowels
        double randomVowelDouble1 = Math.random() * 5;
        int randomVowelInt1 = (int) randomVowelDouble1;
        if (randomVowelInt1 == 5) {
            randomVowelInt1 = 4;
        }
        String randomVowel1 = "";
        if (randomVowelInt1 == 0) {
            randomVowel1 = "A";
        } else if (randomVowelInt1 == 1) {
            randomVowel1 = "E";
        } else if (randomVowelInt1 == 2) {
            randomVowel1 = "I";
        } else if (randomVowelInt1 == 3) {
            randomVowel1 = "O";
        } else if (randomVowelInt1 == 4) {
            randomVowel1 = "U";
        }

        double randomVowelDouble2 = Math.random() * 5;
        int randomVowelInt2 = (int) randomVowelDouble2;
        if (randomVowelInt2 == 5) {
            randomVowelInt2 = 4;
        }
        String randomVowel2 = "";
        if (randomVowelInt2 == 0) {
            randomVowel2 = "A";
        } else if (randomVowelInt2 == 1) {
            randomVowel2 = "E";
        } else if (randomVowelInt2 == 2) {
            randomVowel2 = "I";
        } else if (randomVowelInt2 == 3) {
            randomVowel2 = "O";
        } else if (randomVowelInt2 == 4) {
            randomVowel2 = "U";
        }

        double randomVowelDouble3 = Math.random() * 5;
        int randomVowelInt3 = (int) randomVowelDouble3;
        if (randomVowelInt3 == 5) {
            randomVowelInt3 = 4;
        }
        String randomVowel3 = "";
        if (randomVowelInt3 == 0) {
            randomVowel3 = "A";
        } else if (randomVowelInt3 == 1) {
            randomVowel3 = "E";
        } else if (randomVowelInt3 == 2) {
            randomVowel3 = "I";
        } else if (randomVowelInt3 == 3) {
            randomVowel3 = "O";
        } else if (randomVowelInt3 == 4) {
            randomVowel3 = "U";
        }



        int randomRow1 = (int) (Math.random() * 4);
        if (randomRow1 == 4) {
            randomRow1 = 3;
        }
        int randomCol1 = (int) (Math.random() * 4);
        if (randomCol1 == 4) {
            randomCol1 = 3;
        }

        int randomRow2 = (int) (Math.random() * 4);
        if (randomRow2 == 4) {
            randomRow2 = 3;
        }
        int randomCol2 = (int) (Math.random() * 4);
        if (randomCol2 == 4) {
            randomCol2 = 3;
        }

        int randomRow3 = (int) (Math.random() * 4);
        if (randomRow3 == 4) {
            randomRow3 = 3;
        }
        int randomCol3 = (int) (Math.random() * 4);
        if (randomCol3 == 4) {
            randomCol3 = 3;
        }

        int vowelCount = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (gameBoard1[i][j].equals("A") || gameBoard1[i][j].equals("E") ||
                        gameBoard1[i][j].equals("I") || gameBoard1[i][j].equals("O") || gameBoard1[i][j].equals("U")) {
                    vowelCount++;
                }
            }
        }
        if (vowelCount < 3) {
            gameBoard1[randomRow1][randomCol1] = randomVowel1;
            gameBoard1[randomRow2][randomCol2] = randomVowel2;
            gameBoard1[randomRow3][randomCol3] = randomVowel3;
        }
        //sets all the selected letters to null at start of game
        for (int k = 0; k < 20; k++) {
           //BUG
            selectedLetters1[k][0] = 4;
            selectedLetters1[k][1] = 4;
            selectedLetters2[k][0] = 4;
            selectedLetters2[k][1] = 4;
        }
        wordBank1 = new ArrayList<String>();  // human used words
        wordBank2 = new ArrayList<String>();

        gameBoard2 = Arrays.copyOf(gameBoard1, gameBoard1.length);


        compUsedWords = new ArrayList<String>(); //computer used words
    }

    /**
     * DEEP COPY
     * The BoggleState copy constructor. Constructs a deep copy of a passed in gameState.
     *
     * @param state the old boggleState to be copied
     */
    public BoggleState(BoggleState state) {
        playerTurn = state.playerTurn;
        player1Score = state.player1Score;
        player2Score = state.player2Score;
        curLetter = state.curLetter;
        curLetterRow = state.curLetterRow;
        curLetterCol = state.curLetterCol;
        secondsLeft = state.secondsLeft;
        gameOver = state.gameOver;
        wordBank1 = new ArrayList<String>();
        wordBank2 = new ArrayList<String>();

        for (int i = 0; i < state.getWordBank(0).size(); i++) {

            wordBank1.add(state.wordBank1.get(i));
        }


        for (int i = 0; i < state.getWordBank(1).size(); i++) {

            wordBank2.add(state.wordBank2.get(i));
        }


        compUsedWords = state.compUsedWords;
        timer = state.timer;
        gameBoard1 = Arrays.copyOf(state.gameBoard1, state.gameBoard1.length);
        gameBoard2 = Arrays.copyOf(state.gameBoard2, state.gameBoard2.length);
        visited = Arrays.copyOf(state.visited, state.gameBoard1.length);
        selectedLetters1 = Arrays.copyOf(state.selectedLetters1, state.selectedLetters1.length);
        selectedLetters2 = Arrays.copyOf(state.selectedLetters2, state.selectedLetters2.length);
        //compPrevWord = state.compPrevWord;
        //NETWORK
        this.currentWord = new String[2];
        for (int i = 0; i < 2; i++) {
            this.currentWord[i] = state.currentWord[i];
        }
    }

    //--------------------------- Getter/Setter End -----------------------------


    public boolean isTimer() {
        return timer;
    }

    public void setTimer(boolean timer) {
        this.timer = timer;
    }

    public ArrayList<String> getCompUsedWords() {
        return compUsedWords;
    }

    public void setCompUsedWords(String word) {
        compUsedWords.add(word);
    }

    public String[][] getGameBoard(int playerNum) {

        if (playerNum == 0) {
            return Arrays.copyOf(gameBoard1, gameBoard1.length);
        } else {
            return Arrays.copyOf(gameBoard2, gameBoard2.length);
        }
    }

    public int getWinner() {
        if (getPlayer1Score() > getPlayer2Score()) {
            return 1;
        } else if (getPlayer2Score() > getPlayer1Score()) {
            return 2;
        } else {
            return 3;
        }
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }

    //NETWORK
    public String getCurrentWord(int playerNum) {
        return currentWord[playerNum];
    }

    public void setCurrentWord(String currentWord, int playerNum) {
        this.currentWord[playerNum] = currentWord;
    }

    public ArrayList<String> getWordBank(int playerNum) {

        if (playerNum == 0) {
            return wordBank1;
        } else {
            return wordBank2;
        }
    }

    public void setWordBank(String word, int playerNum) {
        if (playerNum == 0) {
            this.wordBank1.add(word);
        } else {
            this.wordBank2.add(word);
        }
    }

    public HashSet<String> getDictionary() {
        return dictionary;
    }

    public int getGameOver() {
        return this.gameOver;
    }

    public void setGameOver(int gameOver) {
        this.gameOver = gameOver;
    }

    public int[][] getSelectedLetters(int playerNum) {
        if(playerNum == 0) {
            return Arrays.copyOf(selectedLetters1, selectedLetters1.length);
        }else{
            return Arrays.copyOf(selectedLetters2, selectedLetters2.length);
        }
    }

    public void setSelectedLetters(int[][] selectedLetters, int playerNum) {
        if(playerNum == 0){
            this.selectedLetters1 = Arrays.copyOf(selectedLetters1, selectedLetters1.length);
        }
        else{
            this.selectedLetters2 = Arrays.copyOf(selectedLetters2, selectedLetters2.length);
        }

    }

    public int getSecondsLeft() {
        return secondsLeft;
    }

    public void setSecondsLeft(int seconds) {
        this.secondsLeft = seconds;
    }

    public String getCurLetterFromBoard(int curLetterRow, int curLetterCol, String[][] gameBoard) {
        return gameBoard[curLetterRow][curLetterCol];
    }

    public String getCurLetter() {
        return curLetter;
    }

    public void setCurLetter(String curLetter) {
        this.curLetter = curLetter;
    }

    public int getCurLetterRow() {
        return curLetterRow;
    }

    public void setCurLetterRow(int curLetterRow) {
        this.curLetterRow = curLetterRow;
    }

    public int getCurLetterCol() {
        return curLetterCol;
    }

    public void setCurLetterCol(int curLetterCol) {
        this.curLetterCol = curLetterCol;
    }

    public int getLastLetterCol(int[][] selectedLetters) {
        return selectedLetters[getLastLetterIndex(selectedLetters)][1];
    }

    public int getLastLetterRow(int[][] selectedLetters) {
        return selectedLetters[getLastLetterIndex(selectedLetters)][0];
    }

    public int getLastLetterIndex(int[][] selectedLetters) {
        int counter = 0;
        while (selectedLetters[counter + 1][0] != 4) {
            counter++;
        }
        return counter;
    }
//--------------------------- End Getter/Setter End -----------------------------

    /**
     * Determines if the word is more then 3 letters, which means its playable
     *
     * @param word the word being checked
     */
    public boolean wordLength(String word) {
        if (word.length() < 3) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if tile can be removed based on the grounds that it is the last checked tile.
     *
     * @param word            word to be edited
     * @param selectedLetters 2d array for the coordinates of each letter tiles
     *                        the array is 16 ints long to represent each tile. 1 -16. each tile is has a 2
     *                        dimension array to correspond to its row and column. When a tile's row or column is 4,
     *                        it means that it is null.
     */
    public void removeLetter(String word, int[][] selectedLetters) {
        int index = getLastLetterIndex(selectedLetters);
        selectedLetters[index][0] = 4;
        selectedLetters[index][1] = 4;
    }

    /**
     * Checks if a word is in the dictionary
     *
     * @param word the word being checked
     * @return a boolean dictating if it is a legal word
     * @throws IOException
     */
    public boolean inDictionary(String word) throws IOException {
        BufferedReader reader;
        word = word.toLowerCase();
        if (dictionary == null) {
            dictionary = new HashSet<String>();
            int count = 0;
            try {
                Activity myActivity = BoggleMainActivity.activity;
                InputStream ins = myActivity.getResources().openRawResource(myActivity.getResources().getIdentifier("words", "raw", myActivity.getPackageName()));
                reader = new BufferedReader(new InputStreamReader(ins));
                String line;

                while ((line = reader.readLine()) != null) {
                    dictionary.add(line.toLowerCase());
                    count++;
                }
            } catch (IOException e) {
                return false;
            }
        }
        boolean rtnVal = dictionary.contains(word);
        return rtnVal;
        /**
         * External Citation
         *
         * Date: 15 March 2016
         * Problem: Didn't know how to check for spelling errors.
         * Resource: http://www.java-gaming.org/index.php?topic=28057.0
         * Solution: Used the code from this post.
         */
    }


    /**
     * checks if tile can be removed based on the grounds that it is the last picked tile
     *
     * @param curLetterRow  the row that the new letter is in
     * @param curLetterCol  the col that the new letter is in
     * @param lastLetterRow the row that the last selected letter was in
     * @param lastLetterCol the col that the last selected letter was in
     */
    public boolean canRemove(int[][] selectedLetters, int curLetterRow, int curLetterCol,
                             int lastLetterRow, int lastLetterCol) {

        int index = getLastLetterIndex(selectedLetters);
        lastLetterRow = selectedLetters[index][0];
        lastLetterCol = selectedLetters[index][1];

        if (curLetterRow == lastLetterRow && curLetterCol == lastLetterCol) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * adds letter to word
     *
     * @param word            the word to edit
     * @param selectedLetters 2d array for the coordinates of each letter tiles
     * @param curLetterRow    the row that the new letter is in
     * @param curLetterCol    the col that the new col is in
     * @param letter          letter to add to end of word
     */
    public void addLetter(String word, int[][] selectedLetters, int curLetterRow, int curLetterCol,
                          String letter) {
        int index = getLastLetterIndex(selectedLetters);
        selectedLetters[index + 1][0] = curLetterRow;
        selectedLetters[index + 1][1] = curLetterCol;
    }

    /**
     * adds letter to word
     *
     * @param currentWord the word to add to
     * @param letter      letter to add to end of word
     * @return currentWord    the resulting word
     */
    public String addToWord(String currentWord, String letter) {

        if (currentWord.length() == 0 || currentWord == null) {
            currentWord = letter;
        } else if (currentWord.length() > 0) {
            currentWord = currentWord.concat(letter);
        }
        return currentWord;

    }

    /**
     * removes letter from word
     *
     * @param currentWord
     */
    public String removeFromWord(String currentWord) {
        if (currentWord == "") {
            return "";
        }
        String lastLetter = currentWord.substring(currentWord.length() - 1);
        if (currentWord.length() > 0 && !lastLetter.equals("u")) {
            currentWord = currentWord.substring(0, currentWord.length() - 1);
        } else if (currentWord.length() > 0 && lastLetter.equals("u")) {
            currentWord = currentWord.substring(0, currentWord.length() - 2);
        }
        return currentWord;
    }

    /**
     * checks if tile can be added based on the grounds that it has not already been added and
     * it is adjacent to the last tile picked
     *
     * @param selectedLetters 2d array for the coordinates of each letter tiles
     * @param curLetterRow    the row that the new letter is in
     * @param curLetterCol    the col that the new letter is in
     * @param lastLetterRow   the row that the last selected letter was in
     * @param lastLetterCol   the col that the last selected letter was in
     */
    public Boolean canAdd(int[][] selectedLetters, int curLetterRow, int curLetterCol,
                          int lastLetterRow, int lastLetterCol) {
        System.out.println("LAST SELECTED LETTER: " + getLastLetterRow(selectedLetters));


        if (getLastLetterRow(selectedLetters) == 4) {
            return true;
        } else if (isCurrentAdjacentToLast
                (lastLetterRow, lastLetterCol, curLetterRow, curLetterCol) == 1) {

            return true;
        } else {
            return false;
        }
    }


    /**
     * Updates the score based on the length of the word entered
     * Words of size 3 and 4 = 1 point
     * 5 = 2 points
     * 6 = 3 points
     * 7 = 5 points
     * 8 or more = 11 points
     * <p/>
     * CAVEAT: Does not take into account who made the move. This is a feature that will be added
     * in later versions of this class.
     *
     * @param word the word the user has submitted
     */
    public int updateScore(String word, int playerNum) {
        int score = 0;

        //Check to see how long the word is
        if (word.length() <= 4 && word.length() >= 3) {
            score = 1;
        } else if (word.length() == 5) {
            score = 2;
        } else if (word.length() == 6) {
            score = 3;
        } else if (word.length() == 7) {
            score = 5;
        } else if (word.length() >= 8) {
            score = 11;
        }
        //Update the player's score
        //player1Score = score;

        //resets the values of the selectedLetters array
        for (int i = 0; i < 20; i++) {
            if(playerNum == 0) {
                selectedLetters1[i][0] = 4;
                selectedLetters1[i][1] = 4;
            }else{
                selectedLetters2[i][0] = 4;
                selectedLetters2[i][1] = 4;
            }

        }
        return score;
    }

    /**
     * METHOD ONLY USED FOR AI
     *
     * @return
     * @p * Updates the score based on the length of the word entered
     * Words of size 3 and 4 = 1 point
     * 5 = 2 points
     * 6 = 3 points
     * 7 = 5 points
     * 8 or more = 11 points
     * Param word
     */
    public int compUpdateScore(String word) {
        int score = 0;

        //Check to see how long the word is
        if (word.length() <= 4 && word.length() >= 3) {
            score = 1;
        } else if (word.length() == 5) {
            score = 2;
        } else if (word.length() == 6) {
            score = 3;
        } else if (word.length() == 7) {
            score = 5;
        } else if (word.length() >= 8) {
            score = 11;
        }
        return score;
    }

    /**
     * Rotates the board
     *
     * @param board two dimensional array representing the board
     */
    public void rotateBoard(String[][] board, int playerNum) {
        String[][] tmp = new String[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                tmp[i][j] = board[4 - j - 1][i]; //Rotates the board
            }
        }
        /**
         * External Citation
         *
         * Date: 15 March 2016
         * Problem: Didn't know how to rotate a two dimensional array.
         * Resource: http://stackoverflow.com/questions/42519/how-do-you-rotate-
         * a-two-dimensional-array
         * Solution: Used the sample code from this post.
         */

        //Copies the rotated board to the existing board
        if (playerNum == 0) {
            gameBoard1 = tmp;
        } else {
            gameBoard2 = tmp;
        }
    }

    /**
     * checks if tile to select is adjacent to last tile picked.
     *
     * @param lastLetterRow the row that the last selected letter was in
     * @param lastLetterCol the col that the last selected letter was in
     * @param curLetterRow  the row that the new letter is in
     * @param curLetterCol  the col that the new letter is in
     */
    public int isCurrentAdjacentToLast(int lastLetterRow,
                                       int lastLetterCol, int curLetterRow, int curLetterCol) {


        int trueOrFalse = 0; //false is 0, true is 1
        //didn't use boolean because it wasn't working


        if (curLetterRow == lastLetterRow - 1) {
            if (curLetterCol == lastLetterCol - 1 || curLetterCol == lastLetterCol || curLetterCol == lastLetterCol + 1) {
                trueOrFalse = 1;
            }
        }

        if (curLetterRow == lastLetterRow) {
            if (curLetterCol == lastLetterCol - 1 || curLetterCol == lastLetterCol + 1) {
                trueOrFalse = 1;
            }
        }

        if (curLetterRow == lastLetterRow + 1) {
            if (curLetterCol == lastLetterCol - 1 || curLetterCol == lastLetterCol || curLetterCol == lastLetterCol + 1) {
                trueOrFalse = 1;
            }
        }


        return trueOrFalse;
    }

    /**
     * @param word Adds a sumbitted correct word to the word bank
     */
    public void addToWordBank(String word, int playerNum) {
        //Adds it to the arrayList

        if (playerNum == 0) {
            wordBank1.add(word);
        } else {
            wordBank2.add(word);
        }
    }
}
