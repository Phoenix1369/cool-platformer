/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Class to handle Vector manipulation
*******/
class Vector {
	private double theta;
	private double X;
	private double Y;

	Vector() {
		this(0, 0);
	}	// end constructor()

	Vector(double X, double Y) {
		this.X = X;
		this.Y = Y;
		this.theta = Math.atan2(this.Y, this.X);
	}	// end constructor(double, double)

	public Vector add(final Vector R) {
		this.X += R.X;
		this.Y += R.Y; // Vector Addition: Tip-to-Tail
		this.theta = Math.atan2(this.Y, this.X);
		return this;
	}	// end method add

	public double getX() {
		return this.X;
	}	// end method getX

	public double getY() {
		return this.Y;
	}	// end method getY

	public void setY(double Y) {
		this.Y = Y;
		this.theta = Math.atan2(this.Y, this.X);
	}	// end method setY
}	// end class Vector
