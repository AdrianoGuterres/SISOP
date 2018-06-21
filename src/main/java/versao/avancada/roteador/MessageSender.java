package versao.avancada.roteador;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

public class MessageSender implements Runnable{
	private RoutingTable table; 
	private ArrayList<String> neighborIPs; 
	private Semaphore sem;

	public MessageSender(RoutingTable table, ArrayList<String> neighborIPs, Semaphore sem){
		this.table = table;
		this.neighborIPs = new ArrayList<>();
		this.neighborIPs = neighborIPs;
		this.sem = sem;
	}

	@Override
	public void run() {
		
		DatagramSocket clientSocket = null;
		byte[] sendData;
		InetAddress IPAddress = null;
		
		while(true){
			
			// Entrando na área critica do código
			try {
				sem.acquire();
			} catch (InterruptedException ex) { 
				JOptionPane.showMessageDialog(null,"The table update coultn't be loaded : "+ ex);
			}

			String tabela_string = table.get_tabela_string();
			sem.release(); 
			
			// Saindo da área critica do código
				try {
					sendData = tabela_string.getBytes();				
					for (String x : neighborIPs) {
						clientSocket = new DatagramSocket();
						IPAddress = InetAddress.getByName(x);					
						
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5001);  					     
						clientSocket.send(sendPacket);		
						clientSocket.close();	
						
						if(table.isWereChanged() == true) {
							Thread.sleep(1000);		
						}else {
							Thread.sleep(1000);
						}						
					}
					
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,"The datagram couldn't be sent: "+ ex);
				}			
		}
	}
}
