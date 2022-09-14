import java.util.ArrayList;
import java.util.List;

public class Members {
	private List<Buyer> member = new ArrayList<>();
	
	public static Buyer signUp( Buyer id, Buyer Name ) {
		Buyer a = new Buyer("id","Name");
	return a;
	}
	// 결과확인 하기 전 구매체크 
	public boolean checkEmpty() {
		if (member.size() == 0) {
			return true;
		} else {
			for (int i = 0; i < member.size(); i++) {
				// 한 명이라도 구매했으면
				if (!member.get(i).getLottoLines().isEmpty()) {
					return false;
				}
			} // 아무도 구매 안 했으면
			return true;
		}
	}
	
	// 로또 리셋하는 메소드 
	public void resetMemOfLot() {
		for (int i = 0; i < member.size(); i++) {
			member.get(i).getLottoLines().clear();
			member.get(i).getOneLottoNums().clear();
		}
	}

	// 모든 바이어의 로또 번호 반환 (메소드)
	public List<List<Integer>> lottosOfMembers() {
		List<List<Integer>> temp = new ArrayList<>();
		for (int i = 0; i < member.size(); i++) {
			temp.addAll(member.get(i).getLottoLines());
		}
		return temp;
	}
	
	public List<Buyer> getMember() {
		return member;
	}

	public void setMember(List<Buyer> member) {
		this.member = member;
	}

	public void addIdName(String name, String id) {
		member.add(new Buyer(name, id));
	}
	
	public int getIndex(String id) {
		int index = 0;
		for (int i = 0; i < member.size(); i++) {
			if(member.get(i).getId().equals(id)) {
				index = i;
			}
		}
		return index;
	}
	

	public void addMember(String id, String Name) {
		member.add(new Buyer(id, Name));
	}
}
