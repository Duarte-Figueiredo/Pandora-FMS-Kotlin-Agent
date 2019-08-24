package com.duartefigueiredo.pandorafmsagent.agent

import com.duartefigueiredo.pandorafmsagent.NodeCompatible
import com.duartefigueiredo.pandorafmsagent.agent.AgentConst.AGENT_DATA
import com.duartefigueiredo.pandorafmsagent.agent.AgentConst.AGENT_DESCRIPTION
import com.duartefigueiredo.pandorafmsagent.agent.AgentConst.AGENT_GROUP
import com.duartefigueiredo.pandorafmsagent.agent.AgentConst.AGENT_INTERVAL
import com.duartefigueiredo.pandorafmsagent.agent.AgentConst.AGENT_NAME
import com.duartefigueiredo.pandorafmsagent.agent.AgentConst.AGENT_OS_NAME
import com.duartefigueiredo.pandorafmsagent.agent.AgentConst.AGENT_OS_VERSION
import com.duartefigueiredo.pandorafmsagent.agent.AgentConst.AGENT_TIMESTAMP
import com.duartefigueiredo.pandorafmsagent.agent.AgentConst.AGENT_TIMEZONE_OFFSET
import com.duartefigueiredo.pandorafmsagent.agent.AgentConst.AGENT_VERSION
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml

private const val EMPTY_STRING = ""

data class Agent(
    val agentName: String,
    val agentAlias: String = EMPTY_STRING,

    val agentDescription: String = EMPTY_STRING,
    val agentGroup: String = EMPTY_STRING,

    val interval: Int = 300,
    val timezoneOffset: String = "0",

    val osName: String = EMPTY_STRING,
    val osVersion: String = EMPTY_STRING,

    val appVersion: String = EMPTY_STRING,
    val appName: String = EMPTY_STRING

) : NodeCompatible() {

    override fun toNode() = xml(AGENT_DATA) {
        this.addAttributeIfNotBlank(AGENT_DESCRIPTION, agentDescription)
        this.addAttributeIfNotBlank(AGENT_GROUP, agentGroup)
        this.addAttributeIfNotBlank(AGENT_OS_NAME, osName)
        this.addAttributeIfNotBlank(AGENT_OS_VERSION, osVersion)
        this.addAttributeIfNotBlank(AGENT_INTERVAL, "$interval")
        this.addAttributeIfNotBlank(AGENT_VERSION, appVersion)
        this.addAttributeIfNotBlank(AGENT_TIMESTAMP, "")
        this.addAttributeIfNotBlank(AGENT_NAME, appName)
        this.addAttributeIfNotBlank(AGENT_TIMEZONE_OFFSET, timezoneOffset)
    }

    private fun Node.addAttributeIfNotBlank(name: String, value: String) {
        if (value.isNotBlank())
            this.attribute(name, value)
    }
}