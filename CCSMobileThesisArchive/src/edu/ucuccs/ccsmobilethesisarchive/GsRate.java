package edu.ucuccs.ccsmobilethesisarchive;

public class GsRate {
	int rid;
	String rate;
	String rthesisid;

	public GsRate() {

	}

	public GsRate(String rthesisid, String rate) {
		this.rthesisid = rthesisid;
		this.rate = rate;
	}

	public GsRate(int rid, String rthesisid, String rate) {
		this.rid = rid;
		this.rate = rate;
		this.rthesisid = rthesisid;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getRthesisid() {
		return rthesisid;
	}

	public void setRthesisid(String rthesisid) {
		this.rthesisid = rthesisid;
	}
}
