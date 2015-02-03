class Board extends JPanel implements MouseListener
{
	Tile[][] tiles = new Tile[8][8];//the entire board.
	
	Board()
	{
		readLevel();
		addMouseListener(this);
	}	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		int count=0;
		for(int i=0;i<tiles.length;i++)
		{
			for(int j=0;j<tiles[i].length;j++)
			{
				count%=2;
				tiles[i][j].draw(g,count);
				count++;
			}
			count++;
		}
	}
	void readLevel()
	{
		//reads in a map for the current level. 
		Scanner sc= null;
		try{
			sc = new Scanner(new File("level.txt"));
		}catch(FileNotFoundException e){}
		
		int token= sc.nextInt();//player token.

		for(int i=0;sc.hasNextLine();i++)
		{
			for(int j=0;j<tiles[i].length;j++)
			{
				int temp = sc.nextInt();
				if(temp>3)//AI tile
				{
					tiles[j][i] = getPiece(j,i,temp);
				}
				else if(temp==2)//0 is immovable, 1 is regular
				{
					tiles[j][i] = getPiece(j,i,token);
				}
				else 
					tiles[j][i] = new Tile(j,i,temp);
			}
		}
		
	}
	
	//various listener methods.
	public Piece getPiece(int x, int y, int i)
	{
		//i is greater than 3. 
		switch(i)
		{
			case(4):return(new Rook(x,y,1));
			case(5):return(new Knight(x,y,1));
			case(6):return(new Bishop(x,y,1));
			case(7):return(new Queen(x,y,1));
			case(8):return(new King(x,y,1));
		}
	}
	public void mousePressed(MouseEvent e)
	{
		Tile temp;
		for(int i=0;i<tiles.length;i++)
		{
			for(int j=0;j<tiles[i].length;j++)
			{
				temp = tiles[j][i].check(e);
				if(temp!=null)
				{
					System.out.println("x:"+j+ " y:" + i);
					break;
				}
			}
		}
	}
	public void mouseExited(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
}	