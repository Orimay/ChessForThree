package chessforthree.pieces;

import chessforthree.ChessField;
import chessforthree.ChessPiece;
import chessforthree.ChessPieceClass;
import chessforthree.ChessPlayer;
import chessforthree.Vector3;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dmitrii
 */
public class Knight extends ChessPiece {
    public Knight(ChessPlayer player) {
        super(player, ChessPieceClass.KNIGHT);
    }
    
    @Override
    public List<ChessField> getFreeAvailableFields() {
        List<ChessField> movements = new ArrayList();
        movements.addAll(this.moveKnight(false, false));
        return movements;
    }
    
    @Override
    public List<ChessField> getOccupedAvailableFields() {
        List<ChessField> movements = new ArrayList();
        movements.addAll(this.moveKnight(true, false));
        return movements;
    }
    
    @Override
    public List<ChessField> getOccupedUnavailableFields() {
        List<ChessField> movements = new ArrayList();
        movements.addAll(this.moveKnight(true, true));
        return movements;
    }
    
    private List<ChessField> moveKnight(boolean occuped, boolean self) {
        List<ChessField> movements = new ArrayList();
        if (super._field == null)
            return movements;
        for (int steps = 1; steps < 3; steps++) {
            ChessField[] firstSteps = new ChessField[18];
            for (int i = 0; i < 18; i++) {
                ChessField curStep;
                Vector3 curStepCoords = super._field.getCoordinates().clone();
                boolean evenStep = true;
                if (super._field.isTriangular() && i > 11)
                    continue;
                int stepsLeft = steps;
                do {
                    stepsLeft--;
                    makeNextStraightStep(i, curStepCoords, evenStep);
                    curStep = super._field.getBoard().getField(curStepCoords);
                    if (curStep == null)
                        break;
                    if (stepsLeft < 2)
                        firstSteps[i] = curStep;
                    if (curStep.isTriangular())
                        evenStep = !evenStep;
                } while (stepsLeft > 0);
            }
            for (int fsi = 0; fsi < 18; fsi++) {
                if (firstSteps[fsi] == null)
                    continue;
                for (int i = 0; i < 18; i++) {
                    switch (fsi) {
                        case 0:
                        case 1:
                        case 6:
                        case 7:
                        case 12:
                        case 15:
                            if (i == 0 || i == 1 || i == 6 || i == 7 || i == 12 || i == 15)
                                continue;
                            break;
                        case 2:
                        case 3:
                        case 8:
                        case 9:
                        case 14:
                        case 17:
                            if (i == 2 || i == 3 || i == 8 || i == 9 || i == 14 || i == 17)
                                continue;
                            break;
                        case 4:
                        case 5:
                        case 10:
                        case 11:
                        case 13:
                        case 16:
                            if (i == 4 || i == 5 || i == 10 || i == 11 || i == 13 || i == 16)
                                continue;
                            break;
                    }
                    ChessField curStep;
                    Vector3 curStepCoords = firstSteps[fsi].getCoordinates().clone();
                    //Debug.println("FirstStep", curStepCoords.toString());
                    boolean evenStep = false;
                    if (firstSteps[fsi].isTriangular() && i > 11)
                        continue;
                    int stepsLeft = 3 - steps;
                    do {
                        stepsLeft--;
                        makeNextStraightStep(i, curStepCoords, evenStep);
                        curStep = super._field.getBoard().getField(curStepCoords);
                        if (curStep == null)
                            break;
                        if (
                            !movements.contains(curStep)
                            && stepsLeft == 0
                            && (
                                occuped && curStep.getPiece() != null
                                && (
                                    self && curStep.getPiece().getPlayer().equals(this._player)
                                    || !self && !curStep.getPiece().getPlayer().equals(this._player)
                                )
                                || !occuped && curStep.getPiece() == null
                            )
                        )
                            movements.add(curStep);
                        if (curStep.isTriangular())
                            evenStep = !evenStep;
                    } while (stepsLeft > 0);
                }
            }
        }
        return movements;
    }
}