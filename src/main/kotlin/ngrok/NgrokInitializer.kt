package ngrok

import database.bank.BankDatabase
import kotlinx.coroutines.*
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Service
import tools.Config
import tools.printCustom
import tools.saveNgrokUrl

@Service
class NgrokInitializer (
    private val ngrokManager: NgrokManager,
    private val config: Config,
    private val database: BankDatabase
): ApplicationRunner {

    suspend fun initializeNgrokAndTunnels() {
        val portsList = config.getNgrokPortsList()
        val deferred = CompletableDeferred<Unit>()
        withContext(Dispatchers.IO) {
            val job = launch {
                var count = 0
                do {
                    delay(1000L)
                    if (ngrokManager.isRunning()) {
                        count = ngrokManager.listTunnels().size
                        if (ngrokManager.listTunnels().isNotEmpty()) {
                            for (i in ngrokManager.listTunnels()) {
                                if (i.name == "command_line") {
                                    ngrokManager.stopTunnel("command_line")
                                    count = ngrokManager.listTunnels().size
                                }
                            }
                        }
                        if (count < portsList.size) {
                            for(port in portsList){
                                try{
                                    ngrokManager.listTunnels().first{
                                        it.config.addr == "http://localhost:$port"
                                    }
                                }catch (e: NoSuchElementException){
                                    ngrokManager.startTunnel(
                                        port,
                                        "http",
                                        "http-$port"
                                    )
                                }
                            }
                        }
                    }
                } while (count < portsList.size)

                if (ngrokManager.listTunnels().isNotEmpty()) {
                    for(port in portsList){
                        try{
                            val url = ngrokManager.listTunnels().first{
                                it.config.addr == "http://localhost:$port"
                            }.publicUrl
                            if (port == config.getServerPort()){
                                database.saveImageUrl(url)
                            }
                            saveNgrokUrl(url, "ngrok_url_$port")
                            println("saved ngrok public url: $url")
                        }catch (e: NoSuchElementException){
                            printCustom(e.message)
                        }
                    }
                }
                deferred.complete(Unit)
            }
            deferred.await()
            job.cancel()
        }
    }

    override fun run(args: ApplicationArguments?) {
        runBlocking {
            launch(Dispatchers.IO) {
                initializeNgrokAndTunnels()
            }
        }
    }
}