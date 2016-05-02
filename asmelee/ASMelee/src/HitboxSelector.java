

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;

public class HitboxSelector extends JFrame implements ActionListener{
	static String fname;
	static BufferedImage img;
	ArrayList<Shape> shapes = new ArrayList<Shape>();

	public static void main(String[] args)
	{
		Scanner sc =new Scanner(System.in);
		//fname = "resources\\" + sc.nextLine();
		fname = "resources\\mario_background_1.png";
		img = null;
		try {
		    img = ImageIO.read(new File(fname));
		} catch (IOException e) {
		}
		new HitboxSelector();
	}
	public HitboxSelector()
	{
		//img.set
		ImageIcon icon = new ImageIcon(img.getScaledInstance(1000, 1000, 0));
		//img.getsc
		  JLabel label = new JLabel(icon);
		  
		  this.setDefaultCloseOperation
		         (JFrame.EXIT_ON_CLOSE);
		  JButton button = new JButton();
		  button.setText("Save configuration");
		  button.addActionListener(this);
		  JComponent paintSurface = new PaintSurface();
		  paintSurface.add(button);
		  button.setVisible(true);
		  this.add(button, BorderLayout.SOUTH);
		  this.add(label,0);
		  this.pack();
		  this.setSize(1000,1000);
		  this.add(paintSurface, BorderLayout.CENTER);
		  this.setVisible(true);
	}
	public void actionPerformed(ActionEvent ae) {
		PrintWriter fwriter = null;
		try {
			 fwriter = new PrintWriter(fname.substring(0, fname.length()-3)+"txt","UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Shape a: shapes)
		{
			Rectangle2D b = ((Rectangle2D)a);
			fwriter.println((int)(b.getMinX()/3) + " " + (int)(b.getMinY()/3) + " " + 
						((int)b.getMaxX()-(int)b.getMinX())/3 + " " + ((int)b.getMaxY()-(int)b.getMinY()/3));
		}
		fwriter.close();
	  }
	private class PaintSurface extends JComponent {
	    
	    Point startDrag, endDrag;

	    public PaintSurface() {
	      this.addMouseListener(new MouseAdapter() {
	        public void mousePressed(MouseEvent e) {
	          startDrag = new Point(e.getX(), e.getY());
	          endDrag = startDrag;
	          repaint();
	        }

	        public void mouseReleased(MouseEvent e) {
	          Shape r = makeRectangle(startDrag.x, startDrag.y, e.getX(), e.getY());
	          shapes.add(r);
	          startDrag = null;
	          endDrag = null;
	          repaint();
	        }
	      });

	      this.addMouseMotionListener(new MouseMotionAdapter() {
	        public void mouseDragged(MouseEvent e) {
	          endDrag = new Point(e.getX(), e.getY());
	          repaint();
	        }
	      });
	    }
	    private void paintBackground(Graphics2D g2){
	      g2.setPaint(Color.LIGHT_GRAY);
	      for (int i = 0; i < getSize().width; i += 10) {
	        Shape line = new Line2D.Float(i, 0, i, getSize().height);
	        g2.draw(line);
	      }

	      for (int i = 0; i < getSize().height; i += 10) {
	        Shape line = new Line2D.Float(0, i, getSize().width, i);
	        g2.draw(line);
	      }

	      
	    }
	    public void paint(Graphics g) {
	      Graphics2D g2 = (Graphics2D) g;
	      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	      //paintBackground(g2);
	      Color[] colors = { Color.YELLOW, Color.MAGENTA, Color.CYAN , Color.RED, Color.BLUE, Color.PINK};
	      int colorIndex = 0;

	      g2.setStroke(new BasicStroke(2));
	      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));

	      for (Shape s : shapes) {
	        g2.setPaint(Color.BLACK);
	        g2.draw(s);
	        g2.setPaint(colors[(colorIndex++) % 6]);
	        g2.fill(s);
	      }

	      if (startDrag != null && endDrag != null) {
	        g2.setPaint(Color.LIGHT_GRAY);
	        Shape r = makeRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
	        g2.draw(r);
	      }
	    }

	    private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
	      return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
	    }
	  }
}
