package cn.eport.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import cn.eport.model.TableColumn;
import cn.eport.model.TableName;

public class POITool {

	public static XWPFDocument readWord(String filePath) {
		try {
			File file = new File(filePath);
			InputStream is = new FileInputStream(file);
			XWPFDocument document = new XWPFDocument(is);
			return document;

		} catch (Exception e) {
			System.out.println("�ļ������ڻ���·������");
			e.printStackTrace();
		}
		return null;
	}

	public static String getSql(XWPFTable table, TableName name) {
		StringBuilder createTableSql = new StringBuilder();
		StringBuilder commentsSql = new StringBuilder();
		commentsSql.append("-- Add comments to the table\n");
		commentsSql.append("comment on table " + name.getEnglishName()
				+ " is '" + name.getChineseName() + "';\n");

		commentsSql.append("-- Add comments to the columns\n");
		createTableSql.append("create table " + name.getEnglishName() + "\n( ");
		List<XWPFTableRow> xwpfTableRows = table.getRows();
		xwpfTableRows.remove(0);
		for (XWPFTableRow xwpfTableRow : xwpfTableRows) {
			TableColumn column = new TableColumn(xwpfTableRow);
			if (!column.isHealth()) {
				MyLog.error("column " + column.toString()
						+ " is not health in table" + name.getChineseName());
				continue;
			}	
			String nullInfo = column.canBeNull() ? "" : " not null ";
			createTableSql.append(column.getEnglishName() + " "
					+ column.getType() + nullInfo + ",\n");

			commentsSql.append("comment on column " + name.getEnglishName()
					+ "." + column.getEnglishName() + " is '"
					+ column.getChineseName()+"   " + column.getComments() + "';\n");
		}
		createTableSql.deleteCharAt(createTableSql.length() - 2);
		createTableSql.append(") tablespace BDDB ;\n");
		createTableSql.append(commentsSql);

		return createTableSql.toString();
	}
}
