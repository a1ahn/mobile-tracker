package io.yena.mobiletracker

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import io.yena.mobiletracker.db.Block

class BlockViewModel(application: Application): AndroidViewModel(application) {

    private val repo = BlockRepo(application)
    private val currentBlocks = repo.getCurrentBlocks()
    private val savedBlocks = repo.getSavedBlocks()

    fun getCurrentBlocks(): LiveData<List<Block>> {
        return this.currentBlocks
    }

    fun getSavedBlocks(): LiveData<List<Block>> {
        return this.savedBlocks
    }

    fun saveBlocksInPosition(positions: List<Int>) {
        repo.saveBlocksInPosition(positions)
    }

    fun getBlocksFromApi(startHash: String) {
       repo.getBlocksFromApi(startHash)
    }
}