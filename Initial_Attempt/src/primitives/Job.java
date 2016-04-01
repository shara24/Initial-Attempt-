/**
 * 
 */
package primitives;


public class Job {
	
	private int jobID;
	private int machineID;
	private int pseudoID;
	private double processingTime;
	
	
	/**
	 * @param jobID
	 * @param machineID
	 * @param processingTime
	 */
	public Job(int jobID, int machineID,  double processingTime) {
		this.jobID = jobID;
		this.machineID = machineID;
		this.processingTime = processingTime;
	}


	/**
	 * @return the jobID
	 */
	public int getJobID() {
		return jobID;
	}


	/**
	 * @param jobID the jobID to set
	 */
	public void setJobID(int jobID) {
		this.jobID = jobID;
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
	 * @return the pseudoID
	 */
	public int getPseudoID() {
		return pseudoID;
	}


	/**
	 * @param pseudoID the pseudoID to set
	 */
	public void setPseudoID(int pseudoID) {
		this.pseudoID = pseudoID;
	}


	/**
	 * @return the processingTime
	 */
	public double getProcessingTime() {
		return processingTime;
	}


	/**
	 * @param processingTime the processingTime to set
	 */
	public void setProcessingTime(int processingTime) {
		this.processingTime = processingTime;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + jobID;
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
		Job other = (Job) obj;
		if (jobID != other.jobID)
			return false;
		return true;
	}

}
