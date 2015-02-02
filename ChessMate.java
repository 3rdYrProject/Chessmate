public class ChessMate 
{
	public static void main(String[] args)
	{
		JFrame f= new JFrame(new Board());
		f.setVisible(true);
	}
}
class Board extends JPanel implements MouseListener
{
	LinkedList<Tile> tiles = new LinkedList<>();//the entire board.
	
	void readLevel()
	{
		//reads in a map for the current level. 
	}
	
	//various listener methods.
}	
class Tile{


}