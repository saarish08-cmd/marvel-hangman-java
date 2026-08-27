//Name: Aarish Sharma
//Date:
//Purpose: To store and manage the game data
public class gameState
{
    //Instance Variables
    private String secretWord; //The word that needs to be guessed
    private String revealedWord; //Holds the guessed letters, it basically stores the current progress
    private int points; //Player's score
    private int mistakes; //# of wrong guesses
    private int round; //# of rounds
    private int lettersLeft; //# of letters left to win the game

    //--- Deafult Constructor ---
    public gameState ()
    {
	secretWord = "";
	revealedWord = "";
	points = 0;
	mistakes = 0;
	round = 1;
    }


    //--- Accessors ---
    public String getSecretWord ()
    {
	return secretWord;
    }


    public String getRevealedWord ()  //Returns the string that holds the progress
    {
	return revealedWord;
    }


    public int getPoints ()  //Returns the current score
    {
	return points;
    }


    public int getMistakes ()  //Returns the mistakes
    {
	return mistakes;
    }


    public int getRound ()  //Return the round #
    {
	return round;
    }


    public int getLettersLeft ()  //Returns the # of letters left to win
    {
	return lettersLeft;
    }


    //--- Mutators ---
    public void setSecretWord (String s)
    {
	secretWord = s;
    }


    public void setRevealedWord (String r)
    {
	revealedWord = r;
    }


    public void setPoints (int p)
    {
	points = p;
    }


    public void setRound (int r)
    {
	round = r;
    }


    public void setMistakes (int m)
    {
	mistakes = m;
    }


    public void setLettersLeft (int l)
    {
	lettersLeft = l;
    }


    //--- Game Logic Method ---
    public void addPoints (int p)
    {
	points += p;
    }


    public void addMistakes ()
    {
	mistakes++;
    }


    //Check if the player lost
    public boolean isGameOver ()
    {
	return mistakes >= 6;
    }


    //Check if the player won
    public boolean isWordSolved ()
    {
	return lettersLeft <= 0;
    }
}
