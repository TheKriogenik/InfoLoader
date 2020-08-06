package edu.kriogenik.infoloader.messaging.serializer

import com.google.gson.Gson
import edu.kriogenik.infoloader.messaging.dto.StudentDto
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.stereotype.Component

/**
 * Класс, содержащий реализацию методов сериализации отправляемых/принимаемых сообщений
 * для типа [[edu.kriogenik.infoloader.messaging.dto.StudentDto]].
 * @param gson
 *          Объект, сериализующий данные формата JSON.
 */
@Component
class GsonStudentDtoSerializer(gson: Gson) extends RedisSerializer[StudentDto]{

  override def serialize(t: StudentDto): Array[Byte] = gson.toJson(t).getBytes

  override def deserialize(bytes: Array[Byte]): StudentDto = gson.fromJson(bytes.toString, StudentDto.getClass)

}
