package cl.trashout.ev2_phonetruck

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import cl.trashout.ev2_phonetruck.model.LoginUIState
import cl.trashout.ev2_phonetruck.model.data.network.LoginResponse
import cl.trashout.ev2_phonetruck.model.data.repository.UserRepository
import cl.trashout.ev2_phonetruck.viewModel.LoginViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    private lateinit var dispatcher: TestDispatcher
    private lateinit var repository: UserRepository
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        dispatcher = StandardTestDispatcher()
        repository = mockk()
        viewModel = LoginViewModel(repository)
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `login exitoso actualiza userId y no setea error`() = runTest {
        // GIVEN
        val loginResp = LoginResponse("LOGIN_OK", 44L, "paola")
        coEvery { repository.login("paola", "123") } returns loginResp

        // WHEN
        viewModel.onUsernameChange("paola")
        viewModel.onPasswordChange("123")
        viewModel.iniciarSesion {}

        dispatcher.scheduler.advanceUntilIdle()

        // THEN
        val estado = viewModel.estado.value

        assertEquals(44L, estado.userId)
        assertNull(estado.error)
        assertEquals("paola", estado.username)
    }

    @Test
    fun `login fallido setea mensaje de error`() = runTest {
        // GIVEN
        coEvery { repository.login("paola", "mala") } returns
                LoginResponse("ERROR", null, null)

        // WHEN
        viewModel.onUsernameChange("paola")
        viewModel.onPasswordChange("mala")
        viewModel.iniciarSesion {}

        dispatcher.scheduler.advanceUntilIdle()

        // THEN
        val estado = viewModel.estado.value

        assertEquals("Usuario o contraseña incorrectos", estado.error)
        assertEquals(0, estado.userId)
    }
}
