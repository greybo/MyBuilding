package com.greybot.mycosts.data.repository.folder

import com.greybot.mycosts.data.dto.FolderRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class FolderActor(coroutineContext: CoroutineContext = EmptyCoroutineContext) {

    private val backupList = mutableListOf<FolderRow>()
    private val scope = CoroutineScope(coroutineContext)

    @OptIn(ObsoleteCoroutinesApi::class)
    private val coroutineCommand = scope.actor<FolderCommand>(capacity = Channel.BUFFERED) {
        for (command in this) {
            when (command) {
                is FolderCommand.Add -> backupList.add(command.model)
                is FolderCommand.AddAll -> {
                    backupList.clear()
                    backupList.addAll(command.list)
                }
                is FolderCommand.Update -> {
                    backupList.forEachIndexed { index, item ->
                        if (command.model.objectId == item.objectId) {
                            backupList[index] = command.model
                        }
                    }
                }
                is FolderCommand.Remote -> {
                    backupList.find { it.objectId == command.id }?.let {
                        backupList.remove(it)
                    }
                }
                is FolderCommand.GetAll -> command.response.complete(backupList)
            }
        }
    }

    suspend fun add(model: FolderRow): List<FolderRow> {
        coroutineCommand.send(FolderCommand.Add(model))
        return getAll()
    }

    fun addAll(list: List<FolderRow>) {
        coroutineCommand.trySend(FolderCommand.AddAll(list))
    }

    suspend fun remote(objectId: String) {
        coroutineCommand.send(FolderCommand.Remote(objectId))
    }

    suspend fun getAll(): List<FolderRow> {
        val getCounter = FolderCommand.GetAll()
        coroutineCommand.send(getCounter)
        return getCounter.response.await()
    }

    suspend fun update(model: FolderRow): List<FolderRow> {
        coroutineCommand.send(FolderCommand.Update(model))
        return getAll()
    }
}


