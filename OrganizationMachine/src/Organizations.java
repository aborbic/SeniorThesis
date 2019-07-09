import java.util.*;
 class Organizations {
	public String name;
	public String code;
	public String type;
	public String status;
	public ArrayList<String> members = new ArrayList<String>();

	 public Organizations(String orgName, String orgCode, String orgType, String orgStatus, ArrayList<String> orgMembers) {
		name = orgName;
		code = orgCode;
		type = orgType;
		status = orgStatus;
		members = orgMembers;
	}
	/* public Organizations() {
		
	} */

	public String addMembers(String member) {
		members.add(member);
		return "added member " + member;
	}

	public String removeMembers(String member) {
		members.remove(member);
		return "removed member " + member;
	}

	public ArrayList<String> getMembers(ArrayList<String> members) {
		return members;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public String getType() {
		return type;
	}

	public String getStatus() {
		return status;
	}
}
