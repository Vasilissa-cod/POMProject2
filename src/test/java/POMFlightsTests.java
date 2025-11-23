import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.FlightsListPage;
import pages.LoginPage;
import pages.RegistrationPage;
import pages.SearchPage;

public class POMFlightsTests {
    // URL приложения - можно менять для тестирования разных версий
    // v01 - версия без ошибок: https://slqamsk.github.io/cases/slflights/v01/
    // v02 - версия с ошибками: https://slqamsk.github.io/cases/slflights/v02/
    // Базовая версия: https://slqamsk.github.io/cases/slflights/
    private static final String BASE_URL = "https://slqamsk.github.io/cases/slflights/v01/";
    
    @BeforeEach
    void setUp() {
        open(BASE_URL);
        getWebDriver().manage().window().maximize();
    }

    //Автотесты
    // 1. Неуспешный логин
    @Test
    @DisplayName("1. Неуспешный логин")
    void test01WrongPassword() {
        stepLogin("standard_user", "WrongPassword");
        stepVerifyLoginUnsuccessful();
    }

    @Step("Выполнить логин с неверным паролем")
    void stepLogin(String username, String password) {
        LoginPage loginPage = new LoginPage();
        loginPage.login(username, password);
    }

    @Step("Проверить, что логин неуспешен")
    void stepVerifyLoginUnsuccessful() {
        LoginPage loginPage = new LoginPage();
        loginPage.isLoginUnsuccessful();
    }

    // 2. Не задана дата
    @Test
    @DisplayName("2. Не задана дата")
    void test02NoDate() {
        stepLoginSuccessfully("standard_user", "stand_pass1", "Иванов Иван Иванович");
        stepSearchWithoutDate();
        stepVerifyDepartureDateEmpty();
    }

    @Step("Выполнить успешный логин")
    void stepLoginSuccessfully(String username, String password, String expectedName) {
        LoginPage loginPage = new LoginPage();
        loginPage.login(username, password);
        loginPage.isLoginSuccessful(expectedName);
    }

    @Step("Выполнить поиск без указания даты")
    void stepSearchWithoutDate() {
        SearchPage searchPage = new SearchPage();
        searchPage.search("");
    }

    @Step("Проверить, что дата вылета не задана")
    void stepVerifyDepartureDateEmpty() {
        SearchPage searchPage = new SearchPage();
        searchPage.isDepartureDateEmpty();
    }
    // 3. Не найдены рейсы
    @Test
    @DisplayName("3. Не найдены рейсы")
    void test03FlightsNotFound() {
        stepLoginSuccessfully("standard_user", "stand_pass1", "Иванов Иван Иванович");
        stepSearchFlights("24.11.2025", "Казань", "Париж");
        stepVerifyNoFlightsFound();
    }

    @Step("Выполнить поиск рейсов")
    void stepSearchFlights(String date, String departureCity, String arrivalCity) {
        SearchPage searchPage = new SearchPage();
        searchPage.search(date, departureCity, arrivalCity);
    }

    @Step("Проверить, что рейсы не найдены")
    void stepVerifyNoFlightsFound() {
        FlightsListPage flightsList = new FlightsListPage();
        flightsList.isNoFlights();
    }

    //4. Неправильный номер паспорта
    @Test
    @DisplayName("4. Неправильный номер паспорта")
    void test04WrongPassportNumber() {
        stepLoginSuccessfully("standard_user", "stand_pass1", "Иванов Иван Иванович");
        stepSearchFlights("24.11.2025", "Москва", "Нью-Йорк");
        stepRegisterToFirstFlight();
        stepSetWrongPassportNumber("паспорт");
        stepVerifyWrongPassportNumber();
    }

    @Step("Зарегистрироваться на первый рейс в списке")
    void stepRegisterToFirstFlight() {
        FlightsListPage flightsList = new FlightsListPage();
        flightsList.registerToFirstFlight();
    }

    @Step("Проверить, что номер паспорта неправильный")
    void stepVerifyWrongPassportNumber() {
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.isWrongPassportNumber();
    }

    //5. Успешная регистрация
    @Test
    @DisplayName("5. Успешная регистрация")
    void test05SuccessRegistration() {
        stepLoginSuccessfully("standard_user", "stand_pass1", "Иванов Иван Иванович");
        stepSearchFlights("24.11.2025", "Москва", "Нью-Йорк");
        stepRegisterToFirstFlight();
        stepFinishRegistration();
        stepVerifyRegistrationSuccessful();
    }

    @Step("Завершить регистрацию")
    void stepFinishRegistration() {
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.finishRegistration();
    }

    @Step("Проверить, что регистрация успешна")
    void stepVerifyRegistrationSuccessful() {
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.isRegistrationSuccessful();
    }

    //6. Сложный сценарий: Поиск - не найдены рейсы - возврат на страницу поиска - найдены рейсы - Регистрация на 1-й рейс в списке - не задан номер паспорта - повторный ввод паспорта с корректными данными - успешная регистрация
    @Test
    @DisplayName("6. Сложный сценарий: Поиск - не найдены рейсы - возврат на страницу поиска - найдены рейсы - Регистрация на 1-й рейс в списке - не задан номер паспорта - повторный ввод паспорта с корректными данными - успешная регистрация")
    void test06ComplexScenario() {
        stepStartComplexScenario();
        stepLoginSuccessfully("standard_user", "stand_pass1", "Иванов Иван Иванович");
        stepSearchFlights("24.11.2025", "Казань", "Париж");
        stepVerifyNoFlightsFound();
        stepReturnToSearchPage();
        stepNavigateToSearchPage();
        stepSearchFlights("24.11.2025", "Москва", "Нью-Йорк");
        stepRegisterToFirstFlight();
        stepTryFinishRegistrationWithoutPassport();
        stepSetCorrectPassportNumber("1234567890");
        stepVerifyRegistrationSuccessful();
    }

    @Step("Начать сложный сценарий")
    void stepStartComplexScenario() {
        // Начало сложного сценария тестирования
    }

    @Step("Вернуться на страницу поиска")
    void stepReturnToSearchPage() {
        FlightsListPage flightsList = new FlightsListPage();
        flightsList.returnToSearch();
    }

    @Step("Перейти на страницу поиска")
    void stepNavigateToSearchPage() {
        // После back() мы можем быть на странице со списком рейсов
        // Нужно нажать "Новый поиск", чтобы вернуться на страницу поиска
        // Даем время браузеру обработать back()
        sleep(1500);
        
        // Проверяем, есть ли кнопка "Новый поиск" (значит мы на странице со списком рейсов)
        if ($x("//button[.='Новый поиск']").exists()) {
            $x("//button[.='Новый поиск']").click();
            sleep(1000);
        }
        
        // Теперь должны быть на странице поиска
        // Метод search() сам проверит наличие элементов формы
    }

    @Step("Попытаться завершить регистрацию без номера паспорта")
    void stepTryFinishRegistrationWithoutPassport() {
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.clearPassportNumber();
        registrationPage.finishRegistration();
        registrationPage.isWrongPassportNumber();
    }

    @Step("Ввести корректный номер паспорта")
    void stepSetCorrectPassportNumber(String passportNumber) {
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.setPassportNumber(passportNumber);
        registrationPage.finishRegistration();
    }

    @Step("Ввести неправильный номер паспорта")
    void stepSetWrongPassportNumber(String passportNumber) {
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.clearPassportNumber();
        registrationPage.setPassportNumber(passportNumber);
        registrationPage.finishRegistration();
    }

    // 7. Некорректный email (без символа @)
    @Test
    @DisplayName("7. Некорректный email (без символа @)")
    void test07WrongEmail() {
        stepLoginSuccessfully("standard_user", "stand_pass1", "Иванов Иван Иванович");
        stepSearchFlights("24.11.2025", "Москва", "Нью-Йорк");
        stepRegisterToFirstFlight();
        stepSetWrongEmail("userexample.com");
        stepVerifyWrongEmail();
    }

    @Step("Ввести некорректный email без символа @")
    void stepSetWrongEmail(String email) {
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.clearEmail();
        registrationPage.setEmail(email);
        registrationPage.finishRegistration();
    }

    @Step("Проверить, что email некорректный")
    void stepVerifyWrongEmail() {
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.isWrongEmail();
    }

    // 8. Некорректный номер паспорта (буквы)
    @Test
    @DisplayName("8. Некорректный номер паспорта (буквы)")
    void test08WrongPassportWithLetters() {
        stepLoginSuccessfully("standard_user", "stand_pass1", "Иванов Иван Иванович");
        stepSearchFlights("24.11.2025", "Москва", "Нью-Йорк");
        stepRegisterToFirstFlight();
        stepSetWrongPassportNumber("ABCDEFGH");
        stepVerifyWrongPassportNumber();
    }

    // 9. Некорректное ФИО (цифры)
    @Test
    @DisplayName("9. Некорректное ФИО (цифры)")
    void test09WrongFullNameWithDigits() {
        stepLoginSuccessfully("standard_user", "stand_pass1", "Иванов Иван Иванович");
        stepSearchFlights("24.11.2025", "Москва", "Нью-Йорк");
        stepRegisterToFirstFlight();
        stepSetWrongFullName("Иванов Иван123 Иванович");
        stepVerifyWrongFullName();
    }

    @Step("Ввести некорректное ФИО с цифрами")
    void stepSetWrongFullName(String fullName) {
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.clearFullName();
        registrationPage.setFullName(fullName);
        registrationPage.finishRegistration();
    }

    @Step("Проверить, что ФИО некорректное")
    void stepVerifyWrongFullName() {
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.isWrongFullName();
    }

    // 10. Дата рейса в прошлом
    @Test
    @DisplayName("10. Дата рейса в прошлом")
    void test10PastDate() {
        stepLoginSuccessfully("standard_user", "stand_pass1", "Иванов Иван Иванович");
        stepSearchFlightsWithPastDate("01.01.2020", "Москва", "Нью-Йорк");
        stepVerifyPastDateError();
    }

    @Step("Выполнить поиск рейсов с датой в прошлом")
    void stepSearchFlightsWithPastDate(String date, String departureCity, String arrivalCity) {
        SearchPage searchPage = new SearchPage();
        searchPage.search(date, departureCity, arrivalCity);
    }

    @Step("Проверить, что дата в прошлом вызывает ошибку")
    void stepVerifyPastDateError() {
        SearchPage searchPage = new SearchPage();
        searchPage.isDepartureDateInPast();
    }
}