class Pawn extends Piece
{
	BufferedImage BPawn = null;
	BufferedImage WPawn = null;
	int color;
	Pawn(int x,int y, int type, int color)
	{
		super(x,y,type);
		this.color= color;
		try
		{
			if(color==0)
			
				BRook= ImageIO.read(new File("BlackRook.png"));
			else 
				WRook= ImageIO.read(new File("WhiteRook.png"));
		}
		catch(FileNotFoundException e){}
		catch(IOException e){}
	}
	public boolean move()
	{
		return true;
	}
}