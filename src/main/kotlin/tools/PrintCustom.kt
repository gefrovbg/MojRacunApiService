package tools

import java.io.*
import java.time.LocalDateTime

fun printCustom(message: Any?){

    try {
        println(message)
        val file = File("log_file.txt")

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

        val newContents = "${LocalDateTime.now()} $message\n$contents"
        val writer = BufferedWriter(FileWriter(file))
        writer.write(newContents)
        writer.close()
    }catch (e: IOException){
        println(e.message)
    }

}