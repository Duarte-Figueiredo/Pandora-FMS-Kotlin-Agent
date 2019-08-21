package com.duartefigueiredo.pandorafmsagent

enum class ModuleType {
    ASYNC_DATA,
    ASYNC_INC,
    ASYNC_PROC,
    ASYNC_STRING,
    GENERIC_DATA,
    GENERIC_DATA_INC,
    GENERIC_DATA_INC_ABS,
    GENERIC_DATA_STRING,
    GENERIC_PROC,
    IMAGE_JPG,
    IMAGE_PNG,
    KEEP_ALIVE;

    companion object {
        private val lowerCaseValues = ModuleType
            .values()
            .map { Pair(it, it.name.toLowerCase()) }
            .toMap()
    }


    override fun toString(): String {
        return lowerCaseValues.getValue(this)
    }
}
//TODO implement below
//        remote_icmp: Remote ICMP network agent (latency)
//        remote_icmp_proc: Remote ICMP network agent, boolean data
//        remote_snmp: Remote SNMP network agent, numeric data
//        remote_snmp_inc: Remote SNMP network agent, incremental data
//        remote_snmp_proc: Remote SNMP network agent, boolean data
//        remote_snmp_string: Remote SNMP network agent, alphanumeric data
//        remote_tcp: Remote TCP network agent, numeric data
//        remote_tcp_inc: Remote TCP network agent, incremental data
//        remote_tcp_proc: Remote TCP network agent, boolean data
//        remote_tcp_string: Remote TCP network agent, alphanumeric data