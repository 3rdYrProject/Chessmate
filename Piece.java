abstract class Piece extends Tile{
	private LinkedList<Tile> moves = new LinkedList<Tile>();
	public void draw();
	public boolean move();
}
/*nothing done, black mark.

retrievemoves
--sets valid moves into a linked list

move
--knows which moves each piece can take

displaymove
--change the colours of the squares found from retrievemove method

*/
