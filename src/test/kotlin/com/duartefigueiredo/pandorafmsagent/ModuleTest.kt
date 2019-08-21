package com.duartefigueiredo.pandorafmsagent

import org.junit.Assert
import org.junit.Test

class ModuleTest {

    @Test
    fun toXml() {
        val module = Module(
            "battery_level",
            "The current Battery level",
            ModuleType.GENERIC_DATA,
            data = listOf("100")
        )

        val actual = module.toXml()

        println(actual)
        Assert.assertEquals(expected, actual)
    }

}

val expected =
    """<module><name><![CDATA[battery_level]]></name><description><![CDATA[The current Battery level]]></description><type><![CDATA[generic_data]]></type><data><![CDATA[100]]></data></module>"""