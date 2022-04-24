package junit;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


public class Lesson2 {

    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
    }

    @Test
    void successFillTest() {
        open("/automation-practice-form");

        $(".main-header").shouldHave(text("Practice Form"));
        $("h5").shouldHave(text("Student Registration Form"));

        $("#firstName").setValue("Sergey");
        $("#lastName").setValue("Barvin");
        $("#userEmail").setValue("bsk@comp.com");
        $(byText("Male")).click();
        $("#userNumber").setValue("0123456789");

        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption("July");
        $(".react-datepicker__year-select").selectOption("1995");
        $("[aria-label$='July 9th, 1995']").click();

        $("#subjectsInput").setValue("Arts").pressEnter();

        $(byText("Sports")).click();

        File file = new File("src/test/resources/images/lesson-2/pic.png");
        $("#uploadPicture").uploadFile(file);

        $("#currentAddress").setValue("Russia, Republic of Bashkortostan");
        $("#react-select-3-input").setValue("NCR").pressEnter();
        $("#react-select-4-input").setValue("Noida").pressEnter();

        $("#submit").scrollTo().click();

        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $(".table-responsive").shouldHave(
                text("Sergey Barvin"),
                text("bsk@comp.com"),
                text("Male"),
                text("0123456789"),
                text("9 July,1995"),
                text("Arts"),
                text("Sports"),
                text("pic.png"),
                text("Russia, Republic of Bashkortostan"),
                text("NCR Noida"));

    }
}
