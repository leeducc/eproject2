package atlantafx.sampler.cashier.layout;

import atlantafx.sampler.cashier.page.components.OrderListPage;
import atlantafx.sampler.cashier.page.components.TableListPage;
import atlantafx.sampler.cashier.event.DefaultEventBus;
import atlantafx.sampler.cashier.event.NavEvent;
import atlantafx.sampler.cashier.page.Page;
import atlantafx.sampler.cashier.page.components.*;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2OutlinedMZ;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static atlantafx.sampler.cashier.layout.MainModel.SubLayer.PAGE;
import static atlantafx.sampler.cashier.layout.MainModel.SubLayer.SOURCE_CODE;

public class MainModel {

  public static final Class<? extends Page> DEFAULT_PAGE = TableListPage.class;

  private static final Map<Class<? extends Page>, NavTree.Item> NAV_TREE = createNavItems();

  public enum SubLayer {
    PAGE,
    SOURCE_CODE
  }

  public NavTree.Item getTreeItemForPage(Class<? extends Page> pageClass) {
    return NAV_TREE.getOrDefault(pageClass, NAV_TREE.get(DEFAULT_PAGE));
  }

  List<NavTree.Item> findPages(String filter) {
    return NAV_TREE.values().stream()
        .filter(item -> item.getValue() != null && item.getValue().matches(filter))
        .toList();
  }

  public MainModel() {
    DefaultEventBus.getInstance().subscribe(NavEvent.class, e -> navigate(e.getPage()));
  }

  ///////////////////////////////////////////////////////////////////////////
  // Properties                                                            //
  ///////////////////////////////////////////////////////////////////////////

  // ~
  private final ReadOnlyObjectWrapper<Class<? extends Page>> selectedPage = new ReadOnlyObjectWrapper<>();

  public ReadOnlyObjectProperty<Class<? extends Page>> selectedPageProperty() {
    return selectedPage.getReadOnlyProperty();
  }

  // ~
  private final ReadOnlyObjectWrapper<SubLayer> currentSubLayer = new ReadOnlyObjectWrapper<>(PAGE);

  public ReadOnlyObjectProperty<SubLayer> currentSubLayerProperty() {
    return currentSubLayer.getReadOnlyProperty();
  }

  // ~
  private final ReadOnlyObjectWrapper<NavTree.Item> navTree = new ReadOnlyObjectWrapper<>(
      createTree());

  public ReadOnlyObjectProperty<NavTree.Item> navTreeProperty() {
    return navTree.getReadOnlyProperty();
  }

  private NavTree.Item createTree() {
    // Bàn group
    var tables = NavTree.Item.group("Bàn", new FontIcon(Material2OutlinedMZ.TABLET));
    tables.getChildren().setAll(
        NAV_TREE.get(TableListPage.class),
        NAV_TREE.get(OrderListPage.class)// Danh sách các bàn
    );
    var viewProduct = NavTree.Item.group("Menu đồ uống", new FontIcon(Material2OutlinedMZ.TABLET));
    viewProduct.getChildren().setAll(
        NAV_TREE.get(ListProductPage.class) // Danh sách các bàn
    );

    // Thông tin cá nhân group
//    var personalInfo = NavTree.Item.group("Chức năng khác",
//        new FontIcon(Material2OutlinedMZ.PEOPLE));
//    personalInfo.getChildren().setAll(
//        NAV_TREE.get(OrderListPages.class)
//    );

    // Add all categories to the root navigation tree
    var root = NavTree.Item.root();
    root.getChildren().setAll(
        tables,
        viewProduct
    );

    return root;
  }

  ///////////////////////////////////////////////////////////////////////////
  // Nav Tree                                                              //
  ///////////////////////////////////////////////////////////////////////////

  public static Map<Class<? extends Page>, NavTree.Item> createNavItems() {
    var map = new HashMap<Class<? extends Page>, NavTree.Item>();

    // Bàn
    map.put(TableListPage.class, NavTree.Item.page("Danh sách các bàn", TableListPage.class));
    map.put(OrderListPage.class, NavTree.Item.page("Hóa đơn", OrderListPage.class));

    map.put(ListProductPage.class, NavTree.Item.page("Danh sách các đồ uống", ListProductPage.class));
    return map;
  }

  ///////////////////////////////////////////////////////////////////////////
  // Commands                                                              //
  ///////////////////////////////////////////////////////////////////////////

  public void navigate(Class<? extends Page> page) {
    selectedPage.set(Objects.requireNonNull(page));
    currentSubLayer.set(PAGE);
  }

  public void showSourceCode() {
    currentSubLayer.set(SOURCE_CODE);
  }

  public void hideSourceCode() {
    currentSubLayer.set(PAGE);
  }
}
