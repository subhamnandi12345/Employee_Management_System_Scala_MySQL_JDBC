package models

import play.api.libs.json.{Format, JsResult, JsValue, Json}

case class Employee(id: Long, name: String, designation: String)

object Employee {
  implicit val employeeFormat: Format[Employee] = new Format[Employee] {
    override def writes(employee: Employee): JsValue = Json.obj(
      "id" -> employee.id,
      "name" -> employee.name,
      "designation" -> employee.designation
    )

    override def reads(json: JsValue): JsResult[Employee] = {
      for {
        id <- (json \ "id").validate[Long]
        name <- (json \ "name").validate[String]
        designation <- (json \ "designation").validate[String]
      } yield Employee(id, name, designation)
    }
  }
}