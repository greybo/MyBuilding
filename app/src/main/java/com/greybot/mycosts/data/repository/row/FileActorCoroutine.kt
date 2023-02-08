package com.greybot.mycosts.data.repository.row

import com.google.firebase.database.DatabaseReference
import com.greybot.mycosts.data.dto.FileRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class FileActorCoroutine(private val coroutineContext: CoroutineContext = EmptyCoroutineContext) {

    private val scope = CoroutineScope(coroutineContext)
    private val backupList = mutableListOf<FileRow>()

    @OptIn(ObsoleteCoroutinesApi::class)
    private val coroutineCommand = scope.actor<FileActorCommand>(capacity = Channel.BUFFERED) {
        for (command in this) {
            when (command) {
                is FileActorCommand.Add -> backupList.add(command.model)
                is FileActorCommand.AddAll -> {
                    backupList.clear()
                    backupList.addAll(command.list)
                }
                is FileActorCommand.Update -> {
                    backupList.forEachIndexed { index, item ->
                        if (command.model.objectId == item.objectId) {
                            backupList[index] = command.model
                        }
                    }
                }
                is FileActorCommand.Remote -> {
                    command.myRef.child(command.id).removeValue()
                    backupList.remove(backupList.find { it.objectId == command.id })
                }
                is FileActorCommand.GetAll -> {
                    val list = backupList.ifEmpty { null }
                    command.response.complete(list)
                }
            }
        }
    }

    suspend fun add(model: FileRow): List<FileRow> {
        coroutineCommand.send(FileActorCommand.Add(model))
        return getAll() ?: emptyList()
    }

    fun addAll(list: List<FileRow>) {
        coroutineCommand.trySend(FileActorCommand.AddAll(list))
    }

    suspend fun remote(id: String, myRef: DatabaseReference) {
        coroutineCommand.send(FileActorCommand.Remote(id, myRef))
    }

    suspend fun getAll(): List<FileRow>? {
        val getCounter = FileActorCommand.GetAll()
        coroutineCommand.send(getCounter)
        return getCounter.response.await()
    }

    suspend fun update(model: FileRow): List<FileRow> {
        coroutineCommand.send(FileActorCommand.Update(model))
        return getAll() ?: emptyList()
    }
}