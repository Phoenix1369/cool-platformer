/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Class to handle Vector manipulation
*******/
class Vector2 {
	private double theta;
	public double X;
	public double Y;

	Vector2() {
		this(0, 0);
	}	// end constructor()

	Vector2(double X, double Y) {
		this.X = X;
		this.Y = Y;
		this.theta = Math.atan2(this.Y, this.X);
	}	// end constructor(double, double)

	public Vector2 add(final Vector2 R) {
		this.X += R.X;
		this.Y += R.Y; // Vector Addition: Tip-to-Tail
		this.theta = Math.atan2(this.Y, this.X);
		return this;
	}	// end method add

	public double getTheta() {
		this.theta = Math.atan2(this.Y, this.X);
		return this.theta;
	}	// end method getTheta
}	// end class Vector
