package chessforthree.pieces;

import chessforthree.ChessField;
import chessforthree.ChessPiece;
import chessforthree.ChessPieceClass;
import chessforthree.ChessPlayer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dmitrii
 */
public class Tower extends ChessPiece {
    public Tower(ChessPlayer player) {
        super(player, ChessPieceClass.TOWER);
    }
    
    @Override
    public List<ChessField> getFreeAvailableFields() {
        List<ChessField> movements = new ArrayList();
        movements.addAll(super.moveStraight(0, false, super._field, false, false));
        return movements;
    }
    
    @Override
    public List<ChessField> getOccupedAvailableFields() {
        List<ChessField> movements = new ArrayList();
        movements.addAll(super.moveStraight(0, false, super._field, true, false));
        return movements;
    }
    
    @Override
    public List<ChessField> getOccupedUnavailableFields() {
        List<ChessField> movements = new ArrayList();
        movements.addAll(super.moveStraight(0, false, super._field, true, true));
        return movements;
    }
}