package com.emix.tempest.data.database.entity


import com.google.gson.annotations.SerializedName

/**
*  It is sub class of json api response which is used in database entity(table)
*  To provide data in an encapsulated form to provide more readability to code
 * * */

data class Condition(
    val code: Int,
    val icon: String,
    val text: String
)