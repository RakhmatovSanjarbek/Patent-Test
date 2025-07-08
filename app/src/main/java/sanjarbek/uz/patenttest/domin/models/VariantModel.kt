package sanjarbek.uz.patenttest.domin.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Suppress("DEPRECATED_ANNOTATION")
@Parcelize
data class VariantModel(
    val variantNumber: String? = null,
    val questionModel: List<QuestionModel>?=null,
): Parcelable
