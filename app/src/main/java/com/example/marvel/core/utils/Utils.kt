package com.example.marvel.core.utils

import androidx.appcompat.app.ActionBar
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Application specific static function which is used globally
 */
class Utils {

    /**
     * Generated Md5 key from the given String
     */
    companion object {
        fun getMd5(inputString: String): String {
            return try {

                // Static getInstance method is called with hashing MD5
                val md: MessageDigest = MessageDigest.getInstance("MD5")

                // digest() method is called to calculate message digest
                //  of an input digest() return array of byte
                val messageDigest: ByteArray = md.digest(inputString.toByteArray())

                // Convert byte array into signum representation
                val no = BigInteger(1, messageDigest)

                // Convert message digest into hex value
                var hashText: String = no.toString(16)
                while (hashText.length < 32) {
                    hashText = "0$hashText"
                }
                hashText
            } // For specifying wrong message digest algorithms
            catch (e: NoSuchAlgorithmException) {
                throw RuntimeException(e)
            }
        }

        /**
         * Sets the given title
         */
        fun setActivityTitle(supportActionBar: ActionBar?, title: String) {
            supportActionBar?.title = title
        }
    }
}