package com.example.network.apiModel

import android.os.Parcel
import android.os.Parcelable





data class HomeResponse(
    var age: String? = null, // Changed to nullable String
    var average_rating: Int = 0,
    var certificates: MutableList<Certificate> = mutableListOf(),
    var country: String = "",
    var education: MutableList<Education> = mutableListOf(),
    var email: String = "",
    var experience_q: String? = null, // Changed to nullable String
    var firstName: String = "",
    var gender: String = "",
    var goal_q: String? = null, // Changed to nullable String
    var id: Int = -1,
    var image: String = "",
    var is_contacted: Boolean = false,
    var lastName: String = "",
    var mobile: String = "",
    var rating_count: Int = 0,
    var resume: String = "",
    var skills: MutableList<Skill> = mutableListOf(),
    var status: String = "",
    var title: String = "",
    var userType: String = "",
    var work_preference_q: String? = null // Changed to nullable String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        TODO("certificates"),
        parcel.readString().toString(),
        TODO("education"),
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        TODO("skills"),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(age)
        parcel.writeInt(average_rating)
        parcel.writeString(country)
        parcel.writeString(email)
        parcel.writeString(experience_q)
        parcel.writeString(firstName)
        parcel.writeString(gender)
        parcel.writeString(goal_q)
        parcel.writeInt(id)
        parcel.writeString(image)
        parcel.writeByte(if (is_contacted) 1 else 0)
        parcel.writeString(lastName)
        parcel.writeString(mobile)
        parcel.writeInt(rating_count)
        parcel.writeString(resume)
        parcel.writeString(status)
        parcel.writeString(title)
        parcel.writeString(userType)
        parcel.writeString(work_preference_q)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HomeResponse> {
        override fun createFromParcel(parcel: Parcel): HomeResponse {
            return HomeResponse(parcel)
        }

        override fun newArray(size: Int): Array<HomeResponse?> {
            return arrayOfNulls(size)
        }
    }
}

data class Certificate(
    var certificate_completion_id: String = "",
    var certificate_name: String = "",
    var certificate_prof: Int = 0,
    var certificate_url: String = "",
    var created_at: String = "",
    var end_date: String = "",
    var id: Int = 0,
    var image: String = "",
    var profile_id: Int = 0,
    var start_date: String = "",
    var updated_at: String = "",
    var user_id: Int = 0
)

data class Education(
    var created_at: String = "",
    var degree: String = "",
    var description: String = "",
    var end_date: String = "",
    var fieldstudy: String = "",
    var grade: String = "",
    var id: Int = 0,
    var profile_id: Int = 0,
    var sch_uni: String = "",
    var start_date: String = "",
    var updated_at: String = "",
    var user_id: Int = 0
)

data class Skill(
    var created_at: String = "",
    var id: Int = 0,
    var profile_id: Int = 0,
    var skill_user: String = "",
    var updated_at: String = "",
    var user_id: Int = 0
)

