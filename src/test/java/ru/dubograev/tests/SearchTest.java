package ru.dubograev.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import io.appium.java_client.AppiumBy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.qameta.allure.Allure.step;

public class SearchTest extends TestBase{
    @Test
    @DisplayName("Sorting test")
    void sortingTest() {
        step("Skip welcome screens", () -> {
            $(AppiumBy.id("ru.sportmaster.app:id/toolbar"))
                    .$(AppiumBy.className("android.widget.ImageButton")).click();
            $(AppiumBy.id("ru.sportmaster.app:id/toolbar"))
                    .$(AppiumBy.className("android.widget.ImageButton")).click();
            $(AppiumBy.id("com.android.permissioncontroller:id/permission_allow_one_time_button")).click();
        });

        step("Select the city", () -> {
            $(AppiumBy.id("ru.sportmaster.app:id/editText")).click();
            $(AppiumBy.id("ru.sportmaster.app:id/recyclerView"))
                    .$$(AppiumBy.className("android.view.ViewGroup")).first().click();
        });

        step("Search for a product", ()-> {
            $(AppiumBy.xpath("//*[@resource-id='ru.sportmaster.app:id/editText']")).click();
            $(AppiumBy.xpath("//*[@resource-id='ru.sportmaster.app:id/editText']")).sendKeys("reebok");
            $(AppiumBy.xpath("//*[@resource-id='ru.sportmaster.app:id/viewHintClickArea']")).click();
        });

        step("Switch the sorting to 'По возрастанию цены'", () -> {
            $(AppiumBy.id("ru.sportmaster.app:id/textViewSorting")).click();
            $(AppiumBy.className("androidx.recyclerview.widget.RecyclerView"))
                    .$$(AppiumBy.className("android.view.ViewGroup")).get(3).click();
        });

        step("Check that sorting button name changed to 'По возрастанию цены'", ()-> {
            $(AppiumBy.id("ru.sportmaster.app:id/textViewSorting")).shouldHave(Condition.text("По возрастанию цены"));
        });

        step("Check that first item has the minimum price", ()-> {
            Assertions.assertThat(getMinPrice()).isEqualTo(getFirstElementPrice());
        });
    }

    int getMinPrice() {
        ElementsCollection elements = $$(AppiumBy.xpath("//*[@resource-id='ru.sportmaster.app:id/textViewMainPrice']"));
        List<Integer> listOfPrices = new ArrayList();
        for (int i = 0; i < elements.size(); i++) {
            listOfPrices.add(Integer.parseInt(elements.get(i).getText().replaceAll(" ₽", "")));
        }
        int minPrice = listOfPrices.stream().min(Integer::compare).get();
        return minPrice;
    }

    int getFirstElementPrice() {
        int firstElementPrice = Integer.parseInt($$(AppiumBy.xpath("//*[@resource-id='ru.sportmaster.app:id/textViewMainPrice']"))
                .get(0).getText().replaceAll(" ₽", ""));
        return firstElementPrice;
    }
}
