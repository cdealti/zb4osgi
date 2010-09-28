package org.aaloa.zb4osgi.network.browser.ui;

import it.cnr.isti.zigbee.api.ZigBeeNode;

import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

public class NetworkGraph extends JFrame {
    
    private SparseMultigraph<ZigBeeNode, String> graph;
    private VisualizationViewer<ZigBeeNode, String> viewer;

    
    private class ZigBeeNodeLocal implements ZigBeeNode {

        private int nwk;
        private String ieee;                
        
        public ZigBeeNodeLocal( int nwk, String ieee ) {
            this.nwk = nwk;
            this.ieee = ieee;
        }

        public Dictionary getDescription() {
            return null;
        }

        public String getIEEEAddress() {
            return ieee;
        }

        public int getNetworkAddress() {
            return nwk;
        }

        public String toString(){
            return nwk + "/" + getIEEEAddress();
        }
        
        public boolean equals( Object obj ){
            final ZigBeeNode n = (ZigBeeNode) obj;
            return n.getNetworkAddress()==nwk && n.getIEEEAddress().equals( ieee );
        }
        
        public int hashCode(){
            return toString().hashCode();
        }
        
    }
    
    public NetworkGraph() {
        graph = new SparseMultigraph<ZigBeeNode, String>();
        viewer =  new VisualizationViewer<ZigBeeNode, String>( new FRLayout<ZigBeeNode, String>( graph ) );
        getContentPane().add( viewer );
                
        HashMap<Integer, ZigBeeNode> vertexes = new HashMap<Integer, ZigBeeNode>();
        vertexes.put( 2468, new ZigBeeNodeLocal( 2468, "0x0011223344556677") );
        vertexes.put(    0, new ZigBeeNodeLocal(    0, "0x7766554433221100") );
        vertexes.put( 1357, new ZigBeeNodeLocal( 1357, "0x0011223377665544") );
        vertexes.put( 3825, new ZigBeeNodeLocal( 2468, "0x7766554400112233") );
        
        
        graph.addEdge( "Root-A", vertexes.get( 0 ), vertexes.get( 2468 ), EdgeType.DIRECTED );
        graph.addEdge( "Root-B", vertexes.get( 0 ), vertexes.get( 1357 ), EdgeType.DIRECTED );
        graph.addEdge( "B-C", vertexes.get( 1357 ), vertexes.get( 3825 ), EdgeType.DIRECTED );
        graph.addEdge( "C-B", vertexes.get( 3825 ), vertexes.get( 1357 ), EdgeType.DIRECTED );
        graph.addEdge( "C<->B", vertexes.get( 3825 ), vertexes.get( 1357 ) );

        Transformer<ZigBeeNode, Paint> transformerNode = new Transformer<ZigBeeNode, Paint>() {
            
            public Paint transform( ZigBeeNode node ) {
                if ( node.getNetworkAddress() == 0 ) {
                    return Color.RED;
                }
                return Color.GREEN;
            }
        };

        viewer.getRenderContext().setVertexFillPaintTransformer( transformerNode );
        viewer.getRenderContext().setVertexLabelTransformer( new ToStringLabeller<ZigBeeNode>() );
        viewer.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
        
        DefaultModalGraphMouse<ZigBeeNode, Paint> hci = new DefaultModalGraphMouse<ZigBeeNode, Paint>();
        hci.setMode( ModalGraphMouse.Mode.PICKING  );
        viewer.setGraphMouse( hci );
        
        pack();
        setVisible( true );
    }
    
    public static void main(String[] args){
        new NetworkGraph();
    }

}
