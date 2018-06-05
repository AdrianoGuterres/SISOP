package trabalho;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.junit.Test;

import versao.avancada.roteador.TabelaRoteamento;

public class A {

	@Test
	public void test() {
		
		ArrayList<String> routersNextDoor = new ArrayList<>();
		
		routersNextDoor.add("1.1.1.1");
		routersNextDoor.add("1.1.1.2");
		routersNextDoor.add("1.1.1.3");
		
		String localHost = "";
		try {
			localHost = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String datagramHost = "1.1.1.3";
		
		try {localHost = InetAddress.getLocalHost().getHostName();} catch (UnknownHostException e) {}
		
		TabelaRoteamento tabela = new TabelaRoteamento(routersNextDoor, localHost);
		
		String update = "*a;1*b;1*c;1*a;1*b;1*c;1";
		tabela.updateTabela(update,datagramHost);
		
		System.out.println("\n\n\n"+tabela.get_tabela_string());

	}

}
