class Knight extends Piece
{
	BufferedImage BKnight = null;
	BufferedImage WKnight = null;
	int color;
	Knight(int x,int y, int type, int color)
	{
		super(x,y,type);
		this.color= color;
	}
	public boolean move()
	{
		return true;
	}
}	