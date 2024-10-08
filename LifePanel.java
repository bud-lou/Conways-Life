//Imports
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.lang.Math;

//This class draws the grid and cells
public class LifePanel extends JPanel {
  //Vars
  boolean[][] cells;
  double width;
  double height;

  public LifePanel(boolean[][] in) {
    cells = in; //getting information from cell array in Life.java
  }

  public void setCells(boolean[][] newcells) {
    cells = newcells; //updates variable
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g); //clears the screen
    
    //Calculate width and height
    width = (double)this.getWidth()/cells[0].length;  
    height = (double)this.getHeight()/cells.length;

    //Draw the Cells
    g.setColor(Color.BLUE);
    for (int row=0; row < cells.length; row++) {
      for (int column = 0; column < cells[0].length; column++) {
        if (cells[row][column] == true) {
          g.fillRect((int)Math.round(column*width), (int)Math.round(row*height), (int)Math.round(width), (int)Math.round(height));
        }
      }
    }
    
    //Draw the Grid
    g.setColor(new Color(129, 201, 191)); //rgb green value
    for (int x = 0; x < cells[0].length+1; x++) {
      g.drawLine((int)Math.round(x*width), 0, (int)Math.round(x*width), this.getHeight());
    }
    for (int y = 0; y < cells[0].length+1; y++) {
      g.drawLine(0, (int)Math.round(y*height), this.getWidth(), (int)Math.round(y*height));
    }
  }
  
}