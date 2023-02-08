package com.greybot.mycosts.data.repository.row

import com.greybot.mycosts.data.dto.FileRow
import kotlinx.coroutines.CompletableDeferred

sealed class FileActorCommand {
    class Add(val model: FileRow) : FileActorCommand()
    class AddAll(val list: List<FileRow>) : FileActorCommand()
    class Update(val model: FileRow) : FileActorCommand()
    class Remote(val model: FileRow) : FileActorCommand()
    class GetAll(val response: CompletableDeferred<List<FileRow>?> = CompletableDeferred()) :
        FileActorCommand()
}