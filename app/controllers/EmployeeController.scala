package controllers


import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.{Employee, EmployeeDAO}
import play.api.libs.json.Format.GenericFormat
import play.api.libs.json.OFormat.oFormatFromReadsAndOWrites

@Singleton
class EmployeeController @Inject()(val controllerComponents: ControllerComponents)
  extends BaseController {
  val employeeWrites: Writes[Employee] = Json.writes[Employee]
  val employeeReads: Reads[Employee] = Json.reads[Employee]
  def getAllEmployees = Action { implicit request =>
    val employees = EmployeeDAO.getAllEmployees
    Ok(Json.toJson(employees))
  }
  def getEmployeeById(id: Long) = Action { implicit request =>
    val employee = EmployeeDAO.getEmployeeById(id)
    employee match {
      case Some(emp) => Ok(Json.toJson(emp))
      case None => NotFound
    }
  }

  def createEmployee = Action(parse.json) { implicit request =>
    val maybeEmployee: Option[Employee] = request.body.asOpt[Employee]

    maybeEmployee match {
      case Some(employee) =>
        // Process the employee data and add it to the database
        Ok(Json.obj("message" -> "Employee added successfully"))
      case None =>
        BadRequest(Json.obj("message" -> "Invalid or missing employee data in the request"))
    }
  }

  def updateEmployee(id: Long) = Action(parse.json) { implicit request =>
    val employeeJson = request.body.validate[Employee]

    employeeJson.fold(
      errors => BadRequest(Json.obj("message" -> JsError.toJson(errors))),
      updatedEmployee => {
        if (EmployeeDAO.updateEmployee(id, updatedEmployee)) {
          Ok(Json.obj("message" -> "Employee updated successfully"))
        } else {
          NotFound(Json.obj("message" -> "Employee not found"))
        }
      }
    )
  }

  def updateEmployeeByName(name: String) = Action(parse.json) { implicit request =>
    val employeeJson = request.body.validate[Employee]
    employeeJson.fold(
      errors => BadRequest(Json.obj("message" -> JsError.toJson(errors))),
      updatedEmployee => {
        if (EmployeeDAO.updateEmployeeByName(name, updatedEmployee)) {
          Ok(Json.obj("message" -> s"Employee with name $name updated successfully"))
        } else {
          NotFound(Json.obj("message" -> s"Employee with name $name not found"))
        }
      }
    )
  }


  def deleteEmployee(id: Long) = Action { implicit request =>
    if (EmployeeDAO.deleteEmployee(id)) {
      Ok(Json.obj("message" -> "Employee deleted successfully"))
    } else {
      NotFound(Json.obj("message" -> "Employee not found"))
    }
  }

  def deleteEmployeeByName(name: String) = Action { implicit request =>
    if (EmployeeDAO.deleteEmployeeByName(name)) {
      Ok(Json.obj("message" -> s"Employee with name $name deleted successfully"))
    } else {
      NotFound(Json.obj("message" -> s"Employee with name $name not found"))
    }
  }

}

