class King extends Piece
{
	BufferedImage BKing = null;
	BufferedImage WKing = null;
	int color;
	King(int x,int y, int type, int color)
	{
		super(x,y,type);
		this.color= color;
		try
		{
			if(color==0)
			
				BRook= ImageIO.read(new File("BlackKing.png"));
			else 
				WRook= ImageIO.read(new File("WhiteKing.png"));
		}
		catch(FileNotFoundException e){}
		catch(IOException e){}
	}
	public boolean move()
	{
		return true;
	}
}