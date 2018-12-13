package com.marcelokmats.lanchonete.util;

import android.util.SparseArray;

import com.marcelokmats.lanchonete.model.CustomIngredient;
import com.marcelokmats.lanchonete.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientUtil {

    public static final int MAXIMUM_INGREDIENT_AMOUNT = 100;

    /**
     * Creates a SparseArray of CustomIngredients from a list of ingredients IDs.
     * Uses the SparseArray of all ingredients to create a CustomIngredients.
     * @param allIngredients The SparseArray of all ingredients
     * @param ingredientIds The IDs of ingredients
     *                      (can have repeated IDs when there's more than 1 ingredient of the same type)
     * @return The SparseArray of CustomIngredients
     */
    public static SparseArray<CustomIngredient> createCustomIngredients(SparseArray<Ingredient> allIngredients,
                                                                        List<Integer> ingredientIds) {
        SparseArray<CustomIngredient> customIngredientList = createEmptyCustomIngredientList(allIngredients);
        if (customIngredientList != null && ingredientIds != null) {
            for (Integer ingredientId : ingredientIds) {
                CustomIngredient customIngredient = customIngredientList.get(ingredientId);

                if (customIngredient != null) {
                    customIngredient.setAmount(customIngredient.getAmount() + 1);
                }
            }
        }

        return customIngredientList;
    }

    /**
     * Creates a list of ingredients IDs from a SparseArray of CustomIngredients.
     * Can have repeated IDs when there's more than 1 ingredient of the same type
     * @param customIngredients The SparseArray of CustomIngredients.
     * @return The IDs of ingredients
     */
    public static List<Integer> transformCustomIngredientsList(SparseArray<CustomIngredient> customIngredients) {
        List<Integer> ingredientIdList = new ArrayList<>();

        for (int i = 0; i < customIngredients.size(); i++) {
            int key = customIngredients.keyAt(i);

            CustomIngredient customIngredient = customIngredients.get(key);

            for (int j = 0; j < customIngredient.getAmount(); j++) {
                ingredientIdList.add(customIngredient.getIngredientId());
            }
        }

        return ingredientIdList;
    }

    /**
     * Simply transform a List into a SparseArray
     * @param ingredientList The list to be transformed
     * @return The SparseArray
     */
    public static SparseArray<Ingredient> transformListIntoSparseArray(List<Ingredient> ingredientList) {
        SparseArray<Ingredient> ingredientSparseArray = new SparseArray<>();

        if (ingredientList != null) {
            for (Ingredient ingredient : ingredientList) {
                ingredientSparseArray.put(ingredient.getId(), ingredient);
            }
        }

        return ingredientSparseArray;
    }

    /**
     * Creates a String containing each ingredient name and its quantity.
     * Used to display the ingredients
     * @param ingredientsIds The list of ingredients IDs (can have repeated IDs)
     * @param allIngredients The SparseArray of all ingredients
     * @return The ingredients String
     */
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

    /**
     * Creates a SparseArray of CustomIngredients
     * @param ingredientIds The IDs of ingredients (can have repeated IDs)
     * @param allIngredients The SparseArray of all ingredients
     * @return The SparseArray of CustomIngredients created from the list of IDs
     */
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

    /**
     * Creates a SparseArray of CustomIngredients, all of them with amount zero
     * @param ingredients The list of ingredients
     * @return The SparseArray of CustomIngredients
     */
    private static SparseArray<CustomIngredient> createEmptyCustomIngredientList(SparseArray<Ingredient> ingredients) {
        SparseArray<CustomIngredient> customIngredientList = new SparseArray<>();

        for (int i = 0; i < ingredients.size(); i++) {
            int key = ingredients.keyAt(i);

            Ingredient ingredient = ingredients.get(key);
            CustomIngredient customIngredient = new CustomIngredient(0, ingredient);
            customIngredientList.put(ingredient.getId(), customIngredient);
        }

        return customIngredientList;
    }

}
