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

import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.ReportListener;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;

import org.persona.zigbee.tester.Mediator;
import org.persona.zigbee.util.Converter;


/**
 * 
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 747 $ ($LastChangedDate: 2010-04-09 18:20:34 +0200 (Fri, 09 Apr 2010) $)
 * @since 0.1.0
 *
 */
public class AttributeActionPanel extends JPanel {
	
	Attribute attribute;
	JPanel buttonPanel;
	
	private JButton readButton;
	private JButton writeButton;
	private JButton subscribeButton;
	private JTextField inputText; 
	
	private Hashtable<Attribute, ReportListener> subscription = new Hashtable<Attribute, ReportListener>();
	
	/**
	 * 
	 */
	public AttributeActionPanel() {
		super(new GridBagLayout());
		buildButtonPanel();
		add(new JScrollPane(getInputText()),Util.setConstrains(0,1,1,1,100,100)); 
		add(buttonPanel,Util.setConstrains(0,2,1,1,1,1));
	}
	
	private JTextField getInputText(){
		if( inputText != null ){
			return inputText;
		}
		inputText = new JTextField();
		return inputText;
	}

	private JButton getWriteButton(){
		if( writeButton != null ){
			return writeButton;
		}
		JButton doAction = new JButton("Write");
		doAction.addActionListener(new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				Object newValue = null;
				try {
					newValue = Converter.fromString(getInputText().getText(), attribute.getZigBeeType());
					attribute.setValue( newValue );
					LogPanel.log(
							"Set Attribute " + attribute.getName() + " to " + newValue  +
							"\n\tStatus: SUCCESS\n"
					);
				} catch (ZigBeeClusterException ex) {
					LogPanel.log(
							"Set Attribute " + attribute.getName() + " to " + newValue  +
							"\n\tStatus: FAILED\n\tException: " + ex + "\n"
					);
					ex.printStackTrace();
				}
			}
		});		
		writeButton = doAction;
		return writeButton;
	}
	
	private JButton getReadButton(){
		if( readButton != null ){
			return readButton;
		}
		JButton doAction = new JButton("Read");
		doAction.addActionListener(new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				try {
					Object result = attribute.getValue();
					LogPanel.log(
							"Read Attribute " + attribute.getName() + 
							"\n\tStatus: SUCCESS\n\tResult: " + result+ "\n"
					);
				} catch (ZigBeeClusterException ex) {
					LogPanel.log(
							"Read Attribute " + attribute.getName() + 
							"\n\tStatus: FAILED\n\tException: " + ex + "\n"
					);
					ex.printStackTrace();
				}
			}
		});		
		readButton = doAction;
		return readButton;
	}
	
	private JButton getSubscribeButton(){
		if( subscribeButton != null ){
			return subscribeButton;
		}
		JButton doAction = new JButton("Subscribe");
		doAction.addActionListener(new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				ReportListener listener = subscription.get(attribute);
				String category = null;
				if ( listener == null ){
					listener = createListener();
					if ( attribute.getSubscription().addReportListner(listener) ) {
						updateCategory(HADeviceTreeNode.SUBSCRIBED_STATE);
						subscription.put(attribute, listener);
						getSubscribeButton().setText("Unsubscribe");
					}
				} else {
					if ( attribute.getSubscription().removeReportListner(listener) ) {
						updateCategory(HADeviceTreeNode.EVENTED_STATE);
						subscription.remove(attribute);
						getSubscribeButton().setText("Subscribe");
					}
				}
			}

			private ReportListener createListener() {
				return new ReportListener() {
					public void receivedReport(Dictionary<Attribute, Object> reports) {
						Enumeration<Attribute> attributes = reports.keys();
						while (attributes.hasMoreElements()) {
							Attribute a = (Attribute) attributes.nextElement();
							Object v = reports.get(a);
							LogPanel.log("Received Event from "+a+" with value "+v);
						}
					}
				};
			}

			private void updateCategory(String category) {
				HADeviceTreeNode node = (HADeviceTreeNode) Mediator.getUPnPDeviceTree().getLastSelectedPathComponent();
			    node.category = category;
			    JTree tree = Mediator.getUPnPDeviceTree();
			    tree.validate();
			    tree.repaint();
			}
		});		
		subscribeButton = doAction;
		return subscribeButton;
	}
	
	private void buildButtonPanel(){
		buttonPanel = new JPanel();
	    buttonPanel.add(getReadButton());
    	buttonPanel.add(getWriteButton());
    	buttonPanel.add(getSubscribeButton());
	}
	
	public void setAttribute(Attribute action){
		this.attribute = action;
		
		getWriteButton().setVisible(attribute.isWritable());
		getInputText().setVisible(attribute.isWritable());
		
		if( attribute.isReportable() ){
			getSubscribeButton().setVisible(true);
			
			if( subscription.get(attribute) == null ){
				getSubscribeButton().setText("Subscribe");
			} else {
				getSubscribeButton().setText("Unsubscribe");
			}
		}else{
			getSubscribeButton().setVisible(false);
		}
	}
	
}

