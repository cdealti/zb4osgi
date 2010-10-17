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
import it.cnr.isti.zigbee.ha.cluster.glue.Cluster;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;

import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.persona.zigbee.tester.Activator;
import org.persona.zigbee.tester.Options;
import org.persona.zigbee.tester.discovery.DeviceNode;


/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.3.0
 *
 */
public class TreeNodeBindPopupMenu
    extends JPopupMenu
    implements PopupMenuListener {

    JTree tree;
    final JMenuItem bind;
    final JPopupMenu targets;
    final Action action;
    HADeviceTreeNode lastNode = null;
    
    public TreeNodeBindPopupMenu(final JTree tree){
        super();
        this.tree = tree;
        bind = new JMenuItem("Bind To");
        targets = new JPopupMenu();
        bind.add( targets );
        action = new AbstractAction(){

            public void actionPerformed( ActionEvent e ) {
                final JMenuItem item = (JMenuItem) e.getSource();
                final String uuidTo = item.getName();
                final Cluster binding = (Cluster) lastNode.getUserObject();
                final int clusterId = binding.getId();
                final DeviceNode device = (DeviceNode) ( (HADeviceTreeNode) lastNode.getParent() ).getUserObject();
                final String uuidFrom = (String) device.getReference().getProperty( HADevice.ZIGBEE_DEVICE_UUID );
                final String filter = 
                    "(|" +
                    "(" + ZigBeeDevice.UUID + "=" + uuidFrom + ")" +
                    "(" + ZigBeeDevice.UUID + "=" + uuidTo + ")" +
                    ")";
                try {
                    ServiceReference[] pair = Activator.context.getServiceReferences( ZigBeeDevice.class.getName(), filter );
                    if ( pair == null || pair.length != 2 ) {
                        return;
                    }
                    final ZigBeeDevice fromDevice;
                    final ZigBeeDevice toDevice;
                    if ( pair[0].getProperty( ZigBeeDevice.UUID ).equals( uuidFrom ) ){
                        fromDevice = (ZigBeeDevice) Activator.context.getService( pair[0] );
                        toDevice = (ZigBeeDevice) Activator.context.getService( pair[1] );
                    } else {
                        toDevice = (ZigBeeDevice) Activator.context.getService( pair[0] );
                        fromDevice = (ZigBeeDevice) Activator.context.getService( pair[1] );
                    }
                    if ( fromDevice == null || toDevice == null ) {
                        return;
                    }
                    if ( Activator.options.get( Options.AlwaysDoubleBinding ) == Boolean.TRUE ){
                        fromDevice.bindTo( toDevice, clusterId );
                        toDevice.bindTo( fromDevice, clusterId );
                    } else {
                        fromDevice.bindTo( toDevice, clusterId );                        
                    }
                }
                catch ( Throwable ex ) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream( out );
                    ex.printStackTrace( ps );
                    ps.flush();
                    ps.close();
                    LogPanel.log( out.toString() );
                }
                
            }
            
        };
    }    
    
    public void popupMenuCanceled( PopupMenuEvent e ) { }
    
    public void popupMenuWillBecomeInvisible( PopupMenuEvent e ) { }

    public void popupMenuWillBecomeVisible( PopupMenuEvent e ) {
        HADeviceTreeNode selectedNode = (HADeviceTreeNode) tree.getLastSelectedPathComponent();
        if ( lastNode != selectedNode && selectedNode.category == HADeviceTreeNode.SERVICE ) {
            lastNode = selectedNode;
            generateSubItems(lastNode);
        } else {
            bind.setEnabled( selectedNode.category == HADeviceTreeNode.SERVICE );
        }
    }

    private void generateSubItems(HADeviceTreeNode node) {
        final String filter;
        final Cluster binding = (Cluster) node.getUserObject();
        if ( Activator.options.get( Options.StrictBinding ) == Boolean.TRUE ){
            filter = "( " + ZigBeeDevice.CLUSTERS_OUTPUT_ID + "=" + binding.getId() + ")";
        } else {
            filter = null;
        }
        try {
            ServiceReference[] refs = Activator.context.getServiceReferences( ZigBeeDevice.class.getName(), filter );
            JMenuItem item;
            if ( refs == null ) {
                item = new JMenuItem("No valid target");
                bind.add( item );
            }
            for ( int i = 0; i < refs.length; i++ ) {
                item = new JMenuItem( (String) refs[i].getProperty( ZigBeeDevice.UUID ) );
                item.setAction( action );
                bind.add( item );
            }
        } catch ( InvalidSyntaxException e ) {
        }
    }

}
