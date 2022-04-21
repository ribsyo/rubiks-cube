import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
    
        RubiksCube cube = new RubiksCube();
        
        //////////STEP 1: solve face 1 edges (NOT DONE)
        
        //////////STEP 2: solve face 1 corners (NOT DONE)
        
        //////////STEP 3: solve 4 middle layer edges (NOT DONE)
        while (cube.checkMiddleEdges()!=4){
            
        }

        
        //////////STEP 4: create cross on face 3 (done)
        cube.setCurrentFace(3);
        
        //if no yellow edges on face 3
        if(cube.getFaceValue(3,1,2)!=3 && cube.getFaceValue(3,2,1)!=3 && cube.getFaceValue(3,2,3)!=3) {
            cube.orientYellowEdges();
            cube.turnFaceLeft(2);
            cube.turnFaceRight(3);
        }
        //if two yellow edges (parallel)
        if(cube.matchingTiles(1,2,3,2) || cube.matchingTiles(2,1,2,3)) {
            if(cube.matchingTiles(1,2,3,2))
                cube.turnFaceRight(3);
            cube.orientYellowEdges(); //first time set up
            cube.orientYellowEdges(); //second time solve and skip to step 5
            cube.turnFaceLeft(2);
        }
        //if two yellow edges (adjacent)
        if(cube.matchingTiles(1,2,2,3) || cube.matchingTiles(2,3,3,2) || cube.matchingTiles(3,2,2,1) || cube.matchingTiles(2,1,1,2)) {
            while(!cube.matchingTiles(1,2,2,3)) //orient properly
                cube.turnFaceRight(3);
            cube.orientYellowEdges();
            cube.turnFaceLeft(2);
        }
            
        
        //////////STEP 5: solve face 3 (done)
        int yellowCorners=0;
        while(yellowCorners!=4) {
            yellowCorners=(cube.getFaceValue(3,1,1)+cube.getFaceValue(3,1,3)+cube.getFaceValue(3,3,1)+cube.getFaceValue(3,3,3))/3;
            if(yellowCorners==0) {
                cube.setCurrentFace(5);
                if(cube.getFaceValue(5,1,3)!=3){ // orient properly
                    while(cube.getFaceValue(5,1,3)!=3)
                        cube.turnFaceLeft(3);
                }
                cube.orientYellowCorners();
            }
            else if(yellowCorners==1){
                cube.setCurrentFace(3);
                while(cube.getFaceValue(3,1,1)!=3)
                    cube.turnFaceLeft(3);
                cube.orientYellowCorners();
            }
            else if(yellowCorners==2) {
                cube.setCurrentFace(2);
                while(cube.getFaceValue(2,1,1)!=3)
                    cube.turnFaceLeft(3);
                cube.orientYellowCorners();
            }
        }//loop if not solved
        //orient properly for next step
        while(cube.getFaceValue(2,1,1)!=2)
                    cube.turnFaceLeft(3);
        
        //////////STEP 6: solve top layer corners (NOT DONE)
        //check if able to skip this step
        int skip=0;
        for(int k=4; k<7; k++) {    
            cube.setCurrentFace(k);
            switch(k) {
                case 4:
                    if(cube.matchingTiles(1,1,2,2) && cube.matchingTiles(3,1,2,2)) 
                        skip++;
                    break;
                case 5:
                    if(cube.matchingTiles(1,1,2,2) && cube.matchingTiles(1,3,2,2))
                        skip++;
                    break;
                case 6:
                    if(cube.matchingTiles(3,1,2,2) && cube.matchingTiles(3,3,2,2))
                        skip++;
                    break;
            }
        }
        if(skip<3){
            cube.setCurrentFace(3);
            //if diagonal corners solved
            if(cube.getFaceValue(5,1,1)==cube.getFaceValue(6,3,1))
                cube.solveYellowCorners();
            //solve 2 parallel corners
            boolean orientedCorners = false;
            for(int k=2; k<7; k++) {    
                cube.setCurrentFace(k);
                switch(k) {
                    case 2:
                        if(cube.matchingTileValue(1,3,3,3)!=0) {
                            cube.turnFaceLeft(3);
                            cube.turnFaceLeft(3);
                            orientedCorners=true;
                        }
                        break;
                    case 4:
                        if(cube.matchingTileValue(1,1,3,1)!=0) 
                            orientedCorners=true;
                        break;
                    case 5:
                        if(cube.matchingTileValue(1,1,1,3)!=0) {
                            cube.turnFaceRight(3);
                            orientedCorners=true;
                        }
                        break;
                    case 6:
                        if(cube.matchingTileValue(3,1,3,3)!=0) {
                            cube.turnFaceLeft(3);
                            orientedCorners=true;
                        }
                        break;
                }
                if(orientedCorners)
                    break;
            }
            cube.solveYellowCorners();
            //orient properly for next step
            while(cube.getFaceValue(2,1,1)!=2)
                        cube.turnFaceLeft(3);
        }
        
        //////////STEP 7: solve top layer edges (NOT DONE)
        //simultaneously solves faces 2 4 5 & 6 if not solved yet
        while(!(cube.faceSolved(2) && cube.faceSolved(4) && cube.faceSolved(5) && cube.faceSolved(6))) {
            cube.checkYellowEdges();
            if (cube.getFaceValue(5,1,2)==cube.getFaceValue(6,1,1))
                cube.swapYellowEdgesLeft();
            else
                cube.swapYellowEdgesRight();
        }
                

        
    }
    
}