package com.devjamesp.musicstreaming.service.model

data class PlayerModel(
    private val playMusicModelList: List<MusicModel> = emptyList(),
    var currentPosition: Int = -1,
    var isWatchingPlayListView: Boolean = false
) {
    fun getAdapterModels() : List<MusicModel> {
        return playMusicModelList.mapIndexed { index, musicModel ->
            val newItem = musicModel.copy(isPlaying = index == currentPosition)
            newItem
        }
    }

    fun updateCurrentPosition(musicModel: MusicModel) {
        currentPosition = playMusicModelList.indexOf(musicModel)
    }

    fun getNextMusic(): MusicModel? {
        if (playMusicModelList.isEmpty()) return null

        currentPosition = if ((currentPosition+1) >= playMusicModelList.size) 0 else currentPosition + 1
        return playMusicModelList[currentPosition]
    }

    fun getPreviousMusic(): MusicModel? {
        if (playMusicModelList.isEmpty()) return null

        currentPosition = if ((currentPosition-1) <= -1) playMusicModelList.lastIndex else currentPosition - 1
        return playMusicModelList[currentPosition]
    }

    fun currentMusicModel(): MusicModel? {
        if (playMusicModelList.isEmpty()) return null

        return playMusicModelList[currentPosition]
    }

}