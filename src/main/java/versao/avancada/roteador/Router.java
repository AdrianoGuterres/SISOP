package versao.avancada.roteador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

public class Router {

    public static void main(String[] args) throws IOException {
    	
    	Semaphore mutex = new Semaphore(1);
    	
    	String localHost = InetAddress.getLocalHost().getHostAddress();
       
       final ArrayList<String> neighborsList = new ArrayList<>();
        
        try ( BufferedReader inputFile = new BufferedReader(new FileReader("src/IPVizinhos.txt"))) {
            String ip;            
            while( (ip = inputFile.readLine()) != null){
                neighborsList.add(ip); 
            }
            
        } catch (Exception ex) {
        	JOptionPane.showMessageDialog(null,"An error was thrown vhile reading the file: "+ ex);
            return;
        }        
      
        RoutingTable table = new RoutingTable(neighborsList, localHost);         
        Thread receiver = new Thread(new MessageReceiver(table, mutex));        
        Thread sender = new Thread(new MessageSender(table, neighborsList, mutex));        
          
        receiver.start();
        sender.start(); 
        
    }    
}
