package simulation;

import java.io.FileNotFoundException;

public class Main {
	private static final double MASS = 70;
	private static final double K = 10000;
	private static final double GAMMA = 100;
	private static final double FINAL_TIME = 5;
	private static final double DELTA_TIME = 0.001;
	private static final double INITIAL_POSITION = 1;
	private static final double INITIAL_VELOCITY = -(GAMMA / MASS / 2);

	public static void main(String[] args) {
		Simulation verletSimulation = new VerletSimulation(MASS, K, GAMMA, FINAL_TIME, DELTA_TIME, INITIAL_POSITION,
				INITIAL_VELOCITY);
		Simulation beemanSimulation = new BeemanSimulation(MASS, K, GAMMA, FINAL_TIME, DELTA_TIME, INITIAL_POSITION,
				INITIAL_VELOCITY);
		Simulation gearPredictorCorrectorSimulation = new GearPredictorCorrectorSimulation(MASS, K, GAMMA, FINAL_TIME,
				DELTA_TIME, INITIAL_POSITION, INITIAL_VELOCITY);

		try {
			verletSimulation.simulate();
		} catch (FileNotFoundException e) {
			System.err.println("vertel simulation file not found");
		}
		try {
			beemanSimulation.simulate();
		} catch (FileNotFoundException e) {
			System.err.println("beeman simulation file not found");
		}
		try {
			gearPredictorCorrectorSimulation.simulate();
		} catch (FileNotFoundException e) {
			System.err.println("gear predictor corrector simulation file not found");
		}
	}
}
