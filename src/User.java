import java.sql.*;
import java.util.Scanner;

public class User {

    public static void add(Connection connection) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Name user: ");
        String userName = sc.nextLine();
        String insertSQL = "insert into users(name) values (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setString(1, userName);
        preparedStatement.executeUpdate();
    }

    public static void edit(Connection ctn, int uid) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Select what to change: (1- name, 0 - return");
        switch (Main.choice(1)) {
            case 1:
                System.out.print("Rename user: ");
                String userName = sc.nextLine();
                String insertSQLname = "update  users set name =? WHERE id = ?";
                PreparedStatement preparedStatement = ctn.prepareStatement(insertSQLname);
                preparedStatement.setString(1, userName);
                preparedStatement.setInt(2, uid);
                preparedStatement.executeUpdate();
                break;
            case 0:
                return;
        }
    }

    public static void delete(Connection ctn, int projID) throws SQLException {
        String SQL = "DELETE FROM users WHERE id = ? ";
        PreparedStatement pstmt = null;
        pstmt = ctn.prepareStatement(SQL);
        pstmt.setInt(1, projID);
        pstmt.executeUpdate();
    }

    public static void showAll(Connection ctn) throws SQLException {
        String selectSQL = "SELECT * FROM users";
        PreparedStatement preparedStatement = ctn.prepareStatement(selectSQL);
        ResultSet rs = preparedStatement.executeQuery();
        System.out.println("+----------------+");
        System.out.print("|");
        System.out.printf("%1s%7s%8s%n", "ID", "NAME", "|");
        System.out.println("+----------------+");
        while (rs.next()) {

            System.out.print("|");
            System.out.printf("%-5s%-11.9s%s%n", rs.getInt("id"), rs.getString("name"), "|");
            System.out.println("+----------------+");
        }
    }

    public static int showAmount(Connection ctn) throws SQLException {
        String selectSQL = "SELECT count(*) FROM users";
        PreparedStatement preparedStatement = ctn.prepareStatement(selectSQL);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            int count = rs.getInt(1);
            return count;
        }
        return 0;
    }

    public static void showProjects(Connection ctn, int oid) throws SQLException {
        String selectSQL = "SELECT * FROM projects WHERE ownerID = ?";
        PreparedStatement preparedStatement = ctn.prepareStatement(selectSQL);
        preparedStatement.setInt(1, oid);
        ResultSet rs = preparedStatement.executeQuery();
        System.out.println("+------------------+");
        System.out.print("|");
        System.out.printf("%-4s%-9s%-5s%1s%n", "ID", "NAME", "USER", "|");
        System.out.println("+------------------+");

        while (rs.next()) {
            System.out.print("|");
            System.out.printf("%-4.4s%-11.9s%-3.4s%s%n", rs.getInt("id"), rs.getString("name"), rs.getInt("ownerID"), "|");
            System.out.println("+------------------+");
        }
    }

    public static int showProjectsAmount(Connection ctn, int oid) throws SQLException {
        String selectSQL = "SELECT count(*) FROM projects WHERE ownerID = ?";
        PreparedStatement preparedStatement = ctn.prepareStatement(selectSQL);
        preparedStatement.setInt(1, oid);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            int count = rs.getInt(1);
            return count;
        }
        return 0;
    }

    public static boolean checkout(Connection ctn, int i) throws SQLException {
        try (PreparedStatement ps = ctn.prepareStatement("SELECT 1 from users where id = ?")) {
            ps.setInt(1, i);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return true;
            }
        }
        return false;
    }
}

