package com.melfouly.domain.usecase

import com.melfouly.domain.repository.Repository

class TransitionsUseCase(private val repositoryImpl: Repository) {
    fun getAllTransitions() = repositoryImpl.getAllTransitions()
}