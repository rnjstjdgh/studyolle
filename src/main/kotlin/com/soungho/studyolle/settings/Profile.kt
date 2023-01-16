package com.soungho.studyolle.settings

import com.soungho.studyolle.domian.Account

class Profile(
    var bio: String,
    var url: String?,
    var occupation: String,
    var location: String
) {
    constructor(account: Account) : this(
        bio = account.bio,
        url = account.url,
        occupation = account.occupation,
        location = account.location
    )
}