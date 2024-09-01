package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.page.LoginPage;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.cleanAuthCodes;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class LoginTest {
    LoginPage loginPage;

    @AfterEach
    void tearDown() {
        cleanAuthCodes();
    }
    @AfterAll
    static void tearDownAll() {
        cleanDatabase();
    }
    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test
    @DisplayName("Successful login")
    void shouldSuccessfulLogin() {
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    @DisplayName("Unsuccessful login")
    void shouldUnsuccessfulLogin() {
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");
    }




}