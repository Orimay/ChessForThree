package chessforthree;

import javax.swing.JFrame;

/**
 *
 * @author Dmitrii
 */
public class ChessForThree extends JFrame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame frame = new ChessForThree();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
        ChessManager manager = ChessManager.getInstance(ChessPlayerClass.ORANGE);
        ChessBoard board = manager.getChessBoard();
        board.setSize(800, 600);
        frame.add(board);
    }
}