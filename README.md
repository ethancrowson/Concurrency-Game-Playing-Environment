# Final Year Project
TicTacToe and Chess application were developed independently and so currently do not 
share a menu to choose between which to run. This means the main class run by 
'mvn clean javafx:run' needs to be updated depending on which you want to run.

Interim Video Link: https://www.youtube.com/watch?v=VhY4edE1UYU

inital setup (using terminal):
- cd into PROJECT\Code\cs3821fyp
- install by typing 'mvn clean install'

To run the Tic-tac-toe Application (The main application developed for interim).
- The main class in pom.xml for javfx-maven-plugin must be set to to 'com.ecrowson.Chess.Main'.
- The code is then run by typing 'mvn clean javafx:run' into the terminal.

To run the Chess Application (not too much development yet).
- The main class in pom.xml for javfx-maven-plugin must be set to to 'com.ecrowson.Chess.Main'.
- The code is then run by typing 'mvn clean javafx:run' into the terminal.