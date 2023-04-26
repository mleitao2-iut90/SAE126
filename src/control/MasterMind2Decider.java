package control;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.MasterMindBoard;
import model.MasterMindPawnPot;
import model.MasterMindStageModel;

import java.util.Calendar;
import java.util.Random;
public class MasterMind2Decider extends Decider {
    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());
    private String line;
    public MasterMind2Decider(Model model, Controller control, String line) {
        super(model, control);
        this.line = line;
    }
    @Override
    public ActionList decide(){
        ActionList actions = new ActionList(false);
        MasterMindStageModel gameStage = (MasterMindStageModel) model.getGameStage();
        MasterMindBoard board = gameStage.getBoard(); // get the board
        MasterMindPawnPot pawnBoard = gameStage.getBoardPotPawn(); // get the pawn board
        if (line.equals("STOP") || line.equals("stop") || line.equals("Stop")) {
            control.stopStage();
        }else if (!board.isEmptyAt(1, 0)) {
            control.stopStage();
        }
        gameStage.moveLineUp(board, actions);
        gameStage.upPawnRedWhite();
        return actions;
    }
}
