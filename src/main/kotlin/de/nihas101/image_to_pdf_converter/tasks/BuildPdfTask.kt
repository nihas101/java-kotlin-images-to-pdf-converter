/*
 JaKoImage2PDF is a program for converting images to PDFs with the use of iText 7
 Copyright (C) 2018  Nikita Hasert

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package de.nihas101.image_to_pdf_converter.tasks

import de.nihas101.image_to_pdf_converter.directory_iterators.DirectoryIterator
import de.nihas101.image_to_pdf_converter.pdf.builders.PdfBuilder
import de.nihas101.image_to_pdf_converter.pdf.pdf_options.ImageToPdfOptions
import de.nihas101.image_to_pdf_converter.util.JaKoLogger
import de.nihas101.image_to_pdf_converter.util.ProgressUpdater

class BuildPdfTask private constructor(
        private val pdfBuilder: PdfBuilder,
        private val directoryIterator: DirectoryIterator,
        private val imageToPdfOptions: ImageToPdfOptions,
        private val progressUpdater: ProgressUpdater,
        callClosure: CallClosure
) : CancellableTask(pdfBuilder, callClosure) {

    override fun call() {
        callClosure.before()
        var wasSuccessful = false
        try {
            wasSuccessful = pdfBuilder.build(directoryIterator, imageToPdfOptions, progressUpdater)
        } catch (exception: InterruptedException) {
            /* The task was cancelled */
            logger.warn("{}", exception)
        }
        callClosure.after(wasSuccessful)
    }

    companion object BuildPdfTaskFactory {
        private val logger = JaKoLogger.createLogger(BuildPdfTask::class.java)

        fun createBuildPdfTask(
                pdfBuilder: PdfBuilder,
                directoryIterator: DirectoryIterator,
                imageToPdfOptions: ImageToPdfOptions,
                progressUpdater: ProgressUpdater,
                callClosure: CallClosure
        ): BuildPdfTask {
            return BuildPdfTask(pdfBuilder, directoryIterator, imageToPdfOptions, progressUpdater, callClosure)
        }
    }
}