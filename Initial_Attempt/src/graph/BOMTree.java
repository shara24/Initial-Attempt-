/**
 * 
 */
package graph;

// import java.awt.JobAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import primitives.Job;
import primitives.JobPair;

/**
 * @author ketandat
 * 
 */
public class BOMTree {

	private int treeID;
	private int levels;
	private List<BOMNode> elements;
	private BOMNode[] elementArray;
	private Set<BOMNode> leaves;

	private BOMNode root;

	/**
	 * @param treeID
	 */
	public BOMTree(int treeID) {
		this.treeID = treeID;
		this.levels = 1;

	}

	public void populateUniform(Random rng, int numJobs) {

		elements = new ArrayList<BOMNode>();
		leaves  = new HashSet<BOMNode>();

		int nodeID = numJobs;
		root = new BOMNode(--nodeID, levels);

		elements.add(root);

		BOMNode parent = null;

		while (nodeID > 0) {

			int parentid = rng.nextInt(elements.size());
			parent = elements.get(parentid);

			int newlevel = parent.getLevel() + 1;
			if (levels < newlevel)
				levels = newlevel;

			BOMNode node = new BOMNode(--nodeID, newlevel);
			node.setParent(parent);
			parent.getChildren().add(node);
			
			elements.add(node);

		}
		
		elementArray = new BOMNode[numJobs];
		for(BOMNode node : elements) {
			elementArray[node.getNodeID()] = node;
			
			if(node.getChildren().isEmpty())
				leaves.add(node);
		}

	}
	
	public void populateFromFile(List<Job> jobList, List<JobPair> jobPairList) {
		
		elements = new ArrayList<BOMNode>();
		elementArray = new BOMNode[jobList.size()];
		leaves  = new HashSet<BOMNode>();
		
		int nodeID = jobList.size() - 1;
		
		root = new BOMNode(nodeID, levels);
		
		for(Job jw : jobList) {
			elementArray[jw.getJobID()] = new BOMNode(jw.getJobID(), levels);
		}
		
		for(JobPair jp : jobPairList) {
			Job j1 = jp.getJ1();
			Job j2 = jp.getJ2();
			
			elementArray[j1.getJobID()].setParent(elementArray[j2.getJobID()]);
			elementArray[j2.getJobID()].getChildren().add(elementArray[j1.getJobID()]);
		}
		
		for(BOMNode node : elements) {
			if(node.getChildren().isEmpty())
				leaves.add(node);
		}
		
	}

	/**
	 * @return the treeID
	 */
	public int getTreeID() {
		return treeID;
	}

	/**
	 * @return the levels
	 */
	public int getLevels() {
		return levels;
	}

	/**
	 * @return the elements
	 */
	public List<BOMNode> getElements() {
		return elements;
	}

	/**
	 * @return the root
	 */
	public BOMNode getRoot() {
		return root;
	}

	/**
	 * @return the elementArray
	 */
	public BOMNode[] getElementArray() {
		return elementArray;
	}

	/**
	 * @return the leaves
	 */
	public Set<BOMNode> getLeaves() {
		return leaves;
	}

}
