package wb.model;

public class NonCollidableBody extends Body
{
	public NonCollidableBody()
	{
		this.isCollidable = false;
	}
	
	public String info()
	{
		
		return "Cannot be collided";
	}

}
