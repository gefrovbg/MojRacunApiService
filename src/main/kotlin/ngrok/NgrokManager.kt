package ngrok

import ngrok.api.NgrokAgentApiClient
import ngrok.api.model.NgrokTunnel
import ngrok.configuration.NgrokConfiguration
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
class NgrokManager(
    private val configuration: NgrokConfiguration
) {
    private val apiClient = NgrokAgentApiClient(configuration)

    fun startTunnel(port: Int, proto: String, name: String) {
        apiClient.startTunnel(
            port,
            proto,
            name
        )
    }

    fun stopTunnel(name: String) {
        apiClient.stopTunnel(name)
    }

    fun isRunning(): Boolean {
        return apiClient.isRunning
    }

    fun listTunnels(): List<NgrokTunnel> {
        return apiClient.listTunnels()
    }
}