package com.calories.calorieslookout.network

import com.squareup.moshi.Json
import kotlin.math.roundToInt

data class BreakfastResponse(

    @Json(name="hits")
    val hits: List<HitsItem?>? = null,

    @Json(name="q")
    val Q: String? = null,

    @Json(name="more")
    val more: Boolean? = null,

    @Json(name="count")
    val count: Int? = null,

    @Json(name="from")
    val from: Int? = null,

    @Json(name="to")
    val to: Int? = null
)

data class VITB12(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class FE(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class DigestItem(

    @Json(name="schemaOrgTag")
    val schemaOrgTag: String? = null,

    @Json(name="total")
    val total: Double? = null,

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="daily")
    val daily: Double? = null,

    @Json(name="hasRDI")
    val hasRDI: Boolean? = null,

    @Json(name="label")
    val label: String? = null,

    @Json(name="tag")
    val tag: String? = null,

    @Json(name="sub")
    val sub: List<SubItem?>? = null
)

data class TotalDaily(

    @Json(name="VITB6A")
    val vITB6A: VITB6A? = null,

    @Json(name="VITC")
    val vITC: VITC? = null,

    @Json(name="CHOCDF")
    val cHOCDF: CHOCDF? = null,

    @Json(name="K")
    val K: K? = null,

    @Json(name="VITD")
    val vITD: VITD? = null,

    @Json(name="P")
    val P: P? = null,

    @Json(name="CHOLE")
    val cHOLE: CHOLE? = null,

    @Json(name="ENERC_KCAL")
    val eNERCKCAL: ENERCKCAL? = null,

    @Json(name="FASAT")
    val fASAT: FASAT? = null,

    @Json(name="VITK1")
    val vITK1: VITK1? = null,

    @Json(name="MG")
    val mG: MG? = null,

    @Json(name="RIBF")
    val rIBF: RIBF? = null,

    @Json(name="CA")
    val cA: CA? = null,

    @Json(name="NIA")
    val nIA: NIA? = null,

    @Json(name="THIA")
    val tHIA: THIA? = null,

    @Json(name="FIBTG")
    val fIBTG: FIBTG? = null,

    @Json(name="VITB12")
    val vITB12: VITB12? = null,

    @Json(name="TOCPHA")
    val tOCPHA: TOCPHA? = null,

    @Json(name="PROCNT")
    val pROCNT: PROCNT? = null,

    @Json(name="FOLDFE")
    val fOLDFE: FOLDFE? = null,

    @Json(name="NA")
    val nA: NA? = null,

    @Json(name="ZN")
    val zN: ZN? = null,

    @Json(name="VITA_RAE")
    val vITARAE: VITARAE? = null,

    @Json(name="FAT")
    val fAT: FAT? = null,

    @Json(name="FE")
    val fE: FE? = null
)

data class VITC(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class MG(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class FOLFD(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class VITB6A(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class PROCNT(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class FOLDFE(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class THIA(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class FATRN(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class FAPU(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class TotalNutrients(

    @Json(name="VITB6A")
    val vITB6A: VITB6A? = null,

    @Json(name="FAMS")
    val fAMS: FAMS? = null,

    @Json(name="VITC")
    val vITC: VITC? = null,

    @Json(name="CHOCDF")
    val cHOCDF: CHOCDF? = null,

    @Json(name="K")
    val K: K? = null,

    @Json(name="VITD")
    val vITD: VITD? = null,

    @Json(name="FATRN")
    val fATRN: FATRN? = null,

    @Json(name="P")
    val P: P? = null,

    @Json(name="CHOLE")
    val cHOLE: CHOLE? = null,

    @Json(name="ENERC_KCAL")
    val eNERCKCAL: ENERCKCAL? = null,

    @Json(name="FASAT")
    val fASAT: FASAT? = null,

    @Json(name="Sugar.alcohol")
    val sugarAlcohol: SugarAlcohol? = null,

    @Json(name="VITK1")
    val vITK1: VITK1? = null,

    @Json(name="CHOCDF.net")
    val cHOCDFNet: CHOCDFNet? = null,

    @Json(name="MG")
    val mG: MG? = null,

    @Json(name="SUGAR.added")
    val sUGARAdded: SUGARAdded? = null,

    @Json(name="RIBF")
    val rIBF: RIBF? = null,

    @Json(name="CA")
    val cA: CA? = null,

    @Json(name="FOLFD")
    val fOLFD: FOLFD? = null,

    @Json(name="WATER")
    val wATER: WATER? = null,

    @Json(name="FAPU")
    val fAPU: FAPU? = null,

    @Json(name="NIA")
    val nIA: NIA? = null,

    @Json(name="THIA")
    val tHIA: THIA? = null,

    @Json(name="FIBTG")
    val fIBTG: FIBTG? = null,

    @Json(name="VITB12")
    val vITB12: VITB12? = null,

    @Json(name="TOCPHA")
    val tOCPHA: TOCPHA? = null,

    @Json(name="SUGAR")
    val sUGAR: SUGAR? = null,

    @Json(name="PROCNT")
    val pROCNT: PROCNT? = null,

    @Json(name="FOLDFE")
    val fOLDFE: FOLDFE? = null,

    @Json(name="NA")
    val nA: NA? = null,

    @Json(name="ZN")
    val zN: ZN? = null,

    @Json(name="VITA_RAE")
    val vITARAE: VITARAE? = null,

    @Json(name="FAT")
    val fAT: FAT? = null,

    @Json(name="FOLAC")
    val fOLAC: FOLAC? = null,

    @Json(name="FE")
    val fE: FE? = null
)

data class ENERCKCAL(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class ZN(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class FAT(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class NIA(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class IngredientsItem(

    @Json(name="image")
    val image: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="measure")
    val measure: String? = null,

    @Json(name="foodId")
    val foodId: String? = null,

    @Json(name="weight")
    val weight: Double? = null,

    @Json(name="text")
    val text: String? = null,

    @Json(name="foodCategory")
    val foodCategory: String? = null,

    @Json(name="food")
    val food: String? = null
)

data class SUGARAdded(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class Recipe(

    @Json(name="image")
    val image: String? = null,

    @Json(name="shareAs")
    val shareAs: String? = null,

    @Json(name="cautions")
    val cautions: List<String?>? = null,

    @Json(name="healthLabels")
    val healthLabels: List<String?>? = null,

    @Json(name="totalTime")
    val totalTime: Double? = null,

    @Json(name="mealType")
    val mealType: List<String?>? = null,

    @Json(name="label")
    val label: String? = null,

    @Json(name="source")
    val source: String? = null,

    @Json(name="calories")
   private val calories: Double? = null,

    @Json(name="cuisineType")
    val cuisineType: List<String?>? = null,

    @Json(name="uri")
    val uri: String? = null,

    @Json(name="url")
    val url: String? = null,

    @Json(name="totalNutrients")
    val totalNutrients: TotalNutrients? = null,

    @Json(name="dietLabels")
    val dietLabels: List<String?>? = null,

    @Json(name="dishType")
    val dishType: List<String?>? = null,

    @Json(name="yield")
    val yield: Double? = null,

    @Json(name="totalWeight")
    val totalWeight: Double? = null,

    @Json(name="digest")
    val digest: List<DigestItem?>? = null,

    @Json(name="ingredients")
    val ingredients: List<IngredientsItem?>? = null,

    @Json(name="totalDaily")
    val totalDaily: TotalDaily? = null,

    @Json(name="ingredientLines")
    val ingredientLines: List<String?>? = null
){
    fun getCaloriesAsString():String = calories?.roundToInt()?.toString()?:""
    fun getCalories():Double = calories?:0.0

}

data class WATER(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class TOCPHA(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class CHOLE(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class FASAT(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class VITK1(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class CA(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class HitsItem(

    @Json(name="recipe")
    val recipe: Recipe? = null
)

data class FAMS(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class VITD(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class CHOCDFNet(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class K(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class P(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class RIBF(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class VITARAE(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class SubItem(

    @Json(name="schemaOrgTag")
    val schemaOrgTag: String? = null,

    @Json(name="total")
    val total: Double? = null,

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="daily")
    val daily: Double? = null,

    @Json(name="hasRDI")
    val hasRDI: Boolean? = null,

    @Json(name="label")
    val label: String? = null,

    @Json(name="tag")
    val tag: String? = null
)

data class SUGAR(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class SugarAlcohol(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class NA(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class CHOCDF(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class FIBTG(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)

data class FOLAC(

    @Json(name="unit")
    val unit: String? = null,

    @Json(name="quantity")
    val quantity: Double? = null,

    @Json(name="label")
    val label: String? = null
)
