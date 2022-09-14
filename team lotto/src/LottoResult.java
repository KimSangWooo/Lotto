import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class LottoResult extends JDialog {
	private List<Integer> winningNum = lotto.chosenNumber();
	private TableCell cell = new TableCell();
	private int index;
	private MainMenu menu = (MainMenu) getOwner();
	
	public MainMenu getMenu() {
		return menu;
	}

	public List<Integer> getWinningNum() {
		return winningNum;
	}

	private StringBuilder sbWinNum = new StringBuilder();
	public static Lotto lotto = new Lotto();
	private JTable table_1;
	

	
	public String getID(int num) {
		MainMenu menu = (MainMenu) getOwner();
		return menu.getMembers().getMember().get(num).getId();
	}
	
	public String getName(int num) {
		MainMenu menu = (MainMenu) getOwner();
		return menu.getMembers().getMember().get(num).getName();
	}
	
	public int getReward(int num) {
		MainMenu menu = (MainMenu) getOwner();
		return menu.getMembers().getMember().get(num).getReward();
	}
	
	public int getIndex() {
		return index;
	}

	public LottoResult(JFrame owner) throws IOException {
		super(owner, true);
		MainMenu menu = (MainMenu) getOwner();
		Buyer buyer = menu.getMembers().getMember().get(index);
		for (int i = 0; i < buyer.getOneLottoNums().size(); i++) {
			buyer.getOneLottoNums().get(i).compareTo(winningNum);
		}
		
		
		
		
		setTitle("당첨 결과");
		JPanel main = new JPanel();
		main.setBackground(Color.WHITE);
		

		
		for(int i = 0 ; i <= 5; i++) {
			sbWinNum.append(winningNum.get(i)).append("  "); //앞 6개자리는 정규번호 .
		}
		sbWinNum.append("  +  "); //보너스번호를 이어줄 +기호
		sbWinNum.append(winningNum.get(6));//당첨 번호 맨 마지막 번호(보너스 번호) 추가
	
		JLabel printWinNum = new JLabel(sbWinNum.toString());
		printWinNum.setFont(new Font("HY울릉도M", Font.BOLD, 13));
		JPanel pnlWin = new JPanel();
		pnlWin.add(printWinNum);
		pnlWin.setBackground(Color.ORANGE);
		main.add(pnlWin);
		

		
		SpringLayout sl_main = new SpringLayout();
		sl_main.putConstraint(SpringLayout.WEST, pnlWin, 109, SpringLayout.WEST, main);
		sl_main.putConstraint(SpringLayout.EAST, pnlWin, -125, SpringLayout.EAST, main);
		sl_main.putConstraint(SpringLayout.EAST, printWinNum, -104, SpringLayout.EAST, main);
		main.setLayout(sl_main);
		
		JLabel lblNewLabel = new JLabel("당첨 결과");
		sl_main.putConstraint(SpringLayout.NORTH, pnlWin, 15, SpringLayout.SOUTH, lblNewLabel);
		sl_main.putConstraint(SpringLayout.NORTH, printWinNum, 18, SpringLayout.SOUTH, lblNewLabel);
		sl_main.putConstraint(SpringLayout.WEST, printWinNum, 24, SpringLayout.WEST, lblNewLabel);
		sl_main.putConstraint(SpringLayout.NORTH, lblNewLabel, 36, SpringLayout.NORTH, main);
		sl_main.putConstraint(SpringLayout.WEST, lblNewLabel, 123, SpringLayout.WEST, main);
		sl_main.putConstraint(SpringLayout.EAST, lblNewLabel, 345, SpringLayout.WEST, main);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("휴먼고딕", Font.BOLD, 23));
		main.add(lblNewLabel);

		getContentPane().add(main);
		

		
		
		String[ ] headings = new String[] {"ID", "이름", "총 구매 복권(줄)", "상세보기"}; //테이블 열
		
		Object[][] data = new Object[SignUp.getIdForLogin().size()][4];
		
		for(int i = 0 ; i < SignUp.getIdForLogin().size(); i++) {
			data[i][0] = menu.getMembers().getMember().get(i).getId();
			data[i][1] = menu.getMembers().getMember().get(i).getName();
			data[i][2] = menu.getMembers().getMember().get(i).getLottoLines().size(); 
			data[i][3] = "클릭";
		}
		
		table_1 = new JTable(data, headings);
		table_1.setPreferredScrollableViewportSize(new Dimension(200,200)); //테이블 크기
		table_1.setFillsViewportHeight(true);
		
		
		JScrollPane scrollPane = new JScrollPane(table_1);
		sl_main.putConstraint(SpringLayout.WEST, scrollPane, 33, SpringLayout.WEST, main);
		sl_main.putConstraint(SpringLayout.EAST, scrollPane, -29, SpringLayout.EAST, main);
		main.add(scrollPane);
		
		lotto.run();
		JLabel reward1 = new JLabel("1등 총 상금 : " + lotto.firstPrice + "원  /  1등 당첨 명 수 : " + lotto.rank[1] +" 명 /  1인당 당첨 금액 : " + lotto.firstPerN  +"원");
		sl_main.putConstraint(SpringLayout.SOUTH, pnlWin, -33, SpringLayout.NORTH, reward1);
		sl_main.putConstraint(SpringLayout.WEST, reward1, 33, SpringLayout.WEST, main);
		sl_main.putConstraint(SpringLayout.NORTH, reward1, 75, SpringLayout.SOUTH, lblNewLabel);
		main.add(reward1);
		JLabel reward2 = new JLabel("2등 총 상금 : " + lotto.secondPrice + "원  /  2등 당첨 명 수 : " + lotto.rank[2] +" 명 /  1인당 당첨 금액 : " + lotto.secondPerN  +"원");
		sl_main.putConstraint(SpringLayout.NORTH, reward2, 6, SpringLayout.SOUTH, reward1);
		sl_main.putConstraint(SpringLayout.EAST, reward1, 0, SpringLayout.EAST, reward2);
		sl_main.putConstraint(SpringLayout.WEST, reward2, 32, SpringLayout.WEST, main);
		main.add(reward2);
		JLabel reward3 = new JLabel("3등 총 상금 : " + lotto.thirdPrice + "원  /  3등 당첨 명 수 : " + lotto.rank[3] +" 명 /  1인당 당첨 금액 : " + lotto.thirdPerN  +"원");
		sl_main.putConstraint(SpringLayout.NORTH, reward3, 6, SpringLayout.SOUTH, reward2);
		sl_main.putConstraint(SpringLayout.EAST, reward2, 0, SpringLayout.EAST, reward3);
		sl_main.putConstraint(SpringLayout.WEST, reward3, 32, SpringLayout.WEST, main);
		main.add(reward3);
		
		JLabel reward4 = new JLabel("4등 총 상금 : " + lotto.fourthPrice + "원  /  4등 당첨 명 수 : " + lotto.rank[4] +" 명 /  1인당 당첨 금액 : " + "50,000원");
		sl_main.putConstraint(SpringLayout.NORTH, reward4, 6, SpringLayout.SOUTH, reward3);
		sl_main.putConstraint(SpringLayout.EAST, reward3, 0, SpringLayout.EAST, reward4);
		sl_main.putConstraint(SpringLayout.WEST, reward4, 32, SpringLayout.WEST, main);
		sl_main.putConstraint(SpringLayout.EAST, reward4, -10, SpringLayout.EAST, main);
		main.add(reward4);
		
		JLabel reward5 = new JLabel("5등 총 상금 : " + lotto.fifthPrice + "원  /  5등 당첨 명 수 : " + lotto.rank[5] +" 명 /  1인당 당첨 금액 : " + "5,000원");
		sl_main.putConstraint(SpringLayout.NORTH, scrollPane, 23, SpringLayout.SOUTH, reward5);
		sl_main.putConstraint(SpringLayout.NORTH, reward5, 6, SpringLayout.SOUTH, reward4);
		sl_main.putConstraint(SpringLayout.EAST, reward5, 0, SpringLayout.EAST, reward4);
		sl_main.putConstraint(SpringLayout.WEST, reward5, 32, SpringLayout.WEST, main);
		main.add(reward5);
		table_1.getColumnModel().getColumn(3).setCellRenderer(new TableCell());
		table_1.getColumnModel().getColumn(3).setCellEditor(new TableCell());
		
		
		
		JButton gotoMain = new JButton("메인 화면으로");
		gotoMain.setBackground(Color.WHITE);
		sl_main.putConstraint(SpringLayout.NORTH, gotoMain, 408, SpringLayout.NORTH, main);
		sl_main.putConstraint(SpringLayout.SOUTH, scrollPane, -26, SpringLayout.NORTH, gotoMain);
		sl_main.putConstraint(SpringLayout.EAST, gotoMain, 0, SpringLayout.EAST, scrollPane);
		main.add(gotoMain);
		
		gotoMain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 버튼 클릭시
				// 1. 회차 올라가기 (숫자)
				int upRound = menu.getRoundNum() + 1;
				menu.setRoundNum(upRound);
				menu.getRound().setText(upRound + "회"); // 성공 
				// 2. 현재까지 정보 리셋
				// - 1만명 돌린것
				lotto.resetRank(); // 순위 인원 초기화
				lotto.resetPrice();
				// - 당첨번호
				winningNum.clear();
				sbWinNum.setLength(0);  // 성공 -> 상세보기에 적용되게 해야함 (새롭게 생성자로 넣어야 할듯?)
				// - 회원정보에서 로또List만 리셋
				menu.getMembers().resetMemOfLot(); // 성공  ->
				
				dispose();
			}
		});
		setSize(500, 500);
		setLocationRelativeTo(null); //화면 가운데로
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
	
	class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{
		JButton jb;
		
		public JButton getJb() {
			return jb;
		}

		public void setJb(JButton jb) {
			this.jb = jb;
		}

		public TableCell() {
			jb = new JButton("버튼");
			jb.addActionListener(e -> {
				int num = table_1.getSelectedRow();
				index = num;
				Detail d = new Detail(LottoResult.this);
		
				d.setVisible(true);
			});
		}

		@Override
		public Object getCellEditorValue() {
			return null;
		}
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			return jb;
		}
		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			return jb;
		}
	}
}

