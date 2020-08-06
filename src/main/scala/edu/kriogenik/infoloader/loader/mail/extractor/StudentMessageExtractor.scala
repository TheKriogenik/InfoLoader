package edu.kriogenik.infoloader.loader.mail.extractor

import java.io.{BufferedReader, InputStreamReader}

import edu.kriogenik.infoloader.model.Student
import edu.kriogenik.infoloader.parsing.Parser.ParseResult
import edu.kriogenik.infoloader.parsing.parsers.StudentParser.parseStudent
import edu.kriogenik.infoloader.typeclasses.Read._
import edu.kriogenik.infoloader.typeclasses.Traversing
import edu.kriogenik.infoloader.typeclasses.instances.TraversingInstances.ListEitherTraversable
import javax.mail.{Message, Multipart}

import scala.jdk.CollectionConverters.IteratorHasAsScala
import scala.util.{Failure, Success, Try}

/**
 * Объект, содержащий реализацию класса типов [[edu.kriogenik.infoloader.loader.mail.extractor.MessageExtractor]]
 * для получения списка студентов.
 */
object StudentMessageExtractor {

  /**
   * Имя файла, который требуется найти в сообщении.
   */
  private val fileName: String = "stud.csv"

  /**
   * Реализация класса типов.
   */
  implicit val studentsMessageExtractor: MessageExtractor[List[Student]] = (from: Message) => {
    val students = for {
      mp       <- Try {from.getContent.asInstanceOf[Multipart]}
      bps      <- Try {((0 until mp.getCount) map mp.getBodyPart).toList.filter { p => p.getFileName == fileName }}
      stream   <- Try {bps.map(_.getInputStream)}
      isr      <- Try {stream map {new InputStreamReader(_, "cp1251")}}
      bsr      <- Try {isr map {new BufferedReader(_)}}
      lines    <- Try {bsr.flatMap { b => b.lines().iterator().asScala.toList }}
      students <- Try {lines.drop(1).map { x => x.read[ParseResult[Student]] }}
    } yield students

    students match {
      case Failure(_) => Nil
      case Success(value) => Traversing[List, ({type T[A] = Either[Exception, A]})#T].traverse(value) match {
        case Left(_) => List.empty[Student]
        case Right(value) => value
      }
    }
  }

}
