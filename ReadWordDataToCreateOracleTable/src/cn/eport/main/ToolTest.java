package cn.eport.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ToolTest {

	@Test
	public void testIsTitle() {
		boolean result=	StringTool.isTitle("报关单表头ENTRY_HEAD");
		assertEquals(true, result);
	}
	@Test
	public void testGetTitle() {
		String result=	StringTool.getTitle("报关单表头ENTRY_HEAD");
		assertEquals("ENTRY_HEAD", result);
	}
	@Test
	public void testGetChinese() {
		String result=	StringTool.getChinese("报关单表头ENTRY_HEAD");
		assertEquals("报关单表头", result);
	}
	

}
