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
		int[] direction; 
		if(user.getName().equals("Bishop")||user.getName().equals("Queen"))
		{
				//do diag
		}
		else if(user.getName().equals("Rook")||user.getName().equals("Queen"))
		{
				//do orth
		}
		else 
				//do knight
	}
	void evaluatePaths()
	{
	
	}
	void blockPaths()
	{
	
	}
}