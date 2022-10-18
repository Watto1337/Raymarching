package raymarching.shapes;

import raymarching.Vector;

import java.lang.Math;

public class Box extends Shape {
	// Constants
	public static final String[] PARAMS = {"width", "height", "length"};

	// Member variables
	private double width, height, length;
	
	// Constructors
	public Box(double[] args, double[] dargs) {
		super(dargs);
		
		width = Math.max(args[0], Shape.MIN_LENGTH);
		height = Math.max(args[1], Shape.MIN_LENGTH);
		length = Math.max(args[2], Shape.MIN_LENGTH);
	}
	
	// Methods
	public double getDistance(Vector v) {
		Vector v1 = new Vector(v);
		
		v1.subtract(getPos());
		v1.inverseRotate(getAngle());
		v1.set(Math.abs(v1.x) - width, Math.abs(v1.y) - height, Math.abs(v1.z) - length);
		
		Vector v2 = new Vector(Math.max(v1.x, 0), Math.max(v1.y, 0), Math.max(v1.z, 0));
		
		return v2.getLength() + Math.min(Math.max(v1.x, Math.max(v1.y, v1.z)), 0);
	}
	
	protected void setBoundRadius() {
		setBoundRadius(Math.sqrt(width*width + height*height + length*length));
	}
	
	// Getters
	public double getWidth() {return width;}
	public double getHeight() {return height;}
	public double getLength() {return length;}
	
	// Setters
	public void setWidth(double w) {
		width = w;
		setBoundRadius(-1);
	}
	
	public void setHeight(double h) {
		height = h;
		setBoundRadius(-1);
	}
	
	public void setLength(double l) {
		length = l;
		setBoundRadius(-1);
	}
	
	public void setSize(double w, double h, double l) {
		width = w;
		height = h;
		length = l;
		setBoundRadius(-1);
	}
}
