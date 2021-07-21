package com.devjamesp.musicstreaming.service.model

fun MusicEntity.mapToMusicModel(id: Long): MusicModel =
    MusicModel(
        id = id,
        track = this.track,
        streamUrl = this.streamUrl,
        artist = this.artist,
        coverUrl = this.coverUrl
    )

fun MusicDTO.mapToPlayerModel(): PlayerModel =
    PlayerModel(
        playMusicModelList = musics.mapIndexed { index, musicEntity ->
            musicEntity.mapToMusicModel(index.toLong())
        }
    )