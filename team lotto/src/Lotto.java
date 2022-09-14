import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Lotto {
	public static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
	public static StringBuilder sb = new StringBuilder();
	public static StringTokenizer st;

	public static List<Integer> winningList = new ArrayList<>(); // 당첨번호리스트 사이즈7
	public static Set<Integer> set = new HashSet<Integer>();
	public static LottoNumber[] lottoLine = new LottoNumber[20000];

	public static int selectNumber = 0; // 목록 선택 번호
	public static int buyCnt = 0; // 구매할줄갯수
	public static int totalCnt = 0;
	public static int reward = (totalCnt * 1000 / 2); // 총 당첨금액.
	public static int fifthPrice = 0;
	public static int fourthPrice = 0;
	public static int thirdPerN = 0;
	public static int secondPerN = 0;
	public static int firstPerN = 0;
	public static int bonusNum = 0; // 보너슨넘버 초기화
	public static int rank[] = new int[6]; // 각 등수에 해당하는 사람 수\
	
	public static int idx;
	public static int firstPrice;
	public static int secondPrice;
	public static int thirdPrice;
	public Lotto() {
		
	}
	
	public void resetPrice() {
		totalCnt = 0;
		fifthPrice = 0;
		fourthPrice = 0;
		thirdPrice = 0;
		secondPrice = 0;
		if (rank[1] != 0) {
			firstPrice = 0;
		}
	}
	
	public void resetRank() {
		for (int i = 0; i < rank.length; i++) {
			rank[i] = 0;
		}
	}
	
	public void run() throws NumberFormatException, IOException {	  
		buyCnt = 10000;
		totalCnt += buyCnt;
		autoNum();
		ranking();
		showMeTheMoney();
		printSameNumbers();
	}	

	public static List<Integer> autoNum1() throws IOException { // 자동으로 한 줄만 만들어줌
		return lottoNumbers();

	}

	public static List<Integer> manualAndSemiAuto1() throws IOException { // 반자동으로 한 줄만 만들어줌
		List<Integer> num6 = new ArrayList<Integer>();
		for (idx = 0; idx < lottoLine.length; idx++) {
			if (lottoLine[idx] == null) {
				num6 = lottoNumbers();
				break;
			}
		}
		return num6;

	}

	public static Set<Integer> manualAndSemiAuto1(List<Integer> origin) throws IOException { // 반자동으로 한 줄만 만들어줌
		Set<Integer> num6 = new HashSet<Integer>(origin);
		while (num6.size() != 6) {
			num6.add((int) (Math.random() * 45 + 1));
		}
		return num6;
	}

	public List<Integer> editNum1(List<Integer> list) {
		return list;
	}


	public static void autoNum() throws IOException {
		List<Integer> num6 = new ArrayList<Integer>();
		while (buyCnt-- > 0) {
			for (int i = 0; i < lottoLine.length; i++) {
				if (lottoLine[i] == null) {
					lottoLine[i] = new LottoNumber( lottoNumbers());
					break;
				}
			}
			
		}
	}





	public static void printSameNumbers() { // 맞힌 번호 출력 메소드!!!!
		for (int i = 0; i < lottoLine.length; i++) {
			if (lottoLine[i] != null) {
				for (int j = 0; j < 6; j++) {
					if (winningList.contains(lottoLine[i].numbers.get(j))) {
						sb.append(lottoLine[i].numbers.get(j)).append(" ");
					}
				}
				switch (lottoLine[i].getCnt()) {
				case 6:
					if (!lottoLine[i].getNumbers().contains(String.valueOf(bonusNum))) {
						lottoLine[i].setCorrectNumber(sb.toString());
					
						break;
					} else if (lottoLine[i].getNumbers().contains(String.valueOf(bonusNum))) {
						lottoLine[i].setCorrectNumber(sb.toString());
					
						break;
					}
				case 5:
					if (!lottoLine[i].getNumbers().contains(String.valueOf(bonusNum))) {
						lottoLine[i].setCorrectNumber(sb.toString());
					
						break;
					} else if (lottoLine[i].getNumbers().contains(String.valueOf(bonusNum))) {
						lottoLine[i].setCorrectNumber(sb.toString());
					
						break;
					}
				case 4:
					if (!lottoLine[i].getNumbers().contains(String.valueOf(bonusNum))) {
						lottoLine[i].setCorrectNumber(sb.toString());
					
						break;
					} else if (lottoLine[i].getNumbers().contains(String.valueOf(bonusNum))) {
						lottoLine[i].setCorrectNumber(sb.toString());
						break;
					}
				case 3:
					if (!lottoLine[i].getNumbers().contains(String.valueOf(bonusNum))) {
						lottoLine[i].setCorrectNumber(sb.toString());
					
						break;
					}
				default:
					lottoLine[i].setCorrectNumber(sb.toString());
				
					break;
				}
				sb.setLength(0);
			}

		}
	}

	public static void showMeTheMoney() {
		fifthPrice = 5000 * rank[5]; // 총 5등의 상금
		fourthPrice = 50_000 * rank[4]; // 총 4등의 상금
		
		if (firstPrice != 0) {
			firstPrice += (int) (((totalCnt * 1000 / 2) - (fifthPrice + fourthPrice)) * 0.75); // 1등 총 당첨금액 이
		} else {
			firstPrice = (int) (((totalCnt * 1000 / 2) - (fifthPrice + fourthPrice)) * 0.75); // 1등 총 당첨금액
		}
		secondPrice = ((int) (((totalCnt * 1000 / 2) - (fifthPrice + fourthPrice)) * 0.125)); // 2등 총 당첨금액
		thirdPrice = ((int) (((totalCnt * 1000 / 2) - (fifthPrice + fourthPrice)) * 0.125)); // 3등 총 당첨금액
		
		
		if (rank[2] == 0) { // 만약 2등 당첨자가 없을시 2등 당첨금액이 1등 당첨금액으로 넘어감.
			firstPrice += secondPrice;
			secondPrice = 0;
		}

		if (rank[3] == 0) { // 만약 3등 당첨자가 없을 시 3등 당첨금액이 1등 당첨금액으로 넘어감.
			firstPrice += thirdPrice;
			thirdPrice = 0;
		}
		try {
			thirdPerN = ((int) (thirdPrice) / rank[3]); // 1인당 3등의 상금
		} catch (Exception a) {
		}
		try {
			secondPerN = ((int) (secondPrice) / rank[2]); // 1인당 2등의 상금
		} catch (Exception b) {
		}
		try {
			firstPerN = (int) (firstPrice / rank[1]); // 1인당 1등의 상금.
		} catch (Exception c) {
		}

		for (int i = 0; i < lottoLine.length; i++) {
			if (lottoLine[i] != null) {
				switch (lottoLine[i].getCnt()) {
				case 6:
					if (!lottoLine[i].getNumbers().contains(String.valueOf(bonusNum))) {
						lottoLine[i].setPrice(firstPerN);
						break;
					} else if (lottoLine[i].getNumbers().contains(String.valueOf(bonusNum))) {
						lottoLine[i].setPrice(secondPerN);
						break; // 1등과 2등
					}
				case 5:
					if (!lottoLine[i].getNumbers().contains(String.valueOf(bonusNum))) {
						lottoLine[i].setPrice(thirdPerN);
						break; // 정규번호만 5개 맞힌 경우 = 3등

					} else {
						lottoLine[i].setPrice(50000);
						break; // 정규번호만 4개 + 보너스번호 1개 맞힌 경우 = 4등
					}
				case 4:
					if (!lottoLine[i].getNumbers().contains(String.valueOf(bonusNum))) {
						lottoLine[i].setPrice(50000); // 정규번호만 4개 맞힌 경우 = 4등
						break;
					} else {
						lottoLine[i].setPrice(5000);
						break; // 정규번호만 3개 + 보너스번호 1개 맞힌 경우 = 5등
					}
				case 3:
					if (!lottoLine[i].getNumbers().contains(String.valueOf(bonusNum))) {
						lottoLine[i].setPrice(5000); // 5등
						break;
					}
				default:
					lottoLine[i].setPrice(0);
					break; 
				}
			}
		}
	}

	public static void ranking() {
		for (int i = 0; i < lottoLine.length; i++) {
			int cnt = 0; // 일치하는 번호의 개수

			if (lottoLine[i] != null) {
				for (int j = 0; j < 6; j++) { // cnt 갯수를 세는 반복문
					if (winningList.contains(lottoLine[i].numbers.get(j))) {
						sb.append(lottoLine[i].numbers.get(j)).append(" ");
						cnt++;
					}
				}

				switch (cnt) { // 일치하는 cnt 값에 따라서 등수 배정하는 switch 문
				case 6:
					if (!lottoLine[i].getNumbers().contains(String.valueOf(bonusNum))) {
						lottoLine[i].setCnt(cnt);
						rank[1]++;
					} else if (lottoLine[i].getNumbers().contains(String.valueOf(bonusNum))) {
						lottoLine[i].setCnt(cnt);
						rank[2]++;
					}
					break;
				case 5:
					if (!lottoLine[i].getNumbers().contains(String.valueOf(bonusNum))) {
						lottoLine[i].setCnt(cnt);
						rank[3]++;
					} else {
						lottoLine[i].setCnt(cnt);
						rank[4]++;
					}
					break;
				case 4:
					if (!lottoLine[i].getNumbers().contains(String.valueOf(bonusNum))) {
						lottoLine[i].setCnt(cnt);
						rank[4]++;
					} else {
						lottoLine[i].setCnt(cnt);
						rank[5]++;
					}
					break;
				case 3:
					if (!lottoLine[i].getNumbers().contains(String.valueOf(bonusNum))) {
						lottoLine[i].setCnt(cnt);
						rank[5]++;
					}
					break;
				default:
					lottoLine[i].setCnt(cnt);
					break;

				}

				sb.setLength(0);

			}
		}
	}

	public static List<Integer> chosenNumber() throws IOException { // 당첨번호 7자리 생성 메소드
		winningList.addAll(lottoNumbers()); // 당첨번호 넣기
		bonusNum = winningList.get(winningList.size() - 1);
		return winningList;
	}

	public static List<Integer> lottoNumbers() throws IOException {

		int setSize = 0;
		String tempSentence = "";
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		StackTraceElement beforeStack = stacks[1];
		if (beforeStack.getMethodName().equals("chosenNumber")) {  // 진혁 수정
			setSize = 7;
		} else if (beforeStack.getMethodName().equals("manualAndSemiAuto1")) {
			st = new StringTokenizer(bf.readLine(), " ");
			while (st.hasMoreTokens()) {
				set.add(Integer.parseInt(st.nextToken()));
			}

			if (set.size() == 6) {
				tempSentence = "수동";
			} else
				tempSentence = "반자동";
			setSize = 6;
		} else
			setSize = 6;

		while (set.size() != setSize) {
			set.add((int) (Math.random() * 45 + 1));
		}
		List<Integer> list = new ArrayList<Integer>(set);
		set.clear();

		if (beforeStack.getMethodName().equals("manualAndSemiAuto1")) {
			lottoLine[idx] = new LottoNumber(list);

		}

		return list;
	}


}