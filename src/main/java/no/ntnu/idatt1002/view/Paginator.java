package no.ntnu.idatt1002.view;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Paginator<Type> {
  private ArrayList<Type> items;
  private int itemsPerPage;
  private int currentPage;

  public Paginator(@NotNull ArrayList<Type> items, int itemsPerPage) {
    this.items = items;
    this.itemsPerPage = itemsPerPage;
    this.currentPage = 0;
  }

  public Paginator(@NotNull ArrayList<Type> items) {
    this(items, 10);
  }

  public Paginator() {
    this(new ArrayList<>());
  }

  public void addItem(@NotNull Type item) {
    items.add(item);
  }

  @SafeVarargs
  public final void addItems(Type @NotNull ... items) {
    for (Type item : items) {
      addItem(item);
    }
  }

  public ArrayList<Type> getCurrentPage() {
    int start = currentPage * itemsPerPage;
    int end = Math.min(start + itemsPerPage, getTotalItems());

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

  public void setCurrentPage(int currentPage) {
    if (currentPage < 0 || currentPage >= getTotalPages()) {
      throw new IllegalArgumentException("Page number out of bounds");
    }
    this.currentPage = currentPage;
  }

  public void setItems(@NotNull ArrayList<Type> items) {
    this.items = items;
  }

  public boolean hasNextPage() {
    return currentPage < getTotalPages();
  }

  public boolean hasPreviousPage() {
    return currentPage > 0;
  }

  public void previousPage() {
    if (hasPreviousPage()) {
      currentPage--;
    }
  }

  public void nextPage() {
    if (hasNextPage()) {
      currentPage++;
    }
  }
}
