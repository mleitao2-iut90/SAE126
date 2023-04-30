package view;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.Pawn;

public class
PawnLook extends ElementLook {

    public PawnLook(GameElement element) {
        super(element, 1, 1);
        Pawn pawn = (Pawn)element;
        if (pawn.getColor() == Pawn.PAWN_BLACK) {
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.BLACK_BACKGROUND + pawn.getNumber() + ConsoleColor.RESET;
        }else if(pawn.getColor() == Pawn.PAWN_RED){
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.RED_BACKGROUND + pawn.getNumber() + ConsoleColor.RESET;
        }else if(pawn.getColor() == Pawn.PAWN_WHITE){
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + pawn.getNumber() + ConsoleColor.RESET;
        }else if(pawn.getColor() == Pawn.PAWN_BLUE){
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.BLUE_BACKGROUND + pawn.getNumber() + ConsoleColor.RESET;
        }else if(pawn.getColor() == Pawn.PAWN_YELLOW) {
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.YELLOW_BACKGROUND + pawn.getNumber() + ConsoleColor.RESET;
        }else if(pawn.getColor() == Pawn.PAWN_GREEN) {
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.GREEN_BACKGROUND + pawn.getNumber() + ConsoleColor.RESET;
        }else if(pawn.getColor() == Pawn.PAWN_CYAN) {
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.CYAN_BACKGROUND + pawn.getNumber() + ConsoleColor.RESET;
        }else if(pawn.getColor() == Pawn.PAWN_PURPLE) {
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.PURPLE_BACKGROUND + pawn.getNumber() + ConsoleColor.RESET;
        }
    }

    @Override
    public void onLookChange() {
        // do nothing since a pawn never change of aspect
        GameElement gameElement = (GameElement)element;
        Pawn pawn = (Pawn)gameElement;
        if (pawn.getColor() == Pawn.PAWN_BLACK) {
            shape[0][0] = ConsoleColor.WHITE + ConsoleColor.BLACK_BACKGROUND + pawn.getNumber() + ConsoleColor.RESET;
        }else if(pawn.getColor() == Pawn.PAWN_RED){
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.RED_BACKGROUND + pawn.getNumber() + ConsoleColor.RESET;
        }else if(pawn.getColor() == Pawn.PAWN_WHITE){
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + pawn.getNumber() + ConsoleColor.RESET;
        }else if(pawn.getColor() == Pawn.PAWN_BLUE){
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.BLUE_BACKGROUND + pawn.getNumber() + ConsoleColor.RESET;
        }else if(pawn.getColor() == Pawn.PAWN_YELLOW) {
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.YELLOW_BACKGROUND + pawn.getNumber() + ConsoleColor.RESET;
        }else if(pawn.getColor() == Pawn.PAWN_GREEN) {
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.GREEN_BACKGROUND + pawn.getNumber() + ConsoleColor.RESET;
        }else if(pawn.getColor() == Pawn.PAWN_CYAN) {
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.CYAN_BACKGROUND + pawn.getNumber() + ConsoleColor.RESET;
        }else if(pawn.getColor() == Pawn.PAWN_PURPLE) {
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.PURPLE_BACKGROUND + pawn.getNumber() + ConsoleColor.RESET;
        }
        pawn.setVisible(true);
    }
}
