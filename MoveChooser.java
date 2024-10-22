import java.util.ArrayList;  

public class MoveChooser {
    static final int[][] pointMap = {    {120,-20,20,5,5, 20, -20, 120},
                            {-20, -40, -5, -5, -5, -5, -40, -20},
                            {20, -5, 15, 3, 3, 15, -5, 20},
                            {5, -5, 3, 3, 3, 3, -5, 5},
                            {5, -5, 3, 3, 3, 3, -5, 5},
                            {20, -5, 15, 3, 3, 15, -5, 20},
                            {-20, -40, -5, -5, -5, -5, -40, -20},
                            {120,-20,20,5,5, 20, -20, 120}
                        };

    public static int BPvalue(BoardState boardState){
        int Wpoints = 0;
        int Bpoints = 0;
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(boardState.getContents(i,j) == 1){
                    Wpoints += pointMap[i][j];
                }

                else if(boardState.getContents(i,j) == -1) {
                    Bpoints += pointMap[i][j];
                }
            }
        }

        return (Wpoints-Bpoints);
    }

    public static int minimaxAB(int xCoord, int yCoord, int depthS,boolean whiteTurn,BoardState boardstateXY, int alp, int beta){
        ArrayList<Move> moves1 = boardstateXY.getLegalMoves();
        if (depthS == 0){
            return BPvalue(boardstateXY);
        }

        else if(whiteTurn==true){
            int maxEval = -100000;
            BoardState boardCopy = boardstateXY.deepCopy();
            boardCopy.makeLegalMove(xCoord,yCoord);
            
            for(int i=0;i<moves1.size();i++){
                int eval = minimaxAB(moves1.get(i).x,moves1.get(i).y,depthS-1,false,boardCopy,alp,beta);
                maxEval = Math.max(maxEval,eval);
                alp = Math.max(alp,eval);
                if(beta <= alp){
                    break;
                }
            }

            return maxEval;
        }

        else{
            int minEval = +100000;
            BoardState boardCopy = boardstateXY.deepCopy();
            boardCopy.makeLegalMove(xCoord,yCoord); //perform move on a copied board
            for(int i=0;i<moves1.size();i++){
                int eval = minimaxAB(moves1.get(i).x,moves1.get(i).y,depthS-1,true,boardCopy,alp,beta);
                minEval = Math.min(minEval,eval);
                beta = Math.min(beta,eval);
                if(beta<=alp){
                    break;
                }
            }

            return minEval;
        }
    }
    public static Move chooseMove(BoardState boardState){
        int searchDepth= Othello.searchDepth;
        ArrayList<Move> moves= boardState.getLegalMoves();
        ArrayList<Integer> MoveScore = new ArrayList<Integer>();
        int currentVal;
        int MaxValue,value;
        Move selectedMove;
        if(moves.isEmpty()){
            return null;
	   }
        MaxValue = minimaxAB(moves.get(0).x,moves.get(0).y,searchDepth,true,boardState,-100000,100000);
        selectedMove = moves.get(0);
        for(int i=0; i<moves.size();i++){
            value = minimaxAB(moves.get(i).x,moves.get(i).y,searchDepth,true,boardState,-100000,100000);
            if(value > MaxValue){
                MaxValue = value;
                selectedMove = moves.get(i);
            }

        } 
        return selectedMove;
    }
}
