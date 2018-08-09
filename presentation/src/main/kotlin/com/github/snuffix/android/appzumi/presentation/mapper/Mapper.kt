package com.github.snuffix.android.appzumi.presentation.mapper

interface Mapper<PresentationModel, DomainModel> {
    fun mapToModel(domainModel: DomainModel): PresentationModel
}