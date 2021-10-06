package GUI;

import DTO.Injection;
import MyService.Validation;
import Storage.InjectionList;
import Storage.StudentList;
import Storage.VaccineList;
import java.util.ArrayList;
import java.util.Scanner;

public class VaccineManagement {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        InjectionList listOfInjections = new InjectionList();
        StudentList listOfStudent = new StudentList();
        VaccineList listOfVaccine = new VaccineList();
        listOfStudent.saveFile("student.dat");
        listOfVaccine.saveFile("vaccine.dat");
        ArrayList<Injection> file = new ArrayList<>();
        boolean flag = false;
        
        int choice = 0;
        do {
            System.out.println("0. Read injection file");
            System.out.println("1. Show injection information");
            System.out.println("2. Add new injection");
            System.out.println("3. Update injection");
            System.out.println("4. Delete injection");
            System.out.println("5. Search injection by student ID");
            System.out.println("6. Store data to file");
            System.out.println("Others quit");
            do {
                try {
                    System.out.println("Select option: ");
                    sc = new Scanner(System.in);
                    choice = sc.nextInt();
                    if (choice < 0) {
                        throw new Exception();
                    }
                    flag = false;
                } catch (Exception e) {
                    System.out.println("Please enter the valid number");
                    flag = true;
                }

            } while (flag);

            switch (choice) {
                case 0: 
                    String fileName = "injection.dat";
                    file = listOfInjections.readFile(fileName);
                    for (int i = 0; i < file.size(); i++) {
                        listOfInjections.addInjection(file.get(i));
                        System.out.println(file.get(i));
                    }
                    break;
                case 1:
                    ArrayList<Injection> result = listOfInjections.readFile("injection.dat");
                    System.out.println(result);
                    break;

                case 2:
                    String ask = "";
                    do {
                        try {
                            Injection in = new Injection();
                            flag = false;
                            do {
                                try {
                                    System.out.println("Enter injection id: ");
                                    sc = new Scanner(System.in);
                                    int id = sc.nextInt();
                                    Injection check = listOfInjections.searchInjectionID(id);
                                    if (id <= 0) {
                                        throw new Exception("Injection id is invalid");
                                    }
                                    if (check != null) {
                                        throw new Exception("Injection already has in the list");
                                    }
                                    in.setId(id);
                                    flag = false;
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                    System.out.println("Do you want to create new injection or go back to the menu(y/n)");
                                    sc = new Scanner(System.in);
                                    ask = sc.nextLine();
                                    if (ask.equalsIgnoreCase("y")) {
                                        flag = true;
                                    } else {
                                        throw new Exception();
                                    }

                                }
                            } while (flag);
                            do {
                                try {
                                    System.out.println("Enter student id (SExxxxx): ");
                                    sc = new Scanner(System.in);
                                    String studentID = sc.nextLine();
                                    String injectedStudent = listOfInjections.isStudentInjected(studentID);
                                    if (listOfStudent.searchByID(studentID).getId().isEmpty() || !Validation.isStudentIdValid(studentID)) {
                                        throw new Exception("Student does not exist");
                                    }
                                    if (!injectedStudent.isBlank()) {
                                        throw new Exception("Student had injection");
                                    } else {
                                        in.setStudentID(studentID);
                                        flag = false;
                                    }
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                    System.out.println("Do you want to create new injection (y/n)");
                                    sc = new Scanner(System.in);
                                    ask = sc.nextLine();
                                    if (ask.equalsIgnoreCase("y")) {
                                        flag = true;
                                    } else {
                                        throw new Exception();
                                    }
                                }
                            } while (flag);
                            in.inputInjection();
                            listOfInjections.addInjection(in);
                            System.out.println("Success");
                            System.out.println("Do you want to create new injection or go back to the menu(y/n)");
                            sc = new Scanner(System.in);
                            ask = sc.nextLine();
                            flag = ask.equalsIgnoreCase("y");
                        } catch (Exception e) {
                            System.out.println("Add fail");
                            break;
                        }
                    } while (flag);

                    break;
                case 3:
                    int injectionId;
                    do {
                        try {
                            System.out.println("Enter injection id to update: ");
                            sc = new Scanner(System.in);
                            injectionId = sc.nextInt();
                            if (listOfInjections.searchInjectionID(injectionId) == null) {
                                System.out.println("Injection does not exist");
                                System.out.println("Do you want to continue(y/n): ");
                                sc = new Scanner(System.in);
                                ask = sc.nextLine();
                                if (ask.equalsIgnoreCase("y")) {
                                    flag = true;
                                } else {
                                    throw new Exception();
                                }
                            } else {
                                listOfInjections.updateInjection(injectionId);
                                System.out.println("Updated");
                                flag = false;
                            }

                        } catch (Exception e) {
                            System.out.println("Update fail");
                            flag = false;
                        }
                    } while (flag);

                    break;
                case 4:
                    do {
                        try {
                            System.out.println("Enter injection id: ");
                            sc = new Scanner(System.in);
                            int injectionID = sc.nextInt();
                            if (Validation.confirmMessage() && listOfInjections.deleteInjection(injectionID)) {
                                System.out.println("Delete success");
                            } else {
                                System.out.println("Delete Fail, injection is not exist");
                                throw new Exception();
                            }
                            flag = false;
                        } catch (Exception e) {
                            System.out.println("Do you want to continue: (y/n)");
                            sc = new Scanner(System.in);
                            ask = sc.nextLine();
                            flag = ask.equalsIgnoreCase("y");
                        }
                    } while (flag);

                    break;
                case 5:
                    do {

                        try {
                            System.out.println("Enter Student ID (SExxxxx): ");
                            sc = new Scanner(System.in);
                            String studentId = sc.nextLine();
                            if (!listOfStudent.searchByID(studentId).getId().isEmpty() && listOfInjections.searchInjectionByStudentID(studentId) != null && Validation.isStudentIdValid(studentId)) {
                                System.out.println(listOfInjections.searchInjectionByStudentID(studentId));
                            } else {
                                throw new Exception();
                            }
                            flag = false;
                        } catch (Exception e) {
                            System.out.println("This student does not have injection");
                            flag = false;
                        }
                    } while (flag);

                    break;
                case 6: {
                    try {
                        listOfInjections.saveFile("injection.dat");
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                break;

            }

        } while (choice >= 0 && choice <= 6);

    }
}
