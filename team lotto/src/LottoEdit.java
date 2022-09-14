import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LottoEdit extends JDialog implements ActionListener {
	private JCheckBox[] cbNumbers = new JCheckBox[45];
	private int count = 0;
	private int editnumber = 0;
	// 반복해서 값 들어감 -> item리스너에서 추가로 값이 들어가서 생긴 문제인듯
	private List<Integer> listEdit = new ArrayList();
	private JButton btnOK;
	private int index;
	private ItemListener item;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public LottoEdit(JDialog owner) {
		super(owner, "Edit", true);
		JPanel pnl = new JPanel();

		item = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				JCheckBox cb = (JCheckBox) e.getSource();

				if (e.getStateChange() == ItemEvent.SELECTED) {
					editnumber = selectNum(cb);
					count++;
				} else {
					cancelNum(cb);
					count--;
				}

				if (count == 6) {
					btnOK.setEnabled(true);
					disableCB();
				} else if (count < 6) {
					btnOK.setEnabled(false);
					enableCB();
				}
			}
		};

		JPanel checks = new JPanel(new GridLayout(5, 9));


		// 체크박스 만들기
		for (int i = 0; i < cbNumbers.length; i++) {
			int num = 1 + i;
			cbNumbers[i] = new JCheckBox(String.valueOf(num));
			checks.add(cbNumbers[i]);
		}

		btnOK = new JButton("수정 하기");
		btnOK.addActionListener(this);

		pnl.add(checks);
		pnl.add(btnOK);

		add(pnl);
		setSize(400, 400);
		setLocationRelativeTo(null);
	}
	
	// 선택된 로또 번호 불러오기
	public void selectedLotto() {
		if (getOwner() instanceof BuyLotto) {
			BuyLotto lotto = (BuyLotto) getOwner();
			listEdit = lotto.getTotalLotto().get(index);
		}

		for (int j = 0; j < listEdit.size(); j++) {
			for (int i = 0; i < cbNumbers.length; i++) {
				int num = 1 + i;
				if (num == listEdit.get(j)) {
					count++;
					cbNumbers[i].setSelected(true);
					cbNumbers[i].setEnabled(true);
				}
			}
		}

		for (int i = 0; i < cbNumbers.length; i++) {
			cbNumbers[i].addItemListener(item); // 얘를 밑의 순서 다 끝내고 달아주기.
		}

		disableCB();
	}

	// 체크 비활성화 메소드
	public void disableCB() {
		for (int i = 0; i < cbNumbers.length; i++) {
			if (!cbNumbers[i].isSelected()) {
				cbNumbers[i].setSelected(false);
				cbNumbers[i].setEnabled(false);
			}
		}
	}

	// 체크 활성화 메소드
	public void enableCB() {
		for (int i = 0; i < cbNumbers.length; i++) {
			cbNumbers[i].setEnabled(true);
		}
	}

	// 로또 번호 선택(추가)
	public Integer selectNum(JCheckBox cb) {
		listEdit.add(Integer.valueOf(cb.getText()));
		return Integer.valueOf(cb.getText());
	}

	// 로또 번호 취소
	public void cancelNum(JCheckBox cb) {
		listEdit.remove(Integer.valueOf(cb.getText()));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (getOwner() instanceof BuyLotto) {
			if (e.getSource() == btnOK) {
				BuyLotto lotto = (BuyLotto) getOwner();
				lotto.setTotalLotto(listEdit, lotto.getMoons().get(index), index);
			}
		}
		dispose();
	}
}
