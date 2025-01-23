# Final Year Project
A Concurreny Based Game Environment

Demonstration and Explanation Video: https://www.youtube.com/watch?v=osYcO-F14JI

## Getting Started
To run the application:
```
cd PROJECT\Code\cs3821fyp
mvn clean install
mvn clean javafx:run
```

## Using The Application
- This will load up the main menu screen in which you can play multiple game instances from. 
- This includes Chess with either 2 human player, 1 human player or 2 computers, and Tic-Tac-Toe (Noughts and Crosses) with 2 human players.
- With Chess you can selected how many computers play with the provided drop down choice box.
- Clicking the 'New Instance' buttons will load up new instances of each game.
- You can load as many of each as you want and have multiple chess games running with varied amounts of computers.
- If creating a chess game with 2 bots, click any tile on the board to start the game.
- Be aware the bots run the game quite fast, this is left as fast in order to simulate games quickly and allow for many instances to run without slowing them down.
- If running bots, do not click the board when it is the bot's turn as it inteferes with their logic.
