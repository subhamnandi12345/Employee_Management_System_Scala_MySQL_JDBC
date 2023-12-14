package config

import java.sql.{Connection, DriverManager}

object DatabaseConfig {
  private val url = "jdbc:mysql://localhost:3306/employeedatabase"
  private val user = "root"
  private val password = "root@123"

  def getConnection: Connection = {
    Class.forName("com.mysql.cj.jdbc.Driver")
    DriverManager.getConnection(url, user, password)
  }
}
