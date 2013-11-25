import java.util.LinkedList;

import daj.Application;
import daj.Node;

/**
 * This class contains the entry point for this program, which simulates a
 * ring shaped distributed system.
 */
public class Main extends Application {

    /**
     * Used to specify a unidirectional or bidirectional edge.
     */
    private enum EdgeDirection { UNIDIRECTIONAL, BIDIRECTIONAL }

    public Main() {
        super("Distributed System Simulation", 400, 300);
    }

    /**
     * Start the simulation.
     */
    public static void main(String[] args) {
        new Main().run();
    }

    @Override
    public void construct() {
        makeNodeRing(8, 100, EdgeDirection.UNIDIRECTIONAL);
    }

    /**
     * Creates a ring of nodes for the simulator, with edges connected on the
     * circumference of the ring.
     *
     * Each node is given a random UID in the range [0, {@code numNodes} - 1].
     *
     * @param numNodes the number of nodes to be put in the simulator
     * @param radius the radius of the node ring
     * @param edgeDirection whether edges are unidirectional or bidirectional
     */
    public void makeNodeRing(final int numNodes, final int radius,
            final EdgeDirection edgeDirection) {

        Node[] nodes = new Node[numNodes];

        // set up a ring of disconnected nodes with random UIDs
        int[] uids = randomInts(nodes.length);
        for (int i = 0; i < nodes.length; i++) {
            double angle = (i / (double)nodes.length) * 2 * Math.PI;
            int x = (int) (radius * Math.cos(angle) + radius * 1.5);
            int y = (int) (radius * Math.sin(angle) + radius * 1.5);
            int uid = uids[i];
            nodes[i] = node(new NodeProgram(uid), String.valueOf(uid), x, y);
        }

        // create a unidirectional link between nodes
        for (int i = 0; i < nodes.length - 1; i++) {
            link(nodes[i], nodes[i + 1]);
        }
        link(nodes[nodes.length - 1], nodes[0]);

        if (edgeDirection == EdgeDirection.BIDIRECTIONAL) {
            // create a second unidirectional link between nodes in the opposite
            // direction, making each edge "bidirectional"
            for (int i = 0; i < nodes.length - 1; i++) {
                link(nodes[i + 1], nodes[i]);
            }
            link(nodes[0], nodes[nodes.length - 1]);
        }
    }

    /**
     * Generates an array of size {@code n} with each index containing a unique
     * integer in the range [0,n-1].
     *
     * @param n size of the result array and the range of its values
     * @return a randomly generated array of size {@code n}
     */
    public static int[] randomInts(int n) {
        LinkedList<Integer> xs = new LinkedList<Integer>();
        for (int i = 0; i < n; i++) {
            xs.add(i);
        }

        int[] ys = new int[n];
        for (int i = 0; i < n; i++) {
            ys[i] = xs.remove((int) (Math.random() * xs.size()));
        }

        return ys;
    }

    @Override
    public String getText() {
        return "Distributed System Simulator";
    }

    @Override
    public void resetStatistics() {
    }
}
