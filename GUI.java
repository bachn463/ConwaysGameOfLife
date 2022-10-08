import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;
import javax.swing.Timer;

public class GUI implements ActionListener{
  Timer t = new Timer(500, this);
	JFrame window = new JFrame("Game of Life Conway");
	JPanel borderPanel = new JPanel(new BorderLayout());
	
	JPanel mainPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
  JButton startButton = new JButton("Start");
	JButton nextButton = new JButton("Next/Stop");
	Random rand = new Random();

	JPanel[][] grid;   
  JPanel[][] gridTemp;

	public GUI (int size) {
		mainPanel.setLayout(new GridLayout(size, size, 2, 2));
    mainPanel.setBackground(Color.GRAY);
		grid = new JPanel[size][size];
		gridTemp = new JPanel[size][size];
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				grid[i][j] = new JPanel();
				gridTemp[i][j] = new JPanel();

				mainPanel.add(grid[i][j]);
				grid[i][j].setBackground(Color.WHITE);
				gridTemp[i][j].setBackground(Color.WHITE);

        int colorChoice = rand.nextInt(10);
        if(colorChoice >= 2) {
          grid[i][j].setBackground(Color.WHITE);
          gridTemp[i][j].setBackground(Color.WHITE);
        }
        else {
          grid[i][j].setBackground(Color.BLACK);
          gridTemp[i][j].setBackground(Color.BLACK);
        }
			}
		}

		borderPanel.add(mainPanel, BorderLayout.CENTER);
		borderPanel.add(buttonPanel, BorderLayout.SOUTH);
    buttonPanel.add(startButton);
		buttonPanel.add(nextButton);
    startButton.addActionListener(this);
    nextButton.addActionListener(this);
		
		window.add(borderPanel);
    window.setSize(700, 700);
		window.setVisible(true);
	}
		
  public int neighbors(int x, int y) {
    int neighborsCount = 0;
    for(int i = -1; i < 2; i++) {
      for(int j = -1; j < 2; j++) {
        if(x+i >= 0 && x+i < grid.length) {
          if(y+j >= 0 && y+j < grid[0].length) {
            if(grid[x+i][y+j].getBackground() == Color.BLACK) {
              neighborsCount++;
            }
          }
        }
      }
    }
    return neighborsCount-1;
  }
  

  public void gridReplacement() {
    for(int i = 0; i< grid.length; i++) {
      for(int j = 0; j < grid[0].length; j++) {
        if(grid[i][j].getBackground() == Color.BLACK) {
          if(neighbors(i, j) == 2 || neighbors(i, j) == 3) {
            gridTemp[i][j].setBackground(Color.BLACK);
          }
          else if(neighbors(i, j) <= 1) {
            gridTemp[i][j].setBackground(Color.WHITE);
          }
          else if(neighbors(i, j) >= 4) {
            gridTemp[i][j].setBackground(Color.WHITE);
          }
        }
        if(grid[i][j].getBackground() == Color.WHITE) {
          if(neighbors(i, j)+1 == 3) {
            gridTemp[i][j].setBackground(Color.BLACK);
          }
        }
      }
    }
  }

  public void repaint() {
    for(int i = 0; i< grid.length; i++) {
      for(int j = 0; j < grid[0].length; j++) {
        grid[i][j].setBackground(gridTemp[i][j].getBackground());
      }
    }
  }

  public void actionPerformed (ActionEvent e) {
    gridReplacement();
    
    if(e.getSource() == startButton) {
      t.start();
    }

    repaint();

    if(e.getSource() == nextButton) {
      t.stop();
    }
  }
}
