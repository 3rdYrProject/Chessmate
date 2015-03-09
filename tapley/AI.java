package com.example.araic.tapley;

/**
 * Created by araic on 05/03/15.
 */

import java.util.*;

class AI
{
    Tile goal;
    Piece user;
    Tile tempUser;
    Tile oldUser;
    int depth;
    int direction=2;//initial direction for the level
    LinkedList<Piece> aiPieces;
    LinkedList<LinkedList<Tile>> path;

    AI()
    {
        aiPieces= new LinkedList<>();
        path= new LinkedList<>();
        depth=2;//change later
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
        for(Piece p:aiPieces)
        {
            if(piece.equals(p))
            {
                tempGoal=new Tile(goal);
                goal=user;
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
        if((user.getName().equals("Rook")||user.getName().equals("Queen"))&&current.checkRoute(goal,getDirection(current,goal),tiles)!=null)
        {
            penalty= 5;
        }
        if((user.getName().equals("Bishop")||user.getName().equals("Queen"))&&current.checkRouteDiag(goal,getDirection(current,goal),tiles)!=null)
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
        String name= user.getName();
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

                    int temp = Math.abs(current.getX()-x)+Math.abs(current.getY()-y);
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
            neighbours= user.getMoves(tiles,0);
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
        vertices.add(tiles[0][0]);//increase size by 1 because unless it is on the goal it will take at least 1 extra move to reach the goal.
        return vertices;
    }
    Tile[][] decision(Tile[][] tiles)
    {
        System.out.println("In decision");
        for(Piece p:aiPieces)
        {
            Tile check=null;
            String name= p.getName();
            if(name.equals("Rook")||name.equals("Queen"))
                check = (p).checkRoute(user,p.checkOrth(user),tiles);
            if(check==null&&name.equals("Bishop")||name.equals("Queen"))
                check = (p).checkRouteDiag(user,p.checkDiag(user),tiles);
            //System.out.println("Check: "+check);
            if(check!=null&&check.equals(user))//take the user
            {
                System.out.println("Took the user");
                tiles= p.move(user,tiles,this);
            }
            else
            {
                System.out.println("NEXT DECISION");
                Node temp= minmax(depth,user,p,tiles);
                System.out.println("Le decision: "+ temp.getTile());
                tiles= (p.move(temp.getTile(),tiles,this));
            }
        }
        return tiles;
    }
    boolean isProtected(Piece piece, Tile[][] tiles)
    {
        for(Piece p:aiPieces)
        {
            String name = p.getName();
            if(p.equals(piece))
                continue;
            if(name.equals("Bishop")&&p.checkRoute(piece,getDirection(p,piece),tiles).equals(piece))
            {
                return true;
            }
            else if(name.equals("Queen")||p.checkRouteDiag(piece,getDirection(p,piece),tiles).equals(piece))
            {
                return true;
            }
        }
        return false;
    }
    Node minmax(int depth,Piece user,Piece piece, Tile[][] tiles)
    {
        return(Min(depth,user,piece,new Node((int)(Double.NEGATIVE_INFINITY)),new Node((int)Double.POSITIVE_INFINITY),tiles));
    }

    Node Max(int depth, Piece user, Piece piece,Node alpha, Node beta, Tile[][] tiles)
    {
        if(depth <= 0)
        {
            int value=evaluatePaths(tiles, user).size();
            if(value==0)//I reached the goal
                return(new Node((int)(Double.POSITIVE_INFINITY)));//reached goal
            return(new Node((10-value)*10));
        }
        LinkedList<Tile> moves = user.getMoves(tiles,1);
        for(Tile t:moves)
        {
            Tile temp= new Tile(user);
            user.changePos(t);
            Node val = Min(depth - 1, user, piece, alpha, beta,tiles);
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
    Node Min(int depth, Piece user, Piece piece, Node alpha, Node beta, Tile[][] tiles)
    {
        if(depth <= 0)
        {
            int value=evaluatePaths(tiles, piece).size();
            if(value==0)
            {
                return new Node((int)(Double.NEGATIVE_INFINITY));
            }
            else if(value==1&&isProtected(piece,tiles))//I can take the user, I can be taken too though
            {
                return(new Node(-100000));
            }
            else if(value==1)//I can be taken
            {
                return(new Node((int)(Double.POSITIVE_INFINITY)-1));
            }
            return(new Node(-((10-value)*10)));
        }
        //System.out.println(piece.getName());
        LinkedList<Tile> moves = piece.getMoves(tiles,1);
        for(Tile t:moves)
        {
            Tile temp= new Tile(piece);
            piece.changePos(t);
            Node val = Max(depth - 1, user, piece, alpha, beta,tiles);
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