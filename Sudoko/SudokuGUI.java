import javax.swing.*;
import java.awt.*;

public class SudokuGUI extends JFrame {

    JTextField[][] cells = new JTextField[9][9];     //creation of 9x9 boxes
    int[][] originalBoard = new int[9][9];   // puzzle
    int[][] solutionBoard = new int[9][9];   // full solution
    public SudokuGUI(){

        setTitle("Sudoku Game");
        setSize(600,650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);    // for closing the window
        setLayout(new BorderLayout());      // layout for the jframe window     it divides south north and all directions buttons at the bottum and girds at the center

        JPanel grid = new JPanel(new GridLayout(9,9));  // jpanel container to hold the components  which create the 9X9 cells


        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){

                cells[i][j] = new JTextField();      // to write the text in the grids
                cells[i][j].setHorizontalAlignment(JTextField.CENTER);     // text center align
                cells[i][j].setFont(new Font("Arial",Font.BOLD,20));      // number font and size
                cells[i][j].setPreferredSize(new Dimension(60,60));  // cells size  & dimensions

                int top = (i % 3 == 0) ? 3 : 1;
                int left = (j % 3 == 0) ? 3 : 1;   // 3 6 and 8 boxes grids have to be bold so we take the indices after we have to bold
                int bottom = (i == 8) ? 3 : 1;
                int right = (j == 8) ? 3 : 1;

                cells[i][j].setBorder(
                        BorderFactory.createMatteBorder(top,left,bottom,right,Color.BLACK)  // bolding the grid parts
                );

                grid.add(cells[i][j]);  // ading cells to panel 
            }
        }

        add(grid,BorderLayout.CENTER); // created grids to the frame at the center to appear at the middle

        JPanel buttons = new JPanel();
        buttons.setBackground(new Color(220,220,220));

        JButton newGame = new JButton("New Game");      // creating buttons
        JButton checkButton = new JButton("Check");
        JButton solveButton = new JButton("Solve");

        newGame.setBackground(new Color(52,152,219));
        newGame.setForeground(Color.WHITE);

        checkButton.setBackground(new Color(46,204,113));
        checkButton.setForeground(Color.WHITE);

        solveButton.setBackground(new Color(231,76,60));
        solveButton.setForeground(Color.WHITE);

        newGame.setFocusPainted(false);
        checkButton.setFocusPainted(false);
        solveButton.setFocusPainted(false);

        newGame.setFont(new Font("Arial",Font.BOLD,14));
        checkButton.setFont(new Font("Arial",Font.BOLD,14));
        solveButton.setFont(new Font("Arial",Font.BOLD,14));

        buttons.add(newGame);
        buttons.add(checkButton);    //adding to jfram
        buttons.add(solveButton);

        add(buttons,BorderLayout.SOUTH);

        newGame.addActionListener(e -> loadNewGame());
        checkButton.addActionListener(e -> checkBoard());
        solveButton.addActionListener(e -> showSolution());

        loadNewGame();
        setVisible(true);
    }

    private void loadNewGame(){

        int[][] puzzle = Solver.generatePuzzle(45);

        // Save puzzle
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
                originalBoard[i][j] = puzzle[i][j];

        // Create solution from puzzle copy
        int[][] temp = new int[9][9];
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
                temp[i][j] = puzzle[i][j];

        Solver.solve(temp);

        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
                solutionBoard[i][j] = temp[i][j];

        // Display puzzle
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){

                if(originalBoard[i][j] != 0){
                    cells[i][j].setText(String.valueOf(originalBoard[i][j]));
                    cells[i][j].setEditable(false);
                    cells[i][j].setBackground(Color.LIGHT_GRAY);
                } else {
                    cells[i][j].setText("");
                    cells[i][j].setEditable(true);
                    cells[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }

    private void checkBoard(){

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){

                if(originalBoard[i][j]==0){

                    String text = cells[i][j].getText();

                    if(!text.isEmpty()){
                        int num = Integer.parseInt(text);

                        if(num != solutionBoard[i][j]){
                            cells[i][j].setBackground(Color.PINK);
                            JOptionPane.showMessageDialog(this,
                                    "Wrong at Row "+(i+1)+" Col "+(j+1));
                            return;
                        }
                    }
                }
            }
        }

        JOptionPane.showMessageDialog(this,"Board looks good!");
    }

    private void showSolution(){

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                cells[i][j].setText(String.valueOf(solutionBoard[i][j]));
                cells[i][j].setEditable(false);
                cells[i][j].setBackground(Color.WHITE);
            }
        }
    }

    public static void main(String[] args){
        new SudokuGUI();
    }
}
