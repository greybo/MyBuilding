package com.greybot.mycosts.data.repository.file

import com.google.firebase.database.DatabaseReference
import com.greybot.mycosts.data.dto.FileRow
import com.greybot.mycosts.utility.LogApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class FileActor(private val coroutineContext: CoroutineContext = EmptyCoroutineContext) {

    private val scope = CoroutineScope(coroutineContext)
    private val backupList = mutableListOf<FileRow>()

    @OptIn(ObsoleteCoroutinesApi::class)
    private val coroutineCommand = scope.actor<FileCommand>(capacity = Channel.BUFFERED) {
        for (command in this) {
            when (command) {
                is FileCommand.Add -> {
                    backupList.add(command.model)
                    LogApp.i("FileActor&Add - size: ${backupList.size}, $backupList")
                }
                is FileCommand.AddAll -> {
                    backupList.clear()
                    backupList.addAll(command.list)
                }
                is FileCommand.Update -> {
                    backupList.forEachIndexed { index, item ->
                        if (command.model.objectId == item.objectId) {
                            backupList[index] = command.model
                        }
                    }
                }
                is FileCommand.Remote -> {
                    command.myRef.child(command.id).removeValue()
                    backupList.remove(backupList.find { it.objectId == command.id })
                }
                is FileCommand.GetAll -> {
                    val list = backupList.ifEmpty { null }
                    command.response.complete(list)
                    LogApp.i("FileActor&GetAll - size: ${backupList.size}, $backupList")
                }
            }
        }
    }

    suspend fun add(model: FileRow): List<FileRow> {
        coroutineCommand.send(FileCommand.Add(model))
        return getAll() ?: emptyList()
    }

    fun addAll(list: List<FileRow>) {
        coroutineCommand.trySend(FileCommand.AddAll(list))
    }

    suspend fun remote(id: String, myRef: DatabaseReference) {
        coroutineCommand.send(FileCommand.Remote(id, myRef))
    }

    suspend fun getAll(): List<FileRow>? {
        val getCounter = FileCommand.GetAll()
        coroutineCommand.send(getCounter)
        return getCounter.response.await()
    }

    suspend fun update(model: FileRow): List<FileRow> {
        coroutineCommand.send(FileCommand.Update(model))
        return getAll() ?: emptyList()
    }
}