package com.example.dogbreed.ui.screens

import com.example.dogbreed.domain.model.DogBreedDetails
import com.example.dogbreed.domain.model.DogBreedsList
import com.example.dogbreed.domain.usecase.GetDogBreedUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DogViewModelTest {

    // Control de corrutinas
    private val testDispatcher = StandardTestDispatcher()

    // Mock
    private val getDogBreedUseCase = mockk<GetDogBreedUseCase>()

    // ViewModel
    private lateinit var viewModel: DogViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher) // Configurar Dispatcher de pruebas
        viewModel = DogViewModel(getDogBreedUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Resetear Dispatcher después de cada prueba
    }

    @Test
    fun `fetchBreedList should emit Loading and then Success when API call is successful`() =
        runTest {
            // Datos simulados de la API
            val fakeBreedsList = listOf(
                DogBreedsList(
                    "1",
                    "Bulldog",
                    "https://cdn2.thedogapi.com/images/sGQvQUpsp.jpg"
                ), DogBreedsList(
                    "2",
                    "Labrador",
                    "https://cdn2.thedogapi.com/images/BJa4kxc4X.jpg"
                )
            )

            // Mock del caso de uso: simula una respuesta exitosa
            coEvery { getDogBreedUseCase.invokeBreedsList() } returns Result.success(fakeBreedsList)

            // Ejecutar la función bajo prueba
            viewModel.fetchBreedList()

            // Avanzar corrutinas para ejecutar la llamada
            advanceUntilIdle()

            // Verificar los estados emitidos
            assertEquals(DogUiState.Success(fakeBreedsList), viewModel.breedsListState.first())
        }

    @Test
    fun `fetchBreedList should emit Loading and then Error when API call fails`() = runTest {
        // Mock del caso de uso: simula una excepción
        coEvery { getDogBreedUseCase.invokeBreedsList() } returns Result.failure(Exception("Network error"))

        // Ejecutar la función bajo prueba
        viewModel.fetchBreedList()

        // Avanzar corrutinas
        advanceUntilIdle()

        // Verificar que el estado final es Error
        assertEquals(DogUiState.Error, viewModel.breedsListState.first())
    }

    @Test
    fun `fetchBreedDetails should emit Loading and then Success when API call is successful`() =
        runTest {
            val fakeDetails = DogBreedDetails(
                "https://cdn2.thedogapi.com/images/BFRYBufpm.jpg",
                name = "Akita",
                origin = "London",
                breedGroup = "Working",
                bredFor = "Hunting",
                lifeSpan = "10 - 14 years",
                temperament = "Docile"
            )

            // Simular respuesta exitosa en el caso de uso
            coEvery { getDogBreedUseCase.invokeBreedDetails("image123") } returns Result.success(
                fakeDetails
            )

            // Ejecutar la función
            viewModel.fetchBreedDetails("image123")

            // Avanzar corrutinas
            advanceUntilIdle()

            // Verificar resultado
            assertEquals(
                DogUiState.Success(breedDetails = fakeDetails),
                viewModel.breedDetailsState.first()
            )
        }

    @Test
    fun `fetchBreedDetails should emit Loading and then Error when API call fails`() = runTest {

        coEvery {
            getDogBreedUseCase.invokeBreedDetails("https://cdn2.thedogapi.com/images/sGQvQUpsp.jpg")
        } returns Result.failure(Exception("Network error"))

        viewModel.fetchBreedDetails("https://cdn2.thedogapi.com/images/sGQvQUpsp.jpg")

        advanceUntilIdle()

        assertEquals(DogUiState.Error, viewModel.breedDetailsState.first())

    }

}