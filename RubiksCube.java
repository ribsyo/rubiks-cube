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
        int nextColumn = 1;
        int target = 2;
        int add = 1;
        face -= 1;
        //swap main face
        int temp = rubiksCube[face][row][column];
        for(int i = 0; i < 8; i++){
            rubiksCube[face][row][column] = rubiksCube[face][nextRow][nextColumn];
            if(column < target || (target == 0 && column > 0)){
                column += add;
                if(nextColumn != target){
                    nextColumn += add;
                }
                else{
                    nextRow = row + add;
                }
            }
            else if(row < target || (target == 0 && row > 0)){
                row += add;
                if(nextRow != target){
                    nextRow += add;
                }
                else{
                    nextColumn = column - add;
                }
            }
            else{
                add = -1;
                target = 0;
            }
        }
        rubiksCube[face][1][0] = temp;

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

        //if face is 1 2 3 or 4
        //else face is 5 or 6
        if(face < 4){

            int leftFace = face - 1;
            if(leftFace < 0)face = 3;

            int rightFace = face + 1;

            if(rightFace > 3)rightFace = 0;
            int tempEdge = 0;

            int tempTracker = -2;

            //face(face - 1)[2][x] goes to (top) goes to face(face + 1) goes to (bottom) goes to face(-1)

            int[] tempArr = {rubiksCube[leftFace][2][0],rubiksCube[leftFace][2][1],rubiksCube[leftFace][2][2]};//store leftFace

            //side(1-4), top, bottom: 1, [2][x], [0][x]; 2, [x][2], [x][2]; 3, [0][x], [2][x]; 4, [x][0], [x][0];
            if(face%2 == 0){//side 1 and 3
                if(face == 0)tempEdge = 2;
                else tempTracker = 0;

                for(int i = 0; i < 3; i++){
                    rubiksCube[leftFace][i][2] = rubiksCube[6][tempEdge][Math.abs(tempTracker + i)];
                }

                
                if(tempEdge == 0){
                    tempEdge = 2;
                    tempTracker = 0;
                }
                else {
                    tempEdge = 0;
                    tempTracker = -2;
                }

                for(int i = 0; i < rubiksCube[face][5].length; i++){//bottom edge
                    rubiksCube[5][tempEdge][i] = tempArr[Math.abs(tempTracker + i)];
                }
            }
            else{//side 2 and 4
                if(face == 1)tempEdge = 2;



                for(int i = 0; i < rubiksCube[face][5].length; i++){
                    rubiksCube[5][i][tempEdge] = tempArr[2 - i];
                }
            }
        }
        else{

        }

    }


    private void turnFaceRight(int face){
        turnFaceLeft(face);
        turnFaceLeft(face);
        turnFaceLeft(face);
    }

    public void printFace(int face){
        String temp = "Face: " + face + "\n";
        face -= 1;
        for(int j = 0; j < rubiksCube[face].length; j++){
            for(int i = 0; i < rubiksCube[face][j].length; i++){
                temp += rubiksCube[face][j][i] + " ";
            }
            temp += "\n";
        }
        System.out.println(temp);
    }
}
