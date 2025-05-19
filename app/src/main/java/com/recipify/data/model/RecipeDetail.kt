package com.recipify.data.model

data class RecipeDetail(
    val id: Int,
    val title: String,
    val image: String,
    val readyInMinutes: Int,
    val servings: Int,
    val healthScore: Int,
    val extendedIngredients: List<Ingredient>,
    val analyzedInstructions: List<AnalyzedInstruction>
)

data class Ingredient(
    val original: String
)

data class AnalyzedInstruction(
    val steps: List<InstructionStep>
)

data class InstructionStep(
    val number: Int,
    val step: String
)
