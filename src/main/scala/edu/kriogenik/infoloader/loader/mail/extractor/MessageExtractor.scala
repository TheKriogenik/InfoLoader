package edu.kriogenik.infoloader.loader.mail.extractor

import javax.mail.Message


/**
 * Расширение класса типов [[edu.kriogenik.infoloader.loader.mail.extractor.Extractor]]
 * для использования с сообщениями типа `Message`.
 * @tparam A
 *           Тип объекта, который требуется получить.
 */
trait MessageExtractor[A] extends Extractor [Message, A]

/**
 * Объект для более удобного использования данного класса типов.
 */
object MessageExtractor{

  /**
   * Метод для удобного получения реализации для требуемых типов.
   * @param ext
   *          Объект класса типа для классов [ `Message`, A],
   *          используется неявно.
   * @tparam A
   *          Класс, для которого реализован тип класса.
   * @return
   *          Объект класса типа для заданных классов.
   */
  def apply[A](implicit ext: Extractor[Message, A]): Extractor[Message, A] = ext

  /**
   * Неявный класс-расширение для использования возможностей класса типов
   * в ООП-стиле.
   * @param x
   *           Сообщение, из которого будет производиться получение данных.
   */
  implicit class MessageExtractorOps(x: Message){

    /**
     * Функция получения данных типа `A` из сообщения.
     * @tparam A
     *         Тип требуемых данных
     * @return
     *         Данные типа `A`.
     */
    def extract[A: MessageExtractor]: A = MessageExtractor[A].extract(x)

  }

}
