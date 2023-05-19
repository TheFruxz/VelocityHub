package de.fruxz.vhub

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Dependency
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import com.velocitypowered.api.proxy.server.RegisteredServer
import de.fruxz.vhub.command.HubCommand
import java.nio.file.Files
import java.nio.file.Paths
import java.util.logging.Logger
import javax.inject.Inject
import kotlin.io.path.div
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
        instance = this

        val commandManager = proxy.commandManager

        commandManager.register("hub", HubCommand.create(proxy))

    }

    companion object {

        lateinit var instance: VelocityHub

        val gson = GsonBuilder().setPrettyPrinting().create()
        val configFile = Paths.get("") / "plugins" / "VelocityHub" / "config.json"

        var configuredHubs: List<String>
            get() {
                if (Files.notExists(configFile)) {
                    Files.createDirectories(configFile.parent)
                    Files.createFile(configFile)
                    Files.writeString(configFile, gson.toJson(listOf("lobby", "hub")))
                }

                @Suppress("UNCHECKED_CAST")
                return gson.fromJson(Files.readString(configFile), List::class.java) as? List<String> ?: throw IllegalStateException("Invalid config file.")

            }
            set(value) {
                Files.writeString(configFile, gson.toJson(value))
            }

        val lobby: RegisteredServer? by lazy {
            configuredHubs.firstNotNullOfOrNull { instance.proxy.getServer(it).getOrNull() }
        }

    }

}