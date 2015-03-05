import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main
{
	public static void main(String[] args)
	{
		JFrame f= new JFrame();
		f.add(new Board());
		f.setVisible(true);
		f.setSize(600,700);
	}
}