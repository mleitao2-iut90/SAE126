package boardifier.control;

import boardifier.model.action.ActionList;
import boardifier.model.Model;
import boardifier.model.action.GameAction;
import boardifier.model.action.MoveAction;
import model.MasterMindBoard;
import model.MasterMindPawnPot;
import model.MasterMindStageModel;
import model.Pawn;

public abstract class Decider {
    protected Model model;
    protected Controller control;
    public Decider(Model model, Controller control) {
        this.model = model;
        this.control = control;
    }

    public abstract ActionList decide();

    public ActionList action(String line){
        // do a cast get a variable of the real type to get access to the attributes of HoleStageModel
        MasterMindStageModel stage = (MasterMindStageModel) model.getGameStage();
        MasterMindBoard board = stage.getBoard(); // get the board
        MasterMindPawnPot whitePot = stage.getWhitePot(); // get the black pot
        MasterMindPawnPot redPot = stage.getRedPot(); // get the red pot
        MasterMindPawnPot pawnBoard = stage.getBoardPotPawn(); // get the pawn board
        MasterMindPawnPot pot = pawnBoard; // the pot where to take a pawn
        ActionList actions = new ActionList(false);
        if (line.equals("STOP") || line.equals("stop") || line.equals("Stop")) {
            control.stopStage();
        }else if (!board.isEmptyAt(1, 0)) {
            control.stopStage();
        }
        stage.moveLineUp(board, actions);
        stage.upPawnRedWhite();
        for (int i = 0; i < 4; i++) {
            int pawnAPoser = control.getPawnAPoser();
            Pawn p = (Pawn) pot.getElement(pawnAPoser, 0);
            if (p != null) {
                /**List<Point> valid = board.computeValidCells(p.getNumber());
                 if (valid.size() != 0) {
                 // choose at random one of the valid cells
                 int id = loto.nextInt(valid.size());
                 pawn = p;
                 rowDest = valid.get(id).y;
                 colDest = valid.get(id).x;
                 break; // stop the loop
                 }**/
                stage.setColors(line, pawnAPoser);
                GameAction move = new MoveAction(model, p, "mastermindboard", 11, i);
                actions.addSingleAction(move);
            }
            control.add1PawnAPoser();
        }
        return actions;
    }
}
