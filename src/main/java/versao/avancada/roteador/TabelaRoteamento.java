package versao.avancada.roteador;

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

	private String localHost;		
	private String lastTable;
	private String completeTable;
	private String mesageForSender;
	private boolean wereChanged;
	private long horaAtual;	

	private HashSet<String> neighborRouters;	
	private HashSet<String> originalneighborRouters;
	private Long actualTime = System.currentTimeMillis();


	public TabelaRoteamento(HashSet<String> neighborList, String localHost){

		this.destinationIpAndTimestamp = new HashMap<>();		
		this.destinationIpAndNeighborsIp = new HashMap<>();		
		this.destinationIpAndYourMetric = new HashMap<>();		
		this.localHost = localHost;		
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
		this.horaAtual = System.currentTimeMillis();

		if(receivedTable.equalsIgnoreCase("!")) {
			this.wereChanged = true;	
			if(originalneighborRouters.contains(neighborIP)) {
				this.neighborRouters.add(neighborIP);				
			}
		}else {

			String[] splitedForAsterisk = receivedTable.split("\\*");
			for(int i = 1; i < splitedForAsterisk.length; i ++) {
				String[] tupla = splitedForAsterisk[i].split(";");
				String ipReceived = tupla[0];
				Integer metricReceived = Integer.parseInt(tupla[1]);

				//verificar se não é os dos vizinhos 
				if(neighborRouters.contains(ipReceived)== false && ipReceived.equalsIgnoreCase(localHost)==false) {		
					ArrayList<String> ipList = new ArrayList<>();
					for(String x:destinationIpAndNeighborsIp.keySet()) {
						ipList.add(x);
					}

					//Verificar se já tenho o ip na lista de destinos
					if(ipList.contains(ipReceived) == false) {
						destinationIpAndNeighborsIp.put(ipReceived, neighborIP);
						destinationIpAndYourMetric.put(ipReceived, metricReceived+1);
						destinationIpAndTimestamp.put(ipReceived, actualTime);												
					}else {
						//verificar qual metrica é a menor
						int metricaArmazenada = 0;
						metricaArmazenada = destinationIpAndYourMetric.get(ipReceived);
						if(metricReceived < metricaArmazenada) {
							destinationIpAndYourMetric.put(ipReceived,metricReceived);
							destinationIpAndNeighborsIp.put(ipReceived, neighborIP);
							destinationIpAndTimestamp.put(ipReceived, actualTime);
						}
					}					
				}	
				if(neighborRouters.contains(ipReceived)) {
					destinationIpAndYourMetric.put(ipReceived,1);
					destinationIpAndNeighborsIp.put(ipReceived, ipReceived);
					destinationIpAndTimestamp.put(ipReceived, actualTime);
				}			
			}			
		}

		long aux = System.currentTimeMillis();

		Iterator<String> it = destinationIpAndTimestamp.keySet().iterator();

		while(it.hasNext()) {
			String key=it.next();

			if(destinationIpAndTimestamp.get(key) + 3000 < aux) {
				destinationIpAndNeighborsIp.remove(key);
				destinationIpAndYourMetric.remove(key);
				it.remove();
			}
		}


		this.completeTable  = "";
		this.mesageForSender = "";
		if(destinationIpAndNeighborsIp.isEmpty() == false) {
			for(String x:destinationIpAndNeighborsIp.keySet()) {
				this.completeTable = completeTable + x +" / "+destinationIpAndYourMetric.get(x)+" / "+destinationIpAndNeighborsIp.get(x)+"\n";
				this.mesageForSender = this.mesageForSender+ "*"+x+";"+ destinationIpAndYourMetric.get(x);				
			}			
		}
		this.lastTable = mesageForSender;


		/*System.out.println(destinationIpAndNeighborsIp.keySet() +"    "+ destinationIpAndNeighborsIp.size());
		System.out.println(destinationIpAndTimestamp.keySet()+"    "+ destinationIpAndTimestamp.size());
		System.out.println(destinationIpAndYourMetric.keySet()  +"    "+ destinationIpAndYourMetric.size());*/

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

}
