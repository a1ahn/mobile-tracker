package io.yena.mobiletracker

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import io.yena.mobiletracker.db.Block

class BlockViewModel(application: Application): AndroidViewModel(application) {

    private val repo = BlockRepo(application)
    private val blocks = repo.getAllBlocks()

    fun getAllBlocks(): LiveData<List<Block>> {
        return this.blocks
    }

    fun loadBlocks(startHash: String) {
        repo.loadBlocks(startHash)
    }
}