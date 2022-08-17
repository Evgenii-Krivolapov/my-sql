import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/purchaselist";
        String user = "root";
        String pass = "*";
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT pl.course_name, count(pl.course_name) / " +
                            "(MAX(MONTH(pl.subscription_date)) - " +
                            "MIN(MONTH(pl.subscription_date)) + 1) `average_purchases`\n" +
                            "FROM purchaselist pl\n" +
                            "GROUP BY pl.course_name");
            while (resultSet.next()) {
                String courseName = resultSet.getString("course_name");
                String averagePurchases = resultSet.getString("average_purchases");
                double average_purchases = Double.parseDouble(averagePurchases);
                System.out.println(courseName + " = " + average_purchases);
            }
            connection.close();
            statement.close();
            resultSet.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}