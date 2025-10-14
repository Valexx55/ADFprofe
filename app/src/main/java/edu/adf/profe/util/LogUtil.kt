package edu.adf.profe.util

object LogUtil {

    fun getLogInfo(): String {
        val stackTrace = Thread.currentThread().stackTrace
        // Saltamos 4 elementos para llegar al punto donde se llamó Logger.d()
        val element = stackTrace.firstOrNull { it.className !in listOf(
            Thread::class.java.name,
            LogUtil::class.java.name,
            "dalvik.system.VMStack"
        ) } ?: return "Unknown"

        return "(${element.fileName}:${element.lineNumber})#${element.methodName}"
    }
}