package versao.avancada.roteador;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class MessageReceiver implements Runnable{
	private TabelaRoteamento tabela;
	private Semaphore sem;

	public MessageReceiver(TabelaRoteamento t, Semaphore sem){
		this.tabela = t;
		this.sem = sem;
	}

	@Override
	public void run() {
		DatagramSocket serverSocket = null;

		try {
			serverSocket = new DatagramSocket(5000);
		} catch (SocketException ex) {
			JOptionPane.showMessageDialog(null,"Deu treta: "+ ex);
			return;
		}

		byte[] receiveData = new byte[1024];

		while(true){
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

			try {
				serverSocket.receive(receivePacket);

				String tabela_string = new String( receivePacket.getData());            
				String stringWithoutBlankSpace = tabela_string.trim();  
				
				String datagramHost = receivePacket.getAddress().getHostName();

				sem.acquire();

				tabela.updateTabela(stringWithoutBlankSpace, datagramHost);     
				sem.release();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null,"Deu treta: "+ ex);
			}  

		}
	}

}
