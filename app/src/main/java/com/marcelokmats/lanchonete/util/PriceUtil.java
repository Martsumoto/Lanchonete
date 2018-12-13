package com.marcelokmats.lanchonete.util;

import android.util.SparseArray;

import com.marcelokmats.lanchonete.model.CustomIngredient;
import com.marcelokmats.lanchonete.model.Ingredient;
import com.marcelokmats.lanchonete.model.Order;
import com.marcelokmats.lanchonete.model.Sandwich;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Util class to calculate prices
 */
public class PriceUtil {

    // For amount promotions
    public static final int HAMBURGER_ID_PROMOTION = 3;
    public static final int CHEESE_ID_PROMOTION = 5;
    public static final int AMOUNT_PROMOTION = 3;

    // For light promotion
    public static final int LETTUCE_ID = 1;
    public static final int BACON_ID = 2;
    public static final BigDecimal LIGHT_PROMOTION_DISCOUNT = BigDecimal.valueOf(0.1);

    /**
     * Calculates the total price based of a list of orders with the applicable discounts
     * @param orderList The list of orders
     * @param allSandwiches The list of sandwiches, used to check each sandwich ingredients,
     *                      if they are not customized
     * @param allIngredients The list of all ingredients, used to check the ingredient prices
     * @return The total price based on the list of ingredients IDs
     */
    public static BigDecimal value(List<Order> orderList,
                                   SparseArray<Sandwich> allSandwiches,
                                   SparseArray<Ingredient> allIngredients) {
        List<Integer> ingredientIdList;
        BigDecimal totalPrice = BigDecimal.ZERO;

        if (orderList != null) {
            for (Order order : orderList) {
                if (order.getIngredients() != null && order.getIngredients().size() > 0) {
                    // Has custom ingredients
                    ingredientIdList = order.getIngredients();
                } else {
                    // No custom ingredients, use default menu sandwich ingredients
                    Sandwich sandwich = allSandwiches.get(order.getSandwichId());

                    if (sandwich != null) {
                        ingredientIdList = sandwich.getIngredients();
                    } else {
                        // Invalid sandwich ID
                        ingredientIdList = new ArrayList<>();
                    }
                }

                totalPrice = totalPrice.add(PriceUtil.value(ingredientIdList, allIngredients));
            }
        }

        return totalPrice;
    }

    /**
     * Calculates the total price based on a list of ingredients ids(can have repeated ingredients),
     * with the applicable discounts
     * @param ingredientIdList The list of discounts IDs
     * @param allIngredients The list of all ingredients, used to check the ingredient prices
     * @return The total price based on the list of ingredients IDs
     */
    public static BigDecimal value(List<Integer> ingredientIdList, SparseArray<Ingredient> allIngredients) {
        List<Ingredient> ingredientList = new ArrayList<>();

        for (Integer ingredientId : ingredientIdList) {
            if (allIngredients.get(ingredientId) != null) {
                ingredientList.add(allIngredients.get(ingredientId));
            }
        }

        return PriceUtil.value(ingredientList);
    }

    /**
     * Calculates the total price based on a list of custom ingredients (can have repeated ingredients),
     * with the applicable discounts
     * @param customIngredientsList The list of custom ingredients
     * @return The total price based on the list of ingredients
     */
    public static BigDecimal value(SparseArray<CustomIngredient> customIngredientsList) {
        SparseArray<Ingredient> ingredientList = new SparseArray<>();

        for (int i = 0; i < customIngredientsList.size(); i++) {
            int key = customIngredientsList.keyAt(i);
            CustomIngredient customIngredient = customIngredientsList.get(key);

            ingredientList.put(customIngredient.getIngredientId(), customIngredient.getIngredient());
        }

        return PriceUtil.value(
                IngredientUtil.transformCustomIngredientsList(customIngredientsList), ingredientList);
    }

    /**
     * Calculates the total price based on a list of ingredients (can have repeated ingredients),
     * with the applicable discounts
     * @param ingredients The list of ingredients
     * @return The total price based on the list of ingredients
     */
    public static BigDecimal value(List<Ingredient> ingredients) {
        BigDecimal value = BigDecimal.ZERO;
        int freeHamburgerPromotion = 0;
        int freeChesePromotion = 0;
        boolean isLight = false;

        freeHamburgerPromotion = PriceUtil.countIngredient(ingredients, HAMBURGER_ID_PROMOTION);
        freeHamburgerPromotion /= AMOUNT_PROMOTION;

        freeChesePromotion = PriceUtil.countIngredient(ingredients, CHEESE_ID_PROMOTION);
        freeChesePromotion /= AMOUNT_PROMOTION;

        isLight = PriceUtil.isLightPromotion(ingredients);

        for (Ingredient ingredient : ingredients) {
            if (freeHamburgerPromotion > 0 && ingredient.getId() == HAMBURGER_ID_PROMOTION) {
                freeHamburgerPromotion--;
                continue;
            }
            if (freeChesePromotion > 0 && ingredient.getId() == CHEESE_ID_PROMOTION) {
                freeChesePromotion--;
                continue;
            }

            value = value.add(ingredient.getPrice());
        }

        // Light promotion will be applied AFTER hamburger and cheese promotions
        if (isLight) {
            // value = value - (value * 0.1)
            value = value.subtract(value.multiply(LIGHT_PROMOTION_DISCOUNT));
        }

        return value;
    }

    private static int countIngredient(List<Ingredient> ingredients, int ingredientId) {
        int count = 0;

        for (Ingredient ingredient : ingredients) {
            if (ingredient.getId() == ingredientId) {
                count++;
            }
        }

        return count;
    }

    /**
     * Light promotion has lettuce and does not have bacon
     * @param ingredients
     * @return
     */
    private static boolean isLightPromotion(List<Ingredient> ingredients) {
        boolean hasLettuce = false;
        boolean hasBacon = false;
        boolean isLight = false;

        for (Ingredient ingredient : ingredients) {
            if (ingredient.getId() == LETTUCE_ID) {
                hasLettuce = true;
            }

            if (ingredient.getId() == BACON_ID) {
                hasBacon = true;
            }
        }

        if (hasLettuce && !hasBacon) {
            isLight = true;
        } else {
            isLight = false;
        }

        return isLight;
    }

}
