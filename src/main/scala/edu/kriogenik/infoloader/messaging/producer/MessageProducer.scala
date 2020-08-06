package edu.kriogenik.infoloader.messaging.producer

/**
 * Интерфейс, предоставляющий возможность отправки сообщения
 * типа T.
 * @tparam T
 *         Тип отправляемого сообщения.
 */
trait MessageProducer[T] {

  /**
   * Функция отправки сообщения.
   * @param message
   *        Сообщение для отправки.
   */
  def send(message: T): Unit

}
