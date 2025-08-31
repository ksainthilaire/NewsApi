package com.ksainthilaire.data.remote.dto

data class NewsApiResponseDto(
    val totalResults: Int,
    var articles: List<ArticleDto>
) : ApiResponseDto()