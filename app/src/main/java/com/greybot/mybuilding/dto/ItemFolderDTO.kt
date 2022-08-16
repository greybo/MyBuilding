package com.greybot.mybuilding.dto

class ItemFolderDTO(
    val path: String,
    var name: String,
) {
    val files: List<FileDto> = mutableListOf()
}