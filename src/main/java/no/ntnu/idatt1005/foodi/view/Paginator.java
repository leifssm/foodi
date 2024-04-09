package no.ntnu.idatt1002.view;

import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;

/**
 * A class for handling a large list of items, and separating them into chunks.
 *
 * @version 1.0
 * @param <T> The type of the items to paginate
 */
public class Paginator<T> {
  private ArrayList<T> items;
  private int itemsPerPage;
  private int currentPage;

  /**
   * Creates a Paginator instance, initialized with the given items and chunk size.
   *
   * @param items The items to paginate
   * @param itemsPerPage The number of items per page
   */
  public Paginator(@NotNull ArrayList<T> items, int itemsPerPage) {
    this.items = items;
    this.itemsPerPage = itemsPerPage;
    this.currentPage = 0;
  }

  /**
   * Creates a Paginator instance, initialized with the given items and a default chunk size of 10.
   *
   * @param items The items to paginate
   */
  public Paginator(@NotNull ArrayList<T> items) {
    this(items, 10);
  }

  /**
   * Creates an empty Paginator instance with a default chunk size of 10.
   */
  public Paginator() {
    this(new ArrayList<>());
  }

  /**
   * Adds an item to the paginator.
   *
   * @param item The item to add
   */
  public void addItem(@NotNull T item) {
    items.add(item);
  }

  /**
   * Adds multiple items to the paginator.
   *
   * @param items The items to add
   */
  @SafeVarargs
  public final void addItems(T @NotNull ... items) {
    for (T item : items) {
      addItem(item);
    }
  }

  /**
   * Returns the current page of items.
   */
  public ArrayList<T> getCurrentPage() {
    int start = currentPage * itemsPerPage;
    int end = Math.min(start + itemsPerPage, getTotalItems());

    // Wrapped in a new ArrayList to prevent modification of the original list
    return new ArrayList<>(items.subList(start, end));
  }

  public int getCurrentPageNumber() {
    return currentPage;
  }

  public int getTotalItems() {
    return items.size();
  }

  public int getTotalPages() {
    return (int) Math.ceil((double) getTotalItems() / itemsPerPage);
  }

  public int getItemsPerPage() {
    return itemsPerPage;
  }

  public void setItemsPerPage(int itemsPerPage) {
    this.itemsPerPage = itemsPerPage;
  }

  /**
   * Sets the current page of the paginator.
   *
   * @param currentPage The page number to set
   * @throws IllegalArgumentException If the page number is less than 0 or greater than the total
   *                                  number of pages
   */
  public void setCurrentPage(int currentPage) throws IllegalArgumentException {
    if (currentPage < 0 || currentPage >= getTotalPages()) {
      throw new IllegalArgumentException("Page number out of bounds");
    }
    this.currentPage = currentPage;
  }

  /**
   * Overrides the current items with the given items.
   *
   * @param items The items to set
   */
  public void setItems(@NotNull ArrayList<T> items) {
    this.items = items;
  }

  /**
   * Returns whether there is a next page.
   */
  public boolean hasNextPage() {
    return currentPage < getTotalPages();
  }

  /**
   * Returns whether there is a previous page.
   */
  public boolean hasPreviousPage() {
    return currentPage > 0;
  }

  /**
   * Moves to the previous page if there is one.
   */
  public void previousPage() {
    if (hasPreviousPage()) {
      currentPage--;
    }
  }

  /**
   * Moves to the next page if there is one.
   */
  public void nextPage() {
    if (hasNextPage()) {
      currentPage++;
    }
  }
}
