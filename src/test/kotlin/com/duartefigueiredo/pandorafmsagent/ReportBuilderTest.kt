package com.duartefigueiredo.pandorafmsagent

import com.duartefigueiredo.pandorafmsagent.agent.Agent
import com.duartefigueiredo.pandorafmsagent.module.Module
import org.junit.Assert
import org.junit.Test
import java.time.Instant
import java.util.*

class ReportBuilderTest {

    @Test
    fun toNode() {
        // Arrange
        val agent = Agent("agentName")

        val module1 = simpleModule(1)
        val module2 = simpleModule(2)
        val module3 = simpleModule(3)

        val reportBuilder = ReportBuilder(agent)
            .addModule(module1)
            .addModule(module2)
            .addModule(module3)

        val expected =
            ReportBuilderTest::class.java.classLoader.getResource("agent/example_agent_file_2.xml").readText()

        // Act
        val actual = reportBuilder.generateXml()

        // Assert
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun randomTest() {
        val agent = Agent("agentName")

        val module1 = simpleModule(1)
        val module2 = simpleModule(2)
        val module3 = simpleModule(3)

        val reportBuilder = ReportBuilder(agent)
            .addModule(module1)
            .addModule(module2)
            .addModule(module3)

        reportBuilder.writeToFile("/home/duarte/tmp", Date.from(Instant.now()))


    }

    companion object {
        fun simpleModule(number: Int) =
            Module("module$number", "description$number")
    }
}