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

package de.nihas101.imageToPdfConverter.util

import org.slf4j.LoggerFactory
import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Level.*
import ch.qos.logback.classic.Logger

class JaKoLogger private constructor(private val logger: Logger) {
    // TODO: Tests
    // TODO: Make it so you can decide where to log

    fun setLoggingLevel(level: Level) {
        logger.level = level
    }

    fun trace(message: String) = logger.trace(message)

    fun debug(format: String, arg: Any) = logger.debug(format, arg)

    fun info(format: String, arg: Any) = logger.info(format, arg)

    fun error(format: String, arg: Any) = logger.error(format, arg)

    companion object {
        fun createLogger(javaClass: Class<*>, level: Level = DEBUG): JaKoLogger {
            val logger: Logger = LoggerFactory.getLogger(javaClass) as Logger
            logger.level = level

            return JaKoLogger(logger)
        }
    }
}