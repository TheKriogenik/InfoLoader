package edu.kriogenik.infoloader.messaging.producer

import edu.kriogenik.infoloader.messaging.dto.{Dto, EmployeeDto}
import edu.kriogenik.infoloader.model.Employee
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

/**
 * Класс, позволяющий отправлять сообщения типа [[edu.kriogenik.infoloader.model.Employee]].
 * @param redisTemplate
 *           Объект, выполняющий отправку сообщения брокеру, реализованному
 *           на платформе Redis.
 * @param topicName
 *           Имя топика, в который идет отправка.
 */
@Component
class EmployeeMessageProducer(
                             redisTemplate: RedisTemplate[String, EmployeeDto],
                             @Value("${topic.employee}")
                             topicName: String) extends MessageProducer[Employee] {

  /**
   * Объект для логгирования действий.
   */
  private val log = LoggerFactory.getLogger(this.getClass)

  override def send(message: Employee): Unit = {
    val dto = Dto[Employee, EmployeeDto].toDto(message)
    redisTemplate.convertAndSend(topicName, dto)
    log.info(s"Published employee message to $topicName.")
    log.debug(s"Message: $dto.")
  }

}
