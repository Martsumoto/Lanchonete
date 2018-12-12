package com.marcelokmats.lanchonete.util;

import android.util.SparseArray;

import com.marcelokmats.lanchonete.model.Ingredient;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PriceUtil {

    public static final int HAMBURGER_ID_PROMOTION = 3;
    public static final int CHEESE_ID_PROMOTION = 5;
    public static final int AMOUNT_PROMOTION = 3;
    public static final BigDecimal LIGHT_PROMOTION_DISCOUNT = BigDecimal.valueOf(0.1);

    // For light promotion
    public static final int LETTUCE_ID = 1;
    public static final int BACON_ID = 2;

    public static BigDecimal value(List<Integer> ingredientIdList, SparseArray<Ingredient> allIngredients) {
        List<Ingredient> ingredientList = new ArrayList<>();

        for (Integer ingredientId : ingredientIdList) {
            if (allIngredients.get(ingredientId) != null) {
                ingredientList.add(allIngredients.get(ingredientId));
            }
        }

        return PriceUtil.value(ingredientList);
    }

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
