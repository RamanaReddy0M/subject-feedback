package in.kvsr.faculty;

import java.util.List;
public class RemarkAndQuestionList {
	
	private List<String> remarks = null;
	private List<Float> q = null;
	
	public RemarkAndQuestionList(List<String> remarks, List<Float> q) {
		this.remarks = remarks;
		this.q = q;
	}

	public List<String> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<String> remarks) {
		this.remarks = remarks;
	}

	public List<Float> getQ() {
		return q;
	}

	public void setQ(List<Float> q) {
		this.q = q;
	}

}
