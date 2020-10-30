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

		super("18011600/�̴ٿ�");
		conDB();
		mainWindow();
		setVisible(true);
		setBounds(200, 200, 1000, 500);
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
			System.out.println("");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	public void mainWindow() {

		btnInit = new JButton("�ʱ�ȭ");
		btnInput = new JButton("�Է�");
		btnSelect1 = new JButton("�˻� 1");
		btnSelect2 = new JButton("�˻� 2");
		btnSelect3 = new JButton("�˻� 3");

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
							+ "WHERE Doctors_doc_id IN (SELECT doc_id FROM doctors WHERE doc_position LIKE '������')\r\n"
							+ "GROUP BY pat_gen;";

					outputTArea.setText("\n\n\n\n");
					outputTArea.append(
							"\t\t\t - - - - - - - - - - �˻� 2 - - - - - - - - - - - - - - - - - - - -- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n");
					outputTArea.append("\t\t\t < �����ǿ��� �Ҵ� ���� ������ ���� ȯ���� �� >\n\n");
					outputTArea.append("\t\t\t SELECT pat_gen, COUNT(*) 'count'\r\n" + "\t\t\t FROM patients\r\n"
							+ "\t\t\t WHERE Doctors_doc_id\n"
							+ "\t\t\t IN (SELECT doc_id FROM doctors WHERE doc_position LIKE '������')\r\n"
							+ "\t\t\t GROUP BY pat_gen;\n");
					outputTArea.append(
							"\t\t\t - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n\n");

					outputTArea.append("\t\t\t ȯ�� ����" + "\t" + "�����ǿ��� �Ҵ� ���� ȯ���� ��" + "\n\n\t\t\t ");
					rs = stmt.executeQuery(query);

					while (rs.next()) {
						String str = rs.getString(1) + "\t" + rs.getString(2) + "\n\t\t\t ";
						outputTArea.append(str);
					}

				} catch (Exception e1) {
					System.out.println("�˻� 2 ���� : " + e1);
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
							+ "IN (SELECT doc_id FROM doctors WHERE doc_position LIKE '������'))\r\n"
							+ "GROUP BY Treatments_Patients_pat_id;";

					outputTArea.setText("\n");
					outputTArea.append(
							"\t\t\t - - - - - - - - - - �˻� 3 - - - - - - - - - - - - - - - - - - - -- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n");
					outputTArea.append("\t\t\t < ȯ�ڰ� 'ȯ�ڿ��� �����ǿ� �Բ� �Ҵ�� �� �ִ� ��ȣ��'���� ���� ���� Ƚ�� >\n\n");
					outputTArea.append("\t\t\t SELECT Treatments_Patients_pat_id, COUNT(*) 'count'\r\n"
							+ "\t\t\t FROM charts\r\n" + "\t\t\t WHERE Nurses_nur_id\r\n"
							+ "\t\t\t IN (SELECT Nurses_nur_id FROM patients WHERE Doctors_doc_id\r\n"
							+ "\t\t\t IN (SELECT doc_id FROM doctors WHERE doc_position LIKE '������'))\r\n"
							+ "\t\t\t GROUP BY Treatments_Patients_pat_id;\n");
					outputTArea.append(
							"\t\t\t - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n\n");

					outputTArea.append("\t\t\t ȯ�� ID" + "\t" + "Ƚ��" + "\n\n\t\t\t ");
					rs = stmt.executeQuery(query);

					while (rs.next()) {
						String str = rs.getString(1) + "\t" + rs.getString(2) + "\n\t\t\t ";
						outputTArea.append(str);
					}

				} catch (Exception e1) {
					System.out.println("�˻� 3 ���� : " + e1);
				}

			}

		});

	}

	// �ʱ�ȭ Action Listener
	private class ActionListenerInit implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			int rowcount;

			try {
				System.out.println("�ʱ�ȭ ���� - - - - - - - - - - - - - - -");

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
				System.out.println("5���� ���̺� DROP �Ϸ�");

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

				System.out.println("5���� ���̺� CREATE �Ϸ�");

				// insert - doctors
				// doc_id, major_treat, doc_name, doc_gen, doc_phone, doc_email, doc_position
				rowcount = 0;

				query = "INSERT INTO doctors VALUES(980312, '�Ҿư�', '������', 'M', '010-333-1340', 'ltj@hanbh.com', '����')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(000601, '����', '�ȼ���', 'M', '011-222-0987', 'ask@hanbh.com', '����')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(001208, '�ܰ�', '�����', 'M', '010-333-8743', 'kmj@hanbh.com', '����')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(020403, '�Ǻΰ�', '���¼�', 'M', '019-777-3764', 'lts@hanbh.com', '����')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(050900, '�Ҿư�', '�迬��', 'F', '010-555-3746', 'kya@hanbh.com', '������')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(050101, '����', '������', 'M', '011-222-7643', 'cth@hanbh.com', '������')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(062019, '�Ҿư�', '������', 'F', '010-999-1265', 'jjh@hanbh.com', '������')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(070576, '�Ǻΰ�', 'ȫ�浿', 'M', '016-333-7263', 'hgd@hanbh.com', '������')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(080543, '��缱��', '���缮', 'M', '010-222-1263', 'yjs@hanbh.com', '����')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(091001, '�ܰ�', '�躴��', 'M', '010-555-3542', 'kbm@hanbh.com', '������')";
				rowcount += stmt.executeUpdate(query);

				query = "INSERT INTO doctors VALUES(987601, '����', '���ϳ�', 'F', '010-123-0001', 'khn@hanbh.com', '������')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(987602, '�ܰ�', '��ѵ�', 'F', '010-123-0002', 'kdd@hanbh.com', '������')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(987603, '�ܰ�', '��¼�', 'F', '010-123-0003', 'kss@hanbh.com', '������')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(987604, '��缱��', '��ݳ�', 'F', '010-123-0004', 'knn@hanbh.com', '������')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(987605, '�Ҿư�', '��ټ�', 'M', '010-123-0005', 'kds@hanbh.com', '������')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(987606, '�Ǻΰ�', '�迩��', 'F', '010-123-0006', 'kys@hanbh.com', '������')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(987607, '��缱��', '���ϰ�', 'M', '010-123-0007', 'kig@hanbh.com', '������')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(987608, '�ܰ�', '�迩��', 'F', '010-123-0008', 'kyd@hanbh.com', '������')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(987609, '�ܰ�', '���ȩ', 'F', '010-123-0009', 'kah@hanbh.com', '������')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO doctors VALUES(987610, '����', '�迭��', 'F', '010-123-0010', 'kyy@hanbh.com', '������')";
				rowcount += stmt.executeUpdate(query);

				System.out.println("doctors ���̺� " + rowcount + "���� Ʃ�� INSERT �Ϸ�");

				// insert - nurses
				// nur_id, major_job, nur_name, nur_gen, nur_email, nur_position
				rowcount = 0;

				query = "INSERT INTO nurses VALUES(050302, '�Ҿư�', '������', 'F', '010-555-8751', 'key@hanbh.com', '����ȣ��')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(050021, '����', '������', 'F', '016-333-8745', 'ysa@hanbh.com', '����ȣ��')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(040089, '�Ǻΰ�', '������', 'M', '010-666-7646', 'sjw@hanbh.com', '����')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(070605, '��缱��', '����ȭ', 'F', '010-333-4588', 'yjh@hanbh.com', '����')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(070804, '����', '���ϳ�', 'F', '010-222-1340', 'nhn@hanbh.com', '����')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(071018, '�Ҿư�', '��ȭ��', 'F', '019-888-4116', 'khk@hanbh.com', '����')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(100356, '�Ҿư�', '�̼���', 'M', '010-777-1234', 'lsy@hanbh.com', '��ȣ��')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(104145, '�ܰ�', '����', 'M', '010-999-8520', 'kh@hanbh.com', '��ȣ��')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(120309, '�Ǻΰ�', '�ڼ���', 'M', '010-777-4996', 'psw@hanbh.com', '��ȣ��')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(130211, '�ܰ�', '�̼���', 'F', '010-222-3214', 'lsy2@hanbh.com', '��ȣ��')";
				rowcount += stmt.executeUpdate(query);

				query = "INSERT INTO nurses VALUES(432101, '�ܰ�', '���ϳ�', 'M', '010-321-0001', 'lhn@hanbh.com', '����')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(432102, '�Ǻΰ�', '�̵ѵ�', 'F', '010-321-0002', 'ldd@hanbh.com', '��ȣ��')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(432103, '�ܰ�', '�̼¼�', 'M', '010-321-0003', 'lss@hanbh.com', '��ȣ��')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(432104, '�Ҿư�', '�̳ݳ�', 'F', '010-321-0004', 'lnn@hanbh.com', '��ȣ��')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(432105, '�Ҿư�', '�̴ټ�', 'M', '010-321-0005', 'lds@hanbh.com', '��ȣ��')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(432106, '����', '�̿���', 'F', '010-321-0006', 'lys@hanbh.com', '��ȣ��')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(432107, '��缱��', '���ϰ�', 'M', '010-321-0007', 'lig@hanbh.com', '��ȣ��')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(432108, '�Ǻΰ�', '�̿���', 'F', '010-321-0008', 'lyd@hanbh.com', '��ȣ��')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(432109, '����', '�̾�ȩ', 'M', '010-321-0009', 'lah@hanbh.com', '��ȣ��')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO nurses VALUES(432110, '�Ҿư�', '�̿���', 'F', '010-321-0010', 'lyy@hanbh.com', '��ȣ��')";
				rowcount += stmt.executeUpdate(query);

				System.out.println("nurses ���̺� " + rowcount + "���� Ʃ�� INSERT �Ϸ�");

				// insert - patients
				// pat_id, Nurses_nur_id, Doctors_nur_id, pat_name, pat_gen, pat_gen, pat_jumin,
				// pat_addr, pat_phone, pat_email, pat_job
				rowcount = 0;

				query = "INSERT INTO patients VALUES(2345, 050302, 980312, '�Ȼ��', 'M', '232345', '����', '010-555-7845', 'ask@ab.com', 'ȸ���')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(3545, 040089, 020403, '�輺��', 'M', '543545', '����', '010-333-7812', 'ksr@ab.com', '�ڿ���')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(3424, 070605, 080543, '������', 'M', '433424', '�λ�', '019-888-4859', 'ljj@ab.com', 'ȸ���')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(7675, 100356, 050900, '�ֱ���', 'M', '677675', '����', '010-222-4847', 'cks@ab.com', 'ȸ���')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(4533, 070804, 000601, '���Ѱ�', 'M', '744533', '����', '010-777-9630', 'jhk@ab.com', '����')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(5546, 120309, 070576, '������', 'M', '765546', '�뱸', '016-777-0214', 'ywh@ab.com', '�ڿ���')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(4543, 070804, 050101, '������', 'M', '454543', '�λ�', '010-555-4187', 'cjj@ab.com', 'ȸ���')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(9768, 130211, 091001, '������', 'F', '119768', '����', '010-888-3675', 'ljh@ab.com', '����')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(4234, 130211, 091001, '������', 'F', '234234', '����', '010-999-6541', 'onm@ab.com', '�л�')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(7643, 071018, 062019, '�ۼ���', 'M', '987643', '����', '010-222-5874', 'ssm@ab.com', '�л�')";
				rowcount += stmt.executeUpdate(query);

				query = "INSERT INTO patients VALUES(9901, 432101, 987602, 'ȯ�ϳ�', 'F', '000001', '����', '010-456-0001', 'hhn@ab.com', '�л�')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(9902, 432102, 987606, 'ȯ�ѵ�', 'M', '000002', '����', '010-456-0002', 'hdd@ab.com', '�л�')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(9903, 432103, 987603, 'ȯ�¼�', 'F', '000003', '����', '010-456-0003', 'hss@ab.com', '�л�')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(9904, 432104, 987605, 'ȯ�ݳ�', 'M', '000004', '����', '010-456-0004', 'hnn@ab.com', '�л�')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(9905, 432105, 987605, 'ȯ�ټ�', 'F', '000005', '����', '010-456-0005', 'hds@ab.com', '�л�')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(9906, 432106, 987601, 'ȯ����', 'M', '000006', '����', '010-456-0006', 'hys@ab.com', '�л�')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(9907, 432107, 987607, 'ȯ�ϰ�', 'F', '000007', '����', '010-456-0007', 'hig@ab.com', '�л�')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(9908, 432108, 987606, 'ȯ����', 'M', '000008', '����', '010-456-0008', 'hyd@ab.com', '�л�')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(9909, 432109, 987610, 'ȯ��ȩ', 'F', '000009', '����', '010-456-0009', 'hah@ab.com', '�л�')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO patients VALUES(9910, 432110, 987605, 'ȯ����', 'M', '000010', '����', '010-456-0010', 'hyy@ab.com', '�л�')";
				rowcount += stmt.executeUpdate(query);

				System.out.println("patients ���̺� " + rowcount + "���� Ʃ�� INSERT �Ϸ�");

				// insert - treatments
				// treat_id, Patients_pat_id, Doctors_doc_id, treat_contents, treat_date
				rowcount = 0;

				query = "INSERT INTO treatments VALUES(130516023, 2345, 980312, '����, ����', STR_TO_DATE('2013-05-16','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(130628100, 3545, 020403, '�Ǻ� Ʈ���� ġ��', STR_TO_DATE('2013-06-28','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(131205056, 3424, 080543, '�� ��ũ�� MRI �Կ�', STR_TO_DATE('2013-12-05','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(131218024, 7675, 050900, '���̿�', STR_TO_DATE('2013-12-18','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(131224012, 4533, 000601, '�忰', STR_TO_DATE('2013-12-24','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(140103001, 5546, 070576, '���帧 ġ��', STR_TO_DATE('2014-01-03','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(140109026, 4543, 050101, '����', STR_TO_DATE('2014-01-09','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(140226102, 9768, 091001, 'ȭ��ġ��', STR_TO_DATE('2014-02-26','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(140303003, 4234, 091001, '������ �ܻ�ġ��', STR_TO_DATE('2014-03-03','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(140308087, 7643, 062019, '�忰', STR_TO_DATE('2014-03-08','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);

				query = "INSERT INTO treatments VALUES(123456701, 9901, 987602, '���᳻�� 1', STR_TO_DATE('2015-05-16','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(123456702, 9902, 987606, '���᳻�� 2', STR_TO_DATE('2015-06-28','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(123456703, 9903, 987603, '���᳻�� 3', STR_TO_DATE('2015-12-05','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(123456704, 9904, 987605, '���᳻�� 4', STR_TO_DATE('2015-12-18','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(123456705, 9905, 987605, '���᳻�� 5', STR_TO_DATE('2015-12-24','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(123456706, 9906, 987601, '���᳻�� 6', STR_TO_DATE('2016-01-03','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(123456707, 9907, 987607, '���᳻�� 7', STR_TO_DATE('2016-01-09','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(123456708, 9908, 987606, '���᳻�� 8', STR_TO_DATE('2016-02-26','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(123456709, 9909, 987610, '���᳻�� 9', STR_TO_DATE('2016-03-03','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO treatments VALUES(123456710, 9910, 987605, '���᳻�� 10', STR_TO_DATE('2016-03-08','%Y-%m-%d'))";
				rowcount += stmt.executeUpdate(query);

				System.out.println("treatments ���̺� " + rowcount + "���� Ʃ�� INSERT �Ϸ�");

				// insert- charts
				// char_id, Treatments_treat_id, Treatments_Doctors_doc_id,
				// Treatments_Patients_pat_id, Nurses_nur_id, chart_contents
				rowcount = 0;

				query = "INSERT INTO charts VALUES('��Ʈ 1', 130516023, 980312, 2345, 050302, '��Ʈ���� 1')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('��Ʈ 2', 130628100, 020403, 3545, 040089, '��Ʈ���� 2')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('��Ʈ 3', 131205056, 080543, 3424, 070605, '��Ʈ���� 3')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('��Ʈ 4', 131218024, 050900, 7675, 100356, '��Ʈ���� 4')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('��Ʈ 5', 131224012, 000601, 4533, 070804, '��Ʈ���� 5')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('��Ʈ 6', 140103001, 070576, 5546, 120309, '��Ʈ���� 6')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('��Ʈ 7', 140109026, 050101, 4543, 070804, '��Ʈ���� 7')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('��Ʈ 8', 140226102, 091001, 9768, 130211, '��Ʈ���� 8')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('��Ʈ 9', 140303003, 091001, 4234, 130211, '��Ʈ���� 9')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('��Ʈ 10', 140308087, 062019, 7643, 071018, '��Ʈ���� 10')";
				rowcount += stmt.executeUpdate(query);

				query = "INSERT INTO charts VALUES('��Ʈ 11', 123456701, 987602, 9901, 432101, '��Ʈ���� 11')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('��Ʈ 12', 123456702, 987606, 9902, 432102, '��Ʈ���� 12')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('��Ʈ 13', 123456703, 987603, 9903, 432103, '��Ʈ���� 13')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('��Ʈ 14', 123456704, 987605, 9904, 432104, '��Ʈ���� 14')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('��Ʈ 15', 123456705, 987605, 9905, 432105, '��Ʈ���� 15')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('��Ʈ 16', 123456706, 987601, 9906, 432106, '��Ʈ���� 16')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('��Ʈ 17', 123456707, 987607, 9907, 432107, '��Ʈ���� 17')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('��Ʈ 18', 123456708, 987606, 9908, 432108, '��Ʈ���� 18')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('��Ʈ 19', 123456709, 987610, 9909, 432109, '��Ʈ���� 19')";
				rowcount += stmt.executeUpdate(query);
				query = "INSERT INTO charts VALUES('��Ʈ 20', 123456710, 987605, 9910, 432110, '��Ʈ���� 20')";
				rowcount += stmt.executeUpdate(query);

				System.out.println("charts ���̺� " + rowcount + "���� Ʃ�� INSERT �Ϸ�");

				System.out.println("�ʱ�ȭ �Ϸ� - - - - - - - - - - - - - - -");
				System.out.println("");

			} catch (Exception e6) {
				System.out.println("�ʱ�ȭ ���� : " + e6);
				System.out.println("");
			}

		}

	}

	// �Է� Action Listener
	private class ActionListenerInput implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			frame = new JFrame("�Է��� ���̺� ����");
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

				frame = new JFrame("Doctors ������ �Է�");
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

				inputPanel.add(new JLabel("�ǻ� ID"));
				inputPanel.add(doc_DI);
				inputPanel.add(new JLabel("��� ���� ����"));
				inputPanel.add(doc_MT);
				inputPanel.add(new JLabel("����"));
				inputPanel.add(doc_DN);
				inputPanel.add(new JLabel("����"));
				inputPanel.add(doc_DG);
				inputPanel.add(new JLabel("��ȭ��ȣ"));
				inputPanel.add(doc_DPH);
				inputPanel.add(new JLabel("�̸���"));
				inputPanel.add(doc_DE);
				inputPanel.add(new JLabel("����"));
				inputPanel.add(doc_DPO);

				completeInputPanel = new JPanel();
				completeInputPanel.setLayout(new BorderLayout());
				completeInputBtn = new JButton("Doctors ���̺� �ش� ������ �߰�");
				completeInputBtn.addActionListener(new ActionListenerCompleteInputDoctors());
				completeInputPanel.add(completeInputBtn);

				frame.add(inputPanel, BorderLayout.CENTER);
				frame.add(completeInputPanel, BorderLayout.SOUTH);

				frame.setSize(600, 300);
				frame.setVisible(true);
				frame.setLocation(400, 375);

			} catch (Exception e6) {
				System.out.println("doctors ������ �Է� ���� : " + e6);
				System.out.println("");
			}

		}

	}

	private class ActionListenerCompleteInputDoctors implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("doctors ���̺� ������ �߰� �õ�...");

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

				System.out.println("doctors ���̺� ������ �߰� ����");
				System.out.println("");

			} catch (Exception e9) {
				System.out.println("doctors ���̺� ������ �߰� ���� : " + e9);
			}

		}
	}

	private class ActionListenerInputNurses implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				frame = new JFrame("Nurses ������ �Է�");
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

				inputPanel.add(new JLabel("��ȣ�� ID"));
				inputPanel.add(nur_NI);
				inputPanel.add(new JLabel("��� ����"));
				inputPanel.add(nur_MJ);
				inputPanel.add(new JLabel("����"));
				inputPanel.add(nur_NN);
				inputPanel.add(new JLabel("����"));
				inputPanel.add(nur_NG);
				inputPanel.add(new JLabel("��ȭ��ȣ"));
				inputPanel.add(nur_NPH);
				inputPanel.add(new JLabel("�̸���"));
				inputPanel.add(nur_NE);
				inputPanel.add(new JLabel("����"));
				inputPanel.add(nur_NPO);

				completeInputPanel = new JPanel();
				completeInputPanel.setLayout(new BorderLayout());
				completeInputBtn = new JButton("Nurses ���̺� �ش� ������ �߰�");
				completeInputBtn.addActionListener(new ActionListenerCompleteInputNurses());
				completeInputPanel.add(completeInputBtn);

				frame.add(inputPanel, BorderLayout.CENTER);
				frame.add(completeInputPanel, BorderLayout.SOUTH);

				frame.setSize(600, 300);
				frame.setVisible(true);
				frame.setLocation(400, 375);

			} catch (Exception e6) {
				System.out.println("nurses ������ �Է� ���� : " + e6);
				System.out.println("");
			}

		}

	}

	private class ActionListenerCompleteInputNurses implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("nurses ���̺� ������ �߰� �õ�...");

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

				System.out.println("nurses ���̺� ������ �߰� ����");
				System.out.println("");

			} catch (Exception e9) {
				System.out.println("nurses ���̺� ������ �߰� ���� : " + e9);
			}

		}
	}

	private class ActionListenerInputPatients implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				frame = new JFrame("Patients ������ �Է�");
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

				inputPanel.add(new JLabel("ȯ�� ID"));
				inputPanel.add(pat_PI);
				inputPanel.add(new JLabel("��ȣ�� ID"));
				inputPanel.add(pat_NI);
				inputPanel.add(new JLabel("�ǻ� ID"));
				inputPanel.add(pat_DI);
				inputPanel.add(new JLabel("ȯ�� ����"));
				inputPanel.add(pat_PN);
				inputPanel.add(new JLabel("ȯ�� ����"));
				inputPanel.add(pat_PG);
				inputPanel.add(new JLabel("�ֹι�ȣ"));
				inputPanel.add(pat_PJU);
				inputPanel.add(new JLabel("�ּ�"));
				inputPanel.add(pat_PA);
				inputPanel.add(new JLabel("��ȭ��ȣ"));
				inputPanel.add(pat_PP);
				inputPanel.add(new JLabel("�̸���"));
				inputPanel.add(pat_PE);
				inputPanel.add(new JLabel("����"));
				inputPanel.add(pat_PJO);

				completeInputPanel = new JPanel();
				completeInputPanel.setLayout(new BorderLayout());
				completeInputBtn = new JButton("Patients ���̺� �ش� ������ �߰�");
				completeInputBtn.addActionListener(new ActionListenerCompleteInputPatients());
				completeInputPanel.add(completeInputBtn);

				frame.add(inputPanel, BorderLayout.CENTER);
				frame.add(completeInputPanel, BorderLayout.SOUTH);

				frame.setSize(600, 400);
				frame.setVisible(true);
				frame.setLocation(400, 375);

			} catch (Exception e6) {
				System.out.println("patients ������ �Է� ���� : " + e6);
				System.out.println("");
			}

		}

	}

	private class ActionListenerCompleteInputPatients implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("patients ���̺� ������ �߰� �õ�...");

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

				System.out.println("patients ���̺� ������ �߰� ����");
				System.out.println("");

			} catch (Exception e9) {
				System.out.println("patients ���̺� ������ �߰� ���� : " + e9);
			}

		}
	}

	private class ActionListenerInputTreatments implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				frame = new JFrame("Treatments ������ �Է�");
				frame.setLayout(new BorderLayout());

				inputPanel = new JPanel();
				inputPanel.setLayout(new GridLayout(5, 2, 5, 5));

				treat_TI = new JTextField("");
				treat_PI = new JTextField("");
				treat_DI = new JTextField("");
				treat_TC = new JTextField("");
				treat_TD = new JTextField("");

				inputPanel.add(new JLabel("���� ID"));
				inputPanel.add(treat_TI);
				inputPanel.add(new JLabel("ȯ�� ID"));
				inputPanel.add(treat_PI);
				inputPanel.add(new JLabel("�ǻ� ID"));
				inputPanel.add(treat_DI);
				inputPanel.add(new JLabel("���� ����"));
				inputPanel.add(treat_TC);
				inputPanel.add(new JLabel("���� ��¥"));
				inputPanel.add(treat_TD);

				completeInputPanel = new JPanel();
				completeInputPanel.setLayout(new BorderLayout());
				completeInputBtn = new JButton("Treatments ���̺� �ش� ������ �߰�");
				completeInputBtn.addActionListener(new ActionListenerCompleteInputTreatments());
				completeInputPanel.add(completeInputBtn);

				frame.add(inputPanel, BorderLayout.CENTER);
				frame.add(completeInputPanel, BorderLayout.SOUTH);

				frame.setSize(600, 300);
				frame.setVisible(true);
				frame.setLocation(400, 375);

			} catch (Exception e6) {
				System.out.println("treatments ������ �Է� ���� : " + e6);
				System.out.println("");
			}

		}

	}

	private class ActionListenerCompleteInputTreatments implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("treatments ���̺� ������ �߰� �õ�...");

				query = "INSERT INTO treatments VALUES (?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(query);

				pstmt.setInt(1, Integer.parseInt(treat_TI.getText()));
				pstmt.setInt(2, Integer.parseInt(treat_PI.getText()));
				pstmt.setInt(3, Integer.parseInt(treat_DI.getText()));
				pstmt.setString(4, treat_TC.getText());
				pstmt.setString(5, treat_TD.getText());
				pstmt.executeUpdate();

				System.out.println("treatments ���̺� ������ �߰� ����");
				System.out.println("");

			} catch (Exception e9) {
				System.out.println("treatments ���̺� ������ �߰� ���� : " + e9);
			}

		}
	}

	private class ActionListenerInputCharts implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				frame = new JFrame("Charts ������ �Է�");
				frame.setLayout(new BorderLayout());

				inputPanel = new JPanel();
				inputPanel.setLayout(new GridLayout(6, 2, 5, 5));

				chart_CI = new JTextField("");
				chart_TI = new JTextField("");
				char_DI = new JTextField("");
				char_PI = new JTextField("");
				char_NI = new JTextField("");
				char_CC = new JTextField("");

				inputPanel.add(new JLabel("��Ʈ ��ȣ"));
				inputPanel.add(chart_CI);
				inputPanel.add(new JLabel("���� ID"));
				inputPanel.add(chart_TI);
				inputPanel.add(new JLabel("�ǻ� ID"));
				inputPanel.add(char_DI);
				inputPanel.add(new JLabel("ȯ�� ID"));
				inputPanel.add(char_PI);
				inputPanel.add(new JLabel("��ȣ�� ID"));
				inputPanel.add(char_NI);
				inputPanel.add(new JLabel("��Ʈ ����"));
				inputPanel.add(char_CC);

				completeInputPanel = new JPanel();
				completeInputPanel.setLayout(new BorderLayout());
				completeInputBtn = new JButton("Charts ���̺� �ش� ������ �߰�");
				completeInputBtn.addActionListener(new ActionListenerCompleteInputCharts());
				completeInputPanel.add(completeInputBtn);

				frame.add(inputPanel, BorderLayout.CENTER);
				frame.add(completeInputPanel, BorderLayout.SOUTH);

				frame.setSize(600, 300);
				frame.setVisible(true);
				frame.setLocation(400, 375);

			} catch (Exception e6) {
				System.out.println("charts ������ �Է� ���� : " + e6);
				System.out.println("");
			}

		}

	}

	private class ActionListenerCompleteInputCharts implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				System.out.println("charts ���̺� ������ �߰� �õ�...");

				query = "INSERT INTO charts VALUES (?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(query);

				pstmt.setString(1, chart_CI.getText());
				pstmt.setInt(2, Integer.parseInt(chart_TI.getText()));
				pstmt.setInt(3, Integer.parseInt(char_DI.getText()));
				pstmt.setInt(4, Integer.parseInt(char_PI.getText()));
				pstmt.setInt(5, Integer.parseInt(char_NI.getText()));
				pstmt.setString(6, char_CC.getText());
				pstmt.executeUpdate();

				System.out.println("charts ���̺� ������ �߰� ����");
				System.out.println("");

			} catch (Exception e9) {
				System.out.println("charts ���̺� ������ �߰� ���� : " + e9);
			}

		}
	}

	// �˻� 1 Action Listener
	private class ActionListenerSelect1 implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			frame = new JFrame("�˻��� ���̺� �����ϸ� �ش� ���̺� ���");
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
				selOutputTArea.append("�ǻ�ID" + "\t" + "����������" + "\t" + "����" + "\t" + "����" + "\t" + "��ȭ��ȣ" + "\t\t"
						+ "�̸���" + "\t\t" + "����" + "\n\n");

				query = "select * from doctors";
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4)
							+ "\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\t\t" + rs.getString(7) + "\n";
					selOutputTArea.append(str);
				}

			} catch (Exception e10) {
				System.out.println("doctors ���̺� ��� ���� : " + e10);
			}

		}

	}

	private class ActionListenerSelectNurses implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				selOutputTArea.setText("");
				selOutputTArea.append("��ȣ��ID" + "\t" + "������" + "\t" + "����" + "\t" + "����" + "\t" + "��ȭ��ȣ" + "\t\t"
						+ "�̸���" + "\t\t" + "����" + "\n\n");

				query = "select * from nurses";
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4)
							+ "\t" + rs.getString(5) + "\t\t" + rs.getString(6) + "\t\t" + rs.getString(7) + "\n";
					selOutputTArea.append(str);
				}

			} catch (Exception e10) {
				System.out.println("nurses ���̺� ��� ���� : " + e10);
			}

		}

	}

	private class ActionListenerSelectPatients implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				selOutputTArea.setText("");
				selOutputTArea.append("ȯ�� ID" + "\t" + "��ȣ�� ID" + "\t" + "�ǻ� ID" + "\t" + "ȯ�� ����" + "\t" + "ȯ�� ����"
						+ "\t" + "�ֹι�ȣ" + "\t" + "�ּ�" + "\t" + "��ȭ��ȣ" + "\t\t" + "�̸���" + "\t\t" + "����" + "\n\n");

				query = "select * from patients";
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4)
							+ "\t" + rs.getString(5) + "\t" + rs.getString(6) + "\t" + rs.getString(7) + "\t"
							+ rs.getString(8) + "\t\t" + rs.getString(9) + "\t\t" + rs.getString(10) + "\n";
					selOutputTArea.append(str);
				}

			} catch (Exception e10) {
				System.out.println("patients ���̺� ��� ���� : " + e10);
			}

		}

	}

	private class ActionListenerSelectTreatments implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				selOutputTArea.setText("");
				selOutputTArea
						.append("����ID" + "\t" + "ȯ�� ID" + "\t" + "�ǻ� ID" + "\t" + "���� ����" + "\t\t" + "���� ��¥" + "\n\n");

				query = "select * from treatments";
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					String str = rs.getInt(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4)
							+ "\t\t" + rs.getString(5) + "\n";
					selOutputTArea.append(str);
				}

			} catch (Exception e10) {
				System.out.println("treatments ���̺� ��� ���� : " + e10);
			}

		}

	}

	private class ActionListenerSelectCharts implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			try {

				selOutputTArea.setText("");
				selOutputTArea.append("��Ʈ ��ȣ" + "\t" + "���� ID" + "\t" + "�ǻ� ID" + "\t" + "ȯ�� ID" + "\t" + "��ȣ�� ID"
						+ "\t" + "��Ʈ ����" + "\n\n");

				query = "select * from charts";
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					String str = rs.getString(1) + "\t" + rs.getInt(2) + "\t" + rs.getInt(3) + "\t" + rs.getInt(4)
							+ "\t" + rs.getInt(5) + "\t" + rs.getString(6) + "\n";
					selOutputTArea.append(str);
				}

			} catch (Exception e10) {
				System.out.println("charts ���̺� ��� ���� : " + e10);
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

				System.out.println("���α׷� ���� ����!");
				System.exit(0);

			}

		});

	}

}