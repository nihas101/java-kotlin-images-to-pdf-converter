package de.nihas101.imagesToPdfConverter.directoryIterators.exceptions

import java.io.File

class NoMoreImagesException(file: File) : Exception("There are no more images to be found at: ${file.absolutePath}")