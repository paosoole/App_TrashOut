package cl.trashout.ev2_phonetruck

import cl.trashout.ev2_phonetruck.model.data.DAO.FormRegistroDao
import cl.trashout.ev2_phonetruck.model.data.network.*
import cl.trashout.ev2_phonetruck.model.data.repository.UserRepository
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class UserRepositoryTest {

    private lateinit var api: ApiService
    private lateinit var repo: UserRepository
    private val dao = mockk<FormRegistroDao>(relaxed = true)

    @Before
    fun setup() {
        api = mockk()
        repo = UserRepository(dao, api)
    }

    @Test
    fun `login exitoso devuelve LoginResponse`() = runTest {
        // GIVEN
        val loginResp = LoginResponse(
            message = "LOGIN_OK",
            userId = 10L,
            username = "paola"
        )

        coEvery { api.login(any()) } returns Response.success(loginResp)

        // WHEN
        val result = repo.login("paola", "123")

        // THEN
        assertNotNull(result)
        assertEquals("LOGIN_OK", result?.message)
        assertEquals(10L, result?.userId)
        assertEquals("paola", result?.username)
    }
}
