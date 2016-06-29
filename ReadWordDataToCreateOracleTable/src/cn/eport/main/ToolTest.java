package cn.eport.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ToolTest {

	@Test
	public void testIsTitle() {
		boolean result=	StringTool.isTitle("���ص���ͷENTRY_HEAD");
		assertEquals(true, result);
	}
	@Test
	public void testGetTitle() {
		String result=	StringTool.getTitle("���ص���ͷENTRY_HEAD");
		assertEquals("ENTRY_HEAD", result);
	}
	@Test
	public void testGetChinese() {
		String result=	StringTool.getChinese("���ص���ͷENTRY_HEAD");
		assertEquals("���ص���ͷ", result);
	}
	

}
