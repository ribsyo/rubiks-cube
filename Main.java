import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
    
        RubiksCube cube = new RubiksCube();
        
        //////////STEP 1: solve face 1 edges (NOT DONE)
        
        //////////STEP 2: solve face 1 corners (NOT DONE)
        
        //////////STEP 3: solve 4 middle layer edges (NOT DONE)
        
        //////////STEP 4: create cross on face 3 (done)
        cube.setCurrentFace(3);
        
        //if no yellow edges on face 3
        if(cube.getFaceValue(3,0,1)!=3 && cube.getFaceValue(3,1,0)!=3 && cube.getFaceValue(3,1,2)!=3) {
            cube.orientYellowEdges();
            cube.turnFaceLeft(2);
            cube.turnFaceRight(3);
        }
        //if two yellow edges (parallel)
        if(cube.matchingTiles(0,1,2,1) || cube.matchingTiles(1,0,1,2)) {
            if(cube.matchingTiles(0,1,2,1))
                cube.turnFaceRight(3);
            cube.orientYellowEdges(); //first time set up
            cube.orientYellowEdges(); //second time solve and skip to step 5
            cube.turnFaceLeft(2);
        }
        //if two yellow edges (adjacent)
        if(cube.matchingTiles(0,1,1,2) || cube.matchingTiles(1,2,2,1) || cube.matchingTiles(2,1,1,0) || cube.matchingTiles(1,0,0,1)) {
            while(!cube.matchingTiles(0,1,1,2)) //orient properly
                cube.turnFaceRight(3);
            cube.orientYellowEdges();
            cube.turnFaceLeft(2);
        }
            
        
        //////////STEP 5: solve face 3 (done)
        int yellowCorners=0;
        while(yellowCorners!=4) {
            yellowCorners=(cube.getFaceValue(3,0,0)+cube.getFaceValue(3,0,2)+cube.getFaceValue(3,2,0)+cube.getFaceValue(3,2,2))/3;
            if(yellowCorners==0) {
                cube.setCurrentFace(5);
                if(cube.getFaceValue(5,0,2)!=3){ // orient properly
                    while(cube.getFaceValue(5,0,2)!=3)
                        cube.turnFaceLeft(3);
                }
                cube.orientYellowCorners();
            }
            else if(yellowCorners==1){
                cube.setCurrentFace(3);
                while(cube.getFaceValue(3,0,0)!=3)
                    cube.turnFaceLeft(3);
                cube.orientYellowCorners();
            }
            else if(yellowCorners==2) {
                cube.setCurrentFace(2);
                while(cube.getFaceValue(2,0,0)!=3)
                    cube.turnFaceLeft(3);
                cube.orientYellowCorners();
            }
        }//loop if not solved
        
        //////////STEP 6: solve top layer corners (NOT DONE)
        System.out.println("add check if able to skip this step");
        cube.setCurrentFace(3);
        if(cube.getFaceValue(5,0,0)==cube.getFaceValue(6,2,0))
            cube.solveYellowCorners();
        
        boolean orientedCorners = false;
        for(int k=2; k<7; k++) {    
            cube.setCurrentFace(k);
            switch(k) {
                case 2:
                    if(cube.matchingTileValue(0,2,2,2)!=0) {
                        cube.turnFaceLeft(3);
                        cube.turnFaceLeft(3);
                        orientedCorners=true;
                    }
                    break;
                case 4:
                    if(cube.matchingTileValue(0,0,2,0)!=0) 
                        orientedCorners=true;
                    break;
                case 5:
                    if(cube.matchingTileValue(0,0,0,2)!=0) {
                        cube.turnFaceRight(3);
                        orientedCorners=true;
                    }
                    break;
                case 6:
                    if(cube.matchingTileValue(2,0,2,2)!=0) {
                        cube.turnFaceLeft(3);
                        orientedCorners=true;
                    }
                    break;
            }
            if(orientedCorners)
                break;
        }
        cube.solveYellowCorners();
        
        //////////STEP 7: solve top layer edges (NOT DONE)
        //simultaneously solves faces 2 4 5 & 6 if not solved yet
        
        
    }
    
}