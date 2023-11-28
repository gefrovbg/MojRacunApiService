package tools

import java.io.*

fun saveNgrokUrl(url:String, fileName: String){
    try {
//        println(url)
        val file = File("$fileName.txt")

        if (!file.exists()) {
            file.createNewFile()
            println("File created successfully.")
        }

        val writer = BufferedWriter(FileWriter(file))
        writer.write(url)
        writer.close()
    }catch (e: IOException){
        println(e.message)
    }
}