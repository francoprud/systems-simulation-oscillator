package simulation;

public class VerletSimulation extends Simulation {
	public VerletSimulation(double mass, double k, double gamma,
			double finalTime, double deltaTime, double r0, double v0) {
		super(mass, k, gamma, finalTime, deltaTime, r0, v0);
	}

	public void simulate() {
		double position = initialPosition;
		double velocity = initialVelocity;
		double deltaTimePow2 = Math.pow(deltaTime, 2);
		double force = calculateForce(position, velocity, k, gamma);
		double error = 0;
		int steps = 0;

		for (double t = 0; t <= finalTime; t += deltaTime) {
			error += getError(position, t);

			position = position + deltaTime * velocity + deltaTimePow2 * force
					/ mass;
			velocity = velocity + (deltaTime / (2 * mass))
					* (force - k * position)
					/ (1 + gamma * (deltaTime / (2 * mass)));
			force = calculateForce(position, velocity, k, gamma);

			steps++;
		}

		System.out.println("Error: " + error / steps);
	}

	private double calculateForce(double position, double velocity, double k,
			double gamma) {
		return -k * position - gamma * velocity;
	}
}