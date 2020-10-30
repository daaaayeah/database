import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;


public class BookListSwing extends JFrame implements ActionListener {
	
	JButton btnInit, btnInput, btnSelect1, btnSelect2, btnSelect3;
	JTextArea txtResult;
	JPanel panel, inputPanel, completeInputPanel;
	JScrollPane scrollPane;
	JFrame frame;
	JTextField oID, cID, bID, sP, oD;

	static Connection con;
	Statement stmt;
	ResultSet rs;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";
	
	public BookListSwing() {
		
		super("18011600/이다예");
		layInit();
		conDB();
		setVisible(true);
		setBounds(200, 200, 600, 400); //가로위치,세로위치,가로길이,세로길이
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void layInit() {

		btnInit = new JButton("초기화");
		btnInput = new JButton("입력");
		btnSelect1 = new JButton("검색 1");
		btnSelect2 = new JButton("검색 2");
		btnSelect3 = new JButton("검색 3");
		
		txtResult = new JTextArea();
      
		panel = new JPanel();
		panel.add(btnInit);
		panel.add(btnInput);
		panel.add(btnBook);
		panel.add(btnOrders);
		panel.add(btnCustomer);
      
		txtResult.setEditable(false);
      
		scrollPane = new JScrollPane(txtResult);
      
		add("North", panel);
		add("Center", scrollPane);
		
		btnInit.addActionListener(new ActionListenerInit());
		btnInput.addActionListener(new ActionListenerInput());
		btnBook.addActionListener(this);
		btnOrders.addActionListener(this);
		btnCustomer.addActionListener(this);
		
	}

	public void conDB() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
      
		try { /* 데이터베이스를 연결하는 과정 */
			System.out.println("데이터베이스 연결 준비...");
			con = DriverManager.getConnection(url, userid, pwd);
			System.out.println("데이터베이스 연결 성공");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}

	private class ActionListenerInit implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			int rowcount;
			
			try {
				System.out.println("DB 초기화 시작");
				txtResult.setText("");
				
				stmt = con.createStatement();
				
				// delete
				String query = "delete FROM orders";
				rowcount = stmt.executeUpdate(query);
				System.out.println("Orders 테이블에서 " + rowcount + "개의 튜플 삭제 완료");
				
				query = "delete FROM book";
				rowcount = stmt.executeUpdate(query);
				System.out.println("Book 테이블에서 " + rowcount + "개의 튜플 삭제 완료");
				
				query = "delete FROM customer";
				rowcount = stmt.executeUpdate(query);
				System.out.println("Customer 테이블에서 " + rowcount + "개의 튜플 삭제 완료");
			
				// insert - book
				rowcount = 0;
				
				query = "INSERT INTO Book VALUES(1, '축구의 역사', '굿스포츠', 7000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(2, '축구아는 여자', '나무수', 13000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(3, '축구의 이해', '대한미디어', 22000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(4, '골프 바이블', '대한미디어', 35000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(5, '피겨 교본', '굿스포츠', 8000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(6, '역도 단계별기술', '굿스포츠', 6000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(7, '야구의 추억', '이상미디어', 20000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(8, '야구를 부탁해', '이상미디어', 13000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(9, '올림픽 이야기', '삼성당', 7500)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(10, 'Olympic Champions', 'Pearson', 13000)";
				rowcount += stmt.executeUpdate(query);
				
				query = "INSERT INTO Book VALUES(11, '책 제목 1', '대한미디어', 1000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(12, '책 제목 2', 'Pearson', 2000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(13, '책 제목 3', '이상미디어', 3000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(14, '책 제목 4', 'Pearson', 4000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(15, '책 제목 5', '굿스포츠', 5000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(16, '책 제목 6', '나무수', 6000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(17, '책 제목 7', '나무수', 7000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(18, '책 제목 8', '대한미디어', 8000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(19, '책 제목 9', '굿스포츠', 9000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(20, '책 제목 10', '삼성당', 10000)";
				rowcount += stmt.executeUpdate(query);
				
				System.out.println("Book 테이블에 " + rowcount + "개의 튜플 추가 완료");
			
				// insert - customer
				rowcount = 0;
				
				query = "INSERT INTO Customer VALUES (1, '박지성', '영국 맨체스타', '000-5000-0001')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Customer VALUES (2, '김연아', '대한민국 서울', '000-6000-0001')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Customer VALUES (3, '장미란', '대한민국 강원도', '000-7000-0001')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Customer VALUES (4, '추신수', '미국 클리블랜드', '000-8000-0001')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Customer VALUES (5, '박세리', '대한민국 대전',  NULL)";
				rowcount += stmt.executeUpdate(query);
				
				query = "INSERT INTO Customer VALUES (6, '가 사람 이름', '아 나라 야 도시', '000-5000-0002')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Customer VALUES (7, '나 사람 이름', '어 나라 여 도시', '000-6000-0002')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Customer VALUES (8, '다 사람 이름', '오 나라 요 도시', '000-7000-0002')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Customer VALUES (9, '라 사람 이름', '우 나라 유 도시', '000-8000-0002')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Customer VALUES (10, '마 사람 이름', '으 나라 이 도시', '000-9000-0002')";
				rowcount += stmt.executeUpdate(query);
				
				System.out.println("Customer 테이블에 " + rowcount + "개의 튜플 추가 완료");
			
				// insert - orders
				rowcount = 0;
				
				query = "INSERT INTO Orders VALUES (1, 1, 1, 6000, STR_TO_DATE('2014-07-01','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Orders VALUES (2, 1, 3, 21000, STR_TO_DATE('2014-07-03','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Orders VALUES (3, 2, 5, 8000, STR_TO_DATE('2014-07-03','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Orders VALUES (4, 3, 6, 6000, STR_TO_DATE('2014-07-04','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Orders VALUES (5, 4, 7, 20000, STR_TO_DATE('2014-07-05','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Orders VALUES (6, 1, 2, 12000, STR_TO_DATE('2014-07-07','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Orders VALUES (7, 4, 8, 13000, STR_TO_DATE( '2014-07-07','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Orders VALUES (8, 3, 10, 12000, STR_TO_DATE('2014-07-08','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Orders VALUES (9, 2, 10, 7000, STR_TO_DATE('2014-07-09','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Orders VALUES (10, 3, 8, 13000, STR_TO_DATE('2014-07-10','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				
				query = "INSERT INTO Orders VALUES (11, 6, 11, 1000, STR_TO_DATE('2020-07-01','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Orders VALUES (12, 6, 13, 3000, STR_TO_DATE('2020-07-03','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Orders VALUES (13, 7, 15, 5000, STR_TO_DATE('2020-07-03','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Orders VALUES (14, 8, 16, 6000, STR_TO_DATE('2020-07-04','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Orders VALUES (15, 9, 17, 7000, STR_TO_DATE('2020-07-05','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Orders VALUES (16, 6, 12, 2000, STR_TO_DATE('2020-07-07','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Orders VALUES (17, 9, 18, 8000, STR_TO_DATE( '2020-07-07','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Orders VALUES (18, 8, 20, 10000, STR_TO_DATE('2020-07-08','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Orders VALUES (19, 7, 20, 10000, STR_TO_DATE('2020-07-09','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Orders VALUES (20, 8, 18, 8000, STR_TO_DATE('2020-07-10','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				
				System.out.println("Orders 테이블에 " + rowcount + "개의 튜플 추가 완료");
			
				System.out.println("DB 초기화 완료");
			} catch(Exception e2) {
				System.out.println("DB 초기화 실패 : " + e2);
			}
			
		}
		
	}
	
	private class ActionListenerInput implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {

			System.out.println("Orders 데이터 추가");
			txtResult.setText("");
			
			// 입력 창
			frame = new JFrame("ORDERS 추가");
			frame.setLayout(new BorderLayout());
			
			inputPanel = new JPanel();
			inputPanel.setLayout(new GridLayout(5, 2, 5, 5));
			
			oID = new JTextField("");
			cID = new JTextField("");				
			bID = new JTextField("");
			sP = new JTextField("");
			oD = new JTextField("");
			
			inputPanel.add(new JLabel("  Order ID"));
			inputPanel.add(oID);
			inputPanel.add(new JLabel("  Customer ID"));
			inputPanel.add(cID);
			inputPanel.add(new JLabel("  Book ID"));
			inputPanel.add(bID);
			inputPanel.add(new JLabel("  Sale Price"));
			inputPanel.add(sP);
			inputPanel.add(new JLabel("  Order Date"));
			inputPanel.add(oD);
			
			completeInputPanel = new JPanel();
			btnCompleteInput = new JButton("Orders 테이블에 해당 데이터 추가");
			completeInputPanel.add(btnCompleteInput);
			btnCompleteInput.addActionListener(new ActionListenerCompleteInput());

			frame.add(inputPanel, BorderLayout.CENTER);
			frame.add(completeInputPanel, BorderLayout.SOUTH);

			frame.setSize(400,250);
			frame.setVisible(true);
			frame.setLocation(300, 300);
			// 입력 창
			
		}
	}
	
	private class ActionListenerCompleteInput implements ActionListener {

		PreparedStatement pstmt;
		int result;
		
		public void actionPerformed(ActionEvent e) {
			
			try {
				String query = "INSERT INTO Orders VALUES (?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(query);
				
				pstmt.setString(1, oID.getText());
				pstmt.setString(2, cID.getText());
				pstmt.setString(3, bID.getText());
				pstmt.setString(4, sP.getText());
				pstmt.setString(5, oD.getText());
				result = pstmt.executeUpdate();
			
				System.out.println("Orders 테이블에 튜플 추가 성공");
			
			} catch(Exception e3) {
				System.out.println("Orders 테이블에 튜플 추가 실패 : " + e3);
				JOptionPane.showMessageDialog(null, "입력 오류 :\n" + e3, "ERROR MESSAGE", JOptionPane.WARNING_MESSAGE);
			}
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
    
		try {
			stmt = con.createStatement();
			String query;
			
			if (e.getSource() == btnBook) {
				query = "SELECT * FROM Book";
				
				txtResult.setText("");
				txtResult.setText("BOOKNO" + "\t" + "BOOK NAME" + "\t" + "PUBLISHER" + "\t" + "PRICE" + "\n");
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4) + "\n";
					txtResult.append(str);
				}
			}
			else if (e.getSource() == btnOrders) {
				query = "SELECT * FROM Orders";

				txtResult.setText("");
				txtResult.setText("ORDERNO" + "\t" + "CUSTNO" + "\t" + "BOOKNO" + "\t" + "SALE PRICE" + "\t" + "ORDER DATE" + "\n");
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4) + "\t" + rs.getDate(5) +"\n";
					txtResult.append(str);
				}
			}
			else if (e.getSource() == btnCustomer) {
				query = "SELECT * FROM Customer";

				txtResult.setText("");
				txtResult.setText("CUSTNO" + "\t" + "NAME" + "\t" + "ADDRESS" + "\t" + "PHONE" + "\n");
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) +"\n";
					txtResult.append(str);
				}
			}
		} catch (Exception e4) {
			System.out.println("쿼리 읽기 실패 : " + e4);
		}
		
	}

	public static void main(String[] args) {
		
		BookListSwing BLS = new BookListSwing();
     
		BLS.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent we) {
				
				try {
					con.close();
				} catch (Exception e5) { }
				
				System.out.println("프로그램 완전 종료!");    	
				System.exit(0);
				
			}
			
		});
		
	}	
	
}

