package com.duartefigueiredo.pandorafmsagent

import org.redundent.kotlin.xml.Node

abstract class NodeCompatible {

    public val node: Node
        get() = this.toNode()

    protected abstract fun toNode(): Node
}