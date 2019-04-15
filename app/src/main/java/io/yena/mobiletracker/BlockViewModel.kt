package io.yena.mobiletracker

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import io.yena.mobiletracker.db.Block
import io.yena.mobiletracker.models.TransactionData

class BlockViewModel(application: Application): AndroidViewModel(application) {

    private val repo = BlockRepo(application)
    private val currentBlocks = repo.getCurrentBlocks()
//    private val savedBlocks = repo.getSavedBlocks()
    private val toastMessage = repo.getToastMsg()

    fun getToastMsg(): LiveData<String> {
        return this.toastMessage
    }

    fun clearToastMsg() {
        repo.clearToastMsg()
    }

    fun getCurrentBlocks(): LiveData<List<Block>> {
        return this.currentBlocks
    }

//    fun getSavedBlocks(): LiveData<List<Block>> {
//        return this.savedBlocks
//    }

    fun saveBlocksInPosition(positions: List<Int>, saveComplete: () -> Unit) {
        repo.saveBlocksInPosition(positions) {
            saveComplete()
        }
    }

    fun getBlocksFromApi(startHash: String) {
       repo.getBlocksFromApi(startHash)
    }
}