package cn.eport.model;

import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class TableColumn {
	private String chineseName;
	private String englishName;
	private String type;
	private boolean canBeNull;
	private String comments;

	public TableColumn(XWPFTableRow row) {
		row.removeCell(0); // È¥³ýÐòºÅÁÐ
		chineseName = row.getCell(0).getText();
		englishName = row.getCell(1).getText();
		type = row.getCell(2).getText();
		canBeNull = !row.getCell(3).getText().equals("N");
		comments = row.getCell(4).getText();
		comments = comments.replaceAll("'", "\"");

	}

	public boolean hasComments() {
		comments = comments.replaceAll("¡¡", "");
		return !comments.trim().equals("");
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean canBeNull() {
		return canBeNull;
	}

	public void setCanBeNull(boolean canBeNull) {
		this.canBeNull = canBeNull;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean isHealth() {
		String[] elements = { chineseName, englishName, type, comments };
		for (String string : elements) {
			if (string == null) {
				return false;
			}
		}
		if (englishName.isEmpty() || type.isEmpty()) {
			return false;
		}
		return true;
	}

	public String toString() {
		return chineseName + " " + englishName;
	}
}
