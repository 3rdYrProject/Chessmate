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
		HashMap<Tile,Tile> came_from = new HashMap<>();
	 
		start.setG(0);   // Cost from start along best known path.
		// Estimated total cost from start to goal through y.
		start.setF(heuristic_cost_estimate(start, goal));
		openset.add(start);
		
		while(!openset.isEmpty())
		{
			Tile current = getLowestF(openset);//lowest f_score 
			LinkedList<Tile> neighbours= new LinkedList<>();
			if(current.equals(goal))
			{
				return reconstruct_path(came_from,current);
			}
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
					came_from.put(neighbour,current);
					
					neighbour.setG(tentative_g_score);
					neighbour.setF(neighbour.getG() + heuristic_cost_estimate(neighbour, goal));
					if(!openset.contains(neighbour))
					{
						openset.add(neighbour);
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
	//needs to know the piece in order to enforce the rules
	{
		LinkedList<Tile> neighbours= new LinkedList<>();
		for(int x=current.getX()-1;x<=current.getX()+1;x++)
		{
			for(int y= current.getY()-1;y<=current.getY()+1;y++)
			{
				if(x<0||x>=tiles[0].length||y<0||y>=tiles.length)
					continue;
				if(current.getType()==0)
					continue;
				if(x==current.getX()&&y!=current.getY()||x!=current.getX()&&y==current.getX())//we assume the piece is a bishop
					continue;
				if(!current.equals(tiles[x][y]))
					neighbours.add(tiles[x][y]);
			}
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
	LinkedList<Tile> reconstruct_path(HashMap<Tile,Tile> came_from,Tile current)
	{
		LinkedList<Tile> total_path = new LinkedList<>();
		total_path.add(current);
		System.out.println("Last one "+ current);
		while(came_from.get(current)!=null)
		{
			current= came_from.get(current);
			total_path.add(current);
		}
		Collections.reverse(total_path);
		return total_path;
	}
	void evaluatePaths()
	{
	
	}
	void blockPaths()
	{
	
	}
}
