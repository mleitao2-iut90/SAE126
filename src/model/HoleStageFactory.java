package model;

import boardifier.model.GameStageModel;
import boardifier.model.StageElementsFactory;
import boardifier.model.TextElement;

public class HoleStageFactory extends StageElementsFactory {
    private HoleStageModel stageModel;

    public HoleStageFactory(GameStageModel gameStageModel) {
        super(gameStageModel);
        stageModel = (HoleStageModel) gameStageModel;
    }

    @Override
    public void setup() {
        // create the board
        stageModel.setBoard(new HoleBoard(7, 0, stageModel));
        //create the pots
        HolePawnPot blackPot = new HolePawnPot(0,0, stageModel, 12);
        stageModel.setBlackPot(blackPot);
        HolePawnPot redPot = new HolePawnPot(29,0, stageModel, 12);
        stageModel.setRedPot(redPot);

        HolePawnPot pawnsBoardPot = new HolePawnPot(40,0, stageModel, 48);
        stageModel.setBoardPotPawn(pawnsBoardPot);

        // create the pawns
        Pawn[] blackPawns = new Pawn[12];
        for(int i=0;i<12;i++) {
            blackPawns[i] = new Pawn(0, Pawn.PAWN_WHITE, stageModel);
        }
        stageModel.setBlackPawns(blackPawns);
        Pawn[] redPawns = new Pawn[12];
        for(int i=0;i<12;i++) {
            redPawns[i] = new Pawn(0, Pawn.PAWN_RED, stageModel);
        }
        stageModel.setRedPawns(redPawns);

        Pawn[] pawnsBoard = new Pawn[48];
        for(int i = 0; i<48;i++) {
            pawnsBoard[i] = new Pawn(0, Pawn.PAWN_BLACK, stageModel);
        }
        stageModel.setPawnsBoard(pawnsBoard);


        // assign pawns to their pot : they will be put at the center
        for (int i=0;i<12;i++) {
            blackPot.putElement(blackPawns[i], i,0);
            redPot.putElement(redPawns[i], i,0);
        }



        for (int i=0;i<48;i++) {
            pawnsBoardPot.putElement(pawnsBoard[i], i,0);
        }
    }
}
