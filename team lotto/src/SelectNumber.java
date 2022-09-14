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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

public class SelectNumber extends JDialog implements ActionListener {
	private JCheckBox[] cbNumbers = new JCheckBox[45];
	private int count = 0;
	private List<Integer> list = new ArrayList();
	private List<JLabel> lblSelNums = new ArrayList();
	private JButton btnOK;
	private int index;
	private JButton btnBack;

	public void setIndex(int index) {
		this.index = index;
	}

	public SelectNumber(JDialog owner) {
		super(owner, "번호 선택창", true);
		JPanel pnl = new JPanel();
		JPanel pnlNumbers = new JPanel();

		btnOK = new JButton("선택 완료");
		okEnable();

		ItemListener item = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				JCheckBox cb = (JCheckBox) e.getSource();

				if (e.getStateChange() == ItemEvent.SELECTED) {
					int num = Integer.valueOf(selectNum(cb));
					lblSelNums.get(count).setText(String.format("%02d", num));
					count++;
				} else {
					int index = list.indexOf(Integer.valueOf(cb.getText()));
					cancelNum(cb);
					pnlNumbers.remove(lblSelNums.get(index));
					lblSelNums.remove(index);
					pnlNumbers.revalidate();
					pnlNumbers.repaint();
					lblSelNums.add(new JLabel());
					int lastIndex = lblSelNums.size() - 1;
					pnlNumbers.add(lblSelNums.get(lastIndex));
					count--;
				}

				if (count == 6) {
					disableCB();
				} else if (count < 6) {
					enableCB();
				}

				okEnable();
			}
		};

		JPanel checks = new JPanel(new GridLayout(5, 9));

		for (int i = 0; i < cbNumbers.length; i++) {
			int num = 1 + i;
			cbNumbers[i] = new JCheckBox(String.valueOf(num));
			cbNumbers[i].addItemListener(item);

			checks.add(cbNumbers[i]);
		}
		
		pnl.add(checks);
		
		// 레이블을 패널에 추가하기
		for (int i = 0; i < 6; i++) {
			lblSelNums.add(new JLabel());
			pnlNumbers.add(lblSelNums.get(i));
		}

		pnl.add(pnlNumbers);

		btnBack = new JButton("돌아가기");

		btnOK.addActionListener(this);
		btnBack.addActionListener(this);

		pnl.add(btnOK);
		pnl.add(btnBack);

		add(pnl);
		setSize(400, 400);
		setLocationRelativeTo(null);
	}

	// ___ 선택 버튼 활성화/비활성화 (선택해야 활성화 됨)
	public void okEnable() {
		if (count == 0) {
			btnOK.setEnabled(false);
		} else {
			btnOK.setEnabled(true);
		}
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
	public String selectNum(JCheckBox cb) {
		list.add(Integer.valueOf(cb.getText()));
		return cb.getText();
	}

	// 로또 번호 취소
	public void cancelNum(JCheckBox cb) {
		list.remove(Integer.valueOf(cb.getText()));
	}

	// (선택완료 버튼 눌렀을 때) 값 반환하는 메소드
	public List<Integer> compleList() {
		return list;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (getOwner() instanceof BuyLotto) {
			if (e.getSource() == btnOK) {
				Lotto temp = new Lotto();
				Set<Integer> tempSet = new HashSet<>();
				try {
					tempSet = temp.manualAndSemiAuto1(list);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				List<Integer> tempList = new ArrayList<Integer>(tempSet);
				BuyLotto lotto = (BuyLotto) getOwner();
				lotto.setTotalLotto(tempList, lotto.getMoons().get(index), index);
				dispose();
			} else if (e.getSource() == btnBack) {
				dispose();
			}
		}
	}
}