import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class PhoneBookAppFile {
    public static void main(String[] args) {
        // Создаем объект для считывания пользовательского ввода
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Введите данные (Ф И О дата_рождения номер_телефона пол) через пробел:");
            // Считываем ввод с консоли
            String userInput = scanner.nextLine();

            // Создаем список для хранения данных и разделяем введенные данные на отдельные значения методом splitUserData()
            List<String> data = splitUserData(userInput);
            // Проверяем корректность введенных данных с помощью метода validateData()
            validateData(data);

            // Получаем данные
            String surname = data.get(0);
            String firstName = data.get(1);
            String middleName = data.get(2);
            String dateOfBirth = data.get(3);
            String phoneNumber = data.get(4);
            String gender = data.get(5);

            // Формируем имя файла на основе фамилии
            String filename = surname + ".txt";
            // Формируем содержимое файла
            String fileContent = surname + " " + firstName + " " + middleName + " " + dateOfBirth + " " + phoneNumber + " " + gender;

            // Сохраняем данные в файл
            saveToFile(filename, fileContent);
            System.out.println("Данные успешно сохранены в файл " + filename);
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    // Метод для разделения введенных данных
    private static List<String> splitUserData(String userData) {
        // Разделяем введенные данные по пробелам
        String[] data = userData.split(" ");

        // Создаем список для хранения отдельных значений
        List<String> result = new ArrayList<>(Arrays.asList("", "", "", "", "", ""));
        for (String item : data) {
            if (item.matches("[0-9]+")) { // Проверка на число
                result.set(4, item);
            } else if (item.equals("f") || item.equals("m")) { // Проверка на пол
                result.set(5, item);
            } else if (item.contains(".")) { // Проверка на дату
                result.set(3, item);
            } else if (result.get(0).isEmpty()) { // Проверка на фамилию
                result.set(0, item);
            } else if (result.get(1).isEmpty()) { // Проверка на имя
                result.set(1, item);
            } else if (result.get(2).isEmpty()) { // Проверка на отчество
                result.set(2, item);
            }
        }

        return result;
    }

    // Метод для проверки корректности введенных данных
    private static void validateData(List<String> data) throws Exception {
        // Проверяем количество данных
        if (data.size() != 6) {
            throw new Exception("Неверное количество данных. Ожидается 6, получено " + data.size());
        }

        try {
            String dateOfBirth = data.get(3);
            // Разделяем дату рождения по точкам
            String[] dateParts = dateOfBirth.split("\\.");
            // Проверяем правильность формата даты
            if (dateParts.length != 3) {
                throw new Exception("Неверный формат даты рождения. Введите дату в формате dd.mm.yyyy");
            }

            Long.parseLong(data.get(4));  // Пытаемся преобразовать номер телефона в целое число
        } catch (NumberFormatException e) {
            throw new Exception("Неверный формат номера телефона. Введите целое число без знаков.");
        }

        String gender = data.get(5);
        // Проверяем правильность формата пола
        if (!gender.equals("f") && !gender.equals("m")) {
            throw new Exception("Неверный формат пола. Введите f или m.");
        }
    }

    // Метод для сохранения данных в файл
    private static void saveToFile(String filename, String content) throws IOException {
        // Создаем объект для записи в файл
        try (FileWriter fileWriter = new FileWriter(filename)) {
            // Записываем содержимое в файл
            fileWriter.write(content);
        }
    }
}

