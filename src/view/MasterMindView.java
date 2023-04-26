package view;

import boardifier.model.GameStageModel;
import boardifier.view.GameStageView;
import boardifier.view.GridLook;
import model.MasterMindPawnPot;
import model.MasterMindStageModel;
import model.Pawn;

public class MasterMindView extends GameStageView {
    public MasterMindView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
    }

    @Override
    public void createLooks() {
        MasterMindStageModel model = (MasterMindStageModel) gameStageModel;

        addLook(new GridLook(5, 2, model.getBoard(), -1, false));
        addLook(new PawnPotLook(5, 2, model.getWhitePot()));
        addLook(new PawnPotLook(5, 2, model.getRedPot()));
        addLook(new PawnPotLook(5, 2, model.getBoardPotPawn()));
        MasterMindPawnPot boardPot = model.getBoardPotPawn();
        boardPot.setVisible(false);

        for (int i = 0; i < 12; i++) {
            addLook(new PawnLook(model.getWhitePawns()[i]));
            addLook(new PawnLook(model.getRedPawns()[i]));
        }
        for (int i = 0; i < 48; i++) {
            addLook(new PawnLook(model.getPawnsBoard()[i]));
        }
        Pawn[] pawns = model.getPawnsBoard();
        for (int i = 0; i < 48; i++) {
            pawns[i].setVisible(false);
        }
    }
}
