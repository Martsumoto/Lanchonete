package com.marcelokmats.lanchonete.util;

import android.util.SparseArray;

import com.marcelokmats.lanchonete.model.CustomIngredient;
import com.marcelokmats.lanchonete.model.Ingredient;

import java.util.List;

public class IngredientUtil {

    public static SparseArray<Ingredient> transformListIntoSparseArray(List<Ingredient> ingredientList) {
        SparseArray<Ingredient> ingredientSparseArray = new SparseArray<>();

        if (ingredientList != null) {
            for (Ingredient ingredient : ingredientList) {
                ingredientSparseArray.put(ingredient.getId(), ingredient);
            }
        }

        return ingredientSparseArray;
    }

    public static String getIngredientsAsString(List<Integer> ingredientsIds,
                                                SparseArray<Ingredient> allIngredients) {
        String ingredientsString = "";
        SparseArray<CustomIngredient> customIngredients = getCustomIngredientsList(ingredientsIds, allIngredients);

        for (int i = 0; i < customIngredients.size(); i++) {
            int key = customIngredients.keyAt(i);

            CustomIngredient customIngredient = customIngredients.get(key);
            ingredientsString += customIngredient.getIngredient().getName();
            ingredientsString += ": ";
            ingredientsString += customIngredient.getAmount();
            ingredientsString += "\n";
        }

        return ingredientsString;
    }

    private static SparseArray<CustomIngredient> getCustomIngredientsList(List<Integer> ingredientIds,
                                                                          SparseArray<Ingredient> allIngredients) {
        SparseArray<CustomIngredient> ingredientList = null;
        CustomIngredient customIngredient;
        Ingredient ingredient;

        if (ingredientIds != null) {
            ingredientList = new SparseArray<>();

            for (Integer ingredientId : ingredientIds) {
                if (ingredientId != null) {
                    ingredient = allIngredients.get(ingredientId);

                    if (ingredient != null) {
                        customIngredient = ingredientList.get(ingredientId);

                        if (customIngredient == null) {
                            customIngredient = new CustomIngredient(1, ingredient);
                            ingredientList.put(ingredientId, customIngredient);
                        } else {
                            customIngredient.setAmount(customIngredient.getAmount() + 1);
                        }
                    }
                }
            }
        }

        return ingredientList;
    }
}
