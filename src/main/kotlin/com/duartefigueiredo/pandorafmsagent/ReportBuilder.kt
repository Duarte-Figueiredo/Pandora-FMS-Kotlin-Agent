package com.duartefigueiredo.pandorafmsagent

import com.duartefigueiredo.pandorafmsagent.agent.Agent
import com.duartefigueiredo.pandorafmsagent.customfields.CustomFields
import com.duartefigueiredo.pandorafmsagent.module.Module
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.PrintOptions
import java.io.File
import java.io.FileNotFoundException
import java.util.*

public class ReportBuilder(private val agent: Agent) {

    companion object {
        private val printOptions = PrintOptions(
            pretty = true,
            singleLineTextElements = false,
            useSelfClosingTags = true
        )
    }

    private val modules = mutableListOf<Module>()

    private val customFields = mutableListOf<CustomFields>()

    public fun addModule(module: Module): ReportBuilder =
        this.modules.add(module).let { this }

    public fun addCustomField(customFields: CustomFields) = this
        .customFields.add(customFields).let { this }

    public fun xml(): Node {
        val agentNode = agent.node

        agentNode.includeXmlProlog = true

        agentNode.let {
            modules.map { it.node }.forEach { module ->
                it.addNode(module)
            }

            customFields.map { it.node }.forEach { customField ->
                it.addNode(customField)
            }
        }

        return agentNode
    }

    public fun generateXml(): String = xml().toString(printOptions)

    public fun writeToFile(directoryPath: String, timestamp: Date): File {

        val directoryFile = File(directoryPath)

        if (!directoryFile.exists()) {
            throw FileNotFoundException("Directory $directoryPath does not exist")
        } else if (!directoryFile.canWrite()) {
            throw Exception("Directory $directoryPath is not writable")
        }

        val fileName = getFileName(timestamp)

        val file = File("$directoryPath/$fileName")

        file.printWriter().use { out ->
            out.print(generateXml())
        }

        return file
    }

    private fun getFileName(timestamp: Date): String {
        val epochStamp = timestamp.toInstant().toEpochMilli() / 1000

        return "${agent.agentName}.$epochStamp.data"
    }
}