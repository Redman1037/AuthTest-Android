package com.appstyx.authtest.models

data class User(
    var _id:String?=null,
    var email:String?=null,
    var firstName:String?=null,
    var lastName:String?=null,
    var gender:String?=null,
    var avatarURL:String?=null,
    var provider:String?=null,
    var username:String?=null,
    var createdAt:String?=null,
    var updatedAt:String?=null,
) {
}