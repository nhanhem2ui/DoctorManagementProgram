package Controller;

import Model.Doctor;
import Model.DoctorDataBase;

import java.util.HashMap;
import java.util.Scanner;

public class Handler {

    private Scanner sc;
    DoctorDataBase doctorDataBase;

    public Handler() {
        sc = new Scanner(System.in);
        doctorDataBase = new DoctorDataBase();
    }

    public void addDoctor() {
        try {
            System.out.println("Enter Doctor Code: ");
            String code = sc.next();

            System.out.println("Enter Doctor Name: ");
            String name = sc.next();

            System.out.println("Enter Doctor Specialization: ");
            String specialization = sc.next();

            System.out.println("Enter Doctor Availability: ");
            int availability = sc.nextInt();

            Doctor newDoctor = new Doctor(code, name, specialization, availability);
            doctorDataBase.addDoctor(newDoctor);
            System.out.println("Doctor added successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateDoctor() {
        try {
            System.out.println("Enter Doctor Code to Update: ");
            String code = sc.next();

            Doctor existingDoctor = doctorDataBase.searchDoctor(code).get(code);
            if (existingDoctor == null) {
                System.out.println("Doctor not found!");
                return;
            }

            System.out.println("Enter New Doctor Name (leave blank to keep existing): ");
            String mame = sc.next();
            if (!mame.isBlank()) {
                existingDoctor.setName(mame);
            }

            System.out.println("Enter New Doctor Specialization (leave blank to keep existing): ");
            String newSpecialization = sc.next();
            if (!newSpecialization.isBlank()) {
                existingDoctor.setSpecialization(newSpecialization);
            }

            System.out.println("Enter New Doctor Availability (enter -1 to keep existing): ");
            int newAvailability = sc.nextInt();
            if (newAvailability != -1) {
                existingDoctor.setAvailability(newAvailability);
            }

            doctorDataBase.updateDoctor(existingDoctor);
            System.out.println("Doctor updated successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteDoctor() {
        try {
            System.out.println("Enter Doctor Code to Delete: ");
            String code = sc.next();

            Doctor existingDoctor = doctorDataBase.searchDoctor(code).get(code);
            if (existingDoctor == null) {
                System.out.println("Doctor not found!");
                return;
            }

            doctorDataBase.deleteDoctor(existingDoctor);
            System.out.println("Doctor deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void searchDoctor() {
        try {
            System.out.println("Enter Search String: ");
            String input = sc.next();

            HashMap<String, Doctor> searchResults = doctorDataBase.searchDoctor(input);
            if (searchResults.isEmpty()) {
                System.out.println("No matching doctors found.");
            } else {
                System.out.println("Search Results:");
                for (Doctor doctor : searchResults.values()) {
                    System.out.println(doctor);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
