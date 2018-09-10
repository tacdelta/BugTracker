import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Issue {

    public static void add(Connection ctn) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Name issue: ");
        String issueName = sc.nextLine();
        System.out.print("Choose severity: (1-trivial, 2-major, 3-critical, 0 - undefined): ");
        String sev = null;
        switch (Main.choice(3)) {
            case 1:
                sev = "trivial";
                break;
            case 2:
                sev = "major";
                break;
            case 3:
                sev = "critical";
                break;
            case 0:
                sev = "-undefined-";
                break;

        }
        System.out.print("Choose priority(1-low, 2-high, 3-urgent, 0 - undefined): ");
        String pri = null;
        switch (Main.choice(3)) {
            case 1:
                pri = "low";
                break;
            case 2:
                pri = "high";
                break;
            case 3:
                pri = "urgent";
                break;
            case 0:
                pri = "-undefined-";
                break;
        }
        System.out.print("Describe steps to reach: ");
        String steps = sc.nextLine();
        if (Project.showAmount(ctn) == 0) {
            System.out.println("No projects exists. Now returning to main menu.");
            return;
        }
        System.out.println("Total projects: " + Project.showAmount(ctn));
        System.out.println("Now choose project id to assign from list below: ");
        Project.showAll(ctn);
        System.out.print("Id: ");
        boolean noMistake = false;
        while (!noMistake) {
            try {
                boolean ifProjectExist = false;
                while (!ifProjectExist) {
                    Scanner scc = new Scanner(System.in);
                    int uid = scc.nextInt();
                    ifProjectExist = Project.checkout(ctn, uid);
                    if (ifProjectExist) {
                        String insertSQL = "insert into issues(name, severity, priority, stepsToReach, ownerID) values (?, ?, ?, ?, ?)";
                        PreparedStatement preparedStatement = ctn.prepareStatement(insertSQL);
                        preparedStatement.setString(1, issueName);
                        preparedStatement.setString(2, sev);
                        preparedStatement.setString(3, pri);
                        preparedStatement.setString(4, steps);
                        preparedStatement.setInt(5, uid);
                        preparedStatement.executeUpdate();
                        noMistake = true;
                    } else
                        System.out.print("Project with this id is not exist, please enter correct id: ");
                }
            } catch (InputMismatchException e) {
                System.out.print("This is not a number, please type a number: ");
            }

        }

    }

    public static void edit(Connection ctn, Integer issID) throws SQLException {
        Scanner sc = new Scanner(System.in);
        boolean notFinished = true;
        while (notFinished) {
            System.out.println("Select what to change: (1- name, 2- severity, 3-priority," + "\n" +
                    "                        4-steps to reach, 5 - parent project, 0 - return)    ");
            switch (Main.choice(5)) {
                case 1:
                    System.out.print("Rename issue: ");
                    String issueName = sc.nextLine();
                    String insertSQLname = "update  issues set name =? WHERE id = ?";
                    PreparedStatement preparedStatement = ctn.prepareStatement(insertSQLname);
                    preparedStatement.setString(1, issueName);
                    preparedStatement.setInt(2, issID);
                    preparedStatement.executeUpdate();
                    break;
                case 2:
                    System.out.print("Choose severity: (1-trivial, 2-major, 3-critical, 0 - undefined): ");
                    String sev = null;
                    switch (Main.choice(3)) {
                        case 1:
                            sev = "trivial";
                            break;
                        case 2:
                            sev = "major";
                            break;
                        case 3:
                            sev = "critical";
                            break;
                        case 0:
                            sev = "-undefined-";
                            break;
                    }
                    String insertSQLsev = "update  issues set severity =? WHERE id = ?";
                    PreparedStatement preparedStatement2 = ctn.prepareStatement(insertSQLsev);
                    preparedStatement2.setString(1, sev);
                    preparedStatement2.setInt(2, issID);
                    preparedStatement2.executeUpdate();
                    break;
                case 3:
                    System.out.print("Choose priority(1-low, 2-high, 3-urgent, 0 - undefined) : ");
                    String pri = null;
                    switch (Main.choice(3)) {
                        case 1:
                            pri = "low";
                            break;
                        case 2:
                            pri = "high";
                            break;
                        case 3:
                            pri = "urgent";
                            break;
                        case 0:
                            pri = "-undefined-";
                            break;
                    }
                    String insertSQLpri = "update  issues set priority =? WHERE id = ?";
                    PreparedStatement preparedStatement3 = ctn.prepareStatement(insertSQLpri);
                    preparedStatement3.setString(1, pri);
                    preparedStatement3.setInt(2, issID);
                    preparedStatement3.executeUpdate();
                    break;
                case 4:
                    System.out.print("Describe steps to reach: ");
                    String steps = sc.nextLine();
                    String insertSQLstr = "update  issues set stepsToReach =? WHERE id = ?";
                    PreparedStatement preparedStatement4 = ctn.prepareStatement(insertSQLstr);
                    preparedStatement4.setString(1, steps);
                    preparedStatement4.setInt(2, issID);
                    preparedStatement4.executeUpdate();
                    break;
                case 5:
                    if (Project.showAmount(ctn) == 0) {
                        System.out.println("No projects exists. Now returning to main menu.");
                        return;
                    }
                    System.out.println("Total projects: " + Project.showAmount(ctn));
                    System.out.print("Now choose owner id to assign from list below: " + "\n");
                    Project.showAll(ctn);
                    boolean noMistake = false;
                    while (!noMistake) {
                        try {
                            boolean ifProjectExist = false;
                            while (!ifProjectExist) {
                                Scanner scc = new Scanner(System.in);
                                int uid = scc.nextInt();
                                ifProjectExist = Project.checkout(ctn, uid);
                                if (ifProjectExist) {
                                    String insertSQLown = "update  issues set ownerID =? WHERE id = ?";
                                    PreparedStatement preparedStatement5 = ctn.prepareStatement(insertSQLown);
                                    preparedStatement5.setInt(1, uid);
                                    preparedStatement5.setInt(2, issID);
                                    preparedStatement5.executeUpdate();
                                } else
                                    System.out.print("Project with this id is not exist, please enter correct id: ");
                            }
                        } catch (InputMismatchException e) {
                            System.out.print("This is not a number, please type a number: ");
                        }

                    }

                    break;
                case 0:
                    return;
            }
            System.out.println("Do you want to keep editing this issue ? (1- yes, 0 - return)");
            int checkout = sc.nextInt();
            switch (checkout) {
                case 1:
                    notFinished = true;
                    break;
                case 0:
                    notFinished = false;
                    break;
            }
        }
    }

    public static void delete(Connection ctn, int issID) throws SQLException {
        String SQL = "DELETE FROM issues WHERE id = ? ";
        PreparedStatement pstmt = null;
        pstmt = ctn.prepareStatement(SQL);
        pstmt.setInt(1, issID);
        pstmt.executeUpdate();
    }

    public static void showAll(Connection ctn) throws SQLException {
        String selectSQL = "SELECT * FROM issues";
        PreparedStatement preparedStatement = ctn.prepareStatement(selectSQL);
        ResultSet rs = preparedStatement.executeQuery();

        System.out.println("+----------------------------------------------------------------------------------------------------------------+");
        System.out.print("|");
        System.out.printf("%-4s%-11s%-14s%-14s%-60s%-5s%3s%n", "ID", "NAME", "SEVERITY", "PRIORITY", "STEPS TO REACH", "PROJECT", "|");
        System.out.println("+----------------------------------------------------------------------------------------------------------------+");
        while (rs.next()) {
            System.out.print("|");
            System.out.printf("%-4.4s%-11.9s%-14.14s%-14.14s%-60.60s%4.4s%6s%n", rs.getInt("id"), rs.getString("name"), rs.getString("severity"), rs.getString("priority"), rs.getString("stepsToReach"), Project.getName(ctn, rs.getInt("ownerID")), "|");
            System.out.println("+----------------------------------------------------------------------------------------------------------------+");
        }
    }

    public static int showAmount(Connection ctn) throws SQLException {
        String selectSQL = "SELECT count(*) FROM issues";
        PreparedStatement preparedStatement = ctn.prepareStatement(selectSQL);
        ResultSet rs = preparedStatement.executeQuery(
        );
        while (rs.next()) {
            int count = rs.getInt(1);
            return count;
        }
        return 0;
    }

    public static boolean checkout(Connection ctn, int i) throws SQLException {
        try (PreparedStatement ps = ctn.prepareStatement("SELECT 1 from issues where id = ?")) {
            ps.setInt(1, i);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return true;
            }
        }
        return false;
    }

}
