package versao.avancada.roteador;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.swing.JOptionPane;

public class MessageSender implements Runnable{
	private TabelaRoteamento tabela; 
	private ArrayList<String> vizinhos; 
	private Semaphore sem;

	public MessageSender(TabelaRoteamento table, ArrayList<String> routersNextDoor, Semaphore sem){
		this.tabela = table;
		this.vizinhos = routersNextDoor;
		this.sem = sem;
	}

	@Override
	public void run() {
		int count = 0;

		DatagramSocket clientSocket = null;
		byte[] sendData;
		InetAddress IPAddress = null;
		


		while(true){
			try {
				sem.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String tabela_string = tabela.get_tabela_string();
			sem.release(); 

			sendData = tabela_string.getBytes();

			int aux = 0;
			while(aux < vizinhos.size()) {
				try {
					clientSocket = new DatagramSocket();
					IPAddress = InetAddress.getByName(this.vizinhos.get(aux));
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5000);         
					clientSocket.send(sendPacket);		
					clientSocket.close();

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,"Deu treta: "+ ex);
				}

				aux++;
			}// fim do laço do envio para os vizinhos	
			
			goSleep();
			
		}//fim do loop		
	}

	public void goSleep() {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			JOptionPane.showMessageDialog(null,"Deu treta: "+ ex);

		}
	}
}
