package com.example;

/**
 * Takes in and evaluates a string representing a tic tac toe board.
 */
public class TicTacToeBoard {
  private int sideLen;
  private String board;

  /**
   * This method should load a string into your TicTacToeBoard class.
   * @param board The string representing the board
   */
  public TicTacToeBoard(String board, int sideLen) {
    if (sideLen < 3) {
      throw new IllegalArgumentException("Invalid board size");
    }

    if (board == null || board.length() != sideLen*sideLen) {
      throw new IllegalArgumentException("Invalid board length");
    }

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

  private boolean checkRowWinByPlayer(char playerTile) {
    int tileCount;

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

  private boolean checkColWinByPlayer(char playerTile) {
    int tileCount;

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

  private boolean checkMajorDiagWinByPlayer(char playerTile) {
    int tileCount=0;

    for (int majorDiagIdx = 0; majorDiagIdx < board.length(); majorDiagIdx += sideLen + 1) {
      if (board.charAt(majorDiagIdx) == playerTile) {
        tileCount++;
      }
    }
    return tileCount == sideLen;
  }

  private boolean checkMinorDiagWinByPlayer(char playerTile) {
    int tileCount=0;

    for (int minorDiagIdx = sideLen - 1; minorDiagIdx < board.length(); minorDiagIdx += sideLen - 1) {
      if (board.charAt(minorDiagIdx) == playerTile) {
        tileCount++;
      }
    }
    return tileCount == sideLen;
  }

  private boolean findWinByPlayer(char playerTile) {
    return checkRowWinByPlayer(playerTile) || checkColWinByPlayer(playerTile)
            || checkMajorDiagWinByPlayer(playerTile) || checkMinorDiagWinByPlayer(playerTile);
  }

  private boolean checkUnreachableState(boolean hasXWin, boolean hasOWin) {
    int numXMoves = 0;
    int numOMoves = 0;

    for (int tileIndex = 0; tileIndex < board.length(); tileIndex++) {
      if (board.charAt(tileIndex) == 'X') {
        numXMoves++;
      } else if (board.charAt(tileIndex) == 'O') {
        numOMoves++;
      }
    }

    boolean hasEqualNumMoves = (numOMoves == numXMoves);
    boolean hasOneMoreXMove = (numXMoves == numOMoves + 1);

    // covers all unreachable states: O plays after X wins, X plays after O wins, or neither equal num moves
    // nor one more X move
    return (hasEqualNumMoves && hasXWin) || (hasOneMoreXMove && hasOWin)
            || (!hasEqualNumMoves && !hasOneMoreXMove);
  }

  /**
   * Checks the state of the board (unreachable, no winner, X wins, or O wins)
   * @return an enum value corresponding to the board evaluation
   */
  public Evaluation evaluate() {
    boolean hasXWin = findWinByPlayer('X');
    boolean hasOWin = findWinByPlayer('O');

    System.out.println(hasXWin);
    System.out.println(hasOWin);

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