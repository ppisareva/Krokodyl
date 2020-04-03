package com.example.krokodyl.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Word(var word: String, var gussedStatus : Boolean ) : Parcelable {}


@Parcelize
data class Words ( var wordsList : MutableList<Word>, var remainedListOfIndexes: MutableList<Int>, var listOfTeam:MutableList<Team>, var currentTeamPlaying : Int):
    Parcelable {}

@Parcelize
data class Team (var teamNumber: Int, var teamScore : Int): Parcelable {}