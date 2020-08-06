package edu.kriogenik.infoloader.parsing.parsers

import edu.kriogenik.infoloader.model.{EducationForm, EducationLevel, Names, Student}
import edu.kriogenik.infoloader.parsing.Parser.ParseResult
import edu.kriogenik.infoloader.parsing.parsers.CsvParsers.parseString
import edu.kriogenik.infoloader.parsing.parsers.NamesParser.parseNames
import edu.kriogenik.infoloader.parsing.Parser
import edu.kriogenik.infoloader.typeclasses.Applicative._
import edu.kriogenik.infoloader.typeclasses.Functor._
import edu.kriogenik.infoloader.typeclasses.Read.ReadOps
import edu.kriogenik.infoloader.typeclasses.Read

/**
 * Объект, содержащий реализацию парсера для объекта типа [[edu.kriogenik.infoloader.model.Student]].
 */
object StudentParser {

  /**
   * Реализация типа класса [[edu.kriogenik.infoloader.typeclasses.Read]]
   * для получения объекта класса [[edu.kriogenik.infoloader.model.Student]]
   * из строки.
   */
  implicit val parseStudent: Read[ParseResult[Student]] = (target: String) => parser(target)
  /**
   * Каррированная функция создания объекта класса [[edu.kriogenik.infoloader.model.Employee]].
   */
  private val model = Parser(Student.curried)

  /**
   * Реализация парсера для класса [[edu.kriogenik.infoloader.model.Student]].
   */
  private lazy val parser =
    model <*> long <*> names <*> string <*> level <*> form <*> string <*> string <*> string <*> dqstring

  /**
   * Базовый парсер для получения строк.
   */
  private val string   = Parser{Read[(String, String)].read _}

  /**
   * Парсер для получения имен.
   */
  private val names    = Parser{Read[(String, Names)].read _}

  /**
   * Базовый парсер для получения строк с нестандартным разделителем `(")`.
   */
  private val dqstring = Parser{CsvParsers.parseDoubleQuotedString.read _}

  /**
   * Парсер для получения целочисленных значений.
   */
  private val long   = string fmap (_.toLong)

  /**
   * Парсер для получения значений типа [[edu.kriogenik.infoloader.model.EducationForm]].
   */
  private val form   = string fmap (_.read[EducationForm])

  /**
   * Парсер для получения значений типа [[edu.kriogenik.infoloader.model.EducationLevel]].
   */
  private val level  = string fmap (_.read[EducationLevel])

}
