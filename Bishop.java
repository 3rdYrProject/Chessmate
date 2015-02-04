class Bishop extends Piece
{
	BufferedImage BBishop = null;
	BufferedImage WBishop = null;
	int color;
	Bishop(int x,int y, int type, int color)
	{
		super(x,y,type);
		this.color= color;
		try
		{
			if(color==0)
			
				BRook= ImageIO.read(new File("BlackBishop.png"));
			else 
				WRook= ImageIO.read(new File("WhiteBishop.png"));
		}
		catch(FileNotFoundException e){}
		catch(IOException e){}
	}
	public boolean move()
	{
		return true;
	}
}