package versao.avancada.roteador;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import javafx.scene.input.DataFormat;


public class TabelaRoteamento {
	private HashMap<String, String> destinyDoorAndExitDoor;
	private HashMap<String, Integer> destinyDoorAndMetrik;
	private HashMap<String, Long> destinyDoorAndTime;
	
	private String localHost;
	private boolean areChanged;	
	private String lastTable;
	private String completeTable;
	
	private ArrayList<String> neighborRouters;
	
	

	public TabelaRoteamento(ArrayList<String> neighbor, String localHost){
		this.destinyDoorAndTime = new HashMap<>();
		this.destinyDoorAndExitDoor = new HashMap<>();
		this.destinyDoorAndMetrik = new HashMap<>();		
		this.localHost = localHost;
		this.neighborRouters = neighbor;		
		this.completeTable="";
		this.lastTable = "";		
		this.areChanged = false;
		
		start(neighbor);
	}
	
	public void start(ArrayList<String> neighbor) {	
		
		for(String x: neighbor) {
			destinyDoorAndExitDoor.put(x, x);
			destinyDoorAndMetrik.put(x, 1);
			atualizaTime(x);
			this.lastTable = lastTable +"*"+x+";1";
			this.completeTable = completeTable + x +";"+1+";"+x+"\n";
		}

	}
	
	
	
	final Long actualTime = System.currentTimeMillis();
	
	private void atualizaTime(String destiny) {
		
		Long aux = actualTime+10000;				
		destinyDoorAndTime.put(destiny,aux);		
	}

	public boolean isChanged() {
		return areChanged;
	}

	DataFormat data = new DataFormat("mm:ss");

	public void updateTabela(String receivedTable, String host){
		
		String[] msgSplitedByAsterisk = receivedTable.split("\\*");

		if(receivedTable.equalsIgnoreCase("!")) {
			this.areChanged = true;			
		}else {
			

			for(int i =1; i< msgSplitedByAsterisk.length; i++) {
				String[] stringTupla = msgSplitedByAsterisk[i].split(";");
				String destinyDoor = stringTupla[0];			
				int newMetrik = Integer.parseInt(stringTupla[1]);
				
				
				long horaAtual = System.currentTimeMillis();
				
				if(destinyDoorAndTime.get(destinyDoor)-horaAtual < 0) {
					destinyDoorAndTime.remove(destinyDoor);
				}
				
				System.out.println("IP e labelTime  "+" " + horaAtual);
				System.out.println("IP e labelTime "+destinyDoor+" " );
				
				if(destinyDoor.equalsIgnoreCase(localHost)== false) {
					if(destinyDoorAndMetrik.containsKey(destinyDoor)) {
						if(destinyDoorAndMetrik.get(destinyDoor) < newMetrik +1) {
							destinyDoorAndMetrik.put(destinyDoor, newMetrik +1);	
							destinyDoorAndExitDoor.put(destinyDoor, host);
							atualizaTime(destinyDoor);
						}		
					}else {
						destinyDoorAndMetrik.put(destinyDoor, newMetrik +1);	
						destinyDoorAndExitDoor.put(destinyDoor, host);
						atualizaTime(destinyDoor);
					}				
				}			
			}
		}
		
		Long aux = System.currentTimeMillis();		
		for(String x: destinyDoorAndTime.keySet()) {
			if(destinyDoorAndTime.get(x) < aux) {
				destinyDoorAndExitDoor.remove(x);
				destinyDoorAndMetrik.remove(x);
				destinyDoorAndTime.remove(x);
			}			
		}			
		
		String tableFormer = "";		
		for(String x: destinyDoorAndExitDoor.keySet() ) {
			tableFormer = tableFormer + "*"+x+";"+ destinyDoorAndMetrik.get(x);
		}
		
		if(tableFormer.equalsIgnoreCase(lastTable)) {
			this.areChanged = false;			
		}else {
			this.areChanged = true;
			this.lastTable = tableFormer;
		}
		
		this.completeTable = "";		
		for(String x: destinyDoorAndExitDoor.keySet() ) {			
			this.completeTable = completeTable+ x + " ; " + destinyDoorAndMetrik.get(x) + " ; " + destinyDoorAndExitDoor.get(x) + "\n";		
		}			
	}
	
	private int count = 0;

	public String get_tabela_string(){		
		DateFormat formato = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String formattedDate = formato.format(date);
		
		String forSend = "!";		
		if(count < 1){
			count ++;
			forSend = "!";			
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\nMessage sending for Routers Neighbors: " + forSend);		
			System.out.println("\n  Routing Table \n" + completeTable +formattedDate+"\n\n");
		   
		}else {						
			forSend = lastTable;			
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\nMessage sending for Routers Neighbors: " + forSend);			
			System.out.println("\n  Routing Table \n" + completeTable+formattedDate+"\n\n");	
		}
		return forSend;
	}
}
