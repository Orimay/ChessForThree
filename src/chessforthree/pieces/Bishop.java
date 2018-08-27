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
public class Bishop extends ChessPiece {
    public Bishop(ChessPlayer player) {
        super(player, ChessPieceClass.BISHOP);
    }
    
    @Override
    public List<ChessField> getFreeAvailableFields() {
        List<ChessField> movements = new ArrayList();
        movements.addAll(super.moveDiagonal(0, false, false));
        return movements;
    }
    
    @Override
    public List<ChessField> getOccupedAvailableFields() {
        List<ChessField> movements = new ArrayList();
        movements.addAll(super.moveDiagonal(0, true, false));
        return movements;
    }
    
    @Override
    public List<ChessField> getOccupedUnavailableFields() {
        List<ChessField> movements = new ArrayList();
        movements.addAll(super.moveDiagonal(0, true, true));
        return movements;
    }
}