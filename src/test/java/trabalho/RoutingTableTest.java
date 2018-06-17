package trabalho;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

import versao.avancada.roteador.RoutingTable;

public class RoutingTableTest {

	@Test
	public void test() {
		
		String localHost = "192.168.15.6";
		
		String vizinho1 = "aaa.168.15.6";
		String vizinho2 = "bbb.168.15.6";
		String vizinho3 = "ccc.168.15.6";
		
		String destino1 = "192.168.15.aaa"; 
		String destino2 = "192.168.15.bbb"; 
		String destino3 = "192.168.15.ccc"; 
		String destino4 = "192.168.15.ddd"; 
		String destino5 = "192.168.15.eee";  
		String destino6 = "192.168.15.fff"; 
		String destino7 = "192.168.15.ggg"; 
		String destino8 = "192.168.15.hhh"; 
		String destino9 = "192.168.15.iii"; 
		String destino10 = "192.168.15.jjj"; 
		String destino11 = "192.168.15.kkk";
		
		ArrayList<String> listaVizinhos = new ArrayList<>();
		listaVizinhos.add(vizinho1);
		listaVizinhos.add(vizinho2);
		listaVizinhos.add(vizinho3);
		
		ArrayList<String> datagramas = new ArrayList<>();
		datagramas.add("*"+vizinho3+";1*"+destino2+";1*"+destino3+";1*");
		
		datagramas.add("*"+destino4+";1*"+destino5+";1*"+destino6+";1*");
		
		datagramas.add("*"+destino1+";1*"+destino2+";1*"+vizinho2+";1*");
		
		datagramas.add("*"+destino7+";1*"+destino8+";1*"+destino9+";1*");
		
		datagramas.add("*"+destino10+";1*"+vizinho1+";1*"+destino9+";1*");		
		

		RoutingTable tabela = new RoutingTable(listaVizinhos, localHost);
		
		int count = 0;		
		while (count != 3000000) {
			for(String x: datagramas) { 
				
				tabela.updateTabela(x, vizinho1);
				System.out.println(tabela.get_tabela_string());
				tabela.updateTabela(x, vizinho2);
				System.out.println(tabela.get_tabela_string());
				tabela.updateTabela(x, vizinho3);			
			}
			
		}
		
		

		

	}

}
