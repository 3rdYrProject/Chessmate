package com.chessmate;
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
*   Pieces sometimes think they are protected because of an ai piece that hasn't moved yet, will try to fix tomorrow.
*   An AI piece should take the user regardless of a piece being in the way since they all move at the same time.
*
*
*
*	Don't allow user to move through pieces, tomorrow.
*/
class AI
{
    Random r = new Random();
	Piece user;
	Tile tempUser;
	Piece currentPiece;
	Tile oldUser;
	int depth;
	int direction=2;//initial direction for the level
	LinkedList<Piece> aiPieces; 
	LinkedList<LinkedList<Tile>> path;
	
	AI()
	{
		aiPieces= new LinkedList<>();
		path= new LinkedList<>();
		depth=4;//change later
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
	}
	void addUser(Piece user)
	{
		this.user=user;
	}
	void updateUser(Piece user)
	{
		oldUser= new Tile(this.user);
		this.user=user;
        updateGoal(user);
		tempUser= new Tile(this.user);
	}
    void updateGoal(Tile user)
    {
        for(Piece p:aiPieces)
        {
            p.setGoal(user);
        }
    }
	public LinkedList<Tile> getPath(Tile[][] tiles, Piece piece)
	{
		currentPiece= piece;
		Tile tempGoal=piece.getGoal();
		LinkedList<Tile> closedset = new LinkedList<>();    // The set of nodes already evaluated.
		LinkedList<Tile> openset = new LinkedList<>();	// The set of tentative nodes to be evaluated, initially containing the start node
		HashMap<Tile,Tile> came_from = new HashMap<>();
		if(oldUser!=null)
			direction= getDirection(oldUser,piece);
		piece.setG(0);   // Cost from start along best known path.
		// Estimated total cost from start to goal through y.
		piece.setF(heuristic_cost_estimate(piece, tempGoal));
		openset.add(piece);
		Tile lastCurrent= null;
		while(!openset.isEmpty())
		{
			Tile current = getLowestF(openset);//lowest f_score 
			
			LinkedList<Tile> neighbours;
			if(current.equals(tempGoal))
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
				int tentative_g_score = current.getG() + dist_between(current,neighbour,tempGoal,tiles);
				
				if(!openset.contains(neighbour)||tentative_g_score < neighbour.getG())
				{ //next goal in the path
					if(lastCurrent!=null)
						direction =getDirection(current,neighbour);
					came_from.put(neighbour,current);
					neighbour.setG(tentative_g_score);
					neighbour.setF(neighbour.getG() + heuristic_cost_estimate(neighbour, tempGoal));
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
		if(direction==0)
			return lastCurrent.checkDiag(current);
		else 
			return direction;
	}
	int dist_between(Tile current, Tile neighbour, Tile goal, Tile[][] tiles)
	{
		int penalty= 15;
		if(!(currentPiece.getName().equals("Knight"))&&getDirection(current,neighbour)!=direction)
			penalty= 40;
		//if we are on the same x or y we don't want to penalise the path
		//might move this into cost_estimate, might.
		if((currentPiece.getName().equals("Rook")||currentPiece.getName().equals("Queen"))&&current.checkRoute(goal,getDirection(current,goal),tiles)!=null)
		{
			penalty= 5;
		}
		if((currentPiece.getName().equals("Bishop")||currentPiece.getName().equals("Queen"))&&current.checkRouteDiag(goal,getDirection(current,goal),tiles)!=null)
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

		LinkedList<Tile> neighbours;
        Tile previous = new Tile(currentPiece);
        tiles =currentPiece.changePos(current,tiles);
	    neighbours= currentPiece.getMoves(tiles,1);
        currentPiece.changePos(previous,tiles);
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
	Tile[][] decision(Tile[][] tiles)
	{
		for(Piece p:aiPieces)
		{
			Tile check=null;
			String name= p.getName();
			if(!name.equals("Bishop"))
				check = (p).checkRoute(user,p.checkOrth(user),tiles);
			if(name.equals("Bishop")||name.equals("Queen"))
				check = (p).checkRouteDiag(user,p.checkDiag(user),tiles);
			if(check!=null&&check.equals(user))//take the user
			{
				System.out.println("Took the user");
				tiles= p.move(user,tiles,this);
			}
			else
			{
				System.out.println("NEXT DECISION");
                System.out.println("The user: "+user);
                Node tempMin= getMin(depth,user,p,tiles);
                if(tempMin.getTile()==null||tempMin.getTile().equals(p)) {
                    System.out.println("BAD DECISION");
                    continue;
                }
				System.out.println("Le decision: "+ tempMin.getTile()+" "+tempMin.getValue());
				tiles= (p.move(tempMin.getTile(),tiles,this));
                p.setMoved();
			}
		}
        for(Piece p:aiPieces)
            p.setMoved();
		return tiles;
	}
    boolean gameOver(Piece piece)
    {
        //System.out.println("USER,GOAL,PIECE "+user+ " "+user.getGoal()+ " "+piece+" "+piece.getGoal());
        return(user.equals(user.getGoal())||piece.equals(piece.getGoal()));
    }
	boolean isProtected(Piece piece, Tile[][] tiles)
	{	
		for(Piece p:aiPieces)
		{
			String name = p.getName();
			if(p.equals(piece))
				continue;
			if(p.getMoved()&&!(name.equals("Bishop"))&&p.checkRoute(piece,getDirection(p,piece),tiles).equals(piece))
			{
				return true;
			}
			else if(p.getMoved()&&name.equals("Queen")||p.checkRouteDiag(piece,getDirection(p,piece),tiles).equals(piece))
			{
				return true;
			}
		}
		return false;
	}
	Node getMin(int depth,Piece user,Piece piece, Tile[][] tiles)
    {
        return(Min(depth, user, piece, new Node((int) (Double.NEGATIVE_INFINITY)), new Node((int) Double.POSITIVE_INFINITY), tiles));
    }
	Node Max(int depth, Piece user, Piece piece, Node alpha, Node beta, Tile[][] tiles)
    {
        if(depth <= 0||gameOver(piece))
        {
            return new Node(evaluation(user,piece,tiles));
        }
		LinkedList<Tile> moves = user.getMoves(tiles,1);
        Node best = new Node((int)(Double.NEGATIVE_INFINITY));
		for(Tile t:moves) {
            Tile temp = new Tile(user);
            tiles = user.changePos(t, tiles);
            Node val = Min(depth - 1, user, piece, alpha, beta, tiles);
            val.addTile(t);
            tiles = user.changePos(temp, tiles);
            if(val.getValue()>best.getValue())
                best=val;
            //if v ≥ β then return v
            //α ← MAX(α, v)
        }
		return best;
	}
	Node Min(int depth, Piece user, Piece piece, Node alpha, Node beta, Tile[][] tiles)
	{
        updateGoal(user);
		if(depth <= 0||gameOver(piece))
		{
            return new Node(evaluation(user,piece,tiles));
		}
		LinkedList<Tile> moves = piece.getMoves(tiles,1);
        Node best = new Node((int)(Double.POSITIVE_INFINITY));
		for(Tile t:moves)
		{
			Tile temp= new Tile(piece);
			tiles= piece.changePos(t,tiles);
            Node val = Max(depth - 1, user, piece, alpha, beta,tiles);
            val.addTile(t);
			tiles= piece.changePos(temp,tiles);
            if(val.getValue()<best.getValue())
                best=val;

		}
		return best;
	}
    int evaluation(Piece user, Piece piece,Tile[][] tiles)
    {
        int score=0;
        //AI piece is protected -10
        //User can take an AI piece +10
        if(isProtected(piece, tiles))
            score-=10;
        if(isProtected(user,tiles))
            score+=100;
        if(piece.equals(piece.getGoal()))//this could also happen when the user takes us. Need to check who took who.
            score-=10000000;
        if(user.equals(user.getGoal()))
            score+=10000000;

        LinkedList<Tile> path =getPath(tiles,user);
        if(path!=null) {
            for (Tile t : path) {
                for (Piece p : aiPieces) {
                    if (p.equals(t)) {
                        if (isProtected(p, tiles))
                            score -= 100;
                        score -= 5;
                    }
                }
            }
        }
        //System.out.println("SCORE AWARDED: "+user+" "+piece+ " "+score);
        //AI has taken the user -infinity
        //User has reached it's goal +infinity
        //AI is blocking the user from reaching his goal -5 if protected too -100
        return score;
    }
}
