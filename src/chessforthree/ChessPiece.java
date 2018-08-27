package chessforthree;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import sun.security.util.Debug;

/**
 *
 * @author Dmitrii
 */
public class ChessPiece {
    protected ChessPlayer _player = null;
    protected ChessPieceClass _pieceClass = null;
    private Image _icon = null;
    private int _cost = 0;
    protected ChessField _field = null;
    private boolean _isAttacked = false;
    
    public ChessPiece(ChessPlayer player, ChessPieceClass pieceClass) {
        this._player = player;
        this._pieceClass = pieceClass;
        switch (this._pieceClass) {
            case PAWN:
                this._cost = 1;
                break;
            case TOWER:
                this._cost = 2;
                break;
            case KNIGHT:
                this._cost = 4;
                break;
            case BISHOP:
                this._cost = 3;
                break;
            case QUEEN:
                this._cost = 5;
                break;
            case KING:
                this._cost = 6;
        }
        this.initIcon();
    }
    
    public ChessPlayer getPlayer() {
        return this._player;
    }
    
    public ChessPieceClass getPieceClass() {
        return this._pieceClass;
    }
    
    public Image getIcon() {
        return this._icon;
    }
    
    public int getCost() {
        return this._cost;
    }
    
    public ChessField getField() {
        return this._field;
    }
    
    public boolean isAttacked() {
        return this._isAttacked;
    }
    
    public void setAttacked(boolean isAttacked) {
        this._isAttacked = isAttacked;
    }
    
    public ChessField[] getMovements(ChessPiece piece) {
        return null;
    }
    
    public ChessField getMovement(Integer cost) {
        cost = 0;
        return null;
    }
    
    public List<ChessField> getAllMovements() {
        List<ChessField> movements = new ArrayList();
        movements.addAll(this.getFreeAvailableFields());
        movements.addAll(this.getOccupedAvailableFields());
        return movements;
    }
    
    protected List<ChessField> getFreeAvailableFields() {
        return new ArrayList();
    }
    
    protected List<ChessField> getOccupedAvailableFields() {
        return new ArrayList();
    }
    
    protected List<ChessField> getOccupedUnavailableFields() {
        return new ArrayList();
    }
    
    protected final void initIcon() {
        String letter = this._pieceClass.toString().substring(0, 1);
        if (!this._pieceClass.equals(ChessPieceClass.QUEEN) && !this._pieceClass.equals(ChessPieceClass.KING))
            letter = letter.toLowerCase();
        Font font = new Font("Tahoma", Font.BOLD, 16);
        FontRenderContext frc = new FontRenderContext(null, true, true);
        Rectangle2D bounds = font.getStringBounds(letter, frc);
        int width = (int)bounds.getWidth();
        int height = (int)bounds.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(letter, 0.0f, height * 0.75f);
        g.dispose();
        this._icon = image;
    }
    
    protected List<ChessField> moveStraight(int steps, boolean canFly, ChessField startStep, boolean occuped, boolean self) {
        List<ChessField> movements = new ArrayList();
        if (this._field == null)
            return movements;
        for (int i = 0; i < 18; i++) {
            ChessField curStep;
            Vector3 curStepCoords = startStep.getCoordinates().clone();
            boolean evenStep = true;
            if (this.getField().isTriangular() && i > 11)
                continue;
            int stepsLeft = steps > 0 ? steps : 19;
            do {
                makeNextStraightStep(i, curStepCoords, evenStep);
                curStep = this._field.getBoard().getField(curStepCoords);
                if (curStep == null)
                    break;
                if (
                    !movements.contains(curStep)
                    && (
                        occuped && curStep.getPiece() != null
                        && (
                            self && curStep.getPiece().getPlayer().equals(this._player)
                            || !self && !curStep.getPiece().getPlayer().equals(this._player)
                        )
                        || !occuped && curStep.getPiece() == null
                    )
                )
                    movements.add(curStep);
                if (curStep.isTriangular())
                    evenStep = !evenStep;
                stepsLeft--;
            } while ((curStep.getPiece() == null || canFly) && stepsLeft > 0);
        }
        return movements;
    }
    
    protected void makeNextStraightStep(int direction, Vector3 currentStep, boolean evenStep) {
        this.makeNextStraightStep(direction, currentStep, evenStep, false);
    }
    
    protected void makeNextStraightStep(int direction, Vector3 currentStep, boolean evenStep, boolean real) {
        switch (direction) {
            case 0:
                if (evenStep)
                    currentStep.incX(real);
                else
                    currentStep.decY(real);
                break;
            case 1:
                if (evenStep)
                    currentStep.decX(real);
                else
                    currentStep.incY(real);
                break;
            case 2:
                if (evenStep)
                    currentStep.incX(real);
                else
                    currentStep.decZ(real);
                break;
            case 3:
                if (evenStep)
                    currentStep.decX(real);
                else
                    currentStep.incZ(real);
                break;
            case 4:
                if (evenStep)
                    currentStep.incY(real);
                else
                    currentStep.decZ(real);
                break;
            case 5:
                if (evenStep)
                    currentStep.decY(real);
                else
                    currentStep.incZ(real);
                break;
            case 6:
                if (!evenStep)
                    currentStep.incX(real);
                else
                    currentStep.decY(real);
                break;
            case 7:
                if (!evenStep)
                    currentStep.decX(real);
                else
                    currentStep.incY(real);
                break;
            case 8:
                if (!evenStep)
                    currentStep.incX(real);
                else
                    currentStep.decZ(real);
                break;
            case 9:
                if (!evenStep)
                    currentStep.decX(real);
                else
                    currentStep.incZ(real);
                break;
            case 10:
                if (!evenStep)
                    currentStep.incY(real);
                else
                    currentStep.decZ(real);
                break;
            case 11:
                if (!evenStep)
                    currentStep.decY(real);
                else
                    currentStep.incZ(real);
                break;
            case 12:
                if (currentStep.getZ(real) < -2) {
                    currentStep.decX(real);
                    currentStep.incY(real);
                } else {
                    currentStep.setZ(-5);
                }
                break;
            case 13:
                if (currentStep.getX(real) < -2) {
                    currentStep.decY(real);
                    currentStep.incZ(real);
                } else {
                    currentStep.setX(-5);
                }
                break;
            case 14:
                if (currentStep.getY(real) < -2) {
                    currentStep.decZ(real);
                    currentStep.incX(real);
                } else {
                    currentStep.setY(-5);
                }
                break;
            case 15:
                if (currentStep.getZ(real) < -2) {
                    currentStep.incX(real);
                    currentStep.decY(real);
                } else {
                    currentStep.setZ(-5);
                }
                break;
            case 16:
                if (currentStep.getX(real) < -2) {
                    currentStep.incY(real);
                    currentStep.decZ(real);
                } else {
                    currentStep.setX(-5);
                }
                break;
            case 17:
                if (currentStep.getY(real) < -2) {
                    currentStep.incZ(real);
                    currentStep.decX(real);
                } else {
                    currentStep.setY(-5);
                }
                break;
        }
    }
    
    protected List<ChessField> moveDiagonal(int steps, boolean occuped, boolean self) {
        List<ChessField> movements = new ArrayList();
        if (this._field == null)
            return movements;
        for (int i = 0; i < 18; i++) {
            ChessField curStep;
            Vector3 curStepCoords = this._field.getCoordinates().clone();
            boolean evenStep = true;
            int stepsLeft = steps > 0 ? steps : 10;
            do {
                makeNextDiagonalStep(i, curStepCoords, evenStep);
                curStep = this._field.getBoard().getField(curStepCoords);
                if (curStep == null)
                    break;
                    if (
                        !movements.contains(curStep)
                        && (
                            occuped && curStep.getPiece() != null
                            && (
                                self && curStep.getPiece().getPlayer().equals(this._player)
                                || !self && !curStep.getPiece().getPlayer().equals(this._player)
                            )
                            || !occuped && curStep.getPiece() == null
                        )
                    )
                        movements.add(curStep);
                if (curStep.isTriangular())
                    evenStep = !evenStep;
                else
                    evenStep = i > 5;
                stepsLeft--;
            } while (curStep.getPiece() == null && stepsLeft > 0);
        }
        return movements;
    }
    
    protected void makeNextDiagonalStep(int direction, Vector3 currentStep, boolean evenStep) {
        this.makeNextDiagonalStep(direction, currentStep, evenStep, false);
    }
    
    protected void makeNextDiagonalStep(int direction, Vector3 currentStep, boolean evenStep, boolean real) {
        switch (direction) {
            case 0:
                if (evenStep && currentStep.getX(real) > -3) {
                    currentStep.incX(real);
                } else {
                    currentStep.incX(real);
                    currentStep.decY(real);
                    currentStep.decZ(real);
                }
                break;
            case 1:
                if (evenStep && currentStep.getY(real) > -3) {
                    currentStep.incY(real);
                } else {
                    currentStep.decX(real);
                    currentStep.incY(real);
                    currentStep.decZ(real);
                }
                break;
            case 2:
                if (evenStep && currentStep.getZ(real) > -3) {
                    currentStep.incZ(real);
                } else {
                    currentStep.decX(real);
                    currentStep.decY(real);
                    currentStep.incZ(real);
                }
                break;
            case 3:
                if (evenStep && currentStep.getX(real) > -2) {
                    currentStep.decX(real);
                } else {
                    if (currentStep.getX(real) > -2) {
                        currentStep.decX(real);
                        currentStep.incY(real);
                        currentStep.incZ(real);
                    } else {
                        currentStep.decX(real);
                        currentStep.decY(real);
                        currentStep.incZ(real);
                    }
                }
                break;
            case 4:
                if (evenStep && currentStep.getY(real) > -2) {
                    currentStep.decY(real);
                } else {
                    if (currentStep.getY(real) > -2) {
                        currentStep.incX(real);
                        currentStep.decY(real);
                        currentStep.incZ(real);
                    } else {
                        currentStep.decX(real);
                        currentStep.decY(real);
                        currentStep.incZ(real);
                    }
                }
                break;
            case 5:
                if (evenStep && currentStep.getZ(real) > -2) {
                    currentStep.decZ(real);
                } else {
                    if (currentStep.getZ(real) > -2) {
                        currentStep.incX(real);
                        currentStep.incY(real);
                        currentStep.decZ(real);
                    } else {
                        currentStep.decX(real);
                        currentStep.incY(real);
                        currentStep.decZ(real);
                    }
                }
                break;
            case 6:
                if (!evenStep && currentStep.getX(real) > -3) {
                    currentStep.incX(real);
                } else {
                    currentStep.incX(real);
                    currentStep.decY(real);
                    currentStep.decZ(real);
                }
                break;
            case 7:
                if (!evenStep && currentStep.getY(real) > -3) {
                    currentStep.incY(real);
                } else {
                    currentStep.decX(real);
                    currentStep.incY(real);
                    currentStep.decZ(real);
                }
                break;
            case 8:
                if (!evenStep && currentStep.getZ(real) > -3) {
                    currentStep.incZ(real);
                } else {
                    currentStep.decX(real);
                    currentStep.decY(real);
                    currentStep.incZ(real);
                }
                break;
            case 9:
                if (!evenStep && currentStep.getX(real) > -2) {
                    currentStep.decX(real);
                } else {
                    if (currentStep.getX(real) > -2) {
                        currentStep.decX(real);
                        currentStep.incY(real);
                        currentStep.incZ(real);
                    } else {
                        currentStep.decX(real);
                        currentStep.decY(real);
                        currentStep.incZ(real);
                    }
                }
                break;
            case 10:
                if (!evenStep && currentStep.getY(real) > -2) {
                    currentStep.decY(real);
                } else {
                    if (currentStep.getY(real) > -2) {
                        currentStep.incX(real);
                        currentStep.decY(real);
                        currentStep.incZ(real);
                    } else {
                        currentStep.decX(real);
                        currentStep.decY(real);
                        currentStep.incZ(real);
                    }
                }
                break;
            case 11:
                if (!evenStep && currentStep.getZ(real) > -2) {
                    currentStep.decZ(real);
                } else {
                    if (currentStep.getZ(real) > -2) {
                        currentStep.incX(real);
                        currentStep.incY(real);
                        currentStep.decZ(real);
                    } else {
                        currentStep.decX(real);
                        currentStep.incY(real);
                        currentStep.decZ(real);
                    }
                }
                break;
            case 12:
                if (evenStep && currentStep.getX(real) > -2) {
                    currentStep.decX(real);
                } else {
                    if (currentStep.getX(real) > -2) {
                        currentStep.decX(real);
                        currentStep.incY(real);
                        currentStep.incZ(real);
                    } else {
                        currentStep.decX(real);
                        currentStep.incY(real);
                        currentStep.decZ(real);
                    }
                }
                break;
            case 13:
                if (evenStep && currentStep.getY(real) > -2) {
                    currentStep.decY(real);
                } else {
                    if (currentStep.getY(real) > -2) {
                        currentStep.incX(real);
                        currentStep.decY(real);
                        currentStep.incZ(real);
                    } else {
                        currentStep.incX(real);
                        currentStep.decY(real);
                        currentStep.decZ(real);
                    }
                }
                break;
            case 14:
                if (evenStep && currentStep.getZ(real) > -2) {
                    currentStep.decZ(real);
                } else {
                    if (currentStep.getZ(real) > -2) {
                        currentStep.incX(real);
                        currentStep.incY(real);
                        currentStep.decZ(real);
                    } else {
                        currentStep.incX(real);
                        currentStep.decY(real);
                        currentStep.decZ(real);
                    }
                }
                break;
            case 15:
                if (!evenStep && currentStep.getX(real) > -2) {
                    currentStep.decX(real);
                } else {
                    if (currentStep.getX(real) > -2) {
                        currentStep.decX(real);
                        currentStep.incY(real);
                        currentStep.incZ(real);
                    } else {
                        currentStep.decX(real);
                        currentStep.incY(real);
                        currentStep.decZ(real);
                    }
                }
                break;
            case 16:
                if (!evenStep && currentStep.getY(real) > -2) {
                    currentStep.decY(real);
                } else {
                    if (currentStep.getY(real) > -2) {
                        currentStep.incX(real);
                        currentStep.decY(real);
                        currentStep.incZ(real);
                    } else {
                        currentStep.incX(real);
                        currentStep.decY(real);
                        currentStep.decZ(real);
                    }
                }
                break;
            case 17:
                if (!evenStep && currentStep.getZ(real) > -2) {
                    currentStep.decZ(real);
                } else {
                    if (currentStep.getZ(real) > -2) {
                        currentStep.incX(real);
                        currentStep.incY(real);
                        currentStep.decZ(real);
                    } else {
                        currentStep.incX(real);
                        currentStep.decY(real);
                        currentStep.decZ(real);
                    }
                }
                break;
        }
    }
}