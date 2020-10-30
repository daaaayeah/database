import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class campingCarCompany extends JFrame {

	JPanel loginPanel;
	JPanel mBtnPanel, mIOPanel;
	JPanel initPanel, carPanel, returnPanel, repairPanel, selectPanel;
	JFrame frame;

	// 대여회사 //
	JPanel companyPanel;
	JPanel companyCompletePanel;
	JButton companyCompleteBtn;
	JTextField comp_CI, comp_CN, comp_CA, comp_CP, comp_RN, comp_RE;

	// 캠핑카 //
	JPanel campingCarPanel;
	JPanel campingCarCompletePanel;
	JButton campingCarCompleteBtn;
	JTextField car_CI, car_CName, car_CNum, car_NP, car_MC, car_MY, car_CDD, car_RC, car_RD, car_comp_CI, car_L;

	// 고객 //
	JPanel customerPanel;
	JPanel customerCompletePanel;
	JButton customerCompleteBtn;
	JTextField cust_LN, cust_CN, cust_CA, cust_CP, cust_CE;

	// 정비소 //
	JPanel repairShopPanel;
	JPanel repairShopCompletePanel;
	JButton repairShopCompleteBtn;
	JTextField shop_SI, shop_SN, shop_SA, shop_SP, shop_RN, shop_RE;

	// 반환 //
	JTextField Return_UN;
	JTextField Return_CI;
	JTextField Return_FP;
	JTextField Return_LP;
	JTextField Return_RP;
	JTextField Return_BP;
	JTextField Return_RR;

	// 정비정보 //
	JPanel maintenanceInfoPanel;
	JPanel maintenanceInfoCompletePanel;
	JButton maintenanceInfoCompleteBtn;
	JTextField info_UMN, info_MH, info_RD, info_RC, info_PD, info_OMI, info_CRSI, info_CI, info_LN;

	JScrollPane mOutputSPane;
	JTextArea mOutputTArea;

	PreparedStatement pstmt, pstmt2;

	// 관리자 기능 버튼 //
	JButton btnGotoManager;

	JButton compInputBtn = new JButton("대여회사 - 입력");
	JButton compDeleteBtn = new JButton("대여회사 - 삭제");
	JButton compModifyBtn = new JButton("대여회사 - 변경");
	JButton carInputBtn = new JButton("캠핑카 - 입력");
	JButton carDeleteBtn = new JButton("캠핑카 - 삭제");
	JButton carModifyBtn = new JButton("캠핑카 - 변경");
	JButton custInputBtn = new JButton("고객 - 입력");
	JButton custDeleteBtn = new JButton("고객 - 삭제");
	JButton custModifyBtn = new JButton("고객 - 변경");
	JButton rShopInputBtn = new JButton("정비소 - 입력");
	JButton rShopDeleteBtn = new JButton("정비소 - 삭제");
	JButton rShopModifyBtn = new JButton("정비소 - 변경");

	JButton infoDeleteBtn = new JButton("캠핑카 정비정보 - 삭제");
	JButton infoModifyBtn = new JButton("캠핑카 정비정보 - 변경");

	JButton returnBtn = new JButton("캠핑카 반환");

	JButton selectBtn1 = new JButton("검색 1");
	JButton selectBtn2 = new JButton("검색 2");
	JButton selectBtn3 = new JButton("검색 3");
	JButton selectBtn4 = new JButton("검색 4");

	// 일반 사용자 기능 버튼 //
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

		super("18011564 변영화, 18011600 이다예");
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

	class newWindow extends JFrame { // 관리자 전용 창

		newWindow() {
			setTitle("관리자 전용");
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

	class newWindow2 extends JFrame { // 일반사용자 전용 창

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

			setTitle("일반사용자 전용");
			setBounds(200, 200, 1500, 700);
			setResizable(false);
			setVisible(true);

			GridLayout g1 = new GridLayout(1, 1); // 프레임 레이아웃 설정
			GridLayout g2 = new GridLayout(2, 1); // 왼쪽 패널 레이아웃 설정

			txtOut = new JTextArea();

			JPanel pnInput;
			JScrollPane pnOutPut = new JScrollPane(txtOut);

			pnInput = new JPanel();

			setLayout(g1);
			add(pnInput);
			add(pnOutPut);

			txtOut.setText("");
			txtOut.setText("출력 : ------------------------------------------\n\n\n\n\n\n\n");
			txtOut.setEditable(false);

			JPanel pnIn1 = new JPanel();
			JPanel pnIn2 = new JPanel();

			pnInput.setLayout(g2);

			pnInput.add(pnIn1);
			pnInput.add(pnIn2);

			txtCar = new JTextField(20);
			txtPNumber = new JTextField(20);
			txtCost = new JTextField(20);

			JButton btnCarSel = new JButton("검색");
			JButton btnPNumberSel = new JButton("검색");
			JButton btnCostSel = new JButton("검색");

			GridLayout g3 = new GridLayout(4, 3, 30, 30);

			pnIn1.setLayout(g3);

			pnIn1.add(new JLabel("<대여가능한 차량 검색>"));
			pnIn1.add(new JLabel(""));
			pnIn1.add(new JLabel(""));

			pnIn1.add(new JLabel("차 이름 : "));
			pnIn1.add(txtCar);
			pnIn1.add(btnCarSel);

			btnCarSel.addActionListener(new ActionListenerCarSel());
			btnPNumberSel.addActionListener(new ActionListenerPNumSel());
			btnCostSel.addActionListener(new ActionListenerCostSel());

			pnIn1.add(new JLabel("최대 승차인원수 : "));
			pnIn1.add(txtPNumber);
			pnIn1.add(btnPNumberSel);

			pnIn1.add(new JLabel("차 대여 비용(입력의 이하로 표기) : "));
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

			JButton btnApp = new JButton("제출");

			GridLayout g4 = new GridLayout(11, 2, 5, 5);
			pnIn2.setLayout(g4);

			pnIn2.add(new JLabel("<차량 대여 신청서>"));
			pnIn2.add(new JLabel(""));

			pnIn2.add(new JLabel("고유대여번호 : "));
			pnIn2.add(txtUniRenNum);

			pnIn2.add(new JLabel("대여시작일 : "));
			pnIn2.add(txtStaDateRen);

			pnIn2.add(new JLabel("대여기간 : "));
			pnIn2.add(txtPeriodRen);

			pnIn2.add(new JLabel("청구요금 : "));
			pnIn2.add(txtCharge);

			pnIn2.add(new JLabel("납입기한 : "));
			pnIn2.add(txtPayDeadline);

			pnIn2.add(new JLabel("기타청구내역 : "));
			pnIn2.add(txtOtherCha);

			pnIn2.add(new JLabel("기타청구요금정보 : "));
			pnIn2.add(txtOtherChaInfo);

			pnIn2.add(new JLabel("캠핑카등록ID : "));
			pnIn2.add(txtCarId);

			pnIn2.add(new JLabel("운전면허증번호 : "));
			pnIn2.add(txtLicNum);

			pnIn2.add(new JLabel(""));
			pnIn2.add(btnApp);

			btnApp.addActionListener(new ActionListenerApp());

		}

		// 일반 사용자 전용 기능 1 - 캠핑카 검색 Action Listener //
		public class ActionListenerCarSel implements ActionListener { // 차이름 찾기

			public void actionPerformed(ActionEvent e) {

				try {

					String caSel = txtCar.getText();

					String sql1 = "select * from camping_car where car_name = ? AND lendable = 1;";

					pstmt = con.prepareStatement(sql1);
					pstmt.setString(1, caSel);

					txtOut.setText("");
					txtOut.setText("차ID" + "\t" + "차이름" + "\t" + "차번호" + "\t" + "승차인원수" + "\t" + "제조회사" + "\t" + "제조년일"
							+ "\t" + "누적주행거리" + "\t" + "대여비용" + "\t" + "대여날짜" + "\t" + "대여회사" + "\t" + "대여가능여부" + "\n");

					rs = pstmt.executeQuery();

					while (rs.next()) {
						String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4)
								+ "\t" + rs.getString(5) + "\t" + rs.getDate(6) + "\t" + rs.getInt(7) + "\t"
								+ rs.getInt(8) + "\t" + rs.getDate(9) + "\t" + rs.getInt(10) + "\t" + rs.getInt(11)
								+ "\n";
						txtOut.append(str);
					}

				} catch (Exception e2) {
					System.out.println("실패 이유 : ");
					System.out.println(e2.getMessage());
				}

			}

		}

		public class ActionListenerPNumSel implements ActionListener { // 승차 인원수 찾기

			public void actionPerformed(ActionEvent e) {

				try {

					String PNumSel = txtPNumber.getText();

					String sql1 = "select * from camping_car where number_of_passengers = ? AND lendable = 1;";

					pstmt = con.prepareStatement(sql1);
					pstmt.setString(1, PNumSel);

					txtOut.setText("");
					txtOut.setText("차ID" + "\t" + "차이름" + "\t" + "차번호" + "\t" + "승차인원수" + "\t" + "제조회사" + "\t" + "제조년일"
							+ "\t" + "누적주행거리" + "\t" + "대여비용" + "\t" + "대여날짜" + "\t" + "대여회사" + "\t" + "대여가능여부" + "\n");

					rs = pstmt.executeQuery();

					while (rs.next()) {
						String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4)
								+ "\t" + rs.getString(5) + "\t" + rs.getDate(6) + "\t" + rs.getInt(7) + "\t"
								+ rs.getInt(8) + "\t" + rs.getDate(9) + "\t" + rs.getInt(10) + "\t" + rs.getInt(11)
								+ "\n";
						txtOut.append(str);
					}

				} catch (Exception e3) {
					System.out.println("실패 이유 : ");
					System.out.println(e3.getMessage());
				}

			}

		}

		public class ActionListenerCostSel implements ActionListener { // 대여비용 찾기

			public void actionPerformed(ActionEvent e) {

				try {

					String CstSel = txtCost.getText();

					String sql1 = "select * from camping_car where rental_cost <= ? AND lendable = 1;";

					pstmt = con.prepareStatement(sql1);
					pstmt.setString(1, CstSel);

					txtOut.setText("");
					txtOut.setText("차ID" + "\t" + "차이름" + "\t" + "차번호" + "\t" + "승차인원수" + "\t" + "제조회사" + "\t" + "제조년일"
							+ "\t" + "누적주행거리" + "\t" + "대여비용" + "\t" + "대여날짜" + "\t" + "대여회사" + "\t" + "대여가능여부" + "\n");

					rs = pstmt.executeQuery();

					while (rs.next()) {
						String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4)
								+ "\t" + rs.getString(5) + "\t" + rs.getDate(6) + "\t" + rs.getInt(7) + "\t"
								+ rs.getInt(8) + "\t" + rs.getDate(9) + "\t" + rs.getInt(10) + "\t" + rs.getInt(11)
								+ "\n";
						txtOut.append(str);
					}

				} catch (Exception e4) {
					System.out.println("실패 이유 : ");
					System.out.println(e4.getMessage());
				}

			}

		}

		// 일반 사용자 전용 기능 2 - 캠핑카 선택 및 대여 Action Listener //
		public class ActionListenerApp implements ActionListener { // 대여 신청서 제출

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

						// 대여가능
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

						System.out.println("대여 신청 성공");
						System.out.println("");

						txtOut.setText("");
						txtOut.setText("고유대여번호" + "\t" + "대여시작일" + "\t" + "대여기간" + "\t" + "대여요금" + "\t" + "납부기한" + "\t"
								+ "기타요금" + "\t" + "기타요금정보" + "\t" + "차ID" + "\t" + "운전면허번호" + "\t" + "회사ID" + "\n");

						String sql2 = "select * from car_loan_application;";
						rs = stmt.executeQuery(sql2);

						while (rs.next()) {
							String str = rs.getInt(1) + "\t" + rs.getDate(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4)
									+ " \t " + rs.getDate(5) + "\t" + rs.getInt(6) + "\t" + rs.getString(7) + "\t"
									+ rs.getInt(8) + "\t" + rs.getInt(9) + "\t" + rs.getInt(10) + "\n";
							txtOut.append(str);
						}

						// 대여 가능(1) -> 대여불가능(0)
						String sql3 = "UPDATE camping_car SET lendable = 0 WHERE car_ID = ?;";

						pstmt = con.prepareStatement(sql3);
						pstmt.setString(1, carid);

						pstmt.executeUpdate();

					} else if (tmp == 0) {
						System.out.println("대여 실패 이유: 이미 대여중인 차 입니다.");
					} else if (tmp == -1) {
						System.out.println("대여 실패 이유 : 찾을 수 없는 car_ID ");
					}

				} catch (Exception e5) {
					System.out.println("대여 실패 이유 : ");
					System.out.println(e5.getMessage());
				}

			}

		}

	}

	public void login() { // 로그인 창(첫 화면)

		loginPanel = new JPanel();
		loginPanel.setLayout(new GridLayout(1, 2, 10, 10));

		btnGotoManager = new JButton("관리자 로그인");
		btnGotoUser = new JButton("일반사용자 로그인");

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

	public void manager() { // 관리자 전용 창

		// initPanel //
		JButton DBInitBtn = new JButton("DB 초기화");
		JButton outputInitBtn = new JButton("출력 초기화");

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
		returnBtn.addActionListener(new ActionListenerReturnCar());// 반납 버튼

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
				" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 출력 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
		mOutputTArea.setEditable(false);

		mOutputSPane = new JScrollPane(mOutputTArea);
		mOutputSPane.setPreferredSize(new Dimension(960, 960));

	}

	// 관리자 전용 기능 1 - 데이터베이스 초기화 //
	private class ActionListenerDBInit implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			int rowcount;

			try {
				System.out.println("");
				System.out.println("DB 초기화 시작");

				stmt = con.createStatement();
				String query;

				// drop
				System.out.println("");
				System.out.println("TABLE DROP 시도...");

				query = "DROP TABLE IF EXISTS car_inspection_details";
				stmt.executeUpdate(query);
				System.out.println("car_inspection_details 테이블 DROP 완료");

				query = "DROP TABLE IF EXISTS car_loan_application";
				stmt.executeUpdate(query);
				System.out.println("car_loan_application 테이블 DROP 완료");

				query = "DROP TABLE IF EXISTS maintenance_information";
				stmt.executeUpdate(query);
				System.out.println("maintenance_information 테이블 DROP 완료");

				query = "DROP TABLE IF EXISTS camping_car";
				stmt.executeUpdate(query);
				System.out.println("camping_car 테이블 DROP 완료");

				query = "DROP TABLE IF EXISTS camping_car_rental_company";
				stmt.executeUpdate(query);
				System.out.println("camping_car_rental_company 테이블 DROP 완료");

				query = "DROP TABLE IF EXISTS customer";
				stmt.executeUpdate(query);
				System.out.println("customer 테이블 DROP 완료");

				query = "DROP TABLE IF EXISTS car_repair_shop";
				stmt.executeUpdate(query);
				System.out.println("car_repair_shop 테이블 DROP 완료");

				System.out.println("TABLE DROP 성공");

				// create
				System.out.println("");
				System.out.println("TABLE CREATE 시도...");

				query = "CREATE TABLE IF NOT EXISTS `camping_car_rental_company` (\r\n"
						+ "  `company_ID` INT NOT NULL,\r\n" + "  `company_name` VARCHAR(45) NULL,\r\n"
						+ "  `company_address` VARCHAR(45) NULL,\r\n" + "  `company_phone` VARCHAR(45) NULL,\r\n"
						+ "  `representative_name` VARCHAR(45) NULL,\r\n"
						+ "  `representative_email` VARCHAR(45) NULL,\r\n" + "  PRIMARY KEY (`company_ID`))\r\n"
						+ "ENGINE = InnoDB;\r\n";
				stmt.executeUpdate(query);
				System.out.println("camping_car_rental_company 테이블 CREATE 완료");

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
				System.out.println("camping_car 테이블 CREATE 완료");

				query = "CREATE TABLE IF NOT EXISTS `customer` (\r\n" + "  `license_number` INT NOT NULL,\r\n"
						+ "  `customer_name` VARCHAR(45) NULL,\r\n" + "  `customer_address` VARCHAR(45) NULL,\r\n"
						+ "  `customer_phone` VARCHAR(45) NULL,\r\n" + "  `customer_email` VARCHAR(45) NULL,\r\n"
						+ "  PRIMARY KEY (`license_number`))\r\n" + "ENGINE = InnoDB;\r\n";
				stmt.executeUpdate(query);
				System.out.println("customer 테이블 CREATE 완료");

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
				System.out.println("car_loan_application 테이블 CREATE 완료");

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
				System.out.println("car_inspection_details 테이블 CREATE 완료");

				query = "CREATE TABLE IF NOT EXISTS `car_repair_shop` (\r\n"
						+ "  `car_repair_shop_ID` INT NOT NULL,\r\n" + "  `repair_shop_name` VARCHAR(45) NULL,\r\n"
						+ "  `repair_shop_address` VARCHAR(45) NULL,\r\n"
						+ "  `repair_shop_phone` VARCHAR(45) NULL,\r\n"
						+ "  `representative_name` VARCHAR(45) NULL,\r\n"
						+ "  `representative_email` VARCHAR(45) NULL,\r\n" + "  PRIMARY KEY (`car_repair_shop_ID`))\r\n"
						+ "ENGINE = InnoDB;\r\n";
				stmt.executeUpdate(query);
				System.out.println("car_repair_shop 테이블 CREATE 완료");

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
				System.out.println("maintenance_information 테이블 CREATE 완료");

				System.out.println("TABLE CREATE 성공");
				System.out.println("");

				// insert - camping_car_rental_company
				System.out.println("INSERT INTO TABLE 시도...");
				System.out.println("");
				rowcount = 0;

				query = "INSERT INTO camping_car_rental_company VALUES(1, 'A 회사', '가 나라 가 도시', '010-0000-0001', '김에이', 'Acompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(2, 'B 회사', '나 나라 나 도시', '010-0000-0002', '김비비', 'Bcompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(3, 'C 회사', '다 나라 다 도시', '010-0000-0003', '김씨씨', 'Ccompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(4, 'D 회사', '라 나라 라 도시', '010-0000-0004', '김디디', 'Dcompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(5, 'E 회사', '마 나라 마 도시', '010-0000-0005', '김이이', 'Ecompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(6, 'F 회사', '바 나라 바 도시', '010-0000-0006', '김에프', 'Fcompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(7, 'G 회사', '사 나라 사 도시', '010-0000-0007', '김쥐쥐', 'Gcompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(8, 'H 회사', '아 나라 아 도시', '010-0000-0008', '김에이치', 'Hcompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(9, 'I 회사', '자 나라 자 도시', '010-0000-0009', '김아이', 'Icompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(10, 'J 회사', '차 나라 차 도시', '010-0000-0010', '김제이', 'Jcompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(11, 'K 회사', '카 나라 카 도시', '010-0000-0011', '김케이', 'Kcompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(12, 'L 회사', '타 나라 타 도시', '010-0000-0012', '김엘엘', 'Lcompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(13, 'M 회사', '파 나라 파 도시', '010-0000-0013', '김엠엠', 'Mcompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(14, 'N 회사', '하 나라 하 도시', '010-0000-0014', '김엔엔', 'Ncompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car_rental_company VALUES(15, 'O 회사', '가 나라 가 도시', '010-0000-0015', '김오오', 'Ocompany@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);

				System.out.println("camping_car_rental_company 테이블에 " + rowcount + "개의 튜플 추가 완료");

				// insert - camping_car
				rowcount = 0;

				query = "INSERT INTO camping_car VALUES(10001, '벤츠A', 1111, 5, '메르세데스벤츠', STR_TO_DATE('2018-01-01','%Y-%m-%d'), 1045, 300000, STR_TO_DATE('2019-01-01','%Y-%m-%d'), 1,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10002, '벤츠B', 2222, 6, '메르세데스벤츠', STR_TO_DATE('2017-12-31','%Y-%m-%d'), 2000, 500000, STR_TO_DATE('2019-01-01','%Y-%m-%d'), 2,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10003, '벤츠C', 3333, 5, '메르세데스벤츠', STR_TO_DATE('2016-01-01','%Y-%m-%d'), 4598, 200000, STR_TO_DATE('2017-01-01','%Y-%m-%d'), 2,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10004, '벤츠D', 4444, 2, '메르세데스벤츠', STR_TO_DATE('2018-02-28','%Y-%m-%d'), 1264, 600000, STR_TO_DATE('2019-01-01','%Y-%m-%d'), 3,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10005, '벤츠F', 5555, 5, '메르세데스벤츠', STR_TO_DATE('2019-01-01','%Y-%m-%d'), 1264, 450000, STR_TO_DATE('2019-12-31','%Y-%m-%d'), 3,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10006, '벤츠G', 6666, 4, '메르세데스벤츠', STR_TO_DATE('2017-01-01','%Y-%m-%d'), 3566, 350000, STR_TO_DATE('2017-02-02','%Y-%m-%d'), 1,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10007, '벤츠A', 7777, 5, '메르세데스벤츠', STR_TO_DATE('2018-01-01','%Y-%m-%d'), 2570, 300000, STR_TO_DATE('2018-03-01','%Y-%m-%d'), 14,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10008, '벤츠B', 8888, 6, '메르세데스벤츠', STR_TO_DATE('2019-01-01','%Y-%m-%d'), 1000, 500000, STR_TO_DATE('2019-03-01','%Y-%m-%d'), 13,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10009, '벤츠F', 9999, 5, '메르세데스벤츠', STR_TO_DATE('2014-01-01','%Y-%m-%d'), 15642, 450000, STR_TO_DATE('2015-03-01','%Y-%m-%d'), 12,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10010, '벤츠A', 1010, 5, '메르세데스벤츠', STR_TO_DATE('2019-01-01','%Y-%m-%d'), 15642, 450000, STR_TO_DATE('2020-01-01','%Y-%m-%d'), 11,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10011, '벤츠D', 11111, 2, '메르세데스벤츠', STR_TO_DATE('2015-01-01','%Y-%m-%d'), 6554, 250000, STR_TO_DATE('2016-01-01','%Y-%m-%d'), 9,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10012, '벤츠C', 11112, 5, '메르세데스벤츠', STR_TO_DATE('2019-01-01','%Y-%m-%d'), 3545, 300000, STR_TO_DATE('2020-01-01','%Y-%m-%d'), 5,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10013, '벤츠A', 11113, 5, '메르세데스벤츠', STR_TO_DATE('2013-01-01','%Y-%m-%d'), 25622, 200000, STR_TO_DATE('2014-01-01','%Y-%m-%d'), 7,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10014, '벤츠G', 11114, 4, '메르세데스벤츠', STR_TO_DATE('2018-01-01','%Y-%m-%d'), 4000, 300000, STR_TO_DATE('2018-12-31','%Y-%m-%d'), 6,1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO camping_car VALUES(10015, '벤츠F', 11115, 5, '메르세데스벤츠', STR_TO_DATE('2019-01-01','%Y-%m-%d'), 5555, 550000, STR_TO_DATE('2019-12-31','%Y-%m-%d'), 4,1)";
				rowcount += stmt.executeUpdate(query);

				System.out.println("camping_car 테이블에 " + rowcount + "개의 튜플 추가 완료");

				// insert - customer
				rowcount = 0;

				query = "INSERT INTO customer VALUES(1234561, 'A고객', '가 나라 가 도시', '010-0001-0001', 'Acustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(1234562, 'B고객', '나 나라 나 도시', '010-0001-0002', 'Bcustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(1234563, 'C고객', '다 나라 다 도시', '010-0001-0003', 'Ccustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(1234564, 'D고객', '라 나라 라 도시', '010-0001-0004', 'Dcustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(1234565, 'E고객', '마 나라 마 도시', '010-0001-0005', 'Ecustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(1234566, 'F고객', '바 나라 바 도시', '010-0001-0006', 'Fcustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(1234567, 'G고객', '사 나라 사 도시', '010-0001-0007', 'Gcustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(1234568, 'H고객', '아 나라 아 도시', '010-0001-0008', 'Hcustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(1234569, 'I고객', '자 나라 자 도시', '010-0001-0009', 'Icustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(2345670, 'J고객', '차 나라 차 도시', '010-0001-0010', 'Jcustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(2345671, 'K고객', '카 나라 카 도시', '010-0001-0011', 'Kcustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(2345672, 'L고객', '타 나라 타 도시', '010-0001-0012', 'Lcustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(2345673, 'M고객', '파 나라 파 도시', '010-0001-0013', 'Mcustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(2345674, 'N고객', '하 나라 하 도시', '010-0001-0014', 'Ncustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO customer VALUES(2345675, 'O고객', '가 나라 가 도시', '010-0001-0015', 'Ocustomer@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);

				System.out.println("customer 테이블에 " + rowcount + "개의 튜플 추가 완료");

				// insert - car_loan_application
				rowcount = 0;

				query = "INSERT INTO car_loan_application VALUES(1, STR_TO_DATE('2020-05-01','%Y-%m-%d'), 15, 600000, STR_TO_DATE('2020-06-01','%Y-%m-%d'), 150000, '수수료', 10001, 1234561, 1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(2, STR_TO_DATE('2020-03-01','%Y-%m-%d'), 15, 700000, STR_TO_DATE('2020-03-31','%Y-%m-%d'), 150000, '수수료', 10002, 1234562, 2)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(3, STR_TO_DATE('2020-04-01','%Y-%m-%d'), 15, 400000, STR_TO_DATE('2020-04-28','%Y-%m-%d'), 150000, '수수료', 10003, 1234563, 2)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(4, STR_TO_DATE('2020-02-01','%Y-%m-%d'), 15, 800000, STR_TO_DATE('2020-02-28','%Y-%m-%d'), 150000, '수수료', 10004, 1234564, 3)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(5, STR_TO_DATE('2020-05-01','%Y-%m-%d'), 15, 600000, STR_TO_DATE('2020-05-31','%Y-%m-%d'), 300000, '수수료+아이용카시트', 10005, 1234565, 3)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(6, STR_TO_DATE('2020-04-01','%Y-%m-%d'), 15, 500000, STR_TO_DATE('2020-04-28','%Y-%m-%d'), 260000, '수수료+프리미엄조리기구', 10006, 1234566, 1)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(7, STR_TO_DATE('2020-05-13','%Y-%m-%d'), 15, 550000, STR_TO_DATE('2020-06-13','%Y-%m-%d'), 300000, '수수료+이벤트용가랜드', 10007, 1234567, 14)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(8, STR_TO_DATE('2020-03-21','%Y-%m-%d'), 15, 900000, STR_TO_DATE('2020-04-21','%Y-%m-%d'), 150000, '수수료', 10008, 1234568, 13)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(9, STR_TO_DATE('2020-01-11','%Y-%m-%d'), 15, 600000, STR_TO_DATE('2020-02-11','%Y-%m-%d'), 150000, '수수료', 10009, 1234568, 12)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(10, STR_TO_DATE('2020-03-15','%Y-%m-%d'), 15, 770000, STR_TO_DATE('2020-04-15','%Y-%m-%d'), 400000, '수수료+프리미엄침대', 10010, 2345670, 11)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(11, STR_TO_DATE('2020-01-20','%Y-%m-%d'), 15, 400000, STR_TO_DATE('2020-02-20','%Y-%m-%d'), 400000, '수수료+프리미엄침대', 10011, 2345671, 9)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(12, STR_TO_DATE('2020-05-06','%Y-%m-%d'), 15, 500000, STR_TO_DATE('2020-06-06','%Y-%m-%d'), 150000, '수수료', 10012, 2345672, 5)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(13, STR_TO_DATE('2020-04-03','%Y-%m-%d'), 15, 400000, STR_TO_DATE('2020-05-03','%Y-%m-%d'), 150000, '수수료', 10013, 2345673, 7)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(14, STR_TO_DATE('2020-01-05','%Y-%m-%d'), 15, 600000, STR_TO_DATE('2020-02-05','%Y-%m-%d'), 500000, '수수료+안전문', 10014, 2345674, 6)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_loan_application VALUES(15, STR_TO_DATE('2020-03-04','%Y-%m-%d'), 15, 900000, STR_TO_DATE('2020-04-04','%Y-%m-%d'), 650000, '수수료+안마의자', 10015, 2345675, 4)";
				rowcount += stmt.executeUpdate(query);

				System.out.println("car_loan_application 테이블에 " + rowcount + "개의 튜플 추가 완료");

				// insert- car_inspection_details
				rowcount = 0;

				query = "INSERT INTO car_inspection_details VALUES('이상 무','이상 무', '이상 무', '이상 무', 0, 1, 10001)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('이상 무','사이드미러 흠집', '이상 무', '이상 무', 1, 2, 10002)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('이상 무','이상 무', '사이드미러 파손', '이상 무', 1, 3, 10003)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('이상 무','이상 무', '이상 무', '이상 무', 0, 4, 10004)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('이상 무','이상 무', '이상 무', '이상 무', 0, 5, 10005)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('이상 무','이상 무', '이상 무', '이상 무', 0, 6, 10006)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('이상 무','이상 무', '이상 무', '이상 무', 0, 7, 10007)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('이상 무','이상 무', '이상 무', '이상 무', 0, 8, 10008)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('이상 무','이상 무', '바퀴 펑크', '이상 무', 1, 9, 10009)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('차 긁힘','이상 무', '이상 무', '이상 무', 1, 10, 10010)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('이상 무','이상 무', '이상 무', '이상 무', 0, 11, 10011)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('이상 무','이상 무', '이상 무', '사다리 훼손', 1, 12, 10012)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('이상 무','이상 무', '이상 무', '이상 무', 0, 13, 10013)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('이상 무','이상 무', '이상 무', '이상 무', 0, 14, 10014)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_inspection_details VALUES('이상 무','이상 무', '이상 무', '이상 무', 0, 15, 10015)";
				rowcount += stmt.executeUpdate(query);

				System.out.println("car_inspection_details 테이블에 " + rowcount + "개의 튜플 추가 완료");

				// insert - car_repair_shop
				rowcount = 0;

				query = "INSERT INTO car_repair_shop VALUES(1, 'A 정비소', '가 나라 가 도시', '010-0002-0001', '이에이', 'Ashop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(2, 'B 정비소', '나 나라 나 도시', '010-0002-0002', '이비비', 'Bshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(3, 'C 정비소', '다 나라 다 도시', '010-0002-0003', '이씨씨', 'Cshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(4, 'D 정비소', '라 나라 라 도시', '010-0002-0004', '이디디', 'Dshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(5, 'E 정비소', '마 나라 마 도시', '010-0002-0005', '이이이', 'Eshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(6, 'F 정비소', '바 나라 바 도시', '010-0002-0006', '이에프', 'Fshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(7, 'G 정비소', '사 나라 사 도시', '010-0002-0007', '이쥐쥐', 'Gshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(8, 'H 정비소', '아 나라 아 도시', '010-0002-0008', '이에이치', 'Hshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(9, 'I 정비소', '자 나라 자 도시', '010-0002-0009', '이아이', 'Ishop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(10, 'J 정비소', '차 나라 차 도시', '010-0002-0010', '이제이', 'Jshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(11, 'K 정비소', '카 나라 카 도시', '010-0002-0011', '이케이', 'Kshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(12, 'L 정비소', '타 나라 타 도시', '010-0002-0012', '이엘엘', 'Lshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(13, 'M 정비소', '파 나라 파 도시', '010-0002-0013', '이엠엠', 'Mshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(14, 'N 정비소', '하 나라 하 도시', '010-0002-0014', '이엔엔', 'Nshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO car_repair_shop VALUES(15, 'O 정비소', '가 나라 가 도시', '010-0002-0015', '이오오', 'Oshop@sejong.ac.kr')";
				rowcount += stmt.executeUpdate(query);

				System.out.println("car_repair_shop 테이블에 " + rowcount + "개의 튜플 추가 완료");

				// insert - maintenance_information
				rowcount = 0;

				query = "INSERT INTO maintenance_information VALUES(1, '사이드미러 흠집', STR_TO_DATE('2001-01-01','%Y-%m-%d'), 50000, STR_TO_DATE('2001-01-10','%Y-%m-%d'), '이상 무', 15, 10001, 1, 1234561)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(2, '차 긁힘', STR_TO_DATE('2020-03-16','%Y-%m-%d'), 60000, STR_TO_DATE('2020-04-16','%Y-%m-%d'), '이상 무', 14, 10002, 2, 1234562)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(3, '사다리 훼손', STR_TO_DATE('2020-04-16','%Y-%m-%d'), 70000, STR_TO_DATE('2020-05-16','%Y-%m-%d'), '이상 무', 13, 10003, 2, 1234563)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(4, '바퀴 펑크', STR_TO_DATE('2001-01-01','%Y-%m-%d'), 25000, STR_TO_DATE('2001-01-10','%Y-%m-%d'), '이상 무', 12, 10004, 3, 1234564)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(5, '화물대 파손', STR_TO_DATE('2001-01-01','%Y-%m-%d'), 100000, STR_TO_DATE('2001-01-10','%Y-%m-%d'), '이상 무', 11, 10005, 3, 1234565)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(6, '창문 파손', STR_TO_DATE('2001-01-01','%Y-%m-%d'), 80000, STR_TO_DATE('2001-01-10','%Y-%m-%d'), '이상 무', 10, 10006, 1, 1234566)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(7, '사다리 훼손', STR_TO_DATE('2001-01-01','%Y-%m-%d'), 100000, STR_TO_DATE('2001-01-10','%Y-%m-%d'), '이상 무', 9, 10007, 14, 1234567)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(8, '차 긁힘', STR_TO_DATE('2001-01-01','%Y-%m-%d'), 150000, STR_TO_DATE('2001-01-10','%Y-%m-%d'), '이상 무', 8, 10008, 13, 1234568)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(9, '차 긁힘', STR_TO_DATE('2020-01-26','%Y-%m-%d'), 90000, STR_TO_DATE('2020-02-26','%Y-%m-%d'), '이상 무', 7, 10009, 12, 1234569)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(10, '사이드미러 파손', STR_TO_DATE('2020-03-30','%Y-%m-%d'), 80000, STR_TO_DATE('2020-04-30','%Y-%m-%d'), '이상 무', 6, 10010, 11, 2345670)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(11, '창문 파손', STR_TO_DATE('2001-01-01','%Y-%m-%d'), 65000, STR_TO_DATE('2001-01-10','%Y-%m-%d'), '이상 무', 5, 10011, 9, 2345671)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(12, '사다리 파손', STR_TO_DATE('2020-05-21','%Y-%m-%d'), 100000, STR_TO_DATE('2020-06-21','%Y-%m-%d'), '이상 무', 4, 10012, 5, 2345672)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(13, '화물대 파손', STR_TO_DATE('2001-01-01','%Y-%m-%d'), 40000, STR_TO_DATE('2001-01-10','%Y-%m-%d'), '이상 무', 3, 10013, 7, 2345673)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(14, '바퀴 펑크', STR_TO_DATE('2001-01-01','%Y-%m-%d'), 60000, STR_TO_DATE('2001-01-10','%Y-%m-%d'), '이상 무', 2, 10014, 6, 2345674)";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO maintenance_information VALUES(15, '차 긁힘', STR_TO_DATE('2001-01-01','%Y-%m-%d'), 50000, STR_TO_DATE('2001-01-10','%Y-%m-%d'), '이상 무', 1, 10015, 4, 2345675)";
				rowcount += stmt.executeUpdate(query);

				System.out.println("maintenance_information 테이블에 " + rowcount + "개의 튜플 추가 완료");

				System.out.println("INSERT INTO TABLE 성공");
				System.out.println("");

				System.out.println("DB 초기화 완료");
				System.out.println("");

			} catch (Exception e6) {
				System.out.println("DB 초기화 실패 : " + e6);
			}

		}

	}

	private class ActionListenerOutputInit implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {
				mOutputTArea.setText(
						" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 출력 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
			} catch (Exception e7) {
				System.out.println("출력 초기화 실패 : " + e7);
			}

		}

	}

	// 관리자 전용 기능 2 - 대여회사 입력 Action Listener //
	private class ActionListenerCompInput implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("대여회사 데이터 입력 시도...");

				frame = new JFrame("대여회사 데이터 입력");
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
				companyCompleteBtn = new JButton("대여회사 테이블에 해당 데이터 추가");
				companyCompletePanel.add(companyCompleteBtn);
				companyCompleteBtn.addActionListener(new ActionListenerCompCompleteInput());

				frame.add(companyPanel, BorderLayout.CENTER);
				frame.add(companyCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 300);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("대여회사 데이터 입력 성공");
				System.out.println("");

			} catch (Exception e8) {
				System.out.println("대여회사 데이터 입력 실패 : " + e8);
			}

		}

	}

	private class ActionListenerCompCompleteInput implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("대여회사 테이블에 데이터 추가 시도...");

				String query = "INSERT INTO camping_car_rental_company VALUES (?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(query);

				pstmt.setInt(1, Integer.parseInt(comp_CI.getText()));
				pstmt.setString(2, comp_CN.getText());
				pstmt.setString(3, comp_CA.getText());
				pstmt.setString(4, comp_CP.getText());
				pstmt.setString(5, comp_RN.getText());
				pstmt.setString(6, comp_RE.getText());
				pstmt.executeUpdate();

				System.out.println("대여회사 테이블에 데이터 추가 성공");

			} catch (Exception e9) {
				System.out.println("대여회사 테이블에 데이터 추가 실패 : " + e9);
			}

			try {

				System.out.println("");
				System.out.println("대여회사 테이블 출력 시도...");

				mOutputTArea.setText("");
				mOutputTArea.setText("대여회사 ID" + "\t\t" + "회사명" + "\t\t" + "주소" + "\t\t" + "전화번호" + "\t\t" + "담당자 이름"
						+ "\t\t" + "담당자 이메일" + "\n");

				String query2 = "select * from camping_car_rental_company";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
							+ rs.getString(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("대여회사 테이블 출력 성공");

			} catch (Exception e10) {
				System.out.println("대여회사 테이블 출력 실패 : " + e10);
			}

		}

	}

	// 관리자 전용 기능 3 - 대여회사 삭제 Action Listener //
	private class ActionListenerCompDelete implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("대여회사에서 삭제하려는 데이터 입력 시도...");

				frame = new JFrame("삭제하려는 대여회사의 데이터 입력");
				frame.setLayout(new BorderLayout());

				companyPanel = new JPanel();
				companyPanel.setLayout(new GridLayout(1, 2, 5, 5));

				comp_CI = new JTextField("");

				companyPanel.add(new JLabel("삭제하려는 대여회사의 Company ID"));
				companyPanel.add(comp_CI);

				companyCompletePanel = new JPanel();
				companyCompleteBtn = new JButton("해당 회사의 데이터 삭제");
				companyCompletePanel.add(companyCompleteBtn);
				companyCompleteBtn.addActionListener(new ActionListenerCompCompleteDelete());

				frame.add(companyPanel, BorderLayout.CENTER);
				frame.add(companyCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 150);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("대여회사에서 삭제하려는 데이터 입력 성공");
				System.out.println("");

			} catch (Exception e11) {
				System.out.println("대여회사에서 삭제하려는 데이터 입력 실패 : " + e11);
			}

		}

	}

	private class ActionListenerCompCompleteDelete implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("대여회사 테이블에서 데이터 삭제 시도...");

				String query = "DELETE FROM camping_car_rental_company WHERE company_ID=?";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, Integer.parseInt(comp_CI.getText()));
				pstmt.executeUpdate();

				System.out.println("대여회사 테이블에서 데이터 삭제 성공");
				System.out.println("");

			} catch (Exception e12) {
				System.out.println("대여회사 테이블에서 데이터 삭제 실패 : " + e12);
			}

			try {

				System.out.println("");
				System.out.println("해당 데이터 삭제한 대여회사 테이블 출력 시도...");

				mOutputTArea.setText("");
				mOutputTArea.setText("대여회사 ID" + "\t\t" + "회사명" + "\t\t" + "주소" + "\t\t" + "전화번호" + "\t\t" + "담당자 이름"
						+ "\t\t" + "담당자 이메일" + "\n");

				String query2 = "select * from camping_car_rental_company";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
							+ rs.getString(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("해당 데이터 삭제한 대여회사 테이블 출력 성공");
				System.out.println("");

			} catch (Exception e13) {
				System.out.println("해당 데이터 삭제한 대여회사 테이블 출력 실패 : " + e13);
			}

		}

	}

	// 관리자 전용 기능 4 - 대여회사 변경 Action Listener //
	private class ActionListenerCompModify implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("대여회사에서 변경하려는 데이터 입력 시도...");

				frame = new JFrame("변경하려는 대여회사의 데이터 입력");
				frame.setLayout(new BorderLayout());

				companyPanel = new JPanel();
				companyPanel.setLayout(new GridLayout(6, 2, 5, 5));

				comp_CI = new JTextField("");
				comp_CN = new JTextField("");
				comp_CA = new JTextField("");
				comp_CP = new JTextField("");
				comp_RN = new JTextField("");
				comp_RE = new JTextField("");

				companyPanel.add(new JLabel("변경하려는 회사의 Company ID"));
				companyPanel.add(comp_CI);
				companyPanel.add(new JLabel("Company Name 변경 내용"));
				companyPanel.add(comp_CN);
				companyPanel.add(new JLabel("Company Address 변경 내용"));
				companyPanel.add(comp_CA);
				companyPanel.add(new JLabel("Company Phone 변경 내용"));
				companyPanel.add(comp_CP);
				companyPanel.add(new JLabel("Representative Name 변경 내용"));
				companyPanel.add(comp_RN);
				companyPanel.add(new JLabel("Representative Email 변경 내용"));
				companyPanel.add(comp_RE);

				companyCompletePanel = new JPanel();
				companyCompleteBtn = new JButton("해당 회사의 데이터 변경");
				companyCompletePanel.add(companyCompleteBtn);
				companyCompleteBtn.addActionListener(new ActionListenerCompCompleteModify());

				frame.add(companyPanel, BorderLayout.CENTER);
				frame.add(companyCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 300);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("대여회사에서 변경하려는 데이터 입력 성공");
				System.out.println("");

			} catch (Exception e14) {
				System.out.println("대여회사에서 변경하려는 데이터 입력 실패 : " + e14);
			}

		}

	}

	private class ActionListenerCompCompleteModify implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("대여회사 테이블에서 데이터 변경 시도...");

				String query = "UPDATE camping_car_rental_company SET company_name=?, company_address=?, company_phone=?, representative_name=?, representative_email=? WHERE company_ID=?";
				pstmt = con.prepareStatement(query);

				pstmt.setString(1, comp_CN.getText());
				pstmt.setString(2, comp_CA.getText());
				pstmt.setString(3, comp_CP.getText());
				pstmt.setString(4, comp_RN.getText());
				pstmt.setString(5, comp_RE.getText());
				pstmt.setInt(6, Integer.parseInt(comp_CI.getText()));
				pstmt.executeUpdate();

				System.out.println("대여회사 테이블에서 데이터 변경 성공");

			} catch (Exception e15) {
				System.out.println("대여회사 테이블에서 데이터 변경 실패 : " + e15);
			}

			try {

				System.out.println("");
				System.out.println("변경된 대여회사 테이블 출력 시도...");

				mOutputTArea.setText("");
				mOutputTArea.setText("대여회사 ID" + "\t\t" + "회사명" + "\t\t" + "주소" + "\t\t" + "전화번호" + "\t\t" + "담당자 이름"
						+ "\t\t" + "담당자 이메일" + "\n");

				String query2 = "select * from camping_car_rental_company";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
							+ rs.getString(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("변경된 대여회사 테이블 출력 성공");

			} catch (Exception e16) {
				System.out.println("변경된 대여회사 테이블 출력 실패 : " + e16);
			}

		}

	}

	// 관리자 전용 기능 5 - 캠핑카 입력 Action Listener //
	private class ActionListenerCarInput implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("캠핑카 데이터 입력 시도...");

				frame = new JFrame("캠핑카 데이터 입력");
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
				campingCarCompleteBtn = new JButton("캠핑카 테이블에 해당 데이터 추가");
				campingCarCompletePanel.add(campingCarCompleteBtn);
				campingCarCompleteBtn.addActionListener(new ActionListenerCarCompleteInput());

				frame.add(campingCarPanel, BorderLayout.CENTER);
				frame.add(campingCarCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 500);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("캠핑카 데이터 입력 성공");
				System.out.println("");

			} catch (Exception e17) {
				System.out.println("캠핑카 데이터 입력 실패 : " + e17);
			}

		}

	}

	private class ActionListenerCarCompleteInput implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("캠핑카 테이블에 데이터 추가 시도...");

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

				System.out.println("캠핑카 테이블에 데이터 추가 성공");

			} catch (Exception e18) {
				System.out.println("캠핑카 테이블에 데이터 추가 실패 : " + e18);
			}

			try {

				System.out.println("");
				System.out.println("캠핑카 테이블 출력 시도...");

				mOutputTArea.setText("");
				mOutputTArea.setText("캠핑카 ID" + "\t" + "캠핑카 이름" + "\t" + "차량번호" + "\t" + "승차 인원수" + "\t" + "제조회사" + "\t"
						+ "제조연도" + "\t" + "누적주행거리" + "\t" + "대여비용" + "\t" + "등록일자" + "\t" + "대여회사 ID" + "\t"
						+ "대여 가능 여부" + "\n");

				String query2 = "select * from camping_car";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4)
							+ "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getInt(7) + "\t"
							+ rs.getInt(8) + "\t" + rs.getString(9) + "\t" + rs.getInt(10) + "\t" + rs.getInt(11)
							+ "\n";
					mOutputTArea.append(str);
				}

				System.out.println("캠핑카 테이블 출력 성공");

			} catch (Exception e19) {
				System.out.println("캠핑카 테이블 출력 실패 : " + e19);
			}

		}

	}

	// 관리자 전용 기능 6 - 캠핑카 삭제 Action Listener //
	private class ActionListenerCarDelete implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("캠핑카에서 삭제하려는 데이터 입력 시도...");

				frame = new JFrame("삭제하려는 캠핑카의 데이터 입력");
				frame.setLayout(new BorderLayout());

				campingCarPanel = new JPanel();
				campingCarPanel.setLayout(new GridLayout(1, 2, 5, 5));

				car_CI = new JTextField("");

				campingCarPanel.add(new JLabel("Car ID"));
				campingCarPanel.add(car_CI);

				campingCarCompletePanel = new JPanel();
				campingCarCompleteBtn = new JButton("해당 캠핑카의 데이터 삭제");
				campingCarCompletePanel.add(campingCarCompleteBtn);
				campingCarCompleteBtn.addActionListener(new ActionListenerCarCompleteDelete());

				frame.add(campingCarPanel, BorderLayout.CENTER);
				frame.add(campingCarCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 150);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("캠핑카에서 삭제하려는 데이터 입력 성공");
				System.out.println("");

			} catch (Exception e20) {
				System.out.println("캠핑카에서 삭제하려는 데이터 입력 실패 : " + e20);
			}

		}

	}

	private class ActionListenerCarCompleteDelete implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("캠핑카 테이블에서 데이터 삭제 시도...");

				String query = "DELETE FROM camping_car WHERE car_ID=?";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, Integer.parseInt(car_CI.getText()));
				pstmt.executeUpdate();

				System.out.println("캠핑카 테이블에서 데이터 삭제 성공");
				System.out.println("");

			} catch (Exception e21) {
				System.out.println("캠핑카 테이블에서 데이터 삭제 실패 : " + e21);
			}

			try {

				System.out.println("");
				System.out.println("해당 데이터 삭제한 캠핑카 테이블 출력 시도...");

				mOutputTArea.setText("");
				mOutputTArea.setText("캠핑카 ID" + "\t" + "캠핑카 이름" + "\t" + "차량번호" + "\t" + "승차 인원수" + "\t" + "제조회사" + "\t"
						+ "제조연도" + "\t" + "누적주행거리" + "\t" + "대여비용" + "\t" + "등록일자" + "\t" + "대여회사 ID" + "\t"
						+ "대여 가능 여부" + "\n");

				String query2 = "select * from camping_car";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4)
							+ "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getInt(7) + "\t"
							+ rs.getInt(8) + "\t" + rs.getString(9) + "\t" + rs.getInt(10) + "\t" + rs.getInt(11)
							+ "\n";
					mOutputTArea.append(str);
				}

				System.out.println("해당 데이터 삭제한 캠핑카 테이블 출력 성공");

			} catch (Exception e22) {
				System.out.println("해당 데이터 삭제한 캠핑카 테이블 출력 실패 : " + e22);
			}

		}

	}

	// 관리자 전용 기능 7 - 캠핑카 변경 Action Listener //
	private class ActionListenerCarModify implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("캠핑카에서 변경하려는 데이터 입력 시도...");

				frame = new JFrame("변경하려는 캠핑카의 데이터 입력");
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

				campingCarPanel.add(new JLabel("변경하려는 캠핑카의 Car ID"));
				campingCarPanel.add(car_CI);
				campingCarPanel.add(new JLabel("Car Name 변경 내용"));
				campingCarPanel.add(car_CName);
				campingCarPanel.add(new JLabel("Car Number 변경 내용"));
				campingCarPanel.add(car_CNum);
				campingCarPanel.add(new JLabel("Number Of Passengers 변경 내용"));
				campingCarPanel.add(car_NP);
				campingCarPanel.add(new JLabel("Manufacturing Company 변경 내용"));
				campingCarPanel.add(car_MC);
				campingCarPanel.add(new JLabel("Manufacturing Year 변경 내용"));
				campingCarPanel.add(car_MY);
				campingCarPanel.add(new JLabel("Cumulative Driving Distance 변경 내용"));
				campingCarPanel.add(car_CDD);
				campingCarPanel.add(new JLabel("Rental Cost 변경 내용"));
				campingCarPanel.add(car_RC);
				campingCarPanel.add(new JLabel("Register Date 변경 내용"));
				campingCarPanel.add(car_RD);
				campingCarPanel.add(new JLabel("Company ID 변경 내용"));
				campingCarPanel.add(car_comp_CI);
				campingCarPanel.add(new JLabel("Lendable 변경 내용"));
				campingCarPanel.add(car_L);

				campingCarCompletePanel = new JPanel();
				campingCarCompleteBtn = new JButton("해당 캠핑카의 데이터 변경");
				campingCarCompletePanel.add(campingCarCompleteBtn);
				campingCarCompleteBtn.addActionListener(new ActionListenerCarCompleteModify());

				frame.add(campingCarPanel, BorderLayout.CENTER);
				frame.add(campingCarCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 500);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("캠핑카에서 변경하려는 데이터 입력 성공");
				System.out.println("");

			} catch (Exception e23) {
				System.out.println("캠핑카에서 변경하려는 데이터 입력 실패 : " + e23);
			}

		}

	}

	private class ActionListenerCarCompleteModify implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("캠핑카 테이블에서 데이터 변경 시도...");

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

				System.out.println("캠핑카 테이블에서 데이터 변경 성공");

			} catch (Exception e24) {
				System.out.println("캠핑카 테이블에서 데이터 변경 실패 : " + e24);
			}

			try {

				System.out.println("");
				System.out.println("변경된 캠핑카 테이블 출력 시도...");

				mOutputTArea.setText("");
				mOutputTArea.setText("캠핑카 ID" + "\t" + "캠핑카 이름" + "\t" + "차량번호" + "\t" + "승차 인원수" + "\t" + "제조회사" + "\t"
						+ "제조연도" + "\t" + "누적주행거리" + "\t" + "대여비용" + "\t" + "등록일자" + "\t" + "대여회사 ID" + "\t"
						+ "대여 가능 여부" + "\n");

				String query2 = "select * from camping_car";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4)
							+ "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getInt(7) + "\t"
							+ rs.getInt(8) + "\t" + rs.getString(9) + "\t" + rs.getInt(10) + "\t" + rs.getInt(11)
							+ "\n";
					mOutputTArea.append(str);
				}

				System.out.println("변경된 캠핑카 테이블 출력 성공");

			} catch (Exception e25) {
				System.out.println("변경된 캠핑카 테이블 출력 실패 : " + e25);
			}

		}

	}

	// 관리자 전용 기능 8 - 고객 입력 Action Listener //
	private class ActionListenerCustInput implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("고객 데이터 입력 시도...");

				frame = new JFrame("고객 데이터 입력");
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
				customerCompleteBtn = new JButton("고객 테이블에 해당 데이터 추가");
				customerCompletePanel.add(customerCompleteBtn);
				customerCompleteBtn.addActionListener(new ActionListenerCustCompleteInput());

				frame.add(customerPanel, BorderLayout.CENTER);
				frame.add(customerCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 300);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("고객 데이터 입력 성공");
				System.out.println("");

			} catch (Exception e26) {
				System.out.println("고객 데이터 입력 실패 : " + e26);
			}

		}

	}

	private class ActionListenerCustCompleteInput implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("고객 테이블에 데이터 추가 시도...");

				String query = "INSERT INTO customer VALUES (?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(query);

				pstmt.setInt(1, Integer.parseInt(cust_LN.getText()));
				pstmt.setString(2, cust_CN.getText());
				pstmt.setString(3, cust_CA.getText());
				pstmt.setString(4, cust_CP.getText());
				pstmt.setString(5, cust_CE.getText());
				pstmt.executeUpdate();

				System.out.println("고객 테이블에 데이터 추가 성공");

			} catch (Exception e27) {
				System.out.println("고객 테이블에 데이터 추가 실패 : " + e27);
			}

			try {

				System.out.println("");
				System.out.println("고객 테이블 출력 시도...");

				mOutputTArea.setText("");
				mOutputTArea
						.setText("운전면허증 번호" + "\t\t" + "이름" + "\t\t" + "주소" + "\t\t" + "전화번호" + "\t\t" + "이메일" + "\n");

				String query2 = "select * from customer";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
							+ rs.getString(4) + "\t\t" + rs.getString(5) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("고객 테이블 출력 성공");

			} catch (Exception e28) {
				System.out.println("고객 테이블 출력 실패 : " + e28);
			}

		}

	}

	// 관리자 전용 기능 9 - 고객 삭제 Action Listener //
	private class ActionListenerCustDelete implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("고객에서 삭제하려는 데이터 입력 시도...");

				frame = new JFrame("삭제하려는 고객의 데이터 입력");
				frame.setLayout(new BorderLayout());

				customerPanel = new JPanel();
				customerPanel.setLayout(new GridLayout(1, 2, 5, 5));

				cust_LN = new JTextField("");

				customerPanel.add(new JLabel("삭제하려는 고객의 License Number"));
				customerPanel.add(cust_LN);

				customerCompletePanel = new JPanel();
				customerCompleteBtn = new JButton("해당 고객의 데이터 삭제");
				customerCompletePanel.add(customerCompleteBtn);
				customerCompleteBtn.addActionListener(new ActionListenerCustCompleteDelete());

				frame.add(customerPanel, BorderLayout.CENTER);
				frame.add(customerCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 150);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("고객에서 삭제하려는 데이터 입력 성공");
				System.out.println("");

			} catch (Exception e29) {
				System.out.println("고객에서 삭제하려는 데이터 입력 실패 : " + e29);
			}

		}

	}

	private class ActionListenerCustCompleteDelete implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("고객 테이블에서 데이터 삭제 시도...");

				String query = "DELETE FROM customer WHERE license_number=?";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, Integer.parseInt(cust_LN.getText()));
				pstmt.executeUpdate();

				System.out.println("고객 테이블에서 데이터 삭제 성공");
				System.out.println("");

			} catch (Exception e30) {
				System.out.println("고객 테이블에서 데이터 삭제 실패 : " + e30);
			}

			try {

				System.out.println("");
				System.out.println("해당 데이터 삭제한 고객 테이블 출력 시도...");

				mOutputTArea.setText("");
				mOutputTArea
						.setText("운전면허증 번호" + "\t\t" + "이름" + "\t\t" + "주소" + "\t\t" + "전화번호" + "\t\t" + "이메일" + "\n");

				String query2 = "select * from customer";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
							+ rs.getString(4) + "\t\t" + rs.getString(5) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("해당 데이터 삭제한 고객 테이블 출력 성공");

			} catch (Exception e31) {
				System.out.println("해당 데이터 삭제한 고객 테이블 출력 실패 : " + e31);
			}

		}

	}

	// 관리자 전용 기능 10 - 고객 변경 Action Listener //
	private class ActionListenerCustModify implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("고객에서 변경하려는 데이터 입력 시도...");

				frame = new JFrame("변경하려는 고객의 데이터 입력");
				frame.setLayout(new BorderLayout());

				customerPanel = new JPanel();
				customerPanel.setLayout(new GridLayout(5, 2, 5, 5));

				cust_LN = new JTextField("");
				cust_CN = new JTextField("");
				cust_CA = new JTextField("");
				cust_CP = new JTextField("");
				cust_CE = new JTextField("");

				customerPanel.add(new JLabel("변경하려는 고객의 License Number"));
				customerPanel.add(cust_LN);
				customerPanel.add(new JLabel("Customer Name 변경 내용"));
				customerPanel.add(cust_CN);
				customerPanel.add(new JLabel("Customer Address 변경 내용"));
				customerPanel.add(cust_CA);
				customerPanel.add(new JLabel("Customer Phone 변경 내용"));
				customerPanel.add(cust_CP);
				customerPanel.add(new JLabel("Customer Email 변경 내용"));
				customerPanel.add(cust_CE);

				customerCompletePanel = new JPanel();
				customerCompleteBtn = new JButton("해당 고객의 데이터 변경");
				customerCompletePanel.add(customerCompleteBtn);
				customerCompleteBtn.addActionListener(new ActionListenerCustCompleteModify());

				frame.add(customerPanel, BorderLayout.CENTER);
				frame.add(customerCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 300);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("고객에서 변경하려는 데이터 입력 성공");
				System.out.println("");

			} catch (Exception e32) {
				System.out.println("고객에서 변경하려는 데이터 입력 실패 : " + e32);
			}

		}

	}

	private class ActionListenerCustCompleteModify implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("고객 테이블에서 데이터 변경 시도...");

				String query = "UPDATE customer SET customer_name=?, customer_address=?, customer_phone=?, customer_email=? WHERE license_number=?";
				pstmt = con.prepareStatement(query);

				pstmt.setString(1, cust_CN.getText());
				pstmt.setString(2, cust_CA.getText());
				pstmt.setString(3, cust_CP.getText());
				pstmt.setString(4, cust_CE.getText());
				pstmt.setInt(5, Integer.parseInt(cust_LN.getText()));
				pstmt.executeUpdate();

				System.out.println("고객 테이블에서 데이터 변경 성공");

			} catch (Exception e33) {
				System.out.println("고객 테이블에서 데이터 변경 실패 : " + e33);
			}

			try {

				System.out.println("");
				System.out.println("변경된 고객 테이블 출력 시도...");

				mOutputTArea.setText("");
				mOutputTArea
						.setText("운전면허증 번호" + "\t\t" + "이름" + "\t\t" + "주소" + "\t\t" + "전화번호" + "\t\t" + "이메일" + "\n");

				String query2 = "select * from customer";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
							+ rs.getString(4) + "\t\t" + rs.getString(5) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("변경된 고객 테이블 출력 성공");

			} catch (Exception e34) {
				System.out.println("변경된 고객 테이블 출력 실패 : " + e34);
			}

		}

	}

	// 관리자 전용 기능 11 - 정비소 입력 Action Listener //
	private class ActionListenerShopInput implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("정비소 데이터 입력 시도...");

				frame = new JFrame("정비소 데이터 입력");
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
				repairShopCompleteBtn = new JButton("정비소 테이블에 해당 데이터 추가");
				repairShopCompletePanel.add(repairShopCompleteBtn);
				repairShopCompleteBtn.addActionListener(new ActionListenerShopComPleteInput());

				frame.add(repairShopPanel, BorderLayout.CENTER);
				frame.add(repairShopCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 300);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("정비소 데이터 입력 성공");
				System.out.println("");

			} catch (Exception e35) {
				System.out.println("정비소 데이터 입력 실패 : " + e35);
			}

		}

	}

	private class ActionListenerShopComPleteInput implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("정비소 테이블에 데이터 추가 시도...");

				String query = "INSERT INTO car_repair_shop VALUES (?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(query);

				pstmt.setInt(1, Integer.parseInt(shop_SI.getText()));
				pstmt.setString(2, shop_SN.getText());
				pstmt.setString(3, shop_SA.getText());
				pstmt.setString(4, shop_SP.getText());
				pstmt.setString(5, shop_RN.getText());
				pstmt.setString(6, shop_RE.getText());
				pstmt.executeUpdate();

				System.out.println("정비소 테이블에 데이터 추가 성공");

			} catch (Exception e36) {
				System.out.println("정비소 테이블에 데이터 추가 실패 : " + e36);
			}

			try {

				System.out.println("");
				System.out.println("정비소 테이블 출력 시도...");

				mOutputTArea.setText("");
				mOutputTArea.setText("정비소 ID" + "\t\t" + "이름" + "\t\t" + "주소" + "\t\t" + "전화번호" + "\t\t" + "담당자 이름"
						+ "\t\t" + "담당자 이메일" + "\n");

				String query2 = "select * from car_repair_shop";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
							+ rs.getString(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("정비소 테이블 출력 성공");

			} catch (Exception e37) {
				System.out.println("정비소 테이블 출력 실패 : " + e37);
			}

		}

	}

	// 관리자 전용 기능 12 - 정비소 삭제 Action Listener //
	private class ActionListenerShopDelete implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("정비소에서 삭제하려는 데이터 입력 시도...");

				frame = new JFrame("삭제하려는 정비소의 데이터 입력");
				frame.setLayout(new BorderLayout());

				repairShopPanel = new JPanel();
				repairShopPanel.setLayout(new GridLayout(1, 2, 5, 5));

				shop_SI = new JTextField("");

				repairShopPanel.add(new JLabel("삭제하려는 정비소의 Car Repair Shop ID"));
				repairShopPanel.add(shop_SI);

				repairShopCompletePanel = new JPanel();
				repairShopCompleteBtn = new JButton("해당 정비소의 데이터 삭제");
				repairShopCompletePanel.add(repairShopCompleteBtn);
				repairShopCompleteBtn.addActionListener(new ActionListenerShopCompleteDelete());

				frame.add(repairShopPanel, BorderLayout.CENTER);
				frame.add(repairShopCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 150);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("정비소에서 삭제하려는 데이터 입력 성공");
				System.out.println("");

			} catch (Exception e38) {
				System.out.println("정비소에서 삭제하려는 데이터 입력 실패 : " + e38);
			}

		}

	}

	private class ActionListenerShopCompleteDelete implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("정비소 테이블에서 데이터 삭제 시도...");

				String query = "DELETE FROM car_repair_shop WHERE car_repair_shop_ID=?";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, Integer.parseInt(shop_SI.getText()));
				pstmt.executeUpdate();

				System.out.println("정비소 테이블에서 데이터 삭제 성공");
				System.out.println("");

			} catch (Exception e39) {
				System.out.println("정비소 테이블에서 데이터 삭제 실패 : " + e39);
			}

			try {

				System.out.println("");
				System.out.println("해당 데이터 삭제한 정비소 테이블 출력 시도...");

				mOutputTArea.setText("");
				mOutputTArea.setText("정비소 ID" + "\t\t" + "이름" + "\t\t" + "주소" + "\t\t" + "전화번호" + "\t\t" + "담당자 이름"
						+ "\t\t" + "담당자 이메일" + "\n");

				String query2 = "select * from car_repair_shop";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
							+ rs.getString(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("해당 데이터 삭제한 정비소 테이블 출력 성공");

			} catch (Exception e40) {
				System.out.println("해당 데이터 삭제한 정비소 테이블 출력 실패 : " + e40);
			}

		}

	}

	// 관리자 전용 기능 13 - 정비소 변경 Action Listener //
	private class ActionListenerShopModify implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("정비소에서 변경하려는 데이터 입력 시도...");

				frame = new JFrame("변경하려는 정비소의 데이터 입력");
				frame.setLayout(new BorderLayout());

				repairShopPanel = new JPanel();
				repairShopPanel.setLayout(new GridLayout(6, 2, 5, 5));

				shop_SI = new JTextField("");
				shop_SN = new JTextField("");
				shop_SA = new JTextField("");
				shop_SP = new JTextField("");
				shop_RN = new JTextField("");
				shop_RE = new JTextField("");

				repairShopPanel.add(new JLabel("변경하려는 정비소의 Car Repair Shop ID"));
				repairShopPanel.add(shop_SI);
				repairShopPanel.add(new JLabel("Repair Shop Name 변경 내용"));
				repairShopPanel.add(shop_SN);
				repairShopPanel.add(new JLabel("Repair Shop Address 변경 내용"));
				repairShopPanel.add(shop_SA);
				repairShopPanel.add(new JLabel("Repair Shop Phone 변경 내용"));
				repairShopPanel.add(shop_SP);
				repairShopPanel.add(new JLabel("Representative Name 변경 내용"));
				repairShopPanel.add(shop_RN);
				repairShopPanel.add(new JLabel("Representative Email 변경 내용"));
				repairShopPanel.add(shop_RE);

				repairShopCompletePanel = new JPanel();
				repairShopCompleteBtn = new JButton("해당 정비소의 데이터 변경");
				repairShopCompletePanel.add(repairShopCompleteBtn);
				repairShopCompleteBtn.addActionListener(new ActionListenerShopCompleteModify());

				frame.add(repairShopPanel, BorderLayout.CENTER);
				frame.add(repairShopCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 300);
				frame.setVisible(true);
				frame.setLocation(150, 500);

				System.out.println("정비소에서 변경하려는 데이터 입력 성공");
				System.out.println("");

			} catch (Exception e41) {
				System.out.println("정비소에서 변경하려는 데이터 입력 실패 : " + e41);
			}

		}

	}

	private class ActionListenerShopCompleteModify implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("정비소 테이블에서 데이터 변경 시도...");

				String query = "UPDATE car_repair_shop SET repair_shop_name=?, repair_shop_address=?, repair_shop_phone=?, representative_name=?, representative_email=? WHERE car_repair_shop_ID=?";
				pstmt = con.prepareStatement(query);

				pstmt.setString(1, shop_SN.getText());
				pstmt.setString(2, shop_SA.getText());
				pstmt.setString(3, shop_SP.getText());
				pstmt.setString(4, shop_RN.getText());
				pstmt.setString(5, shop_RE.getText());
				pstmt.setInt(6, Integer.parseInt(shop_SI.getText()));
				pstmt.executeUpdate();

				System.out.println("정비소 테이블에서 데이터 변경 성공");

			} catch (Exception e42) {
				System.out.println("정비소 테이블에서 데이터 변경 실패 : " + e42);
			}

			try {

				System.out.println("");
				System.out.println("변경된 정비소 테이블 출력 시도...");

				mOutputTArea.setText("");
				mOutputTArea.setText("정비소 ID" + "\t\t" + "이름" + "\t\t" + "주소" + "\t\t" + "전화번호" + "\t\t" + "담당자 이름"
						+ "\t\t" + "담당자 이메일" + "\n");

				String query2 = "select * from car_repair_shop";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t\t" + rs.getString(2) + "\t\t" + rs.getString(3) + "\t\t"
							+ rs.getString(4) + "\t\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("변경된 정비소 테이블 출력 성공");

			} catch (Exception e43) {
				System.out.println("변경된 정비소 테이블 출력 실패 : " + e43);
			}

		}

	}

	// 관리자 전용 기능 14 - 캠핑카 반환 Action Listener //
	private class ActionListenerReturnCar implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				JFrame ReturnInputFrame = new JFrame("캠핑카 반환창");
				JButton btnReCar = new JButton("반환");

				Return_UN = new JTextField(20);
				Return_CI = new JTextField(20);
				Return_FP = new JTextField(20);
				Return_LP = new JTextField(20);
				Return_RP = new JTextField(20);
				Return_BP = new JTextField(20);
				Return_RR = new JTextField(20);

				GridLayout gridReturnCar = new GridLayout(9, 2, 10, 10);

				ReturnInputFrame.setLayout(gridReturnCar);

				ReturnInputFrame.add(new JLabel("<캠핑카 정검내역>"));
				ReturnInputFrame.add(new JLabel(""));

				ReturnInputFrame.add(new JLabel("고유대여번호 : "));
				ReturnInputFrame.add(Return_UN);

				ReturnInputFrame.add(new JLabel("캠핑카등록ID : "));
				ReturnInputFrame.add(Return_CI);

				ReturnInputFrame.add(new JLabel("앞부분 설명 : "));
				ReturnInputFrame.add(Return_FP);

				ReturnInputFrame.add(new JLabel("왼쪽 설명 : "));
				ReturnInputFrame.add(Return_LP);

				ReturnInputFrame.add(new JLabel("오른쪽 설명 : "));
				ReturnInputFrame.add(Return_RP);

				ReturnInputFrame.add(new JLabel("뒤쪽 설명 : "));
				ReturnInputFrame.add(Return_BP);

				ReturnInputFrame.add(new JLabel("수리필요 여부 : "));
				ReturnInputFrame.add(Return_RR);

				ReturnInputFrame.add(new JLabel(""));
				ReturnInputFrame.add(btnReCar);

				btnReCar.addActionListener(new ActionListenerRetBtn());

				ReturnInputFrame.setSize(500, 500);
				ReturnInputFrame.setVisible(true);
				ReturnInputFrame.setLocation(150, 200);

			} catch (Exception e44) {
				System.out.println("실패 이유: ");
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

						System.out.println("car_ID : " + recar + ", 반환완료");
						System.out.println("");
					} else if (tmplen == 1)
						System.out.println("오류 : 이미 반납된 차량입니다");
					else if (tmplen == -1)
						System.out.println("오류 : 없는 차 ID입니다.");
				} else if (rprq_i == 1) {

					try {

						System.out.println("정비정보 데이터 입력 시도...");

						frame = new JFrame("정비정보 데이터 입력");
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
						maintenanceInfoCompleteBtn = new JButton("정비정보 테이블에 해당 데이터 추가");
						maintenanceInfoCompletePanel.add(maintenanceInfoCompleteBtn);
						maintenanceInfoCompleteBtn.addActionListener(new ActionListenerMInfoCompleteInput());

						frame.add(maintenanceInfoPanel, BorderLayout.CENTER);
						frame.add(maintenanceInfoCompletePanel, BorderLayout.SOUTH);

						frame.setSize(500, 500);
						frame.setVisible(true);
						frame.setLocation(150, 100);

						System.out.println("정비정보 데이터 입력 성공");
						System.out.println("");

					} catch (Exception e45) {
						System.out.println("정비정보 데이터 입력 실패 : " + e45);
					}

				}

			} catch (Exception e46) {

				System.out.println("실패 이유: ");
				System.out.println(e46.getMessage());
			}

		}

	}

	// 관리자 전용 기능 15 - 수리가 필요할 경우 정비정보 입력 Action Listener //
	private class ActionListenerMInfoCompleteInput implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("정비정보 테이블에 데이터 추가 시도...");

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

				System.out.println("정비정보 테이블에 데이터 추가 성공");

			} catch (Exception e47) {
				System.out.println("정비정보 테이블에 데이터 추가 실패 : " + e47);
			}

			try {

				System.out.println("");
				System.out.println("정비정보 테이블 출력 시도...");

				mOutputTArea.setText("");
				mOutputTArea.setText("고유정비번호" + "\t" + "정비내역" + "\t" + "수리날짜" + "\t" + "수리비용" + "\t" + "납입기한" + "\t"
						+ "기타정비내역" + "\t" + "정비소ID" + "\t" + "캠핑카등록ID" + "\t" + "대여회사ID" + "\t" + "고객 운전면허증번호" + "\n");

				String query2 = "select * from maintenance_information";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4)
							+ "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getInt(7) + "\t"
							+ rs.getInt(8) + "\t" + rs.getInt(9) + "\t" + rs.getInt(10) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("정비정보 테이블 출력 성공");

			} catch (Exception e48) {
				System.out.println("정비정보 테이블 출력 실패 : " + e48);
			}

		}

	}

	// 관리자 전용 기능 16 - 정비정보 삭제 Action Listener //
	private class ActionListenerMInfoDelete implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("정비정보에서 삭제하려는 데이터 입력 시도...");

				frame = new JFrame("삭제하려는 정비정보의 데이터 입력");
				frame.setLayout(new BorderLayout());

				maintenanceInfoPanel = new JPanel();
				maintenanceInfoPanel.setLayout(new GridLayout(1, 2, 5, 5));

				info_UMN = new JTextField("");

				maintenanceInfoPanel.add(new JLabel("삭제하려는 정비정보의 Unique Maintenance Number"));
				maintenanceInfoPanel.add(info_UMN);

				maintenanceInfoCompletePanel = new JPanel();
				maintenanceInfoCompleteBtn = new JButton("해당 정비정보의 데이터 삭제");
				maintenanceInfoCompletePanel.add(maintenanceInfoCompleteBtn);
				maintenanceInfoCompleteBtn.addActionListener(new ActionListenerMInfoCompleteDelete());

				frame.add(maintenanceInfoPanel, BorderLayout.CENTER);
				frame.add(maintenanceInfoCompletePanel, BorderLayout.SOUTH);

				frame.setSize(700, 150);
				frame.setVisible(true);
				frame.setLocation(40, 400);

				System.out.println("정비정보에서 삭제하려는 데이터 입력 성공");
				System.out.println("");

			} catch (Exception e49) {
				System.out.println("정비정보에서 삭제하려는 데이터 입력 실패 : " + e49);
			}

		}

	}

	private class ActionListenerMInfoCompleteDelete implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("정비정보 테이블에서 데이터 삭제 시도...");

				String query = "DELETE FROM maintenance_information WHERE uniqe_maintenance_number=?";
				pstmt = con.prepareStatement(query);
				pstmt.setInt(1, Integer.parseInt(info_UMN.getText()));
				pstmt.executeUpdate();

				System.out.println("정비정보 테이블에서 데이터 삭제 성공");
				System.out.println("");

			} catch (Exception e50) {
				System.out.println("정비정보 테이블에서 데이터 삭제 실패 : " + e50);
			}

			try {

				System.out.println("");
				System.out.println("해당 데이터 삭제한 정비정보 테이블 출력 시도...");

				mOutputTArea.setText("");
				mOutputTArea.setText("고유정비번호" + "\t" + "정비내역" + "\t" + "수리날짜" + "\t" + "수리비용" + "\t" + "납입기한" + "\t"
						+ "기타정비내역" + "\t" + "정비소ID" + "\t" + "캠핑카등록ID" + "\t" + "대여회사ID" + "\t" + "고객 운전면허증번호" + "\n");

				String query2 = "select * from maintenance_information";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4)
							+ "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getInt(7) + "\t"
							+ rs.getInt(8) + "\t" + rs.getInt(9) + "\t" + rs.getInt(10) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("해당 데이터 삭제한 정비정보 테이블 출력 성공");

			} catch (Exception e51) {
				System.out.println("해당 데이터 삭제한 정비정보 테이블 출력 실패 : " + e51);
			}

		}

	}

	// 관리자 전용 기능 17 - 정비정보 변경 Action Listener //
	private class ActionListenerMInfoModify implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("정비정보에서 변경하려는 데이터 입력 시도...");

				frame = new JFrame("변경하려는 정비정보의 데이터 입력");
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

				maintenanceInfoPanel.add(new JLabel("변경하려는 정비정보의 Unique Maintenance Number"));
				maintenanceInfoPanel.add(info_UMN);
				maintenanceInfoPanel.add(new JLabel("Maintenance History 변경 내용"));
				maintenanceInfoPanel.add(info_MH);
				maintenanceInfoPanel.add(new JLabel("Repair Date 변경 내용"));
				maintenanceInfoPanel.add(info_RD);
				maintenanceInfoPanel.add(new JLabel("Repair Charge 변경 내용"));
				maintenanceInfoPanel.add(info_RC);
				maintenanceInfoPanel.add(new JLabel("Payment Deadline 변경 내용"));
				maintenanceInfoPanel.add(info_PD);
				maintenanceInfoPanel.add(new JLabel("Other Maintenance Info 변경 내용"));
				maintenanceInfoPanel.add(info_OMI);
				maintenanceInfoPanel.add(new JLabel("Car Repair Shop ID 변경 내용"));
				maintenanceInfoPanel.add(info_CRSI);
				maintenanceInfoPanel.add(new JLabel("Camping Car ID 변경 내용"));
				maintenanceInfoPanel.add(info_CI);
				maintenanceInfoPanel.add(new JLabel("Customer License Number 변경 내용"));
				maintenanceInfoPanel.add(info_LN);

				maintenanceInfoCompletePanel = new JPanel();
				maintenanceInfoCompleteBtn = new JButton("해당 정비정보의 데이터 변경");
				maintenanceInfoCompletePanel.add(maintenanceInfoCompleteBtn);
				maintenanceInfoCompleteBtn.addActionListener(new ActionListenerMInfoCompleteModify());

				frame.add(maintenanceInfoPanel, BorderLayout.CENTER);
				frame.add(maintenanceInfoCompletePanel, BorderLayout.SOUTH);

				frame.setSize(500, 500);
				frame.setVisible(true);
				frame.setLocation(150, 100);

				System.out.println("정비정보에서 변경하려는 데이터 입력 성공");
				System.out.println("");

			} catch (Exception e52) {
				System.out.println("정비소에서 변경하려는 데이터 입력 실패 : " + e52);
			}

		}

	}

	private class ActionListenerMInfoCompleteModify implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("정비정보 테이블에서 데이터 변경 시도...");

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

				System.out.println("정비정보 테이블에서 데이터 변경 성공");

			} catch (Exception e53) {
				System.out.println("정비정보 테이블에서 데이터 변경 실패 : " + e53);
			}

			try {

				System.out.println("");
				System.out.println("변경된 정비정보 테이블 출력 시도...");

				mOutputTArea.setText("");
				mOutputTArea.setText("고유정비번호" + "\t" + "정비내역" + "\t" + "수리날짜" + "\t" + "수리비용" + "\t" + "납입기한" + "\t"
						+ "기타정비내역" + "\t" + "정비소ID" + "\t" + "캠핑카등록ID" + "\t" + "대여회사ID" + "\t" + "고객 운전면허증번호" + "\n");

				String query2 = "select * from maintenance_information";
				rs = stmt.executeQuery(query2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getInt(4)
							+ "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getInt(7) + "\t"
							+ rs.getInt(8) + "\t" + rs.getInt(9) + "\t" + rs.getInt(10) + "\n";
					mOutputTArea.append(str);
				}

				System.out.println("변경된 정비정보 테이블 출력 성공");

			} catch (Exception e54) {
				System.out.println("변경된 정비정보 테이블 출력 실패 : " + e54);
			}

		}

	}

	// 관리자 전용 기능 18 - 검색 1 Action Listener //
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
				mOutputTArea.append("< 검색 1 - 캠핑카 ID와 캠핑카 이름에 대한 총 청구요금 >" + "\n\n");

				mOutputTArea.append("캠핑카 ID" + "\t" + "캠핑카 이름" + "\t" + "총 청구요금\n\n");
				rs = stmt.executeQuery(sqlSel1);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\n";
					mOutputTArea.append(str);
				}

			} catch (Exception e55) {
				System.out.println("실패 이유: ");
				System.out.println(e55.getMessage());

			}

		}

	}

	// 관리자 전용 기능 19 - 검색 2 Action Listener //
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
				mOutputTArea.append("< 검색 2 - 운전면허증번호와 고객 이름에 대한 총 청구요금 >\n\n");

				mOutputTArea.append("면허증 번호" + "\t" + "고객 이름" + "\t" + "총 청구요금\n\n");
				rs = stmt.executeQuery(sqlSel2);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\n";
					mOutputTArea.append(str);
				}

			} catch (Exception e56) {
				System.out.println("실패 이유: ");
				System.out.println(e56.getMessage());
			}

		}

	}

	// 관리자 전용 기능 20 - 검색 3 Action Listener //
	private class ActionListenerSelBtn3 implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				stmt = con.createStatement();

				String sqlSel3 = "select cs.customer_name '고객이름' , count(mi.uniqe_maintenance_number) '총 수리횟수' , sum(mi.repair_charge) '총 수리요금'\r\n"
						+ "from customer cs, maintenance_information mi\r\n"
						+ "where cs.license_number = mi.customer_license_number\r\n"
						+ "group by mi.customer_license_number;";

				mOutputTArea.setText("");
				mOutputTArea.append("< 검색 3 - 고객별 총 수리 횟수를 표시하고, 수리의 총 청구요금을 출력 >\n\n");

				mOutputTArea.append("고객 이름" + "\t" + "수리 횟수" + "\t" + "총 청구요금\n\n");
				rs = stmt.executeQuery(sqlSel3);

				while (rs.next()) {
					String str = rs.getString(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\n";
					mOutputTArea.append(str);
				}

			} catch (Exception e57) {
				System.out.println("실패 이유: ");
				System.out.println(e57.getMessage());
			}

		}

	}

	// 관리자 전용 기능 21 - 검색 4 Action Listener //
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
				mOutputTArea.append("< 검색 4 - 회사ID와 회사명에 대한 총 수리요금  >\n\n");

				mOutputTArea.append("회사 ID" + "\t" + "회사명" + "\t" + "총 수리요금\n\n");
				rs = stmt.executeQuery(sqlSel4);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\n";
					mOutputTArea.append(str);
				}

			} catch (Exception e58) {
				System.out.println("실패 이유: ");
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
				System.out.println("프로그램 완전 종료!");
				System.exit(0);

			}

		});

	}

}