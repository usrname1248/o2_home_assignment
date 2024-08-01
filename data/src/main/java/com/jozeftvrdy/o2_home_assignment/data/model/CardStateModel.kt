package com.jozeftvrdy.o2_home_assignment.data.model

import java.util.UUID

sealed class CardStateModel {
    data object Initial: CardStateModel()

    sealed class Revealed: CardStateModel() {
        abstract val generatedUUID: UUID

        data class Unregistered(
            override val generatedUUID: UUID
        ): Revealed()

        data class Registered(
            override val generatedUUID: UUID
        ): Revealed()

        data class RegisterFailed(
            override val generatedUUID: UUID
        ): Revealed()
    }
}