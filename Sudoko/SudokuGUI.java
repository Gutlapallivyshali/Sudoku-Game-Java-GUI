import javax.swing.*;
import java.awt.*;

public class SudokuGUI extends JFrame {

    JTextField[][] cells = new JTextField[9][9];
    int[][] board;

    public SudokuGUI(){

        setTitle("Sudoku Game");
        setSize(600,650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel grid = new JPanel(new GridLayout(9,9));

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){

                cells[i][j] = new JTextField();
                cells[i][j].setHorizontalAlignment(JTextField.CENTER);
                cells[i][j].setFont(new Font("Arial",Font.BOLD,20));
                cells[i][j].setPreferredSize(new Dimension(60,60));

                // 🔥 3x3 Thick Borders
                int top = (i % 3 == 0) ? 3 : 1;
                int left = (j % 3 == 0) ? 3 : 1;
                int bottom = (i == 8) ? 3 : 1;
                int right = (j == 8) ? 3 : 1;

                cells[i][j].setBorder(
                        BorderFactory.createMatteBorder(top,left,bottom,right,Color.BLACK)
                );

                grid.add(cells[i][j]);
            }
        }

        add(grid,BorderLayout.CENTER);

        // 🎨 Button Panel
        JPanel buttons = new JPanel();
        buttons.setBackground(new Color(220,220,220));

        JButton newGame = new JButton("New Game");
        JButton checkButton = new JButton("Check");
        JButton solveButton = new JButton("Solve");

        // Button Colors
        newGame.setBackground(new Color(52,152,219));   // Blue
        newGame.setForeground(Color.WHITE);

        checkButton.setBackground(new Color(46,204,113)); // Green
        checkButton.setForeground(Color.WHITE);

        solveButton.setBackground(new Color(231,76,60)); // Red
        solveButton.setForeground(Color.WHITE);

        // Remove focus border
        newGame.setFocusPainted(false);
        checkButton.setFocusPainted(false);
        solveButton.setFocusPainted(false);

        // Button Font
        newGame.setFont(new Font("Arial",Font.BOLD,14));
        checkButton.setFont(new Font("Arial",Font.BOLD,14));
        solveButton.setFont(new Font("Arial",Font.BOLD,14));

        buttons.add(newGame);
        buttons.add(checkButton);
        buttons.add(solveButton);

        add(buttons,BorderLayout.SOUTH);

        newGame.addActionListener(e -> loadNewGame());
        checkButton.addActionListener(e -> checkBoard());
        solveButton.addActionListener(e -> solveBoard());

        loadNewGame();

        setVisible(true);
    }

    private void loadNewGame(){

        board = Solver.generatePuzzle(45);

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){

                if(board[i][j] != 0){
                    cells[i][j].setText(String.valueOf(board[i][j]));
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

                String text = cells[i][j].getText();

                if(!text.isEmpty()){
                    int num = Integer.parseInt(text);

                    if(!isSafe(i,j,num)){
                        cells[i][j].setBackground(Color.PINK);
                        JOptionPane.showMessageDialog(this,
                                "Wrong move at Row "+(i+1)+" Col "+(j+1));
                        return;
                    } else {
                        if(board[i][j]==0)
                            cells[i][j].setBackground(Color.WHITE);
                    }
                }
            }
        }

        JOptionPane.showMessageDialog(this,"Board looks good!");
    }

    private boolean isSafe(int r,int c,int n){

        for(int i=0;i<9;i++){
            if(i!=c && cells[r][i].getText().equals(String.valueOf(n)))
                return false;
            if(i!=r && cells[i][c].getText().equals(String.valueOf(n)))
                return false;
        }

        int sr=r-r%3, sc=c-c%3;

        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++){
                int row=sr+i,col=sc+j;
                if((row!=r || col!=c) &&
                        cells[row][col].getText().equals(String.valueOf(n)))
                    return false;
            }

        return true;
    }

    private void solveBoard(){

        int[][] temp = new int[9][9];

        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
                temp[i][j] = cells[i][j].getText().isEmpty()
                        ? 0
                        : Integer.parseInt(cells[i][j].getText());

        if(Solver.solve(temp)){
            for(int i=0;i<9;i++)
                for(int j=0;j<9;j++)
                    cells[i][j].setText(String.valueOf(temp[i][j]));
        } else {
            JOptionPane.showMessageDialog(this,"No Solution Found!");
        }
    }

    public static void main(String[] args){
        new SudokuGUI();
    }
}