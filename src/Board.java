import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

class Board {
    private final int squareSize; // Size of each square
    private final int boardSize;
    private BufferedImage tileImage;

    private BufferedImage mirrorImage;

    Mirror[][] tiles = new Mirror[5][5];

    private Point ghostTilePosition = new Point(-1, -1); // Initialize to an off-screen position
    private Mirror selectedTile;

    int mouseX = 0;
    int mouseY = 0;

    public boolean placing = false;

    public Board(int boardSize, int squareSize) throws IOException {
        this.boardSize = boardSize;
        this.squareSize = squareSize;
        try {
            this.tileImage = ImageIO.read(new File("src/assets/empty.png"));
            this.mirrorImage = ImageIO.read(new File("src/assets/doubleMirror.png"));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could not load image file!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Initialize the tiles list after mirrorImage has been loaded
        tiles[0][0] = new Mirror(mirrorImage);
        selectedTile = new Mirror(mirrorImage);
    }

    //TODO Make a laser class so we can draw the laser on the board.
    //TODO Draw the Laser when the player wants to test the level.

    public void setGhostTilePosition(int x, int y) {
        this.ghostTilePosition = getTile(x, y);
    }
    public void setMousePos(int x, int y) {
        mouseX = x;
        mouseY = y;

        if (placing) {
            setGhostTilePosition(x, y);
        } else {
            ghostTilePosition = new Point(-1, -1);
        }
    }

    public void setPlacing() {
        this.placing = !this.placing;
        if (placing) {
            setGhostTilePosition(mouseX, mouseY);
        } else {
            ghostTilePosition = new Point(-1, -1);
        }

    }



    public Point getTile(int x, int y) {

        return new Point(Math.min(x / squareSize,boardSize-1),  Math.min(y / squareSize,boardSize-1));
    }

    public void drawBoard(Graphics g) {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                g.drawImage(tileImage, col * squareSize, row * squareSize, squareSize, squareSize, null);
                if (tiles[row][col] != null) {
                    g.drawImage(tiles[row][col].image, row * squareSize, col * squareSize, squareSize, squareSize, null);
                }
            }
        }

        if (selectedTile != null && ghostTilePosition.x >= 0 && ghostTilePosition.y >= 0) {
            g.drawImage(selectedTile.image, ghostTilePosition.x * squareSize, ghostTilePosition.y * squareSize, squareSize, squareSize, null);
        }

    }

    public void addTile(int x, int y) {
        Point p = getTile(x, y);
        System.out.println("Tile clicked: " + p);
        tiles[p.x][p.y] = new Mirror(selectedTile.image);
        //printTiles();
    }

    public void removeTile(int x, int y) {
        Point p = getTile(x, y);
        System.out.println("Tile removed: " + p);
        tiles[p.x][p.y] = null;
        //printTiles();
    }

    public void printTiles() {
        System.out.println("Tiles:");

        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (tiles[i][j] != null) {
                    System.out.print(tiles[i][j]);
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void rotateSelectedTile() {
        if (selectedTile != null) {
            selectedTile.rotation = (selectedTile.rotation + 1) % 4;
            selectedTile.image = ImageHandler.rotateImage(selectedTile.image, 90);
        }
    }

    public void setSelectedTile() {
        selectedTile = new Mirror(mirrorImage);
    }


}