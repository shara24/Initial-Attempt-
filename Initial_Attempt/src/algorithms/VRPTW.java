package algorithms;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import cplex.InputDataReader;
import cplex.InputDataReader.InputDataReaderException;
//import primitives.EndItem;
import primitives.Job;
import primitives.JobPair;
import primitives.MachineCenter;
import ilog.concert.IloException;
import ilog.concert.IloIntVar;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

public class VRPTW {
	private static IloCplex cplex;
	private static final String inputFile = "input.dat";
	private static final int M = 100000;

		
	private static int numJobs = 9;
	private static int numCenters = 2;
	private static int dueDate = 25;
	

	private static final Job dummy1 = new Job(-1111, -1, 0); //job id, machine id, processing time
	private static final Job dummy2 = new Job(1111, -1, 0);

	private static List<Job> jobList;
	private static List<JobPair> jobPairList;
	//Commented on 29th March//private static List<EndItem> endList;  // list of end items; class should be created
	private static List<MachineCenter> machineList;
	private static List<int[]> jobMachineList;
	private static List<int[][]> jobPrecedence;

	private static IloNumVar B;		// Batch size
	private static IloNumVar m;		// Move size 
	private static IloNumVar g_max;
	private static IloNumVar[] S;		//Start time of operation
	private static IloNumVar[] s;		// Start time of horizon
	private static IloNumVar[] F;		// Finish time of an operation
	private static IloNumVar[] setuptime; // setup time of an operation
	private static IloNumVar[][][] x;

	private static double objVal;

	private static boolean lprelax;
	
	public static void main(String[] args) {

		try {
			cplex = new IloCplex();

			objVal = 0;

			// readData(inputFile);

			// cplex.setOut(null);

			initializeDvars();
			addConstraints();
			cplex.addMinimize(g_max);

			cplex.setParam(IloCplex.DoubleParam.TiLim, 1800);

			long start = System.currentTimeMillis();
			

			cplex.solve();

			objVal = cplex.getObjValue();

			long end = System.currentTimeMillis();
			m = cplex.numVar(-5, -5); // Right way to do ????????? trying to set value of m as 5
			B = cplex.numVar(100, 100);
			// long solutiontime = (end - start);
			//
			// double objectivevalue = cplex.getObjValue();
			//
			// double[] solutionS = cplex.getValues(S);
			//
			// cplex.output().println();
			// cplex.output().println("Solution status = " + cplex.getStatus());
			// cplex.output().println("Solution value = " + objectivevalue);
			// cplex.output().println("Solution time = " + solutiontime);

			// System.out.println(Arrays.toString(solutionS));

			if (args != null) {
				try {
					PrintWriter pw = new PrintWriter(new BufferedWriter(
							new FileWriter("gap" + args[0] + ".txt", true)));
					pw.println(cplex.getMIPRelativeGap());
					pw.close();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			cplex.end();

		} catch (IloException exc) {
			System.err.println("Concert exception '" + exc + "' caught");
			// } catch (IOException exc) {
			// System.err.println("Error reading file " + inputFile + ": " +
			// exc);
			// } catch (InputDataReader.InputDataReaderException exc) {
			// System.err.println(exc);
		}
	}

	private static void initializeDvars() {
		try {

			g_max = cplex.numVar(0, Double.MAX_VALUE);

			S = cplex.numVarArray(numJobs, 0, Double.MAX_VALUE);
			for (int u = 0; u < numJobs; u++) {
				S[u].setName("S[" + u + "]");
			}

			x = new IloNumVar[numCenters][][];

			for (int y = 0; y < numCenters; y++) {

				MachineCenter mc = machineList.get(y);

				x[y] = new IloNumVar[mc.getMachineCount()][];

				for (int k = 0; k < mc.getMachineCount(); k++) {
					x[y][k] = new IloNumVar[mc.getJobPairList().size()];

					for (int uv = 0; uv < mc.getJobPairList().size(); uv++) {
						if (lprelax)
							x[y][k][uv] = cplex.numVar(0, 1);
						else
							x[y][k][uv] = cplex.boolVar();
						x[y][k][uv].setName("x[" + y + "][" + k + "][" + uv
								+ "]");
					}
				}
			}

		} catch (IloException e) {
			e.printStackTrace();
		}
	} 
	

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
private static void addConstraints() {

	
	try {
		//first constraint
		for (JobPair jp : jobPairList) {
			Job j1 = jp.getJ1();
			Job j2 = jp.getJ2();
			if(j1.getProcessingTime()<j2.getProcessingTime()){
			IloLinearNumExpr con1 = cplex.linearNumExpr();
			con1.addTerm(-1, s[j1.getJobID()]);
			con1.addTerm(1, s[j2.getJobID()]);			
			con1.addTerm(j1.getProcessingTime(), m);
			cplex.addGe(con1,setuptime[j1.getJobID()]);
		} else 
		{
			IloLinearNumExpr con2 = cplex.linearNumExpr();
			con2.addTerm(1, F[j1.getJobID()]);
			con2.addTerm(m, j1.getProcessingTime());
			cplex.addLe(con2,s[j2.getJobID()]);
		}
		}
// s represents start time of operation; S represents start time of planning horizon
		
		//third constraint
		for (Job j1 : jobList) {
			IloLinearNumExpr con3 = cplex.linearNumExpr();
			con3.addTerm(1, s[j1.getJobID()]);
		    con3.addTerm(1, S);
			//con3.addTerm(1, S);
			cplex.addLe(1,con3);
		}
		
		//fourth constraint
		for (JobPair jp : jobPairList) {
			Job j1 = jp.getJ1();
			Job j2 = jp.getJ2();	
			IloLinearNumExpr con4 = cplex.linearNumExpr();
			con4.addTerm(1, s[j1.getJobID()]);
			con4.addTerm(B, j1.getProcessingTime());
			con4.addTerm(1,setuptime[j1.getJobID()]);
			cplex.addEq(F[j1.getJobID()],con4); // s represents start time of operation; S represents start time of planning horizon; F represents Finish Time of an operation
		}
		
		/* Commented on 29th March
		//fifth constraint
		for (EndItem e1 : endList) {   //create EndID
			cplex.addLe(F[e1.getEndID()],D[e1.getEndID]);   // D is the due date of the end item; Fu <= Du
		}
		Comment end 29th March
	*/  
		
		//sixth constraint
			for (MachineCenter mc : machineList) {

			int y = mc.getMachineID();

			for (Job ju : mc.getJobList()) {

				if (!(ju.equals(dummy1) || ju.equals(dummy2))) {

					IloLinearNumExpr con6 = cplex.linearNumExpr();

					for (int k = 0; k < mc.getMachineCount(); k++) {

						for (int uv = 0; uv < mc.getJobPairList().size(); uv++) {
							JobPair jp = mc.getJobPairList().get(uv);

							if (ju.equals(jp.getJ1())) {

								con6.addTerm(1, x[y][k][uv]);
							}

						}
					}

					cplex.addEq(con6, 1);
				}
			}
		}

		//seventh constraint
			for (MachineCenter mc : machineList) {
			int y = mc.getMachineID();

			for (int k = 0; k < mc.getMachineCount(); k++) {
				IloLinearNumExpr con7 = cplex.linearNumExpr();

				for (int uv = 0; uv < mc.getJobPairList().size(); uv++) {
					JobPair jp = mc.getJobPairList().get(uv);
					if (jp.getJ1().equals(dummy1))
						con7.addTerm(1, x[y][k][uv]);
				}

				cplex.addEq(con7, 1);
			}

		}
			
			// Eighth Constraint
			for (MachineCenter mc : machineList) {
				int y = mc.getMachineID();

				for (int k = 0; k < mc.getMachineCount(); k++) {

					for (Job jw : mc.getJobList()) {

						if (!(jw.equals(dummy1) || jw.equals(dummy2))) {

							IloLinearNumExpr con8 = cplex.linearNumExpr();

							for (int uv = 0; uv < mc.getJobPairList().size(); uv++) {
								JobPair jp = mc.getJobPairList().get(uv);

								if (jw.equals(jp.getJ2()))
									con8.addTerm(1, x[y][k][uv]);
								else if (jw.equals(jp.getJ1()))
									con8.addTerm(-1, x[y][k][uv]);
							}

							cplex.addEq(con8, 0);
						}
					}
				}
			}

			

			//ninth constraint
		for (MachineCenter mc : machineList) {
			int y = mc.getMachineID();

			for (int k = 0; k < mc.getMachineCount(); k++) {
				IloLinearNumExpr con9 = cplex.linearNumExpr();

				for (int uv = 0; uv < mc.getJobPairList().size(); uv++) {
					JobPair jp = mc.getJobPairList().get(uv);
					if (jp.getJ2().equals(dummy2))
						con9.addTerm(1, x[y][k][uv]);

				}
				IloRange con9 = cplex.addEq(con9, 1);
				con9.setName("Constraint9")
			}

		}

		
		
		//tenth constraint
		for (MachineCenter mc : machineList) {
			int y = mc.getMachineID();

			for (int k = 0; k < mc.getMachineCount(); k++) {

				for (int uv = 0; uv < mc.getJobPairList().size(); uv++) {
					JobPair jp = mc.getJobPairList().get(uv);

					if (!(jp.getJ1().equals(dummy1) || jp.getJ2().equals(
							dummy2))) {

						IloLinearNumExpr con10 = cplex.linearNumExpr();

						Job j1 = jp.getJ1();
						Job j2 = jp.getJ2();

						con10.addTerm(1, S[j2.getJobID()]);
						con10.addTerm(-1, S[j1.getJobID()]);
						con10.addTerm(-M, x[y][k][uv]);
						con10.addTerm(m, j1.getProcessingTime());
					
						cplex.addGe(con10, setuptime[j1.getJobID()]-M ) ;

					}
					
				}
			}

