package edu.kriogenik.infoloader.model

import edu.kriogenik.infoloader.typeclasses.{Read, Show}

/**
 * Тип, описывающий возможные формы обучения.
 */
sealed trait EducationForm

/**
 * Объект, содержащий формы обучения.
 */
object EducationForm{

  /**
   * Объект, описывающий `очную форму обучения`.
   */
  case object FULL_TIME    extends EducationForm

  /**
   * Объект, описывающий `заочную форму обученияю`.
   */
  case object PART_TIME    extends EducationForm

  /**
   * Объект, описывающий `обучение в вечернее время`.
   */
  case object EXTRAMURAL   extends EducationForm

  /**
   * Объект, описывающий отсутствие информации о форме обучения.
   */
  case object NOTHING_FORM extends EducationForm

  /**
   * Реализация класса типов [[edu.kriogenik.infoloader.typeclasses.Show]].
   */
  implicit  val showEducationFormInstance: Show[EducationForm] = {
    case FULL_TIME => "очная"
    case PART_TIME => "очно-заочная"
    case EXTRAMURAL => "заочная"
    case _ => "-"
  }

  /**
   * Реализация класса типов [[edu.kriogenik.infoloader.typeclasses.Read]].
   */
  implicit val readEducationFormInstance: Read[EducationForm] = {
    case "очная" => FULL_TIME
    case "очно-заочная" => PART_TIME
    case "заочная" => EXTRAMURAL
    case _ => NOTHING_FORM
  }

}
