package view;

import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.view.GameStageView;
import boardifier.view.GridLook;
import model.HolePawnPot;
import model.HoleStageModel;
import model.Pawn;

public class HoleStageView extends GameStageView {
    public HoleStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
    }

    @Override
    public void createLooks() {
        HoleStageModel model = (HoleStageModel)gameStageModel;

        addLook(new GridLook(5, 2, model.getBoard(), -1, false));
        addLook(new PawnPotLook(5,2, model.getBlackPot()));
        addLook(new PawnPotLook(5, 2, model.getRedPot()));
        addLook(new PawnPotLook(5,2,model.getBoardPotPawn()));
        HolePawnPot boardPot = model.getBoardPotPawn();
        boardPot.setVisible(false);

        for(int i=0;i<12;i++) {
            addLook(new PawnLook(model.getBlackPawns()[i]));
            addLook(new PawnLook(model.getRedPawns()[i]));
        }
        for(int i = 0; i < 48; i++){
            addLook(new PawnLook(model.getPawnsBoard()[i]));
        }
        Pawn[] pawns = model.getPawnsBoard();
        for(int i = 0; i < 48; i++){
            pawns[i].setVisible(false);
        }
    }
}
