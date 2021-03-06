package de.nihas101.image_to_pdf_converter.gui.controller

import de.nihas101.image_to_pdf_converter.directory_iterators.DirectoryIterator
import de.nihas101.image_to_pdf_converter.gui.MainWindow
import de.nihas101.image_to_pdf_converter.pdf.pdf_options.IteratorOptions
import javafx.scene.input.KeyCode
import javafx.stage.Stage
import junit.framework.TestCase.*
import org.junit.After
import org.junit.Test
import org.testfx.framework.junit.ApplicationTest
import java.io.File

class MainWindowTest : ApplicationTest() {
    private val waitingPeriod = 500L
    private var mainWindow: MainWindow? = null
    private var mainWindowController: MainWindowController? = null

    override fun start(stage: Stage?) {
        mainWindow = MainWindow()
        mainWindow!!.start(Stage())
        mainWindowController = mainWindow!!.mainWindowController
    }

    @After
    fun closeWindow() {
        closeCurrentWindow()
    }

    private fun setupDirectoryIterator(path: String, multipleDirectories: Boolean = false): DirectoryIterator {
        val directoryIterator = DirectoryIterator.createDirectoryIterator(
                File(path),
                IteratorOptions(multipleDirectories = multipleDirectories)
        )

        mainWindow!!.directoryIterator = directoryIterator
        mainWindowController!!.setupListView(directoryIterator)
        return directoryIterator
    }

    @Test
    fun deleteImageTest() {
        val directoryIterator = setupDirectoryIterator("src/test/resources/images")
        clickOnFirstCell(false)
        Thread.sleep(waitingPeriod)
        push(KeyCode.DELETE)
        Thread.sleep(waitingPeriod)

        assertEquals(3, directoryIterator.numberOfFiles())
    }

    @Test
    fun deleteDirectoryTest() {
        val directoryIterator = setupDirectoryIterator("src/test/resources", true)

        clickOnFirstCell(false)
        Thread.sleep(waitingPeriod)
        push(KeyCode.DELETE)
        Thread.sleep(waitingPeriod)

        assertEquals(0, directoryIterator.numberOfFiles())
    }

    private fun clickOnFirstCell(doubleClick: Boolean) {
        val coordinates = getCoordinatesOfFirstCell()
        if (doubleClick)
            doubleClickOn(coordinates[0], coordinates[1])
        else
            clickOn(coordinates[0], coordinates[1])
        Thread.sleep(waitingPeriod)
    }

    private fun getCoordinatesOfFirstCell(): List<Double> {
        val bounds = mainWindow!!.root.localToScreen(mainWindowController!!.imageListView.boundsInLocal)
        return listOf(bounds.minX + 200, bounds.minY + 50)
    }

    @Test
    fun buildSinglePdf() {
        val file = File("src/test/resources/images.pdf")
        setupDirectoryIterator("src/test/resources/images")

        mainWindowController!!.buildPdf(file)

        file.deleteOnExit()
        Thread.sleep(waitingPeriod)
        assertEquals(true, file.exists())
    }

    @Test
    fun buildMultiplePdf() {
        clickOn("#optionsButton")
        Thread.sleep(waitingPeriod)
        clickOn("#multipleDirectoriesCheckBox")
        Thread.sleep(waitingPeriod)
        clickOn("#saveToParticularLocationCheckBox")
        Thread.sleep(waitingPeriod)
        closeCurrentWindow()

        assertTrue(mainWindow!!.imageToPdfOptions.getIteratorOptions().multipleDirectories)
        assertTrue(mainWindow!!.imageToPdfOptions.getPdfOptions().useCustomLocation)
    }

    @Test
    fun saveToCustomLocationPermanenceTest() {
        clickOn("#optionsButton")
        Thread.sleep(waitingPeriod)
        clickOn("#saveToParticularLocationCheckBox")
        Thread.sleep(waitingPeriod)
        closeCurrentWindow()

        assertTrue(mainWindow!!.imageToPdfOptions.getPdfOptions().useCustomLocation)

        clickOn("#optionsButton")
        Thread.sleep(waitingPeriod)
        closeCurrentWindow()

        assertTrue(mainWindow!!.imageToPdfOptions.getPdfOptions().useCustomLocation)
    }

    @Test
    fun saveToCustomLocationChangeTest() {
        clickOn("#optionsButton")
        Thread.sleep(waitingPeriod)
        clickOn("#saveToParticularLocationCheckBox")
        Thread.sleep(waitingPeriod)
        closeCurrentWindow()

        assertTrue(mainWindow!!.imageToPdfOptions.getPdfOptions().useCustomLocation)

        clickOn("#optionsButton")
        Thread.sleep(waitingPeriod)
        clickOn("#saveToParticularLocationCheckBox")
        Thread.sleep(waitingPeriod)
        closeCurrentWindow()

        assertFalse(mainWindow!!.imageToPdfOptions.getPdfOptions().useCustomLocation)
    }

    @Test
    fun clearAllNoSetup() {
        clickOn("#clearAllButton")
    }

    @Test
    fun setupDirectory() {
        mainWindowController!!.setupChosenDirectory(File("src/test/resources/images"))

        assertEquals(true, mainWindow!!.chosenDirectory.exists())
        assertEquals(true, mainWindow!!.chosenDirectory.isDirectory)
    }

    @Test
    fun valuesSetFalse() {
        assertEquals(false, mainWindowController!!.valuesSetForBuilding())
    }

    @Test
    fun valuesSetTrue() {
        setupDirectoryIterator("src/test/resources/images")
        assertEquals(true, mainWindowController!!.valuesSetForBuilding())
    }

    @Test
    fun valuesSetNoFiles() {
        setupDirectoryIterator("src/test/resources/images/doesntExist")
        assertEquals(false, mainWindowController!!.valuesSetForBuilding())
    }

    @Test
    fun displayDirectory() {
        setupDirectoryIterator("src/test/resources", true)

        clickOnFirstCell(true)
        sleep(waitingPeriod)
        closeCurrentWindow()
    }
}