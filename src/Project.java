import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Project {

    public static void add(Connection ctn) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Name project: ");
        String s = sc.nextLine();
        if (User.showAmount(ctn) == 0) {
            System.out.println("No users exists. Now returning to main menu.");
            return;
        }
        System.out.println("Total users: " + User.showAmount(ctn));
        System.out.println("Type user ID to assign from list below: ");
        User.showAll(ctn);
        boolean noMistake = false;
        while (!noMistake) {
            try {
                boolean ifUserExist = false;
                while (!ifUserExist) {
                    Scanner scc = new Scanner(System.in);
                    int uid = scc.nextInt();
                    ifUserExist = User.checkout(ctn, uid);
                    if (ifUserExist) {
                        String insertSQL = "insert into projects(name, ownerID) values (?, ?)";
                        PreparedStatement preparedStatement = ctn.prepareStatement(insertSQL);
                        preparedStatement.setString(1, s);
                        preparedStatement.setInt(2, uid);
                        preparedStatement.executeUpdate();
                        noMistake = true;
                    } else
                        System.out.print("User with this id is not exist, please enter correct id: ");
                }
            } catch (InputMismatchException e) {
                System.out.print("This is not a number, please type a number: ");
            }
        }
    }

    public static void edit(Connection ctn, int projID) throws SQLException {
        Scanner sc = new Scanner(System.in);
        boolean notFinished = true;
        while (notFinished) {
            System.out.print("Select what to change: (1- name,  2 - owner, 0 - return");
            switch (Main.choice(2)) {
                case 1:
                    System.out.print("Rename project: ");
                    String projectName = sc.nextLine();
                    String insertSQLname = "update  projects set name =? WHERE id = ?";
                    PreparedStatement preparedStatement = ctn.prepareStatement(insertSQLname);
                    preparedStatement.setString(1, projectName);
                    preparedStatement.setInt(2, projID);
                    preparedStatement.executeUpdate();
                    break;
                case 2:
                    if (User.showAmount(ctn) == 0) {
                        System.out.println("No users exists. Now returning to main menu.");
                        return;
                    }
                    System.out.println("Total users: " + User.showAmount(ctn));
                    System.out.println("Now choose user id to assign from list below: " + "\n");
                    User.showAll(ctn);
                    boolean noMistake = false;
                    while (!noMistake) {
                        try {
                            boolean ifUserExist = false;
                            while (!ifUserExist) {
                                Scanner scc = new Scanner(System.in);
                                int uid = scc.nextInt();
                                ifUserExist = User.checkout(ctn, uid);
                                if (ifUserExist) {
                                    String insertSQLown = "update  projects set ownerID =? WHERE id = ?";
                                    PreparedStatement preparedStatement2 = ctn.prepareStatement(insertSQLown);
                                    preparedStatement2.setInt(1, uid);
                                    preparedStatement2.setInt(2, projID);
                                    preparedStatement2.executeUpdate();
                                    noMistake = true;
                                } else
                                    System.out.print("User with this id is not exist, please enter correct id: ");
                            }
                        } catch (InputMismatchException e) {
                            System.out.print("This is not a number, please type a number: ");
                        }
                    }
                    break;
                case 0:
                    return;
            }
            System.out.print("Do you want to keep editing this project ? (1- yes, 0 - return)");
            switch (Main.choice(1)) {
                case 1:
                    notFinished = true;
                    break;
                case 0:
                    notFinished = false;
                    break;
            }
        }

    }

    public static void delete(Connection ctn, int projID) throws SQLException {
        String SQL = "DELETE FROM projects WHERE id = ? ";
        PreparedStatement pstmt = null;
        pstmt = ctn.prepareStatement(SQL);
        pstmt.setInt(1, projID);
        pstmt.executeUpdate();
    }

    public static void showAll(Connection ctn) throws SQLException {
        String selectSQL = "SELECT * FROM projects";
        PreparedStatement preparedStatement = ctn.prepareStatement(selectSQL);
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

    public static int showAmount(Connection ctn) throws SQLException {
        String selectSQL = "SELECT count(*) FROM projects";
        PreparedStatement preparedStatement = ctn.prepareStatement(selectSQL);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            int count = rs.getInt(1);
            return count;

        }
        return 0;
    }

    public static void showIssues(Connection ctn, int oid) throws SQLException {
        String selectSQL = "SELECT * FROM issues WHERE ownerID = ?";
        PreparedStatement preparedStatement = ctn.prepareStatement(selectSQL);
        preparedStatement.setInt(1, oid);
        ResultSet rs = preparedStatement.executeQuery();
        System.out.println("+----------------------------------------------------------------------------------------------------------------+");
        System.out.print("|");
        System.out.printf("%-4s%-11s%-14s%-14s%-61s%-5s%2s%n", "ID", "NAME", "SEVERITY", "PRIORITY", "STEPS TO REACH", "PROJECT", "|");
        System.out.println("+----------------------------------------------------------------------------------------------------------------+");

        while (rs.next()) {
            System.out.print("|");
            System.out.printf("%-4.4s%-11.9s%-14.14s%-14.14s%-60.60s%4.4s%6s%n", rs.getInt("id"), rs.getString("name"), rs.getString("severity"), rs.getString("priority"), rs.getString("stepsToReach"), Project.getName(ctn, rs.getInt("ownerID")), "|");
            System.out.println("+----------------------------------------------------------------------------------------------------------------+");

        }
    }

    public static int showIssuesAmount(Connection ctn, int oid) throws SQLException {
        String selectSQL = "SELECT count(*) FROM issues WHERE ownerID = ?";
        PreparedStatement preparedStatement = ctn.prepareStatement(selectSQL);
        preparedStatement.setInt(1, oid);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            int count = rs.getInt(1);
            return count;
        }
        return 0;
    }

    public static String getName(Connection ctn, int id) throws SQLException {
        String selectSQL = "SELECT * FROM projects WHERE id = ?";
        PreparedStatement preparedStatement = ctn.prepareStatement(selectSQL);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            return rs.getString("name");
        }
        return "no name ";
    }

    public static boolean checkout(Connection ctn, int i) throws SQLException {
        try (PreparedStatement ps = ctn.prepareStatement("SELECT 1 from projects where id = ?")) {
            ps.setInt(1, i);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return true;
            }
        }
        return false;
    }
}