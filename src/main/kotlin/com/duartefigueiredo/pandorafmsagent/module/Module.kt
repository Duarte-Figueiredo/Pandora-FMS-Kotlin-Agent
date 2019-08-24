package com.duartefigueiredo.pandorafmsagent.module

import com.duartefigueiredo.pandorafmsagent.NodeCompatible
import com.duartefigueiredo.pandorafmsagent.module.ModuleConst.MODULE
import com.duartefigueiredo.pandorafmsagent.module.ModuleConst.MODULE_CRITICAL_INVERSE
import com.duartefigueiredo.pandorafmsagent.module.ModuleConst.MODULE_DATA
import com.duartefigueiredo.pandorafmsagent.module.ModuleConst.MODULE_DATA_LIST
import com.duartefigueiredo.pandorafmsagent.module.ModuleConst.MODULE_DATA_LIST_VALUE
import com.duartefigueiredo.pandorafmsagent.module.ModuleConst.MODULE_DESCRIPTION
import com.duartefigueiredo.pandorafmsagent.module.ModuleConst.MODULE_GROUP
import com.duartefigueiredo.pandorafmsagent.module.ModuleConst.MODULE_MAX
import com.duartefigueiredo.pandorafmsagent.module.ModuleConst.MODULE_MAX_CRITICAL
import com.duartefigueiredo.pandorafmsagent.module.ModuleConst.MODULE_MAX_WARNING
import com.duartefigueiredo.pandorafmsagent.module.ModuleConst.MODULE_MIN
import com.duartefigueiredo.pandorafmsagent.module.ModuleConst.MODULE_MIN_CRITICAL
import com.duartefigueiredo.pandorafmsagent.module.ModuleConst.MODULE_MIN_WARNING
import com.duartefigueiredo.pandorafmsagent.module.ModuleConst.MODULE_NAME
import com.duartefigueiredo.pandorafmsagent.module.ModuleConst.MODULE_STATUS
import com.duartefigueiredo.pandorafmsagent.module.ModuleConst.MODULE_TAG
import com.duartefigueiredo.pandorafmsagent.module.ModuleConst.MODULE_TYPE
import com.duartefigueiredo.pandorafmsagent.module.ModuleConst.MODULE_UNIT
import com.duartefigueiredo.pandorafmsagent.module.ModuleConst.MODULE_WARNING_INVERSE
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.xml

private const val EMPTY_STRING = ""

data class Module(
    val name: String,
    val description: String,
    val moduleType: ModuleType = ModuleType.GENERIC_DATA,
    val data: List<String> = emptyList(),

    val moduleTags: String = EMPTY_STRING,
    val moduleGroups: String = EMPTY_STRING,
    val moduleState: ModuleState = ModuleState.NOT_SET,
    val moduleUnit: String = EMPTY_STRING,

    val max: String = EMPTY_STRING,
    val min: String = EMPTY_STRING,
    val minWarning: String = EMPTY_STRING,
    val maxWarning: String = EMPTY_STRING,
    val minCritical: String = EMPTY_STRING,
    val maxCritical: String = EMPTY_STRING,

    val warningInverted: Boolean = false,
    val criticalInverted: Boolean = false

) : NodeCompatible() {

    override fun toNode() = xml(MODULE) {

        this.simpleAdd(MODULE_NAME, name)

        this.simpleAdd(MODULE_DESCRIPTION, description)

        this.simpleAdd(MODULE_TYPE, moduleType)

        this.addDataList(data)

        this.addIfNotBlank(MODULE_GROUP, moduleGroups)

        this.addIfNotBlank(MODULE_TAG, moduleTags)

        if (moduleState != ModuleState.NOT_SET) {
            simpleAdd(MODULE_STATUS, moduleState)
        }

        this.addIfNotBlank(MODULE_UNIT, moduleUnit)

        this.addIfNotBlank(MODULE_MIN, min)
        this.addIfNotBlank(MODULE_MAX, max)

        this.addIfNotBlank(MODULE_MIN_WARNING, minWarning)
        this.addIfNotBlank(MODULE_MAX_WARNING, maxWarning)

        this.addIfNotBlank(MODULE_MIN_CRITICAL, minCritical)
        this.addIfNotBlank(MODULE_MAX_CRITICAL, maxCritical)

        if (warningInverted) {
            this.simpleAdd(
                MODULE_WARNING_INVERSE,
                ONE, false
            )
        }

        if (criticalInverted) {
            this.simpleAdd(
                MODULE_CRITICAL_INVERSE,
                ONE, false
            )
        }
    }


    companion object {
        private const val ONE = "1"

        fun Node.simpleAdd(name: String, value: Any, isCdata: Boolean = true) {
            this.addNode(xml(name) {
                if (isCdata) cdata(value.toString())
                else -value.toString()
            })
        }

        fun Node.addIfNotBlank(name: String, value: String, sanitized: Boolean = true) {
            if (value.isNotBlank()) simpleAdd(name, value, sanitized)
        }

        fun Node.addDataList(dataList: List<String>) {
            dataList.filter { it.isNotBlank() }.let {
                when {
                    it.isEmpty() -> return@let
                    it.size == 1 -> this.simpleAdd(MODULE_DATA, it.first())
                    else -> this.addNode(xml(MODULE_DATA_LIST) {
                        it.forEach { value ->
                            MODULE_DATA_LIST_VALUE {
                                cdata(value)
                            }
                        }
                    })
                }
            }
        }
    }
}
