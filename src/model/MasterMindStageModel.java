package model;

import boardifier.model.*;
import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;
import boardifier.model.action.MoveAction;

public class MasterMindStageModel extends GameStageModel {

    private MasterMindBoard board;
    private MasterMindPawnPot whitePot;
    private MasterMindPawnPot redPot;
    private MasterMindPawnPot boardPawn;
    private Pawn[] whitePawns;
    private Pawn[] redPawns;
    private Pawn[] pawnsBoard;
    private int whitePawnsToPlay;
    private int redPawnsToPlay;

    public MasterMindStageModel(String name, Model model) {
        super(name, model);
        whitePawnsToPlay = 4;
        redPawnsToPlay = 4;
        setupCallbacks();
    }

    public MasterMindBoard getBoard() {
        return board;
    }

    public void setBoard(MasterMindBoard board) {
        this.board = board;
        addGrid(board);
    }

    public MasterMindPawnPot getWhitePot() {
        return whitePot;
    }

    public void setWhitePot(MasterMindPawnPot whitePot) {
        this.whitePot = whitePot;
        addGrid(whitePot);
    }

    public MasterMindPawnPot getRedPot() {
        return redPot;
    }

    public void setRedPot(MasterMindPawnPot redPot) {
        this.redPot = redPot;
        addGrid(redPot);
    }

    public Pawn[] getWhitePawns() {
        return whitePawns;
    }

    public void setWhitePawns(Pawn[] whitePawns) {
        this.whitePawns = whitePawns;
        for (int i = 0; i < whitePawns.length; i++) {
            addElement(whitePawns[i]);
        }
    }

    public Pawn[] getRedPawns() {
        return redPawns;
    }

    public void setRedPawns(Pawn[] redPawns) {
        this.redPawns = redPawns;
        for (int i = 0; i < redPawns.length; i++) {
            addElement(redPawns[i]);
        }
    }

    public Pawn[] getPawnsBoard() {
        return pawnsBoard;
    }

    public void setPawnsBoard(Pawn[] pawnsBoard) {
        this.pawnsBoard = pawnsBoard;
        for (int i = 0; i < pawnsBoard.length; i++) {
            addElement(pawnsBoard[i]);
        }
    }

    public MasterMindPawnPot getBoardPotPawn() {
        return boardPawn;
    }

    public void setBoardPotPawn(MasterMindPawnPot boardPawn) {
        this.boardPawn = boardPawn;
        addGrid(boardPawn);
    }

    private void setupCallbacks() {
        onPutInGrid((element, gridDest, rowDest, colDest) -> {
            // just check when pawns are put in 3x3 board
            if (gridDest != board) return;
            Pawn p = (Pawn) element;
            /**if (p.getColor() == 0) {
             whitePawnsToPlay--;
             }
             else {
             redPawnsToPlay--;
             }**/
            if ((whitePawnsToPlay == 0) && (redPawnsToPlay == 0)) {
                computePartyResult();
            }
        });
    }

    ;

    public void setColors(String line, int pos) {
        Pawn p = (Pawn) (boardPawn.getElement(pos, 0));
        char c = line.charAt(pos % 4);
        p.setColor(c);
        //System.out.println(line.charAt(i));
        //System.out.println(p.getColor());
        //System.out.println(p.isLookChanged());
    }

    ;

    public void upPawnRedWhite() {
        int tmp;
        for (int i = 0; i < 11; i++) {
            Pawn p1 = (Pawn) (whitePot.getElement(i + 1, 0));
            tmp = p1.getNumber();
            Pawn p2 = (Pawn) (whitePot.getElement(i, 0));
            p2.setNumber(tmp);
            Pawn p3 = (Pawn) (redPot.getElement(i + 1, 0));
            tmp = p3.getNumber();
            Pawn p4 = (Pawn) (redPot.getElement(i, 0));
            p4.setNumber(tmp);
        }
    }
    public void setNumberPawnDown(String line, String comb) {
        Pawn redPawn = (Pawn) (redPot.getElement(11, 0));
        Pawn whitePawn = (Pawn) (whitePot.getElement(11, 0));
        char[] combCharArray = comb.toCharArray();
        char[] lineCharArray = line.toCharArray();
        int tmpred = 0;
        int tmpWhite = 0;
        for (int i = 0; i < combCharArray.length; i++) {
            if (combCharArray[i] == lineCharArray[i]) {
                tmpred++;
                combCharArray[i] = ' ';
                lineCharArray[i] = ' ';
            }
        }
        for (int i = 0; i < combCharArray.length; i++) {
            if (lineCharArray[i] != ' ') {
                for (int j = 0; j < combCharArray.length; j++) {
                    if (lineCharArray[i] == combCharArray[j]) {
                        tmpWhite++;
                        combCharArray[j] = ' ';
                        break;
                    }
                }
            }
        }
        redPawn.setNumber(tmpred);
        whitePawn.setNumber(tmpWhite);
    }

    public int verifWin(){
        Pawn p = (Pawn) (redPot.getElement(11, 0));
        return p.getNumber();
    }

    public void moveLineUp(GridElement board, ActionList actions) {
        boolean find = false;
        for (int i = 0; i < 11; i++) {
            if ((board.isEmptyAt(i, 0) && !board.isEmptyAt(i + 1, 0)) || find) {
                find = true;
                for (int j = 0; j < 4; j++) {
                    GameElement pawn = board.getElement(i + 1, j);
                    GameAction move = new MoveAction(model, pawn, "mastermindboard", i, j);
                    actions.addSingleAction(move);
                }
            }
        }
    }

    private void computePartyResult() {
        int idWinner = -1;
        // get the empty cell, which should be in 2D at [0,0], [0,2], [1,1], [2,0] or [2,2]
        // i.e. or in 1D at index 0, 2, 4, 6 or 8
        int i = 0;
        int nbWhite = 0;
        int nbRed = 0;
        int countWhite = 0;
        int countRed = 0;
        Pawn p = null;
        int row, col;
        for (i = 0; i < 9; i += 2) {
            if (board.isEmptyAt(i / 3, i % 3)) break;
        }
        // get the 4 adjacent cells (if they exist) starting by the upper one
        row = (i / 3) - 1;
        col = i % 3;
        for (int j = 0; j < 4; j++) {
            // skip invalid cells
            if ((row >= 0) && (row <= 2) && (col >= 0) && (col <= 2)) {
                p = (Pawn) (board.getElement(row, col));
                if (p.getColor() == Pawn.PAWN_WHITE) {
                    nbWhite++;
                    countWhite += p.getNumber();
                } else {
                    nbRed++;
                    countRed += p.getNumber();
                }
            }
            // change row & col to set them at the correct value for the next iteration
            if ((j == 0) || (j == 2)) {
                row++;
                col--;
            } else if (j == 1) {
                col += 2;
            }
        }

        System.out.println("nb black: " + nbWhite + ", nb red: " + nbRed + ", count white: " + countWhite + ", count red: " + countRed);
        // decide whose winning
        if (nbWhite < nbRed) {
            idWinner = 0;
        } else if (nbWhite > nbRed) {
            idWinner = 1;
        } else {
            if (countWhite < countRed) {
                idWinner = 0;
            } else if (countWhite > countRed) {
                idWinner = 1;
            }
        }
        // set the winner
        //model.setIdWinner(idWinner);
        // stop de the game
        model.stopStage();
    }

    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new MasterMindStageFactory(this);
    }
}
