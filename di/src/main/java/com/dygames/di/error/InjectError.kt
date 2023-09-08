package com.dygames.di.error

import kotlin.reflect.KType

sealed class InjectError(override val message: String?) : Throwable() {
    class ConstructorNoneAvailable(type: KType) : InjectError("{$type}의 생성자가 존재하지 않습니다.")
    class DependenciesNotInitialized : InjectError("의존이 초기화되지 않았습니다.")
}
