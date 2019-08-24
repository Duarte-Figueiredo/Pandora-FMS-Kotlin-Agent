package com.duartefigueiredo.pandorafmsagent.module

import org.junit.Assert
import org.junit.Test

class ModuleTest {

    @Test
    fun toXml() {
        // Arrange
        val module = Module(
            "battery_level",
            "The current Battery level",
            ModuleType.GENERIC_DATA,
            data = listOf("100")
        )

        val expected = ModuleTest::class.java.classLoader.getResource("module/example_module_1.xml").readText()


        // Act
        val actual = module.node.toString(false)

        // Assert
        Assert.assertEquals(expected, actual)
    }

}
