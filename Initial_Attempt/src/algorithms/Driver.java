package algorithms;

import graph.Problem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import primitives.MachineCenter;
public class Driver {
static final String inputFile = "input.dat";
	static final String outputFile = "outlog.dat";
	static List<Problem> problemSet;
	public static void main(String[] args) {
		PrintWriter pw = null;
		generateProblems(true);

		for (int nummach = 1; nummach <= 3; nummach++) {

			try {
				pw = new PrintWriter(new BufferedWriter(new FileWriter("outlog"
						+ nummach + ".txt")));
			} catch (IOException e) {
				e.printStackTrace();
			}
for (int pr = 0; pr < problemSet.size(); pr++) {

				Problem PR = problemSet.get(pr);

				PR.setMachineCount(nummach);

				double[] feasibleSchedule = PR.getFeasibleSchedule(nummach);

				DWSub.setDueDate(PR.getUB());

				numJobs = PR.getJobList().size();
				numCenters = PR.getMachineList().size();

				int colLength = numJobs + PR.getJobPairList().size();
				for (int i = 0; i < PR.getJobMachineList().size(); i++) {
					colLength += PR.getJobMachineList().get(i).length;
				}

				for (MachineCenter mc : PR.getMachineList()) {
					colLength += mc.getMachineCount();
				}
				VRPTW.setNumJobs(numJobs);
				VRPTW.setNumCenters(numCenters);
				VRPTW.setJobList(PR.getJobList());
				VRPTW.setJobPairList(PR.getJobPairList());
				VRPTW.setMachineList(PR.getMachineList());

				VRPTW.setLprelax(true);
				long start_lp = System.currentTimeMillis();
				VRPTW.main(null);
				double lpobj = VRPTW.getObjVal();
				long end_lp = System.currentTimeMillis();

				double intobj = 0;
				
				String st = "_" + nummach;
				String[] arr = { st };
				long start_int = System.currentTimeMillis();
				VRPTW.setLprelax(false);
				VRPTW.main(arr);
				intobj = VRPTW.getObjVal();
				long end_int = System.currentTimeMillis();
				
				pw.println(PR.getId() 
				+ "\t" + lpobj + "\t" + (end_lp - start_lp) + "\t"
				+ intobj + "\t" + (end_int - start_int));
				}
				
private static void generateProblems(boolean readFromFile) {

	Random rng = new Random(seed);

	problemSet = new ArrayList<Problem>();

	if (readFromFile) {

		Problem pr = new Problem(0);
		pr.readProblem(inputFile, numMachines);
		problemSet.add(pr);

	} else {

		for (int p = 0; p < numProblems; p++) {

			Problem pr = new Problem(p);
			pr.generateProblem(rng, numJobs, numCenters, numMachines);
			problemSet.add(pr);
		}

	}

				