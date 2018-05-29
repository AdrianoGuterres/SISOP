package TokenRing;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.Semaphore;

// Bob porta destino 5000## ip destino 2  
public class MessageController implements Runnable {

    private MessageQueue fila;               private InetAddress IPDestino;  
    private InetAddress IPLocal;             private int portaDestino, time_token;
    private Semaphore WaitForMessage;        private String nickname;         
    private Boolean token;                   private JanelaChat janela;

    private DatagramSocket socketReceiver;   private boolean esperaACK = false;

    public MessageController(MessageQueue q, String ip, int port, int t_token, Boolean t, String n, JanelaChat ja) {

        this.fila = q;
        this.portaDestino = port;
        try {
            this.IPDestino = InetAddress.getByName(ip);
            this.IPLocal = InetAddress.getByName("127.0.0.2");
            this.time_token = t_token;
            this.token = t;
            this.nickname = n;
            this.janela = ja;

            janela.novaFila(fila);
            janela.setNicname(nickname);
            janela.setPorta(5002);
            janela.setToken(token);
            janela.setTamanhoFila(fila.getSize());
            

            socketReceiver = new DatagramSocket(5002, IPLocal);
            janela.addLog("Starting receiver socket on port 5000 IP"+ IPLocal);
            atualizaLogTokenAck();
            
        } catch (SocketException | UnknownHostException ex) {
            System.err.println(ex);
        }

        WaitForMessage = new Semaphore(0);
    }

       @Override
    public void run() {
        try {
            
            if (token == true && esperaACK == false ) {                
                byte[] sendData = new byte[1024];
                DatagramSocket serverSender = new DatagramSocket();
                String sentence = new String("4060");                
                sendData = sentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPDestino, portaDestino);                
                            
                serverSender.send(sendPacket);
                token = false;
                esperaACK = false;
                
                janela.addLog("Sending: 4060" );
                atualizaLogTokenAck();
            }

            while (true) {
                DatagramSocket serverSender = new DatagramSocket();
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socketReceiver.receive(receivePacket);                
                DatagramPacket packetEnvio = receivedDatagram(receivePacket);
                serverSender.send(packetEnvio);
                
                Thread.sleep(3000);
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public DatagramPacket receivedDatagram(DatagramPacket packet) {
        byte[] sendData = new byte[1024];
        String sentence = new String(packet.getData());
        String[] array = sentence.split(";");
        String mensagemRetorno = "";
        
                

        switch (array[0].trim()) {
            case "4060":      
                token = true;
                janela.addLog("receiving: "+ sentence);
                atualizaLogTokenAck();                
                if (fila.getSize()== 0) {  
                    esperaACK = false;
                    token = false;
                    janela.addLog("Sending:  "+ sentence);
                    atualizaLogTokenAck();
                    mensagemRetorno = sentence;
                                        
                } else {
                    esperaACK = true;    
                    mensagemRetorno = fila.RemoveMessage(); 
                    String aux = mensagemRetorno.replaceAll(";",":");
                    String[] restoArray = aux.split(":");

                    String remetente = restoArray[1];
                    String destino   = restoArray[2];
                    String mensagem  = restoArray[3];
                    
                    janela.addMensagem("Message to "+destino+": "+mensagem);
                    
                    
                    janela.addLog("Sending:  "+ mensagemRetorno);  
                    atualizaLogTokenAck();
                    janela.setTamanhoFila(fila.getSize());
                    
                    
                }
                break;
            case "4067":
                janela.addLog("receiving: "+ sentence);
                atualizaLogTokenAck();
                
                String aux = array[1];
                if (aux.trim().equalsIgnoreCase(nickname)) {
                    mensagemRetorno = "4060";
                    
                    esperaACK = false;
                    token= false;
                    janela.addLog("Sending:  "+ mensagemRetorno);
                    atualizaLogTokenAck();
                } else {
                    mensagemRetorno = sentence;
                    janela.addLog("Sending:  "+ mensagemRetorno);
                    atualizaLogTokenAck();
                }
                break;
            case "4066":
                janela.addLog("receiving: "+ sentence);atualizaLogTokenAck();
                atualizaLogTokenAck();
                
                String[] restoArray = array[1].trim().split(":");

                String remetente = restoArray[0];
                String destino = restoArray[1];
                String mensagem = restoArray[2];

                if (destino.equalsIgnoreCase(nickname)) {
                    System.out.println("Message from " + remetente + ": " + mensagem);
                    mensagemRetorno = "4067;" + remetente;
                    
                    janela.addMensagem("Message from " + remetente + ": " + mensagem);   
                    janela.addLog("Sending:  "+ mensagemRetorno);atualizaLogTokenAck();
                    atualizaLogTokenAck();
                } else {
                    mensagemRetorno = sentence;
                    janela.addLog("Sending:  "+ mensagemRetorno);
                    atualizaLogTokenAck();
                }
                break;
            default:
                mensagemRetorno = "Destinaraio inexistente";
        }

        sendData = mensagemRetorno.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPDestino, portaDestino);
        return sendPacket;
    }
    
    public void atualizaLogTokenAck(){
        janela.addLog("I have a token:        " + ((token==true)? "Yes":"No"));
        janela.addLog("Waiting for an ack: " + ((esperaACK==true)? "Yes":"No"));
        janela.addLog("");
        
    }
}