package versao.avancada.roteador;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.concurrent.Semaphore;

import javax.swing.JOptionPane;

public class MessageSender implements Runnable{
	private TabelaRoteamento table; 
	private HashSet<String> neighborIPs; 
	private Semaphore sem;

	public MessageSender(TabelaRoteamento table, HashSet<String> neighborIPs, Semaphore sem){
		this.table = table;
		this.neighborIPs = neighborIPs;
		this.sem = sem;
	}

	@Override
	public void run() {
		
		DatagramSocket clientSocket = null;
		byte[] sendData = new byte[1024];
		InetAddress IPAddress = null;
		
		while(true){
			
			// Entrando na área critica do código
			try {
				sem.acquire();
			} catch (InterruptedException ex) {
				JOptionPane.showMessageDialog(null,"Deu treta acquire do sender: "+ ex);
			}

			String tabela_string = table.get_tabela_string();
			sem.release(); 
			
			// Saindo da área critica do código

			if(table.getSizeNeighborActive() > 0) {
				try {
					sendData = tabela_string.getBytes();				
					for (String x : neighborIPs) {
						clientSocket = new DatagramSocket();
						IPAddress = InetAddress.getByName(x);					
						
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5001);  					     
						clientSocket.send(sendPacket);		
						clientSocket.close();	
						
						if(table.isChanged() == true) {
							System.out.println(table.getSizeNeighborActive()+"la"+table.getSizeNeighborActive());
							Thread.sleep(1000);														
						}else {
							System.out.println(table.getSizeNeighborActive()+"lalala");
							Thread.sleep(3000);
						}						
					}
					
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,"Deu treta no sender com rota: "+ ex);
				}				

			}else {
				try {
					
					System.out.println("entrou aqui");
					byte[] sendDataAux = ("!").getBytes();
					clientSocket = new DatagramSocket();
					DatagramPacket sendPacket = new DatagramPacket(sendDataAux, 1024, 5000);         
					clientSocket.send(sendPacket);		
					clientSocket.close();
					
					Thread.sleep(10000);
					
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,"Deu treta no sender sem rota: "+ ex);
				}
			}
		}
	}
}
