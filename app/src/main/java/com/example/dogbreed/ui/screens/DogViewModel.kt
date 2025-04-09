package com.example.dogbreed.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dogbreed.DogBreedApplication
import com.example.dogbreed.domain.model.DogBreedDetails
import com.example.dogbreed.domain.model.DogBreedsList
import com.example.dogbreed.domain.usecase.GetDogBreedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DogViewModel(private val getDogBreedUseCase: GetDogBreedUseCase) : ViewModel() {

    /*** List***/

    // Estado para List
    private val _breedsListState = MutableStateFlow<DogUiState>(DogUiState.Loading)
    val breedsListState: StateFlow<DogUiState> = _breedsListState.asStateFlow()

    /*result.fold(...): Se llama a la función fold() sobre el objeto result, que es el Result
     devuelto por getDogBreedsUseCase().
onSuccess = { DogUiState.Success(it) }:
Esta es la lambda que se ejecutará si result es un éxito.
it representa la lista de razas de perros (List<DogBreed>) que contiene el Result exitoso.
DogUiState.Success(it): Se crea un nuevo estado de la UI (DogUiState.Success) que contiene la lista
de razas (it). Este estado indica que la operación fue exitosa y que hay datos disponibles.

En resumen:
result.fold() en tu código hace lo siguiente:
Evalúa el Result: Determina si el Result es un éxito o un fallo.
Ejecuta la lambda correspondiente: Ejecuta la lambda onSuccess si es un éxito o la lambda onFailure
 si es un fallo.
Transforma el resultado: Transforma el resultado (la lista de razas o la excepción) en un nuevo
estado de la UI (DogUiState.Success o DogUiState.Error).
Actualiza el estado de la UI: Asigna el nuevo estado de la UI a uiState.value, lo que provoca la
actualización de la interfaz de usuario.
*/
    fun fetchBreedList() {
        viewModelScope.launch {
            try {
                _breedsListState.value = DogUiState.Loading
                val result = getDogBreedUseCase.invokeBreedsList()
                _breedsListState.value = result.fold(
                    onSuccess = { DogUiState.Success(breedsList = it) },
                    onFailure = { DogUiState.Error }
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    /*** Details***/

    // Estado para Details
    private val _breedDetailsState = MutableStateFlow<DogUiState>(DogUiState.Loading)
    val breedDetailsState: StateFlow<DogUiState> = _breedDetailsState.asStateFlow()

    fun fetchBreedDetails(referenceImageId: String) {
        //Guarda la referencia
        lastReferenceImageId = referenceImageId
        viewModelScope.launch {
            try {
                _breedDetailsState.value = DogUiState.Loading
                val result = getDogBreedUseCase.invokeBreedDetails(referenceImageId)
                _breedDetailsState.value = result.fold(
                    onSuccess = { DogUiState.Success(breedDetails = it) },
                    onFailure = { DogUiState.Error }
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private var lastReferenceImageId: String? = null

    //En caso de error al estar en detalles, se llama
    fun retryToFetchBreedDetails() {
        lastReferenceImageId?.let { fetchBreedDetails(it) }
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DogBreedApplication)
                DogViewModel(
                    getDogBreedUseCase = GetDogBreedUseCase(application.container.dogBreedRepository)
                )
            }
        }
    }
}

sealed interface DogUiState {
    data class Success(
        val breedsList: List<DogBreedsList> = emptyList(),
        val breedDetails: DogBreedDetails? = null,
    ) : DogUiState

    object Error : DogUiState
    object Loading : DogUiState
}
