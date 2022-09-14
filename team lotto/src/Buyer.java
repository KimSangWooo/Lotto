import java.io.IOException;
import java.util.*;

public class Buyer {

	private String name;
	private String id;
	private List<List<Integer>> lottoLines = new ArrayList<>();
	private List<LottoNumber> oneLottoNums = new ArrayList<>();
	private int reward;
	
	public List<LottoNumber> getOneLottoNums() {
		return oneLottoNums;
	}
	

	public void setOneLottoNums(List<LottoNumber> oneLottoNums) {
		this.oneLottoNums = oneLottoNums;
	}

	public void linesToOne() {
		for (int i = 0; i < lottoLines.size(); i++) {
			oneLottoNums.add(new LottoNumber(lottoLines.get(i)));
		}
	}
	
	
	public int getReward() {
		for (int i = 0; i < oneLottoNums.size(); i++) {
			reward += oneLottoNums.get(i).getPrice();
		}
		return reward;
	}
	
	
	public void setReward(int reward) {
		this.reward = reward;
	}

	public Buyer(String id, String name) { //________________ 박로 수정(7.6)
		this.id = id;
		this.name = name;
	}
	

	public List<List<Integer>> getLottoLines() {
		return lottoLines;
	}

	public void setLottoLines(List<List<Integer>> lottoNum) {
		this.lottoLines = lottoNum;
	}
	
	// 로또 번호 추가 메소드 (중요)  _________수정
	public void addLottoLines(List<Integer> lottoNum) {
		List<Integer> temp = lottoNum;
		lottoLines.add(temp);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
