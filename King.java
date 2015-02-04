class King extends Piece
{
	BufferedImage BKing = null;
	BufferedImage WKing = null;
	int color;
	King(int x,int y, int type, int color)
	{
		super(x,y,type);
		this.color= color;
	}
	public boolean move()
	{
		return true;
	}
}