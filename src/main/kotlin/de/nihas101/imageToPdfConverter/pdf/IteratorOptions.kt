package de.nihas101.imageToPdfConverter.pdf

data class IteratorOptions(
        val multipleDirectories: Boolean = false,
        val zipFiles: Boolean = false,
        val deleteOnExit: Boolean = true
)