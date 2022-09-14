import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

public class MainMenu extends JFrame {
	private int roundNum = 1000;
	private String[] lottoCnt = { "0", "1", "2", "3", "4", "5" };
	private BuyLotto buyLotto;
	private LottoResult lottoResult;
	private Members members = new Members();
	private int loginOn;
	private int buyCnt = 0;
	private JLabel round;
	
	
	
	public int getRoundNum() {
		return roundNum;
	}

	public JLabel getRound() {
		return round;
	}

	public void setRound(JLabel round) {
		this.round = round;
	}

	public void setRoundNum(int roundNum) {
		this.roundNum = roundNum;
	}
	
	

	public int getBuyCnt() {
		return buyCnt;
	}
	
	public int getLoginOn() {
		return loginOn;
	}

	public void setLoginOn(int loginOn) {
		this.loginOn = loginOn;
	}

	public Members getMembers() {
		return members;
	}

	public void setMembers(Members members, int index) {
		Buyer b = members.getMember().get(index);
		this.members.getMember();
	}

	public MainMenu() {
		super("로또 프로그램");
		JPanel total = new JPanel();
		JPanel top = new JPanel();
		JPanel bottom = new JPanel();
		JPanel buttons = new JPanel();
		JLabel sentence = new JLabel("인생 한 방!");
		
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, MainMenu.class.getClassLoader().getResourceAsStream("font/12롯데마트행복Light.ttf"));
		} catch (FontFormatException | IOException e2) {
			e2.printStackTrace();
		}
		font = font.deriveFont(Font.BOLD);
		font = font.deriveFont(Font.ITALIC);
		Font font19 = font.deriveFont(19.0F);
		Font font32 = font.deriveFont(32.0F);
		sentence.setHorizontalAlignment(SwingConstants.CENTER);
		sentence.setFont(font19);
		JLabel sentence2 = new JLabel("오늘 살 복권을 내일로 미루지 말자.");
		sentence2.setFont(font32);
		

		total.setBackground(Color.white);
		top.setBackground(Color.white);
		bottom.setBackground(Color.white);
		buttons.setBackground(Color.white);

		// top & 메인 배너
		Toolkit kit = Toolkit.getDefaultToolkit();
		URL url = MainMenu.class.getClassLoader().getResource("images/캡처.JPG");
		Image img1 = kit.getImage(url);
		Image logoImg = img1.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
		SpringLayout sl_total = new SpringLayout();
		sl_total.putConstraint(SpringLayout.NORTH, top, 44, SpringLayout.SOUTH, sentence2);
		sl_total.putConstraint(SpringLayout.WEST, top, 22, SpringLayout.WEST, total);
		sl_total.putConstraint(SpringLayout.WEST, sentence, 0, SpringLayout.WEST, sentence2);
		sl_total.putConstraint(SpringLayout.NORTH, bottom, 309, SpringLayout.NORTH, total);
		sl_total.putConstraint(SpringLayout.EAST, bottom, -51, SpringLayout.WEST, buttons);
		sl_total.putConstraint(SpringLayout.WEST, buttons, 384, SpringLayout.WEST, total);
		sl_total.putConstraint(SpringLayout.SOUTH, buttons, -135, SpringLayout.SOUTH, total);
		total.setLayout(sl_total);

		total.add(top);
		total.add(bottom);
		total.add(sentence);
		total.add(sentence2);

		// 여러 버튼들
		JButton btnSign = new JButton("회원가입");
		sl_total.putConstraint(SpringLayout.SOUTH, sentence, -93, SpringLayout.NORTH, btnSign);
		sl_total.putConstraint(SpringLayout.SOUTH, bottom, -6, SpringLayout.NORTH, btnSign);

		// 구매 장수 선택
		JLabel lblCnt = new JLabel("구매 장수 선택");
		bottom.add(lblCnt);
		JComboBox combo = new JComboBox(lottoCnt);

		// 수량 선택에 따라 넘아감
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buyCnt = Integer.parseInt(combo.getSelectedItem().toString());
			}
		});
		
		bottom.add(combo);
		sl_total.putConstraint(SpringLayout.SOUTH, combo, -262, SpringLayout.SOUTH, total);
		sl_total.putConstraint(SpringLayout.EAST, combo, -367, SpringLayout.EAST, total);
		sl_total.putConstraint(SpringLayout.NORTH, btnSign, 346, SpringLayout.NORTH, total);
		sl_total.putConstraint(SpringLayout.WEST, btnSign, 0, SpringLayout.WEST, total);
		sl_total.putConstraint(SpringLayout.SOUTH, btnSign, 0, SpringLayout.SOUTH, total);
		btnSign.setForeground(Color.BLACK);
		btnSign.setBackground(Color.WHITE);
		total.add(btnSign);

		// 회원가입 버튼 연결
		btnSign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SignUp signUp = new SignUp(MainMenu.this);
				signUp.setVisible(true);
				
			}
		});
		JButton btnBuy = new JButton("구매하기");
		sl_total.putConstraint(SpringLayout.WEST, bottom, -9, SpringLayout.WEST, btnBuy);
		sl_total.putConstraint(SpringLayout.EAST, btnSign, -6, SpringLayout.WEST, btnBuy);
		sl_total.putConstraint(SpringLayout.NORTH, btnBuy, 0, SpringLayout.NORTH, btnSign);
		sl_total.putConstraint(SpringLayout.SOUTH, btnBuy, 0, SpringLayout.SOUTH, total);
		sl_total.putConstraint(SpringLayout.WEST, btnBuy, 168, SpringLayout.WEST, total);
		sl_total.putConstraint(SpringLayout.EAST, btnBuy, -161, SpringLayout.EAST, total);
		btnBuy.setBackground(Color.WHITE);
		total.add(btnBuy);

		// 구매하기 버튼 연결
		btnBuy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Login login = new Login(MainMenu.this);
				login.setLocationRelativeTo(null);
				login.setVisible(true);
				if (login.isLoginPass()) {
					if(buyCnt == 0) {
						JOptionPane.showMessageDialog(null, "로또 구매 장 수를 선택하세요.");
					}
					else {
						buyLotto = new BuyLotto(MainMenu.this);
						buyLotto.setVisible(true);
					}
				}
			}
		});
		
		JButton btnEnd = new JButton("결과확인");
		sl_total.putConstraint(SpringLayout.NORTH, btnEnd, 20, SpringLayout.SOUTH, buttons);
		sl_total.putConstraint(SpringLayout.SOUTH, btnEnd, 0, SpringLayout.SOUTH, total);
		btnEnd.setBackground(Color.WHITE);
		sl_total.putConstraint(SpringLayout.WEST, btnEnd, -155, SpringLayout.EAST, total);
		sl_total.putConstraint(SpringLayout.EAST, btnEnd, 0, SpringLayout.EAST, total);
		total.add(btnEnd);

		// 당첨 결과 버튼 연결 
		btnEnd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (members.checkEmpty()) {
					JOptionPane.showMessageDialog(null, "먼저 구매하기를 해주세요.");
				} else {
					try {
						lottoResult = new LottoResult(MainMenu.this);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					lottoResult.setVisible(true);
				}
			}
		});
		total.add(buttons);
		getContentPane().add(total);
		JLabel logo = new JLabel(new ImageIcon(logoImg));
		sl_total.putConstraint(SpringLayout.EAST, sentence, -10, SpringLayout.WEST, logo);
		sl_total.putConstraint(SpringLayout.NORTH, logo, 40, SpringLayout.NORTH, total);
		sl_total.putConstraint(SpringLayout.WEST, logo, 110, SpringLayout.WEST, total);
		sl_total.putConstraint(SpringLayout.SOUTH, logo, -186, SpringLayout.SOUTH, total);
		sl_total.putConstraint(SpringLayout.EAST, logo, -122, SpringLayout.EAST, total);
		total.add(logo);
		
		round = new JLabel(roundNum + "회\r\n");
		sl_total.putConstraint(SpringLayout.NORTH, round, 6, SpringLayout.SOUTH, logo);
		sl_total.putConstraint(SpringLayout.WEST, round, 182, SpringLayout.WEST, total);
		sl_total.putConstraint(SpringLayout.EAST, round, -178, SpringLayout.EAST, total);
		Font font21 = font.deriveFont(21.0F);
		round.setFont(font21);
		round.setHorizontalAlignment(SwingConstants.CENTER);
		total.add(round);
		setSize(500, 500);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);// 창이 가운데 나오게

		setResizable(false);// 창의 크기를 변경하지 못하게
	}

	public static void main(String[] args) {
		new MainMenu().setVisible(true);
	}
}
