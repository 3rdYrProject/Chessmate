import java.util.*;

class AI
{
	Tile goal;
	LinkedList<Piece> aiPieces; 
	LinkedList<LinkedList<Tile> > path;
	AI(Tile goal)
	{
		this.goal= goal;
	}
	void addPiece(Piece piece)//adds ai piece to the AI mum
	{
		aiPieces.add(piece);
	}
	void createPaths(Piece user,Tile[][] tiles)//generates the path to goal
	{
		int userX=user.getX() ,userY=user.getY();
		int goalX=goal.getX() ,goalY=goal.getY();
		int currentX=userX ,currentY=userY;
		int[] direction; 
		LinkedList<Tile> route = new LinkedList<>();
		System.out.println("Start:" +currentX+" "+currentY);
		if(user.getName().equals("Bishop")||user.getName().equals("Queen"))
		{
			System.out.println("in bishop");
			boolean loc =true;
			while(loc){
				loc = currentX!=goalX;
				if(!loc){
					loc = currentY!=goalY;
				}
				if(goalX>currentX){
					System.out.println("goal greater than current");
					if(goalY>currentY){
						currentX++;
						currentY++;
					}
					else{
						currentX++;
						currentY--;
					}
				}
				else if(goalX<currentX){
					System.out.println("goal less than current");
					if(goalY>currentY){
						currentX--;
						currentY++;
					}
					else{
						currentX--;
						currentY--;
					}
				}
				else if(goalX == currentX){
					System.out.println("goalX equal to currentX");
					if(goalY>currentY){
						currentX++;
						currentY++;
					}
					else{
						currentX++;
						currentY--;
					}
				}
				else if(goalY == currentY){
					System.out.println("goalY equal to currentY");
					if(goalY>currentY){
						currentX--;
						currentY++;
					}
					else{
						currentX--;
						currentY--;
					}
				}
				else if((goalX == currentX)&&(goalX == currentX)&&{
					System.out.println("neither statement true");
					loc = false;
				}
				route.add(tiles[currentX][currentY]);
				System.out.println("Next Tile: "+currentX+" "+currentY);
			}
			//do diag
		}
		else if(user.getName().equals("Rook")||user.getName().equals("Queen"))
		{//do orth
			while(currentX!=goalX||currentY!=goalY)
			{
				if(goalX>currentX)
				{
					currentX++;
				}
				else if(goalX<currentX)
				{
					currentX--;
				}
				else if(goalY>currentY)
				{
					currentY++;
				}
				else if(goalY<currentY)
				{
					currentY--;
				}
				route.add(tiles[currentX][currentY]);
				System.out.println("Next Tile: "+currentX+" "+currentY);
			}
		}
		else {}
				//do knight
	}
	void evaluatePaths()
	{
	
	}
	void blockPaths()
	{
	
	}
}
