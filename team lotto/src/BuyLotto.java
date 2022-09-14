import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SpringLayout;

public class BuyLotto extends JDialog {
//	private List<Integer> oneLotto;
	private List<List<Integer>> totalLotto = new ArrayList<>(5);
	private int buyCnt;
	private List<JLabel[]> moons;
	private JButton[][] toolBtns;
	private JButton btnBuy;

	public BuyLotto(JFrame owner) {
		super(owner, true);
		for (int i = 0; i < 5; i++) {
			totalLotto.add(null);
		}

		MainMenu menu = (MainMenu) getOwner();
		buyCnt = menu.getBuyCnt();

		JPanel pnlTotal = new JPanel();
		JPanel pnl = new JPanel();

		// 이미지
		Toolkit kit = Toolkit.getDefaultToolkit();
		URL url = BuyLotto.class.getClassLoader().getResource("images/보름달.png");
		Image img1 = kit.getImage(url);
		Image moonLight = img1.getScaledInstance(30, 30, Image.SCALE_SMOOTH);

		URL url2 = BuyLotto.class.getClassLoader().getResource("images/잿빛달.png");
		Image img2 = kit.getImage(url2);
		Image moonGray = img2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);

		// 그림 생성 -> 나중에 text값만 바꿔주면 됨.
		moons = ImgLines(buyCnt, moonLight, moonGray);
		JPanel[] pnlFive = fivePanel();
		JPanel packFive = new JPanel();
		packFive.setLayout(new BoxLayout(packFive, BoxLayout.Y_AXIS));

		for (int i = 0; i < pnlFive.length; i++) {
			for (int j = 0; j < moons.get(i).length; j++) {
				pnlFive[i].add(moons.get(i)[j]);
				packFive.add(pnlFive[i]);
			}
		}

		// 버튼 구매 개수에 따라 생성하기
		JPanel packFive2 = new JPanel();
		JPanel[] pnlCnt = makePanel(5);
		packFive2.setLayout(new BoxLayout(packFive2, BoxLayout.Y_AXIS));

		// 방법선택(auto, 수동&반자동, edit, reset)
		// ________________진혁 수정(고정 버튼으로)__________________________
		toolBtns = new JButton[5][3];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				if (j == 0) {
					toolBtns[i][j] = new JButton("Auto");
					toolBtns[i][j].addActionListener(auto(i));
				} else if (j == 1) {
					toolBtns[i][j] = new JButton("수동 & 반자동");
					toolBtns[i][j].addActionListener(manual(i));
				} else {
					toolBtns[i][j] = new JButton("Edit");
					toolBtns[i][j].addActionListener(edit(i));
				}
			}
		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < toolBtns[i].length; j++) {
				pnlCnt[i].add(toolBtns[i][j]);
				packFive2.add(pnlCnt[i]);
			}
		}

		for (int i = buyCnt; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				toolBtns[i][j].setEnabled(false);
				;
			}
		}
		SpringLayout sl_pnlTotal = new SpringLayout();
		sl_pnlTotal.putConstraint(SpringLayout.NORTH, pnl, 11, SpringLayout.NORTH, pnlTotal);
		sl_pnlTotal.putConstraint(SpringLayout.WEST, pnl, 29, SpringLayout.WEST, pnlTotal);
		sl_pnlTotal.putConstraint(SpringLayout.SOUTH, pnl, 216, SpringLayout.NORTH, pnlTotal);
		sl_pnlTotal.putConstraint(SpringLayout.EAST, pnl, 596, SpringLayout.WEST, pnlTotal);
		pnlTotal.setLayout(sl_pnlTotal);
		pnl.add(packFive);
		pnl.add(packFive2);
		pnlTotal.add(pnl);

		getContentPane().add(pnlTotal);
		
		
		btnBuy = new JButton("구매완료");
		
		btnBuy.setEnabled(false);
		sl_pnlTotal.putConstraint(SpringLayout.SOUTH, btnBuy, -27, SpringLayout.SOUTH, pnlTotal);
		sl_pnlTotal.putConstraint(SpringLayout.EAST, btnBuy, -41, SpringLayout.EAST, pnlTotal);
		pnlTotal.add(btnBuy);

		// 구매 완료 (버튼 선택시 로또 번호 들어감)
		btnBuy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainMenu menu = (MainMenu) getOwner();
				int index = menu.getLoginOn();
				Buyer buyer = menu.getMembers().getMember().get(index);
				for (int i = 0; i < totalLotto.size(); i++) {
					if (totalLotto.get(i) != null) {
					buyer.addLottoLines(totalLotto.get(i));
					}
				}
				dispose();
			}
		});

		setModal(true);
		setSize(640, 320);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}


	public List<List<Integer>> getTotalLotto() {
		return totalLotto;
	}

	public void setTotalLotto(List<Integer> list, JLabel[] lbls, int index) {
		totalLotto.remove(index);
		totalLotto.add(index, list);

		List<Integer> oneList = totalLotto.get(index);
		for (int i = 0; i < oneList.size(); i++) {
			lbls[i].setText(String.valueOf(oneList.get(i)));
		}
	}

	// 5줄에 값 넣기
//	public void addList(List<Integer> list) {
//		totalLotto.add(oneLotto);
//	}

	public int getBuyCnt() {
		return buyCnt;
	}

	public void setBuyCnt(int buyCnt) {
		this.buyCnt = buyCnt;
	}

	public List<JLabel[]> getMoons() {
		return moons;
	}

	public void setMoons(List<JLabel[]> moons) {
		this.moons = moons;
	}
	
	public void checkAllSelected() {
		int cnt = 0;
		for (int i = 0; i < buyCnt; i++) {
			if (totalLotto.get(i) != null) cnt++;
		}
		if(cnt == buyCnt) btnBuy.setEnabled(true);
	}

	// auto버튼
	public ActionListener auto(int index) {
		ActionListener temp = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Lotto temp = new Lotto();
				List<Integer> list = new ArrayList<>();
				try {
					list = temp.autoNum1();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				setTotalLotto(list, getMoons().get(index), index);
				checkAllSelected();
			}
		};
		return temp;
	}

	// 수동 & 반자동 버튼
	public ActionListener manual(int index) {
		ActionListener temp = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SelectNumber dialog = new SelectNumber(BuyLotto.this);
				dialog.setIndex(index); // 엄청 고민했던 문제 해결해준 idea
				dialog.setVisible(true);
				checkAllSelected();
			}
		};
		return temp;
	}

	// edit버튼
	public ActionListener edit(int index) {
		ActionListener temp = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ____________________박로 수정 (번호 선택해야 수정되게)__________
				if (totalLotto.get(index) == null) {
					JOptionPane.showMessageDialog(null, "번호를 선택해주세요.");
				} else {
					LottoEdit dialog = new LottoEdit(BuyLotto.this);
					dialog.setIndex(index);
					dialog.selectedLotto();
					dialog.setVisible(true);
					checkAllSelected();
				}
			}
		};
		return temp;
	}

	// 패널 5개만
	public JPanel[] fivePanel() {
		JPanel[] pnls = new JPanel[5];
		for (int i = 0; i < 5; i++) {
			pnls[i] = new JPanel();
		}
		return pnls;
	}

	// 패널 만드는 메소드
	public JPanel[] makePanel(int num) {
		JPanel[] pnls = new JPanel[num];
		for (int i = 0; i < pnls.length; i++) {
			pnls[i] = new JPanel();
		}
		return pnls;
	}

	// 이미지 담긴 레이블(size 6) 만드는 메소드
	public JLabel[] makeImgLbl(Image img) {
		JLabel[] lbls = new JLabel[6];
		for (int i = 0; i < lbls.length; i++) {
			lbls[i] = new JLabel("", new ImageIcon(img), JLabel.CENTER);
			lbls[i].setHorizontalTextPosition(JLabel.CENTER);
//				pnlSelected.add(labels[i]);
		}
		return lbls;
	}

	// 이미지레이블 배열을 담을 List를 만드는 메소드
	public List<JLabel[]> ImgLines(int buyCnt, Image Light, Image Gray) {
		List<JLabel[]> list = new ArrayList<>();
		switch (buyCnt) {
		case 1:
			for (int i = 0; i < buyCnt; i++) {
				list.add(makeImgLbl(Light));
			}
			for (int i = 0; i < 5 - buyCnt; i++) {
				list.add(makeImgLbl(Gray));
			}
			return list;
		case 2:
			for (int i = 0; i < buyCnt; i++) {
				list.add(makeImgLbl(Light));
			}
			for (int i = 0; i < 5 - buyCnt; i++) {
				list.add(makeImgLbl(Gray));
			}
			return list;
		case 3:
			for (int i = 0; i < buyCnt; i++) {
				list.add(makeImgLbl(Light));
			}
			for (int i = 0; i < 5 - buyCnt; i++) {
				list.add(makeImgLbl(Gray));
			}
			return list;
		case 4:
			for (int i = 0; i < buyCnt; i++) {
				list.add(makeImgLbl(Light));
			}
			for (int i = 0; i < 5 - buyCnt; i++) {
				list.add(makeImgLbl(Gray));
			}
			return list;
		case 5:
			for (int i = 0; i < buyCnt; i++) {
				list.add(makeImgLbl(Light));
			}
			for (int i = 0; i < 5 - buyCnt; i++) {
				list.add(makeImgLbl(Gray));
			}
			return list;
		}
		return list;
	}
}
