import java.util.HashMap;
import java.util.Map;
public class RubiksCube {

    Map<Integer, int[]> sides = new HashMap<Integer, int[]>();

    int[][][] rubiksCube = new int[6][3][3];
    int[][] facing = new int[6][4]; 
    int currentFace = 1;
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
            if(leftFace < 0)leftFace = 3;

            int rightFace = face + 1;

            if(rightFace > 3)rightFace = 0;
            int tempTopEdge = 0;
            int tempBottomEdge = 0;
            int tempTopTracker = -2;
            int tempBottomTracker = -2;

            //face(face - 1)[2][x] goes to (top) goes to face(face + 1) goes to (bottom) goes to face(-1)

            int[] tempArr = {rubiksCube[leftFace][0][2],rubiksCube[leftFace][1][2],rubiksCube[leftFace][2][2]};//store leftFace

            //side(1-4), top, bottom: 1, [2][x], [0][x]; 2, [x][2], [x][2]; 3, [0][x], [2][x]; 4, [x][0], [x][0];
            if(face%2 == 0){//side 1 and 3
                if(face == 0){
                    tempTopEdge = 2;
                    tempBottomTracker = 0;
                }
                else {
                    tempBottomEdge = 2;
                    tempTopTracker = 0;
                }

                for(int i = 0; i < 3; i++){
                    rubiksCube[leftFace][i][2] = rubiksCube[4][tempTopEdge][Math.abs(tempTopTracker + i)];
                    rubiksCube[4][tempTopEdge][Math.abs(tempTopTracker + i)] = rubiksCube[rightFace][Math.abs(-2 + i)][0];
                    rubiksCube[rightFace][Math.abs(-2 + i)][0] = rubiksCube[5][tempBottomEdge][Math.abs(tempBottomTracker + i)];
                    rubiksCube[5][tempBottomEdge][Math.abs(tempBottomTracker + i)] = tempArr[i];
                }

            }
            else{//side 2 and 4
                if(face == 1){
                    tempTopEdge = 2;
                    tempBottomEdge = 2;
                    tempTopTracker = 0;
                    tempBottomTracker = 0;
                }



                for(int i = 0; i < 3; i++){
                    rubiksCube[leftFace][i][2] = rubiksCube[4][Math.abs(tempTopTracker + i)][tempTopEdge];
                    rubiksCube[4][Math.abs(tempTopTracker + i)][tempTopEdge] = rubiksCube[rightFace][Math.abs(-2 + i)][0];
                    rubiksCube[rightFace][Math.abs(-2 + i)][0] = rubiksCube[5][Math.abs(tempBottomTracker + i)][tempBottomEdge];
                    rubiksCube[5][Math.abs(tempBottomTracker + i)][tempBottomEdge] = tempArr[i];
                }


            }
        }
        else{
            int tempEdge = 0;
            if(face == 5){
                tempEdge = 2;
            }
            int[] tempArr = {rubiksCube[0][tempEdge][0], rubiksCube[0][tempEdge][1], rubiksCube[0][tempEdge][2]};
            for(int i = 0; i < 3; i++){
                rubiksCube[0][tempEdge][i] = rubiksCube[3][tempEdge][i];
                rubiksCube[3][tempEdge][i] = rubiksCube[2][tempEdge][i];
                rubiksCube[2][tempEdge][i] = rubiksCube[1][tempEdge][i];
                rubiksCube[1][tempEdge][i] = tempArr[i];
            }
        }

    }


    private void turnFaceRight(int face){
        turnFaceLeft(face);
        turnFaceLeft(face);
        turnFaceLeft(face);
    }

    public void printAllFace(){
        printFace(1);
        printFace(2);
        printFace(3);
        printFace(4);
        printFace(5);
        printFace(6);
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
<<<<<<< Updated upstream


    public boolean faceSolved(int face){//not done
        boolean solved = false;
        return solved;
    }

    public int[][] findAllColor(int color){
        int[][] tempPos = new int[6][3];
        int piece = 0;

        for(int i = 0; i < rubiksCube.length; i++){
            for(int j = 0; j < rubiksCube[i].length; j++){
                for(int k = 0; k < rubiksCube[i][j].length; k++){

                    if(rubiksCube[i][j][k] == color){
                        tempPos[piece][0] = i;
                        tempPos[piece][1] = j;
                        tempPos[piece][2] = k;   
                    }   

                }
            }
        }
        return tempPos;
    }

    public int findColorAdjacent(int face, int x, int y){ 
        int color = 0;

        return color;
    }
    public int findColor(int face, int x, int y){ 
        return rubiksCube[face][x][y];
    }
}
=======
    /*
    (left,right,up,down)
    face 1: 4, 2, 5, 6
    face 2: 1, 3, 5, 6
    face 3: 2, 4, 5, 6
    face 4: 3, 1, 5, 6
    face 5: 4, 2, 3, 1
    face 6: 4, 2, 1, 3
    */
    public int getTopFace(){
        if(this.currentFace < 4)
            return 5;
        else if(this.currentFace == 4)
            return 3;

        return 1;
    }

    public int getBottomFace(){
        if(this.currentFace < 4)
        return 6;
    else if(this.currentFace == 4)
        return 1;

    return 3;
    }

    public int getLeftFace(){
        if(this.currentFace < 4){
            int leftFace = this.currentFace - 1;
            if(leftFace < 0)leftFace = 3;
            return leftFace;
        }
        return 4;
    }

    public int getRightFace(){
        if(this.currentFace < 4){
            int rightFace = this.currentFace + 1;
            if(rightFace > 3)rightFace = 0;
            return rightFace;
        }
        return 5;
    }

    public int getCurrentFace(){
        return this.currentFace + 1;
    }

    public void setCurrentFace(int face){
        this.currentFace = face - 1;
    }

    public int getFaceValue(int row, int column){
        return getFaceValue(this.currentFace, row, column);
    }
    public int getFaceValue(int face, int row, int column){
        return rubiksCube[face - 1][row -1][column - 1];
    }

    public int getAdjacentValue(int face, int row, int column){
        return rubiksCube[face - 1][row -1][column - 1];
    }
}
>>>>>>> Stashed changes
