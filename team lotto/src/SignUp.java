import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class SignUp extends JDialog {
	private static Map<String, Members> checkID = new HashMap<>();
	private static List<String> idForLogin = new ArrayList<>();

	public static List<String> getIdForLogin() {
		return idForLogin;
	}

	public void setIdForLogin(List<String> idForLogin) {
		this.idForLogin = idForLogin;
	}

	public SignUp(JFrame owner) {
		super(owner, true);

		setTitle("Signup");
		JPanel pnl = new JPanel();
		pnl.setBackground(Color.WHITE);
		JLabel jl = new JLabel("ID: ");
		JLabel jl2 = new JLabel("Name: ");

		JTextField jfId = new JTextField();
		JTextField jfName = new JTextField();
		JButton btnSign = new JButton("가입완료");
		btnSign.setBackground(Color.WHITE);
		JButton jb1 = new JButton("돌아가기");
		jb1.setBackground(Color.WHITE);

		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		Members temp = new Members();

		if (getOwner() instanceof JFrame) {
			MainMenu menu = (MainMenu) getOwner();
			temp = menu.getMembers();
		}

		for (int i = 0; i < temp.getMember().size(); i++) {
			getCheckID().put(temp.getMember().get(i).getId(), temp);

		}

		btnSign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (getOwner() instanceof JFrame) {
					if (e.getSource() == btnSign) {
						MainMenu menu = (MainMenu) getOwner();
						if (jfId.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "ID를 입력해주세요.");
						} else if (jfName.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "이름을 입력해주세요. ");
						} else if (jfName.getText().charAt(0) == ' ' || jfId.getText().charAt(0) == ' ') {
							JOptionPane.showMessageDialog(null, " 공백을 입력할 수 없습니다. ");
						}
						else if (!idForLogin.contains(jfId.getText())) {
							menu.getMembers().addMember(jfId.getText(), jfName.getText());
							idForLogin.add(jfId.getText());
							JOptionPane.showMessageDialog(null, "회원가입 되었습니다.");
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "이미 가입된 아이디입니다.");
						}
					}
				}
			}
		});

		SpringLayout sl_pnl = new SpringLayout();
		sl_pnl.putConstraint(SpringLayout.EAST, jl, -245, SpringLayout.EAST, pnl);
		sl_pnl.putConstraint(SpringLayout.EAST, jfId, -56, SpringLayout.EAST, pnl);
		sl_pnl.putConstraint(SpringLayout.WEST, jfName, 98, SpringLayout.WEST, pnl);
		sl_pnl.putConstraint(SpringLayout.EAST, jfName, -56, SpringLayout.EAST, pnl);
		sl_pnl.putConstraint(SpringLayout.NORTH, jl2, 3, SpringLayout.NORTH, jfName);
		sl_pnl.putConstraint(SpringLayout.EAST, jl2, 0, SpringLayout.EAST, jl);
		sl_pnl.putConstraint(SpringLayout.NORTH, jl, 3, SpringLayout.NORTH, jfId);
		sl_pnl.putConstraint(SpringLayout.NORTH, jfId, 53, SpringLayout.NORTH, pnl);
		sl_pnl.putConstraint(SpringLayout.SOUTH, jfId, -187, SpringLayout.SOUTH, pnl);
		sl_pnl.putConstraint(SpringLayout.NORTH, jfName, 26, SpringLayout.SOUTH, jfId);
		sl_pnl.putConstraint(SpringLayout.WEST, jfId, 98, SpringLayout.WEST, pnl);
		sl_pnl.putConstraint(SpringLayout.NORTH, btnSign, 161, SpringLayout.NORTH, pnl);
		sl_pnl.putConstraint(SpringLayout.WEST, jb1, 170, SpringLayout.WEST, pnl);
		sl_pnl.putConstraint(SpringLayout.EAST, jb1, -56, SpringLayout.EAST, pnl);
		sl_pnl.putConstraint(SpringLayout.SOUTH, btnSign, -69, SpringLayout.SOUTH, pnl);
		sl_pnl.putConstraint(SpringLayout.WEST, btnSign, 50, SpringLayout.WEST, pnl);
		sl_pnl.putConstraint(SpringLayout.EAST, btnSign, -12, SpringLayout.WEST, jb1);
		sl_pnl.putConstraint(SpringLayout.NORTH, jb1, 0, SpringLayout.NORTH, btnSign);
		sl_pnl.putConstraint(SpringLayout.SOUTH, jb1, -69, SpringLayout.SOUTH, pnl);
		pnl.setLayout(sl_pnl);
		pnl.add(jl);
		pnl.add(jl2);
		pnl.add(jfName);
		pnl.add(jfId);
		pnl.add(btnSign);
		pnl.add(jb1);

		getContentPane().add(pnl);

		setSize(350, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public static Map<String, Members> getCheckID() {
		return checkID;
	}

	public void setCheckID(Map<String, Members> checkID) {
		this.checkID = checkID;
	}
}
