package com.rightfromleftsw.domain

interface UseCase<Input, Output> {
  fun apply(input: Input): Output
}
