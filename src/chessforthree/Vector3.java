package chessforthree;

import java.awt.Color;

/**
 *
 * @author Dmitrii
 */
public class Vector3 {
    private static int _state = 0;
    private int _x = 0;
    private int _y = 0;
    private int _z = 0;
    
    public static final Vector3 Zero = new Vector3(0, 0, 0);
    
    public Vector3() {
        
    }
    
    public Vector3(int x, int y, int z) {
        switch (Vector3._state) {
            case 1:
                this._x = y;
                this._y = z;
                this._z = x;
                return;
            case 2:
                this._x = z;
                this._y = x;
                this._z = y;
                return;
        }
        this._x = x;
        this._y = y;
        this._z = z;
    }
    
    public int getX() {
        return this.getX(false);
    }
    
    public int getX(boolean real) {
        if (!real)
            switch (Vector3._state) {
                case 1:
                    return this._y;
                case 2:
                    return this._z;
            }
        return this._x;
    }
    
    public int getY() {
        return this.getY(false);
    }
    
    public int getY(boolean real) {
        if (!real)
            switch (Vector3._state) {
                case 1:
                    return this._z;
                case 2:
                    return this._x;
            }
        return this._y;
    }
    
    public int getZ() {
        return this.getZ(false);
    }
    
    public int getZ(boolean real) {
        if (!real)
            switch (Vector3._state) {
                case 1:
                    return this._x;
                case 2:
                    return this._y;
            }
        return this._z;
    }
    
    public int getRealX() {
        return this._x;
    }
    
    public int getRealY() {
        return this._y;
    }
    
    public int getRealZ() {
        return this._z;
    }
    
    public void setX(int value) {
        switch (Vector3._state) {
            case 1:
                this._y = value;
                return;
            case 2:
                this._z = value;
                return;
        }
        this._x = value;
    }
    
    public void setY(int value) {
        switch (Vector3._state) {
            case 1:
                this._z = value;
                return;
            case 2:
                this._x = value;
                return;
        }
        this._y = value;
    }
    
    public void setZ(int value) {
        switch (Vector3._state) {
            case 1:
                this._x = value;
                return;
            case 2:
                this._y = value;
                return;
        }
        this._z = value;
    }
    
    public void incX(boolean real) {
        if (!real)
            switch (Vector3._state) {
                case 1:
                    this._y++;
                    return;
                case 2:
                    this._z++;
                    return;
            }
        this._x++;
    }
    
    public void incY(boolean real) {
        if (!real)
            switch (Vector3._state) {
                case 1:
                    this._z++;
                    return;
                case 2:
                    this._x++;
                    return;
            }
        this._y++;
    }
    
    public void incZ(boolean real) {
        if (!real)
            switch (Vector3._state) {
                case 1:
                    this._x++;
                    return;
                case 2:
                    this._y++;
                    return;
            }
        this._z++;
    }
    
    public void decX(boolean real) {
        if (!real)
            switch (Vector3._state) {
                case 1:
                    this._y--;
                    return;
                case 2:
                    this._z--;
                    return;
            }
        this._x--;
    }
    
    public void decY(boolean real) {
        if (!real)
            switch (Vector3._state) {
                case 1:
                    this._z--;
                    return;
                case 2:
                    this._x--;
                    return;
            }
        this._y--;
    }
    
    public void decZ(boolean real) {
        if (!real)
            switch (Vector3._state) {
                case 1:
                    this._x--;
                    return;
                case 2:
                    this._y--;
                    return;
            }
        this._z--;
    }
    
    public static void turnBack() {
        switch (Vector3._state) {
            case 1:
                turnRight();
                break;
            case 2:
                turnLeft();
                break;
        }
    }
    
    public static void turnLeft() {
        Vector3._state += 1;
        if (Vector3._state > 2)
            Vector3._state -= 3;
        Color[] boardColors = ChessManager.getInstance().getChessBoard().getBoardColors();
        Color tmp = boardColors[0];
        boardColors[0] = boardColors[1];
        boardColors[1] = boardColors[2];
        boardColors[2] = tmp;
        ChessManager.getInstance().getChessBoard().repaint();
    }
    
    public static void turnRight() {
        Vector3._state -= 1;
        if (Vector3._state < 0)
            Vector3._state += 3;
        Color[] boardColors = ChessManager.getInstance().getChessBoard().getBoardColors();
        Color tmp = boardColors[2];
        boardColors[2] = boardColors[1];
        boardColors[1] = boardColors[0];
        boardColors[0] = tmp;
        ChessManager.getInstance().getChessBoard().repaint();
    }
    
    static void turnLeft(ChessManager manager) {
        Vector3._state += 1;
        if (Vector3._state > 2)
            Vector3._state -= 3;
        Color[] boardColors = manager.getChessBoard().getBoardColors();
        Color tmp = boardColors[0];
        boardColors[0] = boardColors[1];
        boardColors[1] = boardColors[2];
        boardColors[2] = tmp;
        manager.getChessBoard().repaint();
    }
    
    public static void turnRight(ChessManager manager) {
        Vector3._state -= 1;
        if (Vector3._state < 0)
            Vector3._state += 3;
        Color[] boardColors = manager.getChessBoard().getBoardColors();
        Color tmp = boardColors[2];
        boardColors[2] = boardColors[1];
        boardColors[1] = boardColors[0];
        boardColors[0] = tmp;
        manager.getChessBoard().repaint();
    }
    
    @Override
    public Vector3 clone() {
        switch (Vector3._state) {
            case 1:
                return new Vector3(this._z, this._x, this._y);
            case 2:
                return new Vector3(this._y, this._z, this._x);
        }
        return new Vector3(this._x, this._y, this._z);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Vector3))
            return false;
        Vector3 v = (Vector3)obj;
        return v._x == this._x && v._y == this._y && v._z == this._z;
    }
    
    @Override
    public int hashCode() {
        return this._x ^ this._y ^ this._z;
    }
    
    @Override
    public String toString() {
        return "Vector3(" + this._x + ", " + this._y + ", " + this._z + ")";
    }
}