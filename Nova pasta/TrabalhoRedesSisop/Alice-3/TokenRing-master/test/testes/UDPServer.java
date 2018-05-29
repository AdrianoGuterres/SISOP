
package testes;

import java.net.*;

class UDPServer { 

    public static void main(String args[]) {
        try {
            boolean token = true;
                        
            DatagramSocket serverReceiver = new DatagramSocket(5000); 
            DatagramSocket serverSender   = new DatagramSocket();
            byte[] receiveData = new byte[1024];
            
            
            if(token){
                token = false;
                byte[] sendData = new byte[1024];
                
                //coloca o conteudo do datagrama em uma string e imprime
                String sentence = new String("4060");
                System.out.println("Datagrama UDP enviado... "+sentence);
                                                
                // passa a string sentence para o array de bytes sendData
                sendData = sentence.getBytes();
                
                // cria num packet para envio e atribui o endereço de ip recebido e a porta que vai ser enviado
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("127.0.0.2"), 5001);
                
                System.out.println("Enviando Datragrama udp ... " + sentence +" "+InetAddress.getByName("127.0.0.2") );
                
                serverSender.send(sendPacket);
                
            }
            
            
            while (true) {
                
                //Cria um packet para receber o datagrama
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                System.out.println("Esperando por datagrama UDP na porta " + 5000);
               
                serverReceiver.receive(receivePacket);
                
                DatagramPacket packetEnvio = receivedDatagram(receivePacket);
                
                serverSender.send(packetEnvio);
                
                Thread.sleep(200);
                
                System.out.println("\n");
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
    
    public static DatagramPacket receivedDatagram(DatagramPacket packet) throws UnknownHostException{
        byte[] sendData = new byte[1024];
        
        //coloca o conteudo do datagrama em uma string e imprime
            String sentence = new String(packet.getData());
            System.out.println("Datagrama UDP recebido... "+sentence);
            
            // passa a string sentence para o array de bytes sendData           
            sendData = sentence.getBytes();
            
            // cria num packet para envio e atribui o endereço de ip recebido e a porta que vai ser enviado
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("127.0.0.2"), 5001);

            System.out.println("Enviando Datragrama udp ... " + sentence +" "+InetAddress.getByName("127.0.0.2"));
                                
        return sendPacket;
    }
}