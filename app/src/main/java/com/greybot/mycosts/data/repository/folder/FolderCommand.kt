package com.greybot.mycosts.data.repository.folder

import com.greybot.mycosts.data.dto.FolderRow
import kotlinx.coroutines.CompletableDeferred

sealed class FolderCommand {
    class Add(val model: FolderRow) : FolderCommand()
    class AddAll(val list: List<FolderRow>) : FolderCommand()
    class Update(val model: FolderRow) : FolderCommand()
    class Remote(val model: FolderRow) : FolderCommand()
    class Get(val response: CompletableDeferred<List<FolderRow>> = CompletableDeferred()) :
        FolderCommand()
}