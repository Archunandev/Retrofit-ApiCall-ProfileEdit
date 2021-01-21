package com.example.retrofit.model


data class Data (

        val id : String,
        val user_type : String,
        val name : String,
        val code : String,
        val email : String,
        val phone : String,
        val token : String,
        val gender : String,
        val profile_pic : String,
        val active : String,
        val device_token : String,
        val email_verified_at : String,
        val blocked_at : String,
        val created_by : String,
        val updated_by : String,
        val deleted_at : String,
        val created_at : String,
        val updated_at : String,
        val key : String
)

data class Data2 (

        val id : String,
        val email : String,
        val first_name : String,
        val last_name : String,
        val avatar : String
)

data class jsonBase (

        val page : Int,
        val per_page : Int,
        val total : Int,
        val total_pages : Int,
        val data : List<Data2>,
        val support : Support
)


data class Support (

        val url : String,
        val text : String
)


data class profile (

        val success : String,
        val data : Data
)