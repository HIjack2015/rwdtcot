package cn.eport.main;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import cn.eport.model.TableName;

public class Main {
	public static void main(String args[]) throws Exception {
		XWPFDocument document = POITool
				.readWord("C:/Users/jiakang.EPORT0/Desktop/danBingAdd.docx");

		// 获取所有段落
		List<XWPFParagraph> paragraphs = document.getParagraphs();

		List<TableName> tableNames = new LinkedList<TableName>();
		for (XWPFParagraph xwpfParagraph : paragraphs) {
			String parContent = xwpfParagraph.getText();
			if (StringTool.isTitle(parContent)) {
				String english = StringTool.getTitle(parContent);
				String chinese = StringTool.getChinese(parContent);
				TableName tableName = new TableName(english, chinese);
				tableNames.add(tableName);
			}

		}

		List<XWPFTable> tables = document.getTables();
		System.out.println(tables.size());
		System.out.println(tableNames.size());
		PrintWriter writer = new PrintWriter("createTable.sql", "UTF-8");
		for (int tableNo = 0; tableNo < tableNames.size(); tableNo++) {
			String sql = POITool.getAllSql(tables.get(tableNo),
					tableNames.get(tableNo));
			if(sql!=null) {
				System.out.println(sql);				
			}
	//		writer.println(sql);
		}
		writer.close();
		
	}
}
