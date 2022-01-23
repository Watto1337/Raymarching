import java.lang.Math;

public class Cylinder extends Shape {
	// Member variables
	private double radius, height, edgeRadius;
	
	// Constructors
	public Cylinder(double x, double y, double z, double r, double h, double eR, double angleX, double angleY, int color, double shine) {
		super(x, y, z, angleX, angleY, color, shine);
		radius = Math.max(r, Main.MIN_LENGTH);
		height = Math.max(h, Main.MIN_LENGTH);
		edgeRadius = eR;
		
		updateBoundCorners();
	}
	
	public Cylinder(Vector v, double r, double h, double eR, double angleX, double angleY, int color, double shine) {this(v.getX(), v.getY(), v.getZ(), r, h, eR, angleX, angleY, color, shine);}
	
	// Methods
	public double getDistance(Vector v) {
		Vector v1 = new Vector(v);
		
		v1.subtract(getPos());
		v1.inverseRotate(getAngleX(), getAngleY());
		
		Vector v2 = new Vector(Math.sqrt(v1.getX()*v1.getX() + v1.getZ()*v1.getZ()) - radius + edgeRadius, Math.abs(v1.getY()) - height + edgeRadius, 0);
		
		double dist = Math.min(Math.max(v2.getX(), v2.getY()), 0);
		
		v2.setX(Math.max(v2.getX(), 0));
		v2.setY(Math.max(v2.getY(), 0));
		
		return dist + v2.getLength() - edgeRadius;
	}
	
	public void updateBoundCorners() {
		Vector[] boundCorners = getBoundCorners();
		
		for (int i = 0; i < 8; i++) {
			boundCorners[i].set(radius * (((i << 1) & 2) - 1), height * ((i & 2) - 1), radius * (((i >> 1) & 2) - 1));
		}
	}
	
	// Getters
	public double getRadius() {return radius;}
	public double getHeight() {return height;}
	
	// Setters
	public void setRadius(double r) {radius = r;}
	public void setHeight(double h) {height = h;}
	
	public void setSize(double r, double h) {
		radius = r;
		height = h;
	}
}