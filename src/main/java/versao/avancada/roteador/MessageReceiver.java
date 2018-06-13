package versao.avancada.roteador;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.Semaphore;

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
			JOptionPane.showMessageDialog(null,"Deu treta no socket do sender: "+ ex);
			return;
		}

		byte[] receiveData = new byte[1024];

		while(true){
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);			

			try {
				serverSocket.receive(receivePacket);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(null,"Deu treta no server socket update do receiver: "+ ex);
			}

			String tabela_string = new String( receivePacket.getData());            
			String stringWithoutBlankSpace = tabela_string.trim();  

			InetAddress datagramHost = receivePacket.getAddress();
			String host = datagramHost.getHostAddress();

			try {
				sem.acquire();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null,"Deu treta no aquairio update do receiver: "+ ex);
			}  

			tabela.updateTabela(stringWithoutBlankSpace, host);     
			sem.release();
		}
	}

}
