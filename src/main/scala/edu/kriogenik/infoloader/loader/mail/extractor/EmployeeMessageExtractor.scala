package edu.kriogenik.infoloader.loader.mail.extractor

import java.io.{BufferedReader, InputStreamReader}

import edu.kriogenik.infoloader.typeclasses.instances.TraversingInstances.ListEitherTraversable
import edu.kriogenik.infoloader.model.Employee
import edu.kriogenik.infoloader.parsing.Parser.ParseResult
import edu.kriogenik.infoloader.parsing.parsers.EmployeeParser.parseEmployee
import edu.kriogenik.infoloader.typeclasses.Read._
import edu.kriogenik.infoloader.typeclasses.Traversing
import javax.mail.{Message, Multipart}

import scala.jdk.CollectionConverters.IteratorHasAsScala
import scala.util.{Failure, Success, Try}

/**
 * Объект, содержащий реализацию класса типов [[edu.kriogenik.infoloader.loader.mail.extractor.MessageExtractor]]
 * для получения списка сотрудников из письма.
 */
object EmployeeMessageExtractor {

  /**
   * Имя файла, который требуется найти в сообщении.
   */
  private val fileName = "PERSON_DATA.CSV"

  /**
   * Реализация класса типов.
   */
  implicit val employeeMessageExtractor: MessageExtractor[List[Employee]] = (from: Message) => {
    val employees = for {
      mp        <- Try {from.getContent.asInstanceOf[Multipart]}
      bps       <- Try {((0 until mp.getCount) map mp.getBodyPart).toList.filter { p => p.getFileName == fileName }}
      stream    <- Try {bps.map(_.getInputStream)}
      isr       <- Try {stream map {new InputStreamReader(_, "cp1251")}}
      bsr       <- Try {isr map {new BufferedReader(_)}}
      lines     <- Try {bsr.flatMap(b => b.lines().iterator().asScala.toList)}
      employees <- Try {lines.drop(1).map { x => x.read[ParseResult[Employee]] }}
    } yield employees

    employees match {
      case Failure(_) => Nil
      case Success(value) => Traversing[List, ({type T[A] = Either[Exception, A]})#T].traverse(value) match {
        case Left(_) => List.empty[Employee]
        case Right(value) => value
      }
    }
  }

}
