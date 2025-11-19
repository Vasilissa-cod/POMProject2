import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleFlightsTests {

    @BeforeEach
    void setUp() {
        open("https://slqa.ru/cases/DeepSeekFlights/");
        getWebDriver().manage().window().maximize();
    }

    //Автотесты
    // 1. Неуспешный логин
    @Test
    @DisplayName("1. Неуспешный логин")
    void test01WrongPassword() {
        stepEnterUsername("standard_user");
        stepEnterPassword("WrongPassword");
        stepClickLoginButton();
        stepVerifyLoginErrorMessage();
    }

    @Step("Ввести имя пользователя: {username}")
    void stepEnterUsername(String username) {
        $("#username").setValue(username);
    }

    @Step("Ввести пароль: {password}")
    void stepEnterPassword(String password) {
        $("#password").setValue(password);
    }

    @Step("Нажать кнопку входа")
    void stepClickLoginButton() {
        $("#loginButton").click();
    }

    @Step("Проверить сообщение об ошибке логина")
    void stepVerifyLoginErrorMessage() {
        $("#message").shouldHave(text("Неверное имя пользователя или пароль."));
    }

    // 2. Не задана дата
    @Test
    @DisplayName("2. Не задана дата")
    void test02NoDate() {
        stepEnterUsername("standard_user");
        stepEnterPassword("stand_pass1");
        stepClickLoginButton();
        stepVerifySuccessfulLogin("Иванов Иван Иванович");
        stepClearDepartureDate();
        stepClickSearchButton();
        stepVerifyDepartureDateEmptyMessage();
    }

    @Step("Проверить успешный логин")
    void stepVerifySuccessfulLogin(String expectedName) {
        $("#greeting").shouldHave(text("Добро пожаловать, " + expectedName + "!"));
    }

    @Step("Очистить поле даты вылета")
    void stepClearDepartureDate() {
        $("#departureDate").setValue("");
    }

    @Step("Нажать кнопку поиска")
    void stepClickSearchButton() {
        $x("//button[.='Найти']").click();
    }

    @Step("Проверить сообщение о пустой дате вылета")
    void stepVerifyDepartureDateEmptyMessage() {
        $("#searchMessage").shouldHave(text("Пожалуйста, укажите дату вылета."));
    }

    // 3. Не найдены рейсы
    @Test
    @DisplayName("3. Не найдены рейсы")
    void test03FlightsNotFound() {
        stepEnterUsername("standard_user");
        stepEnterPassword("stand_pass1");
        stepClickLoginButton();
        stepVerifySuccessfulLogin("Иванов Иван Иванович");
        stepSelectDepartureCity("Казань");
        stepSelectArrivalCity("Париж");
        stepSetDepartureDate("24.11.2025");
        stepClickSearchButton();
        stepVerifyNoFlightsFound();
    }

    @Step("Выбрать город отправления: {city}")
    void stepSelectDepartureCity(String city) {
        $("#departureCity").selectOption(city);
    }

    @Step("Выбрать город прибытия: {city}")
    void stepSelectArrivalCity(String city) {
        $("#arrivalCity").selectOption(city);
    }

    @Step("Установить дату вылета: {date}")
    void stepSetDepartureDate(String date) {
        $("#departureDate").setValue(date);
    }

    @Step("Проверить, что рейсы не найдены")
    void stepVerifyNoFlightsFound() {
        $("#flightsTable").shouldHave(text("Рейсов по вашему запросу не найдено."));
    }

    //4. Неправильный номер паспорта
    @Test
    @DisplayName("4. Неправильный номер паспорта")
    void test04WrongPassportNumber() {
        stepEnterUsername("standard_user");
        stepEnterPassword("stand_pass1");
        stepClickLoginButton();
        stepVerifySuccessfulLogin("Иванов Иван Иванович");
        stepSelectDepartureCity("Москва");
        stepSelectArrivalCity("Нью-Йорк");
        stepSetDepartureDate("24.11.2025");
        stepClickSearchButton();
        stepClickRegisterButton();
        stepSetPassportNumber("паспорт");
        stepClickFinishRegistrationButton();
        stepVerifyWrongPassportNumberMessage();
    }

    @Step("Нажать кнопку регистрации")
    void stepClickRegisterButton() {
        $x("//button[.='Зарегистрироваться']").click();
    }

    @Step("Ввести номер паспорта: {passportNumber}")
    void stepSetPassportNumber(String passportNumber) {
        $("#passportNumber").setValue(passportNumber);
    }

    @Step("Нажать кнопку завершения регистрации")
    void stepClickFinishRegistrationButton() {
        $x("//button[.='Завершить регистрацию']").click();
    }

    @Step("Проверить сообщение о неправильном номере паспорта")
    void stepVerifyWrongPassportNumberMessage() {
        $("#registrationMessage").shouldHave(text("Номер паспорта должен содержать только цифры и пробелы."));
    }

    //5. Успешная регистрация
    @Test
    @DisplayName("5. Успешная регистрация")
    void test05SuccessRegistration() {
        stepEnterUsername("standard_user");
        stepEnterPassword("stand_pass1");
        stepClickLoginButton();
        stepVerifySuccessfulLogin("Иванов Иван Иванович");
        stepSelectDepartureCity("Москва");
        stepSelectArrivalCity("Нью-Йорк");
        stepSetDepartureDate("24.11.2025");
        stepClickSearchButton();
        stepClickRegisterButton();
        stepClickFinishRegistrationButton();
        stepVerifyAndAcceptBookingAlert();
        stepVerifyRegistrationSuccessMessage();
    }

    @Step("Проверить и принять алерт о бронировании")
    void stepVerifyAndAcceptBookingAlert() {
        Alert alert = switchTo().alert();
        assertTrue(alert.getText().contains("Бронирование завершено"));
        alert.accept();
    }

    @Step("Проверить сообщение об успешной регистрации")
    void stepVerifyRegistrationSuccessMessage() {
        $("#registrationMessage").shouldHave(text("Регистрация успешно завершена!"));
    }
}