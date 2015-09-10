import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Scanner;

public class phoneContact {
	public static void main(String[] args) {
		String username = "root";
		String password = "123";
		String driver = "com.mysql.jdbc.Driver";
		String jdbcstr = "jdbc:mysql://localhost/csie101502039";
		Scanner input = new Scanner(System.in);
		int choice = 0;
		try {
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(jdbcstr,
					username, password);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("SELECT*FROM phonecontact");
			ResultSetMetaData metaData = resultSet.getMetaData();
			int numberOfData = metaData.getColumnCount();
			for (int i = 1; i <= numberOfData; i++) {
				System.out.printf("%-4s\t", metaData.getColumnName(i));
			}
			while (resultSet.next()) {
				System.out.print("\n " + resultSet.getString("No") + "\t"
						+ resultSet.getString("name") + "\t"
						+ resultSet.getString("phoneNumber"));
			}
			System.out.println("\n�o�Ocsie101502039���q��ï,�аݱz�n...?(��J�Ʀr)");
			while (choice != 5) {
				System.out
						.print("\n1.�s�W���-�W�[�@���q�T����Ʀܸ�Ʈw	\n2.�R�����-�q��Ʈw�R���@�����	\n3.�ק���-�ק�@���w�s�b�����	\n4.��ܥ������-��ܥ����q�T�����\n5.���}\n");
				
				Statement smt = connection.createStatement(
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
				choice = input.nextInt();
				switch (choice) {
				case 1:
					ResultSet rs1 = smt
							.executeQuery("SELECT*FROM phonecontact");
					rs1.moveToInsertRow();
					System.out.println("��JNo");
					String noString = input.next();
					rs1.updateString("No", noString);
					System.out.println("��Jname");
					String nameString = input.next();
					rs1.updateString("name", nameString);
					System.out.println("��JphoneNumber");
					String phoneNumberString = input.next();
					rs1.updateString("phoneNumber", phoneNumberString);
					rs1.insertRow();
					rs1.close();
					break;
				case 2:
					System.out.println("��ܱ��R����ƪ�No");
					String deleteNo = input.next();
					ResultSet rs2 = smt.executeQuery("SELECT*FROM phonecontact WHERE No='"+deleteNo+"'");
					rs2.last();
					rs2.deleteRow();
					rs2.close();
					break;
				case 3:
					System.out.println("��ܱ��ק��ƪ��s��");
					String modifyRow = input.next();
					ResultSet rs3 = smt.executeQuery("SELECT*FROM phonecontact WHERE No='"+modifyRow+"'");
					rs3.last();
					System.out.print("�ק�m�W��");
					String reName = input.next();
					rs3.updateString("name",reName);
					System.out.print("�ק�q�ܸ��X��");
					String rePhoneNumber = input.next();
					rs3.updateString("PhoneNumber",rePhoneNumber);
					rs3.updateRow();
					rs3.close();
				case 4:
					ResultSet rs4 = statement
							.executeQuery("SELECT*FROM phonecontact");
					ResultSetMetaData metaData4 = rs4.getMetaData();
					numberOfData = metaData4.getColumnCount();
					for (int i = 1; i <= numberOfData; i++) {
						System.out.printf("%-4s\t", metaData4.getColumnName(i));
					}
					while (rs4.next()) {
						System.out.print("\n " + rs4.getString("No")
								+ "\t" + rs4.getString("name") + "\t"
								+ rs4.getString("phoneNumber"));
					}
					break;
				case 5 :
					System.out.println("bye~bye~");
					break;
				}
			}
			statement.close();
			connection.close();

		} catch (Exception e) {
			System.out.println("Error " + driver);
			e.printStackTrace();
		}
	}
}