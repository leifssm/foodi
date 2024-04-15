package no.ntnu.idatt1005.foodi.model.objects.dtos;

import org.jetbrains.annotations.NotNull;

/**
 * This class represents a user with an id and a display name.
 *
 * @param userId the id of the user
 * @param name   the display name of the user
 */
public record User(int userId, @NotNull String name) {

}
