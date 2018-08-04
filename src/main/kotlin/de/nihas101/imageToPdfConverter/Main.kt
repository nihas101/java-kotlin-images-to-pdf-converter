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

package de.nihas101.imageToPdfConverter

import de.nihas101.imageToPdfConverter.gui.MainWindow
import de.nihas101.imageToPdfConverter.ui.PdfBuilderUI.PdfBuilderUiFactory.createPdfBuilderUI
import de.nihas101.imageToPdfConverter.ui.commandLineIO.PdfBuilderCommandLineInput
import de.nihas101.imageToPdfConverter.ui.commandLineIO.PdfBuilderCommandLineInterface
import de.nihas101.imageToPdfConverter.ui.commandLineIO.PdfBuilderCommandLineOutput
import javafx.application.Platform.runLater
import javafx.stage.Stage
import java.io.BufferedReader
import java.io.InputStreamReader

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.isEmpty()) startGUI()
        else if ("--nogui" == args[0].toLowerCase()) startTextOnly()
    }

    // TODO: ADD LOGGING WHERE POSSIBLE
    // TODO: Allow for debug flag, which prints all debug messages from slf4j - see video again if you don't know what this means
    // TODO: Replace option window with drop down menu if possible
    // TODO: [If possible] Make it so empty ImageIterators are created when the option is selected + images/directories are always added to it
    // instead of sometimes creating one Remember: The first image/ directory added sets the directory

    private fun startGUI() {
        try {
            MainWindow.main(arrayOf())
        } catch (exception: IllegalStateException) {
            /*  Application launch was already called before */
            runLater { MainWindow().start(Stage()) }
        }
    }

    private fun startTextOnly() {
        val commandLineOutput = PdfBuilderCommandLineOutput.createCommandLineOutput(System.out)
        val commandLineInput = PdfBuilderCommandLineInput.createCommandLineInput(BufferedReader(InputStreamReader(System.`in`)))
        val commandLineInterface = PdfBuilderCommandLineInterface.createCommandLineInterface(commandLineInput, commandLineOutput)
        createPdfBuilderUI(commandLineInterface).start()
    }
}
