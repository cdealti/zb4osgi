/*
   Copyright 2008-2010 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies 
   of the Italian National Research Council 


   See the NOTICE file distributed with this work for additional 
   information regarding copyright ownership

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package org.persona.zigbee.tester.gui;


import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.api.ZigBeeNode;
import it.cnr.isti.zigbee.ha.cluster.glue.Cluster;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.persona.zigbee.tester.Mediator;
import org.persona.zigbee.tester.discovery.DeviceNode;
import org.persona.zigbee.tester.discovery.DeviceNodeListener;


/**
 * 
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 747 $ ($LastChangedDate: 2010-04-09 18:20:34 +0200 (Fri, 09 Apr 2010) $)
 * @since 0.1.0
 *
 */

public class TreeViewer extends JPanel 	implements DeviceNodeListener 
{
	
	private HADeviceTreeNode root;
	private DefaultTreeModel treeModel;
	private JTree tree;
    final TreePopup popup ;
    private StringBuilder formatted = new StringBuilder();
	private Formatter formatter = new Formatter(formatted);
    
	public TreeViewer(){
		super(new BorderLayout());
		Mediator.setTreeViewer(this);
		root = new HADeviceTreeNode("ZigBee Devices");
		treeModel= new DefaultTreeModel(root);
		tree = new JTree(treeModel);
		Mediator.setUPnPDeviceTree(tree);
		tree.setCellRenderer(new TreeNodeCellRenderer() );
		tree.putClientProperty("JTree.lineStyle", "Angled");
        add(new JScrollPane(tree));
		addTreeSelectionListener();
        
        
        popup = new TreePopup(tree);
        popup.setEnabled(false);
        tree.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if (SwingUtilities.isRightMouseButton(e)){
                    TreePath path = tree.getClosestPathForLocation(e.getX(), e.getY());
                    tree.setSelectionPath(path);
                    tree.scrollPathToVisible(path);
                    popup.show(tree, e.getX(), e.getY());
                }
            }
        });
        ToolTipManager.sharedInstance().registerComponent(tree);
         

	}
	
    public void setPopupMenuEnabled(boolean driverControllerAvailable){
        popup.getComponent(0).setEnabled(driverControllerAvailable);
    }
    

	public void deviceDetected(ZigBeeDevice node) {
		root.add(new HADeviceTreeNode(node));
		treeModel.nodeStructureChanged(root);
	}
    
    public void deviceDetected(DeviceNode node) {
		root.add(new HADeviceTreeNode(node, root));
		treeModel.nodeStructureChanged(root);
	}
	
	@SuppressWarnings("unchecked")
	public void rootDeviceUnplugged(String udn){
		Enumeration list = root.children();
		LogPanel.log("Unregistering udn = '" + udn + "'"); 
		while (list.hasMoreElements()){
			HADeviceTreeNode node = (HADeviceTreeNode)list.nextElement();
			boolean matches = false;
			if ( node.category == HADeviceTreeNode.HA_DEVICE ) {
				DeviceNode dn = (DeviceNode) node.getUserObject();
				LogPanel.log("Comparing with HA_DEVICE with udn = '" + dn + "'");
				matches = udn.equals(dn.toString());
			}else if ( node.category == HADeviceTreeNode.ZIGBEE_DEVICE ) {
				ZigBeeDevice zbd = (ZigBeeDevice) node.getUserObject();
				LogPanel.log("Comparing with ZIGBEE_DEVICE with udn = '" + zbd.getUniqueIdenfier() +"'");
				matches = udn.equals( zbd.getUniqueIdenfier() );
			}
			if( matches ) {
				LogPanel.log("Removing from TreeView the node "+node+" ("+node.getUserObject()+")");
				node.removeFromParent();
				treeModel.nodeChanged(root);
				return;
			}
		}
	}

	private void addTreeSelectionListener(){
		tree.addTreeSelectionListener(new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent e){
				HADeviceTreeNode selectedNode = (HADeviceTreeNode)tree.getLastSelectedPathComponent();				
				doNodeAction(selectedNode);			
			}		
		});
	}
	
	private void doNodeAction(HADeviceTreeNode node){
		if (node == null) {
			clearPropertiesViewer();
			return;
		}
		if ( node.category == HADeviceTreeNode.ZCL_ATTRIBUTE 
				|| node.category == HADeviceTreeNode.SUBSCRIBED_STATE 
				|| node.category == HADeviceTreeNode.EVENTED_STATE) {
			Mediator.getPropertiesViewer().showAttrbutePanel(true);
		} else {
			Mediator.getPropertiesViewer().showAttrbutePanel(false);
		}
		if (node.category == HADeviceTreeNode.HA_EVENT) {
			//TODO Add read all
			//TODO Add write all
			Mediator.getPropertiesViewer().setHAEvent((HAEvent) node.getUserObject());
			Mediator.getPropertiesViewer().showHAEventPanel(true);
		} else {
			Mediator.getPropertiesViewer().showHAEventPanel(false);
		}
		if (node.category.equals(HADeviceTreeNode.ZCL_COMMAND)) {
			//TODO Add read all
			//TODO Add write all
			Mediator.getPropertiesViewer().showCommandPanel(true);
		} else {
			Mediator.getPropertiesViewer().showCommandPanel(false);
		}
		if ( node.category.equals(HADeviceTreeNode.ZIGBEE_DEVICE) ) {
			ZigBeeDevice device = (ZigBeeDevice) node.getUserObject();
			makeProperties(device);
		} else if ( node.category.equals(HADeviceTreeNode.HA_DEVICE)){
			DeviceNode device = (DeviceNode) node.getUserObject();
			makeProperties(device.getReference());		
		} else if (node.category.equals(HADeviceTreeNode.SERVICE)){
			Cluster service = (Cluster) node.getUserObject();			
			makeProperties(service);
		} else if (node.category.equals(HADeviceTreeNode.ZCL_COMMAND)){
			Command cmd = (Command) node.getUserObject();
			Mediator.getPropertiesViewer().setAction(cmd);
			makeProperties(cmd);
		} else if (node.category.equals(HADeviceTreeNode.ZCL_ATTRIBUTE)
		        ||node.category.equals(HADeviceTreeNode.EVENTED_STATE)
		        ||node.category.equals(HADeviceTreeNode.SUBSCRIBED_STATE)){
			Attribute state = (Attribute) node.getUserObject();
			makeProperties(state);
			Mediator.getPropertiesViewer().setAttributeAction(state);
		}
		
	}
	
	private void clearPropertiesViewer(){
		String[] names = new String[]{};
		String[] values = new String[]{};
		PropertiesViewer viewer = Mediator.getPropertiesViewer();
		viewer.setProperties(names,values);
		viewer.showAttrbutePanel(false);
		viewer.showCommandPanel(false);
	}
	
	private void makeProperties(ServiceReference reference) {
		Dictionary<String,Object> dict = new Hashtable<String, Object>();
		dict.put(HADevice.HA_DEVICE_GROUP, reference.getProperty(HADevice.HA_DEVICE_GROUP));
		dict.put(HADevice.HA_DEVICE_NAME, reference.getProperty(HADevice.HA_DEVICE_NAME));
		dict.put(HADevice.HA_DEVICE_STANDARD, reference.getProperty(HADevice.HA_DEVICE_STANDARD));
		dict.put(HADevice.HA_DRIVER, reference.getProperty(HADevice.HA_DRIVER));
		dict.put(HADevice.ZIGBEE_DEVICE_SERVICE, reference.getProperty(HADevice.ZIGBEE_DEVICE_SERVICE));
		dict.put(HADevice.ZIGBEE_DEVICE_UUID, reference.getProperty(HADevice.ZIGBEE_DEVICE_UUID));
		dict.put(Constants.OBJECTCLASS, reference.getProperty(Constants.OBJECTCLASS));
		dict.put(Constants.SERVICE_ID, reference.getProperty(Constants.SERVICE_ID));
		
		makeProperties(dict);
	}
	
	private void makeProperties(Dictionary<String,Object> dict) {
		int size = dict.size();
		String[] names = new String[size];
		String[] values = new String[size];
		Enumeration<String> keys = dict.keys();
		for (int i=0;i<size;i++){
			names[i]= (String) keys.nextElement();
		}
		Arrays.sort(names);
		for (int i=0;i<size;i++){
			values[i]= Util.justString(dict.get(names[i]));
		}
		Mediator.getPropertiesViewer().setProperties(names,values);
	}

	private void makeProperties(ZigBeeDevice zb){
		int[] clusters;
		Dictionary<String,Object> dict = new Hashtable<String, Object>();
		formatted.setLength(0); formatter.format("%08X", zb.getDeviceId());
		dict.put(ZigBeeDevice.DEVICE_ID,"0x"+formatted.toString().substring(4)+" ("+zb.getDeviceId()+")");
		formatted.setLength(0); formatter.format("%04X", zb.getId());
		dict.put(ZigBeeDevice.ENDPOINT,"0x"+formatted.toString().substring(2)+" ("+zb.getId()+")");
		formatted.setLength(0); formatter.format("%08X", zb.getProfileId());
		dict.put(ZigBeeDevice.PROFILE_ID,"0x"+formatted.toString().substring(4)+" ("+zb.getProfileId()+")");
		dict.put(ZigBeeDevice.UUID,zb.getUniqueIdenfier());
		dict.put(ZigBeeNode.IEEE_ADDRESS, zb.getPhysicalNode().getIEEEAddress());
		formatted.setLength(0); formatter.format("%08X", zb.getDeviceId());
		dict.put(ZigBeeDevice.DEVICE_ID,"0x"+formatted.toString().substring(4)+" ("+zb.getDeviceId()+")");
		
		clusters = zb.getInputClusters();
		if(clusters != null ) {
			for (int i = 0; i < clusters.length; i++) {
				formatted.setLength(0); formatter.format("%08X", clusters[i]);
				dict.put(ZigBeeDevice.CLUSTERS_INPUT_ID+"["+i+"]","0x"+formatted.toString().substring(4)+" ("+clusters[i]+")");
			}
		}

		clusters = zb.getOutputClusters();
		if(clusters != null ) {
			for (int i = 0; i < clusters.length; i++) {
				formatted.setLength(0); formatter.format("%08X", clusters[i]);
				dict.put(ZigBeeDevice.CLUSTERS_OUTPUT_ID+"["+i+"]","0x"+formatted.toString().substring(4)+" ("+clusters[i]+")");
			}
		}
		
		makeProperties(dict);
	}
	/*
	private void makeProperties(HADevice ha){
		Dictionary dict = ha.getZBDevice().getDescription();
		int size = dict.size();
		String[] names = new String[size];
		String[] values = new String[size];
		Enumeration keys = dict.keys();
		for (int i=0;i<size;i++){
			names[i]= (String) keys.nextElement();
			values[i]= Util.justString(dict.get(names[i]));
		}
		Mediator.getPropertiesViewer().setProperties(names,values);
	}
	*/
	private void makeProperties(Command service){
		Mediator.getPropertiesViewer().setProperties(new String[]{}, new String[]{});
	}
	
	private void makeProperties(Cluster service){
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		names.add("Id");
		formatted.setLength(0);
		formatter.format("%08X", service.getId());
		values.add("0x"+formatted.toString().substring(4));
		names.add("Name");
		values.add(service.getName());
		Mediator.getPropertiesViewer().setProperties(
				(String[])names.toArray(new String[]{}),
				(String[])values.toArray(new String[]{})
		);
	}

// TODO Command 	
//	private void makeProperties(UPnPAction action){
//		ArrayList names = new ArrayList();
//		ArrayList values = new ArrayList();
//		names.add("Name");
//		values.add(action.getName());
//		
//		String returnName = action.getReturnArgumentName();
//		if (returnName != null){
//			names.add("Return value name");
//			values.add(returnName);
//		}
//		String[] inArg = action.getInputArgumentNames();
//		if (inArg != null){
//			for (int i = 0; i<inArg.length;i++){
//				names.add("Input arg["+ (i+1)+"]");
//				values.add(inArg[i]);			
//			}
//		}
//		String[] outArg = action.getOutputArgumentNames();
//		if (outArg != null){
//			for (int i = 0; i<outArg.length;i++){
//				names.add("Output arg["+ (i+1)+"]");
//				values.add(outArg[i]);			
//			}
//		}
//		
//		Mediator.getPropertiesViewer().setProperties(
//				(String[])names.toArray(new String[]{}),
//				(String[])values.toArray(new String[]{})
//		);
//		
//	}
	
	private void makeProperties(Attribute state){
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		names.add("Id");
		formatted.setLength(0);
		formatter.format("%08X", state.getId());
		values.add("0x"+formatted.toString().substring(4));
		names.add("Name");
		values.add(state.getName());
		names.add("Writeable");
		values.add(state.isWritable()? "yes":"no");
		names.add("Reportable");
		values.add(state.isReportable()? "yes":"no");
		names.add("Java Type");
		values.add(state.getType().getName());
		names.add("ZCL Type");
		values.add(state.getZigBeeType().toString());
		Mediator.getPropertiesViewer().setProperties(
				(String[])names.toArray(new String[]{}),
				(String[])values.toArray(new String[]{})
		);
	}
	
	
}

class TreePopup extends JPopupMenu implements PopupMenuListener {
    JTree tree;
    JMenuItem item;

    public TreePopup(final JTree tree){
        super();
        this.tree = tree;
        (item = add(new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                HADeviceTreeNode selectedNode = (HADeviceTreeNode)tree.getLastSelectedPathComponent();   
                String url = "";
                if (selectedNode.category.equals(HADeviceTreeNode.ZIGBEE_DEVICE)){
//                    HADeviceTreeNode parent =  (HADeviceTreeNode)selectedNode.getParent();
//                    while (parent.category!=HADeviceTreeNode.ROOT_DEVICE)
//                         parent =  (HADeviceTreeNode)parent.getParent();
//                    DeviceNode device =  (DeviceNode) parent.getUserObject();
//                    String udn = (String)device.getReference().getProperty(HADevice.ZIGBEE_DEVICE_UUID);
//                    url = Mediator.getDriverProxy().getDeviceDescriptionURI(udn);
                }
                        
                else if (selectedNode.category.equals(HADeviceTreeNode.HA_DEVICE))
                {
//                    DeviceNode node =  (DeviceNode) selectedNode.getUserObject();
//                    String udn = (String)node.getReference().getProperty(HADevice.ZIGBEE_DEVICE_UUID);
//                    url = Mediator.getDriverProxy().getDeviceDescriptionURI(udn);
                }
                else if (selectedNode.category.equals(HADeviceTreeNode.SERVICE))
                {
                    HADeviceTreeNode parent =  (HADeviceTreeNode)selectedNode.getParent();
                    while (parent.category!=HADeviceTreeNode.HA_DEVICE)
                         parent =  (HADeviceTreeNode)parent.getParent();
                    DeviceNode device =  (DeviceNode) parent.getUserObject();
                    String udn = (String)device.getReference().getProperty(HADevice.ZIGBEE_DEVICE_UUID);
                    Cluster service =  (Cluster) selectedNode.getUserObject();
//                    url = Mediator.getDriverProxy().getServiceDescriptionURI(udn,service.getId());
                }                    
                Util.openUrl(url);   
            }
        })).setText("Show Description");
        addPopupMenuListener(this);

    }
    
    public void popupMenuCanceled(PopupMenuEvent e){}
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e){}
    public void popupMenuWillBecomeVisible(PopupMenuEvent e){
//        if (Mediator.getDriverProxy().isDriverAvailable()){
//        HADeviceTreeNode selectedNode = (HADeviceTreeNode)tree.getLastSelectedPathComponent();              
//            if (selectedNode.category.equals(HADeviceTreeNode.DEVICE)
//                ||selectedNode.category.equals(HADeviceTreeNode.ROOT_DEVICE)
//                ||selectedNode.category.equals(HADeviceTreeNode.SERVICE))
//            {
//                item.setEnabled(true);
//            } 
//            else
//                item.setEnabled(false);
//        }
//        else
//            item.setEnabled(false);
           
    }
}
