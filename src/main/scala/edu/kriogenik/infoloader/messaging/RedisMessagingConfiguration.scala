package edu.kriogenik.infoloader.messaging

import edu.kriogenik.infoloader.messaging.dto.{EmployeeDto, StudentDto}
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.{Bean, Configuration, PropertySource}
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import org.springframework.data.redis.serializer.RedisSerializer

/**
 * Файл, содержащий конфигурации классов, используемых для отправки сообщений.
 */
@Configuration
@PropertySource(Array("classpath:application.properties"))
class RedisMessagingConfiguration {

  /**
   * Синоним типа, используется для улучшения читаемости.
   */
  type TopicAdapter = (ChannelTopic, MessageListenerAdapter)

  /**
   * Конструктор фабрики подключений к хранилищу Redis.
   * @param host
   *             Адрес подключения.
   * @param port
   *             Порт подключения.
   * @return
   *         Фабрика подключений.
   */
  @Bean
  def jedisConnectionFactory(@Value("${redis.host}") host: String,
                             @Value("${redis.port}") port: Int): JedisConnectionFactory = {
    val cfg = new RedisStandaloneConfiguration(){
      this.setHostName(host)
      this.setPort(port)
    }
    new JedisConnectionFactory(cfg)
  }

  /**
   * Конструктор объекта для отправки сообщений типа [[edu.kriogenik.infoloader.messaging.dto.StudentDto]]
   * в хранилище Redis.
   * @param serializer
   *           Объект-сериализатор сообщений.
   * @param connectionFactory
   *           Объект для создания подключения к хранилищу.
   * @return
   *           Объект, позволяющий отправлять сообщения.
   */
  @Bean
  def studentRedisTemplate(serializer: RedisSerializer[StudentDto],
                           connectionFactory: JedisConnectionFactory): RedisTemplate[String, StudentDto] = {
    new RedisTemplate[String, StudentDto]{
      setConnectionFactory(connectionFactory)
      setValueSerializer(serializer)
    }
  }

  /**
   * Конструктор объекта для отправки сообщений типа [[edu.kriogenik.infoloader.messaging.dto.EmployeeDto]]
   * в хранилище Redis.
   * @param serializer
   *           Объект-сериализатор сообщений.
   * @param connectionFactory
   *           Объект для создания подключения к хранилищу.
   * @return
   *           Объект, позволяющий отправлять сообщения.
   */
  @Bean
  def employeeRedisTemplate(serializer: RedisSerializer[EmployeeDto],
                            connectionFactory: JedisConnectionFactory): RedisTemplate[String, EmployeeDto] = {
    new RedisTemplate[String, EmployeeDto]{
      setConnectionFactory(connectionFactory)
      setValueSerializer(serializer)
    }
  }

}
