package no.ntnu.idatt1005.foodi.view.components.shoppinglist;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.function.Consumer;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1005.foodi.model.objects.dtos.AmountedIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.ExpiringIngredient;
import no.ntnu.idatt1005.foodi.view.components.dialog.StandardDialog;
import no.ntnu.idatt1005.foodi.view.exceptions.ValidationException;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AddIngredientsExpirationDialog extends StandardDialog {

  private final ExpiringIngredient[] expiringIngredients;
  private boolean isCompleted = false;

  public AddIngredientsExpirationDialog(@NotNull List<AmountedIngredient> ingredients) {
    super();
    setHeaderTitle("Add Expiration Dates");
    setTitle("Add Expiration Dates");

    VBox ingredientExpirationQueries = new VBox();

    expiringIngredients = new ExpiringIngredient[ingredients.size()];

    for (int i = 0; i < ingredients.size(); i++) {
      int finalI = i;
      expiringIngredients[i] = null;
      ingredientExpirationQueries.getChildren().add(
          new IngredientExpirationQuery(
              ingredients.get(i),
              ingredient -> expiringIngredients[finalI] = ingredient
          )
      );
    }

    getDialogPane().setContent(ingredientExpirationQueries);
    addCancelButton();
    addOkButton();
    setOkAction(() -> {
      if (!isFilled()) {
        throw new ValidationException("Please fill out all expiration dates with valid dates.");
      }
      isCompleted = true;
    });
  }

  public boolean isFilled() {
    for (ExpiringIngredient expiringIngredient : expiringIngredients) {
      if (expiringIngredient == null) {
        return false;
      }
    }
    return true;
  }

  public @Nullable ExpiringIngredient[] getExpiringIngredients() {
    if (!isCompleted) {
      return null;
    }
    return expiringIngredients;
  }

  private static class IngredientExpirationQuery extends BorderPane implements ComponentUtils {

    private final TextField expirationDateField = new TextField();

    public IngredientExpirationQuery(
        AmountedIngredient ingredient,
        Consumer<@Nullable ExpiringIngredient> changeExpirationDate
    ) {
      super();

      expirationDateField.setPromptText("dd.mm.yyyy");
      expirationDateField.setOnKeyTyped(
          e -> {
            LocalDate expirationDate = getExpirationDate();
            System.out.println(expirationDate);
            if (expirationDate == null) {
              changeExpirationDate.accept(null);
              return;
            }
            changeExpirationDate.accept(
                new ExpiringIngredient(
                    ingredient,
                    expirationDate,
                    false
                )
            );
          }
      );

      setLeft(new Label(ingredient.getName()));
      setRight(expirationDateField);
    }

    private @Nullable LocalDate getExpirationDate() {
      if (expirationDateField.getText().isBlank()) {
        return null;
      }

      try {
        return LocalDate.parse(
            expirationDateField.getText(),
            DateTimeFormatter.ofPattern("dd.MM.yyyy")
        );
      } catch (DateTimeParseException e) {
        return null;
      }
    }
  }
}
