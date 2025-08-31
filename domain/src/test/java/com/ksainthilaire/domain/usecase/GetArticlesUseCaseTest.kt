package com.ksainthilaire.domain.usecase

import com.ksainthilaire.domain.model.Article
import com.ksainthilaire.domain.model.enum.Language
import com.ksainthilaire.domain.model.enum.Sort
import com.ksainthilaire.domain.repository.NewsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

@OptIn(ExperimentalCoroutinesApi::class)
class GetArticlesUseCaseTest {

    private lateinit var repository: NewsRepository
    private lateinit var useCase: GetArticlesUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk<NewsRepository>()
        useCase = GetArticlesUseCase(repository)
    }

    private val params = GetArticlesUseCase.Params(
        query = "cinéma",
        searchIn = "title,description",
        sources = "le-monde,le-parisien",
        domains = "lemonde.fr",
        excludeDomains = "example.com",
        from = "2025-08-01",
        to = "2025-08-27",
        language = Language.FR,
        sortBy = Sort.Popularity,
        pageSize = 20,
        page = 2
    )

    @Test
    fun `delegates all params to repository and returns list`() = runTest {
        val articles: List<Article> = listOf(mockk(), mockk())

        coEvery { repository.getArticles(params) } returns flowOf(articles)

        val result = useCase(params).first()

        assertSame(articles, result)
        coVerify(exactly = 1) { repository.getArticles(params) }
    }

    @Test
    fun `calls repository with defaults when no params provided`() = runTest {
        val expected = emptyList<Article>()
        val captured = slot<GetArticlesUseCase.Params>()

        coEvery { repository.getArticles(capture(captured)) } returns flowOf(expected)

        val result = useCase(GetArticlesUseCase.Params()).first()

        assertEquals(expected, result)

        val defaultParams = GetArticlesUseCase.Params()
        assertEquals(defaultParams, captured.captured)
        coVerify(exactly = 1) { repository.getArticles(any()) }
    }

    @Test
    fun `propagates exception from repository`() = runTest {
        coEvery { repository.getArticles(any()) } throws IllegalStateException("test erreur keny")

        try {
            useCase(GetArticlesUseCase.Params(query = "breaking")).first()
            assertTrue(false, "Expected IllegalStateException to be thrown")
        } catch (ex: IllegalStateException) {
            assertEquals("test erreur keny", ex.message)
        }
    }

    @Test
    fun `passes through empty csv strings as provided (no normalization)`() = runTest {

        val p = GetArticlesUseCase.Params(
            query = "q",
            searchIn = null,
            sources = "",
            domains = "",
            excludeDomains = "",
            from = null,
            to = null,
            language = null,
            sortBy = null,
            pageSize = 0,
            page = 0
        )

        val expected: List<Article> = emptyList()
        coEvery { repository.getArticles(p) } returns flowOf(expected)

        val result = useCase(p).first()

        assertEquals(expected, result)
        coVerify(exactly = 1) { repository.getArticles(p) }
    }
}
