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