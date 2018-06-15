package versao.avancada.roteador;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


public class TabelaRoteamento {
	private HashMap<String, String> destinationIpAndNeighborsIp;
	private HashMap<String, Integer> destinationIpAndYourMetric;
	private HashMap<String, Long> destinationIpAndTimestamp;


	private String lastTable;
	private String completeTable;
	private String mesageForSender;
	private boolean wereChanged;
	private long horaAtual;	

	private HashSet<String> neighborRouters;	
	private HashSet<String> originalneighborRouters;
	private Long actualTime = System.currentTimeMillis();


	public TabelaRoteamento(HashSet<String> neighborList){

		this.destinationIpAndTimestamp = new HashMap<>();		
		this.destinationIpAndNeighborsIp = new HashMap<>();		
		this.destinationIpAndYourMetric = new HashMap<>();			
		this.completeTable="";
		this.lastTable = "";		
		this.mesageForSender = "!";
		this.wereChanged = false;
		this.neighborRouters = neighborList;	
		this.originalneighborRouters = neighborList;		
		start(neighborList);
	}


	public void start(HashSet<String> neighbor) {	
		for(String x: neighbor) {
			destinationIpAndNeighborsIp.put(x, x);					
			destinationIpAndYourMetric.put(x, 1);	
			destinationIpAndTimestamp.put(x, actualTime);
			this.lastTable = lastTable +"*"+x+";1";
			this.completeTable = completeTable + x +";"+1+";"+x+"\n";
		}
	}

	public boolean isChanged() {
		return wereChanged;		
	}

	public void updateTabela(String receivedTable, String neighborIP){	
		
		

		System.out.println(destinationIpAndNeighborsIp.size());
		System.out.println(destinationIpAndTimestamp.size());
		System.out.println(destinationIpAndYourMetric.size());
	}
	
	public void verificaEntrada(String mensagem) {
		if(mensagem.contains("!")== false) {
			
			String[] msgSplitedByAsterisk = mensagem.split("\\*");
			for(int i =1; i< msgSplitedByAsterisk.length; i++) {
				String[] stringTupla = msgSplitedByAsterisk[i].split(";");
				String destinyDoor = stringTupla[0];			
				int newMetrik = Integer.parseInt(stringTupla[1]);
			}
			
		}else {
			wereChanged = true;
		}
		
	}
	
	public void verificaHost(String ipRecebido) {
		String aux = "";
		try { aux = InetAddress.getLocalHost().getHostAddress(); } catch (UnknownHostException e) { }
		
		if(ipRecebido.equalsIgnoreCase(aux)) {
			verificaVizinho(ipRecebido);
		}
		
	}



	private void verificaVizinho(String ipRecebido) {
		if(neighborRouters.contains(ipRecebido)== false) {
			verificaTabela(ipRecebido, ipSender);			
		}		
	}
	
	public void verificaTabela(String ipRecebido, String ipSender) {
		if(destinationIpAndNeighborsIp.containsKey(ipRecebido)) {
			destinationIpAndNeighborsIp.put(ipRecebido, ipSender);
			e
		}
	}


	public String get_tabela_string(){		
		DateFormat formato = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String formattedDate = formato.format(date);

		String forSend = "!";		
		if(destinationIpAndNeighborsIp.size()==0){
			forSend = "!";			
			System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");	
			System.out.println("Message sending for Routers Neighbors: ");	
			System.out.println(forSend);	
			System.out.println("\n          Routing Table \n-------------/--/---------------");
			System.out.println(formattedDate);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");

		}else {						
			forSend = mesageForSender;			
			System.out.println("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");	
			System.out.println("Message sending for Routers Neighbors: ");	
			System.out.println(forSend);	
			System.out.println("\n          Routing Table \n"+completeTable);
			System.out.println(formattedDate);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n");

		}
		return forSend;
	}


	public int getSizeNeighborActive() {
		return neighborRouters.size();
	}

	public String verificaIpEntradaRede(String tabelaRecebida) {

		if(tabelaRecebida.contains("!")) {
			wereChanged = true;		
			return "";
		}else{
			return tabelaRecebida;
		}		
	}

	

}
