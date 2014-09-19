package edu.ucuccs.ccsmobilethesisarchive;

public class GsThesis {

	private String title;
	private String researcher;
	private String adviser;
	private String year;
	private String abs;

	public GsThesis() {
	}

	public GsThesis(String title, String researcher, String adviser,
			String year, String abs) {
		super();
		this.title = title;
		this.researcher = researcher;
		this.adviser = adviser;
		this.year = year;
		this.abs = abs;

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getResearcher() {
		return researcher;
	}

	public void setResearcher(String researcher) {
		this.researcher = researcher;
	}

	public String getAdviser() {
		return adviser;
	}

	public void setAdviser(String adviser) {
		this.adviser = adviser;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}

	
}
