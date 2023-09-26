package br.com.shopeasy.shopeasy.util

import java.io.IOException
import java.nio.charset.Charset


object ResourceUtils {
    fun getContentFromResource(resourceName: String): String {
        try {
            val stream = ResourceUtils::class.java.getResourceAsStream(resourceName)
            return stream?.let { inputStream ->
                inputStream.bufferedReader(Charset.forName("UTF-8")).use { reader ->
                    reader.readText()
                }
            } ?: throw IOException("Resource not found: $resourceName")
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}

