package trabalho;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import versao.avancada.roteador.TuplesManager;

public class TuplesManagerTest {

	@Test
	public void test1() {

		TuplesManager manager = new TuplesManager();
		
		
		manager.addNeigtbor("192.168.10.10");
		
		System.out.println(manager.getTuplesList().size());
		
		System.out.println(manager.getTuplesList().get(0).getIpDestiny()+" "+manager.getTuplesList().get(0).getMetric()+" "+manager.getTuplesList().get(0).getIpOut()+" "+manager.getTuplesList().get(0).getTimeStamp());
	
	}
	
	@Test
	public void test02() {
		TuplesManager manager = new TuplesManager();
		
		manager.addTuple("10.10.10.40", 1,"20");
		System.out.println(manager.getTuplesList().get(0).getIpDestiny()+" "+manager.getTuplesList().get(0).getMetric()+" "+manager.getTuplesList().get(0).getIpOut()+" "+manager.getTuplesList().get(0).getTimeStamp());
		
	}
	
	@Test
	public void test03() {
		TuplesManager manager = new TuplesManager();
		manager.addTuple("10.10.10.40", 1,"20");
		System.out.println(manager.getTuplesList().get(0).getIpDestiny()+" "+manager.getTuplesList().get(0).getMetric()+" "+manager.getTuplesList().get(0).getIpOut()+" "+manager.getTuplesList().get(0).getTimeStamp());
		
		System.out.println(manager.removeTuple("10.10.10.40"));
		
	}

}
