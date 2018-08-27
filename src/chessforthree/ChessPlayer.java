package chessforthree;

import chessforthree.pieces.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Dmitrii
 */
public class ChessPlayer {
    private ChessBoard _board = null;
    private ChessPlayerClass _playerClass = null;
    private Color _color;
    private boolean _isHuman = false;
    private ChessPiece _king = null;
    private List<ChessPiece> _pieces = null;
    
    public ChessPlayer(ChessBoard board, ChessPlayerClass playerClass) {
        this._board = board;
        this._playerClass = playerClass;
        switch (this._playerClass) {
            case ORANGE:
                this._color = new Color(255, 127, 0);
                break;
            case SAPPHIRINE:
                this._color = new Color(15, 82, 186);
                break;
            case PURPLE:
                this._color = new Color(128, 0, 128);
                break;
            default:
                this._color = new Color(0, 0, 0);
                break;
        }
        this._pieces = new ArrayList();
    }
    
    public ChessPlayerClass getPlayerClass() {
        return this._playerClass;
    }
    
    public Color getColor() {
        return this._color;
    }
    
    public boolean isHuman() {
        return this._isHuman;
    }
    
    public List<ChessPiece> getPieces() {
        return this._pieces;
    }
    
    public ChessPiece getPiece(Class pieceClass) {
        ChessPiece newPiece = null;
        if (pieceClass.equals(Pawn.class)) {
            newPiece = new Pawn(this);
        } else if (pieceClass.equals(Tower.class)) {
            newPiece = new Tower(this);
        } else if (pieceClass.equals(Knight.class)) {
            newPiece = new Knight(this);
        } else if (pieceClass.equals(Bishop.class)) {
            newPiece = new Bishop(this);
        } else if (pieceClass.equals(Queen.class)) {
            newPiece = new Queen(this);
        } else if (pieceClass.equals(King.class)) {
            newPiece = new King(this);
            this._king = newPiece;
        }
        if (newPiece != null)
            this._pieces.add(newPiece);
        return newPiece;
    }
    
    public void calculate() {
        Map<ChessMovement, Integer> movements = new HashMap();
        for (ChessPiece piece : this._pieces) {
            ChessField originalField = piece.getField();
            List<ChessField> availableFields = piece.getAllMovements();
            for (ChessField availableField : availableFields) {
                ChessPiece availableFieldPiece = availableField.getPiece();
                availableField.setPiece(piece);
                int cost = calculateOverageCost();
                if (!this._king.isAttacked())
                    movements.put(new ChessMovement(originalField.getCoordinates(), availableField.getCoordinates()), cost);
                originalField.setPiece(piece);
                availableField.setPiece(availableFieldPiece);
            }
        }
        calculateOverageCost();
        if (movements.size() < 1) {
            for (ChessPiece piece : this._pieces) {
                if (piece.getField() != null)
                    piece.getField().setPiece(null);
            }
            if (this._king.isAttacked()) {
                // Player was checkmated
            } else {
                // Player was pated
            }
            //this._board.getManager().checkmatePlayer(this);
            return;
        }
        int bestMovementCost = movements.values().iterator().next();
        ChessMovement bestMovement = movements.keySet().iterator().next();
        for (ChessMovement movement : movements.keySet()) {
            int movementCost = movements.get(movement);
            if (bestMovementCost < movementCost) {
                bestMovementCost = movementCost;
                bestMovement = movement;
            }
        }
        this._board.getField(bestMovement.getTarget()).setPiece(this._board.getField(bestMovement.getSource()).getPiece());
        for (int i = this._pieces.size() - 1; i > -1; i--) {
            if (this._pieces.get(i)._field == null) {
                this._pieces.remove(i);
            }
        }
        for (ChessPlayer player : this._board.getManager().getPlayers()) {
            for (int i = player._pieces.size() - 1; i > -1; i--) {
                if (player._pieces.get(i)._field == null) {
                    player._pieces.remove(i);
                }
            }
        }
    }
    
    public boolean tryMovement(ChessPiece piece, ChessField field) {
        ChessField sourceField = piece.getField();
        ChessPiece chosenFieldPiece = field.getPiece();
        field.setPiece(piece);
        calculateOverageCost();
        if (this._king.isAttacked()) {
            sourceField.setPiece(piece);
            field.setPiece(chosenFieldPiece);
            calculateOverageCost();
            return false;
        }
        return true;
    }
    
    private int calculateOverageCost() {
        int overage = 0;
        ArrayList<ChessPlayer> players = this._board.getManager().getPlayers();
        for (ChessPlayer player : players) {
            for (ChessPiece piece : player._pieces) {
                piece.setAttacked(false);
            }            
        }
        for (ChessPlayer player : players) {
            for (ChessPiece piece : player._pieces) {
                List<ChessField> freeFields = piece.getFreeAvailableFields();
                for (Iterator<ChessField> it = freeFields.iterator(); it.hasNext(); it.next()) {
                    if (player.equals(this)) {
                        overage++;
                    } else {
                        overage--;
                    }
                }
                List<ChessField> occupedFields = piece.getOccupedAvailableFields();
                for (ChessField occupedField : occupedFields) {
                    occupedField.getPiece().setAttacked(true);
                    if (player.equals(this)) {
                        overage += occupedField.getPiece().getCost() * 10;
                    } else {
                        overage -= occupedField.getPiece().getCost() * 100;
                    }
                }
                List<ChessField> protectedFields = piece.getOccupedUnavailableFields();
                for (ChessField protectedField : protectedFields) {
                    if (player.equals(this)) {
                        overage += protectedField.getPiece().getCost();
                    } else {
                        overage -= protectedField.getPiece().getCost();
                    }
                }
            }
        }
        return overage;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof ChessPlayer))
            return false;
        ChessPlayer cp = (ChessPlayer)obj;
        return cp._playerClass.equals(this._playerClass) && cp._color.equals(this._color) && cp._isHuman == this._isHuman;
    }
    
    @Override
    public int hashCode() {
        return this._playerClass.hashCode() + this._color.hashCode();
    }
}