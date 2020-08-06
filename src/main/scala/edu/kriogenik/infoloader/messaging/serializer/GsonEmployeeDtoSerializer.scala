package edu.kriogenik.infoloader.messaging.serializer

import com.google.gson.Gson
import edu.kriogenik.infoloader.messaging.dto.EmployeeDto
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.stereotype.Component

/**
 * Класс, содержащий реализацию методов сериализации отправляемых/принимаемых сообщений
 * для типа [[edu.kriogenik.infoloader.messaging.dto.EmployeeDto]].
 * @param gson
 *          Объект, сериализующий данные формата JSON.
 */
@Component
class GsonEmployeeDtoSerializer(gson: Gson) extends RedisSerializer[EmployeeDto] {

  override def serialize(t: EmployeeDto): Array[Byte] = gson.toJson(t).getBytes

  override def deserialize(bytes: Array[Byte]): EmployeeDto = gson.fromJson(bytes.toString, EmployeeDto.getClass)

}
