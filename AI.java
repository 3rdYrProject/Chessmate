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
	public LinkedList<Tile> getPath(Tile start,Tile goal,Tile[][] tiles)
	{
		LinkedList<Tile> closedset = new LinkedList<>();    // The set of nodes already evaluated.
		LinkedList<Tile> openset = new LinkedList<>();	// The set of tentative nodes to be evaluated, initially containing the start node
		LinkedList<Tile> shortest = new LinkedList<>();
		Tree<Tile> came_from = new Tree<>(start);    // The map of navigated nodes.
	 
		start.setG(0);   // Cost from start along best known path.
		// Estimated total cost from start to goal through y.
		start.setF(heuristic_cost_estimate(start, goal));
		openset.add(start);
		
		while(!openset.isEmpty())
		{
			Tile current = getLowestF(openset);//lowest f_score 
			LinkedList<Tile> neighbours= new LinkedList<>();
			if(current.equals(goal))
				return shortest;
	 
			openset.remove(current);
			closedset.add(current);
			neighbours= getNeighbours(current,tiles);
			//
			for(Tile neighbour:neighbours)
			{
				if(closedset.contains(neighbour))
					continue;
				int tentative_g_score = current.getG() + dist_between(current,neighbour);
				if(!openset.contains(neighbour)||tentative_g_score < neighbour.getG())
				{ //next goal in the path
					neighbour.setG(tentative_g_score);
					neighbour.setF(neighbour.getG() + heuristic_cost_estimate(neighbour, goal));
					if(!openset.contains(neighbour))
					{
						openset.add(neighbour);
						if(!shortest.contains(current))
							shortest.add(current);
					}
				}
			}
		}
	 
		return null;
	}
	int dist_between(Tile current, Tile neighbour)
	{
		return 10;
	}
	int heuristic_cost_estimate(Tile start,Tile goal)//edit here to add an increased value for changing direction
	{
		return((Math.abs(start.getX() - goal.getX())+ Math.abs(start.getY() - goal.getY()))*10);
	}
	
	LinkedList<Tile> getNeighbours(Tile current, Tile[][] tiles)//edit here for obstacles and to limit moves
	{
		LinkedList<Tile> neighbours= new LinkedList<>();
		for(int x=current.getX()-1;x<=current.getX()+1;x++)
		{
			for(int y= current.getY()-1;y<=current.getY()+1;y++)
				if(!current.equals(tiles[x][y]))
					neighbours.add(tiles[x][y]);
		}
		return neighbours;
	}
	Tile getLowestF(LinkedList<Tile> list)
	{
		Tile min= null;
		for(Tile t:list)
		{
			if(min==null)
			{
				min=t;
			}
			else if(t.getF()<min.getF())
			{
				min=t;
			}
		}
		return min;
	}
	
	void evaluatePaths()
	{
	
	}
	void blockPaths()
	{
	
	}
}
