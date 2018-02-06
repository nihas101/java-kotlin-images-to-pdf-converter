package de.nihas101.imagesToPdfConverter.pdf

import de.nihas101.imagesToPdfConverter.fileReader.DirectoryIterator
import de.nihas101.imagesToPdfConverter.fileReader.ImageDirectoriesIterator
import de.nihas101.imagesToPdfConverter.fileReader.ImageFilesIterator
import de.nihas101.imagesToPdfConverter.pdf.PdfWriterOptions.OptionsFactory.createOptions
import java.io.File

data class PdfBuildInformation(
        var sourceFile: File? = null,
        private var pdfWriterOptions: PdfWriterOptions = createOptions(),
        private var directoryIterator: DirectoryIterator? = null,
        var targetFile: File? = null
        ){

    fun setupDirectoryIterator(){
        directoryIterator = if(!pdfWriterOptions.multipleDirectories)
            ImageFilesIterator.createImageFilesIterator(sourceFile!!)
        else
            ImageDirectoriesIterator.createImageDirectoriesIterator(sourceFile!!)
    }

    fun setMultipleDirectories(multipleDirectories: Boolean){
        pdfWriterOptions = pdfWriterOptions.copy(multipleDirectories = multipleDirectories)
    }

    fun getMultipleDirectories() = pdfWriterOptions.multipleDirectories

    fun getPdfWriterOptions() = pdfWriterOptions

    fun getDirectoryIterator() = directoryIterator!!
}