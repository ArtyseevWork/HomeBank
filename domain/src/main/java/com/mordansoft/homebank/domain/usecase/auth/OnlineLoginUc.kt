package com.mordansoft.homebank.domain.usecase.auth

import com.mordansoft.homebank.domain.model.auth.LoggedInUser
import com.mordansoft.homebank.domain.model.auth.Result
import com.mordansoft.homebank.domain.repo.LoginRepo

class OnlineLoginUc(private val loginRepo : LoginRepo) {

    fun execute(username: String, password: String): Result<LoggedInUser> {
        return loginRepo.login(username = username, password = password)
    }
}