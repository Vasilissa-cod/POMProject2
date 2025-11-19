package pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.back;

public class FlightsListPage {
    public void isNoFlights() {
        $("#flightsTable").shouldHave(text("Рейсов по вашему запросу не найдено."));
    }

    public void registerToFirstFlight() {
        $x("//button[.='Зарегистрироваться']").click();
    }

    public void returnToSearch() {
        back();
    }
}