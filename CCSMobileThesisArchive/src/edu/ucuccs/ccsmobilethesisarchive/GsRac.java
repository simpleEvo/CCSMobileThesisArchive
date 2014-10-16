package edu.ucuccs.ccsmobilethesisarchive;

public class GsRac {
	int rac_id;
	String rac_fname;
	String rac_lname;
	int rac_rate;
	String rac_comment;
	String rac_uid;
	String rac_tid;

	public GsRac() {

	}

	public GsRac(String rac_fname, String rac_lname, int rac_rate,
			String rac_comment, String rac_uid, String rac_tid) {
		this.rac_fname = rac_fname;
		this.rac_lname = rac_lname;
		this.rac_rate = rac_rate;
		this.rac_comment = rac_comment;
		this.rac_uid = rac_uid;
		this.rac_tid = rac_tid;
	}

	public GsRac(int rac_id, String rac_fname, String rac_lname, int rac_rate,
			String rac_comment, String rac_uid, String rac_tid) {
		this.rac_id = rac_id;
		this.rac_fname = rac_fname;
		this.rac_lname = rac_lname;
		this.rac_rate = rac_rate;
		this.rac_comment = rac_comment;
		this.rac_uid = rac_uid;
		this.rac_tid = rac_tid;
	}

	public int getRac_id() {
		return rac_id;
	}

	public void setRac_id(int rac_id) {
		this.rac_id = rac_id;
	}

	public String getRac_fname() {
		return rac_fname;
	}

	public void setRac_fname(String rac_fname) {
		this.rac_fname = rac_fname;
	}

	public String getRac_lname() {
		return rac_lname;
	}

	public void setRac_lname(String rac_lname) {
		this.rac_lname = rac_lname;
	}

	public int getRac_rate() {
		return rac_rate;
	}

	public void setRac_rate(int rac_rate) {
		this.rac_rate = rac_rate;
	}

	public String getRac_comment() {
		return rac_comment;
	}

	public void setRac_comment(String rac_comment) {
		this.rac_comment = rac_comment;
	}

	public String getRac_uid() {
		return rac_uid;
	}

	public void setRac_uid(String rac_uid) {
		this.rac_uid = rac_uid;
	}

	public String getRac_tid() {
		return rac_tid;
	}

	public void setRac_tid(String rac_tid) {
		this.rac_tid = rac_tid;
	}

}
