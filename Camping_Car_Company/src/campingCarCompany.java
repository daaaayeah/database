import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class campingCarCompany extends JFrame {

	JPanel loginPanel;
	JPanel mBtnPanel, mIOPanel;
	JPanel initPanel, carPanel, returnPanel, repairPanel, selectPanel;
	JFrame frame;

	// �뿩ȸ�� //
	JPanel companyPanel;
	JPanel companyCompletePanel;
	JButton companyCompleteBtn;
	JTextField comp_CI, comp_CN, comp_CA, comp_CP, comp_RN, comp_RE;

	// ķ��ī //
	JPanel campingCarPanel;
	JPanel campingCarCompletePanel;
	JButton campingCarCompleteBtn;
	JTextField car_CI, car_CName, car_CNum, car_NP, car_MC, car_MY, car_CDD, car_RC, car_RD, car_comp_CI, car_L;

	// �� //
	JPanel customerPanel;
	JPanel customerCompletePanel;
	JButton customerCompleteBtn;
	JTextField cust_LN, cust_CN, cust_CA, cust_CP, cust_CE;

	// ����� //
	JPanel repairShopPanel;
	JPanel repairShopCompletePanel;
	JButton repairShopCompleteBtn;
	JTextField shop_SI, shop_SN, shop_SA, shop_SP, shop_RN, shop_RE;

	// ��ȯ //
	JTextField Return_UN;
	JTextField Return_CI;
	JTextField Return_FP;
	JTextField Return_LP;
	JTextField Return_RP;
	JTextField Return_BP;
	JTextField Return_RR;

	// �������� //
	JPanel maintenanceInfoPanel;
	JPanel maintenanceInfoCompletePanel;
	JButton maintenanceInfoCompleteBtn;
	JTextField info_UMN, info_MH, info_RD, info_RC, info_PD, info_OMI, info_CRSI, info_CI, info_LN;

	JScrollPane mOutputSPane;
	JTextArea mOutputTArea;

	PreparedStatement pstmt, pstmt2;

	// ������ ��� ��ư //
	JButton btnGotoManager;

	JButton compInputBtn = new JButton("�뿩ȸ�� - �Է�");
	JButton compDeleteBtn = new JButton("�뿩ȸ�� - ����");
	JButton compModifyBtn = new JButton("�뿩ȸ�� - ����");
	JButton carInputBtn = new JButton("ķ��ī - �Է�");
	JButton carDeleteBtn = new JButton("ķ��ī - ����");
	JButton carModifyBtn = new JButton("ķ��ī - ����");
	JButton custInputBtn = new JButton("�� - �Է�");
	JButton custDeleteBtn = new JButton("�� - ����");
	JButton custModifyBtn = new JButton("�� - ����");
	JButton rShopInputBtn = new JButton("����� - �Է�");
	JButton rShopDeleteBtn = new JButton("����� - ����");
	JButton rShopModifyBtn = new JButton("����� - ����");

	JButton infoDeleteBtn = new JButton("ķ��ī �������� - ����");
	JButton infoModifyBtn = new JButton("ķ��ī �������� - ����");

	JButton returnBtn = new JButton("ķ��ī ��ȯ");

	JButton selectBtn1 = new JButton("�˻� 1");
	JButton selectBtn2 = new JButton("�˻� 2");
	JButton selectBtn3 = new JButton("�˻� 3");
	JButton selectBtn4 = new JButton("�˻� 4");

	// �Ϲ� ����� ��� ��ư //
	JButton btnGotoUser;

	JButton btnSelCar;
	JButton btnLoan;

	static Connection con;
	Statement stmt;
	ResultSet rs;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";

	public campingCarCompany() {

		super("18011564 ����ȭ, 18011600 �̴ٿ�");
		conDB();
		login();
		manager();
		setVisible(true);
		setBounds(200, 200, 600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

	class newWindow extends JFrame { // ������ ���� â

		newWindow() {
			setTitle("������ ����");
			setLayout(new BorderLayout(10, 10));

			mBtnPanel = new JPanel();
			mBtnPanel.setLayout(new GridLayout(5, 1, 10, 10));

			mBtnPanel.add(initPanel);
			mBtnPanel.add(carPanel);
			mBtnPanel.add(returnPanel);
			mBtnPanel.add(repairPanel);
			mBtnPanel.add(selectPanel);

			mIOPanel = new JPanel();
			mIOPanel.setPreferredSize(new Dimension(960, 960));

			mIOPanel.add(mOutputSPane);

			add(mBtnPanel, BorderLayout.WEST);
			add(mIOPanel, BorderLayout.EAST);

			setBounds(20, 20, 1700, 1000);
			setResizable(false);
			setVisible(true);
		}

	}

	class newWindow2 extends JFrame { // �Ϲݻ���� ���� â

		JTextField txtCar;
		JTextField txtPNumber;
		JTextField txtCost;

		JTextField txtUniRenNum;
		JTextField txtStaDateRen;
		JTextField txtPeriodRen;
		JTextField txtCharge;
		JTextField txtPayDeadline;
		JTextField txtOtherCha;
		JTextField txtOtherChaInfo;
		JTextField txtCarId;
		JTextField txtLicNum;
		JTextField txtRenCompId;

		JTextArea txtOut;
		ResultSet rs;

		newWindow2() {

			setTitle("�Ϲݻ���� ����");
			setBounds(200, 200, 1500, 700);
			setResizable(false);
			setVisible(true);

			GridLayout g1 = new GridLayout(1, 1); // ������ ���̾ƿ� ����
			GridLayout g2 = new GridLayout(2, 1); // ���� �г� ���̾ƿ� ����

			txtOut = new JTextArea();

			JPanel pnInput;
			JScrollPane pnOutPut = new JScrollPane(txtOut);

			pnInput = new JPanel();

			setLayout(g1);
			add(pnInput);
			add(pnOutPut);

			txtOut.setText("");
			txtOut.setText("��� : ------------------------------------------\n\n\n\n\n\n\n");
			txtOut.setEditable(false);

			JPanel pnIn1 = new JPanel();
			JPanel pnIn2 = new JPanel();

			pnInput.setLayout(g2);

			pnInput.add(pnIn1);
			pnInput.add(pnIn2);

			txtCar = new JTextField(20);
			txtPNumber = new JTextField(20);
			txtCost = new JTextField(20);

			JButton btnCarSel = new JButton("�˻�");
			JButton btnPNumberSel = new JButton("�˻�");
			JButton btnCostSel = new JButton("�˻�");

			GridLayout g3 = new GridLayout(4, 3, 30, 30);

			pnIn1.setLayout(g3);

			pnIn1.add(new JLabel("<�뿩������ ���� �˻�>"));
			pnIn1.add(new JLabel(""));
			pnIn1.add(new JLabel(""));

			pnIn1.add(new JLabel("�� �̸� : "));
			pnIn1.add(txtCar);
			pnIn1.add(btnCarSel);

			btnCarSel.addActionListener(new ActionListenerCarSel());
			btnPNumberSel.addActionListener(new ActionListenerPNumSel());
			btnCostSel.addActionListener(new ActionListenerCostSel());

			pnIn1.add(new JLabel("�ִ� �����ο��� : "));
			pnIn1.add(txtPNumber);
			pnIn1.add(btnPNumberSel);

			pnIn1.add(new JLabel("�� �뿩 ���(�Է��� ���Ϸ� ǥ��) : "));
			pnIn1.add(txtCost);
			pnIn1.add(btnCostSel);

			txtUniRenNum = new JTextField(20);
			txtStaDateRen = new JTextField(20);
			txtPeriodRen = new JTextField(20);
			txtCharge = new JTextField(20);
			txtPayDeadline = new JTextField(20);
			txtOtherCha = new JTextField(20);
			txtOtherChaInfo = new JTextField(20);
			txtCarId = new JTextField(20);
			txtLicNum = new JTextField(20);
			txtRenCompId = new JTextField(20);

			JButton btnApp = new JButton("����");

			GridLayout g4 = new GridLayout(11, 2, 5, 5);
			pnIn2.setLayout(g4);

			pnIn2.add(new JLabel("<���� �뿩 ��û��>"));
			pnIn2.add(new JLabel(""));

			pnIn2.add(new JLabel("�����뿩��ȣ : "));
			pnIn2.add(txtUniRenNum);

			pnIn2.add(new JLabel("�뿩������ : "));
			pnIn2.add(txtStaDateRen);

			pnIn2.add(new JLabel("�뿩�Ⱓ : "));
			pnIn2.add(txtPeriodRen);

			pnIn2.add(new JLabel("û����� : "));
			pnIn2.add(txtCharge);

			pnIn2.add(new JLabel("���Ա��� : "));
			pnIn2.add(txtPayDeadline);

			pnIn2.add(new JLabel("��Ÿû������ : "));
			pnIn2.add(txtOtherCha);

			pnIn2.add(new JLabel("��Ÿû��������� : "));
			pnIn2.add(txtOtherChaInfo);

			pnIn2.add(new JLabel("ķ��ī���ID : "));
			pnIn2.add(txtCarId);

			pnIn2.add(new JLabel("������������ȣ : "));
			pnIn2.add(txtLicNum);

			pnIn2.add(new JLabel(""));
			pnIn2.add(btnApp);

			btnApp.addActionListener(new ActionListenerApp());

		}

		// �Ϲ� ����� ���� ��� 1 - ķ��ī �˻� Action Listener //
		public class ActionListenerCarSel implements ActionListener { // ���̸� ã��

			public void actionPerformed(ActionEvent e) {

				try {

					String caSel = txtCar.getText();

					String sql1 = "select * from camping_car where car_name = ? AND lendable = 1;";

					pstmt = con.prepareStatement(sql1);
					pstmt.setString(1, caSel);

					txtOut.setText("");
					txtOut.setText("��ID" + "\t" + "���̸�" + "\t" + "����ȣ" + "\t" + "�����ο���" + "\t" + "����ȸ��" + "\t" + "��������"
							+ "\t" + "��������Ÿ�" + "\t" + "�뿩���" + "\t" + "�뿩��¥" + "\t" + "�뿩ȸ��" + "\t" + "�뿩���ɿ���" + "\n");

					rs = pstmt.executeQuery();

					while (rs.next()) {
						String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4)
								+ "\t" + rs.getString(5) + "\t" + rs.getDate(6) + "\t" + rs.getInt(7) + "\t"
								+ rs.getInt(8) + "\t" + rs.getDate(9) + "\t" + rs.getInt(10) + "\t" + rs.getInt(11)
								+ "\n";
						txtOut.append(str);
					}

				} catch (Exception e2) {
					System.out.println("���� ���� : ");
					System.out.println(e2.getMessage());
				}

			}

		}

		public class ActionListenerPNumSel implements ActionListener { // ���� �ο��� ã��

			public void actionPerformed(ActionEvent e) {

				try {

					String PNumSel = txtPNumber.getText();

					String sql1 = "select * from camping_car where number_of_passengers = ? AND lendable = 1;";

					pstmt = con.prepareStatement(sql1);
					pstmt.setString(1, PNumSel);

					txtOut.setText("");
					txtOut.setText("��ID" + "\t" + "���̸�" + "\t" + "����ȣ" + "\t" + "�����ο���" + "\t" + "����ȸ��" + "\t" + "��������"
							+ "\t" + "��������Ÿ�" + "\t" + "�뿩���" + "\t" + "�뿩��¥" + "\t" + "�뿩ȸ��" + "\t" + "�뿩���ɿ���" + "\n");

					rs = pstmt.executeQuery();

					while (rs.next()) {
						String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4)
								+ "\t" + rs.getString(5) + "\t" + rs.getDate(6) + "\t" + rs.getInt(7) + "\t"
								+ rs.getInt(8) + "\t" + rs.getDate(9) + "\t" + rs.getInt(10) + "\t" + rs.getInt(11)
								+ "\n";
						txtOut.append(str);
					}

				} catch (Exception e3) {
					System.out.println("���� ���� : ");
					System.out.println(e3.getMessage());
				}

			}

		}

		public class ActionListenerCostSel implements ActionListener { // �뿩��� ã��

			public void actionPerformed(ActionEvent e) {

				try {

					String CstSel = txtCost.getText();

					String sql1 = "select * from camping_car where rental_cost <= ? AND lendable = 1;";

					pstmt = con.prepareStatement(sql1);
					pstmt.setString(1, CstSel);

					txtOut.setText("");
					txtOut.setText("��ID" + "\t" + "���̸�" + "\t" + "����ȣ" + "\t" + "�����ο���" + "\t" + "����ȸ��" + "\t" + "��������"
							+ "\t" + "��������Ÿ�" + "\t" + "�뿩���" + "\t" + "�뿩��¥" + "\t" + "�뿩ȸ��" + "\t" + "�뿩���ɿ���" + "\n");

					rs = pstmt.executeQuery();

					while (rs.next()) {
						String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4)
								+ "\t" + rs.getString(5) + "\t" + rs.getDate(6) + "\t" + rs.getInt(7) + "\t"
								+ rs.getInt(8) + "\t" + rs.getDate(9) + "\t" + rs.getInt(10) + "\t" + rs.getInt(11)
								+ "\n";
						txtOut.append(str);
					}

				} catch (Exception e4) {
					System.out.println("���� ���� : ");
					System.out.println(e4.getMessage());
				}

			}

		}

		// �Ϲ� ����� ���� ��� 2 - ķ��ī ���� �� �뿩 Action Listener //
		public class ActionListenerApp implements ActionListener { // �뿩 ��û�� ����

			public void actionPerformed(ActionEvent e) {

				try {

					String unir = txtUniRenNum.getText();
					String sdr = txtStaDateRen.getText();
					String pdr = txtPeriodRen.getText();
					String charg = txtCharge.getText();
					String payd = txtPayDeadline.getText();
					String otch = txtOtherCha.getText();
					String otchif = txtOtherChaInfo.getText();
					String carid = txtCarId.getText();
					String linu = txtLicNum.getText();

					int tmp = -1;
					int tmpComp = -1;

					String sqlCheck = "select lendable, camping_car_rental_company_company_ID from camping_car where car_ID = ?;";

					pstmt2 = con.prepareStatement(sqlCheck);
					pstmt2.setString(1, carid);
					rs = pstmt2.executeQuery();

					while (rs.next()) {
						tmp = rs.getInt(1);
						tmpComp = rs.getInt(2);
					}

					if (tmp == 1) {

						// �뿩����
						String sql1 = "INSERT INTO car_loan_application VALUES(?,?,?,?,?,?,?,?,?,?);";

						pstmt = con.prepareStatement(sql1);

						pstmt.setString(1, unir);
						pstmt.setString(2, sdr);
						pstmt.setString(3, pdr);
						pstmt.setString(4, charg);
						pstmt.setString(5, payd);
						pstmt.setString(6, otch);
						pstmt.setString(7, otchif);
						pstmt.setString(8, carid);
						pstmt.setString(9, linu);
						pstmt.setString(10, String.valueOf(tmpComp));

						pstmt.executeUpdate();

						System.out.println("�뿩 ��û ����");
						System.out.println("");

						txtOut.setText("");
						txtOut.setText("�����뿩��ȣ" + "\t" + "�뿩������" + "\t" + "�뿩�Ⱓ" + "\t" + "�뿩���" + "\t" + "���α���" + "\t"
								+ "��Ÿ���" + "\t" + "��Ÿ�������" + "\t" + "��ID" + "\t" + "���������ȣ" + "\t" + "ȸ��ID" + "\n");

						String sql2 = "select * from car_loan_application;";
						rs = stmt.executeQuery(sql2);

						while (rs.next()) {
							String str = rs.getInt(1) + "\t" + rs.getDate(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4)
									+ " \t " + rs.getDate(5) + "\t" + rs.getInt(6) + "\t" + rs.getString(7) + "\t"
									+ rs.getInt(8) + "\t" + rs.getInt(9) + "\t" + rs.getInt(10) + "\n";
							txtOut.append(str);
						}

						// �뿩 ����(1) -> �뿩�Ұ���(0)
						String sql3 = "UPDATE camping_car SET lendable = 0 WHERE car_ID = ?;";

						pstmt = con.prepareStatement(sql3);
						pstmt.setString(1, carid);

						pstmt.executeUpdate();

					} else if (tmp == 0) {
						System.out.println("�뿩 ���� ����: �̹� �뿩���� �� �Դϴ�.");
					} else if (tmp == -1) {
						System.out.println("�뿩 ���� ���� : ã�� �� ���� car_ID ");
					}

				} catch (Exception e5) {
					System.out.println("�뿩 ���� ���� : ");
					System.out.println(e5.getMessage());
				}

			}

		}

	}

	public void login() { // �α��� â(ù ȭ��)

		loginPanel = new JPanel();
		loginPanel.setLayout(new GridLayout(1, 2, 10, 10));

		btnGotoManager = new JButton("������ �α���");
		btnGotoUser = new JButton("�Ϲݻ���� �α���");

		loginPanel.add(btnGotoManager);
		loginPanel.add(btnGotoUser);

		btnGotoManager.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new newWindow();
			}

		});
		btnGotoUser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new newWindow2();
			}

		});

		add("Center", loginPanel);

	}

	public void manager() { // ������ ���� â

		// initPanel //
		JButton DBInitBtn = new JButton("DB �ʱ�ȭ");
		JButton outputInitBtn = new JButton("��� �ʱ�ȭ");

		initPanel = new JPanel();
		initPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		DBInitBtn.setPreferredSize(new Dimension(350, 175));
		outputInitBtn.setPreferredSize(new Dimension(350, 175));

		initPanel.add(DBInitBtn);
		initPanel.add(outputInitBtn);

		DBInitBtn.addActionListener(new ActionListenerDBInit());
		outputInitBtn.addActionListener(new ActionListenerOutputInit());

		// carPanel //
		carPanel = new JPanel();
		carPanel.setLayout(new GridLayout(4, 3, 10, 10));

		carPanel.add(compInputBtn);
		carPanel.add(compDeleteBtn);
		carPanel.add(compModifyBtn);
		carPanel.add(carInputBtn);
		carPanel.add(carDeleteBtn);
		carPanel.add(carModifyBtn);
		carPanel.add(custInputBtn);
		carPanel.add(custDeleteBtn);
		carPanel.add(custModifyBtn);
		carPanel.add(rShopInputBtn);
		carPanel.add(rShopDeleteBtn);
		carPanel.add(rShopModifyBtn);

		compInputBtn.addActionListener(new ActionListenerCompInput());
		compDeleteBtn.addActionListener(new ActionListenerCompDelete());
		compModifyBtn.addActionListener(new ActionListenerCompModify());
		carInputBtn.addActionListener(new ActionListenerCarInput());
		carDeleteBtn.addActionListener(new ActionListenerCarDelete());
		carModifyBtn.addActionListener(new ActionListenerCarModify());
		custInputBtn.addActionListener(new ActionListenerCustInput());
		custDeleteBtn.addActionListener(new ActionListenerCustDelete());
		custModifyBtn.addActionListener(new ActionListenerCustModify());
		rShopInputBtn.addActionListener(new ActionListenerShopInput());
		rShopDeleteBtn.addActionListener(new ActionListenerShopDelete());
		rShopModifyBtn.addActionListener(new ActionListenerShopModify());

		// returnPanel //
		returnPanel = new JPanel();
		returnPanel.setLayout(new BorderLayout());
		returnPanel.add(returnBtn, BorderLayout.CENTER);
		returnBtn.addActionListener(new ActionListenerReturnCar());// �ݳ� ��ư

		// repairPanel //
		repairPanel = new JPanel();
		repairPanel.setLayout(new GridLayout(1, 2, 10, 10));

		repairPanel.add(infoDeleteBtn);
		repairPanel.add(infoModifyBtn);

		infoDeleteBtn.addActionListener(new ActionListenerMInfoDelete());
		infoModifyBtn.addActionListener(new ActionListenerMInfoModify());

		// selectPanel //
		selectPanel = new JPanel();
		selectPanel.setLayout(new GridLayout(1, 4, 10, 10));

		selectPanel.add(selectBtn1);
		selectPanel.add(selectBtn2);
		selectPanel.add(selectBtn3);
		selectPanel.add(selectBtn4);

		selectBtn1.addActionListener(new ActionListenerSelBtn1());////
		selectBtn2.addActionListener(new ActionListenerSelBtn2());
		selectBtn3.addActionListener(new ActionListenerSelBtn3());
		selectBtn4.addActionListener(new ActionListenerSelBtn4());

		// mOutputPanel //
		mOutputTArea = new JTextArea();
		mOutputTArea.setText(
				" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ��� - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
		mOutputTArea.setEditable(false);

		mOutputSPane = new JScrollPane(mOutputTArea);
		mOutputSPane.setPreferredSize(new Dimension(960, 960));

	}

	// ������ ���� ��� 1 - �����ͺ��̽� �ʱ�ȭ //
	private class ActionListenerDBInit implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			int rowcount;

			try {
				System.out.println("");
				System.out.println("DB �ʱ�ȭ ����");

				stmt = con.createStatement();
				String query;

				// drop
				System.out.println("");
				System.out.println("TABLE DROP �õ�...");

				query = "DROP TABLE IF EXISTS car_inspection_details";
				stmt.executeUpdate(query);
				System.out.println("car_inspection_details ���̺� DROP �Ϸ�");

				query = "DROP TABLE IF EXISTS car_loan_application";
				stmt.executeUpdate(query);
				System.out.println("car_loan_application ���̺� DROP �Ϸ�");

				query = "DROP TABLE IF EXISTS maintenance_information";
				stmt.executeUpdate(query);
				System.out.println("maintenance_information ���̺� DROP �Ϸ�");

				query = "DROP TABLE IF EXISTS camping_car";
				stmt.executeUpdate(query);
				System.out.println("camping_car ���̺� DROP �Ϸ�");

				query = "DROP TABLE IF EXISTS camping_car_rental_company";
				stmt.executeUpdate(query);
				System.out.println("camping_car_rental_company ���̺� DROP �Ϸ�");

				query = "DROP TABLE IF EXISTS customer";
				stmt.executeUpdate(query);
				System.out.println("customer ���̺� DROP �Ϸ�");

				query = "DROP TABLE IF EXISTS car_repair_shop";
				stmt.executeUpdate(query);
				System.out.println("car_repair_shop ���̺� DROP �Ϸ�");

				System.out.println("TABLE DROP ����");

				// create
				System.out.println("");
				System.out.println("TABLE CREATE �õ�...");

				query = "CREATE TABLE IF NOT EXISTS `camping_car_rental_company` (\r\n"
						+ "  `company_ID` INT NOT NULL,\r\n" + "  `company_name` VARCHAR(45) NULL,\r\n"
						+ "  `company_address` VARCHAR(45) NULL,\r\n" + "  `company_phone` VARCHAR(45) NULL,\r\n"
						+ "  `representative_name` VARCHAR(45) NULL,\r\n"
						+ "  `representative_email` VARCHAR(45) NULL,\r\n" + "  PRIMARY KEY (`company_ID`))\r\n"
						+ "ENGINE = InnoDB;\r\n";
				stmt.executeUpdate(query);
				System.out.println("camping_car_rental_company ���̺� CREATE �Ϸ�");

				query = "CREATE TABLE IF NOT EXISTS `camping_car` (\r\n" + "  `car_ID` INT NOT NULL,\r\n"
						+ "  `car_name` VARCHAR(45) NULL,\r\n" + "  `car_number` INT NULL,\r\n"
						+ "  `number_of_passengers` INT NULL,\r\n" + "  `manufacturing_company` VARCHAR(45) NULL,\r\n"
						+ "  `manufacturing_year` DATE NULL,\r\n" + "  `cumulative_driving_distance` INT NULL,\r\n"
						+ "  `rental_cost` INT NULL,\r\n" + "  `resigter_date` DATE NULL,\r\n"
						+ "  `camping_car_rental_company_company_ID` INT NOT NULL,\r\n" + "  `lendable` INT NULL,\r\n"
						+ "  PRIMARY KEY (`car_ID`),\r\n"
						+ "  INDEX `fk_camping car_camping car rental company_idx` (`camping_car_rental_company_company_ID` ASC) VISIBLE,\r\n"
						+ "  CONSTRAINT `fk_camping car_camping car rental company`\r\n"
						+ "    FOREIGN KEY (`camping_car_rental_company_company_ID`)\r\n"
						+ "    REFERENCES `camping_car_rental_company` (`company_ID`)\r\n" + "    ON DELETE CASCADE\r\n"
						+ "    ON UPDATE NO ACTION)\r\n" + "ENGINE = InnoDB;";
				stmt.executeUpdate(query);
				System.out.println("camping_car ���̺� CREATE �Ϸ�");

				query = "CREATE TABLE IF NOT EXISTS `customer` (\r\n" + "  `license_number` INT NOT NULL,\r\n"
						+ "  `customer_name` VARCHAR(45) NULL,\r\n" + "  `customer_address` VARCHAR(45) NULL,\r\n"
						+ "  `customer_phone` VARCHAR(45) NULL,\r\n" + "  `customer_email` VARCHAR(45) NULL,\r\n"
						+ "  PRIMARY KEY (`license_number`))\r\n" + "ENGINE = InnoDB;\r\n";
				stmt.executeUpdate(query);
				System.out.println("customer ���̺� CREATE �Ϸ�");

				query = "CREATE TABLE IF NOT EXISTS `car_loan_application` (\r\n"
						+ "  `unique_rental_number` INT NOT NULL,\r\n" + "  `start_date_of_rental` DATE NULL,\r\n"
						+ "  `period_of_rental` INT NULL,\r\n" + "  `charge` INT NULL,\r\n"
						+ "  `payment_deadline` DATE NULL,\r\n" + "  `other_charge` INT NULL,\r\n"
						+ "  `other_charge_info` VARCHAR(45) NULL,\r\n" + "  `camping_car_car_ID` INT NOT NULL,\r\n"
						+ "  `customer_license_number` INT NOT NULL,\r\n"
						+ "  `camping_car_rental_company_company_ID` INT NOT NULL,\r\n"
						+ "  PRIMARY KEY (`unique_rental_number`),\r\n"
						+ "  INDEX `fk_Car Loan Application_camping car1_idx` (`camping_car_car_ID` ASC) VISIBLE,\r\n"
						+ "  INDEX `fk_Car Loan Application_customer1_idx` (`customer_license_number` ASC) VISIBLE,\r\n"
						+ "  INDEX `fk_car_loan_application_camping_car_rental_company1_idx` (`camping_car_rental_company_company_ID` ASC) VISIBLE,\r\n"
						+ "  CONSTRAINT `fk_Car Loan Application_camping car1`\r\n"
						+ "    FOREIGN KEY (`camping_car_car_ID`)\r\n" + "    REFERENCES `camping_car` (`car_ID`)\r\n"
						+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE NO ACTION,\r\n"
						+ "  CONSTRAINT `fk_Car Loan Application_customer1`\r\n"
						+ "    FOREIGN KEY (`customer_license_number`)\r\n"
						+ "    REFERENCES `customer` (`license_number`)\r\n" + "    ON DELETE CASCADE\r\n"
						+ "    ON UPDATE NO ACTION,\r\n"
						+ "  CONSTRAINT `fk_car_loan_application_camping_car_rental_company1`\r\n"
						+ "    FOREIGN KEY (`camping_car_rental_company_company_ID`)\r\n"
						+ "    REFERENCES `camping_car_rental_company` (`company_ID`)\r\n" + "    ON DELETE CASCADE\r\n"
						+ "    ON UPDATE NO ACTION)\r\n" + "ENGINE = InnoDB;\r\n";
				stmt.executeUpdate(query);
				System.out.println("car_loan_application ���̺� CREATE �Ϸ�");

				query = "CREATE TABLE IF NOT EXISTS `car_inspection_details` (\r\n"
						+ "  `front_part_info` VARCHAR(45) NULL,\r\n" + "  `left_part_info` VARCHAR(45) NULL,\r\n"
						+ "  `right_part_info` VARCHAR(45) NULL,\r\n" + "  `backside_info` VARCHAR(45) NULL,\r\n"
						+ "  `repair_required` INT NULL,\r\n"
						+ "  `car_loan_application_unique_rental_number` INT NOT NULL,\r\n"
						+ "  `camping_car_car_ID` INT NOT NULL,\r\n"
						+ "  PRIMARY KEY (`car_loan_application_unique_rental_number`),\r\n"
						+ "  INDEX `fk_car_inspection_details_car_loan_application1_idx` (`car_loan_application_unique_rental_number` ASC) VISIBLE,\r\n"
						+ "  INDEX `fk_car_inspection_details_camping_car1_idx` (`camping_car_car_ID` ASC) VISIBLE,\r\n"
						+ "  CONSTRAINT `fk_car_inspection_details_car_loan_application1`\r\n"
						+ "    FOREIGN KEY (`car_loan_application_unique_rental_number`)\r\n"
						+ "    REFERENCES `car_loan_application` (`unique_rental_number`)\r\n"
						+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE NO ACTION,\r\n"
						+ "  CONSTRAINT `fk_car_inspection_details_camping_car1`\r\n"
						+ "    FOREIGN KEY (`camping_car_car_ID`)\r\n" + "    REFERENCES `camping_car` (`car_ID`)\r\n"
						+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE NO ACTION)\r\n" + "ENGINE = InnoDB;\r\n";
				stmt.executeUpdate(query);
				System.out.println("car_inspection_details ���̺� CREATE �Ϸ�");

				query = "CREATE TABLE IF NOT EXISTS `car_repair_shop` (\r\n"
						+ "  `car_repair_shop_ID` INT NOT NULL,\r\n" + "  `repair_shop_name` VARCHAR(45) NULL,\r\n"
						+ "  `repair_shop_address` VARCHAR(45) NULL,\r\n"
						+ "  `repair_shop_phone` VARCHAR(45) NULL,\r\n"
						+ "  `representative_name` VARCHAR(45) NULL,\r\n"
						+ "  `representative_email` VARCHAR(45) NULL,\r\n" + "  PRIMARY KEY (`car_repair_shop_ID`))\r\n"
						+ "ENGINE = InnoDB;\r\n";
				stmt.executeUpdate(query);
				System.out.println("car_repair_shop ���̺� CREATE �Ϸ�");

				query = "CREATE TABLE IF NOT EXISTS `maintenance_information` (\r\n"
						+ "  `uniqe_maintenance_number` INT NOT NULL,\r\n"
						+ "  `maintenance_history` VARCHAR(45) NULL,\r\n" + "  `repair_date` DATE NULL,\r\n"
						+ "  `repair_charge` INT NULL,\r\n" + "  `payment_deadline` DATE NULL,\r\n"
						+ "  `other_maintenance_info` VARCHAR(45) NULL,\r\n"
						+ "  `car_repair_shop_car_repair_shop_ID` INT NOT NULL,\r\n"
						+ "  `camping_car_car_ID` INT NOT NULL,\r\n"
						+ "  `camping_car_rental_company_company_ID` INT NOT NULL,\r\n"
						+ "  `customer_license_number` INT NOT NULL,\r\n"
						+ "  PRIMARY KEY (`uniqe_maintenance_number`),\r\n"
						+ "  INDEX `fk_maintenance information_car repair shop1_idx` (`car_repair_shop_car_repair_shop_ID` ASC) VISIBLE,\r\n"
						+ "  INDEX `fk_maintenance_information_camping_car1_idx` (`camping_car_car_ID` ASC) VISIBLE,\r\n"
						+ "  INDEX `fk_maintenance_information_camping_car_rental_company1_idx` (`camping_car_rental_company_company_ID` ASC) VISIBLE,\r\n"
						+ "  INDEX `fk_maintenance_information_customer1_idx` (`customer_license_number` ASC) VISIBLE,\r\n"
						+ "  CONSTRAINT `fk_maintenance information_car repair shop1`\r\n"
						+ "    FOREIGN KEY (`car_repair_shop_car_repair_shop_ID`)\r\n"
						+ "    REFERENCES `car_repair_shop` (`car_repair_shop_ID`)\r\n" + "    ON DELETE CASCADE\r\n"
						+ "    ON UPDATE NO ACTION,\r\n" + "  CONSTRAINT `fk_maintenance_information_camping_car1`\r\n"
						+ "    FOREIGN KEY (`camping_car_car_ID`)\r\n" + "    REFERENCES `camping_car` (`car_ID`)\r\n"
						+ "    ON DELETE CASCADE\r\n" + "    ON UPDATE NO ACTION,\r\n"
						+ "  CONSTRAINT `fk_maintenance_information_camping_car_rental_company1`\r\n"
						+ "    FOREIGN KEY (`camping_car_rental_company_company_ID`)\r\n"
						+ "    REFERENCES `camping_car_rental_company` (`company_ID`)\r\n" + "    ON DELETE CASCADE\r\n"
						+ "    ON UPDATE NO ACTION,\r\n" + "  CONSTRAINT `fk_maintenance_information_customer1`\r\n"
						+ "    FOREIGN KEY (`customer_license_number`)\r\n"
						+ "    REFERENCES `customer` (`license_number`)\r\n" + "    ON DELETE CASCADE\r\n"
						+ "    ON UPDATE NO ACTION)\r\n" + "ENGINE = InnoDB;\r\n";
				stmt.executeUpdate(query);
				System.out.println("maintenance_information ���̺� CREATE �Ϸ�");

				System.out.println("TABLE CREATE ����");
				System.out.println("");

				// insert - camping_car_rental_company
				System.out.println("INSERT INTO TABLE �õ�...");
				System.out.println("");
				rowcount = 0;

				query = "INSERT INTO camping_car_rental_company VALUES(1, 'A ȸ��', '�� ���� �� ����', '010-0000-0001', '�迡��', 'Acompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(2, 'B ȸ��', '�� ���� �� ����', '010-0000-0002', '����', 'Bcompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(3, 'C ȸ��', '�� ���� �� ����', '010-0000-0003', '�达��', 'Ccompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(4, 'D ȸ��', '�� ���� �� ����', '010-0000-0004', '����', 'Dcompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(5, 'E ȸ��', '�� ���� �� ����', '010-0000-0005', '������', 'Ecompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(6, 'F ȸ��', '�� ���� �� ����', '010-0000-0006', '�迡��', 'Fcompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(7, 'G ȸ��', '�� ���� �� ����', '010-0000-0007', '������', 'Gcompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(8, 'H ȸ��', '�� ���� �� ����', '010-0000-0008', '�迡��ġ', 'Hcompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(9, 'I ȸ��', '�� ���� �� ����', '010-0000-0009', '�����', 'Icompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(10, 'J ȸ��', '�� ���� �� ����', '010-0000-0010', '������', 'Jcompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(11, 'K ȸ��', 'ī ���� ī ����', '010-0000-0011', '������', 'Kcompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(12, 'L ȸ��', 'Ÿ ���� Ÿ ����', '010-0000-0012', '�迤��', 'Lcompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(13, 'M ȸ��', '�� ���� �� ����', '010-0000-0013', '�迥��', 'Mcompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(14, 'N ȸ��', '�� ���� �� ����', '010-0000-0014', '�迣��', 'Ncompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(15, 'O ȸ��', '�� ���� �� ����', '010-0000-0015', '�����', 'Ocompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);

				System.out.println("camping_car_rental_company ���̺� " + rowcount + "���� Ʃ�� �߰� �Ϸ�");

				// insert - camping_car
				rowcount = 0;

				query = "INSERT INTO camping_car VALUES(10001, '����A', 1111, 5, '�޸�����������', STR_TO_DATE('2018-01-01','%Y-%m-%d'), 1045, 300000, STR_TO_DATE('2019-01-01','%Y-%m-%d'), 1,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10002, '����B', 2222, 6, '�޸�����������', STR_TO_DATE('2017-12-31','%Y-%m-%d'), 2000, 500000, STR_TO_DATE('2019-01-01','%Y-%m-%d'), 2,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10003, '����C', 3333, 5, '�޸�����������', STR_TO_DATE('2016-01-01','%Y-%m-%d'), 4598, 200000, STR_TO_DATE('2017-01-01','%Y-%m-%d'), 2,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10004, '����D', 4444, 2, '�޸�����������', STR_TO_DATE('2018-02-28','%Y-%m-%d'), 1264, 600000, STR_TO_DATE('2019-01-01','%Y-%m-%d'), 3,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10005, '����F', 5555, 5, '�޸�����������', STR_TO_DATE('2019-01-01','%Y-%m-%d'), 1264, 450000, STR_TO_DATE('2019-12-31','%Y-%m-%d'), 3,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10006, '����G', 6666, 4, '�޸�����������', STR_TO_DATE('2017-01-01','%Y-%m-%d'), 3566, 350000, STR_TO_DATE('2017-02-02','%Y-%m-%d'), 1,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10007, '����A', 7777, 5, '�޸�����������', STR_TO_DATE('2018-01-01','%Y-%m-%d'), 2570, 300000, STR_TO_DATE('2018-03-01','%Y-%m-%d'), 14,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10008, '����B', 8888, 6, '�޸�����������', STR_TO_DATE('2019-01-01','%Y-%m-%d'), 1000, 500000, STR_TO_DATE('2019-03-01','%Y-%m-%d'), 13,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10009, '����F', 9999, 5, '�޸�����������', STR_TO_DATE('2014-01-01','%Y-%m-%d'), 15642, 450000, STR_TO_DATE('2015-03-01','%Y-%m-%d'), 12,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10010, '����A', 1010, 5, '�޸�����������', STR_TO_DATE('2019-01-01','%Y-%m-%d'), 15642, 450000, STR_TO_DATE('2020-01-01','%Y-%m-%d'), 11,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10011, '����D', 11111, 2, '�޸�����������', STR_TO_DATE('2015-01-01','%Y-%m-%d'), 6554, 250000, STR_TO_DATE('2016-01-01','%Y-%m-%d'), 9,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10012, '����C', 11112, 5, '�޸�����������', STR_TO_DATE('2019-01-01','%Y-%m-%d'), 3545, 300000, STR_TO_DATE('2020-01-01','%Y-%m-%d'), 5,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10013, '����A', 11113, 5, '�޸�����������', STR_TO_DATE('2013-01-01','%Y-%m-%d'), 25622, 200000, STR_TO_DATE('2014-01-01','%Y-%m-%d'), 7,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10014, '����G', 11114, 4, '�޸�����������', STR_TO_DATE('2018-01-01','%Y-%m-%d'), 4000, 300000, STR_TO_DATE('2018-12-31','%Y-%m-%d'), 6,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10015, '����F', 11115, 5, '�޸�����������', STR_TO_DATE('2019-01-01','%Y-%m-%d'), 5555, 550000, STR_TO_DATE('2019-12-31','%Y-%m-%d'), 4,1)";
				rowcount += stmt.executeUpdate(query);

				System.out.println("camping_car ���̺� " + rowcount + "���� Ʃ�� �߰� �Ϸ�");

				// insert - customer
				rowcount = 0;

				query = "INSERT INTO customer VALUES(1234561, 'A��', '�� ���� �� ����', '010-0001-0001', 'Acustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(1234562, 'B��', '�� ���� �� ����', '010-0001-0002', 'Bcustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(1234563, 'C��', '�� ���� �� ����', '010-0001-0003', 'Ccustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(1234564, 'D��', '�� ���� �� ����', '010-0001-0004', 'Dcustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(1234565, 'E��', '�� ���� �� ����', '010-0001-0005', 'Ecustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(1234566, 'F��', '�� ���� �� ����', '010-0001-0006', 'Fcustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(1234567, 'G��', '�� ���� �� ����', '010-0001-0007', 'Gcustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(1234568, 'H��', '�� ���� �� ����', '010-0001-0008', 'Hcustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(1234569, 'I��', '�� ���� �� ����', '010-0001-0009', 'Icustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(2345670, 'J��', '�� ���� �� ����', '010-0001-0010', 'Jcustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(2345671, 'K��', 'ī ���� ī ����', '010-0001-0011', 'Kcustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(2345672, 'L��', 'Ÿ ���� Ÿ ����', '010-0001-0012', 'Lcustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(2345673, 'M��', '�� ���� �� ����', '010-0001-0013', 'Mcustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(2345674, 'N��', '�� ���� �� ����', '010-0001-0014', 'Ncustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(2345675, 'O��', '�� ���� �� ����', '010-0001-0015', 'Ocustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);

				System.out.println("customer ���̺� " + rowcount + "���� Ʃ�� �߰� �Ϸ�");

				// insert - car_loan_application
				rowcount = 0;

				query = "INSERT INTO car_loan_application VALUES(1, STR_TO_DATE('2020-05-01','%Y-%m-%d'), 15, 600000, STR_TO_DATE('2020-06-01','%Y-%m-%d'), 150000, '������', 10001, 1234561, 1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(2, STR_TO_DATE('2020-03-01','%Y-%m-%d'), 15, 700000, STR_TO_DATE('2020-03-31','%Y-%m-%d'), 150000, '������', 10002, 1234562, 2)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(3, STR_TO_DATE('2020-04-01','%Y-%m-%d'), 15, 400000, STR_TO_DATE('2020-04-28','%Y-%m-%d'), 150000, '������', 10003, 1234563, 2)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(4, STR_TO_DATE('2020-02-01','%Y-%m-%d'), 15, 800000, STR_TO_DATE('2020-02-28','%Y-%m-%d'), 150000, '������', 10004, 1234564, 3)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(5, STR_TO_DATE('2020-05-01','%Y-%m-%d'), 15, 600000, STR_TO_DATE('2020-05-31','%Y-%m-%d'), 300000, '������+���̿�ī��Ʈ', 10005, 1234565, 3)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(6, STR_TO_DATE('2020-04-01','%Y-%m-%d'), 15, 500000, STR_TO_DATE('2020-04-28','%Y-%m-%d'), 260000, '������+�����̾������ⱸ', 10006, 1234566, 1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(7, STR_TO_DATE('2020-05-13','%Y-%m-%d'), 15, 550000, STR_TO_DATE('2020-06-13','%Y-%m-%d'), 300000, '������+�̺�Ʈ�밡����', 10007, 1234567, 14)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(8, STR_TO_DATE('2020-03-21','%Y-%m-%d'), 15, 900000, STR_TO_DATE('2020-04-21','%Y-%m-%d'), 150000, '������', 10008, 1234568, 13)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(9, STR_TO_DATE('2020-01-11','%Y-%m-%d'), 15, 600000, STR_TO_DATE('2020-02-11','%Y-%m-%d'), 150000, '������', 10009, 1234568, 12)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(10, STR_TO_DATE('2020-03-15','%Y-%m-%d'), 15, 770000, STR_TO_DATE('2020-04-15','%Y-%m-%d'), 400000, '������+�����̾�ħ��', 10010, 2345670, 11)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(11, STR_TO_DATE('2020-01-20','%Y-%m-%d'), 15, 400000, STR_TO_DATE('2020-02-20','%Y-%m-%d'), 400000, '������+�����̾�ħ��', 10011, 2345671, 9)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(12, STR_TO_DATE('2020-05-06','%Y-%m-%d'), 15, 500000, STR_TO_DATE('2020-06-06','%Y-%m-%d'), 150000, '������', 10012, 2345672, 5)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(13, STR_TO_DATE('2020-04-03','%Y-%m-%d'), 15, 400000, STR_TO_DATE('2020-05-03','%Y-%m-%d'), 150000, '������', 10013, 2345673, 7)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(14, STR_TO_DATE('2020-01-05','%Y-%m-%d'), 15, 600000, STR_TO_DATE('2020-02-05','%Y-%m-%d'), 500000, '������+������', 10014, 2345674, 6)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(15, STR_TO_DATE('2020-03-04','%Y-%m-%d'), 15, 900000, STR_TO_DATE('2020-04-04','%Y-%m-%d'), 650000, '������+�ȸ�����', 10015, 2345675, 4)";
				rowcount += stmt.executeUpdate(query);

				System.out.println("car_loan_application ���̺� " + rowcount + "���� Ʃ�� �߰� �Ϸ�");

				// insert- car_inspection_details
				rowcount = 0;

				query = "INSERT INTO car_inspection_details VALUES('�̻� ��','�̻� ��', '�̻� ��', '�̻� ��', 0, 1, 10001)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('�̻� ��','���̵�̷� ����', '�̻� ��', '�̻� ��', 1, 2, 10002)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('�̻� ��','�̻� ��', '���̵�̷� �ļ�', '�̻� ��', 1, 3, 10003)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('�̻� ��','�̻� ��', '�̻� ��', '�̻� ��', 0, 4, 10004)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('�̻� ��','�̻� ��', '�̻� ��', '�̻� ��', 0, 5, 10005)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('�̻� ��','�̻� ��', '�̻� ��', '�̻� ��', 0, 6, 10006)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('�̻� ��','�̻� ��', '�̻� ��', '�̻� ��', 0, 7, 10007)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('�̻� ��','�̻� ��', '�̻� ��', '�̻� ��', 0, 8, 10008)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('�̻� ��','�̻� ��', '���� ��ũ', '�̻� ��', 1, 9, 10009)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('�� ����','�̻� ��', '�̻� ��', '�̻� ��', 1, 10, 10010)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('�̻� ��','�̻� ��', '�̻� ��', '�̻� ��', 0, 11, 10011)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('�̻� ��','�̻� ��', '�̻� ��', '��ٸ� �Ѽ�', 1, 12, 10012)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('�̻� ��','�̻� ��', '�̻� ��', '�̻� ��', 0, 13, 10013)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('�̻� ��','�̻� ��', '�̻� ��', '�̻� ��', 0, 14, 10014)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('�̻� ��','�̻� ��', '�̻� ��', '�̻� ��', 0, 15, 10015)";
				rowcount += stmt.executeUpdate(query);

				System.out.println("car_inspection_details ���̺� " + rowcount + "���� Ʃ�� �߰� �Ϸ�");

				// insert - car_repair_shop
				rowcount = 0;

				query = "INSERT INTO car_repair_shop VALUES(1, 'A �����', '�� ���� �� ����', '010-0002-0001', '�̿���', 'Ashop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(2, 'B �����', '�� ���� �� ����', '010-0002-0002', '�̺��', 'Bshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(3, 'C �����', '�� ���� �� ����', '010-0002-0003', '�̾���', 'Cshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(4, 'D �����', '�� ���� �� ����', '010-0002-0004', '�̵��', 'Dshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(5, 'E �����', '�� ���� �� ����', '010-0002-0005', '������', 'Eshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(6, 'F �����', '�� ���� �� ����', '010-0002-0006', '�̿���', 'Fshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(7, 'G �����', '�� ���� �� ����', '010-0002-0007', '������', 'Gshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(8, 'H �����', '�� ���� �� ����', '010-0002-0008', '�̿���ġ', 'Hshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(9, 'I �����', '�� ���� �� ����', '010-0002-0009', '�̾���', 'Ishop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(10, 'J �����', '�� ���� �� ����', '010-0002-0010', '������', 'Jshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(11, 'K �����', 'ī ���� ī ����', '010-0002-0011', '������', 'Kshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(12, 'L �����', 'Ÿ ���� Ÿ ����', '010-0002-0012', '�̿���', 'Lshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(13, 'M �����', '�� ���� �� ����', '010-0002-0013', '�̿���', 'Mshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(14, 'N �����', '�� ���� �� ����', '010-0002-0014', '�̿���', 'Nshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(15, 'O �����', '�� ���� �� ����', '010-0002-0015', '�̿���', 'Oshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);

				System.out.println("car_repair_shop ���̺� " + rowcount + "���� Ʃ�� �߰� �Ϸ�");

				// insert - maintenance_information
				rowcount = 0;

				query = "INSERT INTO maintenance_information VALUES(1, '���̵�̷� ����', STR_TO_DATE('2001-01-01','%Y-%m-%d'), 50000, STR_TO_DATE('2001-01-10','%Y-%m-%d'), '�̻� ��', 15, 10001, 1, 1234561)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(2, '�� ����', STR_TO_DATE('2020-03-16','%Y-%m-%d'), 60000, STR_TO_DATE('2020-04-16','%Y-%m-%d'), '�̻� ��', 14, 10002, 2, 1234562)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(3, '��ٸ� �Ѽ�', STR_TO_DATE('2020-04-16','%Y-%m-%d'), 70000, STR_TO_DATE('2020-05-16','%Y-%m-%d'), '�̻� ��', 13, 10003, 2, 1234563)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(4, '���� ��ũ', STR_TO_DATE('2001-01-01','%Y-%m-%d'), 25000, STR_TO_DATE('2001-01-10','%Y-%m-%d'), '�̻� ��', 12, 10004, 3, 1234564)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(5, 'ȭ���� �ļ�', STR_TO_DATE('2001-01-01','%Y-%m-%d'), 100000, STR_TO_DATE('2001-01-10','%Y-%m-%d'), '�̻� ��', 11, 10005, 3, 1234565)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(6, 'â�� �ļ�', STR_TO_DATE('2001-01-01','%Y-%m-%d'), 80000, STR_TO_DATE('2001-01-10','%Y-%m-%d'), '�̻� ��', 10, 10006, 1, 1234566)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(7, '��ٸ� �Ѽ�', STR_TO_DATE('2001-01-01','%Y-%m-%d'), 100000, STR_TO_DATE('2001-01-10','%Y-%m-%d'), '�̻� ��', 9, 10007, 14, 1234567)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(8, '�� ����', STR_TO_DATE('2001-01-01','%Y-%m-%d'), 150000, STR_TO_DATE('2001-01-10','%Y-%m-%d'), '�̻� ��', 8, 10008, 13, 1234568)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(9, '�� ����', STR_TO_DATE('2020-01-26','%Y-%m-%d'), 90000, STR_TO_DATE('2020-02-26','%Y-%m-%d'), '�̻� ��', 7, 10009, 12, 1234569)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(10, '���̵�̷� �ļ�', STR_TO_DATE('2020-03-30','%Y-%m-%d'), 80000, STR_TO_DATE('2020-04-30','%Y-%m-%d'), '�̻� ��', 6, 10010, 11, 2345670)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(11, 'â�� �ļ�', STR_TO_DATE('2001-01-01','%Y-%m-%d'), 65000, STR_TO_DATE('2001-01-10','%Y-%m-%d'), '�̻� ��', 5, 10011, 9, 2345671)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(12, '��ٸ� �ļ�', STR_TO_DATE('2020-05-21','%Y-%m-%d'), 100000, STR_TO_DATE('2020-06-21','%Y-%m-%d'), '�̻� ��', 4, 10012, 5, 2345672)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(13, 'ȭ���� �ļ�', STR_TO_DATE('2001-01-01','%Y-%m-%d'), 40000, STR_TO_DATE('2001-01-10','%Y-%m-%d'), '�̻� ��', 3, 10013, 7, 2345673)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(14, '���� ��ũ', STR_TO_DATE('2001-01-01','%Y-%m-%d'), 60000, STR_TO_DATE('2001-01-10','%Y-%m-%d'), '�̻� ��', 2, 10014, 6, 2345674)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(15, '�� ����', STR_TO_DATE('2001-01-01','%Y-%m-%d'), 50000, STR_TO_DATE('2001-01-10','%Y-%m-%d'), '�̻� ��', 1, 10015, 4, 2345675)";
				rowcount += stmt.executeUpdate(query);

				System.out.println("maintenance_information ���̺� " + rowcount + "���� Ʃ�� �߰� �Ϸ�");

				System.out.println("INSERT INTO TABLE ����");
				System.out.println("");

				System.out.println("DB �ʱ�ȭ �Ϸ�");
				System.out.println("");

			} catch (Exception e6) {
				System.out.println("DB �ʱ�ȭ ���� : " + e6);
			}

		}

	}

	private class ActionListenerOutputInit implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {
				mOutputTArea.setText(
						" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ��� - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
			} catch (Exception e7) {
				System.out.println("��� �ʱ�ȭ ���� : " + e7);
			}

		}

	}

	// ������ ���� ��� 2 - �뿩ȸ�� �Է� Action Listener //
	private class ActionListenerCompInput implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("�뿩ȸ�� ������ �Է� �õ�...");

				frame = new JFrame("�뿩ȸ�� ������ �Է�");
				frame.setLayout(new BorderLayout());

				companyPanel = new JPanel();
				companyPanel.setLayout(new GridLayout(6, 2, 5, 5));

				comp_CI = new JTextField("");
				comp_CN = new JTextField("");
				comp_CA = new JTextField("");
				comp_CP = new JTextField("");
				comp_RN = new JTextField("");
				comp_RE = new JTextField("");

				companyPanel.add(new JLabel("Company ID"));
				companyPanel.add(comp_CI);
				companyPanel.add(new JLabel("Company Name"));
				companyPanel.add(comp_CN);
				companyPanel.add(new JLabel("Company Address"));
				companyPanel.add(comp_CA);
				companyPanel.add(new JLabel("Company Phone"));
				companyPanel.add(comp_CP);
				companyPanel.add(new JLabel("Representative Name"));
				companyPanel.add(comp_RN);
				companyPanel.add(new JLabel("Representative Email"));
				companyPanel.add(comp_RE);

				companyCompletePanel = new JPanel();
				companyCompleteBtn = new JButton("�뿩ȸ�� ���̺� �ش� ������ �߰�");
				companyCompletePanel.add(companyCompleteBtn);
				companyCompleteBtn.addActionListener(new ActionListenerCompCompleteInput());

				frame.add(companyPanel, BorderLayout.CENTER);
				frame.add(companyCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 300);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("�뿩ȸ�� ������ �Է� ����");
				System.out.println("");

			} catch (Exception e8) {
				System.out.println("�뿩ȸ�� ������ �Է� ���� : " + e8);
			}

		}

	}

	private class ActionListenerCompCompleteInput implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("�뿩ȸ�� ���̺� ������ �߰� �õ�...");

				String query = "INSERT INTO camping_car_rental_company VALUES (?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(query);

				pstmt.setInt(1, Integer.parseInt(comp_CI.getText()));
				pstmt.setString(2, comp_CN.getText());
				pstmt.setString(3, comp_CA.getText());
				pstmt.setString(4, comp_CP.getText());
				pstmt.setString(5, comp_RN.getText());
				pstmt.setString(6, comp_RE.getText());
				pstmt.executeUpdate();

				System.out.println("�뿩ȸ�� ���̺� ������ �߰� ����");

			} catch (Exception e9) {
				System.out.println("�뿩ȸ�� ���̺� ������ �߰� ���� : " + e9);
			}

			try {

				System.out.println("");
				System.out.println("�뿩ȸ�� ���̺� ��� �õ�...");

				mOutputTArea.setText("");
				mOutputTArea.setText("�뿩ȸ�� ID" + "\t\t" + "ȸ���" + "\t\t" + "�ּ�" + "\t\t" + "��ȭ��ȣ" + "\t\t" + "����� �̸�"
						+ "\t\t" + "����� �̸���" + "\n");

				String query2 = "select * from camping_car_rental_company";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
							+ rs.getString(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("�뿩ȸ�� ���̺� ��� ����");

			} catch (Exception e10) {
				System.out.println("�뿩ȸ�� ���̺� ��� ���� : " + e10);
			}

		}

	}

	// ������ ���� ��� 3 - �뿩ȸ�� ���� Action Listener //
	private class ActionListenerCompDelete implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("�뿩ȸ�翡�� �����Ϸ��� ������ �Է� �õ�...");

				frame = new JFrame("�����Ϸ��� �뿩ȸ���� ������ �Է�");
				frame.setLayout(new BorderLayout());

				companyPanel = new JPanel();
				companyPanel.setLayout(new GridLayout(1, 2, 5, 5));

				comp_CI = new JTextField("");

				companyPanel.add(new JLabel("�����Ϸ��� �뿩ȸ���� Company ID"));
				companyPanel.add(comp_CI);

				companyCompletePanel = new JPanel();
				companyCompleteBtn = new JButton("�ش� ȸ���� ������ ����");
				companyCompletePanel.add(companyCompleteBtn);
				companyCompleteBtn.addActionListener(new ActionListenerCompCompleteDelete());

				frame.add(companyPanel, BorderLayout.CENTER);
				frame.add(companyCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 150);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("�뿩ȸ�翡�� �����Ϸ��� ������ �Է� ����");
				System.out.println("");

			} catch (Exception e11) {
				System.out.println("�뿩ȸ�翡�� �����Ϸ��� ������ �Է� ���� : " + e11);
			}

		}

	}

	private class ActionListenerCompCompleteDelete implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("�뿩ȸ�� ���̺��� ������ ���� �õ�...");

				String query = "DELETE FROM camping_car_rental_company WHERE company_ID=?";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, Integer.parseInt(comp_CI.getText()));
				pstmt.executeUpdate();

				System.out.println("�뿩ȸ�� ���̺��� ������ ���� ����");
				System.out.println("");

			} catch (Exception e12) {
				System.out.println("�뿩ȸ�� ���̺��� ������ ���� ���� : " + e12);
			}

			try {

				System.out.println("");
				System.out.println("�ش� ������ ������ �뿩ȸ�� ���̺� ��� �õ�...");

				mOutputTArea.setText("");
				mOutputTArea.setText("�뿩ȸ�� ID" + "\t\t" + "ȸ���" + "\t\t" + "�ּ�" + "\t\t" + "��ȭ��ȣ" + "\t\t" + "����� �̸�"
						+ "\t\t" + "����� �̸���" + "\n");

				String query2 = "select * from camping_car_rental_company";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
							+ rs.getString(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("�ش� ������ ������ �뿩ȸ�� ���̺� ��� ����");
				System.out.println("");

			} catch (Exception e13) {
				System.out.println("�ش� ������ ������ �뿩ȸ�� ���̺� ��� ���� : " + e13);
			}

		}

	}

	// ������ ���� ��� 4 - �뿩ȸ�� ���� Action Listener //
	private class ActionListenerCompModify implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("�뿩ȸ�翡�� �����Ϸ��� ������ �Է� �õ�...");

				frame = new JFrame("�����Ϸ��� �뿩ȸ���� ������ �Է�");
				frame.setLayout(new BorderLayout());

				companyPanel = new JPanel();
				companyPanel.setLayout(new GridLayout(6, 2, 5, 5));

				comp_CI = new JTextField("");
				comp_CN = new JTextField("");
				comp_CA = new JTextField("");
				comp_CP = new JTextField("");
				comp_RN = new JTextField("");
				comp_RE = new JTextField("");

				companyPanel.add(new JLabel("�����Ϸ��� ȸ���� Company ID"));
				companyPanel.add(comp_CI);
				companyPanel.add(new JLabel("Company Name ���� ����"));
				companyPanel.add(comp_CN);
				companyPanel.add(new JLabel("Company Address ���� ����"));
				companyPanel.add(comp_CA);
				companyPanel.add(new JLabel("Company Phone ���� ����"));
				companyPanel.add(comp_CP);
				companyPanel.add(new JLabel("Representative Name ���� ����"));
				companyPanel.add(comp_RN);
				companyPanel.add(new JLabel("Representative Email ���� ����"));
				companyPanel.add(comp_RE);

				companyCompletePanel = new JPanel();
				companyCompleteBtn = new JButton("�ش� ȸ���� ������ ����");
				companyCompletePanel.add(companyCompleteBtn);
				companyCompleteBtn.addActionListener(new ActionListenerCompCompleteModify());

				frame.add(companyPanel, BorderLayout.CENTER);
				frame.add(companyCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 300);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("�뿩ȸ�翡�� �����Ϸ��� ������ �Է� ����");
				System.out.println("");

			} catch (Exception e14) {
				System.out.println("�뿩ȸ�翡�� �����Ϸ��� ������ �Է� ���� : " + e14);
			}

		}

	}

	private class ActionListenerCompCompleteModify implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("�뿩ȸ�� ���̺��� ������ ���� �õ�...");

				String query = "UPDATE camping_car_rental_company SET company_name=?, company_address=?, company_phone=?, representative_name=?, representative_email=? WHERE company_ID=?";
				pstmt = con.prepareStatement(query);

				pstmt.setString(1, comp_CN.getText());
				pstmt.setString(2, comp_CA.getText());
				pstmt.setString(3, comp_CP.getText());
				pstmt.setString(4, comp_RN.getText());
				pstmt.setString(5, comp_RE.getText());
				pstmt.setInt(6, Integer.parseInt(comp_CI.getText()));
				pstmt.executeUpdate();

				System.out.println("�뿩ȸ�� ���̺��� ������ ���� ����");

			} catch (Exception e15) {
				System.out.println("�뿩ȸ�� ���̺��� ������ ���� ���� : " + e15);
			}

			try {

				System.out.println("");
				System.out.println("����� �뿩ȸ�� ���̺� ��� �õ�...");

				mOutputTArea.setText("");
				mOutputTArea.setText("�뿩ȸ�� ID" + "\t\t" + "ȸ���" + "\t\t" + "�ּ�" + "\t\t" + "��ȭ��ȣ" + "\t\t" + "����� �̸�"
						+ "\t\t" + "����� �̸���" + "\n");

				String query2 = "select * from camping_car_rental_company";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
							+ rs.getString(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("����� �뿩ȸ�� ���̺� ��� ����");

			} catch (Exception e16) {
				System.out.println("����� �뿩ȸ�� ���̺� ��� ���� : " + e16);
			}

		}

	}

	// ������ ���� ��� 5 - ķ��ī �Է� Action Listener //
	private class ActionListenerCarInput implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("ķ��ī ������ �Է� �õ�...");

				frame = new JFrame("ķ��ī ������ �Է�");
				frame.setLayout(new BorderLayout());

				campingCarPanel = new JPanel();
				campingCarPanel.setLayout(new GridLayout(11, 2, 5, 5));

				car_CI = new JTextField("");
				car_CName = new JTextField("");
				car_CNum = new JTextField("");
				car_NP = new JTextField("");
				car_MC = new JTextField("");
				car_MY = new JTextField("");
				car_CDD = new JTextField("");
				car_RC = new JTextField("");
				car_RD = new JTextField("");
				car_comp_CI = new JTextField("");
				car_L = new JTextField("");

				campingCarPanel.add(new JLabel("Car ID"));
				campingCarPanel.add(car_CI);
				campingCarPanel.add(new JLabel("Car Name"));
				campingCarPanel.add(car_CName);
				campingCarPanel.add(new JLabel("Car Number"));
				campingCarPanel.add(car_CNum);
				campingCarPanel.add(new JLabel("Number Of Passengers"));
				campingCarPanel.add(car_NP);
				campingCarPanel.add(new JLabel("Manufacturing Company"));
				campingCarPanel.add(car_MC);
				campingCarPanel.add(new JLabel("Manufacturing Year"));
				campingCarPanel.add(car_MY);
				campingCarPanel.add(new JLabel("Cumulative Driving Distance"));
				campingCarPanel.add(car_CDD);
				campingCarPanel.add(new JLabel("Rental Cost"));
				campingCarPanel.add(car_RC);
				campingCarPanel.add(new JLabel("Register Date"));
				campingCarPanel.add(car_RD);
				campingCarPanel.add(new JLabel("Company ID"));
				campingCarPanel.add(car_comp_CI);
				campingCarPanel.add(new JLabel("Lendable"));
				campingCarPanel.add(car_L);

				campingCarCompletePanel = new JPanel();
				campingCarCompleteBtn = new JButton("ķ��ī ���̺� �ش� ������ �߰�");
				campingCarCompletePanel.add(campingCarCompleteBtn);
				campingCarCompleteBtn.addActionListener(new ActionListenerCarCompleteInput());

				frame.add(campingCarPanel, BorderLayout.CENTER);
				frame.add(campingCarCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 500);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("ķ��ī ������ �Է� ����");
				System.out.println("");

			} catch (Exception e17) {
				System.out.println("ķ��ī ������ �Է� ���� : " + e17);
			}

		}

	}

	private class ActionListenerCarCompleteInput implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("ķ��ī ���̺� ������ �߰� �õ�...");

				String query = "INSERT INTO camping_car VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(query);

				pstmt.setInt(1, Integer.parseInt(car_CI.getText()));
				pstmt.setString(2, car_CName.getText());
				pstmt.setInt(3, Integer.parseInt(car_CNum.getText()));
				pstmt.setInt(4, Integer.parseInt(car_NP.getText()));
				pstmt.setString(5, car_MC.getText());
				pstmt.setString(6, car_MY.getText());
				pstmt.setInt(7, Integer.parseInt(car_CDD.getText()));
				pstmt.setInt(8, Integer.parseInt(car_RC.getText()));
				pstmt.setString(9, car_RD.getText());
				pstmt.setInt(10, Integer.parseInt(car_comp_CI.getText()));
				pstmt.setInt(11, Integer.parseInt(car_L.getText()));
				pstmt.executeUpdate();

				System.out.println("ķ��ī ���̺� ������ �߰� ����");

			} catch (Exception e18) {
				System.out.println("ķ��ī ���̺� ������ �߰� ���� : " + e18);
			}

			try {

				System.out.println("");
				System.out.println("ķ��ī ���̺� ��� �õ�...");

				mOutputTArea.setText("");
				mOutputTArea.setText("ķ��ī ID" + "\t" + "ķ��ī �̸�" + "\t" + "������ȣ" + "\t" + "���� �ο���" + "\t" + "����ȸ��" + "\t"
						+ "��������" + "\t" + "��������Ÿ�" + "\t" + "�뿩���" + "\t" + "�������" + "\t" + "�뿩ȸ�� ID" + "\t"
						+ "�뿩 ���� ����" + "\n");

				String query2 = "select * from camping_car";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4)
							+ "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getInt(7) + "\t"
							+ rs.getInt(8) + "\t" + rs.getString(9) + "\t" + rs.getInt(10) + "\t" + rs.getInt(11)
							+ "\n";
					mOutputTArea.append(str);
				}

				System.out.println("ķ��ī ���̺� ��� ����");

			} catch (Exception e19) {
				System.out.println("ķ��ī ���̺� ��� ���� : " + e19);
			}

		}

	}

	// ������ ���� ��� 6 - ķ��ī ���� Action Listener //
	private class ActionListenerCarDelete implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("ķ��ī���� �����Ϸ��� ������ �Է� �õ�...");

				frame = new JFrame("�����Ϸ��� ķ��ī�� ������ �Է�");
				frame.setLayout(new BorderLayout());

				campingCarPanel = new JPanel();
				campingCarPanel.setLayout(new GridLayout(1, 2, 5, 5));

				car_CI = new JTextField("");

				campingCarPanel.add(new JLabel("Car ID"));
				campingCarPanel.add(car_CI);

				campingCarCompletePanel = new JPanel();
				campingCarCompleteBtn = new JButton("�ش� ķ��ī�� ������ ����");
				campingCarCompletePanel.add(campingCarCompleteBtn);
				campingCarCompleteBtn.addActionListener(new ActionListenerCarCompleteDelete());

				frame.add(campingCarPanel, BorderLayout.CENTER);
				frame.add(campingCarCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 150);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("ķ��ī���� �����Ϸ��� ������ �Է� ����");
				System.out.println("");

			} catch (Exception e20) {
				System.out.println("ķ��ī���� �����Ϸ��� ������ �Է� ���� : " + e20);
			}

		}

	}

	private class ActionListenerCarCompleteDelete implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("ķ��ī ���̺��� ������ ���� �õ�...");

				String query = "DELETE FROM camping_car WHERE car_ID=?";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, Integer.parseInt(car_CI.getText()));
				pstmt.executeUpdate();

				System.out.println("ķ��ī ���̺��� ������ ���� ����");
				System.out.println("");

			} catch (Exception e21) {
				System.out.println("ķ��ī ���̺��� ������ ���� ���� : " + e21);
			}

			try {

				System.out.println("");
				System.out.println("�ش� ������ ������ ķ��ī ���̺� ��� �õ�...");

				mOutputTArea.setText("");
				mOutputTArea.setText("ķ��ī ID" + "\t" + "ķ��ī �̸�" + "\t" + "������ȣ" + "\t" + "���� �ο���" + "\t" + "����ȸ��" + "\t"
						+ "��������" + "\t" + "��������Ÿ�" + "\t" + "�뿩���" + "\t" + "�������" + "\t" + "�뿩ȸ�� ID" + "\t"
						+ "�뿩 ���� ����" + "\n");

				String query2 = "select * from camping_car";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4)
							+ "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getInt(7) + "\t"
							+ rs.getInt(8) + "\t" + rs.getString(9) + "\t" + rs.getInt(10) + "\t" + rs.getInt(11)
							+ "\n";
					mOutputTArea.append(str);
				}

				System.out.println("�ش� ������ ������ ķ��ī ���̺� ��� ����");

			} catch (Exception e22) {
				System.out.println("�ش� ������ ������ ķ��ī ���̺� ��� ���� : " + e22);
			}

		}

	}

	// ������ ���� ��� 7 - ķ��ī ���� Action Listener //
	private class ActionListenerCarModify implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("ķ��ī���� �����Ϸ��� ������ �Է� �õ�...");

				frame = new JFrame("�����Ϸ��� ķ��ī�� ������ �Է�");
				frame.setLayout(new BorderLayout());

				campingCarPanel = new JPanel();
				campingCarPanel.setLayout(new GridLayout(11, 2, 5, 5));

				car_CI = new JTextField("");
				car_CName = new JTextField("");
				car_CNum = new JTextField("");
				car_NP = new JTextField("");
				car_MC = new JTextField("");
				car_MY = new JTextField("");
				car_CDD = new JTextField("");
				car_RC = new JTextField("");
				car_RD = new JTextField("");
				car_comp_CI = new JTextField("");
				car_L = new JTextField("");

				campingCarPanel.add(new JLabel("�����Ϸ��� ķ��ī�� Car ID"));
				campingCarPanel.add(car_CI);
				campingCarPanel.add(new JLabel("Car Name ���� ����"));
				campingCarPanel.add(car_CName);
				campingCarPanel.add(new JLabel("Car Number ���� ����"));
				campingCarPanel.add(car_CNum);
				campingCarPanel.add(new JLabel("Number Of Passengers ���� ����"));
				campingCarPanel.add(car_NP);
				campingCarPanel.add(new JLabel("Manufacturing Company ���� ����"));
				campingCarPanel.add(car_MC);
				campingCarPanel.add(new JLabel("Manufacturing Year ���� ����"));
				campingCarPanel.add(car_MY);
				campingCarPanel.add(new JLabel("Cumulative Driving Distance ���� ����"));
				campingCarPanel.add(car_CDD);
				campingCarPanel.add(new JLabel("Rental Cost ���� ����"));
				campingCarPanel.add(car_RC);
				campingCarPanel.add(new JLabel("Register Date ���� ����"));
				campingCarPanel.add(car_RD);
				campingCarPanel.add(new JLabel("Company ID ���� ����"));
				campingCarPanel.add(car_comp_CI);
				campingCarPanel.add(new JLabel("Lendable ���� ����"));
				campingCarPanel.add(car_L);

				campingCarCompletePanel = new JPanel();
				campingCarCompleteBtn = new JButton("�ش� ķ��ī�� ������ ����");
				campingCarCompletePanel.add(campingCarCompleteBtn);
				campingCarCompleteBtn.addActionListener(new ActionListenerCarCompleteModify());

				frame.add(campingCarPanel, BorderLayout.CENTER);
				frame.add(campingCarCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 500);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("ķ��ī���� �����Ϸ��� ������ �Է� ����");
				System.out.println("");

			} catch (Exception e23) {
				System.out.println("ķ��ī���� �����Ϸ��� ������ �Է� ���� : " + e23);
			}

		}

	}

	private class ActionListenerCarCompleteModify implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("ķ��ī ���̺��� ������ ���� �õ�...");

				String query = "UPDATE camping_car SET car_name=?, car_number=?, number_of_passengers=?, manufacturing_company=?, manufacturing_year=?, cumulative_driving_distance=?, rental_cost=?, resigter_date=?, camping_car_rental_company_company_ID=?, lendable=? WHERE car_ID=?";
				pstmt = con.prepareStatement(query);

				pstmt.setString(1, car_CName.getText());
				pstmt.setInt(2, Integer.parseInt(car_CNum.getText()));
				pstmt.setInt(3, Integer.parseInt(car_NP.getText()));
				pstmt.setString(4, car_MC.getText());
				pstmt.setString(5, car_MY.getText());
				pstmt.setInt(6, Integer.parseInt(car_CDD.getText()));
				pstmt.setInt(7, Integer.parseInt(car_RC.getText()));
				pstmt.setString(8, car_RD.getText());
				pstmt.setInt(9, Integer.parseInt(car_comp_CI.getText()));
				pstmt.setInt(10, Integer.parseInt(car_L.getText()));
				pstmt.setInt(11, Integer.parseInt(car_CI.getText()));
				pstmt.executeUpdate();

				System.out.println("ķ��ī ���̺��� ������ ���� ����");

			} catch (Exception e24) {
				System.out.println("ķ��ī ���̺��� ������ ���� ���� : " + e24);
			}

			try {

				System.out.println("");
				System.out.println("����� ķ��ī ���̺� ��� �õ�...");

				mOutputTArea.setText("");
				mOutputTArea.setText("ķ��ī ID" + "\t" + "ķ��ī �̸�" + "\t" + "������ȣ" + "\t" + "���� �ο���" + "\t" + "����ȸ��" + "\t"
						+ "��������" + "\t" + "��������Ÿ�" + "\t" + "�뿩���" + "\t" + "�������" + "\t" + "�뿩ȸ�� ID" + "\t"
						+ "�뿩 ���� ����" + "\n");

				String query2 = "select * from camping_car";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4)
							+ "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getInt(7) + "\t"
							+ rs.getInt(8) + "\t" + rs.getString(9) + "\t" + rs.getInt(10) + "\t" + rs.getInt(11)
							+ "\n";
					mOutputTArea.append(str);
				}

				System.out.println("����� ķ��ī ���̺� ��� ����");

			} catch (Exception e25) {
				System.out.println("����� ķ��ī ���̺� ��� ���� : " + e25);
			}

		}

	}

	// ������ ���� ��� 8 - �� �Է� Action Listener //
	private class ActionListenerCustInput implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("�� ������ �Է� �õ�...");

				frame = new JFrame("�� ������ �Է�");
				frame.setLayout(new BorderLayout());

				customerPanel = new JPanel();
				customerPanel.setLayout(new GridLayout(5, 2, 5, 5));

				cust_LN = new JTextField("");
				cust_CN = new JTextField("");
				cust_CA = new JTextField("");
				cust_CP = new JTextField("");
				cust_CE = new JTextField("");

				customerPanel.add(new JLabel("License Number"));
				customerPanel.add(cust_LN);
				customerPanel.add(new JLabel("Customer Name"));
				customerPanel.add(cust_CN);
				customerPanel.add(new JLabel("Customer Address"));
				customerPanel.add(cust_CA);
				customerPanel.add(new JLabel("Customer Phone"));
				customerPanel.add(cust_CP);
				customerPanel.add(new JLabel("Customer Email"));
				customerPanel.add(cust_CE);

				customerCompletePanel = new JPanel();
				customerCompleteBtn = new JButton("�� ���̺� �ش� ������ �߰�");
				customerCompletePanel.add(customerCompleteBtn);
				customerCompleteBtn.addActionListener(new ActionListenerCustCompleteInput());

				frame.add(customerPanel, BorderLayout.CENTER);
				frame.add(customerCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 300);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("�� ������ �Է� ����");
				System.out.println("");

			} catch (Exception e26) {
				System.out.println("�� ������ �Է� ���� : " + e26);
			}

		}

	}

	private class ActionListenerCustCompleteInput implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("�� ���̺� ������ �߰� �õ�...");

				String query = "INSERT INTO customer VALUES (?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(query);

				pstmt.setInt(1, Integer.parseInt(cust_LN.getText()));
				pstmt.setString(2, cust_CN.getText());
				pstmt.setString(3, cust_CA.getText());
				pstmt.setString(4, cust_CP.getText());
				pstmt.setString(5, cust_CE.getText());
				pstmt.executeUpdate();

				System.out.println("�� ���̺� ������ �߰� ����");

			} catch (Exception e27) {
				System.out.println("�� ���̺� ������ �߰� ���� : " + e27);
			}

			try {

				System.out.println("");
				System.out.println("�� ���̺� ��� �õ�...");

				mOutputTArea.setText("");
				mOutputTArea
						.setText("���������� ��ȣ" + "\t\t" + "�̸�" + "\t\t" + "�ּ�" + "\t\t" + "��ȭ��ȣ" + "\t\t" + "�̸���" + "\n");

				String query2 = "select * from customer";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
							+ rs.getString(4) + "\t\t" + rs.getString(5) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("�� ���̺� ��� ����");

			} catch (Exception e28) {
				System.out.println("�� ���̺� ��� ���� : " + e28);
			}

		}

	}

	// ������ ���� ��� 9 - �� ���� Action Listener //
	private class ActionListenerCustDelete implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("������ �����Ϸ��� ������ �Է� �õ�...");

				frame = new JFrame("�����Ϸ��� ���� ������ �Է�");
				frame.setLayout(new BorderLayout());

				customerPanel = new JPanel();
				customerPanel.setLayout(new GridLayout(1, 2, 5, 5));

				cust_LN = new JTextField("");

				customerPanel.add(new JLabel("�����Ϸ��� ���� License Number"));
				customerPanel.add(cust_LN);

				customerCompletePanel = new JPanel();
				customerCompleteBtn = new JButton("�ش� ���� ������ ����");
				customerCompletePanel.add(customerCompleteBtn);
				customerCompleteBtn.addActionListener(new ActionListenerCustCompleteDelete());

				frame.add(customerPanel, BorderLayout.CENTER);
				frame.add(customerCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 150);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("������ �����Ϸ��� ������ �Է� ����");
				System.out.println("");

			} catch (Exception e29) {
				System.out.println("������ �����Ϸ��� ������ �Է� ���� : " + e29);
			}

		}

	}

	private class ActionListenerCustCompleteDelete implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("�� ���̺��� ������ ���� �õ�...");

				String query = "DELETE FROM customer WHERE license_number=?";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, Integer.parseInt(cust_LN.getText()));
				pstmt.executeUpdate();

				System.out.println("�� ���̺��� ������ ���� ����");
				System.out.println("");

			} catch (Exception e30) {
				System.out.println("�� ���̺��� ������ ���� ���� : " + e30);
			}

			try {

				System.out.println("");
				System.out.println("�ش� ������ ������ �� ���̺� ��� �õ�...");

				mOutputTArea.setText("");
				mOutputTArea
						.setText("���������� ��ȣ" + "\t\t" + "�̸�" + "\t\t" + "�ּ�" + "\t\t" + "��ȭ��ȣ" + "\t\t" + "�̸���" + "\n");

				String query2 = "select * from customer";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
							+ rs.getString(4) + "\t\t" + rs.getString(5) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("�ش� ������ ������ �� ���̺� ��� ����");

			} catch (Exception e31) {
				System.out.println("�ش� ������ ������ �� ���̺� ��� ���� : " + e31);
			}

		}

	}

	// ������ ���� ��� 10 - �� ���� Action Listener //
	private class ActionListenerCustModify implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("������ �����Ϸ��� ������ �Է� �õ�...");

				frame = new JFrame("�����Ϸ��� ���� ������ �Է�");
				frame.setLayout(new BorderLayout());

				customerPanel = new JPanel();
				customerPanel.setLayout(new GridLayout(5, 2, 5, 5));

				cust_LN = new JTextField("");
				cust_CN = new JTextField("");
				cust_CA = new JTextField("");
				cust_CP = new JTextField("");
				cust_CE = new JTextField("");

				customerPanel.add(new JLabel("�����Ϸ��� ���� License Number"));
				customerPanel.add(cust_LN);
				customerPanel.add(new JLabel("Customer Name ���� ����"));
				customerPanel.add(cust_CN);
				customerPanel.add(new JLabel("Customer Address ���� ����"));
				customerPanel.add(cust_CA);
				customerPanel.add(new JLabel("Customer Phone ���� ����"));
				customerPanel.add(cust_CP);
				customerPanel.add(new JLabel("Customer Email ���� ����"));
				customerPanel.add(cust_CE);

				customerCompletePanel = new JPanel();
				customerCompleteBtn = new JButton("�ش� ���� ������ ����");
				customerCompletePanel.add(customerCompleteBtn);
				customerCompleteBtn.addActionListener(new ActionListenerCustCompleteModify());

				frame.add(customerPanel, BorderLayout.CENTER);
				frame.add(customerCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 300);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("������ �����Ϸ��� ������ �Է� ����");
				System.out.println("");

			} catch (Exception e32) {
				System.out.println("������ �����Ϸ��� ������ �Է� ���� : " + e32);
			}

		}

	}

	private class ActionListenerCustCompleteModify implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("�� ���̺��� ������ ���� �õ�...");

				String query = "UPDATE customer SET customer_name=?, customer_address=?, customer_phone=?, customer_email=? WHERE license_number=?";
				pstmt = con.prepareStatement(query);

				pstmt.setString(1, cust_CN.getText());
				pstmt.setString(2, cust_CA.getText());
				pstmt.setString(3, cust_CP.getText());
				pstmt.setString(4, cust_CE.getText());
				pstmt.setInt(5, Integer.parseInt(cust_LN.getText()));
				pstmt.executeUpdate();

				System.out.println("�� ���̺��� ������ ���� ����");

			} catch (Exception e33) {
				System.out.println("�� ���̺��� ������ ���� ���� : " + e33);
			}

			try {

				System.out.println("");
				System.out.println("����� �� ���̺� ��� �õ�...");

				mOutputTArea.setText("");
				mOutputTArea
						.setText("���������� ��ȣ" + "\t\t" + "�̸�" + "\t\t" + "�ּ�" + "\t\t" + "��ȭ��ȣ" + "\t\t" + "�̸���" + "\n");

				String query2 = "select * from customer";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
							+ rs.getString(4) + "\t\t" + rs.getString(5) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("����� �� ���̺� ��� ����");

			} catch (Exception e34) {
				System.out.println("����� �� ���̺� ��� ���� : " + e34);
			}

		}

	}

	// ������ ���� ��� 11 - ����� �Է� Action Listener //
	private class ActionListenerShopInput implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("����� ������ �Է� �õ�...");

				frame = new JFrame("����� ������ �Է�");
				frame.setLayout(new BorderLayout());

				repairShopPanel = new JPanel();
				repairShopPanel.setLayout(new GridLayout(6, 2, 5, 5));

				shop_SI = new JTextField("");
				shop_SN = new JTextField("");
				shop_SA = new JTextField("");
				shop_SP = new JTextField("");
				shop_RN = new JTextField("");
				shop_RE = new JTextField("");

				repairShopPanel.add(new JLabel("Car Repair Shop ID"));
				repairShopPanel.add(shop_SI);
				repairShopPanel.add(new JLabel("Repair Shop Name"));
				repairShopPanel.add(shop_SN);
				repairShopPanel.add(new JLabel("Repair Shop Address"));
				repairShopPanel.add(shop_SA);
				repairShopPanel.add(new JLabel("Repair Shop Phone"));
				repairShopPanel.add(shop_SP);
				repairShopPanel.add(new JLabel("Representative Name"));
				repairShopPanel.add(shop_RN);
				repairShopPanel.add(new JLabel("Representative Email"));
				repairShopPanel.add(shop_RE);

				repairShopCompletePanel = new JPanel();
				repairShopCompleteBtn = new JButton("����� ���̺� �ش� ������ �߰�");
				repairShopCompletePanel.add(repairShopCompleteBtn);
				repairShopCompleteBtn.addActionListener(new ActionListenerShopComPleteInput());

				frame.add(repairShopPanel, BorderLayout.CENTER);
				frame.add(repairShopCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 300);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("����� ������ �Է� ����");
				System.out.println("");

			} catch (Exception e35) {
				System.out.println("����� ������ �Է� ���� : " + e35);
			}

		}

	}

	private class ActionListenerShopComPleteInput implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("����� ���̺� ������ �߰� �õ�...");

				String query = "INSERT INTO car_repair_shop VALUES (?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(query);

				pstmt.setInt(1, Integer.parseInt(shop_SI.getText()));
				pstmt.setString(2, shop_SN.getText());
				pstmt.setString(3, shop_SA.getText());
				pstmt.setString(4, shop_SP.getText());
				pstmt.setString(5, shop_RN.getText());
				pstmt.setString(6, shop_RE.getText());
				pstmt.executeUpdate();

				System.out.println("����� ���̺� ������ �߰� ����");

			} catch (Exception e36) {
				System.out.println("����� ���̺� ������ �߰� ���� : " + e36);
			}

			try {

				System.out.println("");
				System.out.println("����� ���̺� ��� �õ�...");

				mOutputTArea.setText("");
				mOutputTArea.setText("����� ID" + "\t\t" + "�̸�" + "\t\t" + "�ּ�" + "\t\t" + "��ȭ��ȣ" + "\t\t" + "����� �̸�"
						+ "\t\t" + "����� �̸���" + "\n");

				String query2 = "select * from car_repair_shop";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
							+ rs.getString(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("����� ���̺� ��� ����");

			} catch (Exception e37) {
				System.out.println("����� ���̺� ��� ���� : " + e37);
			}

		}

	}

	// ������ ���� ��� 12 - ����� ���� Action Listener //
	private class ActionListenerShopDelete implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("����ҿ��� �����Ϸ��� ������ �Է� �õ�...");

				frame = new JFrame("�����Ϸ��� ������� ������ �Է�");
				frame.setLayout(new BorderLayout());

				repairShopPanel = new JPanel();
				repairShopPanel.setLayout(new GridLayout(1, 2, 5, 5));

				shop_SI = new JTextField("");

				repairShopPanel.add(new JLabel("�����Ϸ��� ������� Car Repair Shop ID"));
				repairShopPanel.add(shop_SI);

				repairShopCompletePanel = new JPanel();
				repairShopCompleteBtn = new JButton("�ش� ������� ������ ����");
				repairShopCompletePanel.add(repairShopCompleteBtn);
				repairShopCompleteBtn.addActionListener(new ActionListenerShopCompleteDelete());

				frame.add(repairShopPanel, BorderLayout.CENTER);
				frame.add(repairShopCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 150);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("����ҿ��� �����Ϸ��� ������ �Է� ����");
				System.out.println("");

			} catch (Exception e38) {
				System.out.println("����ҿ��� �����Ϸ��� ������ �Է� ���� : " + e38);
			}

		}

	}

	private class ActionListenerShopCompleteDelete implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("����� ���̺��� ������ ���� �õ�...");

				String query = "DELETE FROM car_repair_shop WHERE car_repair_shop_ID=?";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, Integer.parseInt(shop_SI.getText()));
				pstmt.executeUpdate();

				System.out.println("����� ���̺��� ������ ���� ����");
				System.out.println("");

			} catch (Exception e39) {
				System.out.println("����� ���̺��� ������ ���� ���� : " + e39);
			}

			try {

				System.out.println("");
				System.out.println("�ش� ������ ������ ����� ���̺� ��� �õ�...");

				mOutputTArea.setText("");
				mOutputTArea.setText("����� ID" + "\t\t" + "�̸�" + "\t\t" + "�ּ�" + "\t\t" + "��ȭ��ȣ" + "\t\t" + "����� �̸�"
						+ "\t\t" + "����� �̸���" + "\n");

				String query2 = "select * from car_repair_shop";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
							+ rs.getString(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("�ش� ������ ������ ����� ���̺� ��� ����");

			} catch (Exception e40) {
				System.out.println("�ش� ������ ������ ����� ���̺� ��� ���� : " + e40);
			}

		}

	}

	// ������ ���� ��� 13 - ����� ���� Action Listener //
	private class ActionListenerShopModify implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("����ҿ��� �����Ϸ��� ������ �Է� �õ�...");

				frame = new JFrame("�����Ϸ��� ������� ������ �Է�");
				frame.setLayout(new BorderLayout());

				repairShopPanel = new JPanel();
				repairShopPanel.setLayout(new GridLayout(6, 2, 5, 5));

				shop_SI = new JTextField("");
				shop_SN = new JTextField("");
				shop_SA = new JTextField("");
				shop_SP = new JTextField("");
				shop_RN = new JTextField("");
				shop_RE = new JTextField("");

				repairShopPanel.add(new JLabel("�����Ϸ��� ������� Car Repair Shop ID"));
				repairShopPanel.add(shop_SI);
				repairShopPanel.add(new JLabel("Repair Shop Name ���� ����"));
				repairShopPanel.add(shop_SN);
				repairShopPanel.add(new JLabel("Repair Shop Address ���� ����"));
				repairShopPanel.add(shop_SA);
				repairShopPanel.add(new JLabel("Repair Shop Phone ���� ����"));
				repairShopPanel.add(shop_SP);
				repairShopPanel.add(new JLabel("Representative Name ���� ����"));
				repairShopPanel.add(shop_RN);
				repairShopPanel.add(new JLabel("Representative Email ���� ����"));
				repairShopPanel.add(shop_RE);

				repairShopCompletePanel = new JPanel();
				repairShopCompleteBtn = new JButton("�ش� ������� ������ ����");
				repairShopCompletePanel.add(repairShopCompleteBtn);
				repairShopCompleteBtn.addActionListener(new ActionListenerShopCompleteModify());

				frame.add(repairShopPanel, BorderLayout.CENTER);
				frame.add(repairShopCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 300);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("����ҿ��� �����Ϸ��� ������ �Է� ����");
				System.out.println("");

			} catch (Exception e41) {
				System.out.println("����ҿ��� �����Ϸ��� ������ �Է� ���� : " + e41);
			}

		}

	}

	private class ActionListenerShopCompleteModify implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("����� ���̺��� ������ ���� �õ�...");

				String query = "UPDATE car_repair_shop SET repair_shop_name=?, repair_shop_address=?, repair_shop_phone=?, representative_name=?, representative_email=? WHERE car_repair_shop_ID=?";
				pstmt = con.prepareStatement(query);

				pstmt.setString(1, shop_SN.getText());
				pstmt.setString(2, shop_SA.getText());
				pstmt.setString(3, shop_SP.getText());
				pstmt.setString(4, shop_RN.getText());
				pstmt.setString(5, shop_RE.getText());
				pstmt.setInt(6, Integer.parseInt(shop_SI.getText()));
				pstmt.executeUpdate();

				System.out.println("����� ���̺��� ������ ���� ����");

			} catch (Exception e42) {
				System.out.println("����� ���̺��� ������ ���� ���� : " + e42);
			}

			try {

				System.out.println("");
				System.out.println("����� ����� ���̺� ��� �õ�...");

				mOutputTArea.setText("");
				mOutputTArea.setText("����� ID" + "\t\t" + "�̸�" + "\t\t" + "�ּ�" + "\t\t" + "��ȭ��ȣ" + "\t\t" + "����� �̸�"
						+ "\t\t" + "����� �̸���" + "\n");

				String query2 = "select * from car_repair_shop";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
							+ rs.getString(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("����� ����� ���̺� ��� ����");

			} catch (Exception e43) {
				System.out.println("����� ����� ���̺� ��� ���� : " + e43);
			}

		}

	}

	// ������ ���� ��� 14 - ķ��ī ��ȯ Action Listener //
	private class ActionListenerReturnCar implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				JFrame ReturnInputFrame = new JFrame("ķ��ī ��ȯâ");
				JButton btnReCar = new JButton("��ȯ");

				Return_UN = new JTextField(20);
				Return_CI = new JTextField(20);
				Return_FP = new JTextField(20);
				Return_LP = new JTextField(20);
				Return_RP = new JTextField(20);
				Return_BP = new JTextField(20);
				Return_RR = new JTextField(20);

				GridLayout gridReturnCar = new GridLayout(9, 2, 10, 10);

				ReturnInputFrame.setLayout(gridReturnCar);

				ReturnInputFrame.add(new JLabel("<ķ��ī ���˳���>"));
				ReturnInputFrame.add(new JLabel(""));

				ReturnInputFrame.add(new JLabel("�����뿩��ȣ : "));
				ReturnInputFrame.add(Return_UN);

				ReturnInputFrame.add(new JLabel("ķ��ī���ID : "));
				ReturnInputFrame.add(Return_CI);

				ReturnInputFrame.add(new JLabel("�պκ� ���� : "));
				ReturnInputFrame.add(Return_FP);

				ReturnInputFrame.add(new JLabel("���� ���� : "));
				ReturnInputFrame.add(Return_LP);

				ReturnInputFrame.add(new JLabel("������ ���� : "));
				ReturnInputFrame.add(Return_RP);

				ReturnInputFrame.add(new JLabel("���� ���� : "));
				ReturnInputFrame.add(Return_BP);

				ReturnInputFrame.add(new JLabel("�����ʿ� ���� : "));
				ReturnInputFrame.add(Return_RR);

				ReturnInputFrame.add(new JLabel(""));
				ReturnInputFrame.add(btnReCar);

				btnReCar.addActionListener(new ActionListenerRetBtn());

				ReturnInputFrame.setSize(500, 500);
				ReturnInputFrame.setVisible(true);
				ReturnInputFrame.setLocation(150, 200);

			} catch (Exception e44) {
				System.out.println("���� ����: ");
				System.out.println(e44.getMessage());
			}

		}

	}

	private class ActionListenerRetBtn implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				String rprq = Return_RR.getText();
				int rprq_i = Integer.parseInt(rprq);

				if (rprq_i == 0) {

					int tmplen = -1;

					String recar = Return_CI.getText(); // car_ID

					String sqlfirst = "select lendable from camping_car where car_ID = ?;";

					pstmt = con.prepareStatement(sqlfirst);
					pstmt.setString(1, recar);
					rs = pstmt.executeQuery();

					while (rs.next())
						tmplen = rs.getInt(1);

					if (tmplen == 0) {
						String sqlre = "UPDATE camping_car SET lendable = 1 WHERE car_ID = ?;";

						pstmt = con.prepareStatement(sqlre);
						pstmt.setString(1, recar);
						pstmt.executeUpdate();

						System.out.println("car_ID : " + recar + ", ��ȯ�Ϸ�");
						System.out.println("");
					} else if (tmplen == 1)
						System.out.println("���� : �̹� �ݳ��� �����Դϴ�");
					else if (tmplen == -1)
						System.out.println("���� : ���� �� ID�Դϴ�.");
				} else if (rprq_i == 1) {

					try {

						System.out.println("�������� ������ �Է� �õ�...");

						frame = new JFrame("�������� ������ �Է�");
						frame.setLayout(new BorderLayout());

						maintenanceInfoPanel = new JPanel();
						maintenanceInfoPanel.setLayout(new GridLayout(9, 2, 5, 5));

						info_UMN = new JTextField("");
						info_MH = new JTextField("");
						info_RD = new JTextField("");
						info_RC = new JTextField("");
						info_PD = new JTextField("");
						info_OMI = new JTextField("");
						info_CRSI = new JTextField("");
						info_CI = new JTextField("");
						info_LN = new JTextField("");

						maintenanceInfoPanel.add(new JLabel("Unique Maintenance Number"));
						maintenanceInfoPanel.add(info_UMN);
						maintenanceInfoPanel.add(new JLabel("Maintenance History"));
						maintenanceInfoPanel.add(info_MH);
						maintenanceInfoPanel.add(new JLabel("Repair Date"));
						maintenanceInfoPanel.add(info_RD);
						maintenanceInfoPanel.add(new JLabel("Repair Charge"));
						maintenanceInfoPanel.add(info_RC);
						maintenanceInfoPanel.add(new JLabel("Payment Deadline"));
						maintenanceInfoPanel.add(info_PD);
						maintenanceInfoPanel.add(new JLabel("Other Maintenance Info"));
						maintenanceInfoPanel.add(info_OMI);
						maintenanceInfoPanel.add(new JLabel("Car Repair Shop ID"));
						maintenanceInfoPanel.add(info_CRSI);
						maintenanceInfoPanel.add(new JLabel("Camping Car ID"));
						maintenanceInfoPanel.add(info_CI);
						maintenanceInfoPanel.add(new JLabel("Customer License Number"));
						maintenanceInfoPanel.add(info_LN);

						maintenanceInfoCompletePanel = new JPanel();
						maintenanceInfoCompleteBtn = new JButton("�������� ���̺� �ش� ������ �߰�");
						maintenanceInfoCompletePanel.add(maintenanceInfoCompleteBtn);
						maintenanceInfoCompleteBtn.addActionListener(new ActionListenerMInfoCompleteInput());

						frame.add(maintenanceInfoPanel, BorderLayout.CENTER);
						frame.add(maintenanceInfoCompletePanel, BorderLayout.SOUTH);

						frame.setSize(500, 500);
						frame.setVisible(true);
						frame.setLocation(150, 100);

						System.out.println("�������� ������ �Է� ����");
						System.out.println("");

					} catch (Exception e45) {
						System.out.println("�������� ������ �Է� ���� : " + e45);
					}

				}

			} catch (Exception e46) {

				System.out.println("���� ����: ");
				System.out.println(e46.getMessage());
			}

		}

	}

	// ������ ���� ��� 15 - ������ �ʿ��� ��� �������� �Է� Action Listener //
	private class ActionListenerMInfoCompleteInput implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("�������� ���̺� ������ �߰� �õ�...");

				String query = "INSERT INTO maintenance_information VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(query);

				String query2 = "SELECT camping_car_rental_company_company_ID from car_loan_application where camping_car_car_ID=? and customer_license_number=?";
				pstmt2 = con.prepareStatement(query2);
				pstmt2.setInt(1, Integer.parseInt(info_CI.getText()));
				pstmt2.setInt(2, Integer.parseInt(info_LN.getText()));
				rs = pstmt2.executeQuery();
				rs.next();
				int RCI = rs.getInt(1);

				pstmt.setInt(1, Integer.parseInt(info_UMN.getText()));
				pstmt.setString(2, info_MH.getText());
				pstmt.setString(3, info_RD.getText());
				pstmt.setInt(4, Integer.parseInt(info_RC.getText()));
				pstmt.setString(5, info_PD.getText());
				pstmt.setString(6, info_OMI.getText());
				pstmt.setInt(7, Integer.parseInt(info_CRSI.getText()));
				pstmt.setInt(8, Integer.parseInt(info_CI.getText()));
				pstmt.setInt(9, RCI);
				pstmt.setInt(10, Integer.parseInt(info_LN.getText()));
				pstmt.executeUpdate();

				System.out.println("�������� ���̺� ������ �߰� ����");

			} catch (Exception e47) {
				System.out.println("�������� ���̺� ������ �߰� ���� : " + e47);
			}

			try {

				System.out.println("");
				System.out.println("�������� ���̺� ��� �õ�...");

				mOutputTArea.setText("");
				mOutputTArea.setText("���������ȣ" + "\t" + "���񳻿�" + "\t" + "������¥" + "\t" + "�������" + "\t" + "���Ա���" + "\t"
						+ "��Ÿ���񳻿�" + "\t" + "�����ID" + "\t" + "ķ��ī���ID" + "\t" + "�뿩ȸ��ID" + "\t" + "�� ������������ȣ" + "\n");

				String query2 = "select * from maintenance_information";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4)
							+ "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getInt(7) + "\t"
							+ rs.getInt(8) + "\t" + rs.getInt(9) + "\t" + rs.getInt(10) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("�������� ���̺� ��� ����");

			} catch (Exception e48) {
				System.out.println("�������� ���̺� ��� ���� : " + e48);
			}

		}

	}

	// ������ ���� ��� 16 - �������� ���� Action Listener //
	private class ActionListenerMInfoDelete implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("������������ �����Ϸ��� ������ �Է� �õ�...");

				frame = new JFrame("�����Ϸ��� ���������� ������ �Է�");
				frame.setLayout(new BorderLayout());

				maintenanceInfoPanel = new JPanel();
				maintenanceInfoPanel.setLayout(new GridLayout(1, 2, 5, 5));

				info_UMN = new JTextField("");

				maintenanceInfoPanel.add(new JLabel("�����Ϸ��� ���������� Unique Maintenance Number"));
				maintenanceInfoPanel.add(info_UMN);

				maintenanceInfoCompletePanel = new JPanel();
				maintenanceInfoCompleteBtn = new JButton("�ش� ���������� ������ ����");
				maintenanceInfoCompletePanel.add(maintenanceInfoCompleteBtn);
				maintenanceInfoCompleteBtn.addActionListener(new ActionListenerMInfoCompleteDelete());

				frame.add(maintenanceInfoPanel, BorderLayout.CENTER);
				frame.add(maintenanceInfoCompletePanel, BorderLayout.SOUTH);

				frame.setSize(700, 150);
				frame.setVisible(true);
				frame.setLocation(40, 400);

				System.out.println("������������ �����Ϸ��� ������ �Է� ����");
				System.out.println("");

			} catch (Exception e49) {
				System.out.println("������������ �����Ϸ��� ������ �Է� ���� : " + e49);
			}

		}

	}

	private class ActionListenerMInfoCompleteDelete implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("�������� ���̺��� ������ ���� �õ�...");

				String query = "DELETE FROM maintenance_information WHERE uniqe_maintenance_number=?";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, Integer.parseInt(info_UMN.getText()));
				pstmt.executeUpdate();

				System.out.println("�������� ���̺��� ������ ���� ����");
				System.out.println("");

			} catch (Exception e50) {
				System.out.println("�������� ���̺��� ������ ���� ���� : " + e50);
			}

			try {

				System.out.println("");
				System.out.println("�ش� ������ ������ �������� ���̺� ��� �õ�...");

				mOutputTArea.setText("");
				mOutputTArea.setText("���������ȣ" + "\t" + "���񳻿�" + "\t" + "������¥" + "\t" + "�������" + "\t" + "���Ա���" + "\t"
						+ "��Ÿ���񳻿�" + "\t" + "�����ID" + "\t" + "ķ��ī���ID" + "\t" + "�뿩ȸ��ID" + "\t" + "�� ������������ȣ" + "\n");

				String query2 = "select * from maintenance_information";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4)
							+ "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getInt(7) + "\t"
							+ rs.getInt(8) + "\t" + rs.getInt(9) + "\t" + rs.getInt(10) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("�ش� ������ ������ �������� ���̺� ��� ����");

			} catch (Exception e51) {
				System.out.println("�ش� ������ ������ �������� ���̺� ��� ���� : " + e51);
			}

		}

	}

	// ������ ���� ��� 17 - �������� ���� Action Listener //
	private class ActionListenerMInfoModify implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("������������ �����Ϸ��� ������ �Է� �õ�...");

				frame = new JFrame("�����Ϸ��� ���������� ������ �Է�");
				frame.setLayout(new BorderLayout());

				maintenanceInfoPanel = new JPanel();
				maintenanceInfoPanel.setLayout(new GridLayout(9, 2, 5, 5));

				info_UMN = new JTextField("");
				info_MH = new JTextField("");
				info_RD = new JTextField("");
				info_RC = new JTextField("");
				info_PD = new JTextField("");
				info_OMI = new JTextField("");
				info_CRSI = new JTextField("");
				info_CI = new JTextField("");
				info_LN = new JTextField("");

				maintenanceInfoPanel.add(new JLabel("�����Ϸ��� ���������� Unique Maintenance Number"));
				maintenanceInfoPanel.add(info_UMN);
				maintenanceInfoPanel.add(new JLabel("Maintenance History ���� ����"));
				maintenanceInfoPanel.add(info_MH);
				maintenanceInfoPanel.add(new JLabel("Repair Date ���� ����"));
				maintenanceInfoPanel.add(info_RD);
				maintenanceInfoPanel.add(new JLabel("Repair Charge ���� ����"));
				maintenanceInfoPanel.add(info_RC);
				maintenanceInfoPanel.add(new JLabel("Payment Deadline ���� ����"));
				maintenanceInfoPanel.add(info_PD);
				maintenanceInfoPanel.add(new JLabel("Other Maintenance Info ���� ����"));
				maintenanceInfoPanel.add(info_OMI);
				maintenanceInfoPanel.add(new JLabel("Car Repair Shop ID ���� ����"));
				maintenanceInfoPanel.add(info_CRSI);
				maintenanceInfoPanel.add(new JLabel("Camping Car ID ���� ����"));
				maintenanceInfoPanel.add(info_CI);
				maintenanceInfoPanel.add(new JLabel("Customer License Number ���� ����"));
				maintenanceInfoPanel.add(info_LN);

				maintenanceInfoCompletePanel = new JPanel();
				maintenanceInfoCompleteBtn = new JButton("�ش� ���������� ������ ����");
				maintenanceInfoCompletePanel.add(maintenanceInfoCompleteBtn);
				maintenanceInfoCompleteBtn.addActionListener(new ActionListenerMInfoCompleteModify());

				frame.add(maintenanceInfoPanel, BorderLayout.CENTER);
				frame.add(maintenanceInfoCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 500);
				frame.setVisible(true);
				frame.setLocation(150, 100);

				System.out.println("������������ �����Ϸ��� ������ �Է� ����");
				System.out.println("");

			} catch (Exception e52) {
				System.out.println("����ҿ��� �����Ϸ��� ������ �Է� ���� : " + e52);
			}

		}

	}

	private class ActionListenerMInfoCompleteModify implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("�������� ���̺��� ������ ���� �õ�...");

				String query = "UPDATE maintenance_information SET maintenance_history=?, repair_date=?, repair_charge=?, payment_deadline=?, other_maintenance_info=?, car_repair_shop_car_repair_shop_ID=?, camping_car_car_ID=?, camping_car_rental_company_company_ID=?, customer_license_number=? WHERE uniqe_maintenance_number=?";
				pstmt = con.prepareStatement(query);

				String query2 = "SELECT camping_car_rental_company_company_ID from car_loan_application where camping_car_car_ID=? and customer_license_number=?";
				pstmt2 = con.prepareStatement(query2);
				pstmt2.setInt(1, Integer.parseInt(info_CI.getText()));
				pstmt2.setInt(2, Integer.parseInt(info_LN.getText()));
				rs = pstmt2.executeQuery();
				rs.next();
				int RCI = rs.getInt(1);

				pstmt.setString(1, info_MH.getText());
				pstmt.setString(2, info_RD.getText());
				pstmt.setInt(3, Integer.parseInt(info_RC.getText()));
				pstmt.setString(4, info_PD.getText());
				pstmt.setString(5, info_OMI.getText());
				pstmt.setInt(6, Integer.parseInt(info_CRSI.getText()));
				pstmt.setInt(7, Integer.parseInt(info_CI.getText()));
				pstmt.setInt(8, RCI);
				pstmt.setInt(9, Integer.parseInt(info_LN.getText()));
				pstmt.setInt(10, Integer.parseInt(info_UMN.getText()));
				pstmt.executeUpdate();

				System.out.println("�������� ���̺��� ������ ���� ����");

			} catch (Exception e53) {
				System.out.println("�������� ���̺��� ������ ���� ���� : " + e53);
			}

			try {

				System.out.println("");
				System.out.println("����� �������� ���̺� ��� �õ�...");

				mOutputTArea.setText("");
				mOutputTArea.setText("���������ȣ" + "\t" + "���񳻿�" + "\t" + "������¥" + "\t" + "�������" + "\t" + "���Ա���" + "\t"
						+ "��Ÿ���񳻿�" + "\t" + "�����ID" + "\t" + "ķ��ī���ID" + "\t" + "�뿩ȸ��ID" + "\t" + "�� ������������ȣ" + "\n");

				String query2 = "select * from maintenance_information";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4)
							+ "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getInt(7) + "\t"
							+ rs.getInt(8) + "\t" + rs.getInt(9) + "\t" + rs.getInt(10) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("����� �������� ���̺� ��� ����");

			} catch (Exception e54) {
				System.out.println("����� �������� ���̺� ��� ���� : " + e54);
			}

		}

	}

	// ������ ���� ��� 18 - �˻� 1 Action Listener //
	private class ActionListenerSelBtn1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				stmt = con.createStatement();

				String sqlSel1 = "select camping_car_car_ID 'car_ID',(select car_name\r\n"
						+ "      from camping_car \r\n"
						+ "        where camping_car.car_ID = car_loan_application.camping_car_car_ID) 'car_name', sum(charge) 'total_cost'\r\n"
						+ "from car_loan_application \r\n" + "group by car_loan_application.camping_car_car_ID;\r\n"
						+ "";

				mOutputTArea.setText("");
				mOutputTArea.append("< �˻� 1 - ķ��ī ID�� ķ��ī �̸��� ���� �� û����� >" + "\n\n");

				mOutputTArea.append("ķ��ī ID" + "\t" + "ķ��ī �̸�" + "\t" + "�� û�����\n\n");
				rs = stmt.executeQuery(sqlSel1);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\n";
					mOutputTArea.append(str);
				}

			} catch (Exception e55) {
				System.out.println("���� ����: ");
				System.out.println(e55.getMessage());

			}

		}

	}

	// ������ ���� ��� 19 - �˻� 2 Action Listener //
	private class ActionListenerSelBtn2 implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				stmt = con.createStatement();

				String sqlSel2 = "select customer_license_number 'license_number' , (select customer_name\r\n"
						+ "      from customer\r\n"
						+ "        where customer.license_number = car_loan_application.customer_license_number) 'customer_name', sum(charge) 'total_cost'\r\n"
						+ "from car_loan_application \r\n"
						+ "group by car_loan_application.customer_license_number;\r\n" + "";

				mOutputTArea.setText("");
				mOutputTArea.append("< �˻� 2 - ������������ȣ�� �� �̸��� ���� �� û����� >\n\n");

				mOutputTArea.append("������ ��ȣ" + "\t" + "�� �̸�" + "\t" + "�� û�����\n\n");
				rs = stmt.executeQuery(sqlSel2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\n";
					mOutputTArea.append(str);
				}

			} catch (Exception e56) {
				System.out.println("���� ����: ");
				System.out.println(e56.getMessage());
			}

		}

	}

	// ������ ���� ��� 20 - �˻� 3 Action Listener //
	private class ActionListenerSelBtn3 implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				stmt = con.createStatement();

				String sqlSel3 = "select cs.customer_name '���̸�' , count(mi.uniqe_maintenance_number) '�� ����Ƚ��' , sum(mi.repair_charge) '�� �������'\r\n"
						+ "from customer cs, maintenance_information mi\r\n"
						+ "where cs.license_number = mi.customer_license_number\r\n"
						+ "group by mi.customer_license_number;";

				mOutputTArea.setText("");
				mOutputTArea.append("< �˻� 3 - ���� �� ���� Ƚ���� ǥ���ϰ�, ������ �� û������� ��� >\n\n");

				mOutputTArea.append("�� �̸�" + "\t" + "���� Ƚ��" + "\t" + "�� û�����\n\n");
				rs = stmt.executeQuery(sqlSel3);

				while (rs.next()) {
					String str = rs.getString(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\n";
					mOutputTArea.append(str);
				}

			} catch (Exception e57) {
				System.out.println("���� ����: ");
				System.out.println(e57.getMessage());
			}

		}

	}

	// ������ ���� ��� 21 - �˻� 4 Action Listener //
	private class ActionListenerSelBtn4 implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				stmt = con.createStatement();

				String sqlSel4 = "select camping_car_rental_company_company_ID 'company_ID' , (select company_name\r\n"
						+ "      from camping_car_rental_company\r\n"
						+ "        where camping_car_rental_company.company_ID = maintenance_information.camping_car_rental_company_company_ID) 'company_name', sum(repair_charge) 'total_repair_cost'\r\n"
						+ "from maintenance_information\r\n"
						+ "group by maintenance_information.camping_car_rental_company_company_ID;";

				mOutputTArea.setText("");
				mOutputTArea.append("< �˻� 4 - ȸ��ID�� ȸ��� ���� �� �������  >\n\n");

				mOutputTArea.append("ȸ�� ID" + "\t" + "ȸ���" + "\t" + "�� �������\n\n");
				rs = stmt.executeQuery(sqlSel4);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\n";
					mOutputTArea.append(str);
				}

			} catch (Exception e58) {
				System.out.println("���� ����: ");
				System.out.println(e58.getMessage());
			}

		}

	}

	public static void main(String[] args) {

		campingCarCompany sejong = new campingCarCompany();

		sejong.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent we) {

				try {
					con.close();
				} catch (Exception e59) {
				}

				System.out.println("");
				System.out.println("���α׷� ���� ����!");
				System.exit(0);

			}

		});

	}

}