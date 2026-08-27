//Name: Aarish Sharma
//Date:
//Purpose: Hangman - Marvel Edition
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.Applet;
import java.io.*;
import java.applet.AudioClip;
public class hangman extends Applet implements ActionListener
{
    //For screens
    Panel p_card;
    Panel card1, card2, card3, card4, inst2, inst3, inst4, endScreen, win, loss;
    CardLayout cdLayout = new CardLayout ();

    //Game screen's grid
    int row = 3;
    int col = 9;
    JButton pics[] = new JButton [row * col];
    char b[] [] = {
	    {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'},
	    {'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R'},
	    {'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0'},  // One empty slot to finish the grid
	};

    int sqDimension = 70;
    String picFileType = ".png";
    JLabel turnpic;

    //Game Logic
    gameState game = new gameState ();

    //Game Screen Buttons
    JButton again, guess, more;

    //Game Modes
    boolean isChallenge = false;
    int timeLeft;
    int target = 0;
    Timer gameTimer; //Timer to time the challenge
    JLabel timerLabel;

    //Game Style
    Panel wordPanel; // This will hold the row of letter images for the answers
    JLabel[] letterImages; // Array to hold each individual answer letter's image

    //Formatting
    Color backgroundColour = new Color (0, 67, 134);
    Color buttonColour = new Color (3, 47, 86);

    //Add Song
    AudioClip avengersTheme;
    public void init ()
    {
	// Load the Avengers theme song
	avengersTheme = getAudioClip (getDocumentBase (), "avengers_themeAura.wav");
	p_card = new Panel ();
	p_card.setLayout (cdLayout);
	//opening ();
	//instructions ();
	//instructions2 ();
	//instructions3 ();
	//instructions4 ();
	settings ();
	chooseWord (); //Choose The Word
	gameScreen ();
	endGameScreen ();
	win ();
	loss ();
	resize (650, 705);
	setLayout (new BorderLayout ());
	add ("Center", p_card);
    }


    public void opening ()
    { //--- Opening Screen ---
	card1 = new Panel ();
	card1.setBackground (backgroundColour);
	JButton title = new JButton (createImageIcon ("titleScreen.png"));
	title.setActionCommand ("s2");
	title.addActionListener (this);
	title.setBorder (null);
	card1.add (title);
	p_card.add ("1", card1);
    }


    public void instructions ()
    { // --- Instruction Screen ---
	card2 = new Panel ();
	card2.setBackground (backgroundColour);
	JButton inst = new JButton (createImageIcon ("inst1.png"));
	inst.setActionCommand ("inst");
	inst.addActionListener (this);
	inst.setBorder (null);
	card2.add (inst);
	p_card.add ("2", card2);
    }


    public void instructions2 ()
    { // --- Instruction Screen2 ---
	inst2 = new Panel ();
	inst2.setBackground (backgroundColour);
	JButton inst = new JButton (createImageIcon ("inst2.png"));
	inst.setActionCommand ("inst2");
	inst.addActionListener (this);
	inst.setBorder (null);
	inst2.add (inst);
	p_card.add ("5", inst2);
    }


    public void instructions3 ()
    { // --- Instruction Screen3 ---
	inst3 = new Panel ();
	inst3.setBackground (backgroundColour);
	JButton inst = new JButton (createImageIcon ("inst3.png"));
	inst.setActionCommand ("inst3");
	inst.addActionListener (this);
	inst.setBorder (null);
	inst3.add (inst);
	p_card.add ("6", inst3);
    }


    public void instructions4 ()
    { // --- Instruction Screen4 ---
	inst4 = new Panel ();
	inst4.setBackground (backgroundColour);
	JButton inst = new JButton (createImageIcon ("inst4.png"));
	inst.setActionCommand ("s3");
	inst.addActionListener (this);
	inst.setBorder (null);
	inst4.add (inst);
	p_card.add ("7", inst4);
    }


    public void settings ()
    { // --- Settings Page ---
	card3 = new Panel ();
	card3.setBackground (backgroundColour);
	JLabel title = new JLabel (createImageIcon ("settings.png"));

	//Game Option Buttons
	Panel options = new Panel ();
	JButton endless = new JButton (createImageIcon ("endless.png"));
	endless.addActionListener (this);
	endless.setActionCommand ("endless");
	endless.setBorder (null);

	JButton challenge = new JButton (createImageIcon ("challenge.png"));
	challenge.addActionListener (this);
	challenge.setActionCommand ("challenge");
	challenge.setBorder (null);

	//Descriptions
	Panel description = new Panel ();
	JLabel endlessDescription = new JLabel (createImageIcon ("description1.png"));
	JLabel challengeDescription = new JLabel (createImageIcon ("description2.png"));

	//Add the options to the panel
	description.add (endlessDescription);
	description.add (challengeDescription);
	options.add (endless);
	options.add (challenge);

	card3.add (title);
	card3.add (description);
	card3.add (options);
	p_card.add ("3", card3);
    }


    public void gameScreen ()
    { //Game Screen ---------------------------------------
	avengersTheme.loop (); //Start Song
	card4 = new Panel ();
	card4.setBackground (backgroundColour);

	//Title
	JButton title = new JButton (createImageIcon ("title.png"));
	title.addActionListener (this);
	title.setActionCommand ("title");
	title.setBorder (null);

	Panel p2 = new Panel ();
	turnpic = new JLabel (createImageIcon ("hangman0.png"));
	p2.add (turnpic);

	//Game Info (Points & Rounds)
	Panel gameInfo = new Panel ();

	//Rounds
	JLabel round = new JLabel (createImageIcon ("rounds.png"));
	JLabel roundCounter = new JLabel (game.getRound () + "");
	roundCounter.setFont (new Font ("Algerian", Font.ITALIC, 35));
	roundCounter.setForeground (new Color (175, 17, 12));

	//Spacing
	JLabel spacing = new JLabel (createImageIcon ("spacing.png"));
	JLabel spacing2 = new JLabel (createImageIcon ("spacing.png"));

	//Points
	JLabel point = new JLabel (createImageIcon ("points.png"));
	JLabel pointCounter = new JLabel (game.getPoints () + "");
	pointCounter.setFont (new Font ("Algerian", Font.ITALIC, 35));
	pointCounter.setForeground (new Color (175, 17, 12));

	//Round must be added before the timer
	gameInfo.add (round);
	gameInfo.add (roundCounter);
	gameInfo.add (spacing);

	//Timer
	//If the game mode is challenge, then add the timer elements
	if (isChallenge)
	{
	    JLabel timerN = new JLabel (createImageIcon ("timer.png"));
	    timerLabel = new JLabel (timeLeft + "s");
	    timerLabel.setFont (new Font ("Algerian", Font.BOLD, 25));
	    timerLabel.setForeground (new Color (175, 17, 12));
	    gameInfo.add (timerN);
	    gameInfo.add (timerLabel);
	}
	//Add all elements into the panel
	gameInfo.add (spacing2);
	gameInfo.add (point);
	gameInfo.add (pointCounter);

	//Display The Number of Blanks Required
	wordPanel = new Panel ();
	for (int i = 0 ; i < letterImages.length ; i++)
	    wordPanel.add (letterImages [i]);

	//Letter image grid
	Panel grid = new Panel (new GridLayout (row, col));
	int m = 0;
	for (int i = 0 ; i < row ; i++)
	{
	    for (int j = 0 ; j < col ; j++)
	    {
		pics [m] = new JButton (createImageIcon (b [i] [j] + picFileType));
		pics [m].setPreferredSize (new Dimension (sqDimension, sqDimension));
		pics [m].setActionCommand (b [i] [j] + ""); //Sets the action command to the letter name itself
		pics [m].addActionListener (this);
		pics [m].setBorder (null);

		//If the game is won or lost, disable all buttons
		if (game.getLettersLeft () == 0 || game.getMistakes () >= 6)
		    pics [m].setEnabled (false);
		// If the letter was already guessed (opened from a saved game), disable it
		else if (game.getRevealedWord ().indexOf (b [i] [j]) != -1)
		    pics [m].setEnabled (false);
		grid.add (pics [m]);
		m++;
	    }
	}

	//Button Panel
	Panel p3 = new Panel ();
	//Play Again ------------
	again = new JButton (createImageIcon ("next.png"));
	again.addActionListener (this);
	again.setActionCommand ("again");
	again.setBorder (null);
	p3.add (again);

	//Guess The Word ---------------
	guess = new JButton (createImageIcon ("guess.png"));
	guess.addActionListener (this);
	guess.setActionCommand ("guess");
	guess.setBorder (null);
	p3.add (guess);

	//More Options ----------------------
	more = new JButton (createImageIcon ("superpowers.png"));
	more.addActionListener (this);
	more.setActionCommand ("more");
	more.setBorder (null);
	p3.add (more);

	//Add all elements to game screen
	card4.add (title);
	card4.add (gameInfo);
	card4.add (p2);
	card4.add (wordPanel);
	card4.add (grid);
	card4.add (p3);
	p_card.add ("4", card4);
    }


    public void endGameScreen ()
    { //--- End Screen ---
	endScreen = new Panel ();
	endScreen.setBackground (backgroundColour);
	JButton title = new JButton (createImageIcon ("endGame.png"));
	title.setActionCommand ("s1");
	title.addActionListener (this);
	title.setBorder (null);
	endScreen.add (title);
	p_card.add ("end", endScreen);
    }


    public void win ()
    { //--- Win Screen ---
	win = new Panel ();
	win.setBackground (backgroundColour);
	JButton title = new JButton (createImageIcon ("win.png"));
	title.setActionCommand ("s1");
	title.addActionListener (this);
	title.setBorder (null);
	win.add (title);
	p_card.add ("8", win);
    }


    public void loss ()
    { //--- Lose Screen ---
	loss = new Panel ();
	loss.setBackground (backgroundColour);
	JButton title = new JButton (createImageIcon ("lose.png"));
	title.setActionCommand ("s1");
	title.addActionListener (this);
	title.setBorder (null);
	loss.add (title);
	p_card.add ("9", loss);
    }


    //----- Choose A Word For The Game -----
    public void chooseWord ()
    {
	String file = "marvel.txt";
	try
	{
	    //Count the number of lines to then randomize the word selection
	    BufferedReader reader = new BufferedReader (new FileReader (file));
	    int lines = 0;
	    while (reader.readLine () != null)
		lines++;
	    reader.close ();

	    //Pick a random word
	    int random = (int) (Math.random () * lines);
	    reader = new BufferedReader (new FileReader (file));

	    for (int i = 0 ; i < random ; i++)
		reader.readLine ();

	    //Store the word in object
	    String word = reader.readLine ().toUpperCase ();
	    game.setSecretWord (word);
	    game.setLettersLeft (word.length ()); //Track the number of letters left to guess
	    reader.close ();

	    //Create the same amount of blanks as the word
	    String progress = "";
	    letterImages = new JLabel [game.getSecretWord ().length ()]; //Get the length of the word from the secretWord object
	    for (int i = 0 ; i < game.getSecretWord ().length () ; i++)
	    {
		letterImages [i] = new JLabel (createImageIcon ("blank.png")); //Add The Number Of Blanks
		progress += "_"; //This is the progress string and updates as the guesses are made
	    }

	    game.setRevealedWord (progress); //Store the progress in the revealedWord object
	}
	catch (IOException e)
	{
	    System.err.println ("Error reading file: " + e.getMessage ());
	}
    }


    //----- Save The Current Progress Of Solving The Word -----
    public void saveGame ()
    {
	try
	{
	    //Save all the game information into a file
	    PrintWriter out = new PrintWriter (new FileWriter ("saveData.txt"));
	    out.println (game.getSecretWord ()); //Save the secret word to the object
	    out.println (game.getRevealedWord ()); //Save the string storing the progress of the guess to the object
	    out.println (game.getLettersLeft ()); //Save the number of letters left to guess to the object
	    out.println (game.getMistakes ()); //Save the number of mistakes to the object
	    out.println (game.getPoints ()); //Save the number of points to the object
	    out.println (game.getRound ()); //Save the number of rounds to the object
	    out.close ();
	    JOptionPane.showMessageDialog (null, "Progress Saved Successfully!");
	}
	catch (IOException e)
	{
	    JOptionPane.showMessageDialog (null, "Error saving game");
	}
    }


    //----- Load The Previous Progress Of Solving The Word -----
    public void openGame ()
    {
	try
	{
	    //Open and display all the saved progress
	    BufferedReader in = new BufferedReader (new FileReader ("saveData.txt"));
	    game.setSecretWord (in.readLine ());
	    game.setRevealedWord (in.readLine ());
	    game.setLettersLeft (Integer.parseInt (in.readLine ()));
	    game.setMistakes (Integer.parseInt (in.readLine ()));
	    game.setPoints (Integer.parseInt (in.readLine ()));
	    game.setRound (Integer.parseInt (in.readLine ()));
	    in.close ();

	    //Update the word panel with the saved letters
	    String progress = game.getRevealedWord ();
	    letterImages = new JLabel [game.getSecretWord ().length ()];

	    //Loop through the entire word
	    for (int i = 0 ; i < game.getSecretWord ().length () ; i++)
	    {
		char letter = progress.charAt (i);
		if (letter != '_')
		    // If the character isn't an underscore then it was guessed
		    letterImages [i] = new JLabel (createImageIcon ("answer" + letter + ".png"));
		else
		    // Otherwise keep it blank
		    letterImages [i] = new JLabel (createImageIcon ("blank.png"));
	    }

	    //Disable the used buttons again
	    for (int i = 0 ; i < progress.length () ; i++)
	    {
		char foundLetter = progress.charAt (i);
		if (foundLetter != '_')
		{
		    //Disable the button for the found letter
		    for (int j = 0 ; j < pics.length ; j++)
		    {
			if (pics [j].getActionCommand ().equals (foundLetter + ""))
			    pics [j].setEnabled (false);
		    }
		}
	    }

	    //Update the other parts of the game
	    turnpic.setIcon (createImageIcon ("hangman" + game.getMistakes () + ".png"));
	    gameScreen ();
	    cdLayout.show (p_card, "4");
	    JOptionPane.showMessageDialog (null, "Progress Loaded!");
	}
	catch (Exception e)
	{
	    JOptionPane.showMessageDialog (null, "File Not Found");
	}
    }


    //----- Give 1 Letter Hint -----
    public void hint ()
    {
	//Check if only one letter is left
	if (game.getLettersLeft () <= 1)
	{
	    JOptionPane.showMessageDialog (null, "Only one letter left! You'll have to guess this one yourself. :)");
	    return;
	}

	//Check if the player has enough points to use hint
	if (!hasEnoughPoints (1))
	    return;

	//Find a letter that hasn't been found yet
	char hintLetter = ' ';
	String currentProgress = game.getRevealedWord ();
	String secret = game.getSecretWord ();

	for (int i = 0 ; i < currentProgress.length () ; i++)
	{
	    //Find the blank
	    if (currentProgress.charAt (i) == '_')
	    {
		hintLetter = secret.charAt (i);
		break;
	    }
	}

	//Reveal the letter in the blank
	if (hintLetter != ' ')
	{
	    for (int i = 0 ; i < secret.length () ; i++)
	    {
		//Replace the blank with the letter
		if (secret.charAt (i) == hintLetter)
		{
		    letterImages [i].setIcon (createImageIcon ("answer" + hintLetter + ".png"));
		    // Update progress in the object
		    String updatedProgress = game.getRevealedWord ().substring (0, i) + hintLetter + game.getRevealedWord ().substring (i + 1);
		    game.setRevealedWord (updatedProgress);
		    game.setLettersLeft (game.getLettersLeft () - 1); // Update letters left
		}
	    }

	}

	//Disable the button of that letter
	for (int j = 0 ; j < pics.length ; j++)
	{
	    if (pics [j].getActionCommand ().equals (hintLetter + ""))
		pics [j].setEnabled (false);
	}

	// Subtract the points and refresh the screen
	game.addPoints (-1);
	gameScreen ();
	cdLayout.show (p_card, "4");

	JOptionPane.showMessageDialog (null, "Your spider-senses tingled! You hint letter is: " + hintLetter);
    }


    //Removes 3 Wrong Letters
    public void hulkSmash ()
    {
	//Check if the player has enough points to use the ability
	if (!hasEnoughPoints (2))
	    return;
	game.addPoints (-2);
	int smashed = 0;
	String secret = game.getSecretWord ();

	for (int i = 0 ; i < pics.length && smashed < 3 ; i++)
	{
	    String letters = pics [i].getActionCommand ();
	    // If the button is enabled and the letter is not in the secret word
	    if (pics [i].isEnabled () && !letters.equals ("0") && secret.indexOf (letters) == -1)
	    {
		pics [i].setEnabled (false);
		smashed++;
	    }
	}
	JOptionPane.showMessageDialog (null, "HULK SMASHED 3 wrong letters!!");
    }


    //----- Check For Win In Challenge Mode -----
    public void challengeWinCheck ()
    {
	// If the player hits the target
	if (game.getPoints () >= target)
	{
	    gameTimer.stop ();
	    afterGameOver ();
	    JOptionPane.showMessageDialog (null, "CHALLENGE COMPLETE! You reached " + target + " points!");
	    cdLayout.show (p_card, "8");
	}
	// If time runs out
	else if (timeLeft <= 0)
	{
	    gameTimer.stop ();
	    afterGameOver ();
	    JOptionPane.showMessageDialog (null, "TIME'S UP! You failed the challenge.\nDon't Give Up & Try Again!!!");
	    //Reset points and send back to settings
	    game.setPoints (0);
	    isChallenge = false;
	    cdLayout.show (p_card, "9");
	}
    }


    //----- Start The Timer For Challenge Mode -----
    public void startTimer ()
    {
	if (gameTimer != null)
	    gameTimer.stop ();

	//Take the selected timer value and start the timer
	gameTimer = new Timer (1000, new ActionListener ()
	{
	    public void actionPerformed (ActionEvent e)
	    {
		timeLeft--;
		// Update the timer on the gameScreen
		if (timerLabel != null)
		    timerLabel.setText (timeLeft + "");
		if (timeLeft <= 0)
		{
		    gameTimer.stop ();
		    challengeWinCheck (); //Check if the player won
		}
		else
		{
		    // Check if they won before time runs out
		    if (game.getPoints () >= target)
			challengeWinCheck ();
		}
	    }
	}
	);
	gameTimer.start ();
    }


    //----- Common Functions To Do After A Guess Is Wrong Or Game Is Over -----
    public void afterGameOver ()
    {
	//Reveal the answer
	for (int i = 0 ; i < game.getSecretWord ().length () ; i++)
	{
	    char l = game.getSecretWord ().charAt (i);
	    letterImages [i].setIcon (createImageIcon ("answer" + l + ".png"));
	}

	// Disable all letter buttons in the grid
	for (int i = 0 ; i < pics.length ; i++)
	{
	    if (pics [i] != null)
		pics [i].setEnabled (false);
	}

	//Disable Game Screen Extra-Features Buttons
	guess.setEnabled (false);
	more.setEnabled (false);
    }


    //----- Check Cost -----
    public boolean hasEnoughPoints (int cost)
    {
	//Check if the current amount of points are enough to buy the ability
	if (game.getPoints () < cost)
	{
	    JOptionPane.showMessageDialog (null, "You need " + cost + " points for this ability!");
	    return false;
	}
	game.addPoints (-cost);
	return true;
    }


    //----- Action Performed -----
    public void actionPerformed (ActionEvent e)
    { //Screen Navigation
	if (e.getActionCommand ().equals ("s1"))
	    cdLayout.show (p_card, "1");
	else if (e.getActionCommand ().equals ("s2"))
	    cdLayout.show (p_card, "2");
	else if (e.getActionCommand ().equals ("s3"))
	    cdLayout.show (p_card, "3");
	else if (e.getActionCommand ().equals ("s4"))
	    cdLayout.show (p_card, "4");
	else if (e.getActionCommand ().equals ("inst"))
	    cdLayout.show (p_card, "5");
	else if (e.getActionCommand ().equals ("inst2"))
	    cdLayout.show (p_card, "6");
	else if (e.getActionCommand ().equals ("inst3"))
	    cdLayout.show (p_card, "7");

	//Button Actions
	else if (e.getActionCommand ().equals ("again"))
	{
	    //Check if the word has been found
	    if (game.getLettersLeft () > 0 && game.getMistakes () < 6)
	    {
		JOptionPane.showMessageDialog (null, "You haven't finished this round yet! Solve the word to move on.");
		return; // This stops the code here, so the game DOES NOT reset
	    }

	    //Reset game
	    game.setMistakes (0);
	    game.setRound (game.getRound () + 1); //Next Round
	    turnpic.setIcon (createImageIcon ("hangman0.png"));
	    chooseWord (); //Choose New Word
	    gameScreen (); //Re-run the game screen to make the changes
	    cdLayout.show (p_card, "4"); //Display Game Screen
	}
	else if (e.getActionCommand ().equals ("guess"))
	{
	    //Pop up to ask the user to enter thier guess
	    String playerGuess = JOptionPane.showInputDialog ("Enter your guess for the Marvel character:");

	    //Check if the guess is correct
	    if (playerGuess != null)
	    {
		playerGuess = playerGuess.toUpperCase ();
		//If the player is correct, show the letters on the screen
		if (playerGuess.equals (game.getSecretWord ()))
		{
		    game.addPoints (2);
		    game.setLettersLeft (0); //The word is found
		    afterGameOver ();
		    JOptionPane.showMessageDialog (null, "Great Guess! +2 Points!");
		    gameScreen ();
		    cdLayout.show (p_card, "4");
		}
		//If incorrect, penalize
		else
		{
		    game.addMistakes ();
		    game.addMistakes ();
		    //Check if more than 6 mistakes have been made
		    if (game.getMistakes () >= 6)
		    {
			JOptionPane.showMessageDialog (null, "Incorrect!");
			afterGameOver ();
		    }
		    else
			turnpic.setIcon (createImageIcon ("hangman" + game.getMistakes () + ".png"));
		    gameScreen (); // Refresh to update
		}
	    }
	}
	else if (e.getActionCommand ().equals ("more"))
	{
	    //Allow the user to choose more options
	    String[] options = {"Save Progress", "Open Progress", "Spider-Sense (-1 Points)", "Hulk Smash (-2 Points)", "Quit To Settings", "End Game"};
	    String choice = (String) JOptionPane.showInputDialog (null, "Choose You Superpowers Wisely: \nSpider Sense: Reveals 1 Letter For 1 Point\nHulk Smash: Removes 3 Wrong Letters For 2 Points", "More Options",
		    JOptionPane.QUESTION_MESSAGE, null,
		    options, options [0]);

	    if (choice != null)
	    {
		if (choice.equals ("Save Progress"))
		    saveGame ();
		else if (choice.equals ("Open Progress"))
		    openGame ();
		else if (choice.equals ("Spider-Sense (-1 Points)"))
		    hint ();
		else if (choice.equals ("Hulk Smash (-2 Points)"))
		    hulkSmash ();
		else if (choice.equals ("Quit To Settings"))
		{
		    int confirm = JOptionPane.showConfirmDialog (null, "Are you sure you want to quit? Unsaved progress will be lost.", "Quit Game", JOptionPane.YES_NO_OPTION);
		    if (confirm == JOptionPane.YES_OPTION)
		    {
			// Stop the timer if challenge mode is played
			if (gameTimer != null)
			    gameTimer.stop ();

			// Reset game variables
			isChallenge = false;
			cdLayout.show (p_card, "3"); // Navigates back to Settings (Panel "3")
		    }
		}
		else if (choice.equals ("End Game"))
		{
		    endGameScreen ();
		    cdLayout.show (p_card, "end");
		}
	    }
	}
	else if (e.getActionCommand ().equals ("title"))
	{
	    //Ask which screen the user wants to travel to
	    String[] locations = {"Title Screen", "Instructions", "Settings"};
	    String decision = (String) JOptionPane.showInputDialog (null,
		    "Which Screen Would You Like To Navigate To ?", "Navigation", JOptionPane.INFORMATION_MESSAGE, null,
		    locations, locations [0]);

	    //Navigate to the screen
	    if (decision.equals ("Title Screen"))
		cdLayout.show (p_card, "1");
	    else if (decision.equals ("Instructions"))
		cdLayout.show (p_card, "2");
	    else if (decision.equals ("Settings"))
		cdLayout.show (p_card, "3");
	    else
		JOptionPane.showMessageDialog (null, "You didn't select anything", "Navigation", JOptionPane.ERROR_MESSAGE);
	}

	//Game Modes
	else if (e.getActionCommand ().equals ("endless"))
	{
	    isChallenge = false;
	    if (gameTimer != null)
		gameTimer.stop (); // Stop any leftover timers
	    game.setMistakes (0);
	    chooseWord ();
	    game.setPoints (0);
	    JOptionPane.showMessageDialog (null, "Endless Mode Activated!");
	    // Switch to game screen
	    gameScreen ();
	    cdLayout.show (p_card, "4");
	}
	else if (e.getActionCommand ().equals ("challenge"))
	{
	    isChallenge = true;
	    String level1 = "Easy [5 pts in 120s]";
	    String level2 = "Medium (10 pts / 90s)";
	    String level3 = "Hard (15 pts / 60s)";

	    String[] levels = {level1, level2, level3};
	    String result = (String) JOptionPane.showInputDialog (null, "Pick Difficulty", "Challenge",
		    JOptionPane.QUESTION_MESSAGE, null, levels, levels [0]);

	    if (result != null)
	    {
		if (result.equals (level1))
		{
		    target = 5;
		    timeLeft = 120;
		}
		else if (result.equals (level2))
		{
		    target = 10;
		    timeLeft = 90;
		}
		else
		{
		    target = 15;
		    timeLeft = 60;
		}
		startTimer (); //Start the Timer
		// Switch to game screen
		gameScreen ();
		cdLayout.show (p_card, "4");
	    }
	}

	//Check if the clicked letter is in the chosen word
	else
	{
	    String letter = e.getActionCommand ();
	    //Check if the clicked button is an actual letter and not the place holder 0.
	    if (!letter.equals ("0"))
	    {
		char guess = letter.charAt (0);
		boolean found = false;
		for (int i = 0 ; i < game.getSecretWord ().length () ; i++)
		{
		    //If the selected letter is in the word, add the letter image
		    if (game.getSecretWord ().charAt (i) == guess)
		    {
			letterImages [i].setIcon (createImageIcon ("answer" + guess + ".png"));

			//Update progress
			String currentProgress = game.getRevealedWord ();
			String newProgress = currentProgress.substring (0, i) + guess + currentProgress.substring (i + 1);
			game.setRevealedWord (newProgress);

			//Update win conditions
			found = true;
			game.setLettersLeft (game.getLettersLeft () - 1);
		    }
		}

		//If the letter is not found, then update the image of the hangman
		if (!found)
		{
		    game.addMistakes ();
		    turnpic.setIcon (createImageIcon ("hangman" + game.getMistakes () + ".png"));

		    //Check if more than 6 mistakes have been made
		    if (game.isGameOver ())
		    {
			JOptionPane.showMessageDialog (null, "Incorrect!!");
			gameScreen ();
			afterGameOver ();
			cdLayout.show (p_card, "4");
			// Check challengeWinCheck if the player played challenge mode
			if (isChallenge)
			    challengeWinCheck ();
		    }
		}

		((JButton) e.getSource ()).setEnabled (false); //Disable the button that has already been clicked

		//Check for win
		// If word is finished in less than 7 guesses, give 1 point
		if (game.isWordSolved () && game.getMistakes () < 7)
		{
		    game.addPoints (1);
		    JOptionPane.showMessageDialog (null, "Well Done! You Found The Word!! +1 Point");
		    gameScreen (); // Refresh screen to show new point
		    cdLayout.show (p_card, "4");
		}
	    }
	}
    }


	protected static ImageIcon createImageIcon (String path)
	{
		java.net.URL imgURL = hangman.class.getResource ("assets/" + path);
		if (imgURL != null)
			return new ImageIcon (imgURL);
		else
		{
			System.err.println ("Couldn't find file: assets/" + path);
			return null;
		}
	}
}
