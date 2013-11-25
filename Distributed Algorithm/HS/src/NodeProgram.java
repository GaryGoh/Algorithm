import daj.Message;
import daj.Program;
import java.lang.*;

/**
 * A program that runs on each node of a distributed system.
 */
class NodeProgram extends Program
{
    /**
     * The unique identifier of the node running this program.
     */
    private final int uid;
    private int phase = 0;
    private int h = 1;
    private String status = "non-leader";
    private int leader_uid = -1;  
    public String out = "out";
    public String in = "in";
    public int state = 0;
    public NodeMessage msg0 = new NodeMessage (-1, "", 0, false);
    public NodeMessage msg1 = new NodeMessage (-1, "", 0, false);
    public int test = 0;
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
            out(0).send(new NodeMessage(uid, out, h, false));          
            out(1).send(new NodeMessage(uid, out, h, false));          

            // out(0).send(new NodeMessage(uid, out, h, false));  
        }

        // HS core part
        while (true){

            int index = in().select();
            if (index % 2 == 0)  {
            msg0 = (NodeMessage)in(index).receive();
            }
            else  {         
            msg1 = (NodeMessage)in(index).receive();
            }

            // NodeMessage send- = (NodeMessage)in(index+1).receive();
            // NodeMessage msg- = (NodeMessage)in(index + 1).receive();
   
            // report message.
            if (msg0.leader){

                // test++;

                leader_uid = msg0.uid;
                if (msg0.uid != this.uid){

                out(0).send(new NodeMessage(leader_uid, out, 1, true));
                // out(1).send(new NodeMessage(leader_uid, out, h, true));
             }
                return;
            }
       
            NodeMessage send0, send1;
            send0 = null;
            send1 = null;

            // suppose in left side of outbound
            if (msg1.in_out.equals(out)) {
                                        // test++;

                    if ((msg1.uid > this.uid) && (h > 1)) {

                        send0 = new NodeMessage (msg1.uid, out, h - 1, false);    
                        out(0).send(send0);                    
                    }

                    if ((msg1.uid > this.uid) && (h == 1)) {

                        send1 = new NodeMessage (msg1.uid, out, 1, false);          
                        out(1).send(send1);              
                    }
                     if (msg1.uid == this.uid) {

                        this.status = "leader";
                        leader_uid = this.uid;
                        send0 = new NodeMessage (msg1.uid, out, 1, true);  
                        // send- = new NodeMessage (msg-.uid, out, h, true);
                        out(0).send(send0);      
                        // out(1).send(send-);                
                    }
            }

             // suppose in right side of outbound
            if (msg0.in_out.equals(out)) {
                                        // test++;

                    if ((msg0.uid > this.uid) && (h > 1)) {

                        send1 = new NodeMessage (msg0.uid, out, h - 1, false);    
                        out(1).send(send1);                    
                    }

                    if ((msg0.uid > this.uid) && (h == 1)) {

                        send0 = new NodeMessage (msg0.uid, out, 1, false);          
                        out(0).send(send0);              
                    }
                     if (msg0.uid == this.uid) {
                        test++;

                        this.status = "leader";
                        leader_uid = this.uid;
                        send0 = new NodeMessage (msg0.uid, out, 1, true);  
                        // send- = new NodeMessage (msg+.uid, out, h, true);  
                        out(0).send(send0); 
                        // out(1).send(send-);                     
                    }
            }

             // suppose in right side of inbound
            if ((msg1.in_out.equals(in)) && (msg1.uid != this.uid) && (msg1.h == 1)) {

                        send0 = new NodeMessage (msg1.uid, in, 1, false);          
                        out(0).send(send0);                         
            }

             // suppose in right side of inbound
            if ((msg0.in_out.equals(in)) && (msg0.uid != this.uid) && (msg1.h == 1)) {

                        send1 = new NodeMessage (msg0.uid, in, 1, false);          
                        out(1).send(send1);                         
            }

            // next phase
            NodeMessage tempMsg = new NodeMessage (this.uid, in, 1, false);
            if ( (msg0 == tempMsg) && (msg1 == tempMsg)){
                                        // test++;

                    this.phase ++;
                    this.h = (int)Math.pow(2, phase);
                    out(0).send(new NodeMessage(this.uid, out, h, false));
                    out(1).send(new NodeMessage(this.uid, out, h, false));

            }

            // if ( (msg.uid < this.uid)){
            //     // Do nothing
            // }
            
        }

}


    @Override
    public String getText() {
        // More status param is shown in GUI.
        return String.valueOf(this.uid) + "  "+this.status + "  " + test;
    }
}
