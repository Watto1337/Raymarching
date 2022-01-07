import java.lang.Math;

public class Box extends Object {
	// Member variables
	private double width, height, length;
	
	// Constructors
	public Box(double x, double y, double z, double w, double h, double l, double angleX, double angleY, int color) {
		super(x, y, z, angleX, angleY, color);
		width = w;
		height = h;
		length = l;
	}
	
	public Box(Vector v, double w, double h, double l, double angleX, double angleY, int color) {this(v.getX(), v.getY(), v.getZ(), w, h, l, angleX, angleY, color);}
	
	public Box(double x, double y, double z, double w, double h, double l, int color) {this(x, y, z, w, h, l, 0, 0, color);}
	
	public Box(Vector v, double w, double h, double l, int color) {this(v.getX(), v.getY(), v.getZ(), w, h, l, 0, 0, color);}
	
	// Methods
	public void setBounds(Object camera, Screen screen) {
		double[] bounds = getBounds();
		
		bounds[0] = screen.getWidth();
		bounds[1] = screen.getHeight();
		bounds[2] = -screen.getWidth();
		bounds[3] = -screen.getHeight();
		
		int numInvalid = 0;
		
		for (int i = 0; i < 8; i++) {
			Vector newPos = new Vector(getPos());
			
			newPos.add(width * (i & 1), height * ((i >> 1) & 1), length * ((i >> 2) & 1));
			
			newPos.rotate(getAngleX(), getAngleY(), getPos());
			
			newPos.subtract(camera.getPos());
			
			newPos.inverseRotate(camera.getAngleX(), camera.getAngleY(), 0, 0, 0);
			
			if (newPos.getZ() < 1) numInvalid++;
			
			else {
				double ratio = screen.getDistance() / newPos.getZ();
				
				double screenX = newPos.getX() * ratio + screen.getWidth() / 2;
				double screenY = newPos.getY() * ratio + screen.getHeight() / 2;
				
				bounds[0] = Math.min(bounds[0], screenX);
				bounds[1] = Math.min(bounds[1], screenY);
				bounds[2] = Math.max(bounds[2], screenX);
				bounds[3] = Math.max(bounds[3], screenY);
			}
		}
		
		if (numInvalid == 8) {
			bounds[0] = 0;
			bounds[1] = 0;
			bounds[2] = 0;
			bounds[3] = 0;
		}
	}
	
	public double getDistance(Vector v) {
		Vector v1 = new Vector(v);
		
		v1.inverseRotate(getAngleX(), getAngleY(), getPos());
		
		Vector nearest = new Vector(Math.max(0, Math.min(width,  v1.getX() - getPos().getX())) + getPos().getX(),
									Math.max(0, Math.min(height, v1.getY() - getPos().getY())) + getPos().getY(),
									Math.max(0, Math.min(length, v1.getZ() - getPos().getZ())) + getPos().getZ());
		
		return v1.getDistance(nearest);
	}
	
	public Vector getNormal(Vector v) {
		Vector normal = new Vector(v);
		
		normal.subtract(getPos());
		
		normal.inverseRotate(getAngleX(), getAngleY(), 0, 0, 0);
		
		if (normal.getX() < 0) normal.setX(-1);
		else if (normal.getX() > width) normal.setX(1);
		else normal.setX(0);
		
		if (normal.getY() < 0) normal.setY(-1);
		else if (normal.getY() > height) normal.setY(1);
		else normal.setY(0);
		
		if (normal.getZ() < 0) normal.setZ(-1);
		else if (normal.getZ() > length) normal.setZ(1);
		else normal.setZ(0);
		
		normal.setLength(1);
		
		normal.rotate(getAngleX(), getAngleY(), 0, 0, 0);
		
		return normal;
	}
	
	// Getters
	public double getWidth() {return width;}
	public double getHeight() {return height;}
	public double getLength() {return length;}
	
	// Setters
	public void setWidth(double w) {width = w;}
	public void setHeight(double h) {height = h;}
	public void setLength(double l) {length = l;}
	
	public void setSize(double w, double h, double l) {
		width = w;
		height = h;
		length = l;
	}
}