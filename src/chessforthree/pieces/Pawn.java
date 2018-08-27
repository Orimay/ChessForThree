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
public class Pawn extends ChessPiece {
    public Pawn(ChessPlayer player) {
        super(player, ChessPieceClass.PAWN);
    }
    
    public void evolve(ChessPieceClass target) {
        if (target.equals(ChessPieceClass.PAWN) || target.equals(ChessPieceClass.KING))
            return;
        this._pieceClass = target;
        super.initIcon();
    }
    
    @Override
    public List<ChessField> getFreeAvailableFields() {
        List<ChessField> movements = new ArrayList();
        if (super._field == null)
            return movements;
        boolean onStartLine = true;
        for (int i = 0; i < 18; i++) {
            switch (super._player.getPlayerClass()) {
                case ORANGE:
                    if (i != 1 && i != 4 && i != 7 && i != 10)
                        continue;
                    onStartLine = super._field.getCoordinates().getRealY() == -3;
                    break;
                case SAPPHIRINE:
                    if (i != 0 && i != 2 && i != 6 && i != 8)
                        continue;
                    onStartLine = super._field.getCoordinates().getRealX() == -3;
                    break;
                case PURPLE:
                    if (i != 3 && i != 5 && i != 9 && i != 11)
                        continue;
                    onStartLine = super._field.getCoordinates().getRealZ() == -3;
                    break;
            }
            ChessField curStep;
            Vector3 curStepCoords = super._field.getCoordinates().clone();
            boolean evenStep = true;
            if (super._field.isTriangular() && i > 11)
                continue;
            int stepsLeft = onStartLine ? 2 : 1;
            do {
                makeNextStraightStep(i, curStepCoords, evenStep, true);
                curStep = super._field.getBoard().getField(curStepCoords);
                if (curStep == null)
                    break;
                if (!movements.contains(curStep) && curStep.getPiece() == null)
                    movements.add(curStep);
                if (curStep.isTriangular())
                    evenStep = !evenStep;
                stepsLeft--;
            } while ((curStep.getPiece() == null) && stepsLeft > 0);
        }
        return movements;
    }
    
    @Override
    public List<ChessField> getOccupedAvailableFields() {
        List<ChessField> movements = new ArrayList();
        if (super._field == null)
            return movements;
        for (int i = 0; i < 18; i++) {
            switch (super._player.getPlayerClass()) {
                case ORANGE:
                    if (i != 1 && i != 5 && i != 7 && i != 11 && i != 12 && i != 15)
                        continue;
                    break;
                case SAPPHIRINE:
                    if (i != 0 && i != 6 && i != 13 && i != 14 && i != 16 && i != 17)
                        continue;
                    break;
                case PURPLE:
                    if (i != 2 && i != 3 && i != 4 && i != 8 && i != 9 && i != 10)
                        continue;
                    break;
            }
            ChessField curStep;
            Vector3 curStepCoords = super._field.getCoordinates().clone();
            boolean evenStep = true;
            int stepsLeft = 1;
            do {
                makeNextDiagonalStep(i, curStepCoords, evenStep, true);
                curStep = super._field.getBoard().getField(curStepCoords);
                if (curStep == null)
                    break;
                if (!movements.contains(curStep) && (curStep.getPiece() != null && !curStep.getPiece().getPlayer().equals(super._player)))
                    movements.add(curStep);
                if (curStep.isTriangular())
                    evenStep = !evenStep;
                else
                    evenStep = i > 5;
                stepsLeft--;
            } while (curStep.getPiece() == null && stepsLeft > 0);
        }
        return movements;
    }
    
    @Override
    public List<ChessField> getOccupedUnavailableFields() {
        List<ChessField> movements = new ArrayList();
        if (super._field == null)
            return movements;
        for (int i = 0; i < 18; i++) {
            switch (super._player.getPlayerClass()) {
                case ORANGE:
                    if (i != 1 && i != 5 && i != 7 && i != 11 && i != 12 && i != 15)
                        continue;
                    break;
                case SAPPHIRINE:
                    if (i != 0 && i != 6 && i != 13 && i != 14 && i != 16 && i != 17)
                        continue;
                    break;
                case PURPLE:
                    if (i != 2 && i != 3 && i != 4 && i != 8 && i != 9 && i != 10)
                        continue;
                    break;
            }
            ChessField curStep;
            Vector3 curStepCoords = super._field.getCoordinates().clone();
            boolean evenStep = true;
            int stepsLeft = 1;
            do {
                makeNextDiagonalStep(i, curStepCoords, evenStep, true);
                curStep = super._field.getBoard().getField(curStepCoords);
                if (curStep == null)
                    break;
                if (!movements.contains(curStep) && (curStep.getPiece() != null && curStep.getPiece().getPlayer().equals(super._player)))
                    movements.add(curStep);
                if (curStep.isTriangular())
                    evenStep = !evenStep;
                else
                    evenStep = i > 5;
                stepsLeft--;
            } while (curStep.getPiece() == null && stepsLeft > 0);
        }
        return movements;
    }
}