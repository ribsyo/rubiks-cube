import java.util.HashMap;
import java.util.Map;
public class RubiksCube {
Map<Integer, int[]> sides = new HashMap<Integer, int[]>();

    int[][][] rubiksCube = new int[6][3][3];
    int[][] facing = new int[6][4]; 
    //facing[] = current face, facing[]{left, up, right, down}
    //side 5 is above 1
    //side 6 is below 1
    RubiksCube(){

        for(int i = 0; i < rubiksCube.length; i++){
            for(int j = 0; j < rubiksCube[i].length; j++){
                for(int k = 0; k < rubiksCube[i][j].length; k++){

                rubiksCube[i][j][k] = i + 1;   

                }
            }
        } 

        for(int i = 0; i < rubiksCube.length; i++){

            System.out.println("side: " + (i + 1));

            for(int j = 0; j < rubiksCube[i].length; j++){

                System.out.println(rubiksCube[i][j][0] + " " + rubiksCube[i][j][1] + " " + rubiksCube[i][j][2]);

            }
        }

    }

    private void turnFaceLeft(int face){

        //1 1 > 1 2 >  1 3 > 2 3 > 3 3 > 3 2 > 3 1 > 2 1 > 1 1
        int row = 0;
        int column = 0;
        int nextRow = 0;
        int nextColumn = 0;
        int target = 3;
        int add = 1;

        //swap main face
        int temp = rubiksCube[face][row][column];
        for(int i = 0; i < 7; i++){
            rubiksCube[face][row][column] = rubiksCube[face][nextRow][nextColumn];
            if(column < target || (target == 0 && column > 0)){
                column += add;
                if(column == target){
                    nextRow += add;
                }
                else{
                    nextColumn = column + add;
                }
            }
            else if(row < target || (target == 0 && row > 0)){
                row += add;
                if(column == target){
                    nextColumn += add;
                }
                else{
                    nextRow = row + add;
                }
            }
            else{
                add = -1;
                target = 0;
            }
        }
        rubiksCube[face][row][column] = temp;

        //swap connected sides
        //left side[x][2] < top depends < right side[x][0] < bottom depends;
        //Possibility 1: 
        //side(1-4), top, bottom: 1, [2][x], [2][x]; 2, [x][2], [x][0]; 3, [0][x], [0][x]; 4, [x][0], [x][2];
        //Side 5(up) left:4 ; up:3 ; right:2 ; down:1
        //Side 6(down) left:2 ; up:3 ; right:4 ; down:1
        
        //hashmap needed
        //Possibility 2: side(1-4), top, bottom: 1, [2][x], [0][x]; 2, [x][2], [x][2]; 3, [0][x], [2][x]; 4, [x][0], [x][0];
        //side 5(up) left:4 ; up:3 ; right:2 ; down:1
        //side 6(down) left:2 ; up:1 ; right:4 ; down:3

    }


    private void turnFaceRight(int face){
        turnFaceLeft(face);
        turnFaceLeft(face);
        turnFaceLeft(face);
    }


}
