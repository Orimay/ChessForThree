package chessforthree.util;

/**
 *
 * @author Dmitrii
 */
public class MutableInteger implements Comparable {
    private int _value = 0;
    
    public MutableInteger() {
        this(0);
    }
    
    public MutableInteger(int value) {
        this._value = value;
    }
    
    public int getValue() {
        return this._value;
    }
    
    public void setValue(int value) {
        this._value = value;
    }
    
    public void Inc() {
        this._value++;
    }
    
    public void Dec() {
        this._value--;
    }
    
    public void Add(int value) {
        this._value += value;
    }
    
    public void Substract(int value) {
        this._value -= value;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof MutableInteger))
            return false;
        MutableInteger mi = (MutableInteger)obj;
        return mi._value == this._value;
    }
    
    @Override
    public int hashCode() {
        return this._value;
    }

    @Override
    public int compareTo(Object t) {
        if (t == null)
            return 1;
        if (t == this)
            return 0;
        if (!(t instanceof MutableInteger))
            return 1;
        MutableInteger mi = (MutableInteger)t;
        if (this._value > mi._value)
            return 1;
        if (this._value > mi._value)
            return -1;
        return 0;
    }
}