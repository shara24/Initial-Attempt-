package graph;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.naming.BinaryRefAddr;
import javax.print.attribute.standard.JobOriginatingUserName;

// commented on 02/12  import algorithms.DWMaster;

import cplex.InputDataReader;

import primitives.Job;
import primitives.JobPair;
import primitives.MachineCenter;

public class Problem {
	private int id;

	private final Job dummyJob1 = new Job(-1111, -1, 0);
	private final Job dummyJob2 = new Job(1111, -1, 0);

	private List<Job> jobList;
	private List<JobPair> jobPairList;
	private List<MachineCenter> machineList;
	private List<int[]> jobMachineList;
	private List<int[][]> jobPrecedence;
	
	private double UB;
	private BOMTree bom;
	public void setMachineCount(int nummach) {
		// TODO Auto-generated method stub
		
	}


}
