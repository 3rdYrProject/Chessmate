import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
public class Tree<T> {

  private T head;

  private ArrayList<Tree<T>> leafs = new ArrayList<Tree<T>>();

  private T parent = null;

  private HashMap<T, Tree<T>> locate = new HashMap<T, Tree<T>>();

  public Tree(T head) 
  {
    this.head = head;
    locate.put(head, this);
  }
  public boolean isEmpty()
  {
	return leafs.isEmpty();
  }
  public void addLeaf(T root, T leaf) 
  {
    if (locate.containsKey(root)) {
      locate.get(root).add(leaf,locate.get(root).head);
    } else {
      add(root,null).add(leaf,head);
    }
	System.out.println("Parent: "+locate.get(root).head);
  } 
  
  public T getLast()
  {
	return(leafs.get(leafs.size()-1).head);
  }
  
  public Tree<T> add(T leaf, T parent) 
  {
    Tree<T> t = new Tree<T>(leaf);
	t.parent=parent;
    leafs.add(t);
    t.locate = this.locate;
    locate.put(leaf, t);
    
    return t;
  }
  public int getLength()
  {
	return leafs.size();
  }
  
  public T getHead() {
    return head;
  }

  public Tree<T> getTree(int i) 
  {
    return leafs.get(i);
  }
  
  public int indexOf(T element)
  {	
	int count=0;
	for(Tree<T> leaf:leafs)
	{
		if(leaf.head.equals(element))
			return count;
		count++;
	}
	return -1;
  }	
 

  @Override
  public String toString() 
  {
    return printTree(0);
  }
  
  private static final int indent = 2;

  private String printTree(int increment) 
  {
    String s = "";
    String inc = "";
    
    for (int i = 0; i < increment; ++i) 
    {
      inc = inc + " ";
    }
    s = inc + head;
    
    for (Tree<T> child : leafs) 
    {
      s += "\n" + child.printTree(increment + indent);
    }
    return s;
  }
}

