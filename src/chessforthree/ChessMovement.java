package chessforthree;

/**
 *
 * @author Dmitrii
 */
public class ChessMovement {
    private Vector3 _sourceField = null;
    private Vector3 _targetField = null;
    
    public ChessMovement(Vector3 source, Vector3 target) {
        this._sourceField = source;
        this._targetField = target;
    }
    
    public Vector3 getSource() {
        return this._sourceField;
    }
    
    public Vector3 getTarget() {
        return this._targetField;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof ChessMovement))
            return false;
        ChessMovement m = (ChessMovement)obj;
        return m._sourceField == this._sourceField && m._targetField == this._targetField;
    }
    
    @Override
    public int hashCode() {
        return this._sourceField.hashCode() ^ this._targetField.hashCode();
    }
}