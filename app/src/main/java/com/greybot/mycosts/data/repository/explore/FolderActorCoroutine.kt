package com.greybot.mycosts.data.repository.explore

import com.greybot.mycosts.data.dto.ExploreRow
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class FolderActorCoroutine(coroutineContext: CoroutineContext = EmptyCoroutineContext) {

    private val backupList = mutableListOf<ExploreRow>()
    private val scope = CoroutineScope(coroutineContext)

    private val coroutineCommand = scope.actor<ExploreCommand>(capacity = Channel.BUFFERED) {
        for (command in this) {
            when (command) {
                is ExploreCommand.Add -> backupList.add(command.model)
                is ExploreCommand.AddAll -> {
                    backupList.clear()
                    backupList.addAll(command.list)
                }
                is ExploreCommand.Update -> {
                    backupList.forEachIndexed { index, item ->
                        if (command.model.objectId == item.objectId) {
                            backupList[index] = command.model
                        }
                    }
                }
                is ExploreCommand.Remote -> backupList.remove(command.model)
                is ExploreCommand.Get -> command.response.complete(backupList)
            }
        }
    }

    suspend fun add(model: ExploreRow): List<ExploreRow> {
        coroutineCommand.send(ExploreCommand.Add(model))
        return getAll()
    }

    fun addAll(list: List<ExploreRow>) {
        coroutineCommand.trySend(ExploreCommand.AddAll(list))
    }

    suspend fun remote(model: ExploreRow) {
        coroutineCommand.send(ExploreCommand.Remote(model))
    }

    suspend fun getAll(): List<ExploreRow> {
        val getCounter = ExploreCommand.Get()
        coroutineCommand.send(getCounter)
        return getCounter.response.await()
    }

    suspend fun update(model: ExploreRow): List<ExploreRow> {
        coroutineCommand.send(ExploreCommand.Update(model))
        return getAll()
    }
}


sealed class ExploreCommand {
    class Add(val model: ExploreRow) : ExploreCommand()
    class AddAll(val list: List<ExploreRow>) : ExploreCommand()
    class Update(val model: ExploreRow) : ExploreCommand()
    class Remote(val model: ExploreRow) : ExploreCommand()
    class Get(val response: CompletableDeferred<List<ExploreRow>> = CompletableDeferred()) :
        ExploreCommand()
}