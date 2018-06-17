package trabalho;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import versao.avancada.roteador.TuplesManager;

public class TuplesManagerTest {

	
	
	@Test
	public void testAddTupla() {
		
		ArrayList<String> neigtborList = new ArrayList<>();
		
		neigtborList.add("aaa.aaa.aaa.aaa");
		neigtborList.add("bbb.bbb.bbb.bbb");
		neigtborList.add("ccc.ccc.ccc.ccc");
		
		TuplesManager manager = new TuplesManager(neigtborList);
		manager.addTuple("aaa.aaa.aaa.aaa", 1, "aaa.aaa.aaa.aaa");
		manager.addTuple("bbb.bbb.bbb.bbb", 1, "aaa.aaa.aaa.aaa");
		manager.addTuple("ccc.ccc.ccc.ccc", 1, "aaa.aaa.aaa.aaa");
		manager.addTuple("bbb.bbb.bbb.bbb", 2, "aaa.aaa.aaa.aaa");
		manager.addTuple("aaa.aaa.aaa.aaa", 1, "aaa.aaa.aaa.aaa");	
		
		manager.addTuple("ddd.aaa.aaa.aaa", 1, "aaa.aaa.aaa.aaa");
		manager.addTuple("eee.bbb.bbb.bbb", 1, "bbb.bbb.bbb.bbb");
		manager.addTuple("fff.ccc.ccc.ccc", 1, "aaa.aaa.aaa.aaa");
		manager.addTuple("ggg.bbb.bbb.bbb", 2, "ccc.ccc.ccc.ccc");
		manager.addTuple("hhh.aaa.aaa.aaa", 1, "aaa.aaa.aaa.aaa");	
		
		manager.addTuple("ddd.aaa.aaa.aaa", 1, "aaa.aaa.aaa.aaa");
		manager.addTuple("eee.bbb.bbb.bbb", 1, "bbb.bbb.bbb.bbb");
		manager.addTuple("fff.ccc.ccc.ccc", 1, "aaa.aaa.aaa.aaa");
		manager.addTuple("ggg.bbb.bbb.bbb", 2, "ccc.ccc.ccc.ccc");
		manager.addTuple("hhh.aaa.aaa.aaa", 1, "aaa.aaa.aaa.aaa");	
		
		int actual = manager.getTuplasList().size();
		int expected = 8;
		
		assertEquals(expected, actual);
	}
}
