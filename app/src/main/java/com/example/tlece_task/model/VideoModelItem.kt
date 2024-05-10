package com.example.tlece_task.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "videos")
data class VideoModelItem(
    @PrimaryKey @SerializedName("id")
    @ColumnInfo(name = "id") var id: String,

    @SerializedName("author")
    @ColumnInfo(name = "author") val author: String?,

    @SerializedName("description")
    @ColumnInfo(name = "description") val description: String?,

    @SerializedName("duration")
    @ColumnInfo(name = "duration") val duration: String?,

    @SerializedName("isLive")
    @ColumnInfo(name = "isLive") val isLive: Boolean?,

    @SerializedName("subscriber")
    @ColumnInfo(name = "subscriber") val subscriber: String?,

    @SerializedName("thumbnailUrl")
    @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl: String?,

    @SerializedName("title")
    @ColumnInfo(name = "title") val title: String?,

    @SerializedName("uploadTime")
    @ColumnInfo(name = "uploadTime") val uploadTime: String?,

    @SerializedName("videoUrl")
    @ColumnInfo(name = "videoUrl") val videoUrl: String?,

    @SerializedName("views")
    @ColumnInfo(name = "views") val views: String?
) : Serializable