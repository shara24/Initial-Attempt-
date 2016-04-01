/**
 * 
 */
package graph;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ketandat
 * 
 */
public class BOMNode {

	private int nodeID;
	private int level;
	private BOMNode parent;
	private Set<BOMNode> children;

	/**
	 * @param nodeID
	 * @param level
	 */
	public BOMNode(int nodeID, int level) {
		this.nodeID = nodeID;
		this.level = level;
		this.children = new HashSet<BOMNode>();
	}

	
	/**
	 * @return the nodeID
	 */
	public int getNodeID() {
		return nodeID;
	}

	/**
	 * @return the parent
	 */
	public BOMNode getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(BOMNode parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public Set<BOMNode> getChildren() {
		return children;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BOMNode [nodeID=" + nodeID + ", parent=" + (parent != null ? parent.getNodeID() : "null") + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nodeID;
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
		BOMNode other = (BOMNode) obj;
		if (nodeID != other.nodeID)
			return false;
		return true;
	}

}
