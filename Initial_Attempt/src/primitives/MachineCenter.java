/**
 * 
 */
package primitives;

import java.util.ArrayList;
import java.util.List;

public class MachineCenter {
	
	private int machineID;
	private int machineCount;
	private List<Job> jobList;
	private List<JobPair> jobPairList;
	
	
	/**
	 * @param machineID
	 * @param machineCount
	 */
	public MachineCenter(int machineID, int machineCount) {
		this.machineID = machineID;
		this.machineCount = machineCount;
		jobList = new ArrayList<Job>();
		jobPairList = new ArrayList<JobPair>();
		
	}

	/**
	 * @return the machineID
	 */
	public int getMachineID() {
		return machineID;
	}

	/**
	 * @param machineID the machineID to set
	 */
	public void setMachineID(int machineID) {
		this.machineID = machineID;
	}

	/**
	 * @return the machineCount
	 */
	public int getMachineCount() {
		return machineCount;
	}

	/**
	 * @param machineCount the machineCount to set
	 */
	public void setMachineCount(int machineCount) {
		this.machineCount = machineCount;
	}

	/**
	 * @return the jobList
	 */
	public List<Job> getJobList() {
		return jobList;
	}

	/**
	 * @return the jobPairList
	 */
	public List<JobPair> getJobPairList() {
		return jobPairList;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + machineID;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MachineCenter other = (MachineCenter) obj;
		if (machineID != other.machineID)
			return false;
		return true;
	}

	
}
