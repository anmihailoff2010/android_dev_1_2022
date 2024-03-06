package com.example.myapplication12

import kotlinx.coroutines.delay

class MainRepository {
    suspend fun search(searchText: String): String {
        delay(5000)
        return searchText
    }
}