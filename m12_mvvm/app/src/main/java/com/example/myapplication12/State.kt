package com.example.myapplication12

sealed class State {
    object Idle : State()
    object Loading : State()
    data class Finish(val searchText: String) : State()
}