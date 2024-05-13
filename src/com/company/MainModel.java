package com.company;

import java.util.ArrayList;
import java.util.List;
public class MainModel {
	
	private List<Node> nodes;
    private List<Arc> arcs;
    private String saveFilePath;
	private String defaultSaveFilePath = "default_save.ser";
    /**
     * MainModel class corresponds to all the data of the program that the user works with.
     */
    public MainModel() {
    	
    	/*Initialize array of nodes and array of arcs*/
    	nodes = new ArrayList<Node>();
        arcs = new ArrayList<Arc>();
    	
    }

    /*Setters and Getters*/
	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<Arc> getArcs() {
		return arcs;
	}

	public void setArcs(List<Arc> arcs) {
		this.arcs = arcs;
	}

	public String getSaveFilePath() {
		return saveFilePath;
	}
	
	public void setSaveFilePath(String saveFilePath) {
		this.saveFilePath = saveFilePath;
	}
	
	public String getDefaultSaveFilePath() {
		return defaultSaveFilePath;
	}

	
    
    
	
	
	
}
