package wb.model;

public class SquareBody extends Body
{
	public SquareBody(int x, int y, int sizeX, int sizeY, int pid, int tid)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.pid = pid;
		this.tid = tid;
		setX(x);
		setY(y);
		isCircle = false;
		setCollidable(true);
	}

	public String info()
	{
		return "Square body";
	}
}
