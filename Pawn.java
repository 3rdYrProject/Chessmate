class Pawn extends Piece
{
	BufferedImage BPawn = null;
	BufferedImage WPawn = null;
	int color;
	Pawn(int x,int y, int type, int color)
	{
		super(x,y,type);
		this.color= color;
	}
	public boolean move()
	{
		return true;
	}
}