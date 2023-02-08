package com.greybot.mycosts.data.repository.row

import com.google.firebase.database.DatabaseReference
import com.greybot.mycosts.data.dto.FileRow
import kotlinx.coroutines.CompletableDeferred

sealed class FileActorCommand {
    class Add(val model: FileRow) : FileActorCommand()
    class AddAll(val list: List<FileRow>) : FileActorCommand()
    class Update(val model: FileRow) : FileActorCommand()
    class Remote(val id: String, val myRef: DatabaseReference) : FileActorCommand()
    class GetAll(val response: CompletableDeferred<List<FileRow>?> = CompletableDeferred()) :
        FileActorCommand()
}