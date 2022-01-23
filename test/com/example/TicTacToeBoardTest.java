package com.example;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TicTacToeBoardTest {
  @Test(expected = IllegalArgumentException.class)
  public void testNullBoardThrowsIllegalArgumentException() {
    TicTacToeBoard gameBoard = new TicTacToeBoard("", 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRectangleBoardThrowsIllegalArgumentException() {
    // 3x4 board
    TicTacToeBoard gameBoard = new TicTacToeBoard("............", 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSmallerThan3x3BoardThrowsIllegalArgumentException() {
    // 2x2 board
    TicTacToeBoard gameBoard = new TicTacToeBoard("....", 2);
  }

  @Test
  public void testNonPlayerTilesAreEmptySpaces() {
    TicTacToeBoard gameBoard = new TicTacToeBoard("aXXXXXXXX", 3);
    assertEquals(" XXXXXXXX", gameBoard.getBoard());
  }

  @Test
  public void testPlayerTilesAreNotCaseSensitive() {
    TicTacToeBoard gameBoard = new TicTacToeBoard("XxOo.....", 3);
    assertEquals("XXOO     ", gameBoard.getBoard());
  }

  @Test
  public void testNoMovesNoWinner() {
    // | |
    // | |
    // | |
    TicTacToeBoard gameBoard = new TicTacToeBoard(".........", 3);
    assertEquals(Evaluation.NoWinner, gameBoard.evaluate());
  }

  @Test
  public void testSomeMovesNoWinner() {
    // O| |
    //  |X|
    // X| |
    TicTacToeBoard gameBoard = new TicTacToeBoard("O...X.X..", 3);
    assertEquals(Evaluation.NoWinner, gameBoard.evaluate());
  }

  @Test
  public void testXWinsOnRow() {
    //  | |
    // X|X|X
    // O|O|
    TicTacToeBoard gameBoard = new TicTacToeBoard("...XXXOO.", 3);
    assertEquals(Evaluation.Xwins, gameBoard.evaluate());
  }

  @Test
  public void testXWinsOnColumn() {
    //  | |X
    //  |O|X
    //  |O|X
    TicTacToeBoard gameBoard = new TicTacToeBoard("..X.OX.OX", 3);
    assertEquals(Evaluation.Xwins, gameBoard.evaluate());
  }

  @Test
  public void testXWinsOnMajorDiagonal() {
    // X| |
    //  |X|O
    //  |O|X
    TicTacToeBoard gameBoard = new TicTacToeBoard("X...XO.OX", 3);
    assertEquals(Evaluation.Xwins, gameBoard.evaluate());
  }

  @Test
  public void testXWinsOnMinorDiagonal() {
    // O| |X
    //  |X|O
    // X| |
    TicTacToeBoard gameBoard = new TicTacToeBoard("O.X.XOX..", 3);
    assertEquals(Evaluation.Xwins, gameBoard.evaluate());
  }

  @Test
  public void testOWinsOnRow() {
    // X| |
    // X|X|
    // O|O|O
    TicTacToeBoard gameBoard = new TicTacToeBoard("X..XX.OOO", 3);
    assertEquals(Evaluation.Owins, gameBoard.evaluate());
  }

  @Test
  public void testOWinsOnColumn() {
    // X|O|
    //  |O|X
    //  |O|X
    TicTacToeBoard gameBoard = new TicTacToeBoard("XO..OX.OX", 3);
    assertEquals(Evaluation.Owins, gameBoard.evaluate());
  }

  @Test
  public void testOWinsOnMajorDiagonal() {
    // O|X|
    //  |O|X
    //  |X|O
    TicTacToeBoard gameBoard = new TicTacToeBoard("OX..OX.XO", 3);
    assertEquals(Evaluation.Owins, gameBoard.evaluate());
  }

  @Test
  public void testOWinsOnMinorDiagonal() {
    // X| |O
    //  |O|X
    // O|X|
    TicTacToeBoard gameBoard = new TicTacToeBoard("X.O.OXOX.", 3);
    assertEquals(Evaluation.Owins, gameBoard.evaluate());
  }

  @Test
  public void testOPlaysAfterXWinsIsUnreachableState() {
    // X|X|X
    //  |O|O
    // O| |
    TicTacToeBoard gameBoard = new TicTacToeBoard("XXX.OOO..", 3);
    assertEquals(Evaluation.UnreachableState, gameBoard.evaluate());
  }

  @Test
  public void testXPlaysAfterOWinsIsUnreachableState() {
    // X|X|O
    //  |O|X
    // O| |X
    TicTacToeBoard gameBoard = new TicTacToeBoard("XXO.OXO.X", 3);
    assertEquals(Evaluation.UnreachableState, gameBoard.evaluate());
  }

  @Test
  public void testXPlaysTooManyTurnsIsUnreachableState() {
    // X|X|O
    // X| |
    //  | |
    TicTacToeBoard gameBoard = new TicTacToeBoard("XXOX.....", 3);
    assertEquals(Evaluation.UnreachableState, gameBoard.evaluate());
  }

  @Test
  public void testOPlaysTooManyTurnsIsUnreachableState() {
    // O|X|O
    //  | |
    //  | |
    TicTacToeBoard gameBoard = new TicTacToeBoard("OXO......", 3);
    assertEquals(Evaluation.UnreachableState, gameBoard.evaluate());
  }

  @Test
  public void testLargerBoardWinOnRow() {
    // X| | |
    // O|O|O|O
    // X| | |
    // X|X| |
    TicTacToeBoard gameBoard = new TicTacToeBoard("X...OOOOX...XX..", 4);
    assertEquals(Evaluation.Owins, gameBoard.evaluate());
  }

  @Test
  public void testLargerBoardWinOnColumn() {
    // O| | |X
    //  |O|O|X
    //  | | |X
    //  | | |X
    TicTacToeBoard gameBoard = new TicTacToeBoard("O..X.OOX...X...X", 4);
    assertEquals(Evaluation.Xwins, gameBoard.evaluate());
  }

  @Test
  public void testLargerBoardWinOnMajorDiagonal() {
    // X| |O|O
    //  |X|O|
    //  | |X|
    //  | | |X
    TicTacToeBoard gameBoard = new TicTacToeBoard("X.OO.XO...X....X", 4);
    assertEquals(Evaluation.Xwins, gameBoard.evaluate());
  }

  @Test
  public void testLargerBoardWinOnMinorDiagonal() {
    // X|X|X|O
    // X| |O|
    //  |O| |
    // O| | |
    TicTacToeBoard gameBoard = new TicTacToeBoard("XXXOX.O..O..O...", 4);
    assertEquals(Evaluation.Owins, gameBoard.evaluate());
  }
}
