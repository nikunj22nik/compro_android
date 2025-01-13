package com.yesitlab.compro.model

data class AddExperienceModel(
    var id:Int?,
    var user_id:Int?,
    var profile_id:Int?,
    var title : String?,
    var company : String?,
    var location : String?,
    var currently_prof : Int?,
    var start_date : String?,
    var end_date : String?,
    var country : String?,
)

//"id": 66,
//"user_id": 140,
//"profile_id": null,
//"company": "company",
//"title": "title",
//"location": "location",
//"currently_prof": 1,
//"start_date": "2024-07-25",
//"end_date": "2024-07-26",
//"country": "country"
