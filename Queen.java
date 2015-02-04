class Queen extends Piece
{
	BufferedImage BQueen = null;
	BufferedImage WQueen = null;
	int color;
	Queen(int x,int y, int type, int color)
	{
		super(x,y,type);
		this.color= color;
	}
	public boolean move()
	{
		return true;
	}
}