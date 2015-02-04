class Knight extends Piece
{
	BufferedImage BKnight = null;
	BufferedImage WKnight = null;
	int color;
	Knight(int x,int y, int type, int color)
	{
		super(x,y,type);
		this.color= color;
		try
		{
			if(color==0)
			
				BRook= ImageIO.read(new File("BlackKnight.png"));
			else 
				WRook= ImageIO.read(new File("WhiteKnight.png"));
		}
		catch(FileNotFoundException e){}
		catch(IOException e){}
	}
	public boolean move()
	{
		return true;
	}
}	