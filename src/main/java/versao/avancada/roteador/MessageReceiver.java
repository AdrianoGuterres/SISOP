package versao.avancada.roteador;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

public class MessageReceiver implements Runnable{
	private RoutingTable table;
	private Semaphore sem;

	public MessageReceiver(RoutingTable t, Semaphore sem){
		this.table = t;
		this.sem = sem;

	} 


	@Override
	public void run() {



		DatagramSocket serverSocket = null;

		try {	serverSocket = new DatagramSocket(5000); } catch (SocketException ex) {	JOptionPane.showMessageDialog(null,"The datagram couldn't be initialized: "+ ex);}

		byte[] receiveData = new byte[1024];

		while(true){
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);			

			try { serverSocket.receive(receivePacket); } catch (IOException ex) { JOptionPane.showMessageDialog(null,"The socket couldn't be initialized: "+ ex); }

			String tabela_string = new String( receivePacket.getData());            
			String stringWithoutBlankSpace = tabela_string.trim();  
			InetAddress datagramHost = receivePacket.getAddress();
			String ipNeighbor = datagramHost.getHostAddress();

			//Entrando na área crítica
			try {
				sem.acquire();				
				table.updateTabela(stringWithoutBlankSpace, ipNeighbor); 
			} catch (Exception ex) { JOptionPane.showMessageDialog(null,"The table update coultn't be sended : "+ ex); }  
			sem.release();
		}
		//Saindo da área crítica
	}

}
