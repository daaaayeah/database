import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Hospital extends JFrame {

	// main window
	JButton btnInit, btnInput, btnSelect1, btnSelect2, btnSelect3;
	JPanel mainBtnPanel;
	JTextArea outputTArea;
	JScrollPane outputSPane;

	// input
	JButton inputDoctors, inputNurses, inputPatients, inputTreatments, inputCharts;

	// input Doctors window
	JTextField doc_DI, doc_MT, doc_DN, doc_DG, doc_DPH, doc_DE, doc_DPO;
	// input Nurses window
	JTextField nur_NI, nur_MJ, nur_NN, nur_NG, nur_NPH, nur_NE, nur_NPO;
	// input Patients window
	JTextField pat_PI, pat_NI, pat_DI, pat_PN, pat_PG, pat_PJU, pat_PA, pat_PP, pat_PE, pat_PJO;
	// input Treatments window
	JTextField treat_TI, treat_PI, treat_DI, treat_TC, treat_TD;
	// input Charts window
	JTextField chart_CI, chart_TI, char_DI, char_PI, char_NI, char_CC;

	// select
	JButton selectDoctors, selectNurses, selectPatients, selectTreatments, selectCharts;
	JPanel selectBtnPanel;
	JTextArea selOutputTArea;
	JScrollPane selOutputSPane;

	JFrame frame;
	JPanel inputPanel;
	JPanel completeInputPanel;
	JButton completeInputBtn;

	static Connection con;
	Statement stmt;
	PreparedStatement pstmt;
	String query;
	ResultSet rs;
	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/madang?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "madang";
	String pwd = "madang";

	public Hospital() {

		super("18011600/이다예");
		conDB();
		mainWindow();
		setVisible(true);
		setBounds(200, 200, 1000, 500);
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
			System.out.println("");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	public void mainWindow() {

		btnInit = new JButton("초기화");
		btnInput = new JButton("입력");
		btnSelect1 = new JButton("검색 1");
		btnSelect2 = new JButton("검색 2");
		btnSelect3 = new JButton("검색 3");

		mainBtnPanel = new JPanel();
		mainBtnPanel.setLayout(new GridLayout(1, 5));

		outputTArea = new JTextArea();
		outputTArea.setEditable(false);
		outputSPane = new JScrollPane(outputTArea);

		mainBtnPanel.add(btnInit);
		mainBtnPanel.add(btnInput);
		mainBtnPanel.add(btnSelect1);
		mainBtnPanel.add(btnSelect2);
		mainBtnPanel.add(btnSelect3);

		add("North", mainBtnPanel);
		add("Center", outputSPane);

		btnInit.addActionListener(new ActionListenerInit());
		btnInput.addActionListener(new ActionListenerInput());
		btnSelect1.addActionListener(new ActionListenerSelect1());
		btnSelect2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {

					stmt = con.createStatement();

					query = "SELECT pat_gen, COUNT(*) 'count'\r\n" + "FROM patients\r\n"
							+ "WHERE Doctors_doc_id IN (SELECT doc_id FROM doctors WHERE doc_position LIKE '전문의')\r\n"
							+ "GROUP BY pat_gen;";

					outputTArea.setText("\n\n\n\n");
					outputTArea.append(
							"\t\t\t - - - - - - - - - - 검색 2 - - - - - - - - - - - - - - - - - - - -- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n");
					outputTArea.append("\t\t\t < 전문의에게 할당 받은 남성과 여성 환자의 수 >\n\n");
					outputTArea.append("\t\t\t SELECT pat_gen, COUNT(*) 'count'\r\n" + "\t\t\t FROM patients\r\n"
							+ "\t\t\t WHERE Doctors_doc_id\n"
							+ "\t\t\t IN (SELECT doc_id FROM doctors WHERE doc_position LIKE '전문의')\r\n"
							+ "\t\t\t GROUP BY pat_gen;\n");
					outputTArea.append(
							"\t\t\t - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n\n");

					outputTArea.append("\t\t\t 환자 성별" + "\t" + "전문의에게 할당 받은 환자의 수" + "\n\n\t\t\t ");
					rs = stmt.executeQuery(query);

					while (rs.next()) {
						String str = rs.getString(1) + "\t" + rs.getString(2) + "\n\t\t\t ";
						outputTArea.append(str);
					}

				} catch (Exception e1) {
					System.out.println("검색 2 실패 : " + e1);
				}

			}

		});
		btnSelect3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {

					stmt = con.createStatement();

					query = "SELECT Treatments_Patients_pat_id, COUNT(*) 'count'\r\n" + "FROM charts\r\n"
							+ "WHERE Nurses_nur_id\r\n"
							+ "IN (SELECT Nurses_nur_id FROM patients WHERE Doctors_doc_id\r\n"
							+ "IN (SELECT doc_id FROM doctors WHERE doc_position LIKE '전문의'))\r\n"
							+ "GROUP BY Treatments_Patients_pat_id;";

					outputTArea.setText("\n");
					outputTArea.append(
							"\t\t\t - - - - - - - - - - 검색 3 - - - - - - - - - - - - - - - - - - - -- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n");
					outputTArea.append("\t\t\t < 환자가 '환자에게 전문의와 함께 할당된 적 있는 간호사'에게 진료 받은 횟수 >\n\n");
					outputTArea.append("\t\t\t SELECT Treatments_Patients_pat_id, COUNT(*) 'count'\r\n"
							+ "\t\t\t FROM charts\r\n" + "\t\t\t WHERE Nurses_nur_id\r\n"
							+ "\t\t\t IN (SELECT Nurses_nur_id FROM patients WHERE Doctors_doc_id\r\n"
							+ "\t\t\t IN (SELECT doc_id FROM doctors WHERE doc_position LIKE '전문의'))\r\n"
							+ "\t\t\t GROUP BY Treatments_Patients_pat_id;\n");
					outputTArea.append(
							"\t\t\t - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n\n");

					outputTArea.append("\t\t\t 환자 ID" + "\t" + "횟수" + "\n\n\t\t\t ");
					rs = stmt.executeQuery(query);

					while (rs.next()) {
						String str = rs.getString(1) + "\t" + rs.getString(2) + "\n\t\t\t ";
						outputTArea.append(str);
					}

				} catch (Exception e1) {
					System.out.println("검색 3 실패 : " + e1);
				}

			}

		});

	}

	// 초기화 Action Listener
	private class ActionListenerInit implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			int rowcount;

			try {
				System.out.println("초기화 시작 - - - - - - - - - - - - - - -");

				stmt = con.createStatement();

				// drop
				query = "DROP TABLE IF EXISTS charts";
				stmt.executeUpdate(query);
				query = "DROP TABLE IF EXISTS treatments";
				stmt.executeUpdate(query);
				query = "DROP TABLE IF EXISTS patients";
				stmt.executeUpdate(query);
				query = "DROP TABLE IF EXISTS nurses";
				stmt.executeUpdate(query);
				query = "DROP TABLE IF EXISTS doctors";
				stmt.executeUpdate(query);
				System.out.println("5개의 테이블 DROP 완료");

				// create
				query = "CREATE TABLE IF NOT EXISTS `Doctors` (\r\n" + "  `doc_id` INT NOT NULL,\r\n"
						+ "  `major_treat` VARCHAR(25) NOT NULL,\r\n" + "  `doc_name` VARCHAR(20) NOT NULL,\r\n"
						+ "  `doc_gen` CHAR(1) NOT NULL,\r\n" + "  `doc_phone` VARCHAR(15) NULL,\r\n"
						+ "  `doc_email` VARCHAR(50) NULL,\r\n" + "  `doc_position` VARCHAR(20) NOT NULL,\r\n"
						+ "  PRIMARY KEY (`doc_id`),\r\n" + "  UNIQUE INDEX `doc_id_UNIQUE` (`doc_id` ASC) VISIBLE,\r\n"
						+ "  UNIQUE INDEX `doc_email_UNIQUE` (`doc_email` ASC) VISIBLE)\r\n" + "ENGINE = InnoDB;";
				stmt.executeUpdate(query);

				query = "CREATE TABLE IF NOT EXISTS `Nurses` (\r\n" + "  `nur_id` INT NOT NULL,\r\n"
						+ "  `major_job` VARCHAR(25) NOT NULL,\r\n" + "  `nur_name` VARCHAR(20) NOT NULL,\r\n"
						+ "  `nur_gen` CHAR(1) NOT NULL,\r\n" + "  `nur_phone` VARCHAR(15) NULL,\r\n"
						+ "  `nur_email` VARCHAR(50) NULL,\r\n" + "  `nur_position` VARCHAR(20) NOT NULL,\r\n"
						+ "  PRIMARY KEY (`nur_id`),\r\n" + "  UNIQUE INDEX `nur_id_UNIQUE` (`nur_id` ASC) VISIBLE,\r\n"
						+ "  UNIQUE INDEX `nur_email_UNIQUE` (`nur_email` ASC) VISIBLE)\r\n" + "ENGINE = InnoDB;";
				stmt.executeUpdate(query);

				query = "CREATE TABLE IF NOT EXISTS `Patients` (\r\n" + "  `pat_id` INT NOT NULL,\r\n"
						+ "  `Nurses_nur_id` INT NOT NULL,\r\n" + "  `Doctors_doc_id` INT NOT NULL,\r\n"
						+ "  `pat_name` VARCHAR(20) NOT NULL,\r\n" + "  `pat_gen` VARCHAR(1) NOT NULL,\r\n"
						+ "  `pat_jumin` VARCHAR(14) NOT NULL,\r\n" + "  `pat_addr` VARCHAR(100) NOT NULL,\r\n"
						+ "  `pat_phone` VARCHAR(15) NULL,\r\n" + "  `pat_email` VARCHAR(50) NULL,\r\n"
						+ "  `pat_job` VARCHAR(20) NOT NULL,\r\n" + "  PRIMARY KEY (`pat_id`),\r\n"
						+ "  INDEX `fk_Patients_Doctors_idx` (`Doctors_doc_id` ASC) VISIBLE,\r\n"
						+ "  INDEX `fk_Patients_Nurses1_idx` (`Nurses_nur_id` ASC) VISIBLE,\r\n"
						+ "  UNIQUE INDEX `pat_id_UNIQUE` (`pat_id` ASC) VISIBLE,\r\n"
						+ "  UNIQUE INDEX `pat_email_UNIQUE` (`pat_email` ASC) VISIBLE,\r\n"
						+ "  CONSTRAINT `fk_Patients_Doctors`\r\n" + "    FOREIGN KEY (`Doctors_doc_id`)\r\n"
						+ "    REFERENCES `Doctors` (`doc_id`)\r\n" + "    ON DELETE NO ACTION\r\n"
						+ "    ON UPDATE NO ACTION,\r\n" + "  CONSTRAINT `fk_Patients_Nurses1`\r\n"
						+ "    FOREIGN KEY (`Nurses_nur_id`)\r\n" + "    REFERENCES `Nurses` (`nur_id`)\r\n"
						+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION)\r\n" + "ENGINE = InnoDB;";
				stmt.executeUpdate(query);

				query = "CREATE TABLE IF NOT EXISTS `Treatments` (\r\n" + "  `treat_id` INT NOT NULL,\r\n"
						+ "  `Patients_pat_id` INT NOT NULL,\r\n" + "  `Doctors_doc_id` INT NOT NULL,\r\n"
						+ "  `treat_contents` VARCHAR(1000) NOT NULL,\r\n" + "  `treat_date` DATE NOT NULL,\r\n"
						+ "  PRIMARY KEY (`treat_id`, `Patients_pat_id`, `Doctors_doc_id`),\r\n"
						+ "  INDEX `fk_Treatments_Doctors1_idx` (`Doctors_doc_id` ASC) VISIBLE,\r\n"
						+ "  INDEX `fk_Treatments_Patients1_idx` (`Patients_pat_id` ASC) VISIBLE,\r\n"
						+ "  UNIQUE INDEX `treat_id_UNIQUE` (`treat_id` ASC) VISIBLE,\r\n"
						+ "  CONSTRAINT `fk_Treatments_Doctors1`\r\n" + "    FOREIGN KEY (`Doctors_doc_id`)\r\n"
						+ "    REFERENCES `Doctors` (`doc_id`)\r\n" + "    ON DELETE NO ACTION\r\n"
						+ "    ON UPDATE NO ACTION,\r\n" + "  CONSTRAINT `fk_Treatments_Patients1`\r\n"
						+ "    FOREIGN KEY (`Patients_pat_id`)\r\n" + "    REFERENCES `Patients` (`pat_id`)\r\n"
						+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION)\r\n" + "ENGINE = InnoDB;";
				stmt.executeUpdate(query);

				query = "CREATE TABLE IF NOT EXISTS `Charts` (\r\n" + "  `chart_id` VARCHAR(20) NOT NULL,\r\n"
						+ "  `Treatments_treat_id` INT NOT NULL,\r\n"
						+ "  `Treatments_Doctors_doc_id` INT NOT NULL,\r\n"
						+ "  `Treatments_Patients_pat_id` INT NOT NULL,\r\n" + "  `Nurses_nur_id` INT NOT NULL,\r\n"
						+ "  `chart_contents` VARCHAR(1000) NOT NULL,\r\n"
						+ "  PRIMARY KEY (`chart_id`, `Treatments_treat_id`, `Treatments_Doctors_doc_id`, `Treatments_Patients_pat_id`),\r\n"
						+ "  INDEX `fk_Charts_Treatments1_idx` (`Treatments_treat_id` ASC, `Treatments_Patients_pat_id` ASC, `Treatments_Doctors_doc_id` ASC) VISIBLE,\r\n"
						+ "  INDEX `fk_Charts_Nurses1_idx` (`Nurses_nur_id` ASC) VISIBLE,\r\n"
						+ "  UNIQUE INDEX `chart_id_UNIQUE` (`chart_id` ASC) VISIBLE,\r\n"
						+ "  CONSTRAINT `fk_Charts_Treatments1`\r\n"
						+ "    FOREIGN KEY (`Treatments_treat_id` , `Treatments_Patients_pat_id` , `Treatments_Doctors_doc_id`)\r\n"
						+ "    REFERENCES `Treatments` (`treat_id` , `Patients_pat_id` , `Doctors_doc_id`)\r\n"
						+ "    ON DELETE NO ACTION\r\n" + "    ON UPDATE NO ACTION,\r\n"
						+ "  CONSTRAINT `fk_Charts_Nurses1`\r\n" + "    FOREIGN KEY (`Nurses_nur_id`)\r\n"
						+ "    REFERENCES `Nurses` (`nur_id`)\r\n" + "    ON DELETE NO ACTION\r\n"
						+ "    ON UPDATE NO ACTION)\r\n" + "ENGINE = InnoDB;";
				stmt.executeUpdate(query);

				System.out.println("5개의 테이블 CREATE 완료");

				// insert - doctors
				// doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position
				rowcount = 0;

				query = "INSERT INTO doctors VALUES(980312, '소아과', '이태정', 'M', '010-333-1340', 'ltj@hanbh.com', '과장')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(000601, '내과', '안성기', 'M', '011-222-0987', 'ask@hanbh.com', '과장')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(001208, '외과', '김민종', 'M', '010-333-8743', 'kmj@hanbh.com', '과장')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(020403, '피부과', '이태서', 'M', '019-777-3764', 'lts@hanbh.com', '과장')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(050900, '소아과', '김연아', 'F', '010-555-3746', 'kya@hanbh.com', '전문의')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(050101, '내과', '차태현', 'M', '011-222-7643', 'cth@hanbh.com', '전문의')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(062019, '소아과', '전지현', 'F', '010-999-1265', 'jjh@hanbh.com', '전문의')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(070576, '피부과', '홍길동', 'M', '016-333-7263', 'hgd@hanbh.com', '전문의')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(080543, '방사선과', '유재석', 'M', '010-222-1263', 'yjs@hanbh.com', '과장')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(091001, '외과', '김병만', 'M', '010-555-3542', 'kbm@hanbh.com', '전문의')";
				rowcount += stmt.executeUpdate(query);

				query = "INSERT INTO doctors VALUES(987601, '내과', '김하나', 'F', '010-123-0001', 'khn@hanbh.com', '전문의')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(987602, '외과', '김둘둘', 'F', '010-123-0002', 'kdd@hanbh.com', '전문의')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(987603, '외과', '김셋셋', 'F', '010-123-0003', 'kss@hanbh.com', '전문의')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(987604, '방사선과', '김넷넷', 'F', '010-123-0004', 'knn@hanbh.com', '전문의')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(987605, '소아과', '김다섯', 'M', '010-123-0005', 'kds@hanbh.com', '전문의')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(987606, '피부과', '김여섯', 'F', '010-123-0006', 'kys@hanbh.com', '전문의')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(987607, '방사선과', '김일곱', 'M', '010-123-0007', 'kig@hanbh.com', '전문의')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(987608, '외과', '김여덟', 'F', '010-123-0008', 'kyd@hanbh.com', '전문의')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(987609, '외과', '김아홉', 'F', '010-123-0009', 'kah@hanbh.com', '전문의')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(987610, '내과', '김열열', 'F', '010-123-0010', 'kyy@hanbh.com', '전문의')";
				rowcount += stmt.executeUpdate(query);

				System.out.println("doctors 테이블에 " + rowcount + "개의 튜플 INSERT 완료");

				// insert - nurses
				// nur_id, major_job, nur_name, nur_gen, nur_email, nur_position
				rowcount = 0;

				query = "INSERT INTO nurses VALUES(050302, '소아과', '김은영', 'F', '010-555-8751', 'key@hanbh.com', '수간호사')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(050021, '내과', '윤성애', 'F', '016-333-8745', 'ysa@hanbh.com', '수간호사')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(040089, '피부과', '신지원', 'M', '010-666-7646', 'sjw@hanbh.com', '주임')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(070605, '방사선과', '유정화', 'F', '010-333-4588', 'yjh@hanbh.com', '주임')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(070804, '내과', '라하나', 'F', '010-222-1340', 'nhn@hanbh.com', '주임')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(071018, '소아과', '김화경', 'F', '019-888-4116', 'khk@hanbh.com', '주임')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(100356, '소아과', '이선용', 'M', '010-777-1234', 'lsy@hanbh.com', '간호사')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(104145, '외과', '김현', 'M', '010-999-8520', 'kh@hanbh.com', '간호사')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(120309, '피부과', '박성완', 'M', '010-777-4996', 'psw@hanbh.com', '간호사')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(130211, '외과', '이서연', 'F', '010-222-3214', 'lsy2@hanbh.com', '간호사')";
				rowcount += stmt.executeUpdate(query);

				query = "INSERT INTO nurses VALUES(432101, '외과', '이하나', 'M', '010-321-0001', 'lhn@hanbh.com', '주임')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(432102, '피부과', '이둘둘', 'F', '010-321-0002', 'ldd@hanbh.com', '간호사')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(432103, '외과', '이셋셋', 'M', '010-321-0003', 'lss@hanbh.com', '간호사')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(432104, '소아과', '이넷넷', 'F', '010-321-0004', 'lnn@hanbh.com', '간호사')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(432105, '소아과', '이다섯', 'M', '010-321-0005', 'lds@hanbh.com', '간호사')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(432106, '내과', '이여섯', 'F', '010-321-0006', 'lys@hanbh.com', '간호사')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(432107, '방사선과', '이일곱', 'M', '010-321-0007', 'lig@hanbh.com', '간호사')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(432108, '피부과', '이여덟', 'F', '010-321-0008', 'lyd@hanbh.com', '간호사')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(432109, '내과', '이아홉', 'M', '010-321-0009', 'lah@hanbh.com', '간호사')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(432110, '소아과', '이열열', 'F', '010-321-0010', 'lyy@hanbh.com', '간호사')";
				rowcount += stmt.executeUpdate(query);

				System.out.println("nurses 테이블에 " + rowcount + "개의 튜플 INSERT 완료");

				// insert - patients
				// pat_id, Nurses_nur_id, Doctors_nur_id, pat_name, pat_gen, pat_gen, pat_jumin,
				// pat_addr, pat_phone, pat_email, pat_job
				rowcount = 0;

				query = "INSERT INTO patients VALUES(2345, 050302, 980312, '안상건', 'M', '232345', '서울', '010-555-7845', 'ask@ab.com', '회사원')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(3545, 040089, 020403, '김성룡', 'M', '543545', '서울', '010-333-7812', 'ksr@ab.com', '자영업')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(3424, 070605, 080543, '이종진', 'M', '433424', '부산', '019-888-4859', 'ljj@ab.com', '회사원')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(7675, 100356, 050900, '최광석', 'M', '677675', '당진', '010-222-4847', 'cks@ab.com', '회사원')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(4533, 070804, 000601, '정한경', 'M', '744533', '강릉', '010-777-9630', 'jhk@ab.com', '교수')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(5546, 120309, 070576, '유원현', 'M', '765546', '대구', '016-777-0214', 'ywh@ab.com', '자영업')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(4543, 070804, 050101, '최재정', 'M', '454543', '부산', '010-555-4187', 'cjj@ab.com', '회사원')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(9768, 130211, 091001, '이진희', 'F', '119768', '서울', '010-888-3675', 'ljh@ab.com', '교수')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(4234, 130211, 091001, '오나미', 'F', '234234', '속초', '010-999-6541', 'onm@ab.com', '학생')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(7643, 071018, 062019, '송성묵', 'M', '987643', '서울', '010-222-5874', 'ssm@ab.com', '학생')";
				rowcount += stmt.executeUpdate(query);

				query = "INSERT INTO patients VALUES(9901, 432101, 987602, '환하나', 'F', '000001', '서울', '010-456-0001', 'hhn@ab.com', '학생')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(9902, 432102, 987606, '환둘둘', 'M', '000002', '서울', '010-456-0002', 'hdd@ab.com', '학생')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(9903, 432103, 987603, '환셋셋', 'F', '000003', '서울', '010-456-0003', 'hss@ab.com', '학생')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(9904, 432104, 987605, '환넷넷', 'M', '000004', '서울', '010-456-0004', 'hnn@ab.com', '학생')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(9905, 432105, 987605, '환다섯', 'F', '000005', '서울', '010-456-0005', 'hds@ab.com', '학생')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(9906, 432106, 987601, '환여섯', 'M', '000006', '서울', '010-456-0006', 'hys@ab.com', '학생')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(9907, 432107, 987607, '환일곱', 'F', '000007', '서울', '010-456-0007', 'hig@ab.com', '학생')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(9908, 432108, 987606, '환여덟', 'M', '000008', '서울', '010-456-0008', 'hyd@ab.com', '학생')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(9909, 432109, 987610, '환아홉', 'F', '000009', '서울', '010-456-0009', 'hah@ab.com', '학생')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(9910, 432110, 987605, '환열열', 'M', '000010', '서울', '010-456-0010', 'hyy@ab.com', '학생')";
				rowcount += stmt.executeUpdate(query);

				System.out.println("patients 테이블에 " + rowcount + "개의 튜플 INSERT 완료");

				// insert - treatments
				// treat_id, Patients_pat_id, Doctors_doc_id, treat_contents, treat_date
				rowcount = 0;

				query = "INSERT INTO treatments VALUES(130516023, 2345, 980312, '감기, 몸살', STR_TO_DATE('2013-05-16','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(130628100, 3545, 020403, '피부 트러블 치료', STR_TO_DATE('2013-06-28','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(131205056, 3424, 080543, '목 디스크로 MRI 촬영', STR_TO_DATE('2013-12-05','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(131218024, 7675, 050900, '중이염', STR_TO_DATE('2013-12-18','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(131224012, 4533, 000601, '장염', STR_TO_DATE('2013-12-24','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(140103001, 5546, 070576, '여드름 치료', STR_TO_DATE('2014-01-03','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(140109026, 4543, 050101, '위염', STR_TO_DATE('2014-01-09','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(140226102, 9768, 091001, '화상치료', STR_TO_DATE('2014-02-26','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(140303003, 4234, 091001, '교통사고 외상치료', STR_TO_DATE('2014-03-03','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(140308087, 7643, 062019, '장염', STR_TO_DATE('2014-03-08','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);

				query = "INSERT INTO treatments VALUES(123456701, 9901, 987602, '진료내용 1', STR_TO_DATE('2015-05-16','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(123456702, 9902, 987606, '진료내용 2', STR_TO_DATE('2015-06-28','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(123456703, 9903, 987603, '진료내용 3', STR_TO_DATE('2015-12-05','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(123456704, 9904, 987605, '진료내용 4', STR_TO_DATE('2015-12-18','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(123456705, 9905, 987605, '진료내용 5', STR_TO_DATE('2015-12-24','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(123456706, 9906, 987601, '진료내용 6', STR_TO_DATE('2016-01-03','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(123456707, 9907, 987607, '진료내용 7', STR_TO_DATE('2016-01-09','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(123456708, 9908, 987606, '진료내용 8', STR_TO_DATE('2016-02-26','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(123456709, 9909, 987610, '진료내용 9', STR_TO_DATE('2016-03-03','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(123456710, 9910, 987605, '진료내용 10', STR_TO_DATE('2016-03-08','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);

				System.out.println("treatments 테이블에 " + rowcount + "개의 튜플 INSERT 완료");

				// insert- charts
				// char_id, Treatments_treat_id, Treatments_Doctors_doc_id,
				// Treatments_Patients_pat_id, Nurses_nur_id, chart_contents
				rowcount = 0;

				query = "INSERT INTO charts VALUES('차트 1', 130516023, 980312, 2345, 050302, '차트내용 1')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('차트 2', 130628100, 020403, 3545, 040089, '차트내용 2')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('차트 3', 131205056, 080543, 3424, 070605, '차트내용 3')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('차트 4', 131218024, 050900, 7675, 100356, '차트내용 4')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('차트 5', 131224012, 000601, 4533, 070804, '차트내용 5')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('차트 6', 140103001, 070576, 5546, 120309, '차트내용 6')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('차트 7', 140109026, 050101, 4543, 070804, '차트내용 7')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('차트 8', 140226102, 091001, 9768, 130211, '차트내용 8')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('차트 9', 140303003, 091001, 4234, 130211, '차트내용 9')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('차트 10', 140308087, 062019, 7643, 071018, '차트내용 10')";
				rowcount += stmt.executeUpdate(query);

				query = "INSERT INTO charts VALUES('차트 11', 123456701, 987602, 9901, 432101, '차트내용 11')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('차트 12', 123456702, 987606, 9902, 432102, '차트내용 12')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('차트 13', 123456703, 987603, 9903, 432103, '차트내용 13')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('차트 14', 123456704, 987605, 9904, 432104, '차트내용 14')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('차트 15', 123456705, 987605, 9905, 432105, '차트내용 15')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('차트 16', 123456706, 987601, 9906, 432106, '차트내용 16')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('차트 17', 123456707, 987607, 9907, 432107, '차트내용 17')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('차트 18', 123456708, 987606, 9908, 432108, '차트내용 18')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('차트 19', 123456709, 987610, 9909, 432109, '차트내용 19')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('차트 20', 123456710, 987605, 9910, 432110, '차트내용 20')";
				rowcount += stmt.executeUpdate(query);

				System.out.println("charts 테이블에 " + rowcount + "개의 튜플 INSERT 완료");

				System.out.println("초기화 완료 - - - - - - - - - - - - - - -");
				System.out.println("");

			} catch (Exception e6) {
				System.out.println("초기화 실패 : " + e6);
				System.out.println("");
			}

		}

	}

	// 입력 Action Listener
	private class ActionListenerInput implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			frame = new JFrame("입력할 테이블 선택");
			frame.setLayout(new GridLayout(1, 5));

			inputDoctors = new JButton("Doctors");
			inputNurses = new JButton("Nurses");
			inputPatients = new JButton("Patients");
			inputTreatments = new JButton("Treatments");
			inputCharts = new JButton("Charts");

			frame.add(inputDoctors);
			frame.add(inputNurses);
			frame.add(inputPatients);
			frame.add(inputTreatments);
			frame.add(inputCharts);

			inputDoctors.addActionListener(new ActionListenerInputDoctors());
			inputNurses.addActionListener(new ActionListenerInputNurses());
			inputPatients.addActionListener(new ActionListenerInputPatients());
			inputTreatments.addActionListener(new ActionListenerInputTreatments());
			inputCharts.addActionListener(new ActionListenerInputCharts());

			frame.setSize(600, 100);
			frame.setVisible(true);
			frame.setLocation(400, 275);

		}

	}

	private class ActionListenerInputDoctors implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				frame = new JFrame("Doctors 데이터 입력");
				frame.setLayout(new BorderLayout());

				inputPanel = new JPanel();
				inputPanel.setLayout(new GridLayout(7, 2, 5, 5));

				doc_DI = new JTextField("");
				doc_MT = new JTextField("");
				doc_DN = new JTextField("");
				doc_DG = new JTextField("");
				doc_DPH = new JTextField("");
				doc_DE = new JTextField("");
				doc_DPO = new JTextField("");

				inputPanel.add(new JLabel("의사 ID"));
				inputPanel.add(doc_DI);
				inputPanel.add(new JLabel("담당 진료 과목"));
				inputPanel.add(doc_MT);
				inputPanel.add(new JLabel("성명"));
				inputPanel.add(doc_DN);
				inputPanel.add(new JLabel("성별"));
				inputPanel.add(doc_DG);
				inputPanel.add(new JLabel("전화번호"));
				inputPanel.add(doc_DPH);
				inputPanel.add(new JLabel("이메일"));
				inputPanel.add(doc_DE);
				inputPanel.add(new JLabel("직급"));
				inputPanel.add(doc_DPO);

				completeInputPanel = new JPanel();
				completeInputPanel.setLayout(new BorderLayout());
				completeInputBtn = new JButton("Doctors 테이블에 해당 데이터 추가");
				completeInputBtn.addActionListener(new ActionListenerCompleteInputDoctors());
				completeInputPanel.add(completeInputBtn);

				frame.add(inputPanel, BorderLayout.CENTER);
				frame.add(completeInputPanel, BorderLayout.SOUTH);

				frame.setSize(600, 300);
				frame.setVisible(true);
				frame.setLocation(400, 375);

			} catch (Exception e6) {
				System.out.println("doctors 데이터 입력 실패 : " + e6);
				System.out.println("");
			}

		}

	}

	private class ActionListenerCompleteInputDoctors implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("doctors 테이블에 데이터 추가 시도...");

				query = "INSERT INTO doctors VALUES (?, ?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(query);

				pstmt.setInt(1, Integer.parseInt(doc_DI.getText()));
				pstmt.setString(2, doc_MT.getText());
				pstmt.setString(3, doc_DN.getText());
				pstmt.setString(4, doc_DG.getText());
				pstmt.setString(5, doc_DPH.getText());
				pstmt.setString(6, doc_DE.getText());
				pstmt.setString(7, doc_DPO.getText());
				pstmt.executeUpdate();

				System.out.println("doctors 테이블에 데이터 추가 성공");
				System.out.println("");

			} catch (Exception e9) {
				System.out.println("doctors 테이블에 데이터 추가 실패 : " + e9);
			}

		}
	}

	private class ActionListenerInputNurses implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				frame = new JFrame("Nurses 데이터 입력");
				frame.setLayout(new BorderLayout());

				inputPanel = new JPanel();
				inputPanel.setLayout(new GridLayout(7, 2, 5, 5));

				nur_NI = new JTextField("");
				nur_MJ = new JTextField("");
				nur_NN = new JTextField("");
				nur_NG = new JTextField("");
				nur_NPH = new JTextField("");
				nur_NE = new JTextField("");
				nur_NPO = new JTextField("");

				inputPanel.add(new JLabel("간호사 ID"));
				inputPanel.add(nur_NI);
				inputPanel.add(new JLabel("담당 업무"));
				inputPanel.add(nur_MJ);
				inputPanel.add(new JLabel("성명"));
				inputPanel.add(nur_NN);
				inputPanel.add(new JLabel("성별"));
				inputPanel.add(nur_NG);
				inputPanel.add(new JLabel("전화번호"));
				inputPanel.add(nur_NPH);
				inputPanel.add(new JLabel("이메일"));
				inputPanel.add(nur_NE);
				inputPanel.add(new JLabel("직급"));
				inputPanel.add(nur_NPO);

				completeInputPanel = new JPanel();
				completeInputPanel.setLayout(new BorderLayout());
				completeInputBtn = new JButton("Nurses 테이블에 해당 데이터 추가");
				completeInputBtn.addActionListener(new ActionListenerCompleteInputNurses());
				completeInputPanel.add(completeInputBtn);

				frame.add(inputPanel, BorderLayout.CENTER);
				frame.add(completeInputPanel, BorderLayout.SOUTH);

				frame.setSize(600, 300);
				frame.setVisible(true);
				frame.setLocation(400, 375);

			} catch (Exception e6) {
				System.out.println("nurses 데이터 입력 실패 : " + e6);
				System.out.println("");
			}

		}

	}

	private class ActionListenerCompleteInputNurses implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("nurses 테이블에 데이터 추가 시도...");

				query = "INSERT INTO nurses VALUES (?, ?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(query);

				pstmt.setInt(1, Integer.parseInt(nur_NI.getText()));
				pstmt.setString(2, nur_MJ.getText());
				pstmt.setString(3, nur_NN.getText());
				pstmt.setString(4, nur_NG.getText());
				pstmt.setString(5, nur_NPH.getText());
				pstmt.setString(6, nur_NE.getText());
				pstmt.setString(7, nur_NPO.getText());
				pstmt.executeUpdate();

				System.out.println("nurses 테이블에 데이터 추가 성공");
				System.out.println("");

			} catch (Exception e9) {
				System.out.println("nurses 테이블에 데이터 추가 실패 : " + e9);
			}

		}
	}

	private class ActionListenerInputPatients implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				frame = new JFrame("Patients 데이터 입력");
				frame.setLayout(new BorderLayout());

				inputPanel = new JPanel();
				inputPanel.setLayout(new GridLayout(10, 2, 5, 5));

				pat_PI = new JTextField("");
				pat_NI = new JTextField("");
				pat_DI = new JTextField("");
				pat_PN = new JTextField("");
				pat_PG = new JTextField("");
				pat_PJU = new JTextField("");
				pat_PA = new JTextField("");
				pat_PP = new JTextField("");
				pat_PE = new JTextField("");
				pat_PJO = new JTextField("");

				inputPanel.add(new JLabel("환자 ID"));
				inputPanel.add(pat_PI);
				inputPanel.add(new JLabel("간호사 ID"));
				inputPanel.add(pat_NI);
				inputPanel.add(new JLabel("의사 ID"));
				inputPanel.add(pat_DI);
				inputPanel.add(new JLabel("환자 성명"));
				inputPanel.add(pat_PN);
				inputPanel.add(new JLabel("환자 성별"));
				inputPanel.add(pat_PG);
				inputPanel.add(new JLabel("주민번호"));
				inputPanel.add(pat_PJU);
				inputPanel.add(new JLabel("주소"));
				inputPanel.add(pat_PA);
				inputPanel.add(new JLabel("전화번호"));
				inputPanel.add(pat_PP);
				inputPanel.add(new JLabel("이메일"));
				inputPanel.add(pat_PE);
				inputPanel.add(new JLabel("직업"));
				inputPanel.add(pat_PJO);

				completeInputPanel = new JPanel();
				completeInputPanel.setLayout(new BorderLayout());
				completeInputBtn = new JButton("Patients 테이블에 해당 데이터 추가");
				completeInputBtn.addActionListener(new ActionListenerCompleteInputPatients());
				completeInputPanel.add(completeInputBtn);

				frame.add(inputPanel, BorderLayout.CENTER);
				frame.add(completeInputPanel, BorderLayout.SOUTH);

				frame.setSize(600, 400);
				frame.setVisible(true);
				frame.setLocation(400, 375);

			} catch (Exception e6) {
				System.out.println("patients 데이터 입력 실패 : " + e6);
				System.out.println("");
			}

		}

	}

	private class ActionListenerCompleteInputPatients implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("patients 테이블에 데이터 추가 시도...");

				query = "INSERT INTO patients VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(query);

				pstmt.setInt(1, Integer.parseInt(pat_PI.getText()));
				pstmt.setInt(2, Integer.parseInt(pat_NI.getText()));
				pstmt.setInt(3, Integer.parseInt(pat_DI.getText()));
				pstmt.setString(4, pat_PN.getText());
				pstmt.setString(5, pat_PG.getText());
				pstmt.setString(6, pat_PJU.getText());
				pstmt.setString(7, pat_PA.getText());
				pstmt.setString(8, pat_PP.getText());
				pstmt.setString(9, pat_PE.getText());
				pstmt.setString(10, pat_PJO.getText());
				pstmt.executeUpdate();

				System.out.println("patients 테이블에 데이터 추가 성공");
				System.out.println("");

			} catch (Exception e9) {
				System.out.println("patients 테이블에 데이터 추가 실패 : " + e9);
			}

		}
	}

	private class ActionListenerInputTreatments implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				frame = new JFrame("Treatments 데이터 입력");
				frame.setLayout(new BorderLayout());

				inputPanel = new JPanel();
				inputPanel.setLayout(new GridLayout(5, 2, 5, 5));

				treat_TI = new JTextField("");
				treat_PI = new JTextField("");
				treat_DI = new JTextField("");
				treat_TC = new JTextField("");
				treat_TD = new JTextField("");

				inputPanel.add(new JLabel("진료 ID"));
				inputPanel.add(treat_TI);
				inputPanel.add(new JLabel("환자 ID"));
				inputPanel.add(treat_PI);
				inputPanel.add(new JLabel("의사 ID"));
				inputPanel.add(treat_DI);
				inputPanel.add(new JLabel("진료 내용"));
				inputPanel.add(treat_TC);
				inputPanel.add(new JLabel("진료 날짜"));
				inputPanel.add(treat_TD);

				completeInputPanel = new JPanel();
				completeInputPanel.setLayout(new BorderLayout());
				completeInputBtn = new JButton("Treatments 테이블에 해당 데이터 추가");
				completeInputBtn.addActionListener(new ActionListenerCompleteInputTreatments());
				completeInputPanel.add(completeInputBtn);

				frame.add(inputPanel, BorderLayout.CENTER);
				frame.add(completeInputPanel, BorderLayout.SOUTH);

				frame.setSize(600, 300);
				frame.setVisible(true);
				frame.setLocation(400, 375);

			} catch (Exception e6) {
				System.out.println("treatments 데이터 입력 실패 : " + e6);
				System.out.println("");
			}

		}

	}

	private class ActionListenerCompleteInputTreatments implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("treatments 테이블에 데이터 추가 시도...");

				query = "INSERT INTO treatments VALUES (?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(query);

				pstmt.setInt(1, Integer.parseInt(treat_TI.getText()));
				pstmt.setInt(2, Integer.parseInt(treat_PI.getText()));
				pstmt.setInt(3, Integer.parseInt(treat_DI.getText()));
				pstmt.setString(4, treat_TC.getText());
				pstmt.setString(5, treat_TD.getText());
				pstmt.executeUpdate();

				System.out.println("treatments 테이블에 데이터 추가 성공");
				System.out.println("");

			} catch (Exception e9) {
				System.out.println("treatments 테이블에 데이터 추가 실패 : " + e9);
			}

		}
	}

	private class ActionListenerInputCharts implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				frame = new JFrame("Charts 데이터 입력");
				frame.setLayout(new BorderLayout());

				inputPanel = new JPanel();
				inputPanel.setLayout(new GridLayout(6, 2, 5, 5));

				chart_CI = new JTextField("");
				chart_TI = new JTextField("");
				char_DI = new JTextField("");
				char_PI = new JTextField("");
				char_NI = new JTextField("");
				char_CC = new JTextField("");

				inputPanel.add(new JLabel("차트 번호"));
				inputPanel.add(chart_CI);
				inputPanel.add(new JLabel("진료 ID"));
				inputPanel.add(chart_TI);
				inputPanel.add(new JLabel("의사 ID"));
				inputPanel.add(char_DI);
				inputPanel.add(new JLabel("환자 ID"));
				inputPanel.add(char_PI);
				inputPanel.add(new JLabel("간호사 ID"));
				inputPanel.add(char_NI);
				inputPanel.add(new JLabel("차트 내용"));
				inputPanel.add(char_CC);

				completeInputPanel = new JPanel();
				completeInputPanel.setLayout(new BorderLayout());
				completeInputBtn = new JButton("Charts 테이블에 해당 데이터 추가");
				completeInputBtn.addActionListener(new ActionListenerCompleteInputCharts());
				completeInputPanel.add(completeInputBtn);

				frame.add(inputPanel, BorderLayout.CENTER);
				frame.add(completeInputPanel, BorderLayout.SOUTH);

				frame.setSize(600, 300);
				frame.setVisible(true);
				frame.setLocation(400, 375);

			} catch (Exception e6) {
				System.out.println("charts 데이터 입력 실패 : " + e6);
				System.out.println("");
			}

		}

	}

	private class ActionListenerCompleteInputCharts implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("charts 테이블에 데이터 추가 시도...");

				query = "INSERT INTO charts VALUES (?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(query);

				pstmt.setString(1, chart_CI.getText());
				pstmt.setInt(2, Integer.parseInt(chart_TI.getText()));
				pstmt.setInt(3, Integer.parseInt(char_DI.getText()));
				pstmt.setInt(4, Integer.parseInt(char_PI.getText()));
				pstmt.setInt(5, Integer.parseInt(char_NI.getText()));
				pstmt.setString(6, char_CC.getText());
				pstmt.executeUpdate();

				System.out.println("charts 테이블에 데이터 추가 성공");
				System.out.println("");

			} catch (Exception e9) {
				System.out.println("charts 테이블에 데이터 추가 실패 : " + e9);
			}

		}
	}

	// 검색 1 Action Listener
	private class ActionListenerSelect1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			frame = new JFrame("검색할 테이블 선택하면 해당 테이블 출력");
			frame.setLayout(new BorderLayout());

			selectBtnPanel = new JPanel();
			selectBtnPanel.setLayout(new GridLayout(1, 5));

			selOutputTArea = new JTextArea();
			selOutputTArea.setEditable(false);
			selOutputSPane = new JScrollPane(selOutputTArea);
			selOutputSPane.setPreferredSize(new Dimension(650, 450));

			selectDoctors = new JButton("Doctors");
			selectNurses = new JButton("Nurses");
			selectPatients = new JButton("Patients");
			selectTreatments = new JButton("Treatments");
			selectCharts = new JButton("Charts");

			selectDoctors.addActionListener(new ActionListenerSelectDoctors());
			selectNurses.addActionListener(new ActionListenerSelectNurses());
			selectPatients.addActionListener(new ActionListenerSelectPatients());
			selectTreatments.addActionListener(new ActionListenerSelectTreatments());
			selectCharts.addActionListener(new ActionListenerSelectCharts());

			selectBtnPanel.add(selectDoctors);
			selectBtnPanel.add(selectNurses);
			selectBtnPanel.add(selectPatients);
			selectBtnPanel.add(selectTreatments);
			selectBtnPanel.add(selectCharts);

			frame.add("North", selectBtnPanel);
			frame.add("Center", selOutputSPane);

			frame.setSize(700, 400);
			frame.setVisible(true);
			frame.setLocation(350, 275);

		}

	}

	private class ActionListenerSelectDoctors implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				selOutputTArea.setText("");
				selOutputTArea.append("의사ID" + "\t" + "담당진료과목" + "\t" + "성명" + "\t" + "성별" + "\t" + "전화번호" + "\t\t"
						+ "이메일" + "\t\t" + "직급" + "\n\n");

				query = "select * from doctors";
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4)
							+ "\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\t\t" + rs.getString(7) + "\n";
					selOutputTArea.append(str);
				}

			} catch (Exception e10) {
				System.out.println("doctors 테이블 출력 실패 : " + e10);
			}

		}

	}

	private class ActionListenerSelectNurses implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				selOutputTArea.setText("");
				selOutputTArea.append("간호사ID" + "\t" + "담당업무" + "\t" + "성명" + "\t" + "성별" + "\t" + "전화번호" + "\t\t"
						+ "이메일" + "\t\t" + "직급" + "\n\n");

				query = "select * from nurses";
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4)
							+ "\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\t\t" + rs.getString(7) + "\n";
					selOutputTArea.append(str);
				}

			} catch (Exception e10) {
				System.out.println("nurses 테이블 출력 실패 : " + e10);
			}

		}

	}

	private class ActionListenerSelectPatients implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				selOutputTArea.setText("");
				selOutputTArea.append("환자 ID" + "\t" + "간호사 ID" + "\t" + "의사 ID" + "\t" + "환자 성명" + "\t" + "환자 성별"
						+ "\t" + "주민번호" + "\t" + "주소" + "\t" + "전화번호" + "\t\t" + "이메일" + "\t\t" + "직업" + "\n\n");

				query = "select * from patients";
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4)
							+ "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7) + "\t"
							+ rs.getString(8) + "\t\t" + rs.getString(9) + "\t\t" + rs.getString(10) + "\n";
					selOutputTArea.append(str);
				}

			} catch (Exception e10) {
				System.out.println("patients 테이블 출력 실패 : " + e10);
			}

		}

	}

	private class ActionListenerSelectTreatments implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				selOutputTArea.setText("");
				selOutputTArea
						.append("진료ID" + "\t" + "환자 ID" + "\t" + "의사 ID" + "\t" + "진료 내용" + "\t\t" + "진료 날짜" + "\n\n");

				query = "select * from treatments";
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4)
							+ "\t\t" + rs.getString(5) + "\n";
					selOutputTArea.append(str);
				}

			} catch (Exception e10) {
				System.out.println("treatments 테이블 출력 실패 : " + e10);
			}

		}

	}

	private class ActionListenerSelectCharts implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				selOutputTArea.setText("");
				selOutputTArea.append("차트 번호" + "\t" + "진료 ID" + "\t" + "의사 ID" + "\t" + "환자 ID" + "\t" + "간호사 ID"
						+ "\t" + "차트 내용" + "\n\n");

				query = "select * from charts";
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					String str = rs.getString(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4)
							+ "\t" + rs.getInt(5) + "\t" + rs.getString(6) + "\n";
					selOutputTArea.append(str);
				}

			} catch (Exception e10) {
				System.out.println("charts 테이블 출력 실패 : " + e10);
			}

		}

	}

	public static void main(String[] args) {

		Hospital hospital = new Hospital();

		hospital.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent we) {

				try {
					con.close();
				} catch (Exception e) {
				}

				System.out.println("프로그램 완전 종료!");
				System.exit(0);

			}

		});

	}

}