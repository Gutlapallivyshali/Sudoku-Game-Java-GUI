import java.util.*;

public class Solver {

    private static Random random = new Random();

    public static boolean solve(int[][] b){

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){     // to iterate through the all the cell but recursion only when it is empty cell

                if(b[i][j]==0){
                    List<Integer> nums = new ArrayList<>();   // instead of list we can also use the loop from 1->9 but it is complex due to non randomness
                    for(int n=1;n<=9;n++) nums.add(n);
                    Collections.shuffle(nums);                //shuffling the number for randomness

                    for(int num : nums){                    // loop will iterate until the perfect number is suited

                        if(isSafe(b,i,j,num)){
                            b[i][j]=num;

                            if(solve(b))          // recurison call of now number is setted then the return false in this condition after that  it execute the backtracking
                                return true;

                            b[i][j]=0;      // for backtracking  assigning zero to that cell
                        }
                    }
                    return false;      // if no number is suitable to it then it will return false
                }
            }
        }
        return true;      // after filling all the blocks  final return is true
    }

    private static boolean isSafe(int[][] b,int r,int c,int n){
// Row check
    for(int i=0;i<9;i++){                             //checking for the number already exsiting in the row or coloumn
        if(b[r][i] == n) return false;
    }

    // Column check
    for(int i=0;i<9;i++){
        if(b[i][c] == n) return false;
    }

        int sr=r-r%3, sc=c-c%3;               // the over all matrix is 9x9 but we want to check for every subgrid if our entered in that 3x3 grid or not

        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(b[sr+i][sc+j]==n)      // if the number is already exist in the 3x3 grid we have to return  false
                    return false;

        return true;
    }

    // 🟢 Generate Random Puzzle
    public static int[][] generatePuzzle(int emptyCells){          //the first function called from the sudokoGUI  from this it will go to the solver  function

        int[][] board = new int[9][9];

        solve(board); // generate full board

        // remove random cells
        while(emptyCells > 0){                    // loop will iterate till we will make our assumed empty cells as empty
            int r = random.nextInt(9);        // randomly generated row and coloumn numbers
            int c = random.nextInt(9);

            if(board[r][c] != 0){              //  this condition will not allow the same numbers in the same row or same column
                board[r][c] = 0;
                emptyCells--;
            }
        }

        return board;
    }
}
