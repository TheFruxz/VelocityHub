package de.fruxz.vhub

import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Dependency
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.server.RegisteredServer
import de.fruxz.vhub.command.HubCommand
import java.util.logging.Logger
import javax.inject.Inject
import kotlin.jvm.optionals.getOrNull

@Plugin(
    id = "vhub",
    name = "Velocity/hub",
    version = "1.0",
    authors = ["Fruxz"],
    dependencies = [Dependency(id = "mckotlin-velocity", optional = true)]
)
class VelocityHub @Inject constructor(
    val proxy: ProxyServer,
    val logger: Logger,
) {

    @Subscribe
    @Suppress("UNUSED_PARAMETER")
    fun onProxyInitialization(event: ProxyInitializeEvent) {
        val commandManager = proxy.commandManager

        lobby = proxy.getServer("lobby").getOrNull() ?: proxy.getServer("hub").getOrNull()

        commandManager.register("hub", HubCommand.create(proxy))

    }

    companion object {
        lateinit var instance: VelocityHub
        var lobby: RegisteredServer? = null
    }

}