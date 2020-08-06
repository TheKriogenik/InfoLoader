package edu.kriogenik.infoloader.model

import edu.kriogenik.infoloader.typeclasses.{Read, Show}

/**
 * Тип, описывающий возможные уровни получаемого образования.
 */
sealed trait EducationLevel

/**
 * Объект, содержащий описания уровней получаемого обучения.
 */
object EducationLevel{

  /**
   * Объект, описывающий уровень обучения `бакалавриат`.
   */
  case object BACCALAUREATE extends EducationLevel

  /**
   * Объект, описывающий уровень обучения `магистратура`.
   */
  case object MAGISTRACY    extends EducationLevel

  /**
   * Объект, описывающий уровень обучения `аспирантура`.
   */
  case object POSTGRADUATE  extends EducationLevel

  /**
   * Объект, описывающий уровень обучения `докторантура`.
   */
  case object DOCTORAL      extends EducationLevel

  /**
   * Объект, описывающий уровень обучения `специалитет`.
   */
  case object SPECIALIST    extends EducationLevel

  /**
   * Объект, описывающий уровень обучения `слушатель`.
   */
  case object LISTENER      extends EducationLevel

  /**
   * Объект, описывающий отсутствие информации об уровне
   * получаемого образования.
   */
  case object NOTHING_LEVEL extends EducationLevel

  /**
   * Реализация класса типов [[edu.kriogenik.infoloader.typeclasses.Show]].
   */
  implicit val showEducationLevelInstance: Show[EducationLevel] = {
    case BACCALAUREATE => "бакалавриат"
    case MAGISTRACY    => "магистратура"
    case POSTGRADUATE  => "аспирантура"
    case DOCTORAL      => "докторантура"
    case SPECIALIST    => "специалитет"
    case LISTENER      => "слушатели"
    case _             => "-"
  }

  /**
   * Реализация класса типов [[edu.kriogenik.infoloader.typeclasses.Read]].
   */
  implicit  val readEducationLevelInstance: Read[EducationLevel] = {
    case "бакалавриат"  => BACCALAUREATE
    case "магистратура" => MAGISTRACY
    case "аспирантура"  => POSTGRADUATE
    case "докторантура" => DOCTORAL
    case "специалитет"  => SPECIALIST
    case "слушатели"    => LISTENER
    case _              => NOTHING_LEVEL
  }

}
