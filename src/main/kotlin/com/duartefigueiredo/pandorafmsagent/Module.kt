package com.duartefigueiredo.pandorafmsagent

import com.duartefigueiredo.pandorafmsagent.ModuleConst.MODULE
import com.duartefigueiredo.pandorafmsagent.ModuleConst.MODULE_CRITICAL_INVERSE
import com.duartefigueiredo.pandorafmsagent.ModuleConst.MODULE_DATA
import com.duartefigueiredo.pandorafmsagent.ModuleConst.MODULE_DATA_LIST
import com.duartefigueiredo.pandorafmsagent.ModuleConst.MODULE_DATA_LIST_VALUE
import com.duartefigueiredo.pandorafmsagent.ModuleConst.MODULE_DESCRIPTION
import com.duartefigueiredo.pandorafmsagent.ModuleConst.MODULE_GROUP
import com.duartefigueiredo.pandorafmsagent.ModuleConst.MODULE_MAX
import com.duartefigueiredo.pandorafmsagent.ModuleConst.MODULE_MAX_CRITICAL
import com.duartefigueiredo.pandorafmsagent.ModuleConst.MODULE_MAX_WARNING
import com.duartefigueiredo.pandorafmsagent.ModuleConst.MODULE_MIN
import com.duartefigueiredo.pandorafmsagent.ModuleConst.MODULE_MIN_CRITICAL
import com.duartefigueiredo.pandorafmsagent.ModuleConst.MODULE_MIN_WARNING
import com.duartefigueiredo.pandorafmsagent.ModuleConst.MODULE_NAME
import com.duartefigueiredo.pandorafmsagent.ModuleConst.MODULE_STATUS
import com.duartefigueiredo.pandorafmsagent.ModuleConst.MODULE_TAG
import com.duartefigueiredo.pandorafmsagent.ModuleConst.MODULE_TYPE
import com.duartefigueiredo.pandorafmsagent.ModuleConst.MODULE_UNIT
import com.duartefigueiredo.pandorafmsagent.ModuleConst.MODULE_WARNING_INVERSE
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.PrintOptions
import org.redundent.kotlin.xml.xml

private const val EMPTY_STRING = ""

data class Module(
    val name: String,
    val description: String,
    val moduleType: ModuleType,
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


) {
    private val printOptions = PrintOptions(
        pretty = false,
        singleLineTextElements = true
    )

    public fun toXml() =
        xml(MODULE) {

            this.simpleAddWithCData(MODULE_NAME, name)

            this.simpleAddWithCData(MODULE_DESCRIPTION, description)

            this.simpleAddWithCData(MODULE_TYPE, moduleType)

            this.addDataList(data)

            this.addIfNotBlank(MODULE_GROUP, moduleGroups)

            this.addIfNotBlank(MODULE_TAG, moduleTags)

            if (moduleState != ModuleState.NOT_SET) {
                simpleAddWithCData(MODULE_STATUS, moduleState)
            }

            this.addIfNotBlank(MODULE_UNIT, moduleUnit)

            this.addIfNotBlank(MODULE_MIN, min)
            this.addIfNotBlank(MODULE_MAX, max)

            this.addIfNotBlank(MODULE_MIN_WARNING, minWarning)
            this.addIfNotBlank(MODULE_MAX_WARNING, maxWarning)

            this.addIfNotBlank(MODULE_MIN_CRITICAL, minCritical)
            this.addIfNotBlank(MODULE_MAX_CRITICAL, maxCritical)

            if (warningInverted) {
                this.simpleAddWithCData(MODULE_WARNING_INVERSE, ONE, false)
            }

            if (criticalInverted) {
                this.simpleAddWithCData(MODULE_CRITICAL_INVERSE, ONE, false)

            }

        }.toString(printOptions = printOptions)

    companion object {
        private const val ONE = "1"

        fun Node.simpleAddWithCData(name: String, value: Any, sanitized: Boolean = true) {
            this.addNode(xml(name) {
                if (sanitized) cdata(value.toString())
                else -value.toString()
            })
        }

        fun Node.addIfNotBlank(name: String, value: String, sanitized: Boolean = true) {
            if (value.isNotBlank()) simpleAddWithCData(name, value, sanitized)
        }

        fun Node.addDataList(dataList: List<String>) {
            dataList.filter { it.isNotBlank() }.let {
                when {
                    it.isEmpty() -> return@let
                    it.size == 1 -> this.simpleAddWithCData(MODULE_DATA, it.first())
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
