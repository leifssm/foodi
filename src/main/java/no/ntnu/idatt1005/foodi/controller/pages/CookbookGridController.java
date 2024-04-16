package no.ntnu.idatt1005.foodi.controller.pages;

import no.ntnu.idatt1005.foodi.view.views.CookbookGrid;

/**
 * Controller for the cookbook grid page. This controller manages the updates to the cookbook grid
 * page.
 */
public class CookbookGridController extends PageController {

  public CookbookGridController(CookbookGrid cookbookGridPage) {
    super(cookbookGridPage);
  }

  @Override
  void update() {
    // Update the cookbook grid view
    System.out.println("Get data from backend and update the cookbook grid view.");
    System.out.println("Call the render() with the appropriate data for the cookbook grid page.");
  }
}
