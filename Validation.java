
package MyService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Validation {

    final static String DATE_FORMAT = "dd-MM-yyyy";

    public static boolean isDateValid(String date) {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isSecondDateValid(String firstDate, String secondDate) {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            Date date1 = df.parse(firstDate);
            Date date2 = df.parse(secondDate);
            // Get time se tra ve milisecond tu 1/1/1970 nen doi don vi sang ngay
            long getDiff = (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
            if (getDiff >= 28 && getDiff <= 84) {
                return true;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean confirmMessage() {
        boolean flag = false;
        int confirm = -1;
        do {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("Do you want to remove this injection? (1: yes / 0: no): ");
                confirm = sc.nextInt();
                if (confirm < 0 || confirm > 1) {
                    throw new Exception();
                }
                flag = false;
            } catch (Exception e) {
                System.out.println("Enter again");
                flag = true;
            }
        } while (flag);
        return confirm == 1;
    }
    public static boolean isStudentIdValid(String studentId){
        try {
            String pattern = "SE[0-9]{5}";
            if(!studentId.matches(pattern) || studentId.isBlank()){
                throw new Exception();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean isVaccineIdValid(String vaccineId){
        try {
            String pattern = "Covid-V[0-9]+";
            if(!vaccineId.matches(pattern) || vaccineId.isBlank()){
                throw new Exception();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static boolean isCharOnlyString(String s) {
        try {
            String pattern = "[a-zA-Z ]+";
            if (!s.matches(pattern) || s.isBlank()) {
                throw new Exception();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
