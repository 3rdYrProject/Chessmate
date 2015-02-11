import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
public class Tree<T> {

  private T head;

  private ArrayList<Tree<T>> leafs = new ArrayList<Tree<T>>();

  private Tree<T> parent = null;

  private HashMap<T, Tree<T>> locate = new HashMap<T, Tree<T>>();

  public Tree(T head) {
    this.head = head;
    locate.put(head, this);
  }

  public void addLeaf(T root, T leaf) {
    if (locate.containsKey(root)) {
      locate.get(root).addLeaf(leaf);
    } else {
      addLeaf(root).addLeaf(leaf);
    }
  }

  public Tree<T> addLeaf(T leaf) {
    Tree<T> t = new Tree<T>(leaf);
    leafs.add(t);
    t.parent = this;
    t.locate = this.locate;
    locate.put(leaf, t);
    return t;
  }

  public T getHead() {
    return head;
  }

  public Tree<T> getTree(T element) {
    return locate.get(element);
  }

  public T getParent(T element) {//this method is fucked really need to re-think it.
  //the equals work now
	if(head.equals(element))
	{
		System.out.println("buddy");
		return null;
	}
	System.out.println("HEAD "+ head);
	for(Tree<T> child : leafs) 
	{
	  System.out.println("CHILD "+ child.head+ " ELEMENT "+ element);
      if((child.head).equals(element))
	  {
		System.out.println("YEY");
		return head;
	  }
	  child.getParent(element);
    }
	return null;
  }

  @Override
  public String toString() {
    return printTree(0);
  }

  private static final int indent = 2;

  private String printTree(int increment) {
    String s = "";
    String inc = "";
    for (int i = 0; i < increment; ++i) {
      inc = inc + " ";
    }
    s = inc + head;
    for (Tree<T> child : leafs) {
      s += "\n" + child.printTree(increment + indent);
    }
    return s;
  }
}