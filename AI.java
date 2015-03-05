
import java.util.*;
/*	This is here to keep me on track, you can delete when they are done
*
*   Monday:
*	Get pieces to protect themselves, done
*	AI pieces have been improved hugely thanks to a fix I found by increasing the vertices by 1 see below
*	Move AI only with valid clicks, done
*
*   Tuesday:
*   Found some huge bugs that are now fixed. The ai is so good now. 
*	Get knight to work, done.
*
*	To do still, once these are done we are finished with all of the mechanics!!!!!! :D
*
*   Pieces sometimes think they are protected because of an ai piece that hasn't moved yet, will try to fix tomorrow.
*   An AI piece should take the user regardless of a piece being in the way since they all move at the same time.
*	Don't allow user to move through pieces, tomorrow.
*/
class AI
{
	Tile goal;
	Piece user;
	Tile tempUser;
	Piece currentPiece;
	Tile oldUser;
	int depth;
	int direction=2;//initial direction for the level
	LinkedList<Piece> aiPieces; 
	LinkedList<Boolean> moved;
	LinkedList<Boolean> copyMoved;
	
	AI()
	{
		aiPieces= new LinkedList<>();
		moved = new LinkedList<>();
		copyMoved = new LinkedList<>();
		depth=6;//change later
	}
	boolean isEmpty()
	{
		return aiPieces.isEmpty();
	}
	void removePiece(Tile t)
	{
		aiPieces.remove(t);
	}
	void addPiece(Piece piece)//adds ai piece to the AI mum
	{
		aiPieces.add(piece);
		moved.add(false);
		copyMoved.add(false);
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
	}
	public LinkedList<Tile> getPath(Tile[][] tiles, Piece piece)
	{
		currentPiece= piece;
		Tile tempGoal=null;
		for(Piece p:aiPieces)
		{
			if(piece.equals(p))
			{
				tempGoal=new Tile(goal);
				goal=this.user;
			}	
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
				{
					goal= new Tile(tempGoal);
				}
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
		if(direction==0&&(currentPiece.getName().equals("Queen")||currentPiece.getName().equals("Bishop")))
			return lastCurrent.checkDiag(current);
		else 
			return direction;
	}
	int dist_between(Tile current, Tile neighbour, Tile goal, Tile[][] tiles)
	{
		int penalty= 15;
		String name = currentPiece.getName();
		if(!name.equals("Knight")&&getDirection(current,neighbour)!=direction)
			penalty= 40;
		//if we are on the same x or y we don't want to penalise the path
		//might move this into cost_estimate, might.
		if(!(name.equals("Bishop"))&&current.checkRoute(goal,getDirection(current,goal),tiles)!=null)
		{
			penalty= 5;
		}
		else if((name.equals("Bishop")||name.equals("Queen"))&&current.checkRouteDiag(goal,getDirection(current,goal),tiles)!=null)
		{
			penalty= 5;
		}
		return penalty;
	}
	
	int heuristic_cost_estimate(Tile start,Tile goal)
	{
		return((Math.abs(start.getX() - goal.getX())+ Math.abs(start.getY() - goal.getY()))*10);
	}
	
	LinkedList<Tile> getNeighbours(Tile current, Tile[][] tiles)//edit here for obstacles and to limit moves
	//needs to know the piece in order to enforce the rules
	{
		LinkedList<Tile> neighbours= new LinkedList<>();
		String name= currentPiece.getName();
		if(!name.equals("Knight"))
		{
			for(int x=current.getX()-1;x<=current.getX()+1;x++)
			{
				for(int y= current.getY()-1;y<=current.getY()+1;y++)
				{
					if(x<0||x>=tiles[0].length||y<0||y>=tiles.length)
						continue;
					if(current.getType()==0)
						continue;
					if(current.equals(tiles[x][y]))
						continue;
					if(tiles[x][y].getOccupied()&&tiles[x][y].getColor()==current.getColor())//user 1, ai 0
						continue;
						
					int temp= Math.abs(current.getX()-x)+Math.abs(current.getY()-y);
					if(name.equals("Bishop"))
					{
						if(temp!=2)//diag
							continue;
					}
					else if(name.equals("Rook"))
					{
						if(temp!=1)//orth
							continue;
					}
					neighbours.add(tiles[x][y]);
				}
			}
		}
		else 
		{
			neighbours= currentPiece.getMoves(tiles,0);
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
	
	int evaluatePaths(Tile[][] tiles, Piece piece)//returns a list of vertices for the ai to block
	{
		LinkedList<Tile> path=getPath(tiles,piece);
		if(path==null)
			return(0);
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
		//increase size by 1 because unless it is on the goal it will take at least 1 extra move to reach the goal. 
		return vertices.size()+1;
	}	
	Tile[][] decision(Tile[][] tiles)
	{
		int count=0;
		moved.clear();
		moved.addAll(copyMoved);
		for(Piece p:aiPieces)
		{
			Tile check=null;
			String name= p.getName();
			if(!name.equals("Bishop"))
				check = (p).checkRoute(user,p.checkOrth(user),tiles);
			if(check==null&&(name.equals("Bishop")||name.equals("Queen")))
				check = (p).checkRouteDiag(user,p.checkDiag(user),tiles);
			if(check!=null&&check.equals(user))//take the user
			{
				tiles= p.move((Tile)user,tiles,this);
			}
			else
			{
				Node temp= minmax(depth,user,p,tiles);
				tiles= (p.move(temp.getTile(),tiles,this));
			}
			moved.set(count,true);
			count++;
		}
		
		return tiles;
	}
	boolean isProtected(Piece piece, Tile[][] tiles)
	{	
		int count=-1;
		for(Piece p:aiPieces)
		{
			count++;
			if(p.equals(piece))
				continue;
			String name = p.getName();
			boolean hasMoved=moved.get(count);
			if(!name.equals("Bishop")&&p.checkRoute(piece,getDirection(p,piece),tiles).equals(piece)&&hasMoved)
			{
				return true;
			}
			else if((name.equals("Bishop")||name.equals("Queen"))&&p.checkRouteDiag(piece,getDirection(p,piece),tiles).equals(piece)&&hasMoved)
			{
				return true;
			}
		}
		return false;
	}
	Node minmax(int depth,Piece user,Piece piece, Tile[][] tiles)
	{
		return(min(depth,user,piece,new Node((int)(Double.NEGATIVE_INFINITY)),new Node((int)Double.POSITIVE_INFINITY),tiles));
	}
	 
	Node max(int depth, Piece user, Piece piece,Node alpha, Node beta, Tile[][] tiles)
	{
		if(user.equals(goal))//or user has been taken
			return alpha;
		LinkedList<Tile> moves = user.getMoves(tiles,1);
		for(Tile t:moves)
		{
			Tile temp= new Tile(user);
			user.changePos(t);
			Node val = min(depth - 1, user, piece, alpha, beta,tiles);
			val.addTile(t);
			user.changePos(temp);
			if(val.getValue() >= beta.getValue())
				return beta;
			if(val.getValue() > alpha.getValue())
			{
				alpha = val;
			}
		}
		return alpha;
	}
	Node min(int depth, Piece user, Piece piece, Node alpha, Node beta, Tile[][] tiles)
	{
		//if(user.equals(goal))
		//	return beta;
		if(depth <= 0)
		{
			int pieceValue=evaluatePaths(tiles, piece);
			int userValue=evaluatePaths(tiles,user);
			if(pieceValue==0)
			{
				return new Node((int)(Double.NEGATIVE_INFINITY));
			}
			else if(pieceValue==1&&isProtected(piece,tiles))
			{
				return(new Node((int)(Double.NEGATIVE_INFINITY+1)));
			}
			else if(userValue==1)
			{
				return(new Node((int)(Double.POSITIVE_INFINITY)));
			}
			else if(pieceValue==1)//I can be taken
			{
				return(new Node((int)(Double.POSITIVE_INFINITY)-1));
			}
			return(new Node(0));
		}
		LinkedList<Tile> moves = piece.getMoves(tiles,1);
		for(Tile t:moves)
		{
			Tile temp= new Tile(piece);
			piece.changePos(t);
			Node val = max(depth - 1, user, piece, alpha, beta,tiles);
			val.addTile(t);
			piece.changePos(temp);
			if(val.getValue() <= alpha.getValue())
				return(alpha);
			if(val.getValue() < beta.getValue())  
			{
				beta = val;
			}
		}
		return beta;
	}
}
