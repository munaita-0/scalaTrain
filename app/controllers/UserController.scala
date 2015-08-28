package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._

case class EntryForm(name: Option[String], email: Option[String], password: Option[String])

class UserController extends Controller {

  val entryForm = Form(
    mapping(
      "name" -> optional(nonEmptyText),
      "email" -> optional(nonEmptyText),
      "password" -> optional(nonEmptyText))(EntryForm.apply)(EntryForm.unapply))

  def findAll() = Action { implicit request =>
    val userList = User.findAll()
    Ok(views.html.list(userList))
  }

  def entry = Action { implicit request =>
    Ok(views.html.entry(entryForm))
  }

  def create = Action { implicit req =>
    entryForm.bindFromRequest.fold(
      formWithErrors => BadRequest("invalid parameters"),
      form => {
        User.create(
          name = form.name,
          email = form.email,
          password = form.password
        )
      }
    )
    Ok(views.html.list(User.findAll()))
  }

  def destroy(id: Long) = Action {
    implicit request =>
      User.destroy(id)
      Ok(views.html.list(User.findAll()))
  }
}
