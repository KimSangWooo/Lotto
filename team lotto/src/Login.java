import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import java.awt.Color;

public class Login extends JDialog {
	private Map<String, String> members = new HashMap<>();
	private List<String> memberKey;
	private JButton btnLogin;
	private JTextField inputID;
	private boolean loginPass;
	
	
	public Login(JFrame owner) {
		super(owner);
		setModal(true);
		setTitle("로그인");
		JPanel pnl = new JPanel();
		pnl.setBackground(Color.WHITE);
		inputID = new JTextField("");
		inputID.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					inputID.setText("");
				}
			}
		});
		JLabel jb = new JLabel("ID :");
		
		btnLogin = new JButton("로그인");
		btnLogin.setBackground(Color.WHITE);
		JButton btnSignUp = new JButton("돌아가기"); // 변경
		btnSignUp.setBackground(Color.WHITE);
		
		btnSignUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String key = inputID.getText();

				if (SignUp.getIdForLogin().contains(key)) {
					JOptionPane.showMessageDialog(null, "로그인 성공");
					
					// 로그인했을 때 index 정보 전달(members의 인덱스)
					// -> key값(ID)로 몇번째 회원인지 찾아야 함. (그래서 구매화면 버튼을 눌렀을 때 모든 정보가 해당 회원에게 들어가게)
					MainMenu menu = (MainMenu) getOwner();
					int index = menu.getMembers().getIndex(key);
					menu.setLoginOn(index);	
					
					inputID.setText("");
					loginPass = true;

					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "등록되지 않은 ID입니다.");
					inputID.setText("");
				}
			}
		});
		
		SpringLayout sl_pnl = new SpringLayout();
		sl_pnl.putConstraint(SpringLayout.EAST, btnSignUp, 0, SpringLayout.EAST, inputID);
		sl_pnl.putConstraint(SpringLayout.NORTH, btnLogin, 17, SpringLayout.SOUTH, inputID);
		sl_pnl.putConstraint(SpringLayout.SOUTH, btnLogin, -34, SpringLayout.SOUTH, pnl);
		sl_pnl.putConstraint(SpringLayout.WEST, btnSignUp, 11, SpringLayout.EAST, btnLogin);
		sl_pnl.putConstraint(SpringLayout.WEST, btnLogin, 0, SpringLayout.WEST, jb);
		sl_pnl.putConstraint(SpringLayout.EAST, btnLogin, -177, SpringLayout.EAST, pnl);
		sl_pnl.putConstraint(SpringLayout.NORTH, btnSignUp, 17, SpringLayout.SOUTH, inputID);
		sl_pnl.putConstraint(SpringLayout.SOUTH, btnSignUp, -34, SpringLayout.SOUTH, pnl);
		sl_pnl.putConstraint(SpringLayout.SOUTH, inputID, -80, SpringLayout.SOUTH, pnl);
		sl_pnl.putConstraint(SpringLayout.WEST, jb, 42, SpringLayout.WEST, pnl);
		sl_pnl.putConstraint(SpringLayout.NORTH, inputID, 32, SpringLayout.NORTH, pnl);
		sl_pnl.putConstraint(SpringLayout.NORTH, jb, 7, SpringLayout.NORTH, inputID);
		sl_pnl.putConstraint(SpringLayout.WEST, inputID, 67, SpringLayout.WEST, pnl);
		sl_pnl.putConstraint(SpringLayout.EAST, inputID, -51, SpringLayout.EAST, pnl);
		pnl.setLayout(sl_pnl);
		
		pnl.add(inputID);
		pnl.add(btnLogin);
		pnl.add(btnSignUp);
		pnl.add(jb);
		getContentPane().add(pnl);
		
		setLocationRelativeTo(owner);
		setSize(350, 180);
	}

	public boolean  isLoginPass() {
		return loginPass;
	}

	public void setLoginPass(boolean loginPass) {
		this.loginPass = loginPass;
	}
}