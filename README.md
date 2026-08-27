# Marvel Hangman - Grade 12 CS Capstone Project

A desktop Hangman game built in Java based on the Marvel Universe[cite: 2, 5]. This project was completed as a final capstone assignment for Grade 12 Computer Science, covering all steps of the software development lifecycle and earning a 100% final grade.

---

## Project Overview

The game builds on traditional Hangman by adding an interactive graphical user interface (GUI), sound effects, scoring mechanics, special power-up abilities, and multiple game modes.

The game reads secret character names randomly from an external text file (`marvel.txt`)[cite: 2, 5]. Players guess letters using an on-screen alphabet grid to reveal hidden letter blanks before making 6 incorrect guesses[cite: 1, 2].

---

## Key Features

**Game Modes**
* **Endless Mode:** Play continuous rounds without time limits to build up a high score.
* **Challenge Mode:** A timed game mode with three difficulty tiers where players must reach a target score before the countdown reaches zero:
    * Easy: 5 points target in 120 seconds
    * Medium: 10 points target in 90 seconds
    * Hard: 15 points target in 60 seconds

**Superpower Abilities (Power-Ups)**
Players earn points by correctly solving words (+1 point) or successfully guessing the full word (+2 points). Earned points can be spent on special abilities during gameplay:
* **Spider-Sense (Costs 1 Point):** Reveals one un-guessed correct letter in the secret word.
* **Hulk Smash (Costs 2 Points):** Removes three incorrect letter options from the keyboard grid.

**Save and Load System**
* **Save Progress:** Writes the active secret word, current progress string, remaining unrevealed letters, mistake count, total points, and current round to `saveData.txt`[cite: 2, 6].
* **Open Progress:** Reads `saveData.txt` to restore a saved game session, rebuilding the word display and disabling previously guessed letters on the grid.

**UI and Media**
* **CardLayout Navigation:** Seamless navigation across the main menu, multi-page instructions, difficulty settings, main game screen, win screen, loss screen, and final game summary screen.
* **Dynamic Hangman Visuals:** Updates the stage graphic each time an incorrect guess is made.
* **Audio Integration:** Plays background theme music (`avengers_themeAura.wav`) during gameplay using Java Audio.

---

## Project Structure

* **`hangman.java`**: The main application controller and GUI interface. Handles window layout, button click events (`ActionListener`), timer loops, image rendering, audio playback, and file reader logic.
* **`gameState.java`**: Object-oriented data model tracking player stats and round details (score, mistakes, round counter, revealed word progress, and victory conditions).
* **`marvel.txt`**: Plain text dictionary file containing Marvel superhero and villain names.
* **`saveData.txt`**: Text file used to store and reload active game state values.
* **`assets/`**: Directory containing user interface images, letter buttons, hangman stage graphics, and audio files.

---

## Technical Concepts Applied

* **Object-Oriented Programming (OOP):** Encapsulation using getter/setter methods and modular class separation.
* **Event-Driven Programming:** Action listeners handling user interactions on button grids and option menus.
* **File I/O Stream Handling:** Custom file parsing with `BufferedReader`, `FileReader`, `PrintWriter`, and `FileWriter`.
* **Multi-Threading & Timing:** Swing `Timer` execution for real-time countdown tracking in Challenge Mode.
* **GUI Layout Management:** Java AWT/Swing container structuring using `CardLayout`, `GridLayout`, `BorderLayout`, and custom `Panel` compositions.