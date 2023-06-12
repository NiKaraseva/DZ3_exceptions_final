import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.Arrays;
import java.util.Scanner;

public class Final_ex {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите через пробел \nФамилию: \nИмя: \nОтчество: \nНомер телефона: ");
        String data = scanner.nextLine();
        String[] partsData = data.split(" ");
        if (checkCount(partsData) == -1) {
            System.out.println("Ошибка: введено больше данных, чем требуется");
        } else if (checkCount(partsData) == -2) {
            System.out.println("Ошибка: введено меньше данных, чем требуется");
        } else try {
            checkNumberFormat(partsData);
            writingToFile(partsData);
        } catch (IncorrectPhoneNumber | FileSystemException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public static int checkCount(String[] str) {
        if (str.length > 4) {
            return -1;
        }else if (str.length < 4) {
            return -2;
        }
        return 0;
    }

    public static void checkNumberFormat(String[] str){
        try {
            int phone = Integer.parseInt(str[3]);
        }catch (NumberFormatException e){
            throw new IncorrectPhoneNumber("Ошибка: Введён некорректный номер телефона");
        }
    }

    public static void writingToFile(String[] str) throws FileSystemException {
        String surname = str[0];
        String name = str[1];
        String patronymic = str[2];
        String phone = str[3];

        String fileName = surname + ".txt";
        File file = new File(fileName);
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            if (file.length() > 0) {
                fileWriter.write('\n');
            }
            fileWriter.write(String.format("%s %s %s %s", surname, name, patronymic, phone));
        } catch (IOException e) {
            throw new FileSystemException("Возникла ошибка при работе с файлом");
        }
    }
}

class IncorrectPhoneNumber extends NumberFormatException{
    public IncorrectPhoneNumber(String s) {
        super(s);
    }
}
