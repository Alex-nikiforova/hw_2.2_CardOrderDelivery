package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static java.time.temporal.TemporalQueries.localDate;


public class CardOrderDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldSuccessfullyWithManualFilling() {

        $("[placeholder='Город']").setValue("Хабаровск");
        $("[placeholder='Дата встречи']").click();
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String localDate = LocalDate.now().plusDays(3).format(formatter);
        $("[placeholder='Дата встречи']").setValue(localDate);
        $("[name='name']").setValue("Курбатов Владислав");
        $("[name='phone']").setValue("+79066786545");
        $(".checkbox__box").click();
        $(withText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofMillis(15000));
        $(withText("Встреча успешно забронирована на "+ localDate)).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    public void shouldSuccessfullyWithAutocomplete() {

        $("[placeholder='Город']").setValue("Ха");
        $(byText("Хабаровск")).click();
        $("[placeholder='Дата встречи']").click();
        $(withText("30")).click();
        $("[name='name']").setValue("Курбатов Владислав");
        $("[name='phone']").setValue("+79066786545");
        $(".checkbox__box").click();
        $(withText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }
}
