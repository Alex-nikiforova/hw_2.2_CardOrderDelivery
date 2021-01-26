package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class CardOrderDeliveryTest {

    public void selectPlusSevenDaysDate() {

        DateTimeFormatter formatterDays = DateTimeFormatter.ofPattern("d");
        String meetingDate = LocalDate.now().plusDays(7).format(formatterDays);

        DateTimeFormatter formatterMonths = DateTimeFormatter.ofPattern("M");
        int month1 = Integer.parseInt(LocalDate.now().format(formatterMonths));
        int month2 = Integer.parseInt(LocalDate.now().plusDays(7).format(formatterMonths));

        if (month2 == month1) {
            $("[type='button']").click();
            $$(".calendar__day").find(text(meetingDate)).click();
        }
        if (month2 > month1) {
            $("[type='button']").click();
            $("[data-step='1']").click();
            $$(".calendar__day").find(text(meetingDate)).click();
        }
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldSuccessfullyWithManualFilling() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String meetingDate = LocalDate.now().plusDays(3).format(formatter);

        $("[placeholder='Город']").setValue("Хабаровск");
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(meetingDate);
        $("[name='name']").setValue("Курбатов Владислав");
        $("[name='phone']").setValue("+79066786545");
        $(".checkbox__box").click();
        $(withText("Забронировать")).click();
        $(byText("Успешно!")).shouldBe(Condition.visible, Duration.ofMillis(15000));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + meetingDate));
    }

    @Test
    public void shouldSuccessfullyWithAutocomplete() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String meetingDate = LocalDate.now().plusDays(7).format(formatter);

        $("[placeholder='Город']").setValue("Ха");
        $(byText("Хабаровск")).click();
        selectPlusSevenDaysDate();
        $("[name='name']").setValue("Курбатов Владислав");
        $("[name='phone']").setValue("+79066786545");
        $(".checkbox__box").click();
        $(withText("Забронировать")).click();
        $(byText("Успешно!")).shouldBe(Condition.visible, Duration.ofMillis(15000));
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + meetingDate));

    }
}
