package com.jozeftvrdy.o2_home_assignment.data.model

import java.util.UUID

sealed class CardStateModel {
    sealed class Unrevealed : CardStateModel() {
        data object Initial : Unrevealed()

        data object Scratching : Unrevealed()

        data object ScratchingFailed : Unrevealed()
    }

    sealed class Revealed : CardStateModel() {
        abstract val generatedUUID: UUID

        class Unregistered(
            override val generatedUUID: UUID
        ) : Revealed()

        class Registering(
            override val generatedUUID: UUID
        ) : Revealed()

        class Registered(
            override val generatedUUID: UUID
        ) : Revealed()

        class RegisterFailed(
            override val generatedUUID: UUID
        ) : Revealed()
    }
}