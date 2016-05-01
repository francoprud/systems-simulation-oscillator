package simulation;

import java.io.FileNotFoundException;

public abstract class Simulation {
	protected double mass;
	protected double k;
	protected double gamma;
	protected double finalTime;
	protected double deltaTime;
	protected double initialPosition;
	protected double initialVelocity;

	protected Simulation(double mass, double k, double gamma, double finalTime,
			double deltaTime, double initialPosition, double initialVelocity) {
		this.mass = mass;
		this.k = k;
		this.gamma = gamma;
		this.finalTime = finalTime;
		this.deltaTime = deltaTime;
		this.initialPosition = initialPosition;
		this.initialVelocity = initialVelocity;
	}

	public abstract void simulate() throws FileNotFoundException;

	public double expectedPosition(double time) {
		return Math.exp(-(gamma * time) / (2 * mass))
				* Math.cos(Math.pow(k / mass - Math.pow(gamma / (2 * mass), 2),
						0.5) * time);
	}

	public double getError(double position, double time) {
		return Math.pow(position - expectedPosition(time), 2);
	}
}