//Imports
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import java.awt.Container;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Life implements MouseListener, ActionListener, Runnable {
  //vars
  int cellNumber = 25;
  boolean[][] cells = new boolean[cellNumber][cellNumber];
  JFrame frame = new JFrame("Life simulation");
  LifePanel panel = new LifePanel(cells);
  Container south = new Container();
  JButton step = new JButton("Step");
  JButton start = new JButton("Start");
  JButton stop = new JButton("Stop");
  boolean running = false;
  
  public Life() {
    frame.setSize(400,400);
    //************Border Layout*********************
    frame.setLayout(new BorderLayout());
    frame.add(panel, BorderLayout.CENTER);
    panel.addMouseListener(this);
    //South Container
    south.setLayout(new GridLayout(1,3));
    south.add(step);
    step.addActionListener(this);
    south.add(start);
    start.addActionListener(this);
    south.add(stop);
    stop.addActionListener(this);
    frame.add(south, BorderLayout.SOUTH);
    
    //closes program when you close window
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    new Life();
  }

  //this method ensures that Main will call this class
  public void Life() {
  }

  //Goes with Action Listener
  @Override
  public void actionPerformed(ActionEvent event) {
    if (event.getSource().equals(step)) {
      System.out.println("Step");
      step();
    }
    if (event.getSource().equals(start)) { //consistently runs step
      System.out.println("Start");
      if (running == false) {
        running = true;
        Thread t = new Thread(this);
        t.start(); //eventually calles run method
      }
    }
    if (event.getSource().equals(stop)) {
      System.out.println("Stop");
      running = false;
    }
  }

  public void step() {
    boolean[][] nextCells = new boolean[cells.length][cells[0].length];
    for (int row = 0; row < cells.length; row++) {
      for (int column = 0; column < cells[0].length; column++) {
        int neighborCount = 0;
        //Check cells for neighbords
        if (row > 0 && column > 0 && cells[row-1][column-1] == true) { //above,left
          neighborCount++;
        }
        if (row > 0 && cells[row-1][column] == true) { //above
          neighborCount++;
        }
        if (row > 0 && column < cells[0].length-1 && cells[row-1][column+1] == true) {
          neighborCount++;
        }
        if (column > 0 && cells[row][column-1] == true) { //left
          neighborCount++;
        }
        if (column < cells[0].length-1 && cells[row][column+1] == true) { //right
          neighborCount++;
        }
        if (row < cells.length-1 && column > 0 && cells[row+1][column-1] == true) { //below,left
          neighborCount++;
        }
        if (row < cells.length-1 && cells[row+1][column] == true) { //below
          neighborCount++;
        }
        if (row < cells.length-1 && column < cells[0].length-1 && cells[row+1][column+1]) { //below,right
          neighborCount++;
        }
        //Rules of Life
        if (cells[row][column] == true) { //player alive
          if (neighborCount == 2 || neighborCount == 3) {
            nextCells[row][column] = true; //alive next time
          } else if (neighborCount >= 4) {
            nextCells[row][column] = false; //dead next tim
          } else {
            nextCells[row][column] = false; //dead next time
          }
        } else { //player dead right now
          if (neighborCount == 3) {
            nextCells[row][column] = true; //alive next time
          } else {
            nextCells[row][column] = false; //dead next time
          }
        }
      }
    }
     //Update Variables & repaint
    cells = nextCells;
    panel.setCells(nextCells);
    frame.repaint();
  }

  //Goes with mouseListener
  @Override
  public void mouseClicked(MouseEvent event) {
  	// TODO Auto-generated method stub
  }
  
  @Override
  public void mousePressed(MouseEvent event) {
  	// TODO Auto-generated method stub
  }
  
  @Override
  public void mouseReleased(MouseEvent event) {
    //Tells you row and column of where you clicked
    double width = (double)panel.getWidth()/cells[0].length;
    double height = (double)panel.getHeight()/cells.length;
    int column = Math.min(cells[0].length-1, (int)(event.getX()/width)); //makes sure computer doesn't rount to ten
    int row = Math.min(cells.length-1, (int)(event.getY()/height));
    
    //check if cell is living or dead & reverse it
    cells[row][column] = !cells[row][column];
    frame.repaint();
  }
  
  @Override
  public void mouseEntered(MouseEvent event) {
  	// TODO Auto-generated method stub
  }
  
  @Override
  public void mouseExited(MouseEvent event) {
  	// TODO Auto-generated method stub
  }

  //Goes with Runnable
  @Override
  public void run() {
    while(running == true) {
      step();
      try {
        Thread.sleep(500); //slows it down
      } catch (Exception ex) { //in case thread causes problems in code
        ex.printStackTrace();
      }
    }
  }
}