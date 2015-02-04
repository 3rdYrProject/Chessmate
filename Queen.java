class Queen extends Piece
{
	BufferedImage BQueen = null;
	BufferedImage WQueen = null;
	int color;
	Queen(int x,int y, int type, int color)
	{
		super(x,y,type);
		this.color= color;
		try
		{
			if(color==0)
			
				BRook= ImageIO.read(new File("BlackQueen.png"));
			else 
				WRook= ImageIO.read(new File("WhiteQueen.png"));
		}
		catch(FileNotFoundException e){}
		catch(IOException e){}
	}
	public boolean move()
	{
		return true;
	}
}