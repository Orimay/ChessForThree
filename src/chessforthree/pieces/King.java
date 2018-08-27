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
public class King extends ChessPiece {
    public King(ChessPlayer player) {
        super(player, ChessPieceClass.KING);
    }
    
    @Override
    public List<ChessField> getFreeAvailableFields() {
        List<ChessField> movements = new ArrayList();
        movements.addAll(super.moveStraight(1, false, super._field, false, false));
        movements.addAll(super.moveDiagonal(1, false, false));
        return movements;
    }
    
    @Override
    public List<ChessField> getOccupedAvailableFields() {
        List<ChessField> movements = new ArrayList();
        movements.addAll(super.moveStraight(1, false, super._field, true, false));
        movements.addAll(super.moveDiagonal(1, true, false));
        return movements;
    }
    
    @Override
    public List<ChessField> getOccupedUnavailableFields() {
        List<ChessField> movements = new ArrayList();
        movements.addAll(super.moveStraight(1, false, super._field, true, true));
        movements.addAll(super.moveDiagonal(1, true, true));
        return movements;
    }
}
