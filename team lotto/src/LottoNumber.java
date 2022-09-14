import java.util.ArrayList;
import java.util.List;

public class LottoNumber {

	public List<Integer> numbers = new ArrayList<>(6);
	private int price;  // 상금은 나중에 세터써서 바꾸는 걸로 (1~3등)
	private String correctNumber;
	private List<Integer> guessNumber = new ArrayList<Integer>();
	private String rank;
	
	

	// 로또 등수검사 메소드
	public void compareTo(List<Integer> winningNum) {
		int cnt = 0; // 일치하는 번호의 개수
		Integer bonus = Lotto.bonusNum;

		for (int i = 0; i < 6; i++) { // cnt 갯수를 세는 반복문
			if (winningNum.contains(numbers.get(i))) {
				guessNumber.add(numbers.get(i));
				cnt++;
			}
		}

		switch (cnt) { // 일치하는 cnt 값에 따라서 등수 배정하는 switch 문
		case 6:
			if (!numbers.contains(bonus)) {
				rank = "1등";
				price = Lotto.firstPrice;
				Lotto.rank[1]++;
			} else {
				rank = "2등";
				price = Lotto.secondPrice;
				Lotto.rank[2]++;
			}
			break;
		case 5:
			if (!numbers.contains(bonus)) {
				rank = "3등";
				price = Lotto.thirdPrice;
				Lotto.rank[3]++;
			} else {
				rank = "4등";
				price = 50000; 
				Lotto.rank[4]++;
			}
			break;
		case 4:
			if (!numbers.contains(bonus)) {
				rank = "4등";
				price = 50000; 
				Lotto.rank[4]++;
			} else {
				rank = "5등 당첨";
				price = 5000; 
				Lotto.rank[5]++;
			}
			break;
		case 3:
			if (!numbers.contains(bonus)) {
				rank = "5등";
				price = 5000;
				Lotto.rank[5]++;
			} else {
				rank = "낙첨";
				price = 0;
			}
			break;
		default:
			rank = "낙첨";
			price = 0;
			break;
		}
	}
	
	public String getRank() {
		return rank;
	}
	
	public void setRank(String rank) {
		this.rank = rank;
	}
	
	public void setCorrectNumber(String correctNumber) {
		this.correctNumber = correctNumber;
	}
	
	public String getCorrectNumber() {
		return correctNumber;
	}

	public LottoNumber(List<Integer> list) {
		for (int i = 0; i <= 5; i++) {
			this.numbers.add(list.get(i));
		}
	}

	public List<Integer> getGuessNumber() {
		return guessNumber;
	}

	public void setGuessNumber(List<Integer> guessNumber) {
		this.guessNumber = guessNumber;
	}

	private int cnt; // 번호 맞춘 개수

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}



	public String getNumbers() {
		StringBuilder sb = new StringBuilder();
		for (int a : numbers) {
			sb.append(a).append(" ");
		}
		return sb.toString();
	}

	

	public void setNumbers(List<Integer> numbers) {
		this.numbers = numbers;
	}
	
	public List<Integer> getNumbers2() {
		return numbers;
	}

}
