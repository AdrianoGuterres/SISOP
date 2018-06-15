package trabalho;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

import versao.avancada.roteador.TabelaRoteamento;
import versao.avancada.roteador.TabelaRoteamento2;

public class A {

	@Test
	public void test() throws InterruptedException {

		HashSet<String> routersNextDoor = new HashSet<>();

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

		try {localHost = InetAddress.getLocalHost().getHostAddress();} catch (UnknownHostException e) {}

		TabelaRoteamento tabela = new TabelaRoteamento(routersNextDoor);
		
		
		//String time = new String();
		long time =  System.currentTimeMillis();
		/*
		int a = 0;
		while ( a < 200000000) {
			System.out.println();
			System.out.println(time);
			a++;
		}*/
		
		int count = 0;
		while(count < 100) {


			String update = "*e;1*f;1*g;1*h;1*i;1*"+localHost+";1";
			tabela.updateTabela(update,datagramHost);
			tabela.get_tabela_string();

			String update2 = "*a;1*b;1*c;1*a;1*b;1*c;1";
			tabela.updateTabela(update2,datagramHost);
			tabela.get_tabela_string();
			String update3 = "*a;1*b;1*c;1*a;1*b;1*c;1";
			tabela.updateTabela(update3,datagramHost);
			tabela.get_tabela_string();
			String update4 = "*a;1*b;1*c;1*a;1*b;1*c;1";
			tabela.updateTabela(update4,datagramHost);
			tabela.get_tabela_string();
			
			count++;
		}

	}

}
