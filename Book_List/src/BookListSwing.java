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
		
		super("18011600/�̴ٿ�");
		layInit();
		conDB();
		setVisible(true);
		setBounds(200, 200, 600, 400); //������ġ,������ġ,���α���,���α���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void layInit() {

		btnInit = new JButton("�ʱ�ȭ");
		btnInput = new JButton("�Է�");
		btnSelect1 = new JButton("�˻� 1");
		btnSelect2 = new JButton("�˻� 2");
		btnSelect3 = new JButton("�˻� 3");
		
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
			System.out.println("����̹� �ε� ����");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
      
		try { /* �����ͺ��̽��� �����ϴ� ���� */
			System.out.println("�����ͺ��̽� ���� �غ�...");
			con = DriverManager.getConnection(url, userid, pwd);
			System.out.println("�����ͺ��̽� ���� ����");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}

	private class ActionListenerInit implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			int rowcount;
			
			try {
				System.out.println("DB �ʱ�ȭ ����");
				txtResult.setText("");
				
				stmt = con.createStatement();
				
				// delete
				String query = "delete FROM orders";
				rowcount = stmt.executeUpdate(query);
				System.out.println("Orders ���̺��� " + rowcount + "���� Ʃ�� ���� �Ϸ�");
				
				query = "delete FROM book";
				rowcount = stmt.executeUpdate(query);
				System.out.println("Book ���̺��� " + rowcount + "���� Ʃ�� ���� �Ϸ�");
				
				query = "delete FROM customer";
				rowcount = stmt.executeUpdate(query);
				System.out.println("Customer ���̺��� " + rowcount + "���� Ʃ�� ���� �Ϸ�");
			
				// insert - book
				rowcount = 0;
				
				query = "INSERT INTO Book VALUES(1, '�౸�� ����', '�½�����', 7000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(2, '�౸�ƴ� ����', '������', 13000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(3, '�౸�� ����', '���ѹ̵��', 22000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(4, '���� ���̺�', '���ѹ̵��', 35000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(5, '�ǰ� ����', '�½�����', 8000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(6, '���� �ܰ躰���', '�½�����', 6000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(7, '�߱��� �߾�', '�̻�̵��', 20000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(8, '�߱��� ��Ź��', '�̻�̵��', 13000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(9, '�ø��� �̾߱�', '�Ｚ��', 7500)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(10, 'Olympic Champions', 'Pearson', 13000)";
				rowcount += stmt.executeUpdate(query);
				
				query = "INSERT INTO Book VALUES(11, 'å ���� 1', '���ѹ̵��', 1000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(12, 'å ���� 2', 'Pearson', 2000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(13, 'å ���� 3', '�̻�̵��', 3000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(14, 'å ���� 4', 'Pearson', 4000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(15, 'å ���� 5', '�½�����', 5000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(16, 'å ���� 6', '������', 6000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(17, 'å ���� 7', '������', 7000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(18, 'å ���� 8', '���ѹ̵��', 8000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(19, 'å ���� 9', '�½�����', 9000)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Book VALUES(20, 'å ���� 10', '�Ｚ��', 10000)";
				rowcount += stmt.executeUpdate(query);
				
				System.out.println("Book ���̺� " + rowcount + "���� Ʃ�� �߰� �Ϸ�");
			
				// insert - customer
				rowcount = 0;
				
				query = "INSERT INTO Customer VALUES (1, '������', '���� ��ü��Ÿ', '000-5000-0001')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Customer VALUES (2, '�迬��', '���ѹα� ����', '000-6000-0001')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Customer VALUES (3, '��̶�', '���ѹα� ������', '000-7000-0001')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Customer VALUES (4, '�߽ż�', '�̱� Ŭ������', '000-8000-0001')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Customer VALUES (5, '�ڼ���', '���ѹα� ����',  NULL)";
				rowcount += stmt.executeUpdate(query);
				
				query = "INSERT INTO Customer VALUES (6, '�� ��� �̸�', '�� ���� �� ����', '000-5000-0002')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Customer VALUES (7, '�� ��� �̸�', '�� ���� �� ����', '000-6000-0002')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Customer VALUES (8, '�� ��� �̸�', '�� ���� �� ����', '000-7000-0002')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Customer VALUES (9, '�� ��� �̸�', '�� ���� �� ����', '000-8000-0002')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO Customer VALUES (10, '�� ��� �̸�', '�� ���� �� ����', '000-9000-0002')";
				rowcount += stmt.executeUpdate(query);
				
				System.out.println("Customer ���̺� " + rowcount + "���� Ʃ�� �߰� �Ϸ�");
			
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
				
				System.out.println("Orders ���̺� " + rowcount + "���� Ʃ�� �߰� �Ϸ�");
			
				System.out.println("DB �ʱ�ȭ �Ϸ�");
			} catch(Exception e2) {
				System.out.println("DB �ʱ�ȭ ���� : " + e2);
			}
			
		}
		
	}
	
	private class ActionListenerInput implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {

			System.out.println("Orders ������ �߰�");
			txtResult.setText("");
			
			// �Է� â
			frame = new JFrame("ORDERS �߰�");
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
			btnCompleteInput = new JButton("Orders ���̺� �ش� ������ �߰�");
			completeInputPanel.add(btnCompleteInput);
			btnCompleteInput.addActionListener(new ActionListenerCompleteInput());

			frame.add(inputPanel, BorderLayout.CENTER);
			frame.add(completeInputPanel, BorderLayout.SOUTH);

			frame.setSize(400,250);
			frame.setVisible(true);
			frame.setLocation(300, 300);
			// �Է� â
			
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
			
				System.out.println("Orders ���̺� Ʃ�� �߰� ����");
			
			} catch(Exception e3) {
				System.out.println("Orders ���̺� Ʃ�� �߰� ���� : " + e3);
				JOptionPane.showMessageDialog(null, "�Է� ���� :\n" + e3, "ERROR MESSAGE", JOptionPane.WARNING_MESSAGE);
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
			System.out.println("���� �б� ���� : " + e4);
		}
		
	}

	public static void main(String[] args) {
		
		BookListSwing BLS = new BookListSwing();
     
		BLS.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent we) {
				
				try {
					con.close();
				} catch (Exception e5) { }
				
				System.out.println("���α׷� ���� ����!");    	
				System.exit(0);
				
			}
			
		});
		
	}	
	
}

