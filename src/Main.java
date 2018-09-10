import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:btt.db");
            startApp(connection);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void startApp(Connection connection) throws SQLException {
        boolean notExit = true;
        while (notExit) {
            System.out.println("\n" + "________________________________________" + "\n"
                    + "________________MAIN MENU_______________" + "\n"
                    + "press 1 to add Data" + "\n"
                    + "press 2 to view Data" + "\n"
                    + "press 3 to edit Data" + "\n"
                    + "press 4 to delete Data" + "\n"
                    + "press 5 to generate report" + "\n"
                    + "press 0 to exit" + "\n"
                    + "________________________________________");
            switch (choice(5)) {
                case 1:
                    addInfo(connection);
                    break;
                case 2:
                    showInfo(connection);
                    break;
                case 3:
                    editInfo(connection);
                    break;
                case 4:
                    deleteInfo(connection);
                    break;
                case 5:
                    getReport(connection);
                    break;
                case 0:
                    notExit = false;
                    break;
            }
        }

    }

    private static void editInfo(Connection connection) throws SQLException {
        boolean stay = true;
        while (stay) {
            System.out.println("\n" + "________________________________________" + "\n"
                    + "____________EDIT DATA SCREEN____________" + "\n"
                    + "press 1 to edit user" + "\n"
                    + "press 2 to edit project" + "\n"
                    + "press 3 to edit issue" + "\n"
                    + "press 0 to return back" + "\n"
                    + "________________________________________");
            switch (choice(3)) {
                case 1:
                    if (User.showAmount(connection) == 0) {
                        System.out.println("No users exists. Now returning to main menu.");
                        return;
                    }
                    System.out.println("Total users: " + User.showAmount(connection));
                    System.out.println("Type user ID to edit: ");
                    User.showAll(connection);
                    boolean noMistake2 = false;
                    while (!noMistake2) {
                        try {
                            Scanner scc = new Scanner(System.in);
                            boolean ifUserExist = false;
                            while (!ifUserExist) {
                                int i = scc.nextInt();
                                ifUserExist = User.checkout(connection, i);
                                if (ifUserExist) {
                                    User.edit(connection, i);
                                    noMistake2 = true;
                                } else
                                    System.out.print("User with this id is not exist, please enter correct id: ");
                            }
                        } catch (InputMismatchException e) {
                            System.out.print("This is not a number, please type a number: ");
                        }
                    }
                    break;
                case 2:
                    if (Project.showAmount(connection) == 0) {
                        System.out.println("No projects exists. Now returning to main menu.");
                        return;
                    }
                    System.out.println("Total projects: " + Project.showAmount(connection));
                    System.out.println("Type project ID to edit: ");
                    Project.showAll(connection);
                    boolean noMistake = false;
                    while (!noMistake) {
                        try {
                            boolean ifProjectExist = false;
                            while (!ifProjectExist) {
                                Scanner scc = new Scanner(System.in);
                                int i = scc.nextInt();
                                ifProjectExist = Project.checkout(connection, i);
                                if (ifProjectExist) {
                                    Project.edit(connection, i);
                                    noMistake = true;
                                } else
                                    System.out.print("Project with this id is not exist, please enter correct id: ");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("This is not a number, please type a number: ");
                        }
                    }
                    break;
                case 3:
                    if (Issue.showAmount(connection) == 0) {
                        System.out.println("No issues exists. Now returning to main menu.");
                        return;
                    }
                    System.out.println("Total issues: " + Issue.showAmount(connection));
                    System.out.println("Issue list: " + "\n");
                    Issue.showAll(connection);
                    boolean noMistake3 = false;
                    while (!noMistake3) {
                        try {
                            System.out.println("Type issue ID to edit: ");
                            boolean ifIssueExists = false;
                            while (!ifIssueExists) {
                                Scanner scc = new Scanner(System.in);
                                int i = scc.nextInt();
                                ifIssueExists = Issue.checkout(connection, i);
                                if (ifIssueExists) {
                                    Issue.edit(connection, i);
                                    noMistake3 = true;
                                } else
                                    System.out.print("Project with this id is not exist, please enter correct id: ");
                            }
                        } catch (InputMismatchException e) {
                            System.out.print("This is not a number, please type a number: ");
                        }
                    }
                    break;
                case 0:
                    stay = false;
                    break;
            }
            System.out.println("Do you want to edit another entity (1- yes, 0 - no) ?");
            switch (choice(3)) {
                case 1:
                    stay = true;
                    break;
                case 0:
                    stay = false;
                    break;
            }
        }
    }

    private static void deleteInfo(Connection connection) throws SQLException {
        System.out.println("\n" + "________________________________________" + "\n"
                + "__________DELETE DATA SCREEN____________" + "\n"
                + "press 1 to delete user" + "\n"
                + "press 2 to delete project" + "\n"
                + "press 3 to delete issue" + "\n"
                + "press 0 to return back" + "\n"
                + "________________________________________");
        switch (choice(3)) {
            case 1:
                if (User.showAmount(connection) == 0) {
                    System.out.println("No users exists. Now returning to main menu.");
                    return;
                }
                System.out.println("Type user ID to delete: ");
                User.showAll(connection);
                boolean noMistake2 = false;
                while (!noMistake2) {
                    try {
                        Scanner scc = new Scanner(System.in);
                        boolean ifUserExist = false;
                        while (!ifUserExist) {
                            int i = scc.nextInt();
                            ifUserExist = User.checkout(connection, i);
                            if (ifUserExist) {
                                User.delete(connection, i);
                                noMistake2 = true;
                            } else
                                System.out.print("User with this id is not exist, please enter correct id: ");
                        }
                    } catch (InputMismatchException e) {
                        System.out.print("This is not a number, please type a number: ");
                    }
                }
                break;
            case 2:
                if (Project.showAmount(connection) == 0) {
                    System.out.println("No projects exists. Now returning to main menu.");
                    return;
                }
                System.out.println("Total projects: " + Project.showAmount(connection));
                System.out.println("Type project ID to delete: ");
                Project.showAll(connection);
                boolean noMistake = false;
                while (!noMistake) {
                    try {
                        boolean ifProjectExist = false;
                        while (!ifProjectExist) {
                            Scanner scc = new Scanner(System.in);
                            int i = scc.nextInt();
                            ifProjectExist = Project.checkout(connection, i);
                            if (ifProjectExist) {
                                Project.delete(connection, i);
                                noMistake = true;
                            } else
                                System.out.print("Project with this id is not exist, please enter correct id: ");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("This is not a number, please type a number: ");
                    }
                }
                break;
            case 3:
                if (Issue.showAmount(connection) == 0) {
                    System.out.println("No issues exists. Now returning to main menu.");
                    return;
                }
                System.out.println("Total issues: " + Issue.showAmount(connection));
                System.out.println("Issue list: " + "\n");
                Issue.showAll(connection);
                boolean noMistake3 = false;
                while (!noMistake3) {
                    try {
                        System.out.println("Type issue ID to delete: ");
                        boolean ifIssueExists = false;
                        while (!ifIssueExists) {
                            Scanner scc = new Scanner(System.in);
                            int i = scc.nextInt();
                            ifIssueExists = Issue.checkout(connection, i);
                            if (ifIssueExists) {
                                Issue.delete(connection, i);
                                noMistake3 = true;
                            } else
                                System.out.print("Project with this id is not exist, please enter correct id: ");
                        }
                    } catch (InputMismatchException e) {
                        System.out.print("This is not a number, please type a number: ");
                    }
                }
                break;
            case 0:
                break;
        }


    }

    private static void getReport(Connection connection) throws SQLException {
        System.out.println("\n" + "________________________________________" + "\n"
                + "___________GENERATE NEW REPORT__________");
        boolean stay = true;
        while (stay) {
            System.out.println("press 1 to view users list " + "\n"
                    + "press 0 to return back" + "\n"
                    + "________________________________________");
            switch (choice(1)) {
                case 1:
                    User.showAll(connection);
                    break;
                case 0:
                    return;
            }
            if (User.showAmount(connection) == 0) {
                System.out.println("No users exists. Now returning to main menu.");
                return;
            }
            System.out.println("Total users: " + User.showAmount(connection));
            System.out.print("Type user id: ");
            boolean noMistake = false;
            while (!noMistake) {
                try {
                    boolean ifUserExist = false;
                    while (!ifUserExist) {
                        Scanner scc = new Scanner(System.in);  //exception turns scanner off everytime,so you have to re-initialize it
                        int uid = scc.nextInt();
                        ifUserExist = User.checkout(connection, uid);
                        if (ifUserExist) {
                            if (User.showProjectsAmount(connection, uid) == 0) {
                                System.out.println("This user has no projects assigned. Now returning to main menu.");
                                return;
                            }
                            System.out.println("Total projects: " + Project.showAmount(connection));
                            User.showProjects(connection, uid);
                            noMistake = true;
                        } else
                            System.out.print("User with this id is not exist, please enter correct id: ");
                    }
                } catch (InputMismatchException e) {
                    System.out.print("This is not a number, please type a number: ");
                }
            }
            System.out.print("Type project id: ");
            boolean noMistake2 = false;
            while (!noMistake2) {
                try {
                    boolean ifProjectNumExist = false;
                    while (!ifProjectNumExist) {
                        Scanner scc = new Scanner(System.in);
                        int projID = scc.nextInt();
                        ifProjectNumExist = Project.checkout(connection, projID);
                        if (ifProjectNumExist) {
                            if (Project.showIssuesAmount(connection, projID) == 0) {
                                System.out.println("This project contains no issues. Now returning to main menu.");
                                return;
                            }
                            System.out.println("Total issues: " + Issue.showAmount(connection));
                            Project.showIssuesAmount(connection, projID);
                            Project.showIssues(connection, projID);
                            noMistake2 = true;
                        } else
                            System.out.print("Project with this id is not exist, please enter correct id: ");
                    }
                } catch (InputMismatchException e) {
                    System.out.print("This is not a number, please type a number: ");
                }
            }
            System.out.print("Do you want to generate another report (1- yes, 0 - no) ?");
            switch (choice(1)) {
                case 1:
                    stay = true;
                    break;
                case 0:
                    stay = false;
                    break;
            }
        }
    }

    private static void showInfo(Connection connection) throws SQLException {
        boolean notExit = true;
        while (notExit) {
            System.out.println("\n" + "________________________________________" + "\n"
                    + "____________SHOW DATA SCREEN____________" + "\n"
                    + "press 1 to view users list" + "\n"
                    + "press 2 to view projects list" + "\n"
                    + "press 3 to view issues list" + "\n"
                    + "press 0 to return back" + "\n" +
                    "________________________________________");

            switch (choice(3)) {
                case 1:
                    if (User.showAmount(connection) == 0) {
                        System.out.println("No users exists. Now returning to main menu.");
                        return;
                    }
                    System.out.println("Total users: " + User.showAmount(connection));
                    User.showAll(connection);
                    break;
                case 2:
                    if (Project.showAmount(connection) == 0) {
                        System.out.println("No projects exists. Now returning to main menu.");
                        return;
                    }
                    System.out.println("Total projects: " + Project.showAmount(connection));
                    Project.showAll(connection);
                    break;
                case 3:
                    if (Issue.showAmount(connection) == 0) {
                        System.out.println("No issues exists. Now returning to main menu.");
                        return;
                    }
                    System.out.println("Total issues: " + Issue.showAmount(connection));
                    Issue.showAll(connection);
                    break;
                case 0:
                    notExit = false;
                    break;
            }
        }
    }

    private static void addInfo(Connection connection) throws SQLException {
        boolean notExit = true;
        while (notExit) {
            System.out.println("\n" + "________________________________________" + "\n"
                    + "_____________ADD DATA SCREEN____________" + "\n"
                    + "press 1 to add user" + "\n"
                    + "press 2 to add project" + "\n"
                    + "press 3 to add issue" + "\n"
                    + "press 0 to return back " + "\n"
                    + "________________________________________");
            switch (choice(3)) {
                case 1:
                    User.add(connection);
                    break;
                case 2:
                    Project.add(connection);
                    break;
                case 3:
                    Issue.add(connection);
                    break;
                case 0:
                    notExit = false;
                    break;
            }
        }
    }

    public static int choice(int max) {
        boolean isCorrect = false;
        while (!isCorrect) {
            try {
                Scanner scan = new Scanner(System.in);
                int number = scan.nextInt();
                if (number >= 0 && number <= max && number % 1 == 0) {
                    isCorrect = true;
                    return number;
                } else
                    System.out.println("Wrong number, please enter correct number: ");
            } catch (InputMismatchException e) {
                System.out.print("This is not a number, please type a number: ");
            }

        }
        return 0;
    }

}