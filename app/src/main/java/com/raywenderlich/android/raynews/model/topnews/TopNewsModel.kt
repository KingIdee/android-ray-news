package com.raywenderlich.android.raynews.model.topnews

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


data class TopNewsResponse (@SerializedName("status") @Expose var status: String,
                            @SerializedName("totalResults") @Expose var totalResults: Int,
                            @SerializedName("articles") @Expose var articles: List<Article>)

@Parcelize
data class Source (@SerializedName("id") @Expose var id: @RawValue Any,
                   @SerializedName("name") @Expose var name: String):Parcelable

@Parcelize
data class Article ( @SerializedName("source") @Expose var source: Source,
                    @SerializedName("author") @Expose var author: String,
                    @SerializedName("title") @Expose var title: String,
                    @SerializedName("description") @Expose var description: String,
                    @SerializedName("url") @Expose var url: String,
                    @SerializedName("urlToImage") @Expose var urlToImage: String,
                    @SerializedName("publishedAt") @Expose var publishedAt: String):Parcelable