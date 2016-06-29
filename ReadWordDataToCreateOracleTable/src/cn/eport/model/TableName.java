package cn.eport.model;

public class TableName {
	private	String englishName;
	private	String chineseName;
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public TableName(String englishName, String chineseName) {
		super();
		this.englishName = englishName;
		this.chineseName = chineseName;
	}
	
	
}
