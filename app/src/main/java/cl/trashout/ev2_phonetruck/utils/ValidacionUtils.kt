package cl.trashout.ev2_phonetruck.utils

import android.util.Patterns

object ValidacionUtils {
    private val passwordRegex = Regex("^(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{6,}$")

    fun isValidPassword(password: String): Boolean = passwordRegex.matches(password)
//    {
//        return password.length >= 6 &&
//                password.any { it.isUpperCase() } &&
//                password.any { it.isDigit() } &&
//                password.any { !it.isLetterOrDigit() }
//    }

    fun isMatchingPasswords(password: String, confirm: String): Boolean {
        return confirm.isNotEmpty() && password == confirm
    }
//    fun isValidEmail(email: String): Boolean {
//        return email.contains("@") && email.contains(".")
//    }
    fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isNotEmpty(text: String): Boolean = text.isNotBlank()
}