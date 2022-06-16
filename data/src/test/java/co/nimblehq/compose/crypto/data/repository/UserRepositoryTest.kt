package co.nimblehq.compose.crypto.data.repository

import co.nimblehq.compose.crypto.data.response.UserResponse
import co.nimblehq.compose.crypto.data.response.toUsers
import co.nimblehq.compose.crypto.data.service.ApiService
import co.nimblehq.compose.crypto.domain.repository.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryTest {

    private lateinit var mockService: ApiService
    private lateinit var repository: UserRepository

    private val userResponse = UserResponse(
        id = 1,
        name = "name",
        username = "username",
        email = "email",
        addressResponse = null,
        phone = null,
        website = null
    )

    @Before
    fun setup() {
        mockService = mockk()
        repository = UserRepositoryImpl(mockService)
    }

    @Test
    fun `When calling getUsers request successfully, it returns success response`() = runBlockingTest {
        coEvery { mockService.getUsers() } returns listOf(userResponse)

        repository.getUsers() shouldBe listOf(userResponse).toUsers()
    }

    @Test
    fun `When calling getUsers request failed, it returns wrapped error`() = runBlockingTest {
        coEvery { mockService.getUsers() } throws Throwable()

        shouldThrow<Throwable> {
            repository.getUsers()
        }
    }
}
