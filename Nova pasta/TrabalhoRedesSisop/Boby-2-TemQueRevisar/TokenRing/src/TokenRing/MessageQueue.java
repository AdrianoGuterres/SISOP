package TokenRing;

import java.util.ArrayList;

public class MessageQueue {
    ArrayList<String> queue ;
    
    public MessageQueue(){
        queue = new ArrayList<>();
    }    
    
    public void AddMessage(String Message){
      queue.add(Message);    
    }
    
    public String RemoveMessage(){
        String aux = queue.get(0);
        queue.remove(0);      
        return aux;
    }   
    
    public int getSize(){
        return queue.size();
    }
}
