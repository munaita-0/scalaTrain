package controllers
import scalikejdbc._
import play.api.mvc._
import org.joda.time._

class Application extends Controller {
  implicit val session = AutoSession

  def index = Action {
    val accounts = {
      try sql"select * from accounts".toMap.list.apply()
      catch {
        case e: Exception =>
          sql"create table accounts(name varchar(100) not null)".execute.apply()
          Seq("Alice", "Bob", "Chris").foreach { name =>
            sql"insert into accounts values ($name)".update.apply()
          }
          sql"select * from accounts".toMap.list.apply()
      }
    }
    Ok(accounts.toString())
  }

//  def index = Action {
//
//    case class Member(id: Long, name: String, birthday: Option[LocalTime] = None)
//    object Member extends SQLSyntaxSupport[Member] {
//      override val tableName = "members"
//      override val columnNames = Seq("id", "name", "birthday")
//
//      def create(name: String, birthday: Option[LocalTime])(implicit session: DBSession): Member = {
//        val id = withSQL {
//          insert.into(Member).namedValues(
//            column.name -> name,
//            column.birthday -> birthday
//          )
//        }.updateAndReturnGeneratedKey.apply()
//        Member(id, name, birthday)
//      }
//
//      def find(id: Long)(implicit session: DBSession): Option[Member] = {
//        val m = Member.syntax("m")
//        withSQL {
//          select.from(Member as m).where.eq(m.id, id)
//        }
//          .map { rs =>
//          new Member(
//            // rs.long の代わりに rs.get[Long] で型推論することもできます
//            id = rs.get(m.resultName.id),
//            name = rs.get(m.resultName.name),
//            birthday = rs.get(m.resultName.birthday)
//          )
//        }.single.apply()
//      }
//    }
//    Ok("hoge")
//  }

////  def index = Action {
//
//    case class User(
//                     id: Long,
//                     name: Option[String] = None,
//                     email: Option[String] = None,
//                     password: Option[String] = None,
//                     createDate: DateTime)
//
//    object User extends SQLSyntaxSupport[User] {
////      override val tableName = "User"
////      override val columnNames = Seq("id", "name", "email", "password", "createDate")
//
//      //    val allColumns = (rs: WrappedResultSet) => User(
//      //      id = rs.long("id"),
//      //      name = rs.stringOpt("name"),
//      //      email = rs.stringOpt("email"),
//      //      password = rs.stringOpt("password"),
//      //      createDate = rs.jodaDateTime("createDate")
//      //    )
//      //
//      //    val users: List[User] = DB readOnly { implicit session =>
//      //      SQL("select * from User limit 10").map(allColumns).list.apply()
//      //    }
//
//      val u = User.syntax("m")
//      val user: User = withSQL {
//        select.from(User as u).where.eq(u.id, 1)
//      }.map { rs =>
//        new User(
//          // rs.long の代わりに rs.get[Long] で型推論することもできます
//          id = rs.get(u.resultName.id),
//          name = rs.get(u.resultName.name),
//          email = rs.get(u.resultName.email),
//          createDate = rs.get(u.resultName.createDate)
//        )
//      }.single.apply()
//
////      print("hoge")
//    }
//
////    Ok("fuga")
//  }
}
