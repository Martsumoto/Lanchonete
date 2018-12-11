package com.marcelokmats.lanchonete;

import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.snackList.SnackListPresenter;
import com.marcelokmats.lanchonete.snackList.SnackListPresenterImpl;
import com.marcelokmats.lanchonete.snackList.SnackListView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SandwichValuesTest {

    @Mock SnackListView mView;
    SnackListPresenter mPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new SnackListPresenterImpl(mView);
    }


    @Test public void menu_items() {

    }

    @Test
    public void hamburger_promotion_test_5() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient hamburger = createHamburger();

        ingredients.add(createHamburger());
        ingredients.add(createHamburger());
        ingredients.add(createHamburger());
        ingredients.add(createHamburger());
        ingredients.add(createHamburger());

        totalPrice = mPresenter.calculatePrice(ingredients);
        assertEquals(hamburger.getPrice().multiply(BigDecimal.valueOf(4)), totalPrice);
    }

    @Test
    public void hamburger_promotion_test_6() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient hamburger = createHamburger();

        ingredients.add(createHamburger());
        ingredients.add(createHamburger());
        ingredients.add(createHamburger());
        ingredients.add(createHamburger());
        ingredients.add(createHamburger());
        ingredients.add(createHamburger());

        totalPrice = mPresenter.calculatePrice(ingredients);
        assertEquals(hamburger.getPrice().multiply(BigDecimal.valueOf(4)), totalPrice);
    }

    @Test
    public void cheese_promotion_test_7() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient cheese = createCheese();

        ingredients.add(createCheese());
        ingredients.add(createCheese());
        ingredients.add(createCheese());
        ingredients.add(createCheese());
        ingredients.add(createCheese());
        ingredients.add(createCheese());
        ingredients.add(createCheese());

        totalPrice = mPresenter.calculatePrice(ingredients);
        assertEquals(cheese.getPrice().multiply(BigDecimal.valueOf(5)), totalPrice);
    }

    @Test
    public void menu_x_bacon_test() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient cheese = createCheese();

        ingredients.add(createBread()); // 1.00
        ingredients.add(createBacon()); // 2.00
        ingredients.add(createHamburger()); // 3.00
        ingredients.add(createCheese()); // 1.50

        // 1.00 + 2.00 + 3.00 + 1.50 = 7.50

        totalPrice = mPresenter.calculatePrice(ingredients);
        assertEquals(BigDecimal.valueOf(7.5), totalPrice);
    }

    @Test
    public void menu_x_burger_test() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient cheese = createCheese();

        ingredients.add(createBread()); // 1.00
        ingredients.add(createHamburger()); // 3.00
        ingredients.add(createCheese()); // 1.50

        // 1.00 + 3.00 + 1.50 = 5.50

        totalPrice = mPresenter.calculatePrice(ingredients);
        assertEquals(BigDecimal.valueOf(5.5), totalPrice);
    }

    @Test
    public void menu_x_egg_test() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient cheese = createCheese();

        ingredients.add(createBread()); // 1.00
        ingredients.add(createEgg()); // 0.80
        ingredients.add(createHamburger()); // 3.00
        ingredients.add(createCheese()); // 1.50

        // 1.00 + 0.80 + 3.00 + 1.50 = 6.30

        totalPrice = mPresenter.calculatePrice(ingredients);
        assertEquals(BigDecimal.valueOf(6.3), totalPrice);
    }

    @Test
    public void menu_x_egg_bacon_test() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<Ingredient> ingredients = new ArrayList<>();
        Ingredient cheese = createCheese();

        ingredients.add(createBread()); // 1.00
        ingredients.add(createLettuce()); // 0.40
        ingredients.add(createEgg()); // 0.80
        ingredients.add(createBacon()); // 2.00
        ingredients.add(createHamburger()); // 3.00
        ingredients.add(createCheese()); // 1.50

        // 1.00 + 0.40 + 0.8 + 2.00 + 3.00 + 1.50 = 8.70

        totalPrice = mPresenter.calculatePrice(ingredients);
        assertEquals(BigDecimal.valueOf(8.7), totalPrice);
    }

    private Ingredient createIngredient(int id, BigDecimal price) {
        Ingredient ingredient = new Ingredient();

        ingredient.setId(id);
        ingredient.setPrice(price);

        return ingredient;
    }
    private Ingredient createLettuce() {
        return createIngredient(1, BigDecimal.valueOf(0.40));
    }
    private Ingredient createBacon() {
        return createIngredient(2, BigDecimal.valueOf(2.00));
    }
    private Ingredient createHamburger() {
        return createIngredient(3, BigDecimal.valueOf(3.00));
    }
    private Ingredient createEgg() {
        return createIngredient(4, BigDecimal.valueOf(0.80));
    }
    private Ingredient createCheese() {
        return createIngredient(5, BigDecimal.valueOf(1.50));
    }
    private Ingredient createBread() {
        return createIngredient(6, BigDecimal.valueOf(1.00));
    }
}
