class Rook extends Piece
{
	BufferedImage rook = null;
	
	Rook()
	{
		try
		{
			rook= new BufferedImage(new File("rook.png"));
		}catch(FileNotFoundException e){}
	}
	public void draw()
	{
	
	}
}