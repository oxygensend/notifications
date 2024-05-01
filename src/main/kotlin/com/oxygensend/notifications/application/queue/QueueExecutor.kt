package com.oxygensend.notifications.application.queue

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.Executors

@Component
class QueueExecutor {
    private val tasks: ConcurrentMap<String, Queue<() -> Unit>> = ConcurrentHashMap()
    private val executor: ExecutorCoroutineDispatcher =
        Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("queue-", 0).factory()).asCoroutineDispatcher()


    fun execute(key: String?, runnable: () -> Unit) {
        if (key == null) {
            CoroutineScope(executor).launch {
                runnable()
            }
            return
        }

        tasks.computeIfAbsent(key) { ConcurrentLinkedQueue() }.apply {
            add(runnable)
            if (size == 1) {
                CoroutineScope(executor).launch {
                    queueExecution(key)
                }
            }
        }

    }

    private suspend fun queueExecution(key: String) {
        tasks[key]?.apply {
            while (isNotEmpty()) {
                poll()?.invoke()
            }
            tasks.remove(key)
        }
    }

}