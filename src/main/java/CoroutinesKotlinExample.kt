import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class KotlinCoroutinesJavaExample {
    suspend fun dbCall1(): String {
        delay(1000)
        return "test1"
    }

    suspend fun dbCall2(): String {
        delay(2000)
        return "test2"
    }

    suspend fun dbCall3(): String {
        delay(1000)
        return "test3"
    }


    fun restCall(): Channel<String> {
        val channel = Channel<String>()

        GlobalScope.launch {
            val task1 = launch {
                channel.send(dbCall1())
            }
            val task2 = launch {
                channel.send(dbCall2())
            }

            task1.join()
            task2.join()

            channel.send(dbCall3())
            channel.close()
        }

        return channel
    }
}

fun main(args: Array<String>) = runBlocking {
    KotlinCoroutinesJavaExample()
        .restCall()
        .consumeEach { println(it) }
}
