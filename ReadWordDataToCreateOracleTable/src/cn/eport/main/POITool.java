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
			System.out.println("文件不存在或者路径不对");
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
	public static String getSqlOfSetPKey(XWPFTable table, TableName name) {
		StringBuilder createTableSql = new StringBuilder();

		createTableSql.append("alter table " + name.getEnglishName()+" add constraint  "+name.getEnglishName()+
				"_PK  primary key (" + "\n");
		List<XWPFTableRow> xwpfTableRows = table.getRows();
		xwpfTableRows.remove(0);
		boolean hasPK=false;
		for (XWPFTableRow xwpfTableRow : xwpfTableRows) {
			TableColumn column = new TableColumn(xwpfTableRow);
			if (!column.isHealth()) {
//				MyLog.error("column " + column.toString()
//						+ " is not health in table" + name.getChineseName());
				continue;
			}	
			String comments=column.getComments();
			if (comments.contains("PK")||comments.contains("pk")||comments.contains("主键")) {
				hasPK=true;
				createTableSql.append(column.getEnglishName()+" ,");
			}

		}
		createTableSql.deleteCharAt(createTableSql.length()-1);
		createTableSql.append(") using index  tablespace BDDB;");
		if (hasPK) {
			return createTableSql.toString();			
		} else {
			return  null;
		}
	}
}
