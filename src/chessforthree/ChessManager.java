package chessforthree;

import chessforthree.pieces.*;
import chessforthree.util.MutableInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Dmitrii
 */
public class ChessManager {
    private static ChessManager _manager = null;
    private ChessBoard _board = null;
    private int _activePlayer = 0;
    private Map<Integer, ChessPlayer> _players = null;
    
    public static ChessManager getInstance(ChessPlayerClass chosenPlayer) {
        if (ChessManager._manager == null) {
            ChessManager._manager = new ChessManager(chosenPlayer);
        } else {
            Vector3.turnBack();
            for (int i = 0; i < ChessManager._manager._players.size(); i++)
                if (ChessManager._manager._players.get(i).getPlayerClass().equals(chosenPlayer)) {
                    ChessManager._manager._activePlayer = i;
                    break;
                }
            for (int i = 0; i < ChessManager._manager._activePlayer; i++)
                Vector3.turnLeft();
        }
        return ChessManager._manager;
    }
    
    static ChessManager getInstance() {
        if (ChessManager._manager == null)
            ChessManager._manager = new ChessManager(ChessPlayerClass.ORANGE);
        return ChessManager._manager;
    }
    
    private ChessManager(ChessPlayerClass chosenPlayer) {
        this._board = new ChessBoard(this);
        this._players = new HashMap();
        this._players.put(0, new ChessPlayer(this._board, ChessPlayerClass.ORANGE));
        this._players.put(1, new ChessPlayer(this._board, ChessPlayerClass.PURPLE));
        this._players.put(2, new ChessPlayer(this._board, ChessPlayerClass.SAPPHIRINE));
        for (int playerIndex : this._players.keySet()) {
            if (this._players.get(playerIndex).getPlayerClass().equals(chosenPlayer)) {
                this._activePlayer = playerIndex;
                break;
            }
        }
        for (ChessPlayer player : this._players.values()) {
            for (int x = 5, z = -2; z < 6; x--, z++)
                this._board.getField(new Vector3(x, -3, z)).setPiece(player.getPiece(Pawn.class));
            this._board.getField(new Vector3(5, -4, -2)).setPiece(player.getPiece(Tower.class));
            this._board.getField(new Vector3(-2, -4, 5)).setPiece(player.getPiece(Tower.class));
            this._board.getField(new Vector3(4, -4, -1)).setPiece(player.getPiece(Knight.class));
            this._board.getField(new Vector3(-1, -4, 4)).setPiece(player.getPiece(Knight.class));
            this._board.getField(new Vector3(3, -4, 0)).setPiece(player.getPiece(Bishop.class));
            this._board.getField(new Vector3(0, -4, 3)).setPiece(player.getPiece(Bishop.class));
            this._board.getField(new Vector3(2, -4, 1)).setPiece(player.getPiece(Queen.class));
            this._board.getField(new Vector3(1, -4, 2)).setPiece(player.getPiece(King.class));
            Vector3.turnRight(this);
        }
        for (int i = 0; i < this._activePlayer; i++)
            Vector3.turnLeft(this);
    }
    
    public ChessBoard getChessBoard() {
        return this._board;
    }
    
    public ChessPlayer getActivePlayer() {
        return this._players.get(this._activePlayer);
    }
    
    public int getActivePlayerNum() {
        return this._activePlayer;
    }
    
    public void calculateStep() {
        this._players.get(this._activePlayer).calculate();
        this.changePlayer();
        this._board.repaint();
    }
    
    public void checkmatePlayer(ChessPlayer player) {
        for (int i = this._players.size() - 1; i > -1; i--) {
            if (this._players.get(i) == player) {
                this._players.remove(i);
            }
        }
    }
    
    public ArrayList<ChessPlayer> getPlayers() {
        return new ArrayList(this._players.values());
    }
    
    public void changePlayer() {
        do {
            this._activePlayer--;
            if (this._activePlayer < 0) {
                this._activePlayer = 2;
            }
        } while (!this._players.containsKey(this._activePlayer));
    }
}