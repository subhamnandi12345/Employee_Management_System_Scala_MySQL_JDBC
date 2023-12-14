package models



import config.DatabaseConfig

import java.sql.{Connection, PreparedStatement, ResultSet}

object EmployeeDAO {
  private val connection: Connection = DatabaseConfig.getConnection

  def getAllEmployees: Seq[Employee] = {
    val query = "SELECT * FROM employees"
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery(query)

    var employees = Seq.empty[Employee]
    while (resultSet.next()) {
      val id = resultSet.getLong("id")
      val name = resultSet.getString("name")
      val designation = resultSet.getString("designation")

      employees :+= Employee(id, name, designation)
    }

    statement.close()
    employees
  }

  def getEmployeeById(id: Long): Option[Employee] = {
    val query = "SELECT * FROM employees WHERE id = ?"
    val preparedStatement: PreparedStatement = connection.prepareStatement(query)
    preparedStatement.setLong(1, id)

    val resultSet: ResultSet = preparedStatement.executeQuery()

    if (resultSet.next()) {
      val name = resultSet.getString("name")
      val designation = resultSet.getString("designation")

      Some(Employee(id, name, designation))
    } else {
      None
    }
  }

  def createEmployee(employee: Employee): Boolean = {
    val query = "INSERT INTO employees (id, name, designation) VALUES (?, ?, ?)"
    val preparedStatement: PreparedStatement = connection.prepareStatement(query)
    preparedStatement.setLong(1, employee.id)
    preparedStatement.setString(2, employee.name)
    preparedStatement.setString(3, employee.designation)

    val rowsAffected = preparedStatement.executeUpdate()
    preparedStatement.close()

    rowsAffected > 0
  }

  def updateEmployee(id: Long, updatedEmployee: Employee): Boolean = {
    val query = "UPDATE employees SET name = ?, designation = ? WHERE id = ?"
    val preparedStatement: PreparedStatement = connection.prepareStatement(query)
    preparedStatement.setString(1, updatedEmployee.name)
    preparedStatement.setString(2, updatedEmployee.designation)
    preparedStatement.setLong(3, id)

    val rowsAffected = preparedStatement.executeUpdate()
    preparedStatement.close()

    rowsAffected > 0
  }

  def updateEmployeeByName(name: String, updatedEmployee: Employee): Boolean = {
    val query = "UPDATE employees SET name = ?, designation = ? WHERE name = ?"
    val preparedStatement: PreparedStatement = connection.prepareStatement(query)
    preparedStatement.setString(1, updatedEmployee.name)
    preparedStatement.setString(2, updatedEmployee.designation)
    preparedStatement.setString(3, name)

    val rowsAffected = preparedStatement.executeUpdate()
    preparedStatement.close()

    rowsAffected > 0
  }

  def deleteEmployee(id: Long): Boolean = {
    val query = "DELETE FROM employees WHERE id = ?"
    val preparedStatement: PreparedStatement = connection.prepareStatement(query)
    preparedStatement.setLong(1, id)

    val rowsAffected = preparedStatement.executeUpdate()
    preparedStatement.close()

    rowsAffected > 0
  }

  def deleteEmployeeByName(name: String): Boolean = {
    val query = "DELETE FROM employees WHERE name = ?"
    val preparedStatement: PreparedStatement = connection.prepareStatement(query)
    preparedStatement.setString(1, name)

    val rowsAffected = preparedStatement.executeUpdate()
    preparedStatement.close()

    rowsAffected > 0
  }
}
