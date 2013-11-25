import daj.Message;

/**
 * A message that can be sent between nodes in a distributed network.
 */
class NodeMessage extends Message
{
    /**
     * The unique identifier of the node that sent this {@code NodeMessage}.
     * @param leader, is to show whether the process is leader or not.
     *
     */
    public final int uid;
    public boolean leader = false;

    public NodeMessage(int uid, boolean leader) {
        this.uid = uid;
        this.leader = leader;
    }

    @Override
    public String getText() {
        // More @param leader to show in channel.
        return String.valueOf(uid)+"  " + leader;
    }
}
