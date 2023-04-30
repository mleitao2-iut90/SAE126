package control;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;
import boardifier.model.action.MoveAction;
import model.MasterMindBoard;
import model.MasterMindPawnPot;
import model.MasterMindStageModel;
import model.Pawn;

import java.util.Calendar;
import java.util.Random;
public class MasterMind2Decider extends Decider {
    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());
    private String line;
    private String[][] detail;
    private int etape;
    private int couleurCourante;
    private boolean changeCouleurCourante;
    private char[] combFinal;
    private String pawnInCombFinal;
    private String lastComb;
    public MasterMind2Decider(Model model, Controller control,String[][] d, int e, int couleurCourante, char[] pawnGoodPlace, String pawnInCombFinal) {
        super(model, control);
        this.detail = d;
        this.etape = e;
        this.couleurCourante = couleurCourante;
        this.line = "";
        this.changeCouleurCourante = false;
        this.combFinal = pawnGoodPlace;
        this.pawnInCombFinal = pawnInCombFinal;
        this.lastComb = "";
    }
    @Override
    public ActionList decide(){
        MasterMindStageModel gameStage = (MasterMindStageModel) model.getGameStage();
        MasterMindBoard board = gameStage.getBoard(); // get the board
        MasterMindPawnPot whitePot = gameStage.getWhitePot(); // get the black pot
        MasterMindPawnPot redPot = gameStage.getRedPot(); // get the red pot
        Pawn redPawn = (Pawn) redPot.getElement(11, 0);
        Pawn whitePawn = (Pawn) whitePot.getElement(11, 0);
        Pawn pawn1 = (Pawn) board.getElement(11,0);
        Pawn pawn2 = (Pawn) board.getElement(11,1);
        Pawn pawn3 = (Pawn) board.getElement(11,2);
        Pawn pawn4 = (Pawn) board.getElement(11,3);


        if(etape !=1){
            System.out.println("Couleur jsp pour quoi je l'affiche : "+pawn1.getColor());
        }

        if(etape == 1){
            this.line = "RRRR";
        }else if(redPawn.getNumber() == 0 && whitePawn.getNumber() == 0 && pawn1.getColor() == this.couleurCourante){
            for(int i = 0; i < 4; i++){
                this.line += detail[0][couleurCourante];
            }
            changeCouleurCourante = true;
        }else{
            if(pawnInCombFinal.equals("")){
                this.pawnInCombFinal += detail[0][couleurCourante-1];
                detail[2][couleurCourante-1] = String.valueOf(redPawn.getNumber());
            }
            if(pawn1.getColor() == pawn2.getColor() && pawn4.getColor() == pawn3.getColor() && pawn1.getColor() == pawn4.getColor()) {
                int p = 0;
                while(detail[2][p].equals("0")){
                    p++;
                }
                this.line+=detail[0][p];
                for(int i = 1; i<4; i++){
                    this.line += detail[0][couleurCourante];
                }
            }else{
                this.lastComb += this.detail[0][pawn1.getColor()-1];
                this.lastComb += this.detail[0][pawn2.getColor()-1];
                this.lastComb += this.detail[0][pawn3.getColor()-1];
                this.lastComb += this.detail[0][pawn4.getColor()-1];
                int pionsBlancs = whitePawn.getNumber();
                int pionsRouges = redPawn.getNumber();
                int pionsBlancRouge = pionsBlancs + pionsRouges;
                System.out.println("\nDerniere combinaison : "+this.lastComb);
                System.out.println("pions Blanc : "+pionsBlancs);
                System.out.println("pions Rouge : "+pionsRouges);
                System.out.println("pions Blanc et Rouge total : "+pionsBlancRouge);

                if(pionsBlancRouge == 1){
                    if(pionsBlancs ==1){
                        int tmp = posPawn();
                    }else{
                        int tmp = posPawn();
                        this.combFinal[tmp] = this.lastComb.charAt(tmp);
                        for(int i = 0; i<4; i++){
                            if(this.combFinal[i] != '0'){
                                this.line += this.combFinal[i];
                            }else{
                                this.line += detail[0][couleurCourante+1];
                            }
                        }
                        changeCouleurCourante = true;
                    }
                }

            }
        }
        System.out.println("Combinaison fibal : "+afficheTab1DChar(this.combFinal));
        System.out.println("lettre dans la combinaison :"+pawnInCombFinal);
        System.out.println("Line a tester : "+this.line);
        return super.action(this.line);
    }

    public String afficheTab1DChar(char[] tab){
        String tmp = "";
        for(int i = 0; i<tab.length; i++){
            tmp += tab[i];
        }
        return tmp;
    }

    public int posPawn() {
        int tmp = 20;
        for (int i = 0; i < this.lastComb.length(); i++) {
            for (int j = 0; j < this.pawnInCombFinal.length(); j++) {
                if (this.lastComb.charAt(i) == this.pawnInCombFinal.charAt(j)) {
                    tmp = i;
                    break;
                }
            }
            if (tmp != 20) {
                break;
            }
        }
        return tmp;
    }

    public void showTab2D(String[][] tab){
        for(int i = 0; i<tab.length; i++){
            for(int j = 0; j<tab[i].length; j++){
                System.out.print(tab[i][j]);
            }
            System.out.println();
        }
    }

    public String getComb(){
        //System.out.println(this.line);
        return this.line;
    }

    public String[][] getDetail(){
        return this.detail;
    }

    public boolean isChangeCouleurCourant(){
        return this.changeCouleurCourante;
    }

    public char[] getCombFinal(){
        return this.combFinal;
    }

    public String getPawnInCombFinal(){
        return this.pawnInCombFinal;
    }
}
