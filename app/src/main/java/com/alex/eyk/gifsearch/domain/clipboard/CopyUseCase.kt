package com.alex.eyk.gifsearch.domain.clipboard

interface CopyUseCase {

    fun copy(label: String, data: String)
}
