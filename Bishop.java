class Bishop extends Piece
{
	BufferedImage BBishop = null;
	BufferedImage WBishop = null;
	int color;
	Bishop(int x,int y, int type, int color)
	{
		super(x,y,type);
		this.color= color;
	}
	public boolean move()
	{
		return true;
	}
}