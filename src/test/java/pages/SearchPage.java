package pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import com.codeborne.selenide.SelenideElement;

public class SearchPage {
    SelenideElement departureDate = $("#departureDate");
    SelenideElement departureCity = $("#departureCity");
    SelenideElement arrivalCity = $("#arrivalCity");
    SelenideElement findButton = $x("//button[.='Найти']");
    SelenideElement message = $("#searchMessage");

    public void search(String departureDate) {
        this.departureDate.setValue(departureDate);
        this.findButton.click();
    }

    public void search(String departureDate, String departureCity, String arrivalCity) {
        // Ждем загрузки элементов перед использованием
        this.departureCity.shouldBe(visible);
        this.arrivalCity.shouldBe(visible);
        this.departureDate.shouldBe(visible);

        this.departureCity.selectOption(departureCity);
        this.arrivalCity.selectOption(arrivalCity);
        this.departureDate.setValue(departureDate);
        this.findButton.click();
    }

    public void isDepartureDateEmpty() {
        this.message.shouldHave(text("Пожалуйста, укажите дату вылета."));
    }

    public void isDepartureDateInPast() {
        this.message.shouldHave(text("Невозможно осуществить поиск: выбранная дата уже прошла."));
    }

    public void waitForPageLoad() {
        // Ждем загрузки страницы поиска - проверяем наличие ключевых элементов напрямую
        // Используем прямые вызовы $() для более надежного ожидания
        // Увеличиваем таймаут до 10 секунд для надежности после back()
        // Сначала проверяем наличие кнопки "Найти" - она точно должна быть на странице поиска
        long originalTimeout = com.codeborne.selenide.Configuration.timeout;
        try {
            com.codeborne.selenide.Configuration.timeout = 10000;
            // Сначала проверяем кнопку поиска - она точно должна быть на странице поиска
            $x("//button[.='Найти']").shouldBe(visible);
            // Затем проверяем остальные элементы
            $("#departureCity").shouldBe(visible);
            $("#arrivalCity").shouldBe(visible);
            $("#departureDate").shouldBe(visible);
        } finally {
            com.codeborne.selenide.Configuration.timeout = originalTimeout;
        }
    }
}