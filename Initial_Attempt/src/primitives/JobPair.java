/**
 * 
 */
package primitives;

public class JobPair {
	
	Job j1;
	Job j2;
	/**
	 * @param j1
	 * @param j2
	 */
	public JobPair(Job j1, Job j2) {
		this.j1 = j1;
		this.j2 = j2;
	}
	/**
	 * @return the j1
	 */
	public Job getJ1() {
		return j1;
	}
	/**
	 * @param j1 the j1 to set
	 */
	public void setJ1(Job j1) {
		this.j1 = j1;
	}
	/**
	 * @return the j2
	 */
	public Job getJ2() {
		return j2;
	}
	/**
	 * @param j2 the j2 to set
	 */
	public void setJ2(Job j2) {
		this.j2 = j2;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((j1 == null) ? 0 : j1.hashCode());
		result = prime * result + ((j2 == null) ? 0 : j2.hashCode());
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
		JobPair other = (JobPair) obj;
		if (j1 == null) {
			if (other.j1 != null)
				return false;
		} else if (!j1.equals(other.j1))
			return false;
		if (j2 == null) {
			if (other.j2 != null)
				return false;
		} else if (!j2.equals(other.j2))
			return false;
		return true;
	}
	
	

}
