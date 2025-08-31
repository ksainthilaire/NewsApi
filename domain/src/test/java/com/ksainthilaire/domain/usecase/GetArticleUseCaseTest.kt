package com.ksainthilaire.domain.usecase

import com.ksainthilaire.domain.model.Article
import com.ksainthilaire.domain.repository.NewsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetArticleByIdUseCaseTest {

    private lateinit var repository: NewsRepository
    private lateinit var useCase: GetArticleByIdUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = GetArticleByIdUseCase(repository)
    }

    @Test
    fun `delegates to repository with articleId and returns article`() = runTest {
        val articleId = 123
        val article: Article = mockk()

        coEvery { repository.getArticleFromDatabase(articleId) } returns article

        val result = useCase(articleId)

        assertSame(article, result)
        coVerify(exactly = 1) { repository.getArticleFromDatabase(articleId) }
    }

    @Test
    fun `propagates exception from repository`() = runTest {
        val articleId = 456
        coEvery { repository.getArticleFromDatabase(articleId) } throws IllegalStateException("test erreur keny")

        val ex = assertThrows(IllegalStateException::class.java) {
            runTest { useCase(articleId) }
        }
        assertEquals("test erreur keny", ex.message)
        coVerify(exactly = 1) { repository.getArticleFromDatabase(articleId) }
    }

    @Test
    fun `does not perform extra repository calls`() = runTest {
        val articleId = 789
        val article: Article = mockk()
        coEvery { repository.getArticleFromDatabase(articleId) } returns article

        useCase(articleId)

        coVerify(exactly = 1) { repository.getArticleFromDatabase(articleId) }
    }
}
