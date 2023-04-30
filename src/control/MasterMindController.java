package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.model.GameElement;
import boardifier.model.GridElement;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.model.action.ActionList;
import boardifier.model.action.GameAction;
import boardifier.model.action.MoveAction;
import boardifier.view.View;
import model.MasterMindPawnPot;
import model.MasterMindStageModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

public class MasterMindController extends Controller {

    BufferedReader consoleIn;
    boolean firstPlayer;
    private String[][] detail = {
            {"R", "B", "J", "V", "W", "N", "C", "P"},
            {"1", "2", "3", "4", "5", "6", "7", "8"},
            {"0", "0", "0", "0", "0", "0", "0", "0"}};

    private char[][] detailChar = {
            {'R', 'B', 'J', 'V', 'W', 'N', 'C', 'P'},
            {'1', '2', '3', '4', '5', '6', '7', '8'},
            {'0', '0', '0', '0', '0', '0', '0', '0'}};
    private int couleurCourante = 1;
    private int etape = 1;
    private char[] combFinal = {'0', '0', '0', '0'};
    private String pawnInCombFinal = "";
    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());

    private int[][] combs;
    private int countCombs;
    private boolean generateCombs;

    public MasterMindController(Model model, View view) {
        super(model, view);
        firstPlayer = true;
        this.combs = new int[12][4];
        this.generateCombs = true;
        this.countCombs = 0;
    }

    /**
     * Defines what to do within the single stage of the single party
     * It is pretty straight forward to write :
     */
    public void stageLoop() {
        consoleIn = new BufferedReader(new InputStreamReader(System.in));
        update();
        while (!model.isEndStage()) {
            nextPlayer();
            update();
        }
        stopStage();
        endGame();
    }

    public void nextPlayer() {
        // for the first player, the id of the player is already set, so do not compute it
        /**if (!firstPlayer) {
         model.setNextPlayer();
         }
         else {
         firstPlayer = false;
         }**/
        // get the new player
        Player p = model.getCurrentPlayer();
        if (p.getType() == Player.COMPUTER) {
            if (p.getName().equals("computerDebile")) {
                analysePlayComputer();
            } else if (p.getName().equals("computerIntelligent1")) {
                analysePlayComputer2();
            } else if (p.getName().equals("computerIntelligent2")) {
                analysePlayComputer3();
            }

        } else {
            boolean ok = false;
            while (!ok) {
                System.out.print(p.getName() + " > ");
                try {
                    String line = consoleIn.readLine();
                    if (line.length() == 4) {
                        ok = analyseAndPlay2(line);
                    }
                    if (!ok) {
                        System.out.println("incorrect instruction. retry !");
                    }
                } catch (IOException e) {
                }
            }
        }
    }

    private void generateCombs() {

    }

    private boolean analyseAndPlay(String line) {
        MasterMindStageModel gameStage = (MasterMindStageModel) model.getGameStage();
        //get the pawn value from the first char
        int pawnIndex = (int) (line.charAt(0) - '1');
        if ((pawnIndex < 0) || (pawnIndex > 3)) return false;
        // get the ccords in the board
        int col = (int) (line.charAt(1) - 'A');
        int row = (int) (line.charAt(2) - '1');
        // check coords validity
        if ((row < 0) || (row > 2)) return false;
        if ((col < 0) || (col > 2)) return false;
        // check if the pawn is still in its pot
        GridElement pot = null;
        if (model.getIdPlayer() == 0) {
            pot = gameStage.getWhitePot();
        } else {
            pot = gameStage.getRedPot();
        }
        if (pot.isEmptyAt(pawnIndex, 0)) return false;
        GameElement pawn = pot.getElement(pawnIndex, 0);
        // compute valid cells for the chosen pawn
        gameStage.getBoard().setValidCells(pawnIndex + 1);
        if (!gameStage.getBoard().canReachCell(row, col)) return false;

        ActionList actions = new ActionList(true);
        GameAction move = new MoveAction(model, pawn, "mastermindboard", row, col);
        // add the action to the action list.
        actions.addSingleAction(move);
        ActionPlayer play = new ActionPlayer(model, this, actions);
        play.start();
        return true;
    }

    private boolean analyseAndPlay2(String line) {
        MasterMindStageModel gameStage = (MasterMindStageModel) model.getGameStage();
        //VERIF
        if (line.equals("STOP") || line.equals("stop") || line.equals("Stop")) {
            stopStage();
            return true;
        } else if (!goodValEnter(line, false)) {
            return false;
        }
        GridElement board = gameStage.getBoard();
        MasterMindPawnPot whitePot = gameStage.getWhitePot();
        MasterMindPawnPot redPot = gameStage.getRedPot();
        if (!board.isEmptyAt(1, 0)) {
            stopStage();
        }

        // ACTIONS
        ActionList actions = new ActionList(false);
        // Actions qui montee les pions du board de 1 ligne
        gameStage.moveLineUp(board, actions);
        // Actions mettre les "pawsBoard" sur le "board" (plateau)
        GridElement pawnBoard = gameStage.getBoardPotPawn();
        for (int i = 0; i < 4; i++) {
            GameElement pawn = pawnBoard.getElement(pawnAPoser, 0);
            gameStage.setColors(line, pawnAPoser);
            GameAction move = new MoveAction(model, pawn, "mastermindboard", 11, i);
            actions.addSingleAction(move);
            pawnAPoser++;
        }
        // Actions qui monte 1 cran les scores des pions blanc et rouge
        gameStage.upPawnRedWhite();

        ActionPlayer play = new ActionPlayer(model, this, actions);
        play.start();

        // Actions qui donne le score pour les pions rouge et blanc du bas
        gameStage.setNumberPawnDown(line, combinaison);

        if (gameStage.verifWin() == 4) {
            stopStage();
            model.setEnd(1);
        }
        return true;
    }

    public void analysePlayComputer() {
        MasterMindStageModel gameStage = (MasterMindStageModel) model.getGameStage();
        System.out.println("COMPUTER PLAYS");
        String line = setCombRand();
        MasterMindDecider decider = new MasterMindDecider(model, this, line);
        ActionPlayer play = new ActionPlayer(model, this, decider, null);
        play.start();
        gameStage.setNumberPawnDown(line, combinaison);
        if (gameStage.verifWin() == 4) {
            stopStage();
            model.setEnd(1);
        }
    }

    public void analysePlayComputer2() {
        MasterMindStageModel gameStage = (MasterMindStageModel) model.getGameStage();
        System.out.println("COMPUTER PLAYS");
        MasterMind2Decider decider = new MasterMind2Decider(model, this, this.detail, this.etape, this.couleurCourante, this.combFinal, this.pawnInCombFinal);
        ActionPlayer play = new ActionPlayer(model, this, decider, null);
        play.start();
        String line = decider.getComb();
        this.pawnInCombFinal = decider.getPawnInCombFinal();
        this.combFinal = decider.getCombFinal();
        this.detail = decider.getDetail();
        gameStage.setNumberPawnDown(line, combinaison);
        if (gameStage.verifWin() == 4) {
            stopStage();
            model.setEnd(1);
        }
        if (decider.isChangeCouleurCourant()) {
            this.couleurCourante++;
        }
        this.etape++;
        stopStage();
        model.setEnd(2);
    }

    public int nbBienPlace(int[] tab1, int[] tab2, int k) {
        int nb_bien_place = 0;
        for (int i = 0; i < k; i++) {
            if (tab1[i] == tab2[i]) {
                nb_bien_place++;
            }
        }
        return nb_bien_place;
    }

    public int[] combiStringToInt(String combi) {
        int[] combiInt = new int[4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                if (combi.charAt(i) == this.detailChar[0][j]) {
                    combiInt[i] = Character.getNumericValue(this.detailChar[1][j]);
                }
            }
        }
        return combiInt;
    }

    public int nbCommuns(int[] tab1, int[] tab2, int k) {
        // Cr�ation de tableaux avec une taille equivalent aux nombres de
        // couleurs
        int[] t1 = new int[k];
        int[] t2 = new int[k];
        for (int i = 0; i < k; i++) {
            t1[i] = tab1[i];
            t2[i] = tab2[i];
        }

        int cpt = 0;
        boolean trouve = false;

        for (int i = 0; i < k; i++) {
            int j = 0;
            trouve = false;
            do {
                if (t1[i] == t2[j]) {
                    cpt++;
                    t2[j] = 0;
                    trouve = true;
                }
                j++;
            } while (j < k && !trouve);
        }

        return cpt;
    }

    public void analysePlayComputer3() {
        if (this.generateCombs) {
            int k = 4; // Nombre de boules de couleurs dans la combinaison
            int n = 8; // Nombre de couleurs diff�rentes
            int[] combi = combiStringToInt(combinaison);
            int coul = 1; // Couleur test�, on commence avec la 1ier couleur : 1
            int[] combiTente = new int[k]; // Tableau contenant la combinaison
            // tent� par l'ia
            int[] combiTrouve = new int[k]; // Tableau contenant les couleurs
            // trouv�es,
            int nbBienPlace = 0;
            int nbMalPlace = 0;
            int nbTrouve = 0; // Nombre de boules de couleurs dont ont a trouv� la
            // position
            int nbTentative = 0;
            int pos = 0;

            int coulSuivante = 1; // Contient la couleur suivante servant � remplir
            // la combinaison lorsqu'on cherche la position de la couleur courante

            int nbBoulesSuivante = 0;

            // tant que le nb de bien plac� est inf�rieur aux nb de cases (ie: tant
            // que le MM n'est pas r�solu) faire *** utiliser la variable nbBienPlace ***
            while (nbBienPlace(combi, combiTente, k) < k && coul <= n) {

                coul = coulSuivante;
                // On cr�e la nouvelle combinaison � tent�, qui determine la
                // presence ou non d'une couleur. Tout en tenant compte des
                // positions des
                // boules de couleurs deja trouv�es
                for (int i = 0; i < k; i++) {
                    if (combiTrouve[i] == 0)
                        combiTente[i] = coul;
                    else
                        combiTente[i] = combiTrouve[i];
                }
                //showTab1D(combiTente);
                if(countCombs < 12){
                    for(int i = 0; i < combiTente.length; i++){
                        combs[countCombs][i] = combiTente[i];
                    }
                    countCombs++;
                }


                // On v�rifie que la combinaison a tent� n'est pas celle deja tent� dernierement)


                // On d�termine le nombre de bien plac�s et mal plac�s
                nbBienPlace = nbBienPlace(combi, combiTente, k);
                nbMalPlace = nbCommuns(combi, combiTente, k)
                        - nbBienPlace(combi, combiTente, k);

                // incr�mentation du nombre de combi tent�
                nbTentative++;

                // affichage de la combi tent� et des indications donn�es
                //System.out.print("cherche couleur : ");
                //afficheTab(combiTente);
                //System.out.println(" --> "+nbBienPlace+" biens plac�es, "+nbMalPlace+" mal plac�es.");
                /*
                 * Analyse des indications donn�es par le nbBienPlace et nbMalPlace
                 */

                // 1 - Si le nombre de bien plac� = k, pas la peine de continu� on a fini la r�solution
                if (nbBienPlace == k)
                    break;


                // D�termination du nombre de boules de la couleur courante
                int nbBoules = nbBienPlace - nbTrouve;


                // Si la couleur tester est pr�sente (ie. nbBoules >= 1)
                // faire
                if (nbBoules >= 1 && coul <= (n + 1)) {

                    do {

                        if (nbBoulesSuivante > 0) // si on a cour circuite la recherche de couleur
                        {
                            //System.out.println("Court circuit");
                            coul = coulSuivante;
                            nbBoules = nbBoulesSuivante;
                        }
                        nbBoulesSuivante = 0; // init du nb de boules de la couleur suivante

                        coulSuivante = coul + 1;

                        for (int x = 1; x <= nbBoules; x++) { // cherche la position de chaque couleurs
                            // On met nbMalPlace � un nombre diff�rent de 0
                            nbMalPlace = 1;

                            // indice de la position test� pour trouver l'emplacement de
                            // la couleur coul. On ne test pas une position dont on
                            // connais deja la
                            // couleur. Donc on cr�e une boucle qui cherche une
                            // position possible.
                            pos = 0;

                            // tant que nbMalPlace != 0 faire
                            while (nbMalPlace > 0) {

                                // recherche d'une position a tester qui n'a pas deja �t� trouv�
                                while ((pos < k) && combiTrouve[pos] != 0)
                                    pos++;

                                // On cr�e la nouvelle combinaison � tent�, qui cherche
                                // la position exacte de la boule de couleur
                                // en cour. Tout en tenant compte des positions des
                                // boules de couleurs deja trouv�es
                                for (int i = 0; i < k; i++) {

                                    if (combiTrouve[i] == 0) // Si la case courante,
                                    // n'est pas une case dont on connais la couleur
                                    // (ie. combiTrouve==0)
                                    {
                                        if (i != pos) // Si la case n'est pas la case
                                            // test�, on met la boule de
                                            // couleur sup�rieur
                                            combiTente[i] = coulSuivante;
                                        else
                                            combiTente[i] = coul;

                                    } else
                                        combiTente[i] = combiTrouve[i];
                                }
                                //showTab1D(combiTente);
                                if(countCombs < 12){
                                    for(int i = 0; i < combiTente.length; i++){
                                        combs[countCombs][i] = combiTente[i];
                                    }
                                    countCombs++;
                                }

                                // Test de la nouvelle tentative
                                // Calcul du nombre de boules mal plac� (en souhaitant
                                // qu'il y en ai 0)
                                nbBienPlace = nbBienPlace(combi, combiTente, k);
                                int nbCommuns = nbCommuns(combi, combiTente, k);
                                nbMalPlace = nbCommuns - nbBienPlace;

                                // Affichage de la tentative ***
                                //afficheTab(combiTente);
                                //System.out.println(" --> "+nbBienPlace+" biens plac�es, "+nbMalPlace+" mal plac�es.");
                                nbTentative++;

                                /*
                                 * Analyse des indications donn�es
                                 */
                                int nbCoulSuivante = (nbCommuns - nbTrouve - 1);
                                //System.out.println("coulSuivante : "+ coulSuivante+ " nbCoulSuivante : "+nbCoulSuivante);
                                if (nbCoulSuivante <= 0) {
                                    coulSuivante++;
                                    //	System.out.println(coulSuivante);
                                } else
                                    nbBoulesSuivante = nbCoulSuivante;

                                pos++;
                            }

                            // A la sortie de la boucle, on a la position de la boule de
                            // couleur -> pos - 1
                            // On ajoute donc cette boule � la combinaison contenant les
                            // boules Trouv�es
                            combiTrouve[pos - 1] = coul;
                            // on incr�mente le nombre de boule trouv�es
                            nbTrouve++;
                        }

                    }
                    while (nbBoulesSuivante > 0 && nbBienPlace != k); // Tant que la couleur suivante est prensente
                } else
                    coulSuivante++;

            }
            countCombs = 0;
        }
        String line = "";
        this.generateCombs = false;
        //showTab1D(combi);
        showTab2D(combs);

        int tmp = 0;
        for(int i = 0; i< 4 ; i++){
            for(int j = 0; j < 8; j++){
                if(combs[countCombs][i] == Integer.parseInt(detail[1][j])){
                    tmp = j;
                    break;
                }
            }
            line += detail[0][tmp];
        }
        countCombs++;
        System.out.println("Line : "+line);

        MasterMindStageModel gameStage = (MasterMindStageModel) model.getGameStage();
        System.out.println("COMPUTER PLAYS");
        MasterMindDecider decider = new MasterMindDecider(model, this, line);
        ActionPlayer play = new ActionPlayer(model, this, decider, null);
        play.start();
        gameStage.setNumberPawnDown(line, combinaison);
        if (gameStage.verifWin() == 4) {
            stopStage();
            model.setEnd(1);
        }
    }

    public void showTab2D(int[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            showTab1D(tab[i]);
        }
    }

    public void showTab1D(int[] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.print(tab[i]);
        }
        System.out.println();
    }

    public String setCombRand() {
        String line = "";
        int nb;
        for (int i = 0; i < 4; i++) {
            nb = loto.nextInt(8);
            if (nb == 0) {
                line += "N";
            } else if (nb == 1) {
                line += "R";
            } else if (nb == 2) {
                line += "B";
            } else if (nb == 3) {
                line += "J";
            } else if (nb == 4) {
                line += "V";
            } else if (nb == 5) {
                line += "W";
            } else if (nb == 6) {
                line += "C";
            } else {
                line += "P";
            }

        }
        //line = "PPPP";
        return line;
    }
}
