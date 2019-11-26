package com.rightfromleftsw.domain

class AnalyzeImageUseCase(): UseCase<ImageInput, EmotionsOutput> {
  override fun apply(input: ImageInput): EmotionsOutput {
    return object : EmotionsOutput { }
  }
}

interface ImageInput

interface EmotionsOutput