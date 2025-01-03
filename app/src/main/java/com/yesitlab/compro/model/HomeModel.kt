package com.yesitlab.compro.model

data class HomeModel(
    var textProfession: String,
    var textName: String,
    var textEmail: String,
    var textRatingNumber: String,
    var textDescription: String,
    var childModel: MutableList<String>?
    )
