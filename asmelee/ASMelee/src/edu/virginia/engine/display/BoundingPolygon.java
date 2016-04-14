package edu.virginia.engine.display;

import java.awt.Point;
import java.util.ArrayList;

public class BoundingPolygon {
	private int[] xPoints;
	private int[] yPoints;
	private int numPoints;
	
	public BoundingPolygon()
	{
		xPoints = new int[100];
		yPoints = new int[100];
		numPoints = 0;
	}
	public BoundingPolygon(ArrayList<Point> vals)
	{
		super();
		int i = 0;
		for(Point a: vals)
		{
			xPoints[i] = (int) a.getX();
			yPoints[i] = (int) a.getY();
			i++;
		}
		numPoints = i;
	}
	public void addNextPoint(double x, double y)
	{
		xPoints[numPoints] = (int) x;
		yPoints[numPoints] = (int) y;
		numPoints++;
	}
	public int getNumPoints()
	{
		return numPoints;
	}
	public void clearPoints()
	{
		numPoints = 0;
	}
	public Point getPoint(int i)
	{
		return new Point((int)xPoints[i],(int)yPoints[i]);
	}
	public int[] getXs()
	{
		return xPoints;
	}
	public int[] getYs()
	{
		return yPoints;
	}
	/*
	int pnpoly(int npol, float *xp, float *yp, float x, float y)
    {
      int i, j, c = 0;
      for (i = 0, j = npol-1; i < npol; j = i++) {
        if ((((yp[i] <= y) && (y < yp[j])) ||
             ((yp[j] <= y) && (y < yp[i]))) &&
            (x < (xp[j] - xp[i]) * (y - yp[i]) / (yp[j] - yp[i]) + xp[i]))
          c = !c;
      }
      return c;
    }*/
	// Modified from the above C solution by Randolph Franklin. I took graphics,
	// know how to do this myself and can explain the theory, this was just faster.
		public boolean oldCollides(BoundingPolygon other)
		{
			for(int i = 0;i<other.numPoints;i++) {
				//System.out.println(other.xPoints[i] + " " + other.yPoints[i]);
				if(pointIsWithin(other.xPoints[i], other.yPoints[i]))
					return true;
			}
			return false;
		}
		public boolean collides(BoundingPolygon other)
		{
			int i,j;
			for(i = 1;i<=other.numPoints;i++) {
				int i0=i%other.numPoints;
				for(j=1;j<=this.numPoints;j++) {
					int j0=j%other.numPoints;
					
					//System.out.println(other.xPoints[i] + " " + other.yPoints[i]);
					if(get_line_intersection(other.xPoints[i0], other.yPoints[i0], other.xPoints[i-1], other.yPoints[i-1], 
							xPoints[j0], yPoints[j0], xPoints[j-1], yPoints[j-1]))
						return true;
				}
			}
			return false;
		}
		
		
		public boolean pointIsWithin(float x, float y)
		{
			int i,j;
			boolean c = false;
			for (i = 0, j = numPoints-1; i < numPoints; j = i++) {
				
				if ((((yPoints[i] <= y) && (y <= yPoints[j])) ||
				     ((yPoints[j] <= y) && (y <= yPoints[i]))) &&
				     (x <= (xPoints[j] - xPoints[i]) * (y - yPoints[i]) / (yPoints[j] - yPoints[i]) + xPoints[i])) {
					c = !c;
				}
			}
			return c;
		}
		// Returns 1 if the lines intersect, otherwise 0. In addition, if the lines 
		// intersect the intersection point may be stored in the floats i_x and i_y.
		boolean get_line_intersection(int p0_x, int p0_y, int p1_x, int p1_y, 
				int p2_x, int p2_y, int p3_x, int p3_y)
		{
		    int s1_x, s1_y, s2_x, s2_y;
		    s1_x = p1_x - p0_x;     s1_y = p1_y - p0_y;
		    s2_x = p3_x - p2_x;     s2_y = p3_y - p2_y;

		    float s, t,denom;
		    denom = (-s2_x * s1_y + s1_x * s2_y);
		    s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / denom;
		    if(s >= 0 && s <= 1) {
		    	t = ( s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / denom;
		    	if(t >= 0 && t <= 1)
		    		return true;
		    }
		    return false;
		}
}
