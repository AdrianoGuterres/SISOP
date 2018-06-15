package versao.avancada.roteador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class Roteador {

    public static void main(String[] args) throws IOException {
    	
    	Semaphore mutex = new Semaphore(1);
    	String localHost = InetAddress.getLocalHost().getHostAddress();
       
       final HashSet<String> neighborsSet = new HashSet<>();
        
        try ( BufferedReader inputFile = new BufferedReader(new FileReader("src/IPVizinhos.txt"))) {
            String ip;            
            while( (ip = inputFile.readLine()) != null){
                neighborsSet.add(ip);
            }
            
        } catch (Exception ex) {
        	JOptionPane.showMessageDialog(null,"Deu treta na leituras do arquivo: "+ ex);
            return;
        }        
      
        TabelaRoteamento tabela = new TabelaRoteamento(neighborsSet);        
        Thread receiver = new Thread(new MessageReceiver(tabela, mutex));        
        Thread sender = new Thread(new MessageSender(tabela, neighborsSet, mutex));
        
        sender.start();   
        receiver.start();
        
    }    
}
