package com.sebqv97.acronymapp.common.utils

object PasswordUtils {
    fun validatePassword(password:String):Boolean{
        val passwordLength = password.length
        val specialCharacters = "!@£$%^&*();:~|`?/<>.,"

        var specialCharacterUsed = false
        var uppercaseCharacterUsed = false
        var correctLength = false
        var digitUsed = false
        var lowerCaseUsed = false


        if(passwordLength in 8..16)
        {
            correctLength = true
            for(character in password){
                if(specialCharacters.indexOf(character)> -1){
                    specialCharacterUsed = true
                }
                if(character.isUpperCase())
                {
                    uppercaseCharacterUsed = true
                }
                if(character.isDigit())
                {
                    digitUsed = true
                }
                if(character.isLowerCase())
                {
                    lowerCaseUsed = true
                }
            }
//            if(correctLength == uppercaseCharacterUsed == specialCharacterUsed == digitUsed == true){
//                return true
            if(correctLength == true && uppercaseCharacterUsed == true && specialCharacterUsed == true && digitUsed == true && lowerCaseUsed == true)
                return true
        }


        return false
    }
}