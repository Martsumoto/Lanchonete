package com.marcelokmats.lanchonete.util;

import android.util.SparseArray;

import com.marcelokmats.lanchonete.model.Sandwich;

import java.util.List;

public class SandwichUtil {

    /**
     * Simply transform a List into a SparseArray
     * @param sandwichList The list to be transformed
     * @return The SparseArray
     */
    public static SparseArray<Sandwich> transformListIntoSparseArray(List<Sandwich> sandwichList) {
        SparseArray<Sandwich> ingredientSparseArray = new SparseArray<>();

        if (sandwichList != null) {
            for (Sandwich sandwich : sandwichList) {
                ingredientSparseArray.put(sandwich.getId(), sandwich);
            }
        }

        return ingredientSparseArray;
    }
}
