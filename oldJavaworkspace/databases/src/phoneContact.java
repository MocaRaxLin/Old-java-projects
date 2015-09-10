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
			System.out.println("\n這是csie101502039的電話簿,請問您要...?(輸入數字)");
			while (choice != 5) {
				System.out
						.print("\n1.新增資料-增加一筆通訊錄資料至資料庫	\n2.刪除資料-從資料庫刪除一筆資料	\n3.修改資料-修改一筆已存在的資料	\n4.顯示全部資料-顯示全部通訊錄資料\n5.離開\n");
				
				Statement smt = connection.createStatement(
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
				choice = input.nextInt();
				switch (choice) {
				case 1:
					ResultSet rs1 = smt
							.executeQuery("SELECT*FROM phonecontact");
					rs1.moveToInsertRow();
					System.out.println("輸入No");
					String noString = input.next();
					rs1.updateString("No", noString);
					System.out.println("輸入name");
					String nameString = input.next();
					rs1.updateString("name", nameString);
					System.out.println("輸入phoneNumber");
					String phoneNumberString = input.next();
					rs1.updateString("phoneNumber", phoneNumberString);
					rs1.insertRow();
					rs1.close();
					break;
				case 2:
					System.out.println("選擇欲刪除資料的No");
					String deleteNo = input.next();
					ResultSet rs2 = smt.executeQuery("SELECT*FROM phonecontact WHERE No='"+deleteNo+"'");
					rs2.last();
					rs2.deleteRow();
					rs2.close();
					break;
				case 3:
					System.out.println("選擇欲修改資料的編號");
					String modifyRow = input.next();
					ResultSet rs3 = smt.executeQuery("SELECT*FROM phonecontact WHERE No='"+modifyRow+"'");
					rs3.last();
					System.out.print("修改姓名為");
					String reName = input.next();
					rs3.updateString("name",reName);
					System.out.print("修改電話號碼為");
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