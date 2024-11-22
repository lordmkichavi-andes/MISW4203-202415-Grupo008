package co.edu.uniandes.vinilos.model.repository

import co.edu.uniandes.vinilos.model.models.Collector
import co.edu.uniandes.vinilos.model.providers.CollectorProvider


class CollectorRepository(private val collectorProvider: CollectorProvider) {
    suspend fun getCollectors(page: Int, pageSize: Int = 20): List<Collector> {
        return collectorProvider.getCollectors(page, pageSize)
    }
}