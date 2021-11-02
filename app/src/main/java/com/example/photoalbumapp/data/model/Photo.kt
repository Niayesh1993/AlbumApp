package com.example.photoalbumapp.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Photo() : Parcelable {

    @SerializedName("id")
    private val id: String = ""

    @SerializedName("user_id")
    private val user_id: String = ""

    @SerializedName("media_type")
    private val media_type: String = ""

    @SerializedName("filename")
    private val filename: String = ""

    @SerializedName("size")
    private val size: Long = 0

    @SerializedName("created_at")
    private val created_at: String = ""

    @SerializedName("taken_at")
    private val taken_at: String? = null

    @SerializedName("guessed_taken_at")
    private val guessed_taken_at: String? = null

    @SerializedName("md5sum")
    private val md5sum: String = ""

    @SerializedName("content_type")
    private val content_type: String = ""

    @SerializedName("video")
    private val video: String? = null

    @SerializedName("thumbnail_url")
    private val thumbnail_url: String = ""

    @SerializedName("download_url")
    private val download_url: String = ""

    @SerializedName("resx")
    private val resx: Long = 0

    @SerializedName("resy")
    private val resy: Long = 0

    constructor(parcel: Parcel) : this() {
    }

    fun getPhoto(): String {
        return thumbnail_url
    }
    fun getDate(): String {
        return created_at
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Photo> {
        override fun createFromParcel(parcel: Parcel): Photo {
            return Photo(parcel)
        }

        override fun newArray(size: Int): Array<Photo?> {
            return arrayOfNulls(size)
        }
    }

}