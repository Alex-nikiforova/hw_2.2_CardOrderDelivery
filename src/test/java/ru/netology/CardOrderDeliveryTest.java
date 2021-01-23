package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CardOrderDeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldSuccessfully() {
        Configuration.timeout = 15000;
        $("[placeholder='Город']").setValue("Хаба");
        $(".menu-item__control").click();
//        $(".calendar-input__native-control").click();
//        $(".calendar-input__native-control").setValue("2021-01-29");
        $("[name='name']").setValue("Курбатов Владислав");
        $("[name='phone']").setValue("+79066786545");
        $(".checkbox__box").click();
        $(withText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible);
    }
}
