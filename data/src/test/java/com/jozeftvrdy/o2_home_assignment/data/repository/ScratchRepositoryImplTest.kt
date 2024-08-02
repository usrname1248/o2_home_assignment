package com.jozeftvrdy.o2_home_assignment.data.repository

import com.jozeftvrdy.networking.api.VersionApi
import com.jozeftvrdy.networking.model.ApiCallResponse
import com.jozeftvrdy.networking.model.VersionApiResponse
import com.jozeftvrdy.o2_home_assignment.data.model.CardStateModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest

open class ScratchRepositoryImplTest : TestCase() {


    @MockK
    lateinit var versionApi: VersionApi

    override fun setUp() {
        super.setUp()

        MockKAnnotations.init(this)
    }

    private val repository by lazy {
        ScratchRepositoryImpl(
            versionApi = versionApi
        )
    }


    fun `test scratchCard throws exception when starting in invalid state`() {
        runTest {
            repository.revealCard()

            val returnedValue = repository.cardStateFlow.value
            // Check if returned state after is UnRegistered state
            assertTrue(returnedValue is CardStateModel.Revealed.Unregistered)

            kotlin.runCatching {
                repository.revealCard()
            }.let {
                // Check if reveal called successful reveal will fail
                assertTrue(it.isFailure)
            }

            coEvery {
                versionApi.getVersion((returnedValue as CardStateModel.Revealed.Unregistered).generatedUUID)
            } returns ApiCallResponse.FailedApiCallResponse.HttpError(500)
            repository.registerCard()

            kotlin.runCatching {
                repository.revealCard()
            }.let {
                // Check if reveal called failed register will fail
                assertTrue(it.isFailure)
            }

            coEvery {
                versionApi.getVersion((returnedValue as CardStateModel.Revealed.Unregistered).generatedUUID)
            } returns ApiCallResponse.SuccessApiCallResponse(VersionApiResponse(ScratchRepositoryImpl.androidValidationValue + 1L))

            repository.registerCard()

            kotlin.runCatching {
                repository.revealCard()
            }.let {
                // Check if reveal called successful register will fail
                assertTrue(it.isFailure)
            }
        }
    }

    fun `test scratchCard`() {

        runTest {
            val collectedCardStates = mutableListOf<CardStateModel>()
            val jobs = launch {
                repository.cardStateFlow.collectLatest {
                    collectedCardStates.add(it)
                }
            }

            repository.revealCard()

            delay(500)

            jobs.cancel()

            // Check if flow received nothing more or less than it needed (loading + newState)
            assertTrue(collectedCardStates.size == 2)

            // Check if flow received loading state
            assertTrue(collectedCardStates.find {
                it is CardStateModel.Unrevealed.Scratching
            } != null)

            // Check if flows last state is Unregistered or ScratchingFailed
            assertTrue(collectedCardStates.last() is CardStateModel.Revealed.Unregistered || collectedCardStates.last() is CardStateModel.Unrevealed.ScratchingFailed)
        }
    }

    fun `test register throws exception when starting in invalid state`() {
        runTest {
            kotlin.runCatching {
                repository.registerCard()
            }.let {
                assertTrue(it.isFailure)
            }
        }
    }

    fun `test register with success response with greater android value`() {
        runTest {
            repository.revealCard()

            delay(500)

            val returnedValue = repository.cardStateFlow.value
            assertTrue(returnedValue is CardStateModel.Revealed.Unregistered)

            coEvery {
                versionApi.getVersion((returnedValue as CardStateModel.Revealed.Unregistered).generatedUUID)
            } returns ApiCallResponse.SuccessApiCallResponse(VersionApiResponse(ScratchRepositoryImpl.androidValidationValue + 1L))

            val collectedCardStates = mutableListOf<CardStateModel>()
            val jobs = launch {
                repository.cardStateFlow.collectLatest {
                    collectedCardStates.add(it)
                }
            }

            delay(100)

            kotlin.runCatching {
                repository.registerCard()
            }.let {
                assertTrue(it.isSuccess)
            }

            delay(100)

            jobs.cancel()

            // Check if flow received nothing more than it needed (loading + newState)
            assertTrue(collectedCardStates.size <= 2)

            // Check if flows last state is Registered
            assertTrue(collectedCardStates.last() is CardStateModel.Revealed.Registered)

        }
    }

    fun `test register with success response with lower android value`() {
        runTest {
            repository.revealCard()

            delay(500)

            val revealCardReturnedValue = repository.cardStateFlow.value
            assertTrue(revealCardReturnedValue is CardStateModel.Revealed.Unregistered)

            coEvery {
                versionApi.getVersion((revealCardReturnedValue as CardStateModel.Revealed.Unregistered).generatedUUID)
            } returns ApiCallResponse.SuccessApiCallResponse(VersionApiResponse(ScratchRepositoryImpl.androidValidationValue - 1L))

            kotlin.runCatching {
                repository.registerCard()
            }.let {
                assertTrue(it.isSuccess)
            }

            val registerCardReturnedValue = repository.cardStateFlow.value

            // Check if flows last state is Unregistered due to validation exception
            assertTrue(registerCardReturnedValue is CardStateModel.Revealed.RegisterFailed)

        }
    }

    fun `test register with failed response`() {
        runTest {
            repository.revealCard()

            delay(500)

            val returnedValue = repository.cardStateFlow.value
            assertTrue(returnedValue is CardStateModel.Revealed.Unregistered)

            coEvery {
                versionApi.getVersion((returnedValue as CardStateModel.Revealed.Unregistered).generatedUUID)
            } returns ApiCallResponse.FailedApiCallResponse.HttpError(500)

            kotlin.runCatching {
                repository.registerCard()
            }.let {
                assertTrue(it.isSuccess)
            }

            val registerCardReturnedValue = repository.cardStateFlow.value

            // Check if flows last state is Unregistered due to Http code 500 response
            assertTrue(registerCardReturnedValue is CardStateModel.Revealed.RegisterFailed)
        }
    }
}