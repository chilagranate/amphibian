package com.example.amphibians.data

import com.example.amphibians.model.Amphibian
import com.example.amphibians.network.AmphibianApiService

interface AmphibianInfoRepository {
    suspend fun getAmphibians(): List<Amphibian>
}

class NetworkAmphibianInfoRepository(
    private val amphibianApiService: AmphibianApiService
) : AmphibianInfoRepository {
    override suspend fun getAmphibians(): List<Amphibian> = amphibianApiService.getAmphibians()

}