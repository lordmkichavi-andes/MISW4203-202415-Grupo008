package co.edu.uniandes.vinilos.model.providers

import co.edu.uniandes.vinilos.model.models.Collector

interface CollectorProvider {
    suspend fun getCollectors(page: Int, pageSize: Int): List<Collector>
}