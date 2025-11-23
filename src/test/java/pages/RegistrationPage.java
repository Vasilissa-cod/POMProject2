package pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.switchTo;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Alert;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationPage {
    SelenideElement passportNumber = $("#passportNumber");
    SelenideElement email = $("#email");
    SelenideElement fullName = $("#passengerName");
    SelenideElement finishRegistrationButton = $x("//button[.='Завершить регистрацию']");
    SelenideElement registrationMessage = $("#registrationMessage");

    public void setPassportNumber(String passport) {
        this.passportNumber.setValue(passport);
    }

    public void clearPassportNumber() {
        this.passportNumber.setValue("");
    }

    public void clearFullName() {
        this.fullName.setValue("");
    }

    public void clearEmail() {
        this.email.setValue("");
    }

    public void setEmail(String emailValue) {
        this.email.setValue(emailValue);
    }

    public void setFullName(String fullNameValue) {
        this.fullName.setValue(fullNameValue);
    }

    public void finishRegistration() {
        this.finishRegistrationButton.click();
    }

    public void isWrongPassportNumber() {
        this.registrationMessage.shouldHave(text("Номер паспорта должен содержать только цифры и пробелы."));
    }

    public void isWrongEmail() {
        this.registrationMessage.shouldHave(text("Пожалуйста, введите корректный email адрес."));
    }

    public void isWrongFullName() {
        this.registrationMessage.shouldHave(text("ФИО должно содержать только русские буквы, пробелы и дефис."));
    }

    public void isRegistrationSuccessful() {
        Alert alert = switchTo().alert();
        assertTrue(alert.getText().contains("Бронирование завершено"));
        alert.accept();
        this.registrationMessage.shouldHave(text("Регистрация успешно завершена!"));
    }
}