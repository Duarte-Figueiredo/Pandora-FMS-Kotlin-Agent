package com.duartefigueiredo.pandorafmsagent.customfields

import com.duartefigueiredo.pandorafmsagent.NodeCompatible
import com.duartefigueiredo.pandorafmsagent.customfields.CustomFieldConst.FIELD_MODULE
import com.duartefigueiredo.pandorafmsagent.customfields.CustomFieldConst.FIELD_NAME
import com.duartefigueiredo.pandorafmsagent.customfields.CustomFieldConst.FIELD_VALUE
import org.redundent.kotlin.xml.xml

data class CustomFields(
    val name: String,
    val value: String
) : NodeCompatible() {

    override fun toNode() = xml(FIELD_MODULE) {
        FIELD_NAME {
            cdata(name)
        }
        FIELD_VALUE {
            cdata(value)
        }
    }
}
