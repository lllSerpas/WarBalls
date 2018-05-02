package wb.model;

public class CircleBody extends Body
{
	public CircleBody(int x, int y, int sizeX, int pid, int tid)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeX;
		setX(x);
		setY(y);
		this.pid = pid;
		this.tid = tid;
		isCircle = true;
		setCollidable(true);
	}

	public String info()
	{
		return "Circle Body";
	}
}
