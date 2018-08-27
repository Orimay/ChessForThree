package chessforthree;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;

/**
 *
 * @author Dmitrii
 */
public class ChessField {
    private ChessBoard _board = null;
    private Vector3 _coordinates = null;
    private Point _position = null;
    private Polygon _polygon = null;
    private int _edgeLength = 0;
    private boolean _activated = false;
    private boolean _selected = false;
    private ChessPiece _piece = null;
    
    public ChessField(ChessBoard board, Vector3 position) {
        this._board = board;
        this._coordinates = position;
        this._position = new Point(0, 0);
        this._polygon = new Polygon();
        this._edgeLength = 70;
    }
    
    public ChessBoard getBoard() {
        return this._board;
    }
    
    public Vector3 getCoordinates() {
        return this._coordinates;
    }
    
    private int getEdgeLength() {
        return this._edgeLength;
    }
    
    private int getTriangularHeight() {
        return (int)(this._edgeLength * 0.5f * Math.sqrt(3));
    }
    
    public Polygon getPolygon() {
        return this._polygon;
    }
    
    public ChessPiece getPiece() {
        return this._piece;
    }
    
    public void setPiece(ChessPiece piece) {
        if (piece != null) {
            if (piece._field != null) {
                piece._field._piece = null;
            }
            piece._field = this;
        }
        if (this._piece != null) {
            this._piece._field = null;
        }
        this._piece = piece;
    }
    
    public boolean isTriangular() {
        return this._coordinates.getX() > -3 && this._coordinates.getY() > -3 && this._coordinates.getZ() > -3;
    }
    
    public Color getColor() {
        if (this._activated)
            return Color.CYAN;
        if (this._selected)
            return Color.DARK_GRAY;
        if (Math.abs((this._coordinates.getX() + 1) % 2) == Math.abs(this._coordinates.getY() % 2) && Math.abs(this._coordinates.getY() % 2) == Math.abs(this._coordinates.getZ() % 2))
            return this._board.getBoardColors()[0];
        if (Math.abs(this._coordinates.getX() % 2) == Math.abs((this._coordinates.getY() + 1) % 2) && Math.abs((this._coordinates.getY() + 1) % 2) == Math.abs(this._coordinates.getZ() % 2))
            return this._board.getBoardColors()[1];
        if (Math.abs(this._coordinates.getX() % 2) == Math.abs(this._coordinates.getY() % 2) && Math.abs(this._coordinates.getY() % 2) == Math.abs((this._coordinates.getZ() + 1) % 2))
            return this._board.getBoardColors()[2];
        return this._board.getBoardColors()[3];
    }
    
    public boolean isActivated() {
        return this._activated;
    }
    
    public boolean isSelected() {
        return this._selected;
    }
    
    public void setActivated(boolean activated) {
        this._activated = activated;
        this._board.repaint();
    }
    
    public void setSelected(boolean selected) {
        if (selected) {
            if (this._piece == null && this._board.getSelectedPieceField() == null)
                return;
            if (this._board.getSelectedPieceField() != null)
                this._board.getSelectedPieceField().setSelected(false);
        }
        this._selected = selected;
        this._board.repaint();
    }
    
    public void paint(Graphics g) {
        this._position = this.getProjectedPoint();
        this._polygon.reset();
        int edgeHalf = this.getEdgeLength() / 2;
        switch (this.getFieldOrientation()) {
            case NORMAL_TRIANGULAR:
                this._polygon.addPoint(this._position.x + edgeHalf, this._position.y + this.getTriangularHeight() / 3);
                this._polygon.addPoint(this._position.x - edgeHalf, this._position.y + this.getTriangularHeight() / 3);
                this._polygon.addPoint(this._position.x, this._position.y - this.getTriangularHeight() * 2 / 3);
                break;
            case INVERTED_TRIANGULAR:
                this._polygon.addPoint(this._position.x + edgeHalf, this._position.y - this.getTriangularHeight() / 3);
                this._polygon.addPoint(this._position.x - edgeHalf, this._position.y - this.getTriangularHeight() / 3);
                this._polygon.addPoint(this._position.x, this._position.y + this.getTriangularHeight() * 2 / 3);
                break;
            case LEFT_SQUARE:
                this._polygon.addPoint(this._position.x + (int)(edgeHalf * Math.cos(Math.toRadians(-60)) - edgeHalf * Math.sin(Math.toRadians(-60))), this._position.y + (int)(edgeHalf * Math.sin(Math.toRadians(-60)) + edgeHalf * Math.cos(Math.toRadians(-60))));
                this._polygon.addPoint(this._position.x + (int)(-edgeHalf * Math.cos(Math.toRadians(-60)) - edgeHalf * Math.sin(Math.toRadians(-60))), this._position.y + (int)(-edgeHalf * Math.sin(Math.toRadians(-60)) + edgeHalf * Math.cos(Math.toRadians(-60))));
                this._polygon.addPoint(this._position.x + (int)(-edgeHalf * Math.cos(Math.toRadians(-60)) + edgeHalf * Math.sin(Math.toRadians(-60))), this._position.y + (int)(-edgeHalf * Math.sin(Math.toRadians(-60)) - edgeHalf * Math.cos(Math.toRadians(-60))));
                this._polygon.addPoint(this._position.x + (int)(edgeHalf * Math.cos(Math.toRadians(-60)) + edgeHalf * Math.sin(Math.toRadians(-60))), this._position.y + (int)(edgeHalf * Math.sin(Math.toRadians(-60)) - edgeHalf * Math.cos(Math.toRadians(-60))));
                break;
            case RIGHT_SQUARE:
                this._polygon.addPoint(this._position.x + (int)(edgeHalf * Math.cos(Math.toRadians(60)) - edgeHalf * Math.sin(Math.toRadians(60))), this._position.y + (int)(edgeHalf * Math.sin(Math.toRadians(60)) + edgeHalf * Math.cos(Math.toRadians(60))));
                this._polygon.addPoint(this._position.x + (int)(-edgeHalf * Math.cos(Math.toRadians(60)) - edgeHalf * Math.sin(Math.toRadians(60))), this._position.y + (int)(-edgeHalf * Math.sin(Math.toRadians(60)) + edgeHalf * Math.cos(Math.toRadians(60))));
                this._polygon.addPoint(this._position.x + (int)(-edgeHalf * Math.cos(Math.toRadians(60)) + edgeHalf * Math.sin(Math.toRadians(60))), this._position.y + (int)(-edgeHalf * Math.sin(Math.toRadians(60)) - edgeHalf * Math.cos(Math.toRadians(60))));
                this._polygon.addPoint(this._position.x + (int)(edgeHalf * Math.cos(Math.toRadians(60)) + edgeHalf * Math.sin(Math.toRadians(60))), this._position.y + (int)(edgeHalf * Math.sin(Math.toRadians(60)) - edgeHalf * Math.cos(Math.toRadians(60))));
                break;
            case BOTTOM_SQUARE:
                this._polygon.addPoint(this._position.x + edgeHalf, this._position.y + edgeHalf);
                this._polygon.addPoint(this._position.x - edgeHalf, this._position.y + edgeHalf);
                this._polygon.addPoint(this._position.x - edgeHalf, this._position.y - edgeHalf);
                this._polygon.addPoint(this._position.x + edgeHalf, this._position.y - edgeHalf);
                break;
        }
        g.setColor(this.getColor());
        g.fillPolygon(this._polygon);
        
        if (this._piece != null) {
            g.setColor(this._piece.getPlayer().getColor());
            g.fillOval((int)(this._position.x - this._edgeLength * 0.25f), (int)(this._position.y - this._edgeLength * 0.25f), (int)(this._edgeLength * 0.5f), (int)(this._edgeLength * 0.5f));
            g.setColor(Color.BLACK);
            g.drawOval((int)(this._position.x - this._edgeLength * 0.25f), (int)(this._position.y - this._edgeLength * 0.25f), (int)(this._edgeLength * 0.5f), (int)(this._edgeLength * 0.5f));
            Image icon = this._piece.getIcon();
            g.drawImage(icon, this._position.x - icon.getWidth(null) / 2, this._position.y - icon.getHeight(null) / 2, null);
        }
        
//        g.setColor(Color.BLACK);
//        g.drawPolygon(this._polygon);
//        Polygon p = new Polygon();
//        p.addPoint(this._position.x + this.getEdgeLength() / 20, this._position.y + this.getTriangularHeight() / 30);
//        p.addPoint(this._position.x - this.getEdgeLength() / 20, this._position.y + this.getTriangularHeight() / 30);
//        p.addPoint(this._position.x, this._position.y - this.getTriangularHeight() * 2 / 30);
//        g.fillPolygon(p);
    }
    
    private Point getProjectedPoint() {
        float x = 0.0f;
        float y = 0.0f;
        switch (this.getFieldOrientation()) {
            case NORMAL_TRIANGULAR:
                x -= this.getEdgeLength() * (this._coordinates.getX() - this._coordinates.getZ()) * 0.5f;
                y -= this.getTriangularHeight() * this._coordinates.getY() - this.getTriangularHeight() / 3;
                break;
            case INVERTED_TRIANGULAR:
                x -= this.getEdgeLength() * (this._coordinates.getX() - this._coordinates.getZ()) * 0.5f;
                y -= this.getTriangularHeight() * this._coordinates.getY();
                break;
            case LEFT_SQUARE:
                x -= this.getEdgeLength() * (this._coordinates.getX() - this._coordinates.getY()) * 0.5f * 0.5f;
                x += (this.getEdgeLength() * this._coordinates.getZ() + this.getEdgeLength() * 0.5f - this.getTriangularHeight() / 3.0f) * Math.sqrt(3) * 0.5f;
                y += this.getEdgeLength() * (this._coordinates.getX() - this._coordinates.getY()) * 0.5f * Math.sqrt(3) * 0.5f;
                y += (this.getEdgeLength() * this._coordinates.getZ() + this.getEdgeLength() * 0.5f - this.getTriangularHeight() / 3.0f) * 0.5f;
                break;
            case RIGHT_SQUARE:
                x -= this.getEdgeLength() * (this._coordinates.getY() - this._coordinates.getZ()) * 0.5f * 0.5f;
                x -= (this.getEdgeLength() * this._coordinates.getX() + this.getEdgeLength() * 0.5f - this.getTriangularHeight() / 3.0f) * Math.sqrt(3) * 0.5f;
                y -= this.getEdgeLength() * (this._coordinates.getY() - this._coordinates.getZ()) * 0.5f * Math.sqrt(3) * 0.5f;
                y += (this.getEdgeLength() * this._coordinates.getX() + this.getEdgeLength() * 0.5f - this.getTriangularHeight() / 3.0f) * 0.5f;
                break;
            case BOTTOM_SQUARE:
                x -= this.getEdgeLength() * (this._coordinates.getX() - this._coordinates.getZ()) * 0.5f;
                y -= this.getEdgeLength() * this._coordinates.getY() + this.getEdgeLength() * 0.5f - this.getTriangularHeight() / 3.0f;
                break;
        }
        x += this._board.getCenter().x;
        y += this._board.getCenter().y;
        return new Point((int)x, (int)y);
    }
    
    private enum FieldOrientation {
        LEFT_SQUARE,
        RIGHT_SQUARE,
        BOTTOM_SQUARE,
        NORMAL_TRIANGULAR,
        INVERTED_TRIANGULAR;
    }
    
    private FieldOrientation getFieldOrientation() {
        if (this._coordinates.getZ() < -2)
            return FieldOrientation.LEFT_SQUARE;
        if (this._coordinates.getX() < -2)
            return FieldOrientation.RIGHT_SQUARE;
        if (this._coordinates.getY() < -2)
            return FieldOrientation.BOTTOM_SQUARE;
        if ((this._coordinates.getX() + this._coordinates.getY() + this._coordinates.getZ()) % 2 == 0)
            return FieldOrientation.INVERTED_TRIANGULAR;
        return FieldOrientation.NORMAL_TRIANGULAR;
    }
}