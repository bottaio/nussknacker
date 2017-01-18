package pl.touk.esp.ui.codec

import argonaut.Json._
import argonaut.{CodecJson, CursorHistory, DecodeResult}
import pl.touk.esp.ui.db.entity.ProcessEntity.ProcessType
import pl.touk.esp.ui.db.entity.ProcessEntity.ProcessType.ProcessType

import scala.util.{Failure, Success, Try}

object ProcessTypeCodec {

  def codec = CodecJson[ProcessType.ProcessType](
    value => jString(value.toString),
    cursor => cursor.as[String].flatMap(tryToParse)
  )

  private def tryToParse(value: String) : DecodeResult[ProcessType]= Try(ProcessType.withName(value)) match {
    case Success(processType) => DecodeResult.ok(processType)
    case Failure(_) => DecodeResult.fail(s"$value cannot be converted to ProcessType", CursorHistory(List()))
  }

}