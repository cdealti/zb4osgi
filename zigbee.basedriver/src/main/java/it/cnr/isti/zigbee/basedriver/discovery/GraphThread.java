package it.cnr.isti.zigbee.basedriver.discovery;

import it.cnr.isti.thread.Stoppable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class GraphThread implements Stoppable {

	public ArrayList<GraphItem> graph = new ArrayList<GraphThread.GraphItem>();
	private Map<GraphItem, ArrayList<GraphItem>> links = new HashMap<GraphItem, ArrayList<GraphItem>>();

	public class GraphItem {

		final short parent; // not real parent, but node directly linked in the mesh network
		final int address;
		final int depth;
		final int lqi;

		GraphItem(short parent, int address, int depth, int lqi){
			this.parent = parent;
			this.address = address;
			this.depth = depth;
			this.lqi = lqi;
		}
	}

	public synchronized void run() {

		createGraph();
		visualize();
	}

	private synchronized void createGraph(){

		int maxDepth = maxDepth();
		GraphItem node;

		for(int k = 0; k < graph.size(); k++){
			node = graph.get(k);
			if(node.depth == 0){
				links.put(node, new ArrayList<GraphItem>());
				break;
			}
		}

		int i = 1;
		while(i <= maxDepth){
			for(int j = 0; j < graph.size(); j++){
				node = graph.get(j);

				Iterator<Entry<GraphItem, ArrayList<GraphItem>>> it = links.entrySet().iterator();
				boolean found = false;
				while(it.hasNext()){
					Entry<GraphItem, ArrayList<GraphItem>> actual = it.next();
					if(actual.getKey().address != node.address)
						found = false;
					else{
						found = true;
						break;
					}
				}
				if(!found)
					links.put(node, new ArrayList<GraphItem>()); // insert every new node in the graph_

				it = links.entrySet().iterator();
				while(it.hasNext()){
					Entry<GraphItem, ArrayList<GraphItem>> actual = it.next();
					if(actual.getKey().address == node.parent){ // actual is linked with node nuovo
						found = false;
						for(int m = 0; m < actual.getValue().size(); m++){
							if(actual.getValue().get(m).address == node.address){
								found = true;
								break;
							}
						}
						if(!found)
							actual.getValue().add(node);
					}
				}
			}
			i++;
		}
	}

	private int maxDepth(){

		int max = -1;
		for(int i = 0; i < graph.size(); i++){
			if(graph.get(i).depth > max)
				max = graph.get(i).depth;
		}

		return max;
	}

	private synchronized void visualize(){

		System.out.print("\nZIGBEE MESH\n");

		Iterator<Entry<GraphItem, ArrayList<GraphItem>>> it = links.entrySet().iterator();
		while(it.hasNext()){
			Entry<GraphItem, ArrayList<GraphItem>> actual = it.next();
			System.out.println("Node "+actual.getKey().address+" linked with nodes:");
			for(int i = 0; i < actual.getValue().size(); i++)
				System.out.println("\t"+actual.getValue().get(i).address);

		}
		System.out.print("\n");	

		// reset
		graph = new ArrayList<GraphThread.GraphItem>();
		links = new HashMap<GraphThread.GraphItem, ArrayList<GraphItem>>();
	}

	public void end() {
		graph = null;
		links = null;
	}
}