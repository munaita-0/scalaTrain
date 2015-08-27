package models

import scalikejdbc._
import org.joda.time._

case class User(
                 id: Long,
                 name: Option[String] = None,
                 email: Option[String] = None,
                 password: Option[String] = None,
                 create_date: DateTime) {
}

object User extends SQLSyntaxSupport[User] {
  def apply(s: SyntaxProvider[User])(rs: WrappedResultSet): User = apply(s.resultName)(rs)

  def apply(s: ResultName[User])(rs: WrappedResultSet): User = new User(
    id = rs.get(s.id),
    name = rs.get(s.name),
    email = rs.get(s.email),
    password = rs.get(s.password),
    create_date = rs.get(s.create_date)
  )

  def opt(s: SyntaxProvider[User])(rs: WrappedResultSet): Option[User] = rs.longOpt(s.resultName.id).map(_ => apply(s.resultName)(rs))

  val u = User.syntax("u")

  def find(id: Long)(implicit session: DBSession = autoSession): Option[User] = withSQL {
    select.from(User as u).where.eq(u.id, id)
  }.map(User(u)).single.apply()

  def findAll()(implicit session: DBSession = autoSession): List[User] = withSQL {
    select.from(User as u)
      .orderBy(u.id)
  }.map(User(u)).list.apply()

  def create(name: Option[String], email: Option[String], password: Option[String])(implicit session: DBSession = autoSession): User = {
    val id = withSQL {
      insert.into(User).namedValues(
        column.name -> name,
        column.email -> email,
        column.password -> password
      )
    }.updateAndReturnGeneratedKey.apply()
    User(id, name, email, password, DateTime.now)
  }

  def destroy(id: Long)(implicit session: DBSession = autoSession): Unit = withSQL {
    delete.from(User).where.eq(User.column.id, 2)
  }.update.apply()

  //  def edit(id: Long, name: Option[String], email: Option[String], password: Option[String])(implicit session: DBSession = autoSession): User = withSQL {
  //    update(User).set(
  //      column.name -> name,
  //      column.email -> email,
  //      column.password -> password
  //    ).where.eq(User.column.id, id)
  //  }.update.apply()
}
