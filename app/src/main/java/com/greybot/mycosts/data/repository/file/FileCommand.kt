package com.greybot.mycosts.data.repository.file

import com.google.firebase.database.DatabaseReference
import com.greybot.mycosts.data.dto.FileRow
import kotlinx.coroutines.CompletableDeferred

sealed class FileCommand {
    class Add(val model: FileRow) : FileCommand()
    class AddAll(val list: List<FileRow>) : FileCommand()
    class Update(val model: FileRow) : FileCommand()
    class Remote(val id: String, val myRef: DatabaseReference) : FileCommand()
    class GetAll(val response: CompletableDeferred<List<FileRow>?> = CompletableDeferred()) :
        FileCommand()
}