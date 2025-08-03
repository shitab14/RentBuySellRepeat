package app.smir.rentbuysellrepeat.util.validation

import java.util.regex.Pattern

/**
Created by Shitab Mir on 3/8/25.
shitabmir@gmail.com
 **/

class EmailValidator {
    fun validate(email: String): Boolean {
        val pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
        return pattern.matcher(email).matches()
    }
}

class PasswordValidator {
    fun validate(password: String): Boolean {
        return password.length >= 6
    }
}

class NameValidator {
    fun validate(name: String): Boolean {
        return name.length >= 2
    }
}

class AddressValidator {
    fun validate(address: String): Boolean {
        return address.length >= 5
    }
}

class PhoneValidator {
    fun validate(phone: String): Boolean {
        val pattern = Pattern.compile("^[+]?[0-9]{10,13}\$")
        return pattern.matcher(phone).matches()
    }
}

class ConfirmPasswordValidator {
    fun validate(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }
}