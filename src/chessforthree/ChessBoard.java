package chessforthree;

import java.awt.Color;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author Dmitrii
 */
public class ChessBoard extends JPanel implements ComponentListener, MouseListener, KeyListener {
    private ChessManager _manager = null;
    private Color[] _boardColors = null;
    private Point _center = new Point(0, 0);
    private Map<Vector3, ChessField> _fields = new HashMap();
    private List<ChessField> _activeFields = new ArrayList();
    private ChessField _selectedPieceField = null;
    
    public ChessBoard(ChessManager manager) {
        this._manager = manager;
        this._boardColors = new Color[4];
        this._boardColors[0] = new Color(1.0f, 0.7f, 0.7f);
        this._boardColors[1] = new Color(0.7f, 0.7f, 1.0f);
        this._boardColors[2] = new Color(1.0f, 1.0f, 0.7f);
        this._boardColors[3] = new Color(0.7f, 1.0f, 0.7f);
        Vector3 fieldPosition;
        for (int row = -4; row < -2; row++)
            for (int lr = -2, rl = 5; lr < 6; lr++, rl--) {
                fieldPosition = new Vector3(row, lr, rl);
                _fields.put(fieldPosition, new ChessField(this, fieldPosition));
                fieldPosition = new Vector3(rl, row, lr);
                _fields.put(fieldPosition, new ChessField(this, fieldPosition));
                fieldPosition = new Vector3(lr, rl, row);
                _fields.put(fieldPosition, new ChessField(this, fieldPosition));
            }
        boolean updateRow = true;
        for (int row = 5, rl = -2; row > -3; updateRow = !updateRow) {
            int tmp = row;
            for (int lr = -2; lr <= tmp; lr++, row--) {
                fieldPosition = new Vector3(row, lr, rl);
                _fields.put(fieldPosition, new ChessField(this, fieldPosition));
            }
            row = tmp;
            if (updateRow)
                row--;
            else
                rl++;
        }
        this.addMouseListener(this);
        this.addComponentListener(this);
        this.addKeyListener(this);
    }
    
    public ChessManager getManager() {
        return this._manager;
    }
    
    public Color[] getBoardColors() {
        return this._boardColors;
    }
    
    public ChessField getField(Vector3 coordinates) {
        if (this._fields.containsKey(coordinates))
            return this._fields.get(coordinates);
        return null;
    }
    
    public Point getCenter() {
        return this._center;
    }
    
    public Map<Vector3, ChessField> getFields() {
        return this._fields;
    }
    
    public ChessField getSelectedPieceField() {
        return this._selectedPieceField;
    }
    
    @Override
    public void setSize(int i, int i1) {
        super.setSize(i, i1);
        this._center.x = (int)(this.getWidth() * 0.5f);
        this._center.y = (int)(this.getHeight() * 0.5f);
        this.repaint();
    }
    
    @Override
    public void paint(Graphics g) {
        for (ChessField field : this._fields.values())
            field.paint(g);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (e.getButton()) {
            case 1:
                if (e.getClickCount() == 1)
                    for (ChessField field : this._fields.values())
                        if (field.getPolygon().contains(e.getX(), e.getY())) {
                            for (ChessField activeField : this._activeFields)
                                activeField.setActivated(false);
                            if (this._activeFields.contains(field)) {
                                this._activeFields.clear();
                                field.setPiece(this._selectedPieceField.getPiece());
                                this._selectedPieceField.setSelected(false);
                                this._manager.changePlayer();
                                //Vector3.turnRight();
                            } else {
                                if (this._selectedPieceField != null)
                                    this._selectedPieceField.setSelected(false);
                                this._activeFields.clear();
                                if (field.getPiece() != null && this._manager.getActivePlayer() == field.getPiece()._player) {
                                    if (!field.isSelected()) {
                                        this._selectedPieceField = field;
                                        field.setSelected(true);
                                        List<ChessField> movements = field.getPiece().getAllMovements();
                                        for (ChessField activeField : movements) {
                                            activeField.setActivated(true);
                                            this._activeFields.add(activeField);
                                        }
                                    } else {
                                        field.setSelected(false);
                                    }
                                }
                            }
                            break;
                        }
                break;
            case 2:
                Vector3.turnRight();
                break;
            case 3:
                for (ChessField activeField : this._activeFields)
                    activeField.setActivated(false);
                if (this._selectedPieceField != null) {
                    this._selectedPieceField.setSelected(false);
                }
                this._manager.calculateStep();
                break;
        }
    }

    @Override
    public void componentResized(ComponentEvent ce) {
        this._center.x = (int)(this.getWidth() * 0.5f);
        this._center.y = (int)(this.getHeight() * 0.5f);
        this.repaint();
    }
    @Override
    public void componentMoved(ComponentEvent ce) {
    }
    @Override
    public void componentShown(ComponentEvent ce) {
    }
    @Override
    public void componentHidden(ComponentEvent ce) {
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        if (ke.getKeyCode()== KeyEvent.VK_SPACE) {
            this._manager.calculateStep();
        }
    }
    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode()== KeyEvent.VK_SPACE) {
            this._manager.calculateStep();
        }
    }
    @Override
    public void keyReleased(KeyEvent ke) {
        if (ke.getKeyCode()== KeyEvent.VK_SPACE) {
            this._manager.calculateStep();
        }
    }
}