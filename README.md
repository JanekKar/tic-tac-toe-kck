# Tic Tac Toe
CLI and GUI version of popular game TicTacToe. Players plays against computer.

## Logic
Computer uses min-max algorithm to choose best moves. On different levels 
difficulty computer uses randomly min-max results or radom move - to insure that 
player has same chances to win.

## app.cli
Using java library Lanterna - on branch cli

## app.gui
Using swing - on branch gui.

## How to run
### Run compiled version
Download .jar file from [GitHub](https://github.com/JanekKar/tic-tac-toe-kck/releases)

To run CLI version `java -cp target/TicTacToe-1.0.jar app.Main`

To run GUI version `java -cp target/TicTacToe-1.0.jar app.Main --gui`
### Compile it yourself
Download [source code](https://github.com/JanekKar/tic-tac-toe-kck/releases) or clone this repository. 

To compile run: `mvn package`

To run CLI version `java -cp target/TicTacToe-1.0.jar app.Main`

To run GUI version `java -cp target/TicTacToe-1.0.jar app.Main --gui`



