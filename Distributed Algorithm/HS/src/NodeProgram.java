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
    public String out = "out";
    public String in = "in";
    public boolean asleep = true;
    public NodeMessage msg0 = null;
    public NodeMessage msg1 = null;
    public int mark = 0;
    // public int leader_uid = -1;


    // public boolean 
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
        if (asleep){

            asleep = false;
            out(0).send(new NodeMessage(uid, out, 1, false));          
            out(1).send(new NodeMessage(uid, out, 1, false));      
        }


        // HS core part
        while (true){

            int index = in().select();
            msg0 = msg1 = null;
            if (index % 2 == 0)  {
             msg0 = (NodeMessage)in(0).receive();
            }
            else  {         
             msg1 = (NodeMessage)in(1).receive();
            }
   
            // report message.
            // if ( msg0 != null && msg0.leader){


            //     leader_uid = msg0.uid;
            //     if (msg0.uid != this.uid){

            //     out(1).send(new NodeMessage(leader_uid, out, 1, true));
            //  }
            //     return;
            // }
       
            NodeMessage send0, send1;
            send0 = null;
            send1 = null;

            // suppose in left side of outbound
            if (msg1 != null && msg1.in_out.equals(out)) {
                    
                    if ((msg1.uid > this.uid) && (msg1.h > 1)) {

                        send0 = new NodeMessage (msg1.uid, out, (msg1.h) - 1, false);    
                        out(1).send(send0);                    
                    }

                    if ((msg1.uid > this.uid) && (msg1.h == 1)) {
                        send1 = new NodeMessage (msg1.uid, in, 1, false);          
                        out(0).send(send1);              
                    }
                     if (msg1.uid == this.uid) {

                        this.status = "leader";
                        // leader_uid = this.uid;
                        // send0 = new NodeMessage (msg1.uid, out, 1, true);  
                        // out(1).send(send0);  
                        return;    
                    }
            }

             // suppose in right side of outbound
            if (msg0 != null && msg0.in_out.equals(out)) {

                    if ((msg0.uid > this.uid) && (msg0.h > 1)) {

                        send1 = new NodeMessage (msg0.uid, out, (msg0.h) - 1, false);    
                        out(0).send(send1);                    
                    }

                    if ((msg0.uid > this.uid) && (msg0.h == 1)) {

                        send0 = new NodeMessage (msg0.uid, in, 1, false);          
                        out(1).send(send0);              
                    }
                     if (msg0.uid == this.uid) {

                        this.status = "leader";
                        // leader_uid = this.uid;
                        // send0 = new NodeMessage (msg0.uid, out, 1, true);  
                        // out(1).send(send0); 
                        return;            
                    }
            }

             // suppose in right side of inbound
            if (msg1 != null && (msg1.in_out.equals(in)) && (msg1.uid != this.uid) && (msg1.h == 1)) {

                        send0 = new NodeMessage (msg1.uid, in, 1, false);          
                        out(1).send(send0);                         
            }

             // suppose in right side of inbound
            if (msg0 != null && (msg0.in_out.equals(in)) && (msg0.uid != this.uid) && (msg0.h == 1)) {
     
                        send1 = new NodeMessage (msg0.uid, in, 1, false);          
                        out(0).send(send1);                         
            }

            // next phase
            if (( msg1 != null && msg1.uid == this.uid && msg1.in_out.equals(in) && msg1.h == 1 )
                 || ( msg0 != null && msg0.uid == this.uid && msg0.in_out.equals(in) && msg0.h == 1)){

                        mark++;
                             
            if (mark == 2){
   
                    this.phase ++;
                    this.h = (int)Math.pow(2, phase);
                    out(0).send(new NodeMessage(this.uid, out, h, false));
                    out(1).send(new NodeMessage(this.uid, out, h, false));
                    mark = 1;
                }
            }
            
        }

}


    @Override
    public String getText() {
        // More status param is shown in GUI.
        return String.valueOf(this.uid) + "  "+this.status + "  ";
    }
}
// 