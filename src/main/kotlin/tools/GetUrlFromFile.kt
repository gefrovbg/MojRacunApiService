package tools

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

internal fun getUrlFromFile(fileName: String): String{
    val file = File("$fileName.txt")

    if (!file.exists()) {
        file.createNewFile()
        println("File created successfully.")
    }

    val reader = BufferedReader(FileReader(file))
    val contents = StringBuilder()
    var line = reader.readLine()
    while (line != null) {
        contents.appendln(line)
        line = reader.readLine()
    }
    reader.close()

    return if (contents.isNotBlank()){
        println(contents.toString().dropLast(1))
        contents.toString().dropLast(1)
    }else{
        ""
    }
}