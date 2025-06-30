import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

import javax.swing.*;

public class FallingSand extends JPanel implements MouseListener, MouseMotionListener {
    
    private int[][] grid; // current grid state
    private int[][] nextGrid; // next frame of animation
    private int w = 6; // width of each cell
    private int cols; // number of columns
    private int rows; // number of rows
    private int width = 480; // width of panel
    private int height = 400; // height of panel
    private Random rand = new Random();
    Color[] colors = {
        new Color(255, 0, 0),      // Bright Red
        new Color(0, 255, 0),      // Bright Green
        new Color(0, 0, 255),      // Bright Blue
        new Color(255, 165, 0),    // Bright Orange
        new Color(255, 0, 255),    // Bright Magenta
        new Color(0, 255, 255),    // Bright Cyan
        new Color(255, 255, 0),    // Bright Yellow
        new Color(255, 20, 147),   // Deep Pink
        new Color(75, 0, 130),     // Indigo
        new Color(255, 105, 180),  // Hot Pink
        new Color(0, 191, 255),    // Deep Sky Blue
        new Color(255, 69, 0),     // Red Orange
        new Color(124, 252, 0),    // Lawn Green
        new Color(255, 20, 147),   // Deep Pink
        new Color(255, 0, 128),    // Neon Pink
        new Color(0, 255, 128),    // Neon Green
        new Color(255, 0, 0),      // Neon Red
        new Color(255, 255, 224),  // Neon Yellow
        new Color(0, 0, 128),      // Navy
        new Color(255, 255, 102),  // Neon Lemon Yellow
        new Color(51, 204, 255),    // Light Blue
    };

    private Color currentColor = colors[0];

    public FallingSand(){
        setUp();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void setUp(){
        // setBackground(Color.BLACK);
        cols = width / w;
        rows = height / w;

        grid = new int[cols][rows];
        nextGrid = new int[cols][rows]; // create the next grid
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        drawGrid(g); // Draw the current grid
    }

    private void drawGrid(Graphics g) {
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                //g.setColor(Color.WHITE); // Border color
                // g.drawRect(x * w, y * w, w, w); // Draw cell border

                if (grid[x][y] > 0) { // if there's sand particle
                    g.setColor(colors[grid[x][y] - 1]); // set color based on grid value
                    g.fillRect(x * w, y * w, w, w); // Draw the square
                    
                }
            }
        }
    }

    public void update() {
        // reset nextGrid for each update
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                nextGrid[x][y] = 0; // clear nextGrid
            }
        }



        // check every cell
        for (int y = rows - 1; y >= 0; y--) {
            for (int x = 0; x < cols; x++) {
                // get current state
                int state = grid[x][y];

                // if its a piece of sand
                if (state > 0) {
                    // check what's below
                    int below = -1;
                    if (y + 1 < rows) {
                        below = grid[x][y + 1]; // check whats below
                    }

                    int direction;
                    if (rand.nextBoolean()) {
                        direction = 1; // choose right
                    } else {
                        direction = -1; // choose left
                    }

                    // check below left and right
                    int belowA = -1;
                    int belowB = -1;

                    if (withinCols(x + direction) && y + 1 < rows) {
                        belowA = grid[x + direction][y + 1];
                    }

                    if (withinCols(x - direction) && y + 1 < rows) {
                        belowB = grid[x - direction][y + 1];
                    }

                    // can it fall below?
                    if (below == 0 && y + 1 < rows) {
                        nextGrid[x][y + 1] = state;
                    } 
                    // can it fall left?
                    else if (y + 1 < rows && withinCols(x + direction) && belowA == 0) { 
                        nextGrid[x + direction][y + 1] = state; // fall left
                    } 
                    // can it fall right?

                    else if (y + 1 < rows && withinCols(x - direction) && belowB == 0) {
                        nextGrid[x - direction][y + 1] = state; // fall right
                    } else {
                        nextGrid[x][y] = state; // stay put
                    }
                }
            }
        }

        // update the grid for the next iteration
        grid = nextGrid;
        nextGrid = new int[cols][rows]; // reset next grid for the next frame
    }

    private boolean withinCols(int index){
        return index >= 0 && index < cols; // check if index is within column bounds
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //System.out.println("being dragged rn");
        placeSand(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println("mouse pressed");
        currentColor = colors[rand.nextInt(colors.length)];
        placeSand(e);
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    
    private void placeSand(MouseEvent e) {
        int x = e.getX() / w;
        int y = e.getY() / w;

        if (withinCols(x) && y >= 0 && y < rows) {
            grid[x][y] = Arrays.asList(colors).indexOf(currentColor) + 1; // Use currentColor
            repaint();
        }

    }


    @Override
    public void mouseClicked(MouseEvent e) {}
    
    @Override
    public void mouseMoved(MouseEvent e) {}
    
    @Override
    public void mouseEntered(MouseEvent e) {}
    
    @Override
    public void mouseExited(MouseEvent e) {}
}
