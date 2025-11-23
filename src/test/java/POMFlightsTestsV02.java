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

/**
 * Тестовый класс для версии приложения v02 (с ошибками валидации)
 * URL: https://slqamsk.github.io/cases/slflights/v02/
 * 
 * В этой версии приложения валидация работает некорректно:
 * - Некорректные данные могут быть приняты
 * - Ошибки валидации не отображаются
 */
public class POMFlightsTestsV02 {
    // URL приложения версии v02 (с ошибками валидации)
    private static final String BASE_URL = "https://slqamsk.github.io/cases/slflights/v02/";
    
    @BeforeEach
    void setUp() {
        open(BASE_URL);
        getWebDriver().manage().window().maximize();
    }

    @Step("Выполнить успешный логин")
    void stepLoginSuccessfully(String username, String password, String expectedName) {
        LoginPage loginPage = new LoginPage();
        loginPage.login(username, password);
        loginPage.isLoginSuccessful(expectedName);
    }

    @Step("Выполнить поиск рейсов")
    void stepSearchFlights(String date, String departureCity, String arrivalCity) {
        SearchPage searchPage = new SearchPage();
        searchPage.search(date, departureCity, arrivalCity);
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

    @Step("Ввести неправильный номер паспорта")
    void stepSetWrongPassportNumber(String passportNumber) {
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.clearPassportNumber();
        registrationPage.setPassportNumber(passportNumber);
        registrationPage.finishRegistration();
    }

    // 7. Некорректный email (без символа @) - v02 (должен провалиться, так как валидация не работает)
    @Test
    @DisplayName("7. Некорректный email (без символа @) - v02")
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

    // 8. Некорректный номер паспорта (буквы) - v02
    @Test
    @DisplayName("8. Некорректный номер паспорта (буквы) - v02")
    void test08WrongPassportWithLetters() {
        stepLoginSuccessfully("standard_user", "stand_pass1", "Иванов Иван Иванович");
        stepSearchFlights("24.11.2025", "Москва", "Нью-Йорк");
        stepRegisterToFirstFlight();
        stepSetWrongPassportNumber("ABCDEFGH");
        stepVerifyWrongPassportNumber();
    }

    // 9. Некорректное ФИО (цифры) - v02
    @Test
    @DisplayName("9. Некорректное ФИО (цифры) - v02")
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

    // 10. Дата рейса в прошлом - v02
    @Test
    @DisplayName("10. Дата рейса в прошлом - v02")
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

