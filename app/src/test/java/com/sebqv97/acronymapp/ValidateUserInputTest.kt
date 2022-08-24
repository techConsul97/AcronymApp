package com.sebqv97.acronymapp

import com.sebqv97.acronymapp.common.utils.validatePassword
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class ValidateUserInputTest {

    @Test
    fun `pass empty string _ expect false`(){
        val emptyPassWord = ""

        //assert that result is false
        assertEquals(false, validatePassword(emptyPassWord))
    }

    @Test
    fun `pass string with length 5 and length 17`(){
       val SizeFiveString = "1ffA."
        val SizeSevenTeenString = "fdfdsfssdfdfds;'A2"

        //assert that results are false

        assertEquals(false, validatePassword(SizeFiveString))
        assertEquals(false, validatePassword(SizeSevenTeenString))

    }

    @Test
    fun `pass correct length but one of the conditions is not met`(){
        val stringList = listOf("sdfdsfq12q","dsfdgfA234","sdfdsgfghdA3r",";;'dgwra32dsfds")

        for(element in stringList)
            assertEquals(false, validatePassword(element))
    }

    @Test
    fun `pass correct passwords _ expect true`(){
        val passwords = listOf("sdfsgew1;A","sdfgfhflk3/G","Adabsdf66.")
        for(password in passwords)
        assertEquals(true, validatePassword(password))
    }

}