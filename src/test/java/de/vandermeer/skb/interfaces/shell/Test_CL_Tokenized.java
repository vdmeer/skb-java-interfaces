package de.vandermeer.skb.interfaces.shell;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Test_CL_Tokenized {

//	@Test
//	public void test_EmptyLine(){
//		CL_Tokenized clk = CL_Tokenized.create("");
//	}

	@Test
	public void test_singleHead(){
		Sh_CommandLineTokens clk = Sh_CommandLineTokens.create("cmd");
		assertEquals(1, clk.head().size());
		assertEquals("cmd", clk.head().get(0));

		assertEquals(0, clk.tail().size());
	}

	@Test
	public void test_doubleHead(){
		Sh_CommandLineTokens clk = Sh_CommandLineTokens.create("cmd1 cmd2");
		assertEquals(2, clk.head().size());
		assertEquals("cmd1", clk.head().get(0));
		assertEquals("cmd2", clk.head().get(1));

		assertEquals(0, clk.tail().size());
	}

	@Test
	public void test_trippleHead(){
		Sh_CommandLineTokens clk = Sh_CommandLineTokens.create("cmd1 cmd2 cmd3");
		assertEquals(3, clk.head().size());
		assertEquals("cmd1", clk.head().get(0));
		assertEquals("cmd2", clk.head().get(1));
		assertEquals("cmd3", clk.head().get(2));

		assertEquals(0, clk.tail().size());
	}

	@Test
	public void test_singleTail(){
		Sh_CommandLineTokens clk = Sh_CommandLineTokens.create("cmd:val");
		assertEquals(1, clk.tail().size());
		assertEquals("cmd", clk.tail().get(0).getKey());
		assertEquals("val", clk.tail().get(0).getValue());

		assertEquals(0, clk.head().size());
	}

	@Test
	public void test_doubleTail(){
		Sh_CommandLineTokens clk = Sh_CommandLineTokens.create("cmd1:val1 cmd2:val2");
		assertEquals(2, clk.tail().size());
		assertEquals("cmd1", clk.tail().get(0).getKey());
		assertEquals("val1", clk.tail().get(0).getValue());
		assertEquals("cmd2", clk.tail().get(1).getKey());
		assertEquals("val2", clk.tail().get(1).getValue());

		assertEquals(0, clk.head().size());
	}

	@Test
	public void test_trippleTail(){
		Sh_CommandLineTokens clk = Sh_CommandLineTokens.create("cmd1:val1 cmd2:val2 cmd3:val3");
		assertEquals(3, clk.tail().size());
		assertEquals("cmd1", clk.tail().get(0).getKey());
		assertEquals("val1", clk.tail().get(0).getValue());
		assertEquals("cmd2", clk.tail().get(1).getKey());
		assertEquals("val2", clk.tail().get(1).getValue());
		assertEquals("cmd3", clk.tail().get(2).getKey());
		assertEquals("val3", clk.tail().get(2).getValue());

		assertEquals(0, clk.head().size());
	}
}
