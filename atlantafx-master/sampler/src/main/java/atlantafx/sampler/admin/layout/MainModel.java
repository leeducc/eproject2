package atlantafx.sampler.admin.layout;

import atlantafx.sampler.admin.event.DefaultEventBus;
import atlantafx.sampler.admin.event.NavEvent;
import atlantafx.sampler.admin.page.Page;
import atlantafx.sampler.admin.page.components.*;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2OutlinedAL;
import org.kordamp.ikonli.material2.Material2OutlinedMZ;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static atlantafx.sampler.admin.layout.MainModel.SubLayer.PAGE;
import static atlantafx.sampler.admin.layout.MainModel.SubLayer.SOURCE_CODE;

public class MainModel {

    public static final Class<? extends Page> DEFAULT_PAGE = RevenuePage.class;

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
    private final ReadOnlyObjectWrapper<NavTree.Item> navTree = new ReadOnlyObjectWrapper<>(createTree());

    public ReadOnlyObjectProperty<NavTree.Item> navTreeProperty() {
        return navTree.getReadOnlyProperty();
    }

    private NavTree.Item createTree() {

        // Orders (Đơn hàng) group
        var orders = NavTree.Item.group("Đơn hàng", new FontIcon(Material2OutlinedMZ.SHOPPING_CART));
        orders.getChildren().setAll(
                NAV_TREE.get(RevenuePage.class),
                NAV_TREE.get(OrderListPage.class)

        );

        // Warehouse Management (Quản lý kho) group
        var warehouseManagement = NavTree.Item.group("Quản lý kho", new FontIcon(Material2OutlinedMZ.STORAGE));
        warehouseManagement.getChildren().setAll(
                NAV_TREE.get(InventoryPage.class),            // Inventory (Tồn kho)
                NAV_TREE.get(DeliveryOrderPage.class),                // Orders (Đặt hàng)
                NAV_TREE.get(ListDeliveryOrderPage.class), //     // Danh sách đơn hàng đã dặt

                NAV_TREE.get(SupplierPage.class),          // Add Supplier (Thêm mới nhà cung cấp)
                NAV_TREE.get(ProductListPage.class)           // Product List (Danh sách sản phẩm)
        );

// Work Shift (Công ca) group
        var workShift = NavTree.Item.group("Công ca", new FontIcon(Material2OutlinedMZ.SCHEDULE));
        workShift.getChildren().setAll(
                NAV_TREE.get(MakeSchedulePage.class)          // Work Schedule (Phân Lịch làm việc)
//                NAV_TREE.get(LeaveApprovalPage.class),        // Leave Approval (Phê duyệt nghỉ phép)
//                NAV_TREE.get(WorkExplanationApprovalPage.class) // Work Explanation Approval (Phê duyệt giải trình công)
        );



// Tables (Bàn) group
        var tables = NavTree.Item.group("Bàn", new FontIcon(Material2OutlinedMZ.TABLET));
        tables.getChildren().setAll(
                NAV_TREE.get(TableListPage.class),          // Table Layout (Sơ đồ bàn)
                NAV_TREE.get(EditTableList.class)           // Change Table (Thay đổi bàn)
        );

// Salary (Lương) group
        var salary = NavTree.Item.group("Lương", new FontIcon(Material2OutlinedMZ.PAYMENTS));
        salary.getChildren().setAll(
                NAV_TREE.get(SalaryListPage.class),           // Salary List (Danh sách lương)
                NAV_TREE.get(ChangeSalaryPage.class)          // Change Salary (Thay đổi lương)
        );

// Staff (Nhân viên) group
        var staff = NavTree.Item.group("Nhân viên", new FontIcon(Material2OutlinedMZ.PEOPLE));
        staff.getChildren().setAll(
                NAV_TREE.get(StaffListPage.class),            // Staff List (Danh sách nhân viên)
                NAV_TREE.get(AddStaffPage.class),             // Add Staff (Thêm mới nhân viên)
                NAV_TREE.get(ChangePasswordPage.class)        // Change Password (Thay đổi mật khẩu)
        );

// Configuration (Cấu hình) group
        var configuration = NavTree.Item.group("Cấu hình", new FontIcon(Material2OutlinedAL.ADMIN_PANEL_SETTINGS));
        configuration.getChildren().setAll(
                NAV_TREE.get(ChangePasswordSettingsPage.class), // Change Password (Đổi mật khẩu)
                NAV_TREE.get(ChangeStoreInfoPage.class)
        );


// Add all categories to the root navigation tree
        var root = NavTree.Item.root();
        root.getChildren().setAll(
                orders,
                warehouseManagement,
                tables,
                workShift,
                salary,
                staff,
                configuration
        );

        return root;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Nav Tree                                                              //
    ///////////////////////////////////////////////////////////////////////////

    public static Map<Class<? extends Page>, NavTree.Item> createNavItems() {
        var map = new HashMap<Class<? extends Page>, NavTree.Item>();
        // Đơn hàng (Orders)
        map.put(RevenuePage.class, NavTree.Item.page("Doanh thu ", RevenuePage.class));
        map.put(OrderListPage.class, NavTree.Item.page("Danh sách đơn hàng", OrderListPage.class));


        // Quản lý kho (Warehouse Management)
        map.put(InventoryPage.class, NavTree.Item.page("Tồn kho", InventoryPage.class));
        map.put(DeliveryOrderPage.class, NavTree.Item.page("Đặt hàng", DeliveryOrderPage.class));
        map.put(ListDeliveryOrderPage.class, NavTree.Item.page("Danh sách đơn hàng đã đặt", ListDeliveryOrderPage.class));


        map.put(SupplierPage.class, NavTree.Item.page("Nhà cung cấp", SupplierPage.class));

        map.put(ProductListPage.class, NavTree.Item.page("Danh sách các đồ uống", ProductListPage.class));

        // Công ca (Work Shift)
        map.put(MakeSchedulePage.class , NavTree.Item.page("Tạo lịch làm việc", MakeSchedulePage.class));
        map.put(LeaveApprovalPage.class, NavTree.Item.page("Phê duyệt nghỉ phép", LeaveApprovalPage.class));
        map.put(WorkExplanationApprovalPage.class, NavTree.Item.page("Phê duyệt giải trình công", WorkExplanationApprovalPage.class));



        // Bàn (Tables)
        map.put(TableListPage.class, NavTree.Item.page("Sơ đồ bàn", TableListPage.class));
        map.put(EditTableList.class, NavTree.Item.page("Thay đổi bàn", EditTableList.class));

        // Lương (Salary)
        map.put(SalaryListPage.class, NavTree.Item.page("Danh sách lương", SalaryListPage.class));
        map.put(ChangeSalaryPage.class, NavTree.Item.page("Thay đổi lương", ChangeSalaryPage.class));

        // Nhân viên (Staff)
        map.put(StaffListPage.class, NavTree.Item.page("Danh sách nhân viên", StaffListPage.class));
        map.put(AddStaffPage.class, NavTree.Item.page("Thêm mới nhân viên", AddStaffPage.class));
        map.put(ChangePasswordPage.class, NavTree.Item.page("Thay đổi mật khẩu nhân viên", ChangePasswordPage.class));

        // Cấu hình (Configuration)
        map.put(ChangePasswordSettingsPage.class, NavTree.Item.page("Đổi thông tin đăng nhập", ChangePasswordSettingsPage.class));
        map.put(ChangeStoreInfoPage.class, NavTree.Item.page("Thau đổi thông tin quán", ChangeStoreInfoPage.class));

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
