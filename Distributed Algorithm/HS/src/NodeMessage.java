import daj.Message;

/**
 * A message that can be sent between nodes in a distributed network.
 */
class NodeMessage extends Message
{
    /**
     * The unique identifier of the node that sent this {@code NodeMessage}.
     * @param in_out, is to show the message is in or out. For simplify, here will use boolean to indicate them:
     *                  in => false
     *                  out => true
     * @param h, is the number of hop of the sent message.
     * @param leader, is to show the process is leader or not.
     *  
     *
     */
    public final int uid;
    public String in_out = null;
    public int h;
    public boolean leader = false;

    public NodeMessage(int uid, String in_out, int h, boolean leader) {
        this.uid = uid;
        this.in_out = in_out;
        this.h = h;
        this.leader = leader;
    }

    @Override
    public String getText() {
        // More @param leader to show in channel.
        return String.valueOf(uid)+"  " + leader;
    }
}
