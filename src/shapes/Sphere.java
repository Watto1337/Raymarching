package src.shapes;

import java.lang.Math;

public class Sphere extends Shape{
	// Constants
	public static final String[] PARAMS = {"radius"};

	// Member variables
	private double radius;
	
	// Constructors
	public Sphere(double[] args, double[] dargs) {
		super(dargs);
		
		radius = Math.max(args[0], Shape.MIN_LENGTH);
	}
	
	// Methods
	public double getDistance(Vector v) {
		return getPos().getDistance(v) - radius;
	}
	
	public Vector getNormal(Vector v) {
		Vector normal = new Vector(v);
		
		normal.subtract(getPos());
		
		normal.setLength(1);
		
		return normal;
	}
	
	public int getColor(Vector v) {
		if (getTexture() == null) return getColor();
		
		v.subtract(getPos());
		v.inverseRotate(getAngle());
		
		Vector step = getNormal(v);
		step.setLength(getDistance(v));
		v.subtract(step);
		
		int x = (int)((Math.atan(v.getX() / v.getZ()) / (Math.PI * -2) + (v.getZ() < 0 ? 0.25 : 0.75)) * (getTexture().getWidth() - 1));
		int y = (int)((Math.acos(v.getY()) / Math.PI) * (getTexture().getHeight() - 1));
		
		v.add(step);
		v.rotate(getAngle());
		v.add(getPos());
		
		return getTexture().getRGB(x, y);
	}
	
	// Getters
	public double getRadius() {return radius;}
	
	// Setters
	public void setRadius(double r) {
		radius = r;
		setBoundRadius();
	}
	
	// Helpers
	@Override
	protected void setBoundRadius() {
		setBoundRadius(radius);
	}
}