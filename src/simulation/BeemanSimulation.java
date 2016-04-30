package simulation;

public class BeemanSimulation extends Simulation {
	public BeemanSimulation(double mass, double k, double gamma,
			double finalTime, double deltaTime, double initialPosition,
			double initialVelocity) {
		super(mass, k, gamma, finalTime, deltaTime, initialPosition,
				initialVelocity);
	}

	public void simulate() {
		double position = initialPosition;
		double velocity = initialVelocity;
		double deltaTimePow2 = Math.pow(deltaTime, 2);
		double acceleration = calculateAcceleration(position, velocity, k,
				gamma, mass);
		double previousAcceleration = acceleration;
		double error = 0;
		int steps = 0;

		for (double t = 0; t <= finalTime; t += deltaTime) {
			error += getError(position, t);

			position = position + velocity * deltaTime + (2.0 / 3.0)
					* acceleration * deltaTimePow2 - (1.0 / 6.0)
					* previousAcceleration * deltaTimePow2;
			velocity = (velocity + (5.0 / 6.0) * acceleration * deltaTime
					- (1.0 / 6.0) * previousAcceleration * deltaTime + (1.0 / 3.0)
					* (-k * position / mass) * deltaTime)
					/ (1 + (gamma * deltaTime / (3 * mass)));
			previousAcceleration = acceleration;
			acceleration = calculateAcceleration(position, velocity, k, gamma,
					mass);
			steps++;
		}

		System.out.println("Error: " + error / steps);
	}

	private double calculateAcceleration(double position, double velocity,
			double k, double gamma, double mass) {
		return (-k * position - gamma * velocity) / mass;
	}
}
