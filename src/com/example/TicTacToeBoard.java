package com.example;

/**
 * Takes in and evaluates a string representing a tic tac toe board.
 */
public class TicTacToeBoard {
  private int sideLen;
  private String board;

  /**
   * Loads a string representing an nxn board into the TicTacToeBoard class.
   *
   * @param board The string representing the board
   * @param sideLen An integer representing the dimension of the board
   */
  public TicTacToeBoard(String board, int sideLen) {
    // Invalid boards are smaller than 3x3, empty, or not square
    if (sideLen < 3) {
      throw new IllegalArgumentException("Invalid board size");
    }
    if (board == null || board.length() != sideLen*sideLen) {
      throw new IllegalArgumentException("Invalid board length");
    }

    // Using user input to build board string of only X, O, or spaces
    StringBuilder gameBoardStrBuilder = new StringBuilder();
    for (int i=0; i<board.length(); i++) {
      if (board.charAt(i) == 'x' || board.charAt(i) == 'X') {
        gameBoardStrBuilder.append("X");
      } else if (board.charAt(i) == 'o' || board.charAt(i) == 'O') {
        gameBoardStrBuilder.append("O");
      } else {
        gameBoardStrBuilder.append(" ");
      }
    }
    this.board = gameBoardStrBuilder.toString();
    this.sideLen = sideLen;
  }

  public String getBoard() {
    return this.board;
  }

  /**
   * Checks for n-in-a-row win by player
   *
   * @param playerTile The character corresponding to the player ('X' or 'O')
   * @return A boolean that is true if the given player has a row win, and false otherwise
   */
  private boolean checkRowWinByPlayer(char playerTile) {
    int tileCount;

    /* Comparing the number of the player's tiles in each row with the side length to determine if
     * there is a winning placement by row */
    for (int rowIdx = 0; rowIdx < board.length(); rowIdx += sideLen) {
      tileCount = 0;
      for (int colIdx = rowIdx; colIdx < rowIdx+ sideLen; colIdx++) {
        if (board.charAt(colIdx) == playerTile) {
          tileCount++;
        }
      }
      if (tileCount == sideLen) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks for n-in-a-column win by player
   *
   * @param playerTile The character corresponding to the player ('X' or 'O')
   * @return A boolean that is true if the given player has a column win, and false otherwise
   */
  private boolean checkColWinByPlayer(char playerTile) {
    int tileCount;

    /* Comparing the number of the player's tiles in each column with the side length to determine if
     * there is a winning placement by column */
    for (int colIdx = 0; colIdx < sideLen; colIdx++) {
      tileCount = 0;
      for (int rowIdx = colIdx; rowIdx < board.length(); rowIdx += sideLen) {
        if (board.charAt(rowIdx) == playerTile) {
          tileCount++;
        }
      }
      if (tileCount == sideLen) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks for a win along the major diagonal win by player
   *
   * @param playerTile The character corresponding to the player ('X' or 'O')
   * @return A boolean that is true if the given player has a major diagonal win, and false otherwise
   */
  private boolean checkMajorDiagWinByPlayer(char playerTile) {
    int tileCount=0;

    /* Comparing the number of the player's tiles on the major diagonal with the side length to
     * determine if there is a winning placement by major diagonal. The indices of the major diagonal
     * go from 0, (n+1), 2(n+1), 3(n+1)... in an nxn board */
    for (int majorDiagIdx = 0; majorDiagIdx < board.length(); majorDiagIdx += sideLen + 1) {
      if (board.charAt(majorDiagIdx) == playerTile) {
        tileCount++;
      }
    }
    return tileCount == sideLen;
  }

  /**
   * Checks for a win along the minor diagonal win by player
   *
   * @param playerTile The character corresponding to the player ('X' or 'O')
   * @return A boolean that is true if the given player has a minor diagonal win, and false otherwise
   */
  private boolean checkMinorDiagWinByPlayer(char playerTile) {
    int tileCount=0;

    /* Comparing the number of the player's tiles on the minor diagonal with the side length to
     * determine if there is a winning placement by minor diagonal. The indices of the minor diagonal
     * go from (n-1), 2(n-1), 3(n-1)... in an nxn board */
    for (int minorDiagIdx = sideLen - 1; minorDiagIdx < board.length(); minorDiagIdx += sideLen - 1) {
      if (board.charAt(minorDiagIdx) == playerTile) {
        tileCount++;
      }
    }
    return tileCount == sideLen;
  }

  /**
   * Checks for a win by player
   *
   * @param playerTile The character corresponding to the player ('X' or 'O')
   * @return A boolean that is true if the given player has a win, and false otherwise
   */
  private boolean findWinByPlayer(char playerTile) {
    // Checking if given player has any win (row, column, major diagonal, minor diagonal)
    return checkRowWinByPlayer(playerTile) || checkColWinByPlayer(playerTile)
            || checkMajorDiagWinByPlayer(playerTile) || checkMinorDiagWinByPlayer(playerTile);
  }

  /**
   * Checks whether the board is at an unreachable state
   *
   * @param hasXWin A boolean that is true if there is a win for X on the board, and false otherwise
   * @param hasOWin A boolean that is true if there is a win for O on the board, and false otherwise
   * @return A boolean that is true if the board is unreachable, and false otherwise
   */
  private boolean checkUnreachableState(boolean hasXWin, boolean hasOWin) {
    int numXMoves = 0;
    int numOMoves = 0;

    for (int tileIndex = 0; tileIndex < board.length(); tileIndex++) {
      // Gathering counts of numbers of Xs and Os on the board
      if (board.charAt(tileIndex) == 'X') {
        numXMoves++;
      } else if (board.charAt(tileIndex) == 'O') {
        numOMoves++;
      }
    }

    boolean hasEqualNumMoves = (numOMoves == numXMoves);
    boolean hasOneMoreXMove = (numXMoves == numOMoves + 1);

    /* Potential unreachable states:
     * 1. If X has a win, but there are an equal number of Xs and Os on the board, O has played after X
     *    won, which is unreachable.
     * 2. If O wins, but X has played one more turn than O, X has played after O won, which is
     *    unreachable.
     * 3. Otherwise, if it is the case that X and O haven't played equal turns and X has not played one
     *    more turn than O, a player has played too many times, which is unreachable.
     */
    return (hasEqualNumMoves && hasXWin) || (hasOneMoreXMove && hasOWin)
            || (!hasEqualNumMoves && !hasOneMoreXMove);
  }

  /**
   * Checks the state of the board (unreachable, no winner, X wins, or O wins)
   *
   * @return an enum value corresponding to the board evaluation
   */
  public Evaluation evaluate() {
    boolean hasXWin = findWinByPlayer('X');
    boolean hasOWin = findWinByPlayer('O');

    if (checkUnreachableState(hasXWin, hasOWin)) {
      return Evaluation.UnreachableState;
    }
    if (hasOWin) {
      return Evaluation.Owins;
    }
    if (hasXWin) {
      return Evaluation.Xwins;
    }
    return Evaluation.NoWinner;
  }
}