package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._
import scalikejdbc._

class UserController extends Controller {
  val userForm = Form(
    tuple(
      "name" -> nonEmptyText,
      "email" -> email,
      "password" -> nonEmptyText))

  def entryInit = Action { implicit request =>
    val filledForm = userForm.fill("name", "email", "password")
//    Ok(views.html.entry("hoge", filledForm))
    Ok("hoge")
  }

  def entrySubmit = Action { implicit request =>
    //    userForm.bindFromRequest.fold(
    //      errors => {
    //        BadRequest(views.html.entry("error", errors))
    //      },
    //      success => {
    //        val (name, email, password) = success
    //        UserService.entry(name,email,password) match {
    //          case Some(id) => {
    //            UserService.findByPk(id) match {
    //              case Some(u) => Ok(views.html.entrySubmit(u))
    //              case None => Redirect("/user/entry").flashing("result" -> "user not found")
    //            }
    //          }
    //          case None => Redirect("/user/entry").flashing("result" -> "entry failure")
    //        }
    //      })
    //  }
    Ok("fuga")
  }

  def find() = Action { implicit request =>
    val user = User.find(1)
    Ok(user.toString)
  }

  def findAll() = Action { implicit request =>
    val userList = User.findAll()
    Ok(userList.toString)
  }

  def create() = Action { implicit request =>
    val user = User.create(Option("hagi"), Option("hagi@cyberagent.co.jp"), Option("12345"))
    Ok(user.toString)
  }

  def destroy() = Action {implicit request =>
    val user = User.destroy(2)
    Ok(user.toString)
  }
}
