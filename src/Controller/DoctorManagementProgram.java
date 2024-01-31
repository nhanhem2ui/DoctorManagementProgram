package Controller;

import Model.DoctorDataBase;
import View.Menu;

public class DoctorManagementProgram {

    Handler handler = new Handler();
    DoctorDataBase database = new DoctorDataBase();

    public void displayMainMenu() {
        Menu<String> mainMenu = new Menu<>("Doctor management", new String[]{
            "Add doctor",
            "Update doctor",
            "Delete doctor",
            "Search doctor",
            "Exit"
        }) {
            @Override
            public void execute(int choice) {
                switch (choice) {
                    case 1 ->
                        handler.addDoctor();
                    case 2 ->
                        handler.updateDoctor();
                    case 3 ->
                        handler.deleteDoctor();
                    case 4 ->
                        handler.searchDoctor();
                    case 5 ->
                        System.exit(0);
                    default ->
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        };
        mainMenu.run();
    }

    public static void main(String[] args) {
        DoctorManagementProgram main = new DoctorManagementProgram();
        main.displayMainMenu();
    }
}
