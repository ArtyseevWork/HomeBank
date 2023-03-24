package com.mordansoft.homebank.data.storage

import android.app.Activity
import android.content.Intent
import android.provider.Settings.System.getString
import androidx.annotation.NonNull
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.mordansoft.homebank.domain.model.auth.LoggedInUser
import java.io.IOException
import java.util.*
import com.mordansoft.homebank.domain.model.auth.Result

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource (private val activity : Activity) {

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication

            val fakeUser = LoggedInUser(UUID.randomUUID().toString(), "Jane Doe")
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }


    fun logout() {
        // TODO: revoke authentication
    }
}