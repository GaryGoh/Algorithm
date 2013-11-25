import daj.Message;
import daj.Program;

/**
 * A program that runs on each node of a distributed system.
 */
class NodeProgram extends Program
{
    /**
     * The unique identifier of the node running this program.
     */
    private final int uid;
    private int state = 0;
    private String status = "non-leader";
    private int leader_uid = -1;  

    /**
     * @param uid the unique identifier for the node on which this
     * {@code NodeProgram} is run.
     */
    public NodeProgram(int uid) {
        this.uid = uid;
    }

    /**
     * The program that will run on each node in a distributed system; not to be
     * confused with {@code public static void main()}.
     */
    public void main() {

        // TODO

        /*
        * More params in following:
        *
        * @param status, indicate the leader status in the GUI.
        * @param msg, indicate the incoming message with UID.
        * @param send, indicate the message with UID is going to send to next node.
        * @param leader_uid, is used for indicating leader id in report message.
        *
        */



        // initialized
        if (state == 0){

            state ++;
            out(0).send(new NodeMessage(uid, false));           
        }

        // LCR core part
        while (true){

            int index = in().select();
            NodeMessage msg = (NodeMessage)in(index).receive();
   
            // report message.
            if (msg.leader){

                leader_uid = msg.uid;
                if (msg.uid != this.uid){

                out(index).send(new NodeMessage(leader_uid, true));
             }
                return;
            }
       

            NodeMessage send = null;

            if (msg.uid > this.uid){

                send = msg;
                out(index).send(send);
            }

            else if (msg.uid == this.uid){

                    this.status = "leader";
                    leader_uid = this.uid;
                    send = new NodeMessage(this.uid, true);
                    out(index).send(send);
            }

            else if ( (msg.uid < this.uid)){
                // Do nothing
            }
            
        }

}


    @Override
    public String getText() {
        // More status param is shown in GUI.
        return String.valueOf(this.uid) + "  "+this.status;
    }
}
