import java.util.*;

public class Solver {

    private static Random random = new Random();

    public static boolean solve(int[][] b){

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){

                if(b[i][j]==0){
                    List<Integer> nums = new ArrayList<>();
                    for(int n=1;n<=9;n++) nums.add(n);
                    Collections.shuffle(nums);

                    for(int num : nums){

                        if(isSafe(b,i,j,num)){
                            b[i][j]=num;

                            if(solve(b))
                                return true;

                            b[i][j]=0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isSafe(int[][] b,int r,int c,int n){

        for(int i=0;i<9;i++)
            if(b[r][i]==n || b[i][c]==n)
                return false;

        int sr=r-r%3, sc=c-c%3;

        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(b[sr+i][sc+j]==n)
                    return false;

        return true;
    }

    // 🟢 Generate Random Puzzle
    public static int[][] generatePuzzle(int emptyCells){

        int[][] board = new int[9][9];

        solve(board); // generate full board

        // remove random cells
        while(emptyCells > 0){
            int r = random.nextInt(9);
            int c = random.nextInt(9);

            if(board[r][c] != 0){
                board[r][c] = 0;
                emptyCells--;
            }
        }

        return board;
    }
}
