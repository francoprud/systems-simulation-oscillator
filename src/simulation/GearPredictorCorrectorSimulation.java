package simulation;

public class GearPredictorCorrectorSimulation extends Simulation {
	public static final int[] FACTORIAL = { 1, 1, 2, 6, 24, 120 };
	public static final double[] ALPHA = { 3.0 / 20.0, 251.0 / 360.0, 1.0,
			11.0 / 18.0, 1.0 / 6.0, 1.0 / 60.0 };

	public GearPredictorCorrectorSimulation(double mass, double k,
			double gamma, double finalTime, double deltaTime,
			double initialPosition, double initialVelocity) {
		super(mass, k, gamma, finalTime, deltaTime, initialPosition,
				initialVelocity);
	}

	public void simulate() {
		double[] initialPositions = getInitialPositions(initialPosition,
				initialVelocity, mass, k, gamma);
		double deltaTimePow2 = Math.pow(deltaTime, 2);
		double error = 0;
		int steps = 0;

		for (double t = 0; t <= finalTime; t += deltaTime) {
			error += getError(initialPositions[0], t);

			double[] predictedPosition = predictPosition(initialPositions,
					deltaTime);

			double deltaAcceleration = calculateAcceleration(
					initialPositions[0], initialPositions[1], mass, k, gamma)
					- predictedPosition[2];
			double deltaR2 = deltaAcceleration * deltaTimePow2 / 2;

			for (int i = 0; i < predictedPosition.length; i++) {
				initialPositions[i] = predictedPosition[i] + ALPHA[i] * deltaR2
						* FACTORIAL[i] / Math.pow(deltaTime, i);
			}

			steps++;
		}

		System.out.println("Error: " + error / steps);
	}

	private double[] getInitialPositions(double initialPosition,
			double initialVelocity, double mass, double k, double gamma) {
		double[] r = new double[6];
		r[0] = initialPosition;
		r[1] = initialVelocity;
		r[2] = calculateAcceleration(r[0], r[1], k, gamma, mass);
		r[3] = calculateAcceleration(r[1], r[2], k, gamma, mass);
		r[4] = calculateAcceleration(r[2], r[3], k, gamma, mass);
		r[5] = calculateAcceleration(r[3], r[4], k, gamma, mass);
		return r;
	}

	private double calculateAcceleration(double position, double velocity,
			double k, double gamma, double mass) {
		return (-k * position - gamma * velocity) / mass;
	}

	private double[] predictPosition(double[] r, double deltaTime) {
		double[] predictedPositions = new double[6];
		double deltaTimePow2 = Math.pow(deltaTime, 2);
		double deltaTimePow3 = Math.pow(deltaTime, 3);
		double deltaTimePow4 = Math.pow(deltaTime, 4);
		double deltaTimePow5 = Math.pow(deltaTime, 5);

		predictedPositions[5] = r[5];
		predictedPositions[5] = r[5];
		predictedPositions[4] = r[4] + r[5] * deltaTime;
		predictedPositions[3] = r[3] + r[4] * deltaTime + r[5] * deltaTimePow2
				/ FACTORIAL[2];
		predictedPositions[2] = r[2] + r[3] * deltaTime + r[4] * deltaTimePow2
				/ FACTORIAL[2] + r[5] * deltaTimePow3 / FACTORIAL[3];
		predictedPositions[1] = r[1] + r[2] * deltaTime + r[3] * deltaTimePow2
				/ FACTORIAL[2] + r[4] * deltaTimePow3 / FACTORIAL[3] + r[5]
				* deltaTimePow4 / FACTORIAL[4];
		predictedPositions[0] = r[0] + r[1] * deltaTime + r[2] * deltaTimePow2
				/ FACTORIAL[2] + r[3] * deltaTimePow3 / FACTORIAL[3] + r[4]
				* deltaTimePow4 / FACTORIAL[4] + r[5] * deltaTimePow5
				/ FACTORIAL[5];
		return predictedPositions;
	}
}
