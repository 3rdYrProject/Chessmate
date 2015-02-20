/* Things to do: 
*	
* Implement a direction change penalty
*
*/
import java.util.*;

class AI
{
	Tile goal;
	Piece user;
	Tile oldUser;
	int direction=2;//initial direction for the level
	LinkedList<Piece> aiPieces; 
	LinkedList<LinkedList<Tile> > path;
	
	AI()
	{
		aiPieces= new LinkedList<>();
		path= new LinkedList<>();
	}
	void removePiece(Tile t)
	{
		for(Piece temp:aiPieces)
		{
			if(t.getX()==temp.getX()&&t.getY()==temp.getY())
			{
				aiPieces.remove(temp);
				System.out.println("Took him bitch");
			}
		}
	}
	void addPiece(Piece piece)//adds ai piece to the AI mum
	{
		aiPieces.add(piece);
	}
	void addUser(Piece user)
	{
		this.user=user;
		System.out.println("I added a user");
	}
	void addGoal(Tile goal)
	{
		this.goal=goal;
	}
	void moveUser()
	{
		oldUser= new Tile(user);
	}
	public LinkedList<Tile> getPath(Tile[][] tiles, Piece piece)
	{
		LinkedList<Tile> closedset = new LinkedList<>();    // The set of nodes already evaluated.
		LinkedList<Tile> openset = new LinkedList<>();	// The set of tentative nodes to be evaluated, initially containing the start node
		HashMap<Tile,Tile> came_from = new HashMap<>();
		direction= getDirection(oldUser,piece);
		piece.setG(0);   // Cost from start along best known path.
		// Estimated total cost from start to goal through y.
		piece.setF(heuristic_cost_estimate(piece, goal));
		openset.add(piece);
		Tile lastCurrent= null;
		while(!openset.isEmpty())
		{
			Tile current = getLowestF(openset);//lowest f_score 
			
			//System.out.println(lastCurrent+ " " +current);
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
				int tentative_g_score = current.getG() + dist_between(current,neighbour,goal,tiles);
				
				if(!openset.contains(neighbour)||tentative_g_score < neighbour.getG())
				{ //next goal in the path
					if(lastCurrent!=null)
						direction =getDirection(current,neighbour);
					came_from.put(neighbour,current);
					neighbour.setG(tentative_g_score);
					neighbour.setF(neighbour.getG() + heuristic_cost_estimate(neighbour, goal));
					if(!openset.contains(neighbour))
					{
						openset.add(neighbour);
					}
				}
			}
			lastCurrent= current;
		}
	 
		return null;
	}
	int getDirection(Tile lastCurrent, Tile current)
	{
		int direction= lastCurrent.checkOrth(current);
		if(direction==0&&user.getName().equals("Queen")||user.getName().equals("Bishop"))
			return lastCurrent.checkDiag(current);
		else 
			return direction;
	}
	String printDirection(int direction)
	{
		if(direction==1)
			return("DOWN");
		else if(direction==2)
			return("UP");
		else if(direction==3)
			return("RIGHT");
		else 
			return("LEFT");
	}
	int dist_between(Tile current, Tile neighbour, Tile goal, Tile[][] tiles)
	{
		int penalty= 15;
		if(getDirection(current,neighbour)!=direction)
			penalty= 40;
		//if we are on the same x or y we don't want to penalise the path
		//might move this into cost_estimate, might.
		if(user.getName().equals("Rook")&&current.checkRoute(goal,getDirection(current,goal),tiles)!=null)
		{
			penalty= 5;
		}
		return penalty;
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
				int temp= Math.abs(current.getX()-x)+Math.abs(current.getY()-y);
				String name= user.getName();
				if(name.equals("Bishop"))
				{
					if(temp!=2)//we assume the piece is a bishop
						continue;
				}
				else if(name.equals("Rook"))
				{
					if(temp!=1)
						continue;
				}
				else if(current.equals(tiles[x][y]))
					continue;
				
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
		while(came_from.get(current)!=null)
		{
			current= came_from.get(current);
			total_path.add(current);
		}
		Collections.reverse(total_path);
		return total_path;
	}
	
	LinkedList<Tile> evaluatePaths(Tile[][] tiles, Piece user)//returns a list of vertices for the ai to block
	{
		LinkedList<Tile> path=getPath(tiles,user);
		for(Tile t:path)
		{
			System.out.println(t);
		}
		System.out.println("\n");
		Tile current = null;
		int direction= 0;
		LinkedList<Tile> vertices = new LinkedList<>();
		for(Tile t:path)
		{
			if(current!=null)
			{
				if(direction!=0&&direction!=getDirection(current,t))
				{
					vertices.add(current);
				}
				direction= getDirection(current,t);
			}
		
			current= t;
		}
		return vertices;
	}	
	void decision(Tile[][] tiles)
	{
		System.out.println("OH SHIET");
		System.out.println("Le decision: "+minmax(2,user,aiPieces.get(0),tiles));//aiPieces.get(0)).move(minmax(4,user,aiPieces.get(0)).getTile(),tiles);//should change the 4 here to a variable depth and pass it in as a parameter
	}
	Tree<Node> populate(Tile[][] tiles, int depth)//generate moves for each ply
	{
		aiPieces.get(0).getMoves(tiles,1);//We need to get all possible ai moves from the current user location. 
		return null;
	}
	Node minmax(int depth,Piece user,Piece piece, Tile[][] tiles)
	{
		//if(SideToMove() == WHITE)    // White is the "maximizing" player.
			return Max(depth,user,piece,tiles);
		//else                          // Black is the "minimizing" player.
		//	return Min(depth,user,piece);
	}
	 
	Node Max(int depth, Piece user, Piece piece, Tile[][] tiles)
	{
		Node best = new Node((int)Double.NEGATIVE_INFINITY);
	 
		if(depth <= 0)
			return(new Node(user,evaluatePaths(tiles,user).size()+1));
		LinkedList<Tile> moves =user.getMoves(tiles,1);
		for(Tile t:moves)
		{
			Tile temp= new Tile(user.getX(),user.getY(),user.getType());
			user.move(t,tiles);
			int val = Min(depth - 1, user, piece,tiles);
			user.move(temp,tiles);
			if(val > best.getValue())
				best = new Node(t,val);
			
		}
		return best;
	}
	int Min(int depth, Piece user, Piece piece, Tile[][] tiles)
	{
		int best = (int)Double.POSITIVE_INFINITY;  // <-- Note that this is different than in "Max".
	 
		if(depth <= 0)
			return evaluatePaths(tiles, piece).size()+1;
		LinkedList<Tile> moves =piece.getMoves(tiles,1);
		for(Tile t:moves)
		{
			Tile temp= new Tile(piece.getX(),piece.getY(),piece.getType());
			piece.move(t,tiles);
			
			int val = Max(depth - 1, user, piece,tiles);
			user.move(temp,tiles);
			if(val < best)  // <-- Note that this is different than in "Max".
				best = val;
		}
		return best;
	}
	//(* Initial call for maximizing player *)
	//minimax(origin, depth, TRUE)//supply how deep you want the search to go
}
