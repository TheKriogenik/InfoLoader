package edu.kriogenik.infoloader.messaging.producer

import edu.kriogenik.infoloader.messaging.dto.{Dto, StudentDto}
import edu.kriogenik.infoloader.model.Student
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

/**
 * Класс, позволяющий отправлять сообщения типа [[edu.kriogenik.infoloader.model.Student]].
 * @param redisTemplate
 *           Объект, выполняющий отправку сообщения брокеру, реализованному
 *           на платформе Redis.
 * @param topicName
 *           Имя топика, в который идет отправка.
 */
@Component
class StudentMessageProducer(
                              redisTemplate: RedisTemplate[String, StudentDto],
                              @Value("${topic.student}")
                              topicName: String) extends MessageProducer[Student] {

  /**
   * Объект для логгирования действий.
   */
  private val log = LoggerFactory.getLogger(this.getClass)

  override def send(message: Student): Unit = {
    val dto = Dto[Student, StudentDto].toDto(message)
    redisTemplate.convertAndSend(topicName, dto)
    log.info(s"Published employee message to $topicName.")
    log.debug(s"Message: $dto.")
  }

}
