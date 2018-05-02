package wb.controller;

import wb.model.Body;
import wb.model.Projectile;

public class Collider
{
	public static int[] getMidPoints(int x1, int y1, int x2, int y2)
	{
		int[] midXY = new int[2];
		int midy = 0;
		int midx = 0;

		double r = getDistance(x1, y1, x2, y2);
		r /=2;
		int y = getDelta(y1, y2);
		int x = getDelta(x1, x2);
		double theta = getTheta(x, y);
		
		midx = (int) (r*Math.cos(theta)+x1);
		midy = (int) (r*Math.sin(theta)+y1);
		
		midXY[0] = midx;
		midXY[1] = midy;
		return midXY;
	}	
	
	public static double getDistance(double ix, double iy, double cx, double cy)
	{
		double value = Math.pow((ix-cx),2) + Math.pow((iy-cy),2);
		value = Math.sqrt(value);
		return value;
	}
	
	public static double[] getDistances(double ix, double iy, int cx, int cy)
	{
		double[] distances = new double[2];
		double value = Math.pow((ix-cx),2);
		value = Math.sqrt(value);
		distances[0] = value;
		value = Math.pow((iy-cy),2);
		distances[1] = value;
		return distances;
	}
	
	public static int[] projectileCollisionData(Projectile projectile, Body objectOne)
	{
		int[] data = new int[3];
		
		if(projectile!=null && objectOne!=null)
		{
			//Test projectile against body
			if(projectile.getPid()!=objectOne.getPid()|| !objectOne.isCircle())
				data = testProjectileBodyCollision(projectile, objectOne);
		}
		
		return data;
	}
	
	public static boolean holeBodyCollision(Body objectOne, Body objectTwo)
	{	
		int r1 = objectOne.getSizeX()/2;
		int distanceOne = (int)getDistance(objectOne.getCx(), objectOne.getCy(), objectTwo.getCx(), objectTwo.getCy());
		
		if(distanceOne<=r1) //Currently Inside Hole
		{	
			return true;
		}
		return false;
	}
	
	public static boolean circleBodyCollision(Body objectOne, int x, int y, Body objectTwo)
	{
		if(objectOne.getPid()==objectTwo.getPid() || !objectOne.isCollidable() || !objectTwo.isCollidable())
			return false;
		
		if(x==0)
			x = objectOne.getCx();
		else
			x += objectOne.getSizeX()/2;
		if(y==0)
			y = objectOne.getCy();
		else
			y += objectOne.getSizeY()/2;
		
		int r1 = objectOne.getSizeX();
		int r2 = objectTwo.getSizeX();
		int r3 = r1/2+r2/2;
		int distanceOne = (int)getDistance(objectOne.getCx(), objectOne.getCy(), objectTwo.getCx(), objectTwo.getCy());
		if(distanceOne<=r3) //Currently Near enough to collide
		{	//Overlaps
			int distanceTwo = (int)getDistance(x, y, objectTwo.getCx(), objectTwo.getCy());
			
			//New Distance based on next location movement
			if(distanceTwo>=(r3-2)) //if next location takes you outside circle
			{
				return false;
			}
			
			return true;
		}
		return false;
	}
	
	public static boolean squareBodyCollision(Body objectOne, int x, int y, Body objectTwo)
	{
		if(objectOne.getPid()==objectTwo.getPid() || objectOne.getTid()==objectTwo.getTid() || !objectOne.isCollidable() || !objectTwo.isCollidable())
			return false;
	
		
		if(x==0)
			x = objectOne.getCx();
		else
			x += objectOne.getSizeX()/2;
		if(y==0)
			y = objectOne.getCy();
		else
			y += objectOne.getSizeY()/2;
		
		int rx = objectOne.getSizeX()/2;
		int ry = objectOne.getSizeY()/2;
		int rx2 = objectTwo.getSizeX()/2;
		int ry2 = objectTwo.getSizeY()/2;
		int distanceX = (int) Math.sqrt((Math.pow(objectOne.getCx()-objectTwo.getCx(), 2)));
		int distanceY = (int) Math.sqrt((Math.pow(objectOne.getCy()-objectTwo.getCy(), 2)));
		
		if(distanceX<=(rx+rx2) || distanceY<=(ry+ry2))//Currently Near enough to collide x or y
		{
			//Overlaps
			distanceX = (int) Math.sqrt((Math.pow(x-objectTwo.getCx(), 2)));
			distanceY = (int) Math.sqrt((Math.pow(y-objectTwo.getCy(), 2)));
			if(distanceX>=((rx+rx2)) || distanceY>=(ry+ry2))//&& distanceTwo>=((ry+ry2))) //if next location takes you outside circle
			{	
				return false;
			}
			return true;
		}

		return false;
	}
	
	public static double getLM(int x1, int y1, int x2, int y2)
	{
		double m = 0;
		if(x2>=x1 && y2>=y1)
		{	
			m = getM(x1, y1, x2, y2);
		}
		else if(x2<=x1 && y2<=y1)
		{	
			m = getM(x2, y2, x1, y1);
		}
		else if(x2<=x1 && y2>=y1)
		{	
			m = getM(x2, y2, x1, y1);
		}
		else if(x2>=x1 && y2<=y1)
		{	 
			m = getM(x1, y1, x2, y2);
		}	
		return m;
	}
	
	public static boolean inSegment(int point, int start, int end)
	{
		if(start<end)
		{
			if(point >= start && point <= end)
				return true;
		}
		else if(start>end)
		{
			if(point >= end && point <= start)
				return true;
		}
		return false;
	}
	
	public static int[] linesIP(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4)
	{
		int[] collisionData = new int[3];
	
		//*****//
		if(x1==x2 && x3==x4)
		{	//vertical 1st line && vertical 2nd line
			if(x1!=x3) //Lines are not overlapping
			{
				collisionData[0] = 0;
				return collisionData;
			}
			else //same x but are y's over lapping?
			{
				if(inSegment(y1, y3, y4))//|| inSegment(y2, y3, y4))
				{//find closest point
					if(Math.abs(y1-y3)<Math.abs(y1-y4))
					{
						collisionData[0] = 1;
						collisionData[1] = x1;
						collisionData[2] = y3;
					}
					else//closer to y4
					{
						collisionData[0] = 1;
						collisionData[1] = x1;
						collisionData[2] = y4;
					}
					return collisionData;
				}
				if(inSegment(y2, y3, y4))
				{
					if(Math.abs(y2-y3)<Math.abs(y2-y4))
					{
						collisionData[0] = 1;
						collisionData[1] = x1;
						collisionData[2] = y3;
					}
					else//closer to y4
					{
						collisionData[0] = 1;
						collisionData[1] = x1;
						collisionData[2] = y4;
					}
					return collisionData;
				}
			}
		}
		if(y1==y2 && y3==y4)
		{	//horizontal 1st line && horizontal 2nd line
			if(y1!=y3) //Lines are not overlapping
			{
				collisionData[0] = 0;
				return collisionData;
			}
			else //same y but are x's over lapping?
			{
				if(inSegment(x1, x3, x4))
				{//find closest point
					if(Math.abs(x1-x3)<Math.abs(x1-x4))
					{
						collisionData[0] = 1;
						collisionData[1] = x3;
						collisionData[2] = y1;
					}
					else//closer to x4
					{
						collisionData[0] = 1;
						collisionData[1] = x4;
						collisionData[2] = y1;
					}
					return collisionData;
				}
				if(inSegment(x2, x3, x4))
				{
					if(Math.abs(x2-x3)<Math.abs(x2-x4))
					{
						collisionData[0] = 1;
						collisionData[1] = x3;
						collisionData[2] = y1;
					}
					else//closer to x4
					{
						collisionData[0] = 1;
						collisionData[1] = x4;
						collisionData[2] = y1;
					}
					return collisionData;
				}
			}
		}

		//*****//
		if(x1==x2 && y3==y4) //Lines overlap but must check inSegment
		{	//vertical 1st line && horizontal 2nd line
			if(inSegment(x1, x3, x4))//
			{
				collisionData[0] = 1;
				collisionData[1] = x1;
				collisionData[2] = y3;
				return collisionData;
			}
		}
		if(y1==y2 && x3 == x4) //Lines will overlap
		{	//Horizontal 1stline && vertical 2nd line
			if(inSegment(y1, y3,y4))
			{
				collisionData[0] = 1;
				collisionData[1] = x3;
				collisionData[2] = y1;
				return collisionData;
			}
		}
		
		//*****//
		if(x1==x2 && x3!=x4)
		{	//vertical 1st line && sloped 2nd line
			double m2 = getLM(x3, y3, x4, y4);
			double b2 = getB(m2, y4, x4);
			int ypoint = (int)(m2*x1 +b2);
			//Check if fits inside segment
			if(inSegment(ypoint, y1, y2))
			{
				collisionData[0] = 1;
				collisionData[1] = x1;
				collisionData[2] = ypoint;
				return collisionData;
			}
		}
		if(y1==y2 && y3!=y4)
		{	//horizontal 1st line && sloped 2nd line
			double m2 = getLM(x3, y3, x4, y4);
			double b2 = getB(m2, y4, x4);
			int xpoint = (int)((y1-b2)/m2); 
			if(inSegment(xpoint, x1, x2))
			{
				collisionData[0] = 1;
				collisionData[1] = xpoint;
				collisionData[2] = y1;
				return collisionData;
			}
		}
		if(x1!=x2 && x3==x4)
		{	//sloped 1st line && vertical 2nd line
			double m1 = getLM(x1, y1, x2, y2);
			double b1 = getB(m1, y2, x2);
			int ypoint = (int)(m1*x3 +b1);
			//Check if fits inside segment
			if(inSegment(ypoint, y3, y4))
			{
				collisionData[0] = 1;
				collisionData[1] = x3;
				collisionData[2] = ypoint;
				return collisionData;
			}
		}
		if(y1!=y2 && y3==y4)
		{	//sloped 1st line && horizontal 2nd line
			double m1 = getLM(x1, y1, x2, y2);
			double b1 = getB(m1, y2, x2);
			int xpoint = (int)((y3-b1)/m1); 

			if(inSegment(xpoint, x3, x4))
			{
				collisionData[0] = 1;
				collisionData[1] = xpoint;
				collisionData[2] = y3;
				return collisionData;
			}
		}
		//*****//
		// Test for two sloped lines// avoid parallel
		//if yes check for b intercept
		//http://stackoverflow.com/questions/16314069/calculation-of-intersections-between-line-segments
		return collisionData;
	}
	
	public static int[] testSquareBodyCollision(Projectile proj, Body obj)
	{
		int[] collisionData = new int[3];
		collisionData[0] = 0;
		int x1, y1, x2, y2;
		x1 = proj.getSx();
		y1 = proj.getSy();
		x2 = proj.getEx();
		y2 = proj.getEy();
		
		
		if(x1<obj.getCx()) //from left
		{	//left up and down
			collisionData = linesIP(x1, y1, x2, y2, obj.getX(), obj.getY(), obj.getX(), obj.getY()+obj.getSizeY());
		}
		if(collisionData[0]==0 && x1>obj.getCx()) //from right
		{	//right top to bottom
			collisionData = linesIP(x1, y1, x2, y2, obj.getX()+obj.getSizeX(), obj.getY(), obj.getX()+obj.getSizeX(), obj.getY()+obj.getSizeY());
		}
		if(collisionData[0]==0 && y1<obj.getCy()) //from above
		{	//top left to right
			collisionData = linesIP(x1, y1, x2, y2, obj.getX(), obj.getY(), obj.getX()+obj.getSizeX(), obj.getY());
		}
		if(collisionData[0]==0 && y1>obj.getCy()) //from below
		{	//top left to right
			collisionData = linesIP(x1, y1, x2, y2, obj.getX(), obj.getY()+obj.getSizeY(), obj.getX()+obj.getSizeX(), obj.getY()+obj.getSizeY());
		}
		return collisionData;
	}

	public static double[] normalSlopedLineCollision(int x1, int y1, int x2, int y2, Body obj)
	{
		double[] collisionPoints = new double[2];

		double m = 0;
		double b = 0; 
		double norm = 0;
		double cb = 0;
		double secty = 0;
		double sectx = 0;
		double sx = 0;
		double sy = 0;
		m = getM(x1, y1, x2, y2);
		b = getB(m, y2, x2);
		norm = 1/m;
		norm*=-1;
		cb = obj.getCy() - (norm*obj.getCx());
		secty = cb - b;
		sectx = m - norm;
		sx = (secty/sectx);
		sy = (m*sx + b);	
		collisionPoints= new double[]{sx, sy};
		
		return collisionPoints;
	}
	
	public static int[] testProjectileBodyCollision(Projectile proj, Body obj)
	{	
		
		int x1, y1, x2, y2;
		x1 = proj.getSx();
		x2 = proj.getEx();
		y1 = proj.getSy();
		y2 = proj.getEy();
		int[] collisionData = new int[3];
		double[] collisionPoints;
		int ix = obj.getX();
		int ix2 = obj.getX()+obj.getSizeX();
		int iy = obj.getY();
		int iy2 = obj.getY()+obj.getSizeY();
		
		if(!obj.isCollidable())
			return collisionData;
		
		if(x2>=x1 && y2>=y1)
		{	
//			System.out.println(" 1");
			collisionPoints = normalSlopedLineCollision(x1, y1, x2, y2, obj);
			
			if(x1<=ix2 && y1<=iy2 && x2>=ix && y2>=iy)
			{
				if(x1>ix && y1>iy)
					collisionData[0] = 2;
				else if(obj.isCircle())
					collisionData = testCircleCollisionDistance(collisionPoints[0], collisionPoints[1], proj, obj);
				else
					collisionData = testSquareBodyCollision(proj, obj);
			}
		}
		else if(x2<=x1 && y2<=y1)
		{	
//			System.out.println(" 2");
			collisionPoints = normalSlopedLineCollision(x2, y2, x1, y1, obj);
			
			if(x1>=ix && y1>=iy && x2<=ix2 && y2<=iy2)
			{	
				if(x1<ix2 && y1<iy2)
					collisionData[0] = 2;
				else if(obj.isCircle())
					collisionData = testCircleCollisionDistance(collisionPoints[0], collisionPoints[1], proj, obj);
				else
					collisionData = testSquareBodyCollision(proj, obj);
			}
		}
		else if(x2<=x1 && y2>=y1)
		{	
//			System.out.println(" 3");
			collisionPoints = normalSlopedLineCollision(x2, y2, x1, y1, obj);
			
			if(x1>=ix && y1<=iy2 && x2<=ix2 && y2>=iy)
			{
				if(x1<ix2 && y1>iy)
					collisionData[0] = 2;
				else if(obj.isCircle())
					collisionData = testCircleCollisionDistance(collisionPoints[0], collisionPoints[1], proj, obj);
				else
					collisionData = testSquareBodyCollision(proj, obj);
			}
		}
		else if(x2>=x1 && y2<=y1)
		{	 
//			System.out.println(" 4");
			collisionPoints = normalSlopedLineCollision(x1, y1, x2, y2, obj);
			
			if(x1<=ix2 && y1>=iy && x2>=ix && y2<=iy2)
			{
				if(x1>ix && y1<iy2)
					collisionData[0] = 2;
				else if(obj.isCircle())
					collisionData = testCircleCollisionDistance(collisionPoints[0], collisionPoints[1], proj, obj);
				else
					collisionData = testSquareBodyCollision(proj, obj);
			}
		}
		return collisionData;
	}
	
	public static int[] testCircleCollisionDistance(double ix, double iy, Projectile proj, Body obj)
	{
		int[] collisionData = new int[3];
		//cD[0]==0 is false / ==1 is true
		//cD[1]==xCollisionPoint 
		//cD[2]==yCollisionPoint
		
		if(proj.getSx()==proj.getEx() || proj.getSy()==proj.getEy())
		{
			if(proj.getSx()==proj.getEx()) //vertical line
			{	
				collisionData = testSquareBodyCollision(proj, obj);
				ix = collisionData[1];
				iy = collisionData[2];
				int r = obj.getSizeY()/2;
				int rsq = (int) Math.pow(r, 2);
				int ixq = (int) Math.pow((ix-obj.getCx()), 2);
				double yt = Math.sqrt (rsq - ixq);
				double theta = Math.atan2(yt, (ix-obj.getCx()));
				int diff = (int) ((iy-obj.getCy())*Math.sin(theta));
				int range=0;
				if(proj.getSy()>obj.getCy())
					range = ((r+diff)-r);
				else
					range = -((r+diff)-r);
				int y = getRangeY(obj.getCx(), obj.getCy(), (int)ix, (int)iy, range);
				collisionData[1] = (int)ix;
				collisionData[2] = y;
			}
			else //Horizontal line
			{
				collisionData = testSquareBodyCollision(proj, obj);
				ix = collisionData[1];
				iy = collisionData[2];
				int r = obj.getSizeX()/2;
				int rsq = (int) Math.pow(r, 2);
				int iyq = (int) Math.pow((iy-obj.getCy()), 2);
				double xt = Math.sqrt (rsq - iyq);
				double theta = Math.atan2((iy-obj.getCy()), xt);
				int diff = (int) ((ix-obj.getCx())*Math.cos(theta));
				int range=0;
				if(proj.getSx()>obj.getCx())
					range = ((r+diff)-r);
				else
					range = -((r+diff)-r);
				int x = getRangeX(obj.getCx(), obj.getCy(), (int)ix, (int)iy, range);
				collisionData[1] = x;
				collisionData[2] = (int) iy;
			}
			return collisionData;
		}
		
		/**************************/
		
		
		double value = Math.pow(obj.getSizeX()/2, 2);
		double proxX = Math.abs(obj.getCx()-ix);
		double proxY = Math.abs(obj.getCy()-iy);
		//Inside of Circle
		double ppx = Math.pow(proxX, 2);
		double ppy = Math.pow(proxY, 2);
		double proxXY = ppx+ppy;
		if(proxXY<value)
		{	
			double r2= getDistance(obj.getCx(), obj.getCy(), (int)ix, (int)iy);
			double r= getDistance(proj.getSx(), proj.getSy(), (int)ix, (int)iy);
			double diff = (obj.getSizeX()/2)-(r+(r2-r));
			r-=diff;
	
			collisionData[0] = 1;
			collisionData[1] = getPolarRangeX(proj.getSx(), proj.getSy(), (int)ix, (int)iy, (int)(r));
			collisionData[2] = getPolarRangeY(proj.getSx(), proj.getSy(), (int)ix, (int)iy, (int)(r));
		}
		return collisionData;
	}
	
	public static double getM(int x1, int y1, int x2, int y2)
	{
		double m = (y2-y1);
		m /= (x2-x1);	
		return m;
	}
	
	public static double getB(double m, int y, int x)
	{
		double b = y - m*x;
		return b;
	}

	public static int getDelta(int a1, int a2)
	{
		return (a2-a1);
	}
	
	public static double getTheta(int x1, int y1, int x2, int y2)
	{
		double theta=0;
		int top = getDelta(x1, x2);
		int bot = getDelta(y1, y2);
		theta = getTheta(top,bot);
		return theta;
	}
	
	public static double getTheta(int a1, int a2)
	{
		return Math.atan2(a2,a1);
	}
	
	public static int getPolarX(int x1, int y1, int x2, int y2)
	{
		double r = getDistance(x1, y1, x2, y2);
		int y = getDelta(y1, y2);
		int x = getDelta(x1, x2);
		double theta = getTheta(x, y);
		
		return (int) (r*Math.cos(theta)+x1);
	}
	
	public static int getPolarY(int x1, int y1, int x2, int y2)
	{
		double r = getDistance(x1, y1, x2, y2);
		int y = getDelta(y1, y2);
		int x = getDelta(x1, x2);
		double theta = getTheta(x, y);
		
		return (int) (r*Math.sin(theta)+y1);
	}
	
	public static int getPolarRangeX(int x1, int y1, int x2, int y2, double r)
	{
		int y = getDelta(y1, y2);
		int x = getDelta(x1, x2);
		double theta = getTheta(x, y);
		
		return (int) (r*Math.cos(theta)+x1);
	}
	
	public static int getPolarRangeY(int x1, int y1, int x2, int y2, double r)
	{
		int y = getDelta(y1, y2);
		int x = getDelta(x1, x2);
		double theta = getTheta(x, y);
		
		return (int) (r*Math.sin(theta)+y1);
	}
	
	public static int getRangeX(int a1, int a2, int b1, int b2, double r)
	{
		int rx = 0;
		rx = (int) (a1+ ((r*(b1-a1)) / Math.sqrt(Math.pow((a1-b1),2) + Math.pow((a2-b2),2))));
		return rx;
	}
	
	public static int getRangeY(int a1, int a2, int b1, int b2, double r)
	{
		int ry = 0;
		ry = (int) (a2+ ((r*(b2-a2)) / Math.sqrt(Math.pow((a1-b1),2) + Math.pow((a2-b2),2))));
		return ry;
	}
	
	public static int getOffSet(int offSet)
	{
		int offSetVal = (int)(Math.random()*offSet);
		int neg = (int)(Math.random()*2);
		if(neg==0)
			offSetVal *=-1;
		
		return offSetVal;
	}
	
	public static double getOffSetTheta(double theta, double offSet)
	{
		offSet*= Math.PI;
		offSet/=180;
	
		double offSetVal = (Math.random()*offSet);
		int neg = (int)(Math.random()*2);
		if(neg==0)
			offSetVal *=-1;
		
		return theta+=offSetVal;
	}
	
	public static int getPolarRangeOffSetX(double x1, double r, double offTheta)
	{
		return (int)(r*Math.cos(offTheta)+x1);
	}
	
	public static int getPolarRangeOffSetY(double y1, double r, double offTheta)
	{
		return (int)(r*Math.sin(offTheta)+y1);
	}
	
	public static int[] getEndPoints(int x1, int y1, int x2, int y2, int range, double offSet)
	{
		int[] points = new int[2];
		int x = getDelta(x1, x2);
		int y = getDelta(y1, y2);
		double theta = getTheta(x, y);
		double offTheta = getOffSetTheta(theta, offSet);
		int rfx = getPolarRangeOffSetX(x1, range, offTheta);
		int rfy = getPolarRangeOffSetY(y1, range, offTheta);
		points[0] = rfx;
		points[1] = rfy;
		return points;
	}
}
