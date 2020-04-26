package models

object CodeGen extends App {
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.PostgresProfile",
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost/tasklist?user=parker&password=password",
    "/home/parker/CSCI-3345/in-class-s20-placy2/server/app",
    "models", None, None, true, false
  )
}