package com.job.test

object InputValidator {

    fun getUserErrorOrNull(input: String): Int? {
        return when {
            input.isEmpty() -> R.string.name_required
            input.length < 2 -> R.string.name_too_short
            else -> null
        }
    }

    fun getPasswordErrorOrNull(input: String): Int? {
        return when {
            input.isEmpty() -> R.string.password_required
            input.length < 3 -> R.string.password_too_short
            else -> null
        }
    }

}
