package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class DoctorDataBase {

    private HashMap<String, Doctor> doctors;
    private final String filePath = "DataBase.txt";

    public DoctorDataBase() {
        this.doctors = new HashMap<>();
        try {
            getInputFromFile();
        } catch (IOException e) {
        }
    }

    private void getInputFromFile() throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            try ( BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // data is stored in CSV format
                    // code,name,specialization,availability
                    String[] data = line.split(",");
                    if (data.length == 4) {
                        Doctor doctor = new Doctor(data[0], data[1], data[2], Integer.parseInt(data[3]));
                        doctors.put(doctor.getCode(), doctor);
                    }
                }
            }
        }
    }

    /**
     * <p>Write doctor to specific file path
     * If the doctor has been updated, find the existing line and update it
     * If the record doesn't exist, add it to the end of the fil</p>
     * I'm gonna assume that it will work.. it's like magic for real
     */
    private void writeToDatabaseFile() {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Doctor doctor : doctors.values()) {
                // code,name,specialization,availability
                String line = doctor.getCode() + "," + doctor.getName() + ","
                        + doctor.getSpecialization() + "," + doctor.getAvailability();

                if (!doctors.containsKey(doctor.getCode())) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    // If the doctor has been updated, find the existing line and update it
                    boolean updated = false;
                    try ( BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                        String currentLine;
                        while ((currentLine = reader.readLine()) != null) {
                            String[] data = currentLine.split(",");
                            if (data.length == 4 && data[0].equals(doctor.getCode())) {
                                writer.write(line);
                                writer.newLine();
                                updated = true;
                            } else {
                                writer.write(currentLine);
                                writer.newLine();
                            }
                        }
                    }
                    // If the record doesn't exist, add it to the end of the file
                    if (!updated) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
        }
    }

    /**
     * add new doctor and invoke writing to database
     *
     * @param doctor
     * @return true upon success
     * @throws Exception
     */
    public boolean addDoctor(Doctor doctor) throws Exception {
        if (doctors == null) {
            throw new Exception("Database does not exist");
        }

        if (doctor == null) {
            throw new Exception("Data does not exist");
        }

        if (doctors.containsKey(doctor.getCode())) {
            throw new Exception("Doctor code " + doctor.getCode() + " is duplicate");
        }

        doctors.put(doctor.getCode(), doctor);
        writeToDatabaseFile();
        return true;
    }

    /**
     * using "doctor.getCode()" and update, write to file
     *
     * @param doctor
     * @return true upon success
     * @throws Exception
     */
    public boolean updateDoctor(Doctor doctor) throws Exception {
        if (doctors == null) {
            throw new Exception("Database does not exist");
        }

        if (doctor == null) {
            throw new Exception("Data does not exist");
        }

        if (!doctors.containsKey(doctor.getCode())) {
            throw new Exception("Doctor code doesn't exist");
        }

        doctors.put(doctor.getCode(), doctor);
        writeToDatabaseFile();
        return true;
    }

    /**
     *
     * @param doctor
     * @return
     * @throws Exception
     */
    public boolean deleteDoctor(Doctor doctor) throws Exception {
        if (doctors == null) {
            throw new Exception("Database does not exist");
        }

        if (doctor == null) {
            throw new Exception("Data does not exist");
        }

        if (!doctors.containsKey(doctor.getCode())) {
            throw new Exception("Doctor code doesn't exist");
        }

        doctors.remove(doctor.getCode());
        writeToDatabaseFile();
        return true;
    }

    /**
     *
     * @param input
     * @return
     * @throws Exception
     */
    public HashMap<String, Doctor> searchDoctor(String input) throws Exception {
        if (doctors == null) {
            throw new Exception("Database does not exist");
        }

        HashMap<String, Doctor> searchResults = new HashMap<>();
        for (Doctor doctor : doctors.values()) {
            if (containsIgnoreCase(doctor.getCode(), input)
                    || containsIgnoreCase(doctor.getName(), input)
                    || containsIgnoreCase(doctor.getSpecialization(), input)
                    || String.valueOf(doctor.getAvailability()).contains(input)) {
                searchResults.put(doctor.getCode(), doctor);
            }
        }

        return searchResults;
    }

    private boolean containsIgnoreCase(String text, String search) {
        return text.toLowerCase().contains(search.toLowerCase());
    }
}
