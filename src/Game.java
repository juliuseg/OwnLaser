import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

public class Game extends JPanel {
    private final Board board;

    public Game(){
        try {
            int boardSize = 5;
            int squareSize = 120;
            int toolbarHeight = (int) Math.round(1.5*squareSize);
            setPreferredSize(new Dimension(boardSize * squareSize, (boardSize) * squareSize+toolbarHeight));
            this.board = new Board(boardSize, squareSize);
        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception as appropriate for your application
            throw new RuntimeException("Failed to initialize the game board", e);
        }



        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON1){
                    board.addTile(e.getX(), e.getY());

                }
                else if (e.getButton()==MouseEvent.BUTTON3) {
                    board.removeTile(e.getX(), e.getY());
                }
                repaint();
            }


        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                board.setMousePos(e.getX(), e.getY());
                repaint();
            }
        });

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    System.out.println("Rotating the selected tile");
                    board.rotateSelectedTile();

                    repaint();
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    board.setPlacing();

                    repaint();
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.drawBoard(g);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chess Board");
            Game chessBoard = new Game();
            frame.add(chessBoard);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null); // Center the window
            frame.setVisible(true);
        });
    }
}