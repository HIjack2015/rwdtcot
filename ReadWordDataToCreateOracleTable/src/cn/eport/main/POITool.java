package cn.eport.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.functions.Column;
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

	private static String getCreateTableSql(List<TableColumn> columns,
			TableName name) {
		StringBuilder createTableSql = new StringBuilder();
		StringBuilder commentsSql = new StringBuilder();
		commentsSql.append("-- Add comments to the table\n");
		commentsSql.append("comment on table " + name.getEnglishName()
				+ " is '" + name.getChineseName() + "';\n");
		commentsSql.append("-- Add comments to the columns\n");
		createTableSql.append("create table " + name.getEnglishName() + "\n( ");
		for (TableColumn column : columns) {
			String nullInfo = column.canBeNull() ? "" : " not null ";
			createTableSql.append(column.getEnglishName() + " "
					+ column.getType() + nullInfo + ",\n");
			commentsSql.append("comment on column " + name.getEnglishName()
					+ "." + column.getEnglishName() + " is '"
					+ column.getChineseName() + "   " + column.getComments()
					+ "';\n");

		}
		createTableSql.deleteCharAt(createTableSql.length() - 2);
		createTableSql.append(") tablespace BDDB ;\n");
		createTableSql.append(commentsSql);

		return createTableSql.toString();
	}

	private static String getSqlOfSetPKey(List<TableColumn> columns,
			TableName name) {
		StringBuilder createTableSql = new StringBuilder();

		createTableSql.append("alter table " + name.getEnglishName()
				+ " add constraint  " + name.getEnglishName()
				+ "_PK  primary key (" + "\n");

		boolean hasPK = false;
		for (TableColumn column : columns) {

			String comments = column.getComments();
			if (comments.toUpperCase().contains("PK")
					|| comments.contains("主键")) {
				hasPK = true;
				createTableSql.append(column.getEnglishName() + " ,");
			}

		}
		createTableSql.deleteCharAt(createTableSql.length() - 1);
		createTableSql.append(") using index  tablespace BDDB;");
		if (hasPK) {
			return createTableSql.toString();
		} else {
			return null;
		}
	}

	public static String getAllSql(XWPFTable table, TableName name) {
		initTable(table);
		List<TableColumn> columns = getColumns(table);
		String cts = getCreateTableSql(columns, name);
		String spks = getSqlOfSetPKey(columns, name);
		return cts + "\n" + spks;
	}

	private static void initTable(XWPFTable table) {
		List<XWPFTableRow> xwpfTableRows = table.getRows();
		for (XWPFTableRow xwpfTableRow : xwpfTableRows) {
			xwpfTableRow.removeCell(0);
			TableColumn column = new TableColumn(xwpfTableRow);
			if (!column.isHealth()) {
				MyLog.error("column " + column.toString() + " not health");
				continue;
			}
		}
		xwpfTableRows.remove(0);
	}

	private static List<TableColumn> getColumns(XWPFTable table) {
		List<TableColumn> tableColumns = new ArrayList<>();
		List<XWPFTableRow> xwpfTableRows = table.getRows();
		for (XWPFTableRow xwpfTableRow : xwpfTableRows) {
			TableColumn column = new TableColumn(xwpfTableRow);
			tableColumns.add(column);
		}
		return tableColumns;
	}
}
