package TokenRing;

import java.io.BufferedReader;
import java.io.FileReader;

public class TokenRing {
    
    // 127.0.0.2:5001
    // Boby
    // 1
    // false

    public static void main(String[] args) {
        String ip; int port; int t_token = 0; boolean token = false; String nickname;
       
        try ( BufferedReader inputFile = new BufferedReader(new FileReader("ring.cfg"))) {
            
            String linha = inputFile.readLine();
            String aux[] = linha.split(":");            
            ip = aux[0];
            port = Integer.parseInt(aux[1]);        
            nickname = inputFile.readLine();
            t_token = Integer.parseInt(inputFile.readLine());
            token = Boolean.parseBoolean(inputFile.readLine());
            
        } catch (Exception ex) {
            System.out.println("Deu erro: "+ ex);
            return;
        }    
        
        MessageQueue queue = new MessageQueue();
        
        JanelaChat janela = new JanelaChat();       
        
        MessageController controller = new MessageController(queue, ip, port, t_token, token, nickname, janela);
        
        Thread thr_controller = new Thread(controller);
       
        janela.setVisible(true); 
        
        thr_controller.start();
              
    }
    
}
