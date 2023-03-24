package com.mordansoft.homebank.domain.repo

import com.mordansoft.homebank.domain.model.auth.LoggedInUser
import com.mordansoft.homebank.domain.model.auth.Result

interface LoginRepo {

    fun logout()

    fun login(username: String, password: String): Result<LoggedInUser>

}