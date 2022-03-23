import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Story("Создание курьера")
public class NewCourierTest {
    private CourierClient courierClient;
    private Courier courier;

    private Response response;

    private final int expectedCodResponse = 201;
    private final String courierLogin = RandomStringUtils.randomAlphabetic(10);

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandomCourier();
    }

    @After
    public void tearDown() {
        int actualCodResponse = response.statusCode();

        if (actualCodResponse == expectedCodResponse) {
            int courierId = courierClient.login(CourierCredentials.from(courier)).then().extract().path("id");
            courierClient.delete(courierId);
            System.out.println("Курьер удален");
        } else {
            System.out.println("Нет курьеров для удаления");
        }
    }

    @Test
    @DisplayName("Курьера можно создать")
    @Description("Курьера можно создать, успешный запрос возвращает код ответа 201")
    public void courierCreateSuccess() {
        response = courierClient.create(courier);
        boolean isCourierCreated = true;

        assertEquals("Код ответа не соответствует 201",expectedCodResponse,response.statusCode());
        assertEquals("Курьер не создан", isCourierCreated, response.then().extract().path("ok"));
    }

}
