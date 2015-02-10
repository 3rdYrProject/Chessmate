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
		int direction; 
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
					if(tiles[currentX+1][currentY].getType()!=0)
						currentX++;
					else 
						direction=0;//will always be relevant
				}
				else if(goalX<currentX)
				{
					if(tiles[currentX-1][currentY].getType()!=0)
						currentX--;
					else 
						direction=1;
				}
				else if(goalY>currentY)
				{
					if(tiles[currentX][currentY+1].getType()!=0)
						currentY++;
					else 
						direction=2;
				}
				else if(goalY<currentY)
				{
					if(tiles[currentX][currentY-1].getType()!=0)
						currentY--;
					else 
						direction=3;
				}
				else //can't get closer 
				{
					if(direction==0)
					{
						
						//loop
							
						//move up until we can move right
						//count moves
						//move down until we can move right 
						//count moves
						//whichever one is bigger add that to route.
					}
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
