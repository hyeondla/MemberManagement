import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class MemberGUI extends JFrame {
	
	JTextField tfDbIp, tfDbUsername;
	JPasswordField pfDbPassword;
	
	JTextField tfIdx, tfName, tfAge, tfEmail1, tfEmail2, tfJumin1, tfJumin2;
	JComboBox<String> comboEmailDomain;
	
	JButton btnLogin;
	
	JTable table; // 회원 목록 정보 표시
	
	boolean isLogin; // 로그인 상태 저장
	
	public MemberGUI() {
		showFrame();
	}
	
	public void showFrame() {
		setTitle("회원관리 프로그램");
		setBounds(600, 400, 822, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false); // 프레임 크기 조절 불가능
		
		createWestPanel();
		createNorthPanel();
		createSouthPanel();
		createCenterPanel();
		
		setVisible(true);
	}
	
	public void createCenterPanel() {
		
		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false); // 테이블 컬럼 이동 불가능
		
		JScrollPane scrollPane = new JScrollPane(table); // 스크롤바 표시
		add(scrollPane, BorderLayout.CENTER);
		
		String[] columnNames = {"번호", "이름", "나이", "E-Mail", "주민번호"};
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0); // 0 : 표시할 행 번호
		table.setModel(tableModel);
		
		// 데이터 수평 정렬
		DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
		tableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER); // 정렬 방식 지정
		TableColumnModel tableColumnModel = table.getColumnModel(); // TableColumnModel 객체 가져오기
		// getColumn() : 정렬할 셀 지정
		// setCellRenderer() : 정렬 방식 객체 전달
		tableColumnModel.getColumn(0).setCellRenderer(tableCellRenderer);
		tableColumnModel.getColumn(1).setCellRenderer(tableCellRenderer);
		tableColumnModel.getColumn(2).setCellRenderer(tableCellRenderer);
		tableColumnModel.getColumn(3).setCellRenderer(tableCellRenderer);
		tableColumnModel.getColumn(4).setCellRenderer(tableCellRenderer);
		
		// 컬럼 너비(폭) 변경
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // 자동 조절 끄기
		// setPreferredWidth() : 셀 너비 지정 (픽셀)
		tableColumnModel.getColumn(0).setPreferredWidth(50);
		tableColumnModel.getColumn(1).setPreferredWidth(100);
		tableColumnModel.getColumn(2).setPreferredWidth(50);
		tableColumnModel.getColumn(3).setPreferredWidth(200);
		tableColumnModel.getColumn(4).setPreferredWidth(200);
		
		// 클릭 시 이벤트 처리
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showSelectedMemberInfo(); 
			}
		});
		
	}
	
	public void createWestPanel() {
		
		JPanel pWest = new JPanel();
		pWest.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        getContentPane().add(pWest, BorderLayout.WEST);
        pWest.setLayout(new GridLayout(5, 1, 0, 0));

        JPanel pIdx = new JPanel();
        FlowLayout flowLayout = (FlowLayout) pIdx.getLayout();
        flowLayout.setAlignment(FlowLayout.RIGHT);
        pWest.add(pIdx);
        pIdx.add(new JLabel("회원번호"));
        tfIdx = new JTextField();
        pIdx.add(tfIdx);
        tfIdx.setColumns(12);
        tfIdx.setEditable(false); // 입력,변경 불가
        
        JPanel pName = new JPanel();
        FlowLayout flowLayout_1 = (FlowLayout) pName.getLayout();
        flowLayout_1.setAlignment(FlowLayout.RIGHT);
        pWest.add(pName);
        pName.add(new JLabel("이름"));
        tfName = new JTextField();
        pName.add(tfName);
        tfName.setColumns(12);
        
        JPanel pAge = new JPanel();
        FlowLayout flowLayout_2 = (FlowLayout) pAge.getLayout();
        flowLayout_2.setAlignment(FlowLayout.RIGHT);
        pWest.add(pAge);
        pAge.add(new JLabel("나이"));
        tfAge = new JTextField();
        pAge.add(tfAge);
        tfAge.setColumns(12);
        
        JPanel pEmail = new JPanel(new GridLayout(2, 1)); // 2행 1열
        // 1행 : 이메일 입력
        pWest.add(pEmail);
        JPanel pEmailInput = new JPanel();
        FlowLayout flowLayout_3 = (FlowLayout) pEmailInput.getLayout();
        flowLayout_3.setAlignment(FlowLayout.RIGHT);
        pEmail.add(pEmailInput);
        pEmailInput.add(new JLabel("E-Mail"));
        tfEmail1 = new JTextField();
        pEmailInput.add(tfEmail1);
        tfEmail1.setColumns(5);
        pEmailInput.add(new JLabel("@"));
        tfEmail2 = new JTextField();
        pEmailInput.add(tfEmail2);
        tfEmail2.setColumns(5);
        // 2행 : 도메인 선택 (콤보박스)
        JPanel pEmailDomain = new JPanel();
        FlowLayout flowLayout_3_2 = (FlowLayout) pEmailDomain.getLayout();
        flowLayout_3_2.setAlignment(FlowLayout.RIGHT);
        pEmail.add(pEmailDomain);
        String[] items = {"직접 입력", "naver.com", "hanmail.net", "gmail.com"};
        comboEmailDomain = new JComboBox<String>();
        comboEmailDomain.setModel(new DefaultComboBoxModel<String>(items));
        pEmailDomain.add(comboEmailDomain);
        // 도메인 선택 시 입력창(tfEmail2)에 표시
        comboEmailDomain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String item = comboEmailDomain.getSelectedItem().toString();
				if(item.equals("직접 입력")) {
					tfEmail2.setText("");
					tfEmail2.requestFocus();
				} else {
					tfEmail2.setText(item);
				}
			}
		});
        
        JPanel pJumin = new JPanel();
        FlowLayout flowLayout_4 = (FlowLayout) pJumin.getLayout();
        flowLayout_4.setAlignment(FlowLayout.RIGHT);
        pWest.add(pJumin);
        pJumin.add(new JLabel("주민번호"));
        tfJumin1 = new JTextField();
        pJumin.add(tfJumin1);
        tfJumin1.setColumns(5);
        pJumin.add(new JLabel("-"));
        tfJumin2 = new JTextField();
        pJumin.add(tfJumin2);
        tfJumin2.setColumns(5);
        
        tfJumin1.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyReleased(KeyEvent e) { // 키 눌렀다 뗄 때 동작
        		// e.getKeyChar() : 눌려진 키의 문자 리턴 (대소문자 구별O)
        		// e.getKeyCode() : 눌려진 키의 아스키코드 리턴 (대소문자 구별X)
        		char keyChar = e.getKeyChar();
        		String jumin1Num = tfJumin1.getText(); 
        		
        		if(Character.isDigit(keyChar)) { // 숫자키 판별
        			if(jumin1Num.length() == 6) { 
        				tfJumin2.requestFocus(); // 뒷자리 입력란으로 커서 이동
        			} else if(jumin1Num.length() > 6) { 
        				jumin1Num = jumin1Num.substring(0, 6);
        				tfJumin1.setText(jumin1Num);
        			}
        		}
        	}
        	
		});
        
	}
	
	public void createNorthPanel() {
		
		JPanel pNorth = new JPanel(new GridLayout(1, 4));
		pNorth.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		getContentPane().add(pNorth, BorderLayout.NORTH);
		
		JPanel pDbIp = new JPanel();
		pNorth.add(pDbIp);
		JLabel lblDbIp = new JLabel("IP");
		pDbIp.add(lblDbIp);
		tfDbIp = new JTextField();
		pDbIp.add(tfDbIp);
		tfDbIp.setColumns(10);
		tfDbIp.setText("localhost");
		tfDbIp.setEditable(false);
		
		JPanel pDbUsername = new JPanel();
		pNorth.add(pDbUsername);
		JLabel lblDbUsername = new JLabel("ID");
		pDbUsername.add(lblDbUsername);
		tfDbUsername = new JTextField();
		pDbUsername.add(tfDbUsername);
		tfDbUsername.setColumns(10);
		
		JPanel pDbPassword = new JPanel();
		pNorth.add(pDbPassword);
		JLabel lblDbPassword = new JLabel("PASSWORD");
		pDbPassword.add(lblDbPassword);
		pfDbPassword = new JPasswordField();
		pfDbPassword.setColumns(10);
		pDbPassword.add(pfDbPassword);
		
		JPanel pLoginBtn = new JPanel();
		pNorth.add(pLoginBtn);
		btnLogin = new JButton("LOGIN");
		pLoginBtn.add(btnLogin);
		// 로그인 버튼 클릭 시 이벤트
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		
	}

	public void createSouthPanel() {
		
		JPanel pSouth = new JPanel();
		pSouth.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		getContentPane().add(pSouth, BorderLayout.SOUTH);
		
		JButton btnInsert = new JButton("회원추가");
		JButton btnUpdate = new JButton("회원수정");
		JButton btnSelect = new JButton("회원목록");
		JButton btnDelete = new JButton("회원삭제");
		
		pSouth.add(btnInsert);
		pSouth.add(btnUpdate);
		pSouth.add(btnSelect);
		pSouth.add(btnDelete);
		
		// 버튼 4개를 하나의 ActionListener로 연결 
		ActionListener btnListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!isLogin) {
					JOptionPane.showMessageDialog(MemberGUI.this, "로그인이 필요합니다", "로그인 요청", JOptionPane.WARNING_MESSAGE);
					tfDbUsername.requestFocus();
				} else {
					// e.getSource() : 클릭된 버튼 객체 가져오기
					if(e.getSource() == btnInsert) {
						insert();
					} else if(e.getSource() == btnUpdate) {
						update();
					} else if(e.getSource() == btnSelect) {
						select();
					} else if(e.getSource() == btnDelete) {
						delete();
					}
				}
			}
		};
		
		btnInsert.addActionListener(btnListener);
		btnUpdate.addActionListener(btnListener);
		btnSelect.addActionListener(btnListener);
		btnDelete.addActionListener(btnListener);
		
	}
	
	public void insert() {
		
		boolean isValid = inputCheck();
		if(!isValid) {
			return;
		}
		
		String name = tfName.getText();
		int age = Integer.parseInt(tfAge.getText());
		String email = tfEmail1.getText() + "@" + tfEmail2.getText(); 
		String jumin = tfJumin1.getText() + "-" + tfJumin2.getText();
		
		MemberDTO dto = new MemberDTO(0, name, age, email, jumin);
		
		MemberDAO dao = new MemberDAO();
		int insertCount = dao.insert(dto);
		if(insertCount > 0) {
			JOptionPane.showMessageDialog(MemberGUI.this, "회원 추가 완료", "알림", JOptionPane.INFORMATION_MESSAGE);
			// 입력란 초기화
			tfName.setText("");
			tfAge.setText("");
			tfEmail1.setText("");
			tfEmail2.setText("");
			tfJumin1.setText("");
			tfJumin2.setText("");
			select(); // 회원 목록 표시 
		} else {
			JOptionPane.showMessageDialog(MemberGUI.this, "회원 추가 실패", "알림", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void update() {
		
		boolean isValid = inputCheck();
		if(!isValid) {
			return;
		}
		
		String name = tfName.getText();
		int age = Integer.parseInt(tfAge.getText());
		String email = tfEmail1.getText() + "@" + tfEmail2.getText(); 
		String jumin = tfJumin1.getText() + "-" + tfJumin2.getText();
		
		MemberDTO dto = new MemberDTO(Integer.parseInt(tfIdx.getText()), name, age, email, jumin);
		
		MemberDAO dao = new MemberDAO();
		int updateCount = dao.update(dto);
		if(updateCount > 0) {
			JOptionPane.showMessageDialog(MemberGUI.this, "회원 수정 완료", "알림", JOptionPane.INFORMATION_MESSAGE);
			// 입력란 초기화
			tfName.setText("");
			tfAge.setText("");
			tfEmail1.setText("");
			tfEmail2.setText("");
			tfJumin1.setText("");
			tfJumin2.setText("");
			select(); // 회원 목록 표시 
		} else {
			JOptionPane.showMessageDialog(MemberGUI.this, "회원 수정 실패", "알림", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void select() {
		
		MemberDAO dao = new MemberDAO();
		Vector<Vector> memberList = dao.select();
		
		DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
		tableModel.setRowCount(0); // 행의 갯수를 0개로 설정 -> 기존 데이터 제거
		
		for(Vector rowData : memberList) {
			tableModel.addRow(rowData);
		}
		
	}
	
	public void delete() {
		
		int idx = Integer.parseInt(JOptionPane.showInputDialog(this, "삭제할 회원 번호를 입력하세요"));
		
		int selectedBtn = JOptionPane.showConfirmDialog(this, idx + "번 회원을 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
		if(selectedBtn == JOptionPane.NO_OPTION) {
			return;
		}
		
		MemberDAO dao = new MemberDAO();
		int deleteCount = dao.delete(idx);
		if(deleteCount > 0) {
			JOptionPane.showMessageDialog(MemberGUI.this, "회원 삭제 완료", "알림", JOptionPane.INFORMATION_MESSAGE);
			select(); // 회원 목록 표시
		} else {
			JOptionPane.showMessageDialog(MemberGUI.this, "회원 삭제 실패", "알림", JOptionPane.ERROR_MESSAGE);
		}
				
	}
	
	public void login() {
		
		if(!isLogin) { 
			String dbUsername = tfDbUsername.getText();
			String dbPassword = new String(pfDbPassword.getPassword());
			
			if(dbUsername.length() == 0) {
				JOptionPane.showMessageDialog(MemberGUI.this, "아이디를 입력하세요", "입력 요청", JOptionPane.WARNING_MESSAGE);
				tfDbUsername.requestFocus();
			} else if(dbPassword.length() == 0) {
				JOptionPane.showMessageDialog(MemberGUI.this, "비밀번호를 입력하세요", "입력 요청", JOptionPane.WARNING_MESSAGE);
				pfDbPassword.requestFocus();
			} else {
				// 로그인 작업
				MemberDAO dao = new MemberDAO();
				try {
					boolean loginResult = dao.login(dbUsername, dbPassword);
					if(loginResult) { // 로그인 성공
						btnLogin.setText("LOGOUT");
						isLogin = true;
						tfDbUsername.setEditable(false);
						pfDbPassword.setEditable(false);
						JOptionPane.showMessageDialog(MemberGUI.this, "로그인 성공", "성공", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (LoginFailedException e1) { // 로그인 실패
					JOptionPane.showMessageDialog(MemberGUI.this, "로그인 실패 - " + e1.getMessage(), "실패", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else { 
			// 로그아웃 작업
			btnLogin.setText("LOGIN");
			isLogin = false;
			tfDbUsername.setEditable(true);
			pfDbPassword.setEditable(true);
			pfDbPassword.setText("");
		}
		
	}
	
	private boolean inputCheck() {
		if(tfName.getText().length() == 0) {
			JOptionPane.showMessageDialog(MemberGUI.this, "이름을 입력하세요", "입력 요청", JOptionPane.WARNING_MESSAGE);
			tfName.requestFocus();
			return false;
		} else if(tfAge.getText().length() == 0) {
			JOptionPane.showMessageDialog(MemberGUI.this, "나이를 입력하세요", "입력 요청", JOptionPane.WARNING_MESSAGE);
			tfAge.requestFocus();
			return false;
		} else if(tfEmail1.getText().length() == 0 || tfEmail2.getText().length() == 0) {
			JOptionPane.showMessageDialog(MemberGUI.this, "이메일을 입력하세요", "입력 요청", JOptionPane.WARNING_MESSAGE);
			tfEmail1.requestFocus();
			return false;
		} else if(tfJumin1.getText().length() == 0 || tfJumin2.getText().length() == 0) {
			JOptionPane.showMessageDialog(MemberGUI.this, "주민번호 입력 필수!", "입력 요청", JOptionPane.WARNING_MESSAGE);
			tfJumin1.requestFocus();
			return false;
		}
		return true;
	}
	
	// 회원 선택(클릭)시 회원 정보를 입력란에 표시
	private void showSelectedMemberInfo() {
		// getValueAt(행번호, 열번호) : 테이블 레코드 정보 가져오기
		// getSelectedRow() : 행번호
		// getSelectedColumn() : 열번호
		// 리턴타입 Object -> toString() 문자열 변환 
		tfIdx.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
		tfName.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
		tfAge.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
		
		String strEmail = table.getValueAt(table.getSelectedRow(), 3).toString();
		String[] strEmailArr = strEmail.split("@"); // "@" 기준 분리
		tfEmail1.setText(strEmailArr[0]);
		tfEmail2.setText(strEmailArr[1]);
		
		String[] strJuminArr = table.getValueAt(table.getSelectedRow(), 4).toString().split("-");
		tfJumin1.setText(strJuminArr[0]);
		tfJumin2.setText(strJuminArr[1]);
	}

	public static void main(String[] args) {
		new MemberGUI();
	}

}



















