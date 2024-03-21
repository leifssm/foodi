package no.ntnu.idatt1005.foodi.controller;

import no.ntnu.idatt1005.foodi.model.DAO.UserDAO;
import no.ntnu.idatt1005.foodi.model.objects.User;

import java.sql.SQLException;

/**
 * This class is responsible for handling the usage of
 * database operations regarding stored users in the frontend.
 *
 * @version 0.1.0
 * @author Snake727
 */
public class UserController {
  private UserDAO userDAO;

  public UserController() {
    userDAO = new UserDAO();
  }

  public void save(User user) {
    try {
      userDAO.save(user);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public User retrieve(User user) {
      return userDAO.retrieve(user);
  }

  public void delete(User user) {
      userDAO.delete(user);
  }
}
