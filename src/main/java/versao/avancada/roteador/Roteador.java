package versao.avancada.roteador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class Roteador {

    public static void main(String[] args) throws IOException {
    	
    	Semaphore mutex = new Semaphore(1);
       
        ArrayList<String> routesNextDoor = new ArrayList<>();
        
        try ( BufferedReader inputFile = new BufferedReader(new FileReader("src/IPVizinhos.txt"))) {
            String ip;            
            while( (ip = inputFile.readLine()) != null){
                routesNextDoor.add(ip);
            }
            
        } catch (Exception ex) {
        	JOptionPane.showMessageDialog(null,"Deu treta: "+ ex);
            return;
        }        
      
        TabelaRoteamento tabela = new TabelaRoteamento(routesNextDoor);        
        Thread receiver = new Thread(new MessageReceiver(tabela, mutex));        
        Thread sender = new Thread(new MessageSender(tabela, routesNextDoor, mutex));
        
        receiver.start();
        sender.start();   
        
                 
        
    }
    
}
