package ru.netology;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class CardOrderDeliveryTest {

    private ClickOptions equalsMonths() {

        DateTimeFormatter formatterDays = DateTimeFormatter.ofPattern("dd");
        String meetingDate = LocalDate.now().plusDays(7).format(formatterDays);

        DateTimeFormatter formatterMonths = DateTimeFormatter.ofPattern("MM");
        int month1 = Integer.parseInt(LocalDate.now().format(formatterMonths));
        int month2 = Integer.parseInt(LocalDate.now().plusDays(7).format(formatterMonths));

        if (month2 == month1) {
            $("[type='button']").click();
            $$(".calendar__day").shouldHave(CollectionCondition.itemWithText(meetingDate));
        }
        if (month2 > month1) {
            $("[type='button']").click();
            $("[data-step='1']").click();
            $$(".calendar__day").shouldHave(CollectionCondition.itemWithText(meetingDate));
        }
        return null;
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

        $("[placeholder='Город']").setValue("Ха");
        $(byText("Хабаровск")).click();
        $("[placeholder='Дата встречи']").click(Objects.requireNonNull(equalsMonths()));
        $("[name='name']").setValue("Курбатов Владислав");
        $("[name='phone']").setValue("+79066786545");
        $(".checkbox__box").click();
        $(withText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }
}
