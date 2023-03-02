package com.greybot.mycosts.present.folder.preview

import com.greybot.mycosts.utility.LogApp
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class DeleteListActor(coroutineContext: CoroutineContext = EmptyCoroutineContext) {

    private val scope = CoroutineScope(coroutineContext)
    private val backupList = mutableListOf<String>()

    @OptIn(ObsoleteCoroutinesApi::class)
    private val coroutineCommand = scope.actor<Any>(capacity = Channel.BUFFERED) {
        for (command in this) {
            when (command) {
                is DeleteListCommand.Add -> {
                    backupList.add(command.id)
                    LogApp.i("FileActor&Add - size: ${backupList.size}, $backupList")
                }
                is DeleteListCommand.Clear -> {
                    backupList.clear()
                    LogApp.i("FileActor&Add - size: ${backupList.size}, $backupList")
                }
                is DeleteListCommand.Contains -> {
                    command.response.complete(backupList.contains(command.id))
                    LogApp.i("FileActor&Add - size: ${backupList.size}, $backupList")
                }

                is DeleteListCommand.Remote -> {
                    backupList.remove(backupList.find { it == command.id })
                }
                is DeleteListCommand.GetAll -> {
                    val list = backupList.ifEmpty { null }
                    command.response.complete(list)
                    LogApp.i("FileActor&GetAll - size: ${backupList.size}, $backupList")
                }
            }
        }
    }

    suspend fun add(id: String): List<String> {
        coroutineCommand.send(DeleteListCommand.Add(id))
        return getAll() ?: emptyList()
    }

    suspend fun remove(id: String) {
        coroutineCommand.send(DeleteListCommand.Remote(id))
    }

    suspend fun clear() {
        coroutineCommand.send(DeleteListCommand.Clear)
    }

    suspend fun getAll(): List<String>? {
        val getCounter = DeleteListCommand.GetAll()
        coroutineCommand.send(getCounter)
        return getCounter.response.await()
    }

    suspend fun contains(objectId: String): Boolean {
        val contains = DeleteListCommand.Contains(objectId)
        coroutineCommand.send(contains)
        return contains.response.await()
    }

    suspend fun isEmpty(): Boolean {
        return getAll()?.isEmpty() ?: true
    }
}


sealed class DeleteListCommand {
    class Add(val id: String) : DeleteListCommand()
    class Remote(val id: String) : DeleteListCommand()
    class Contains(
        val id: String,
        val response: CompletableDeferred<Boolean> = CompletableDeferred()
    ) : DeleteListCommand()

    object Clear : DeleteListCommand()
    class GetAll(val response: CompletableDeferred<List<String>?> = CompletableDeferred()) :
        DeleteListCommand()
}