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
	Tile tempUser;
	Tile oldUser;
	int direction=2;//initial direction for the level
	LinkedList<Piece> aiPieces; 
	LinkedList<LinkedList<Tile> > path;
	
	AI()
	{
		aiPieces= new LinkedList<>();
		path= new LinkedList<>();
	}
	boolean isEmpty()
	{
		return aiPieces.isEmpty();
	}
	void removePiece(Tile t)
	{
		for(Piece temp:aiPieces)
		{
			if(t.getX()==temp.getX()&&t.getY()==temp.getY())
			{
				aiPieces.remove(temp);
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
	}
	void addGoal(Tile goal)
	{
		this.goal=goal;
	}
	void updateUser(Piece user)
	{
		oldUser= new Tile(this.user);
		this.user=user;
		tempUser= new Tile(this.user);
		System.out.println("UPDATED: "+user);
	}
	public LinkedList<Tile> getPath(Tile[][] tiles, Piece piece)
	{
		Tile tempGoal=null;
		if(piece.equals(aiPieces.get(0)))
		{
			tempGoal=new Tile(goal);
			goal=user;
		}	
		LinkedList<Tile> closedset = new LinkedList<>();    // The set of nodes already evaluated.
		LinkedList<Tile> openset = new LinkedList<>();	// The set of tentative nodes to be evaluated, initially containing the start node
		HashMap<Tile,Tile> came_from = new HashMap<>();
		if(oldUser!=null)
			direction= getDirection(oldUser,piece);
		piece.setG(0);   // Cost from start along best known path.
		// Estimated total cost from start to goal through y.
		piece.setF(heuristic_cost_estimate(piece, goal));
		openset.add(piece);
		Tile lastCurrent= null;
		while(!openset.isEmpty())
		{
			Tile current = getLowestF(openset);//lowest f_score 
			
			LinkedList<Tile> neighbours= new LinkedList<>();
			if(current.equals(goal))
			{
				if(tempGoal!=null)
					goal= tempGoal;
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
					if(temp!=2)
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
		if(path==null)
			return(new LinkedList<>());
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
	Tile[][] decision(Tile[][] tiles)
	{
		Tile check = (aiPieces.get(0)).checkRoute(user,aiPieces.get(0).checkOrth(user),tiles);
		if(check!=null&&check.equals(user))//take the user
		{
			System.out.println("Took the user");
			return aiPieces.get(0).move((Tile)user,tiles,this);
		}
		else
		{
			Node temp= minmax(4,user,aiPieces.get(0),tiles);
			System.out.println("Le decision: "+ temp.getTile());
			return(aiPieces.get(0).move(temp.getTile(),tiles,this));//should change the 4 here to a variable depth and pass it in as a parameter	
		}
	}
	Node minmax(int depth,Piece user,Piece piece, Tile[][] tiles)
	{
		return(Min(depth,user,piece,tiles));
	}
	 
	Node Max(int depth, Piece user, Piece piece, Tile[][] tiles)
	{
		Node best = new Node((int)Double.NEGATIVE_INFINITY);
	 
		if(depth <= 0)
		{
			int value=evaluatePaths(tiles, user).size();
			if(value==0)
				return(new Node(-best.getValue()));
			else return(new Node((10-value)*10));
		}
		LinkedList<Tile> moves =user.getMoves(tiles,1);
		for(Tile t:moves)
		{
			Tile temp= new Tile(user);
			user.changePos(t);
			Node val = Min(depth - 1, user, piece,tiles);
			val.addTile(t);
			user.changePos(temp);
			if(val.getValue() > best.getValue())
			{
				best = val;
			}
		}
		return best;
	}
	Node Min(int depth, Piece user, Piece piece, Tile[][] tiles)
	{
		Node best = new Node((int)Double.POSITIVE_INFINITY); 
	 
		if(depth <= 0)
		{
			int value=evaluatePaths(tiles, piece).size();
			if(value==0)
			{
				System.out.println(piece+ " " +this.tempUser);
				return(new Node(-best.getValue()));
			}
			else return(new Node(-((10-value)*10)));
		}
		LinkedList<Tile> moves =piece.getMoves(tiles,1);
		for(Tile t:moves)
		{
			Tile temp= new Tile(piece);
			piece.changePos(t);
			Node val = Max(depth - 1, user, piece,tiles);
			val.addTile(t);
			piece.changePos(temp);
			if(val.getValue() < best.getValue())  
			{
				best = val;
			}
		}
		return best;
	}
}
