package no.ntnu.idatt1002.view.testclasses;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

/**
 * Temp data class, will be replaced with actual data from the database.
 */
public class InventoryItem {
  private final String type;
  private final String name;
  private final Date expiryDate;
  private final String category;
  private final String quantity;
  private final String unit;

  public InventoryItem() {
    this.type = "🍗";
    this.name = "Chicken";
    this.expiryDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 14 - (int) (1000 * 60 * 60 * 24 * 16 * Math.random()));
    this.category = new Random().nextInt(4) < 2 ? "Meat" : "Dairy";
    this.quantity = "100";
    this.unit = "g";
  }

  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public Date getExpiryDate() {
    return expiryDate;
  }

  public String getCategory() {
    return category;
  }

  public String getQuantity() {
    return quantity;
  }

  public String getUnit() {
    return unit;
  }


  public static GroupedInventoryItems groupItemsBy(InventoryItem[] items, String sortedBy, Function<InventoryItem, String> nameGetter) {
    InventoryItem[] sortedItems = Arrays.copyOf(items, items.length);
    Comparator<InventoryItem> comparator = Comparator.comparing(nameGetter);
    Arrays.sort(sortedItems, comparator);

    ArrayList<ArrayList<InventoryItem>> sortedGroups = new ArrayList<>();

    ArrayList<InventoryItem> currentGroup = null;
    for (int i = 0; i < sortedItems.length; i++) {
      InventoryItem item = sortedItems[i];
      if (currentGroup == null) {
        currentGroup = new ArrayList<>();
        sortedGroups.add(currentGroup);
      } else if (comparator.compare(item, sortedItems[i - 1]) != 0) {
        currentGroup = new ArrayList<>();
        sortedGroups.add(currentGroup);
      }
      currentGroup.add(item);
    }

    return new GroupedInventoryItems(sortedBy, nameGetter, sortedGroups);
  }

  public static final class InventoryItemGroup {
    private final String name;
    private final InventoryItem[] items;
    public InventoryItemGroup(@NotNull String name, @NotNull InventoryItem @NotNull ... items) {
      this.name = name;
      this.items = items;
    }
    public InventoryItemGroup(@NotNull String name, @NotNull List<InventoryItem> items) {
      this.name = name;
      this.items = items.toArray(new InventoryItem[0]);
    }

    public String getName() {
      return name;
    }

    public InventoryItem[] getItems() {
      return items;
    }
  }

  public static final class GroupedInventoryItems {
    private final String groupedBy;
    private final InventoryItemGroup[] groups;
    public GroupedInventoryItems(@NotNull String groupedBy, @NotNull InventoryItemGroup @NotNull ... groups) {
      this.groupedBy = groupedBy;
      this.groups = groups;
    }

    public GroupedInventoryItems(
        @NotNull String groupedBy,
        @NotNull Function<InventoryItem, String> categoryNameGetter,
        @NotNull ArrayList<@NotNull ArrayList<@NotNull InventoryItem>> groups
    ) {
      this.groupedBy = groupedBy;

      this.groups = new InventoryItemGroup[groups.size()];
      for (int i = 0; i < groups.size(); i++) {
        //groups.get(i).forEach(e -> System.out.println(e.getCategory()));
        List<InventoryItem> item = groups.get(i);
        String groupName = categoryNameGetter.apply(item.get(0));
        this.groups[i] = new InventoryItemGroup(groupName, item);
      }
    }

    public String getGroupedBy() {
      return groupedBy;
    }

    public InventoryItemGroup[] getGroups() {
      return groups;
    }
  }
}
